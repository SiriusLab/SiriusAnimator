package org.gemoc.sequential_addons.stategraph.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer;
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
import fr.inria.diverse.trace.gemoc.api.ITraceListener;
import fr.inria.diverse.trace.gemoc.api.ITraceViewListener;

public class StateGraph extends DirectedGraph<StateVertex>implements ITraceViewListener, ITraceListener {

	private ITraceExplorer<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer;
	private ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor;

	private final Map<State<?,?>, StateVertex> stateToNode = new HashMap<>();

	private final Map<State<?,?>, State<?,?>> stateToEquivalentState = new HashMap<>();

	private final List<State<?,?>> equivalentStates = new ArrayList<>();

	private BiConsumer<Boolean, StateVertex> renderCommand = null;

	public void setTraceExtractor(ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor) {
		if (this.traceExtractor != null) {
			this.traceExtractor.removeListener(this);
		}
		this.traceExtractor = traceExtractor;
		if (this.traceExtractor != null) {
			this.traceExtractor.registerCommand(this, () -> updateGraph());
		}
	}

	public void setTraceExplorer(ITraceExplorer<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer) {
		if (this.traceExplorer != null) {
			this.traceExplorer.removeListener(this);
		}
		this.traceExplorer = traceExplorer;
		if (this.traceExplorer != null) {
			this.traceExplorer.registerCommand(this, () -> updateCurrentState());
		}
	}

	public void setUpdateCommand(BiConsumer<Boolean, StateVertex> command) {
		renderCommand = command;
	}

	private final List<Boolean> ignoredValueTraces = new ArrayList<>();

	private void updateGraph() {
		// Finding out whether the list of ignored values has changed
		final List<Boolean> newIgnoredValueTraces = new ArrayList<>();
		for (Dimension<?> dimension : traceExtractor.getDimensions()) {
			newIgnoredValueTraces.add(traceExtractor.isDimensionIgnored(dimension));
		}
		// If it did we have to recompute the graph
		if (!newIgnoredValueTraces.equals(ignoredValueTraces)) {
			ignoredValueTraces.clear();
			ignoredValueTraces.addAll(newIgnoredValueTraces);
			computeStateSpace();
			render(false, null);
		}
	}

	private void updateCurrentState() {
		StateVertex currentNode = null;
		if (traceExplorer != null) {
			final State<?,?> currentState = traceExplorer.getCurrentState();
			if (currentState != null) {
				final EObject equivalentState = stateToEquivalentState.get(currentState);
				currentNode = stateToNode.get(equivalentState);
			}
		}
		render(false, currentNode);
	}

	@Override
	public void update() {
	}

	private void updateEquivalentStates(Collection<List<State<?,?>>> equivalenceClasses) {
		equivalenceClasses.forEach(l -> {
			State<?,?> equivalentState = null;
			for (State<?,?> state : l) {
				if (equivalentStates.contains(state)) {
					equivalentState = state;
					break;
				}
			}
			if (equivalentState == null) {
				if (l.isEmpty()) {
					return;
				}
				// The current equivalence class does not yet have
				// a dedicated vertex, we thus add its first state
				// to the list of equivalent states.
				equivalentState = l.remove(0);
				equivalentStates.add(equivalentState);
			} else {
				// The current equivalence class does have a
				// dedicated vertex.
				l.remove(equivalentState);
				l.forEach(s -> {
					equivalentStates.remove(s);
					// Cleaning up old vertice in case the equivalence class of
					// the state changed.
					removeVertex(stateToNode.remove(s));
				});
			}
			stateToEquivalentState.put(equivalentState, equivalentState);
			for (State<?,?> otherState : l) {
				stateToEquivalentState.put(otherState, equivalentState);
			}
		});
	}

