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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.gemoc.executionframework.engine.core.EngineStoppedException;
import org.gemoc.executionframework.engine.ui.debug.AbstractGemocDebugger;
import org.gemoc.executionframework.engine.ui.debug.breakpoint.GemocBreakpoint;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;

import fr.inria.diverse.melange.resource.MelangeResourceImpl;
import fr.inria.diverse.trace.commons.model.trace.MSE;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.obeo.dsl.debug.ide.event.IDSLDebugEventProcessor;

public class GenericSequentialModelDebugger extends AbstractGemocDebugger {

	/**
	 * A fake instruction to prevent the stepping return to stop on each event.
	 */
	private static final EObject FAKE_INSTRUCTION = EcorePackage.eINSTANCE;

	private List<ToPushPop> toPushPop = new ArrayList<>();

	protected final String threadName = "Model debugging";

	protected int nbStackFrames = 0;

	protected boolean executionTerminated = false;

	public GenericSequentialModelDebugger(IDSLDebugEventProcessor target, IExecutionEngine engine) {
		super(target, engine);
	}

	@Override
	/*
	 * This method is eventually called within a new engine thread.
	 * (non-Javadoc)
	 * 
	 * @see fr.obeo.dsl.debug.ide.IDSLDebugger#start()
	 */
	public void start() {
		engine.start();
	}

	@Override
	public void disconnect() {
		return;
	}

	protected void setupStepReturnPredicateBreak() {
		final IExecutionEngine seqEngine = (IExecutionEngine) engine;
		final Deque<MSEOccurrence> stack = seqEngine.getCurrentStack();
		if (stack.size() > 1) {
			final Iterator<MSEOccurrence> it = stack.iterator();
			it.next();
			addPredicateBreak(new BiPredicate<IExecutionEngine, MSEOccurrence>() {
				// The operation we want to step return
				private MSEOccurrence steppedReturn = it.next();

				@Override
				public boolean test(IExecutionEngine t, MSEOccurrence u) {
					// We finished stepping over once the mseoccurrence is not
					// there anymore
					return !seqEngine.getCurrentStack().contains(steppedReturn);
				}
			});
		}
	}

	@Override
	public void steppingReturn(String threadName) {
		// To send notifications
		super.steppingReturn(threadName);
		// We add a future break as soon as the step is returned
		setupStepReturnPredicateBreak();
	}

	protected void setupStepOverPredicateBreak() {
		addPredicateBreak(new BiPredicate<IExecutionEngine, MSEOccurrence>() {
			final IExecutionEngine seqEngine = (IExecutionEngine) engine;
			// The operation we want to step over
			private MSEOccurrence steppedOver = seqEngine.getCurrentMSEOccurrence();

			@Override
			public boolean test(IExecutionEngine t, MSEOccurrence u) {
				// We finished stepping over once the mseoccurrence is not there
				// anymore
				return !seqEngine.getCurrentStack().contains(steppedOver);
			}
		});
	}

	@Override
	public void steppingOver(String threadName) {
		// To send notifications
		super.steppingOver(threadName);
		// We add a future break as soon as the step is over
		setupStepOverPredicateBreak();
	}

	@Override
	public boolean canStepInto(String threadName, EObject instruction) {
		return currentInstructions.get(threadName) == instruction;
	}

	@Override
	public void steppingInto(String threadName) {
		// To send notifications, but probably useless
		super.steppingInto(threadName);
		// We add a future break asap
		addPredicateBreak(new BiPredicate<IExecutionEngine, MSEOccurrence>() {
			@Override
			public boolean test(IExecutionEngine t, MSEOccurrence u) {
				// We finished stepping as soon as we encounter a new step
				return true;
			}
		});
	}

	@Override
	public void pushStackFrame(String threadName, String frameName, EObject context, EObject instruction) {
		super.pushStackFrame(threadName, frameName, context, instruction);
		nbStackFrames++;
	}

	@Override
	public void popStackFrame(String threadName) {
		super.popStackFrame(threadName);
		nbStackFrames--;
	}

	protected final DefaultDeclarativeQualifiedNameProvider nameprovider = new DefaultDeclarativeQualifiedNameProvider();

	@Override
	protected void updateStack(String threadName, EObject instruction) {
		// Catching the stack up with events that occurred since last suspension
		// We use a virtual stack to replay the last events to avoid pushing
		// stackframes that would be popped right after.
		Deque<MSEOccurrence> virtualStack = new ArrayDeque<>();
		for (ToPushPop m : toPushPop) {
			if (m.push) {
				// We push the mse onto the virtual stack.
				virtualStack.push(m.mseOccurrence);
			} else {
				if (virtualStack.isEmpty()) {
					// The virtual stack is empty, we pop the top stackframe off
					// of the real stack.
					popStackFrame(threadName);
				} else {
					// The virtual stack is not empty, we pop the top stackframe
					// off of it.
					virtualStack.pop();
				}
			}
		}

		// We then push the missing stackframes onto the real stack.
		Iterator<MSEOccurrence> iterator = virtualStack.descendingIterator();
		while (iterator.hasNext()) {
			MSEOccurrence mseOccurrence = iterator.next();
			EObject caller = mseOccurrence.getMse().getCaller();
			QualifiedName qname = nameprovider.getFullyQualifiedName(caller);
			String objectName = "";
			if (qname != null)
				objectName = qname.toString();
			else
				objectName = caller.toString();
			String opName = mseOccurrence.getMse().getAction().getName();
			String callerType = caller.eClass().getName();
			String prettyName = "(" + callerType + ") " + objectName + " -> " + opName + "()";
			pushStackFrame(threadName, prettyName, caller, caller);
		}

		setCurrentInstruction(threadName, instruction);

		toPushPop.clear();
	}

