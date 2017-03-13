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
package fr.inria.diverse.trace.gemoc.api;

import java.util.List;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;

public interface ITraceExplorer<StepSubType extends Step<?>, StateSubType extends State<?,?>, TracedObjectSubType extends TracedObject<?>, DimensionSubType extends Dimension<?>, ValueSubType extends Value<?>> extends ITraceViewNotifier, ITraceListener {

	/**
	 * Loads the trace into the trace explorer
	 * @param trace
	 */
	void loadTrace(Trace<StepSubType, TracedObjectSubType, StateSubType> trace);
	
	/**
	 * Loads the trace into the trace explorer
	 * @param trace
	 * @param stateManager
	 */
	void loadTrace(Trace<StepSubType, TracedObjectSubType, StateSubType> trace, IStateManager<StateSubType> stateManager);
	
	/**
	 * @return The current step
	 */
	StepSubType getCurrentForwardStep();

	/**
	 * @return The step (big or small) preceding the current step
	 */
	StepSubType getCurrentBackwardStep();

	/**
	 * @return The big step containing the current step
	 */
	StepSubType getCurrentBigStep();

	/**
	 * @return The current state
	 */
	StateSubType getCurrentState();

	/**
	 * Updates the explorer so that <code>state</code> becomes the current state.
	 * @param state The State to jump to
	 */
	void jump(StateSubType state);

	/**
	 * Updates the explorer so that the first state to contain <code>value</code>
	 * becomes the current state.
	 * @param value The Value to jump to
	 */
	void jump(ValueSubType value);

	/**
	 * Updates the explorer so that the last recorded state and step in the trace become the current state and step.
	 */
	void loadLastState();

	/**
	 * If the current step is a big step, updates the state of the explorer so that its current step
	 * becomes the first step contained by the current step.
	 * If the current step is a small step, updates the state of the explorer so that its current step
	 * becomes the step following the current step.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepInto();

	/**
	 * Updates the state of the explorer so that its current step becomes the step following the current step.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepOver();

	/**
	 * Updates the state of the explorer so that its current step becomes the step following the big step
	 * containing the current step.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepReturn();

	/**
	 * @return Whether the stepBackInto method can be called
	 */
	boolean canStepBackInto();

	/**
	 * @return Whether the stepBackOver method can be called
	 */
	boolean canStepBackOver();

	/**
	 * @return Whether the stepBackOut method can be called
	 */
	boolean canStepBackOut();

	/**
	 * Updates the state of the explorer so that its current step becomes the small step preceding the current step.
	 * This is the backward equivalent of the step into operation.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepBackInto();

	/**
	 * Updates the state of the explorer so that its current step becomes the step (big or small)
	 * preceding the current step.
	 * This is the backward equivalent of the step over operation.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepBackOver();

	/**
	 * Updates the state of the explorer so that its current step becomes the big step containing the current step.
	 * This is the backward equivalent of the step return operation.
	 * @return Whether the operation succeeded or not
	 */
	boolean stepBackOut();

	/**
	 * Updates the state of the explorer so that its current state is the first one to contain
	 * the value following the current one in the provided dimension.
	 * @param dimension
	 */
	void stepValue(DimensionSubType dimension);

	/**
	 * Updates the state of the explorer so that its current state is the first one to contain
	 * the value preceding the current one in the provided dimension.
	 * @param dimension
	 */
	void backValue(DimensionSubType dimension);

	/**
	 * Returns whether the provided dimension has at least one value following the current one.
	 * @param dimension
	 * @return Whether the dimension has a value following the current one
	 */
	boolean canStepValue(DimensionSubType dimension);

	/**
	 * Returns whether the provided dimension has at least one value preceding the current one.
	 * @param dimension
	 * @return Whether the dimension has a value preceding the current one
	 */
	boolean canBackValue(DimensionSubType dimension);

	/**
	 * @return Whether the explorer is in replay mode or not
	 */
	boolean isInReplayMode();

	/**
	 * Returns the current call stack of the explorer. The current step is at the end of the list.
	 * @return The current call stack
	 */
	List<StepSubType> getCallStack();

	/**
	 * Updates the call stack of the explorer so that the provided step becomes the current step.
	 * The state of the explorer is then recomputed accordingly. 
	 * @param step The step that will become the current step
	 */
	void updateCallStack(StepSubType step);
}
