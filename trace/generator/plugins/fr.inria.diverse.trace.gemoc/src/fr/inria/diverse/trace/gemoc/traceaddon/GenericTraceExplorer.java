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
package fr.inria.diverse.trace.gemoc.traceaddon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.IStateManager;
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer;
import fr.inria.diverse.trace.gemoc.api.ITraceViewListener;

public class GenericTraceExplorer implements ITraceExplorer<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> {

	private Trace<?,?,?> trace;

	final private List<Step<?>> callStack = new ArrayList<>();

	private State<?, ?> currentState;

	private Step<?> stepIntoResult;
	private Step<?> stepOverResult;
	private Step<?> stepReturnResult;

	private Step<?> stepBackIntoResult;
	private Step<?> stepBackOverResult;
	private Step<?> stepBackOutResult;

	private HashMap<Dimension<?>, Boolean> canBackValueCache = new HashMap<>();
	private HashMap<Dimension<?>, Boolean> canStepValueCache = new HashMap<>();

	private Map<ITraceViewListener,Set<TraceViewCommand>> listeners = new HashMap<>();
	
	private IStateManager<State<?,?>> stateManager;
	
	public GenericTraceExplorer(Trace<?,?,?> trace) {
		this.trace = trace;
	}
	
	public GenericTraceExplorer(Trace<?,?,?> trace, IStateManager<State<?,?>> stateManager) {
		this.stateManager = stateManager;
		this.trace = trace;
	}
	
	private List<? extends Step<?>> getSubSteps(Step<?> step) {
		if (step instanceof BigStep<?,?>) {
			return ((BigStep<?,?>) step).getSubSteps();
		} else {
			return Collections.emptyList();
		}
	}

	private Step<?> computeBackInto(List<Step<?>> stepPath) {
		final List<? extends Step<?>> rootSteps = getSubSteps(trace.getRootStep());
		final int depth = stepPath.size();
		Step<?> result = null;
		if (depth > 1) {
			final Step<?> currentStep = stepPath.get(depth - 1);
			final Step<?> parentStep = stepPath.get(depth - 2);
			final SequentialStep<?,?> parentStep_cast = (SequentialStep<?,?>) parentStep;
			final List<? extends Step<?>> parentSubSteps = parentStep_cast.getSubSteps();
			final int idx = parentSubSteps.indexOf(currentStep);
			if (idx == 0) {
				// If the current step is the first in its parents substeps,
				// return parent step
				result = parentStep;
			} else if (idx > 0) {
				// Otherwise, return the deepest substep in the previous sibling
				// step
				final Step<?> previousSiblingStep = parentSubSteps.get(idx - 1);
				Step<?> tmpStep = previousSiblingStep;
				final List<Step<?>> tmpSubSteps = new ArrayList<>();
				tmpSubSteps.clear();
				if (tmpStep instanceof SequentialStep<?,?>) {
					SequentialStep<?,?> tmpStep_cast = (SequentialStep<?,?>) tmpStep;
					tmpSubSteps.addAll(tmpStep_cast.getSubSteps());
				}
				while (!tmpSubSteps.isEmpty()) {
					tmpStep = tmpSubSteps.get(tmpSubSteps.size() - 1);
					tmpSubSteps.clear();
					if (tmpStep instanceof SequentialStep<?,?>) {
						SequentialStep<?,?> tmpStep_cast = (SequentialStep<?,?>) tmpStep;
						tmpSubSteps.addAll(tmpStep_cast.getSubSteps());
					}
				}
				result = tmpStep;
			}
		} else if (depth == 1) {
			final Step<?> currentStep = stepPath.get(0);
			final int idx = rootSteps.indexOf(currentStep);
			if (idx > 0) {
				Step<?> tmpStep = rootSteps.get(idx - 1);
				final List<Step<?>> tmpSubSteps = new ArrayList<>();
				tmpSubSteps.clear();
				if (tmpStep instanceof SequentialStep<?,?>) {
					SequentialStep<?,?> tmpStep_cast = (SequentialStep<?,?>) tmpStep;
					tmpSubSteps.addAll(tmpStep_cast.getSubSteps());
				}
				while (!tmpSubSteps.isEmpty()) {
					tmpStep = tmpSubSteps.get(tmpSubSteps.size() - 1);
					tmpSubSteps.clear();
					if (tmpStep instanceof SequentialStep<?,?>) {
						SequentialStep<?,?> tmpStep_cast = (SequentialStep<?,?>) tmpStep;
						tmpSubSteps.addAll(tmpStep_cast.getSubSteps());
					}
				}
				result = tmpStep;
			}
		}
		return result;
	}

