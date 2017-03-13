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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.FeatureFilter;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.internal.spec.MatchSpec;
import org.eclipse.emf.compare.postprocessor.BasicPostProcessorDescriptorImpl;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.postprocessor.IPostProcessor.Descriptor.Registry;
import org.eclipse.emf.compare.postprocessor.PostProcessorDescriptorRegistryImpl;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.inria.diverse.trace.commons.model.generictrace.GenericAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
import fr.inria.diverse.trace.gemoc.api.ITraceViewListener;

@SuppressWarnings("restriction")
public class GenericTraceExtractor
		implements ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> {

	private Trace<?, ?, ?> trace;
	private Map<Dimension<?>, Boolean> ignoredDimensions = new HashMap<>();
	private final IQualifiedNameProvider nameProvider = new DefaultDeclarativeQualifiedNameProvider();
	private Map<ITraceViewListener, Set<TraceViewCommand>> listeners = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param trace
	 *            The trace
	 */
	public GenericTraceExtractor(Trace<Step<?>, TracedObject<?>, State<?, ?>> trace) {
		this.trace = trace;
		configureDiffEngine();
	}

	@Override
	public void loadTrace(Trace<Step<?>, TracedObject<?>, State<?, ?>> trace) {
		this.trace = trace;
	}

	@Override
	public void notifyListeners() {
		for (Map.Entry<ITraceViewListener, Set<TraceViewCommand>> entry : listeners.entrySet()) {
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
	public void ignoreDimension(Dimension<?> dimension, boolean ignore) {
		ignoredDimensions.put(dimension, ignore);
	}

	@Override
	public boolean isDimensionIgnored(Dimension<?> dimension) {
		final Boolean ignored = ignoredDimensions.get(dimension);
		return ignored != null && ignored.booleanValue();
	}

	private boolean isDimensionIgnored(int index) {
		return isDimensionIgnored(getDimensions().get(index));
	}

	@Override
	public boolean isStateBreakable(State<?, ?> state) {
		// final boolean b = state.getStartedSteps().size() == 1;
		// if (b) {
		// Step<?> s = state.getStartedSteps().get(0);
		// return !(s instanceof ImplicitStep<?>);
		// }
		return true;
	}

	private final IPostProcessor customPostProcessor = new IPostProcessor() {

		private final Function<EObject, String> getIdFunction = e -> e.eClass().getName();

		@Override
		public void postMatch(Comparison comparison, Monitor monitor) {
			final List<Match> matches = new ArrayList<>(comparison.getMatches());
			final List<Match> treatedMatches = new ArrayList<>();
			matches.forEach(m1 -> {
				matches.forEach(m2 -> {
					if (m1 != m2 && !treatedMatches.contains(m2)) {
						final EObject left;
						final EObject right;
						if (m1.getLeft() != null && m1.getRight() == null && m2.getLeft() == null
								&& m2.getRight() != null) {
							left = m1.getLeft();
							right = m2.getRight();
						} else if (m2.getLeft() != null && m2.getRight() == null && m1.getLeft() == null
								&& m1.getRight() != null) {
							left = m2.getLeft();
							right = m1.getRight();
						} else {
							return;
						}
						final String leftId = getIdFunction.apply(left);
						final String rightId = getIdFunction.apply(right);
						if (leftId.equals(rightId)) {
							comparison.getMatches().remove(m1);
							comparison.getMatches().remove(m2);
							final Match match = new MatchSpec();
							match.setLeft(left);
							match.setRight(right);
							comparison.getMatches().add(match);
						}
					}
				});
				treatedMatches.add(m1);
			});
		}

		@Override
		public void postDiff(Comparison comparison, Monitor monitor) {
		}

		@Override
		public void postRequirements(Comparison comparison, Monitor monitor) {
		}

		@Override
		public void postEquivalences(Comparison comparison, Monitor monitor) {
		}

		@Override
		public void postConflicts(Comparison comparison, Monitor monitor) {
		}

		@Override
		public void postComparison(Comparison comparison, Monitor monitor) {
		}
	};

	private boolean compareInitialized = false;
	private IPostProcessor.Descriptor descriptor = null;
	private Registry<String> registry = null;
	private EMFCompare compare;
	private IDiffEngine diffEngine = null;

	private void configureDiffEngine() {
		IDiffProcessor diffProcessor = new DiffBuilder();
		diffEngine = new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
						final String name = reference.getName();
						return name.equals("parent") || name.equals("states") || name.equals("statesNoOpposite");
					}
				};
			}
		};
	}

	private boolean compareEObjects(EObject e1, EObject e2) {
		if (e1 == e2) {
			return true;
		}

		if (e1 == null || e2 == null) {
			return false;
		}

		if (!compareInitialized) {
			descriptor = new BasicPostProcessorDescriptorImpl(customPostProcessor, Pattern.compile(".*"), null);
			registry = new PostProcessorDescriptorRegistryImpl<String>();
			registry.put(customPostProcessor.getClass().getName(), descriptor);
			compare = EMFCompare.builder().setPostProcessorRegistry(registry).setDiffEngine(diffEngine).build();
			compareInitialized = true;
		}

		final IComparisonScope scope = new DefaultComparisonScope(e1, e2, null);
		final Comparison comparison = compare.compare(scope);
		return comparison.getDifferences().isEmpty();
	}

	@Override
	public boolean compareStates(State<?, ?> state1, State<?, ?> state2, boolean respectIgnored) {
		if (state1.getValues().size() != state2.getValues().size()) {
			return false;
		}

		final List<Value<?>> values1 = getStateValues(state1);
		final List<Value<?>> values2 = getStateValues(state2);

		boolean result = true;
		for (int i = 0; i < values1.size(); i++) {
			if (!respectIgnored || !isDimensionIgnored(i)) {
				final Value<?> value1 = values1.get(i);
				final Value<?> value2 = values2.get(i);
				if (value1 != value2) {
					result = result && compareEObjects(value1, value2);
					if (!result) {
						break;
					}
				}
			}
		}

		return result;
	}

	private final List<Dimension<?>> cachedDimensions = new ArrayList<>();
	private final Map<List<Integer>, List<State<?, ?>>> stateEquivalenceClasses = Collections
			.synchronizedMap(new HashMap<>());
	private final Map<List<Integer>, List<State<?, ?>>> cachedMaskedStateEquivalenceClasses = Collections
			.synchronizedMap(new HashMap<>());
	private final List<Value<?>> observedValues = new ArrayList<>();

	private List<Integer> computeStateComparisonList(List<? extends Value<?>> values) {
		final List<Integer> valueIndexes = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			final Value<?> value = values.get(i);
			int idx = -1;
			for (int j = 0; j < observedValues.size(); j++) {
				final Value<?> v1 = observedValues.get(j);
				final Value<?> v2 = value;
				if (compareEObjects(v1, v2)) {
					idx = j;
					break;
				}
			}
			if (idx != -1) {
				valueIndexes.add(idx);
			} else {
				valueIndexes.add(observedValues.size());
				observedValues.add(value);
			}
		}
		return valueIndexes;
	}

	private void updateEquivalenceClasses(State<?, ?> state) {
		final List<? extends Value<?>> values = getStateValues(state);
		final List<Integer> valueIndexes = computeStateComparisonList(values);
		List<State<?, ?>> equivalenceClass = stateEquivalenceClasses.get(valueIndexes);
		if (equivalenceClass == null) {
			equivalenceClass = new ArrayList<>();
			stateEquivalenceClasses.put(valueIndexes, equivalenceClass);
		}
		equivalenceClass.add(state);
		final List<Dimension<?>> dimensionsToMask = getIgnoredDimensions();
		// If the cached masked equivalence classes have not been flushed,
		// updated them.
		if (!(dimensionsToMask.isEmpty() || cachedMaskedStateEquivalenceClasses.isEmpty())) {
			final List<Dimension<?>> dimensions = getDimensions();
			final List<Integer> dimensionIndexesToMask = dimensionsToMask.stream().map(d -> dimensions.indexOf(d))
					.sorted().collect(Collectors.toList());
			final List<Integer> maskedIndexList = applyMask(valueIndexes, dimensionIndexesToMask);
			equivalenceClass = cachedMaskedStateEquivalenceClasses.get(maskedIndexList);
			if (equivalenceClass == null) {
				equivalenceClass = new ArrayList<>();
				cachedMaskedStateEquivalenceClasses.put(maskedIndexList, equivalenceClass);
			}
			equivalenceClass.add(state);
		}
	}

	private List<Integer> applyMask(List<Integer> source, List<Integer> mask) {
		final List<Integer> result = new ArrayList<>(source);
		int j = 0;
		for (Integer i : mask) {
			result.remove(i - j);
			j++;
		}
		return result;
	}

	private List<List<State<?, ?>>> getStateEquivalenceClasses() {
		final Set<Dimension<?>> dimensionsToMask = ignoredDimensions.keySet();
		if (dimensionsToMask.isEmpty()) {
			return new ArrayList<>(stateEquivalenceClasses.values());
		}
		if (cachedMaskedStateEquivalenceClasses.isEmpty()) {
			final List<Dimension<?>> dimensions = getDimensions();
			final List<Integer> dimensionIndexesToMask = dimensionsToMask.stream().map(d -> dimensions.indexOf(d))
					.sorted().collect(Collectors.toList());
			stateEquivalenceClasses.forEach((indexList, stateList) -> {
				final List<Integer> maskedIndexList = applyMask(indexList, dimensionIndexesToMask);
				List<State<?, ?>> equivalenceClass = cachedMaskedStateEquivalenceClasses.get(maskedIndexList);
				if (equivalenceClass == null) {
					equivalenceClass = new ArrayList<>();
					cachedMaskedStateEquivalenceClasses.put(maskedIndexList, equivalenceClass);
				}
				equivalenceClass.addAll(stateList);
			});
		}
		return new ArrayList<>(cachedMaskedStateEquivalenceClasses.values());
	}

	@Override
	public List<List<State<?, ?>>> computeStateEquivalenceClasses(List<? extends State<?, ?>> states) {
		return getStateEquivalenceClasses().stream()
				.map(l -> l.stream().filter(s -> states.contains(s)).collect(Collectors.toList()))
				.collect(Collectors.toList());
	}

	@Override
	public List<List<State<?, ?>>> computeStateEquivalenceClasses() {
		return getStateEquivalenceClasses().stream().map(l -> new ArrayList<>(l)).collect(Collectors.toList());
	}

	@Override
	public LaunchConfiguration getLaunchConfiguration() {
		return trace.getLaunchconfiguration();
	}

	@Override
	public int getNumberOfDimensions() {
		return trace.getTracedObjects().stream().map(o -> o.getDimensions().size()).reduce(0, (i1, i2) -> i1 + i2);
	}

	private List<Value<?>> getStateValues(State<?, ?> state) {
		final Map<Dimension<?>, Value<?>> dimensionToValue = new HashMap<>();
		state.getValues().forEach(v -> dimensionToValue.put((Dimension<?>) v.eContainer(), v));
		return getDimensions().stream().map(d -> dimensionToValue.get(d)).collect(Collectors.toList());
	}

	@Override
	public String getStateDescription(State<?, ?> state) {
		String result = "";
		final List<Value<?>> values = getStateValues(state);
		for (int i = 0; i < values.size(); i++) {
			if (!isDimensionIgnored(i)) {
				String description = getValueDescription(values.get(i));
				result += (description == null ? "" : (result.length() == 0 ? "" : "\n") + description);
			}
		}

		return result;
	}

	@Override
	public int getStatesTraceLength() {
		return trace.getStates().size();
	}

	@Override
	public State<?, ?> getState(int stateIndex) {
		return trace.getStates().get(stateIndex);
	}

	@Override
	public List<State<?, ?>> getStates(int firstStateIndex, int lastStateIndex) {
		final List<State<?, ?>> result = new ArrayList<>();
		final int effectiveFrom = Math.max(0, firstStateIndex);
		final int effectiveTo = Math.min(trace.getStates().size(), lastStateIndex + 1);
		trace.getStates().subList(effectiveFrom, effectiveTo).forEach(s -> result.add(s));
		return result;
	}

	@Override
	public int getStateIndex(State<?, ?> state) {
		return trace.getStates().indexOf(state);
	}

	@Override
	public int getValueFirstStateIndex(Value<?> value) {
		return trace.getStates().indexOf(value.getStates().get(0));
	}

	@Override
	public int getValueLastStateIndex(Value<?> value) {
		List<? extends State<?, ?>> states = value.getStates();
		return trace.getStates().indexOf(states.get(states.size() - 1));
	}

	private String getValueName(Value<?> value) {
		final String eClassName = value.eClass().getName();
		return eClassName.substring(eClassName.indexOf('_') + 1, eClassName.indexOf("_Value"));
	}

	private String getObjectDescription(Object object) {
		if (object == null) {
			return "null";
		}
		if (object instanceof EObject) {
			final Object originalObject = getOriginalObject((EObject) object);
			if (originalObject != null) {
				if (originalObject instanceof EObject) {
					final QualifiedName qname = nameProvider.getFullyQualifiedName((EObject) originalObject);
					if (qname != null) {
						return qname.getLastSegment();
					}
				}
				return originalObject.toString();
			}
			QualifiedName qname = nameProvider.getFullyQualifiedName((EObject) object);
			if (qname != null) {
				return qname.getLastSegment();
			}
		}
		if (object instanceof Collection) {
			@SuppressWarnings("unchecked")
			final Collection<Object> o_cast = (Collection<Object>) object;
			if (!o_cast.isEmpty()) {
				List<String> strings = o_cast.stream().map(o -> getObjectDescription(o)).collect(Collectors.toList());
				return strings.toString();
			}
		}
		return object.toString();
	}

	public List<Value<?>> getValuesForStates(Dimension<?> dimension, int from, int to) {
		final List<Value<?>> values = dimension.getValues().stream().filter(v -> {
			final List<? extends State<?, ?>> states = v.getStates();
			final State<?, ?> firstState = states.get(0);
			final State<?, ?> lastState = states.get(states.size() - 1);
			return getStateIndex(firstState) < to && getStateIndex(lastState) > from;
		}).collect(Collectors.toList());
		return values;
	}

	@Override
	public String getValueDescription(Value<?> value) {
		if (value == null) {
			return "";
		}
		String description = getDimensionLabel((Dimension<?>) value.eContainer()) + " : ";
		final String attributeName;
		if (value instanceof GenericValue) {
			if (value instanceof GenericAttributeValue) {
				attributeName = "attributeValue";
			} else if (value instanceof SingleReferenceValue) {
				attributeName = "referenceValue";
			} else {
				attributeName = "referenceValues";
			}
		} else {
			attributeName = getValueName(value);
		}
		if (attributeName.length() > 0) {
			final Optional<EStructuralFeature> attribute = value.eClass().getEAllStructuralFeatures().stream()
					.filter(r -> r.getName().equals(attributeName)).findFirst();
			if (attribute.isPresent()) {
				final Object o = value.eGet(attribute.get());
				return description + getObjectDescription(o);
			}
		}
		return description + value;
	}

	private Object getOriginalObject(EObject eObject) {
		return eObject.eClass().getEAllReferences().stream().filter(r -> r.getName().startsWith("originalObject"))
				.findFirst().map(r -> eObject.eGet(r)).orElse(null);
	}

	private Map<Dimension<?>, String> dimensionToLabel = new HashMap<>();

	@Override
	public String getDimensionLabel(Dimension<?> dimension) {
		return dimensionToLabel.computeIfAbsent(dimension, d -> {
			EObject container = dimension.eContainer();
			final String modelElement;
			if (container != null) {
				Object originalObject;
				if (container instanceof GenericTracedObject) {
					originalObject = ((GenericTracedObject) container).getOriginalObject();
				} else {
					originalObject = getOriginalObject(container);
				}
				if (originalObject != null) {
					final QualifiedName fqn = nameProvider.getFullyQualifiedName((EObject) originalObject);
					modelElement = fqn == null ? "" : fqn.getLastSegment() + ".";
				} else {
					modelElement = "";
				}
			} else {
				modelElement = "";
			}
			final String result;
			if (dimension instanceof GenericDimension) {
				result = ((GenericDimension) dimension).getDynamicProperty().getName();
			} else {
				final String dimensionName = dimension.eClass().getName();
				final String tmp = dimensionName.substring(0, dimensionName.indexOf("_Dimension"));
				result = tmp.substring(tmp.lastIndexOf("_") + 1);
			}
			return modelElement + result;
		});
	}

	@Override
	public int getDimensionLength(Dimension<?> dimension) {
		return dimension.getValues().size();
	}

	private void updateEquivalenceClasses(List<State<?, ?>> states) {
		states.stream().distinct().forEach(s -> updateEquivalenceClasses(s));
	}

	@Override
	public void statesAdded(List<State<?, ?>> states) {
//		updateEquivalenceClasses(states);
		notifyListeners();
	}

	@Override
	public void stepsStarted(List<Step<?>> steps) {
	}

	@Override
	public void stepsEnded(List<Step<?>> steps) {
	}

	@Override
	public void valuesAdded(List<Value<?>> values) {
	}

	@Override
	public void dimensionsAdded(List<Dimension<?>> addedDimensions) {
		cachedMaskedStateEquivalenceClasses.clear();
		cachedDimensions.clear();
		final List<Dimension<?>> dimensions = getDimensions();
		final List<Integer> insertedTracesIndexes = new ArrayList<>();
		for (Dimension<?> dimension : addedDimensions) {
			final int i = dimensions.indexOf(dimension);
			insertedTracesIndexes.add(i);
		}
		Collections.sort(insertedTracesIndexes);
		final List<List<Integer>> keys = new ArrayList<>(stateEquivalenceClasses.keySet());
		for (List<Integer> key : keys) {
			List<State<?, ?>> states = stateEquivalenceClasses.remove(key);
			for (Integer i : insertedTracesIndexes) {
				key.add(i, -1);
			}
			stateEquivalenceClasses.put(key, states);
		}
	}

	@Override
	public List<Step<?>> getSubSteps(Step<?> step) {
		if (step instanceof BigStep<?, ?>) {
			return new ArrayList<>(((BigStep<?, ?>) step).getSubSteps());
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Step<?>> getSteps(int firstStateIndex, int lastStateIndex) {
		final Step<?> rootStep = trace.getRootStep();
		if (rootStep instanceof BigStep<?, ?>) {
			final List<Step<?>> steps = new ArrayList<>(((BigStep<?, ?>) rootStep).getSubSteps());
			steps.removeIf(s -> (s.getEndingState() != null && getStateIndex(s.getEndingState()) < firstStateIndex)
					|| getStateIndex(s.getStartingState()) > lastStateIndex);
			return steps;
		}
		return Collections.singletonList(rootStep);
	}

	@Override
	public List<Dimension<?>> getDimensions() {
		if (cachedDimensions.isEmpty()) {
			trace.getTracedObjects().forEach(o -> cachedDimensions.addAll(o.getDimensions()));
		}
		return cachedDimensions;
	}

	private List<Dimension<?>> getIgnoredDimensions() {
		return getDimensions().stream().filter(d -> isDimensionIgnored(d)).collect(Collectors.toList());
	}
}
