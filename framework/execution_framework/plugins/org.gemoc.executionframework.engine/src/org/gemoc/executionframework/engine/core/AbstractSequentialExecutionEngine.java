/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.gemoc.executionframework.engine.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.gemoc.executionframework.engine.Activator;
import org.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;

import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.EventManagerRegistry;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IEventManager;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictraceFactory;
import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.GenericMSE;
import fr.inria.diverse.trace.commons.model.trace.MSE;
import fr.inria.diverse.trace.commons.model.trace.MSEModel;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TraceFactory;
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon;


/**
 * Abstract class providing a basic implementation for sequential engines
 *  
 * @author Didier Vojtisek<didier.vojtisek@inria.fr>
 *
 */
public abstract class AbstractSequentialExecutionEngine extends AbstractExecutionEngine implements IExecutionEngine {

	private MSEModel _actionModel;
	private IMultiDimensionalTraceAddon<?,?,?,?,?> traceAddon;

	protected abstract void executeEntryPoint();
	
	/**
	 * if it exists, calls the method tagged as @Initialize
	 */
	protected abstract void initializeModel();

	/**
	 * search for an applicable entry point for the simulation, this is typically a method having the @Main annotation
	 * @param executionContext the execution context of the simulation
	 */
	protected abstract void prepareEntryPoint(IExecutionContext executionContext);

	/**
	 * search for an applicable method tagged as @Initialize
	 */
	protected abstract void prepareInitializeModel(IExecutionContext executionContext);

	@Override
	public final void performInitialize(IExecutionContext executionContext) {
		@SuppressWarnings("rawtypes")
		Set<IMultiDimensionalTraceAddon> traceManagers = this.getAddonsTypedBy(IMultiDimensionalTraceAddon.class);
		if (!traceManagers.isEmpty())
			this.traceAddon = traceManagers.iterator().next();
		prepareEntryPoint(executionContext);
		prepareInitializeModel(executionContext);
	}

	@Override
	protected final void performStart() {
		initializeModel();
		notifyEngineInitialized();
		executeEntryPoint();
		Activator.getDefault().info("Execution finished");
	}
	
	private void manageEvents() {
		MSEOccurrence mse = getCurrentMSEOccurrence();
		if (mse != null) {
			EObject container = mse.eContainer();
			if (container instanceof BigStep<?,?>) {
				IEventManager eventManager = EventManagerRegistry.getInstance().findEventManager();
				if (eventManager != null) {
					eventManager.manageEvents();
				}
			}
		}
	}

	@Override
	protected final void afterExecutionStep() {
		manageEvents();
		super.afterExecutionStep();
	}
	
	/**
	 * To be called just before each execution step by an implementing engine.
	 */
	protected final void beforeExecutionStep(Object caller, String className, String operationName) {
		// We will trick the transaction with an empty command. This most
		// probably make rollbacks impossible, but at least we can manage
		// transactions the way we want.
		RecordingCommand rc = new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
			}
		};

		beforeExecutionStep(caller, className, operationName, rc);
		rc.execute();
	}

	/**
	 * To be called just after each execution step by an implementing engine. If
	 * the step was done through a RecordingCommand, it can be given.
	 */
	protected final void beforeExecutionStep(Object caller, String className, String operationName, RecordingCommand rc) {
		if (caller != null && caller instanceof EObject && editingDomain != null) {
			// Call expected to be done from an EMF model, hence EObjects
			EObject callerCast = (EObject) caller;
			// We create a step
			Step<?> step = createStep(callerCast, className, operationName);

			manageEvents();

			beforeExecutionStep(step, rc);
		}
	}

	private Step<?> createStep(EObject caller, String className, String methodName) {
		MSE mse = findOrCreateMSE(caller, className, methodName);
		Step<?> result;
		if (traceAddon == null) {
			GenericSequentialStep step = GenerictraceFactory.eINSTANCE.createGenericSequentialStep();
			MSEOccurrence occurrence = null;
			occurrence = TraceFactory.eINSTANCE.createMSEOccurrence();
			step.setMseoccurrence(occurrence);
			occurrence.setMse(mse);
			result = step;
		} else {
			result = traceAddon.getFactory().createStep(mse, new ArrayList<Object>(), new ArrayList<Object>());
		}

		return result;
	}

	private EOperation findOperation(EObject object, String className, String methodName) {
		// We try to find the corresponding EOperation in the execution
		// metamodel
		for (EOperation operation : object.eClass().getEAllOperations()) {
			// TODO !!! this is not super correct yet as overloading allows the
			// definition of 2 methods with the same name !!!
			if (operation.getName().equalsIgnoreCase(methodName)) {
				return operation;
			}
		}

		// If we didn't find it, we try to find the class that should contain
		// this operation
		EClass containingEClass = null;
		if (object.eClass().getName().equalsIgnoreCase(className)) {
			containingEClass = object.eClass();
		} else {
			for (EClass candidate : object.eClass().getEAllSuperTypes()) {
				if (candidate.getName().equalsIgnoreCase(className)) {
					containingEClass = candidate;
				}
			}
		}

		// Then we create the missing operation (VERY approximatively)
		EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
		if (containingEClass != null) {
			containingEClass.getEOperations().add(operation);
		}
		operation.setName(methodName);
		return operation;
	}

	
	
	/**
	 * Find the MSE element for the triplet caller/className/MethodName in the model of precalculated possible MSE.
	 * If it doesn't exist yet, create one and add it to the model.
	 * @param caller the caller object
	 * @param className the class containing the method
	 * @param methodName the name of the method
	 * @return the retrieved or created MSE
	 */
	public final MSE findOrCreateMSE(EObject caller, String className, String methodName) {
		EOperation operation = findOperation(caller, className, methodName);
		// TODO Should be created/loaded before execution by analyzing the
		// model?
		if (_actionModel == null) {
			_actionModel = TraceFactory.eINSTANCE.createMSEModel();
		}

		if (_actionModel != null) {
			for (MSE existingMSE : _actionModel.getOwnedMSEs()) {
				if (existingMSE.getCaller().equals(caller) && ((existingMSE.getAction() != null && existingMSE.getAction().equals(operation)) || (existingMSE.getAction() == null && operation == null))) {
					// no need to create one, we already have it
					return existingMSE;
				}
			}
		}
		// let's create a MSE
		final GenericMSE mse = TraceFactory.eINSTANCE.createGenericMSE();
		mse.setCallerReference(caller);
		mse.setActionReference(operation);
		if (operation != null)
			mse.setName("MSE_" + caller.getClass().getSimpleName() + "_" + operation.getName());
		else
			mse.setName("MSE_" + caller.getClass().getSimpleName() + "_" + methodName);
		// and add it for possible reuse
		if (_actionModel != null) {

			if (_actionModel.eResource() != null) {
				TransactionUtil.getEditingDomain(_actionModel.eResource());
				RecordingCommand command = new RecordingCommand(TransactionUtil.getEditingDomain(_actionModel.eResource()), "Saving new MSE ") {
					@Override
					protected void doExecute() {
						_actionModel.getOwnedMSEs().add(mse);
						try {
							_actionModel.eResource().save(null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				TransactionUtil.getEditingDomain(_actionModel.eResource()).getCommandStack().execute(command);
			}
		} else {
			_actionModel.getOwnedMSEs().add(mse);
		}
		return mse;
	}

	@Override
	protected void beforeStart() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void performStop() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void finishDispose() {
		// TODO Auto-generated method stub
	}
}