	private Step<?> computeBackOver(List<Step<?>> stepPath) {
		final List<? extends Step<?>> rootSteps = getSubSteps(trace.getRootStep());
		final int depth = stepPath.size();
		Step<?> result = null;
		if (depth > 1) {
			final Step<?> currentStep = stepPath.get(depth - 1);
			final Step<?> parentStep = stepPath.get(depth - 2);
			final BigStep<?,?> parentStep_cast = (BigStep<?,?>) parentStep;
			final List<? extends Step<?>> parentSubSteps = parentStep_cast.getSubSteps();
			final int idx = parentSubSteps.indexOf(currentStep);
			if (idx == 0) {
				// If the current step is the first in its parents substeps,
				// return parent step
				result = parentStep;
			} else {
				// Otherwise, return the previous sibling step
				result = parentSubSteps.get(idx - 1);
			}
		} else if (depth == 1) {
			final Step<?> currentStep = stepPath.get(0);
			final int idx = rootSteps.indexOf(currentStep);
			if (idx > 0) {
				result = rootSteps.get(idx - 1);
			}
		}
		return result;
	}

	private Step<?> computeBackOut(List<Step<?>> stepPath) {
		if (stepPath.size() > 1) {
			return stepPath.get(stepPath.size() - 2);
		}
		return null;
	}

	private Step<?> computeStepInto(List<? extends Step<?>> stepPath, List<? extends Step<?>> rootSteps) {
		return findNextStep(stepPath, null, 0);
	}

	private Step<?> computeStepOver(List<? extends Step<?>> stepPath, List<? extends Step<?>> rootSteps) {
		if (!stepPath.isEmpty()) {
			return findNextStep(stepPath, stepPath.get(stepPath.size() - 1), 1);
		}
		return null;
	}

	private Step<?> computeStepReturn(List<? extends Step<?>> stepPath, List<? extends Step<?>> rootSteps) {
		if (stepPath.size() > 1) {
			return findNextStep(stepPath, stepPath.get(stepPath.size() - 2), 2);
		}
		return null;
	}

	private Step<?> findNextStep(final List<? extends Step<?>> stepPath, final Step<?> previousStep, final int start) {
		final List<? extends Step<?>> rootSteps = getSubSteps(trace.getRootStep());
		Step<?> result = null;
		int i = start;
		int depth = stepPath.size();
		Step<?> previous = previousStep;
		while (result == null && i < depth) {
			final Step<?> currentStep = stepPath.get(depth - i - 1);
			final List<Step<?>> currentSubSteps = new ArrayList<>();
			if (currentStep instanceof BigStep<?,?>) {
				currentSubSteps.addAll(((BigStep<?,?>) currentStep).getSubSteps());
			}
			if (currentSubSteps.isEmpty()) {
				// No substep to step into, we thus have to explore the substeps
				// of the parent step
				previous = currentStep;
			} else {
				if (previous == null) {
					// First time we step into
					result = currentSubSteps.get(0);
				} else {
					final int idx = currentSubSteps.indexOf(previous) + 1;
					if (idx < currentSubSteps.size()) {
						// We step into the next step
						result = currentSubSteps.get(idx);
					} else {
						previous = currentStep;
					}
				}
			}
			i++;
		}
		if (result == null) {
			final int idx = rootSteps.indexOf(previous) + 1;
			if (idx < rootSteps.size()) {
				result = rootSteps.get(idx);
			}
		}
		return result;
	}