	private void computeStateSpace() {
		stateToEquivalentState.clear();
		List<List<State<?,?>>> equivalenceClasses = traceExtractor.computeStateEquivalenceClasses();
		updateEquivalentStates(equivalenceClasses);

		final int n = traceExtractor.getStatesTraceLength();
		final List<Step<?>> steps = traceExtractor.getSteps(0, n);
		final List<Edge<StateVertex>> addedEdges = new ArrayList<>();

		while (!steps.isEmpty()) {
			final Step<?> step = steps.remove(0);
			final List<Step<?>> subSteps = traceExtractor.getSubSteps(step);
			if (step.getEndingState() != null && subSteps.isEmpty()) {
				final State<?,?> startingState = step.getStartingState();
				final State<?,?> endingState = step.getEndingState();
				final Edge<StateVertex> addedEdge = addEdge(startingState, endingState, step);
				if (addedEdge != null) {
					addedEdges.add(addedEdge);
				}
			}
			steps.addAll(0, subSteps);
		}

		final List<Edge<StateVertex>> edgesToRemove = new ArrayList<>(getEdges());
		edgesToRemove.removeAll(addedEdges);
		edgesToRemove.forEach(e -> removeEdge(e));
	}

	public Edge<StateVertex> addEdge(State<?,?> startState, State<?,?> endState, Step<?> step) {
		final State<?,?> equivalentStartState = stateToEquivalentState.get(startState);
		final State<?,?> equivalentEndState = stateToEquivalentState.get(endState);
		if (equivalentEndState == equivalentStartState || equivalentStartState == null || equivalentEndState == null) {
			return null;
		}
		StateVertex startNode = null;
		StateVertex endNode = null;
		for (Entry<State<?,?>, StateVertex> entry : stateToNode.entrySet()) {
			final EObject entryState = entry.getKey();
			if (startNode == null) {
				if (equivalentStartState == entryState) {
					startNode = entry.getValue();
					continue;
				}
			}

			if (endNode == null) {
				if (equivalentEndState == entryState) {
					endNode = entry.getValue();
				}
			}

			if (startNode != null && endNode != null) {
				break;
			}
		}

		if (startNode == null) {
			startNode = addVertex(new StateVertex(traceExtractor.getStateDescription(startState), traceExtractor.getStateIndex(startState)));
			stateToNode.put(equivalentStartState, startNode);
		} else {
			final int startIndex = traceExtractor.getStateIndex(equivalentStartState);
			final String description = traceExtractor.getStateDescription(equivalentStartState);
			startNode.setTooltip(description);
			startNode.setIndex(startIndex);
		}
		if (endNode == null) {
			endNode = addVertex(new StateVertex(traceExtractor.getStateDescription(endState), traceExtractor.getStateIndex(endState)));
			stateToNode.put(equivalentEndState, endNode);
		} else {
			final int endIndex = traceExtractor.getStateIndex(equivalentEndState);
			final String description = traceExtractor.getStateDescription(equivalentEndState);
			endNode.setTooltip(description);
			endNode.setIndex(endIndex);
		}

		Edge<StateVertex> result = getEdge(startNode, endNode);

		if (result == null) {
			result = addEdge(startNode, endNode);
		}
		return result;
	}

	public void clear() {
		stateToNode.clear();
		if (renderCommand != null) {
			renderCommand.accept(true, null);
		}
		update();
	}

	private void render(boolean clear, StateVertex currentVertex) {
		if (renderCommand != null) {
			renderCommand.accept(clear, currentVertex);
		}
	}

	@Override
	public void statesAdded(List<State<?,?>> states) {
		List<List<State<?,?>>> equivalenceClasses = traceExtractor.computeStateEquivalenceClasses();
		updateEquivalentStates(equivalenceClasses);
		render(false, null);
	}

	@Override
	public void stepsEnded(List<Step<?>> steps) {
		for (Step<?> step : steps) {
			addEdge(step.getStartingState(), step.getEndingState(), step);
		}
		render(false, null);
	}

	@Override
	public void stepsStarted(List<Step<?>> steps) {
		// Nothing to do here.
	};

	@Override
	public void valuesAdded(List<Value<?>> values) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dimensionsAdded(List<Dimension<?>> dimensions) {
		// TODO Auto-generated method stub
	}
}