	@Override
	public void updateData(String threadName, EObject instruction) {
		if (instruction == null) {
			updateVariables(threadName);
			updateStack(threadName, null);
			return;
		}

		// We don't want to deal with logical steps since we are in sequential mode
		if (instruction instanceof Step<?>) {
			instruction = ((Step<?>) instruction).getMseoccurrence().getMse().getCaller();
		} else if (instruction instanceof MSEOccurrence) {
			instruction = ((MSEOccurrence) instruction).getMse().getCaller();
		}

		super.updateData(threadName, instruction);
	}

	@Override
	public boolean shouldBreak(EObject instruction) {
		if (instruction instanceof MSEOccurrence) {
			return shouldBreakMSEOccurence((MSEOccurrence) instruction);
		} else if (instruction == FAKE_INSTRUCTION) {
			// Part of the breakpoint simulation to suspend the execution once
			// the end has been reached.
			return true;
		}
		return false;
	}

	private boolean hasRegularBreakpointTrue(EObject o) {
		EObject target = o;
		// Try to get the original object if 'o' comes from 
		// a downcast resource
		if(this.engine instanceof PlainK3ExecutionEngine){
			Resource res = o.eResource();
			if(res != null) {
				
				MelangeResourceImpl mr = null;
				for(Resource candidate : res.getResourceSet().getResources()) {
					if(candidate instanceof MelangeResourceImpl) {
						mr = (MelangeResourceImpl) candidate;
						break;
					}
				}
				
				if(mr != null) {
					String uriFragment = res.getURIFragment(o);
					target = mr.getWrappedResource().getEObject(uriFragment);
				}
			}
		}
		
		return super.shouldBreak(target)
				&& (Boolean.valueOf((String) getBreakpointAttributes(target, GemocBreakpoint.BREAK_ON_LOGICAL_STEP)) || Boolean
						.valueOf((String) getBreakpointAttributes(target, GemocBreakpoint.BREAK_ON_MSE_OCCURRENCE)));
	}

	private boolean shouldBreakMSEOccurence(MSEOccurrence mseOccurrence) {
		if (shouldBreakPredicates(engine, mseOccurrence))
			return true;
		// If still no break yet, we look at regular breakpoints on MSE
		MSE mse = mseOccurrence.getMse();
		if (hasRegularBreakpointTrue(mse)) {
			return true;
		}
		// If still no break yet, we look at regular breakpoints on MSE's caller
		EObject caller = mseOccurrence.getMse().getCaller();
		if (hasRegularBreakpointTrue(caller)) {
			return true;
		}
		return false;
	}

	@Override
	public EObject getNextInstruction(String threadName, EObject currentInstruction, Stepping stepping) {
		// We always return fakeinstruction to make sure to not stop and to
		// handle everything with shouldBreak
		return FAKE_INSTRUCTION;
	}

	@Override
	public void engineStarted(IExecutionEngine executionEngine) {
		spawnRunningThread(threadName, engine.getExecutionContext().getResourceModel().getContents().get(0));
	}

	@Override
	public void engineStopped(IExecutionEngine engine) {
		if (!isTerminated(threadName)) {
			terminated(threadName);
		}
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine executionEngine, Step<?> step) {
		MSEOccurrence mseOccurrence = step.getMseoccurrence();
		if (mseOccurrence != null) {
			ToPushPop stackModification = new ToPushPop(mseOccurrence, true);
			toPushPop.add(stackModification);
			if (!control(threadName, mseOccurrence)) {
				throw new EngineStoppedException("Debug thread has stopped.");
			}
		}
	}

	@Override
	public void stepExecuted(IExecutionEngine engine, Step<?> step) {
		MSEOccurrence mseOccurrence = step.getMseoccurrence();
		if (mseOccurrence != null) {
			ToPushPop stackModification = new ToPushPop(mseOccurrence, false);
			toPushPop.add(stackModification);
		}
	}

	@Override
	public void engineAboutToStop(IExecutionEngine engine) {
		// Simulating breakpoint
		// TODO maybe display a warning informing the user the execution has
		// ended, as resuming execution will prevent further interactions with the
		// trace and the debugging facilities, which might not be desirable.
		executionTerminated = true;
		control(threadName, FAKE_INSTRUCTION);
	}

	@Override
	public void terminate() {
		super.terminate();
		engine.stop();
	}

	private static class ToPushPop {
		public MSEOccurrence mseOccurrence;
		public boolean push;

		ToPushPop(MSEOccurrence mseOccurrence, boolean push) {
			this.mseOccurrence = mseOccurrence;
			this.push = push;
		}
	}
}