	private void computeExplorerState(List<Step<?>> stepPath) {
		final List<? extends Step<?>> rootSteps = getSubSteps(trace.getRootStep());

		stepIntoResult = computeStepInto(stepPath, rootSteps);
		stepOverResult = computeStepOver(stepPath, rootSteps);
		stepReturnResult = computeStepReturn(stepPath, rootSteps);

		stepBackIntoResult = computeBackInto(stepPath);
		stepBackOverResult = computeBackOver(stepPath);
		stepBackOutResult = computeBackOut(stepPath);

		callStack.clear();
		callStack.addAll(stepPath);
	}
	
	private void goTo(State<?,?> state) {
		if (state != null && stateManager != null) {
			stateManager.restoreState(state);
		}
	}
	
	private void jumpBeforeStep(Step<?> step) {
		if (step != null) {
			final State<?,?> state = step.getStartingState();
			currentState = state;
			goTo(currentState);
			updateCallStack(step);
		}
	}
	
	@Override
	public void loadTrace(Trace<Step<?>, TracedObject<?>, State<?,?>> trace) {
		this.trace = trace;
	}
	
	@Override
	public void loadTrace(Trace<Step<?>, TracedObject<?>, State<?,?>> trace, IStateManager<State<?,?>> stateManager) {
		this.stateManager = stateManager;
		this.trace = trace;
	}

	@Override
	public Step<?> getCurrentForwardStep() {
		if (!callStack.isEmpty()) {
			return callStack.get(callStack.size() - 1);
		}
		return null;
	}

	@Override
	public Step<?> getCurrentBackwardStep() {
		return stepBackOverResult;
	}

	@Override
	public Step<?> getCurrentBigStep() {
		return stepBackOutResult;
	}

	@Override
	public void jump(State<?,?> state) {
		if (state != null) {
			currentState = state;
			goTo(currentState);
			final List<? extends Step<?>> steps = currentState.getStartedSteps();
			final Step<?> step = steps.isEmpty() ? null : steps.get(0);
			updateCallStack(step);
		}
	}
	
	@Override
	public void jump(Value<?> value) {
		if (value != null) {
			List<? extends State<?,?>> states = value.getStates();
			if (!states.isEmpty()) {
				jump(states.get(0));
			}
		}
	}
	
	private List<? extends Step<?>> getStepsForStates(int startingState, int endingState) {
		final List<? extends State<?,?>> states = trace.getStates();
		Predicate<Step<?>> predicate = s -> {
			final State<?,?> stepStartingState = s.getStartingState();
			final State<?,?> stepEndingState = s.getEndingState();
			final int stepStartingIndex = states.indexOf(stepStartingState);
			final int stepEndingIndex = stepEndingState == null ? -1 : states.indexOf(stepEndingState);
			return (stepEndingIndex == -1 || stepEndingIndex >= startingState) && stepStartingIndex <= endingState;
		};
		return getSubSteps(trace.getRootStep()).stream().filter(predicate).collect(Collectors.toList());
	}
	
	@Override
	public void loadLastState() {
		final int idx = trace.getStates().size() - 1;
		final List<Step<?>> steps = new ArrayList<>(getStepsForStates(idx, idx));
		Step<?> lastStep = null;
		while (!steps.isEmpty()) {
			lastStep = steps.get(steps.size() - 1);
			steps.clear();
			if (lastStep instanceof BigStep<?,?>) {
				final List<? extends Step<?>> subSteps = ((BigStep<?,?>) lastStep).getSubSteps();
				steps.addAll(subSteps);
			}
		}
		jumpBeforeStep(lastStep);
	}

	@Override
	public boolean stepInto() {
		if (stepIntoResult != null) {
			jumpBeforeStep(stepIntoResult);
			return true;
		}
		return false;
	}

	@Override
	public boolean stepOver() {
		if (stepOverResult != null) {
			jumpBeforeStep(stepOverResult);
			return true;
		}
		return false;
	}

	@Override
	public boolean stepReturn() {
		if (stepReturnResult != null) {
			jumpBeforeStep(stepReturnResult);
			return true;
		}
		return false;
	}

	@Override
	public boolean canStepBackInto() {
		return stepBackIntoResult != null;
	}

	@Override
	public boolean canStepBackOver() {
		return stepBackOverResult != null;
	}

	@Override
	public boolean canStepBackOut() {
		return stepBackOutResult != null;
	}

