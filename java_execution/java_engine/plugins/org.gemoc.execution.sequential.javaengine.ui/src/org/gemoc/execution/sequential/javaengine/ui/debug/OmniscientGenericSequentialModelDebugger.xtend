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
 package org.gemoc.execution.sequential.javaengine.ui.debug;

import fr.inria.diverse.trace.commons.model.trace.Dimension
import fr.inria.diverse.trace.commons.model.trace.MSE
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence
import fr.inria.diverse.trace.commons.model.trace.State
import fr.inria.diverse.trace.commons.model.trace.Step
import fr.inria.diverse.trace.commons.model.trace.TracedObject
import fr.inria.diverse.trace.commons.model.trace.Value
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer
import fr.inria.diverse.trace.gemoc.api.ITraceViewListener
import fr.obeo.dsl.debug.ide.event.IDSLDebugEventProcessor
import java.util.ArrayList
import java.util.List
import java.util.function.BiPredicate
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.dialogs.ErrorDialog
import org.eclipse.xtext.naming.QualifiedName
import org.gemoc.execution.sequential.javaengine.ui.Activator
import org.gemoc.executionframework.engine.core.EngineStoppedException
import org.gemoc.xdsmlframework.api.core.IExecutionEngine

public class OmniscientGenericSequentialModelDebugger extends GenericSequentialModelDebugger implements ITraceViewListener {

	private var ITraceExplorer<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer

	private var steppingOverStackFrameIndex = -1

	private var steppingReturnStackFrameIndex = -1

	private val List<EObject> callerStack = new ArrayList

	private val List<Step<?>> previousCallStack = new ArrayList
	
	new(IDSLDebugEventProcessor target, IExecutionEngine engine) {
		super(target, engine)
	}

	def private MSE getMSEFromStep(Step<?> step) {
		val mseOccurrence = step.mseoccurrence
		if (mseOccurrence == null) {
			val container = step.eContainer
			if (container instanceof Step<?>) {
				val parentStep = container as Step<?>
				val parentMseOccurrence = parentStep.mseoccurrence
				if (parentMseOccurrence == null) {
					throw new IllegalStateException("A step without MSEOccurrence cannot be contained in a step without MSEOccurrence")
				} else {
					return parentMseOccurrence.mse
				}
			} else {
				throw new IllegalStateException("A step without MSEOccurrence has to be contained in a step")
			}
		} else {
			return mseOccurrence.mse
		}
	}

	def private void pushStackFrame(String threadName, Step<?> step) {
		var MSE mse = getMSEFromStep(step)
		var EObject caller = mse.caller
		val QualifiedName qname = nameprovider.getFullyQualifiedName(caller)
		val String objectName = if(qname !== null) qname.toString() else caller.toString()
		val String opName = if (step.mseoccurrence == null) {mse.action?.name + "_implicitStep"} else {mse.action?.name}
		val String callerType = caller.eClass().getName()
		val String prettyName = "(" + callerType + ") " + objectName + " -> " + opName + "()"
		pushStackFrame(threadName, prettyName, caller, caller)
		callerStack.add(0, caller)
	}

	override void popStackFrame(String threadName) {
		super.popStackFrame(threadName)
		callerStack.remove(0)
	}
	
	override void aboutToExecuteStep(IExecutionEngine executionEngine, Step<?> step) {
		val mseOccurrence = step.mseoccurrence
		if (mseOccurrence != null) {
			if (!control(threadName, mseOccurrence)) {
				throw new EngineStoppedException("Debug thread has stopped.");
			}
		}
	}

	override public void resume() {
		if (!executionTerminated) {
			if (traceExplorer.inReplayMode) {
				traceExplorer.loadLastState
			}
			super.resume
		}
	}

	override public void resume(String threadName) {
		if (!executionTerminated) {
			if (traceExplorer.inReplayMode) {
				traceExplorer.loadLastState
			}
			super.resume(threadName)
		}
	}

	override public void terminate() {
		super.terminate()
		Activator.^default.debuggerSupplier = [|null]
	}

	override protected void setupStepOverPredicateBreak() {
		if (steppingOverStackFrameIndex != -1) {
			val seqEngine = engine as IExecutionEngine
			val stack = traceExplorer.callStack
			val idx = stack.size - steppingOverStackFrameIndex - 1
			// We add a future break as soon as the step is over
			addPredicateBreak(new BiPredicate<IExecutionEngine, MSEOccurrence>() {
				// The operation we want to step over
				private MSEOccurrence steppedOver = stack.get(idx).mseoccurrence

				override test(IExecutionEngine t, MSEOccurrence u) {
					return !seqEngine.getCurrentStack().contains(steppedOver)
				}
			})
			steppingOverStackFrameIndex = -1
		} else {
			super.setupStepOverPredicateBreak
		}
	}