	@Override
	public boolean stepBackInto() {
		if (stepBackIntoResult != null) {
			jumpBeforeStep(stepBackIntoResult);
			return true;
		}
		return false;
	}

	@Override
	public boolean stepBackOver() {
		if (stepBackOverResult != null) {
			jumpBeforeStep(stepBackOverResult);
			return true;
		}
		return false;
	}

	@Override
	public boolean stepBackOut() {
		if (stepBackOutResult != null) {
			jumpBeforeStep(stepBackOutResult);
			return true;
		}
		return false;
	}
	
	private Value<?> getCurrentValue(Dimension<?> dimension) {
		if (currentState == null) {
			return null;
		}
		return currentState.getValues().stream()
				.filter(v -> v.eContainer() == dimension)
				.findFirst().orElse(null);
	}

	@Override
	public boolean canStepValue(Dimension<?> dimension) {
		final Boolean cachedValue = canStepValueCache.get(dimension);
		if (cachedValue != null) {
			return cachedValue;
		} else {
			final Value<?> currentValue = getCurrentValue(dimension);
			final boolean result;
			if (currentValue != null) {
				final List<? extends Value<?>> values = dimension.getValues();
				final int idx = values.indexOf(currentValue);
				result = idx < values.size();
			} else {
				result = false;
			}
			canStepValueCache.put(dimension, result);
			return result;
		}
	}

	@Override
	public boolean canBackValue(Dimension<?> dimension) {
		final Boolean cachedValue = canBackValueCache.get(dimension);
		if (cachedValue != null) {
			return cachedValue;
		} else {
			final Value<?> currentValue = getCurrentValue(dimension);
			final boolean result;
			if (currentValue != null) {
				final List<? extends Value<?>> values = dimension.getValues();
				final int idx = values.indexOf(currentValue);
				result = idx > 0;
			} else {
				result = false;
			}
			canBackValueCache.put(dimension, result);
			return result;
		}
	}

	@Override
	public void stepValue(Dimension<?> dimension) {
		if (canStepValue(dimension)) {
			Value<?> currentValue = getCurrentValue(dimension);
			final List<? extends Value<?>> values = dimension.getValues();
			final int idx = values.indexOf(currentValue);
			jump(values.get(idx + 1));
		}
	}

	@Override
	public void backValue(Dimension<?> dimension) {
		if (canBackValue(dimension)) {
			Value<?> currentValue = getCurrentValue(dimension);
			final List<? extends Value<?>> values = dimension.getValues();
			final int idx = values.indexOf(currentValue);
			jump(values.get(idx - 1));
		}
	}

	@Override
	public boolean isInReplayMode() {
		return stepIntoResult != null;
	}

	@Override
	public List<Step<?>> getCallStack() {
		return callStack;
	}

	@Override
	public void updateCallStack(Step<?> step) {
		final List<Step<?>> newPath = new ArrayList<>();
		if (step != null) {
			newPath.add(step);
			EObject container = step.eContainer();
			while (container != null && container instanceof Step && container != trace.getRootStep()) {
				newPath.add(0, (Step<?>) container);
				container = container.eContainer();
			}
			currentState = step.getStartingState();
		}
		computeExplorerState(newPath);
		notifyListeners();
	}

	@Override
	public void notifyListeners() {
		for (Map.Entry<ITraceViewListener,Set<TraceViewCommand>> entry : listeners.entrySet()) {
			entry.getValue().forEach(c -> c.execute());
		}
	}

	@Override
	public void registerCommand(ITraceViewListener listener, TraceViewCommand command) {
		if (listener != null) {
			Set<TraceViewCommand> commands = listeners.get(listener);
			if (commands == null) {
				commands = new HashSet<>();
				listeners.put(listener, commands);
			}
			commands.add(command);
		}
	}

	@Override
	public void removeListener(ITraceViewListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

	@Override
	public void statesAdded(List<State<?, ?>> states) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepsStarted(List<Step<?>> steps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepsEnded(List<Step<?>> steps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valuesAdded(List<Value<?>> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dimensionsAdded(List<Dimension<?>> dimensions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State<?, ?> getCurrentState() {
		return currentState;
	}
}