	override public void stepInto(String threadName) {
		if (traceExplorer.inReplayMode || executionTerminated) {
			if (!traceExplorer.stepInto && !executionTerminated) {
				traceExplorer.loadLastState
				super.stepInto(threadName)
			}
		} else {
			super.stepInto(threadName)
		}
	}

	override public void stepOver(String threadName) {
		if (traceExplorer.inReplayMode || executionTerminated) {
			if (!traceExplorer.stepOver && !executionTerminated) {
				steppingOverStackFrameIndex = nbStackFrames - 1
				traceExplorer.loadLastState
				super.stepOver(threadName)
			}
		} else {
			super.stepOver(threadName)
		}
	}

	override protected void setupStepReturnPredicateBreak() {
		if (steppingReturnStackFrameIndex != -1) {
			val seqEngine = engine as IExecutionEngine
			val stack = traceExplorer.callStack
			val idx = stack.size - steppingReturnStackFrameIndex - 1
			addPredicateBreak(new BiPredicate<IExecutionEngine, MSEOccurrence>() {
				private MSEOccurrence steppedReturn = stack.get(idx).mseoccurrence

				override test(IExecutionEngine t, MSEOccurrence u) {
					return !seqEngine.getCurrentStack().contains(steppedReturn)
				}
			})
			steppingReturnStackFrameIndex = -1
		} else {
			super.setupStepReturnPredicateBreak
		}
	}

	override public void stepReturn(String threadName) {
		if (traceExplorer.inReplayMode || executionTerminated) {
			if (!traceExplorer.stepReturn && !executionTerminated) {
				steppingReturnStackFrameIndex = nbStackFrames - 2
				traceExplorer.loadLastState
				super.stepReturn(threadName)
			}
		} else {
			super.stepReturn(threadName)
		}
	}

	def public void stepBackInto() {
		traceExplorer.stepBackInto
	}

	def public void stepBackOver() {
		traceExplorer.stepBackOver
	}

	def public void stepBackOut() {
		traceExplorer.stepBackOut
	}

	def public boolean canStepBackInto() {
		return traceExplorer.canStepBackInto
	}

	def public boolean canStepBackOver() {
		return traceExplorer.canStepBackOver
	}

	def public boolean canStepBackOut() {
		return traceExplorer.canStepBackOut
	}

//	def private setupStepValuePredicateBreak(IValueTrace valueTrace, int valueIndex) {
//		addPredicateBreak(new BiPredicate<IBasicExecutionEngine, MSEOccurrence>() {
//			override test(IBasicExecutionEngine t, MSEOccurrence u) {
//				val i = valueTrace.getActiveValueIndex(currentStateIndex)
//				val j = valueIndex
//				return i == j
//			}
//		});
//	}

	override public validateVariableValue(String threadName, String variableName, String value) {
		if (traceExplorer.inReplayMode) {
			ErrorDialog.openError(null, "Illegal variable value set",
				"Cannot set the value of a variable when in replay mode",
				new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Illegal variable value set"))
			return false
		}
		return super.validateVariableValue(threadName, variableName, value)
	}

	override void engineStarted(IExecutionEngine executionEngine) {
		val Activator activator = Activator.getDefault()
		activator.debuggerSupplier = [this]
		super.engineStarted(executionEngine)
		val traceAddons = executionEngine.getAddonsTypedBy(IMultiDimensionalTraceAddon)
		val traceAddon = traceAddons.iterator().next()
		traceExplorer = traceAddon.getTraceExplorer()
		traceExplorer.registerCommand(this, [|update])
	}

	override void engineAboutToStop(IExecutionEngine engine) {
		traceExplorer.loadLastState
		super.engineAboutToStop(engine)
	}

	override void engineStopped(IExecutionEngine executionEngine) {
		val Activator activator = Activator.getDefault()
		activator.debuggerSupplier = null
		super.engineStopped(executionEngine)
	}

	override updateStack(String threadName, EObject instruction) {
		var i = 0
		while (i < previousCallStack.size && i < traceExplorer.callStack.size && previousCallStack.get(i) == traceExplorer.callStack.get(i)) {
			i++
		}
		for (var j = i; j < previousCallStack.size; j++) {
			popStackFrame(threadName)
		}
		for (var j = i; j < traceExplorer.callStack.size; j++) {
			pushStackFrame(threadName, traceExplorer.callStack.get(j))
		}
		if (!callerStack.empty) {
			setCurrentInstruction(threadName, callerStack.get(0))
		}
		previousCallStack.clear
		previousCallStack.addAll(traceExplorer.callStack)
	}

	override update() {
		if (executedModelRoot != null) {
			try {
				if(!callerStack.empty){
					updateData(threadName, callerStack.findFirst[true])
				} else {
					
				}
			} catch (IllegalStateException e) {
				// Shhh
			}
		}
	}
}
