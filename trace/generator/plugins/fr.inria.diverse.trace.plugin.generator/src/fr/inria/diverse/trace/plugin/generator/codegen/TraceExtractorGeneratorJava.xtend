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
package fr.inria.diverse.trace.plugin.generator.codegen


import ecorext.Rule
import fr.inria.diverse.trace.commons.CodeGenUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.metamodel.generator.TraceMMGenerationTraceability
import fr.inria.diverse.trace.metamodel.generator.TraceMMStrings
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

class TraceExtractorGeneratorJava {

	// Inputs
	private val String className
	private val String packageQN
	private val EPackage traceMM
	private val EPackage abstractSyntax
	private val TraceMMGenerationTraceability traceability
	private val Set<GenPackage> refGenPackages
	
	// Shortcuts
	private val EClass stateClass
	private val EClass valueClass
	private val EClass specificStepClass
	
	private val String stateFQN
	private val String valueFQN
	private val String specificStepFQN
	private val String specificTraceFQN
	private val String statesPackageFQN

	public def String getClassName() {
		return className
	}

	new(String languageName, String packageQN, EPackage traceMM, TraceMMGenerationTraceability traceability,
		Set<GenPackage> refGenPackages, boolean gemoc, EPackage abstractSyntax, boolean partialTraceManagement) {
		this.traceMM = traceMM
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "Extractor"
		this.packageQN = packageQN
		this.traceability = traceability
		this.refGenPackages = refGenPackages
		this.abstractSyntax = abstractSyntax
		stateClass = traceability.traceMMExplorer.getSpecificStateClass
		valueClass = traceability.traceMMExplorer.getSpecificValueClass
		statesPackageFQN = EcoreCraftingUtil.getBaseFQN(traceability.traceMMExplorer.statesPackage) + "." + traceability.traceMMExplorer.statesPackage.name.toFirstUpper + "Package"
		specificStepClass = traceability.traceMMExplorer.specificStepClass
		stateFQN = getJavaFQN(stateClass)
		valueFQN = getJavaFQN(valueClass)
		specificStepFQN = getJavaFQN(specificStepClass)
		specificTraceFQN = getJavaFQN(traceability.traceMMExplorer.specificTraceClass)
	}
	
	private def String getFQN(EStructuralFeature eFeature) {
		return EcoreCraftingUtil.getBaseFQN(eFeature.EContainingClass) + "." + eFeature.name
	}
	
	private def String getJavaFQN(EClassifier c) {
		return getJavaFQN(c,false)
	}
	
	private def String getJavaFQN(EClassifier c, boolean enforcePrimitiveJavaClass) {
		return EcoreCraftingUtil.getJavaFQN(c,refGenPackages,enforcePrimitiveJavaClass)
	}
	
	public def String generateCode() {
		val String code = generateTraceExtractorClass()
		try {
			return CodeGenUtil.formatJavaCode(code)
		} catch (Throwable t) {
			return code
		}
	}
	
	public static def String getBaseFQN(Rule r) {
		val EOperation o = r.operation
		val EClass c = r.containingClass
		return EcoreCraftingUtil.getBaseFQN(c) + "." + o.name
	}

	private def Set<EStructuralFeature> getAllMutablePropertiesOf(EClass exeClass) {
		val Set<EStructuralFeature> res = new HashSet<EStructuralFeature>
		res.addAll(traceability.getMutablePropertiesOf(exeClass))
		res.addAll(exeClass.EAllSuperTypes.map[s|traceability.getMutablePropertiesOf(s)].flatten.toSet);
		return res
	}

	private def String generateImports() {
		return
				'''
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
					import java.util.function.Predicate;
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
					import org.eclipse.emf.ecore.util.EcoreEList;
					import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
					import org.eclipse.xtext.naming.QualifiedName;
					
					import fr.inria.diverse.trace.commons.model.trace.Dimension;
					import fr.inria.diverse.trace.commons.model.trace.LaunchConfiguration;
					import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
					import fr.inria.diverse.trace.commons.model.trace.State;
					import fr.inria.diverse.trace.commons.model.trace.Step;
					import fr.inria.diverse.trace.commons.model.trace.Trace;
					import fr.inria.diverse.trace.commons.model.trace.Value;
					import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
					import fr.inria.diverse.trace.gemoc.api.ITraceViewListener;
				'''
	}
	
	private def String generateFields() {
		return
				'''
					private «getJavaFQN(traceability.traceMMExplorer.specificTraceClass)» traceRoot;
					
					final private List<List<? extends «valueFQN»>> valueTraces = Collections.synchronizedList(new ArrayList<>());
					
					private List<«stateFQN»> statesTrace;
					
					final private Map<«specificStepFQN», Integer> stepToStartingIndex = new HashMap<>();
					final private Map<«specificStepFQN», Integer> stepToEndingIndex = new HashMap<>();
					
					final private DefaultDeclarativeQualifiedNameProvider nameProvider = new DefaultDeclarativeQualifiedNameProvider();
					
					final private Map<Integer, Boolean> ignoredValueTraces = new HashMap<>();
					
					private final Map<List<Integer>, List<EObject>> stateEquivalenceClasses = Collections.synchronizedMap(new HashMap<>());
					private final Map<List<Integer>, List<EObject>> cachedMaskedStateEquivalenceClasses = Collections.synchronizedMap(new HashMap<>());
					
					private final List<«valueFQN»> observedValues = new ArrayList<>();
				'''
	}
	
	private def String generateConstructors() {
		return
				'''
					public «className»() {
						observedValues.add(null);
						configureDiffEngine();
					}
				'''
	}
	
	private def String generateStateUtilities() {
		return
				'''
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
					
					private Registry registry = null;
					
					private EMFCompare compare;
					
					private boolean compareEObjects(EObject e1, EObject e2) {
						if (e1 == e2) {
							return true;
						}
						
						if (e1 == null || e2 == null) {
							return false;
						}
						
						if (!compareInitialized) {
							descriptor = new BasicPostProcessorDescriptorImpl(customPostProcessor, Pattern.compile(".*"), null);
							registry = new PostProcessorDescriptorRegistryImpl();
							registry.put(customPostProcessor.getClass().getName(), descriptor);
							compare = EMFCompare.builder().setPostProcessorRegistry(registry).setDiffEngine(diffEngine).build();
							compareInitialized = true;
						}
					
						final IComparisonScope scope = new DefaultComparisonScope(e1, e2, null);
						final Comparison comparison = compare.compare(scope);
						return comparison.getDifferences().isEmpty();
					}
					
					private List<Integer> computeStateComparisonList(List<«valueFQN»> values) {
							final List<Integer> valueIndexes = new ArrayList<>();
							for (int i = 0; i < values.size(); i++) {
								final «valueFQN» value = values.get(i);
								int idx = -1;
								for (int j = 0; j < observedValues.size(); j++) {
									final EObject v1 = observedValues.get(j);
									final EObject v2 = value;
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
					
						private void updateEquivalenceClasses(«stateFQN» state) {
							final List<«valueFQN»> values = getAllStateValues(state, true);
							final List<Integer> valueIndexes = computeStateComparisonList(values);
							List<EObject> equivalenceClass = stateEquivalenceClasses.get(valueIndexes);
							if (equivalenceClass == null) {
								equivalenceClass = new ArrayList<>();
								stateEquivalenceClasses.put(valueIndexes, equivalenceClass);
							}
							equivalenceClass.add(state);
							// If the cached masked equivalence classes have not been flushed, updated them.
							final List<Integer> dimensionsToMask = computeDimensionMask();
							if (!(dimensionsToMask.isEmpty() || cachedMaskedStateEquivalenceClasses.isEmpty())) {
								final List<Integer> maskedIndexList = applyMask(valueIndexes, dimensionsToMask);
								equivalenceClass = cachedMaskedStateEquivalenceClasses.get(maskedIndexList);
								if (equivalenceClass == null) {
									equivalenceClass = new ArrayList<>();
									cachedMaskedStateEquivalenceClasses.put(maskedIndexList, equivalenceClass);
								}
								equivalenceClass.add(state);
							}
						}
					
						private void updateEquivalenceClasses(List<«stateFQN»> states) {
							states.stream().distinct().forEach(s -> updateEquivalenceClasses(s));
						}
						
						/*
						 * Return the list of indexes of value traces that are ignored.
						 */
						private List<Integer> computeDimensionMask() {
							final List<Integer> result = new ArrayList<>();
							for (int i = 0; i < valueTraces.size(); i++) {
								if (isValueTraceIgnored(i)) {
									result.add(i);
								}
							}
							return result;
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
					
						private List<List<EObject>> getStateEquivalenceClasses() {
							final List<Integer> dimensionsToMask = computeDimensionMask();
							if (dimensionsToMask.isEmpty()) {
								return new ArrayList<>(stateEquivalenceClasses.values());
							}
							if (cachedMaskedStateEquivalenceClasses.isEmpty()) {
								stateEquivalenceClasses.forEach((indexList, stateList) -> {
									final List<Integer> maskedIndexList = applyMask(indexList, dimensionsToMask);
									List<EObject> equivalenceClass = cachedMaskedStateEquivalenceClasses.get(maskedIndexList);
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
						public List<List<EObject>> computeStateEquivalenceClasses() {
							return getStateEquivalenceClasses().stream()
									.map(l -> new ArrayList<>(l))
									.collect(Collectors.toList());
						}
					
						@Override
						public List<List<EObject>> computeStateEquivalenceClasses(List<? extends EObject> states) {
							return getStateEquivalenceClasses().stream()
									.map(l -> l.stream()
											.filter(s -> states.contains(s))
											.collect(Collectors.toList()))
									.collect(Collectors.toList());
						}
					
					@Override
					public boolean compareStates(EObject eObject1, EObject eObject2, boolean respectIgnored) {
						final «stateFQN» state1;
						final «stateFQN» state2;
						
						if (eObject1 instanceof «stateFQN») {
							state1 = («stateFQN») eObject1;
						} else {
							return false;
						}
						
						if (eObject2 instanceof «stateFQN») {
							state2 = («stateFQN») eObject2;
						} else {
							return false;
						}
						
						final List<«valueFQN»> values1 = getAllStateValues(state1);
						final List<«valueFQN»> values2 = getAllStateValues(state2);
						
						if (values1.size() != values2.size()) {
							return false;
						}
						
						boolean result = true;
						for (int i = 0; i < values1.size(); i++) {
							if (!respectIgnored || !isValueTraceIgnored(i)) {
								final «valueFQN» value1 = values1.get(i);
								final «valueFQN» value2 = values2.get(i);
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
					
					public boolean compareSteps(EObject eObject1, EObject eObject2) {
						final «specificStepFQN» step1;
						final «specificStepFQN» step2;
						
						if (eObject1 instanceof «specificStepFQN») {
							step1 = («specificStepFQN») eObject1;
						} else {
							return false;
						}
						
						if (eObject2 instanceof «specificStepFQN») {
							step2 = («specificStepFQN») eObject2;
						} else {
							return false;
						}
						
						if (step1.eClass() == step2.eClass()) {
							return true;
						}
						
						return false;
					}
					
					public boolean compareStatesWithSteps(EObject eObject1, EObject eObject2, boolean respectIgnored) {
						final «stateFQN» state1;
						final «stateFQN» state2;
					
						if (eObject1 instanceof «stateFQN») {
							state1 = («stateFQN») eObject1;
						} else {
							return false;
						}
					
						if (eObject2 instanceof «stateFQN») {
							state2 = («stateFQN») eObject2;
						} else {
							return false;
						}
						
						if (compareStates(state1, state2, respectIgnored)) {
							final List<«specificStepFQN»> endedSteps1 = state1.getEndedSteps();
							final List<«specificStepFQN»> startedSteps1 = state1.getStartedSteps();
							final List<«specificStepFQN»> endedSteps2 = state2.getEndedSteps();
							final List<«specificStepFQN»> startedSteps2 = state2.getStartedSteps();
							if (endedSteps1.size() == endedSteps2.size() &&
									startedSteps1.size() == startedSteps2.size()) {
								boolean result = true;
								for (int i = 0; i < endedSteps1.size(); i++) {
									final «specificStepFQN» endedStep1 = endedSteps1.get(i);
									final «specificStepFQN» endedStep2 = endedSteps2.get(i);
									if (!compareSteps(endedStep1, endedStep2)) {
										result = false;
										break;
									}
								}
								if (!result) {
									return false;
								}
								for (int i = 0; i < startedSteps1.size(); i++) {
									final «specificStepFQN» startedStep1 = startedSteps1.get(i);
									final «specificStepFQN» startedStep2 = startedSteps2.get(i);
									if (!compareSteps(startedStep1, startedStep2)) {
										result = false;
										break;
									}
								}
								return result;
							} else {
								return false;
							}
						} else {
							return false;
						}
					}
					
					public boolean compareTraces(EObject eObject1, EObject eObject2, boolean respectIgnored) {
						final «specificTraceFQN» trace1;
						final «specificTraceFQN» trace2;
						
						if (eObject1 instanceof «specificTraceFQN») {
							trace1 = («specificTraceFQN») eObject1;
						} else {
							return false;
						}
						
						if (eObject2 instanceof «specificTraceFQN») {
							trace2 = («specificTraceFQN») eObject2;
						} else {
							return false;
						}
						
						final List<«stateFQN»> states1 = trace1.getStates();
						final List<«stateFQN»> states2 = trace2.getStates();
						
						if (states1.size() != states2.size()) {
							return false;
						}
						
						boolean result = true;
						
						for (int i = 0; i < states1.size(); i++) {
							final «stateFQN» state1 = states1.get(i);
							final «stateFQN» state2 = states2.get(i);
							if (!compareStatesWithSteps(state1, state2, respectIgnored)) {
								result = false;
								break;
							}
						}
						
						return result;
					}
					
					private List<«valueFQN»> getAllStateValues(«stateFQN» state) {
						return getAllStateValues(state, false);
					}
					
					private List<«valueFQN»> getAllStateValues(«stateFQN» state,
							boolean includeHiddenValues) {
						final List<«valueFQN»> result = new ArrayList<>();
						for (int i = 0; i < valueTraces.size(); i++) {
							if (includeHiddenValues || !isValueTraceIgnored(i)) {
								final List<? extends «valueFQN»> trace = valueTraces.get(i);
								boolean notFound = true;
								for («valueFQN» value : trace) {
									if (value.getStatesView().contains(state)) {
										result.add(value);
										notFound = false;
										break;
									}
								}
								if (notFound) {
									result.add(null);
								}
							}
						}
						return result;
					}
				'''
	}
	
	private def String generateValueUtilities() {
		return
				'''
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
					
					private List<List<? extends «valueFQN»>> getAllValueTraces() {
						final List<List<? extends «valueFQN»>> result = new ArrayList<>();
						«FOR mutClass : traceability.allMutableClasses.filter[c|!c.isAbstract].sortBy[name]»
						«val traced = traceability.getTracedClass(mutClass)»
						«val mutProps = getAllMutablePropertiesOf(mutClass).sortBy[FQN]»
						«IF !mutProps.empty»
						for («getJavaFQN(traced)» tracedObject : traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_createTraceClassToTracedClass(traced))») {
						«FOR p : mutProps»
						«val EReference pdimension = traceability.getDimensionRef(p)»
							result.add(tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues());
						«ENDFOR»
						}
						«ENDIF»
						«ENDFOR»
						return result;
					}

					private «valueFQN» getValueAt(int traceIndex, int stateIndex) {
						«valueFQN» result = null;
						if (traceIndex >= 0 && traceIndex < valueTraces.size()) {
							final List<? extends «valueFQN»> valueTrace = valueTraces.get(traceIndex);
							final «stateFQN» state = statesTrace.get(stateIndex);
							for («valueFQN» value : valueTrace) {
								if (value.getStatesView().contains(state)) {
									result = value;
									break;
								}
							}
						}
						return result;
					}
				'''
	}
	
	private def generateStepUtilities() {
		return
				'''
					private int getStartingIndex(«specificStepFQN» step) {
						return stepToStartingIndex.computeIfAbsent(step, s -> {
							return statesTrace.indexOf(s.getStartingState());
						});
					}
					
					private int getEndingIndex(«specificStepFQN» step) {
						if (step.getEndingState() != null) {
							return stepToEndingIndex.computeIfAbsent(step, s -> {
								return statesTrace.indexOf(s.getEndingState());
							});
						}
						return -1;
					}
				'''
	}

	private def String generateLoadTraceUtilities() {
		return
				'''
					public void loadTrace(«getJavaFQN(traceability.traceMMExplorer.specificTraceClass)» root) {
						traceRoot = root;
						statesTrace = traceRoot.getStates();
						valueTraces.addAll(getAllValueTraces());
						updateEquivalenceClasses(statesTrace);
					}
				'''
	}
	
	private def String generateAPI() {
		return
				'''
					private boolean isStateBreakable(«stateFQN» state) {
						«IF !traceability.bigStepClasses.empty»
						final boolean b = state.getStartedSteps().size() == 1;
						if (b) {
							«specificStepFQN» s = state.getStartedSteps().get(0);
							return
								!(«FOR bigStepClass : traceability.bigStepClasses.sortBy[name] SEPARATOR "||"»
								s instanceof «getJavaFQN(bigStepClass)»_ImplicitStep
								«ENDFOR»);
						}
						«ENDIF»
						return true;
					}
					
					@Override
					public int getNumberOfTraces() {
						return valueTraces.size();
					}
					
					@Override
					public int getStatesTraceLength() {
						return statesTrace.size();
					}
					
					@Override
					public State getState(int stateIndex) {
						return statesTrace.get(stateIndex);
					}
					
					@Override
					public State getStateIndex(State state) {
						return statesTrace.indexOf(state);
					}
					
					@Override
					public int getValueFirstStateIndex(Value value) {
						return getStateIndex(value.getStatesView().get(0));
					}
					
					@Override
					public int getValueLastStateIndex(Value value) {
						final List<State> states = value.getStatesView()
						return getStateIndex(states.get(states.size() - 1));
					}
					
					@Override
					public int getValuesTraceLength(int traceIndex) {
						if (traceIndex > -1 && traceIndex < valueTraces.size()) {
							List<? extends «valueFQN»> trace = valueTraces.get(traceIndex);
							return trace.size();
						}
						return -1;
					}
					
					private String getValueName(EObject value) {
						final EObject container = value.eContainer();
						final List<String> attributes = container.eClass().getEAllReferences().stream()
								.filter(r -> r.getName().endsWith("Sequence"))
								.map(r -> r.getName().substring(0, r.getName().length() - 8)).collect(Collectors.toList());
						if (attributes.isEmpty()) {
							return "";
						} else {
							return attributes.stream()
									.filter(s -> value.getClass().getName().contains("_" + s + "_"))
									.findFirst().orElse("");
						}
					}
					
					private Object getOriginalObject(EObject eObject) {
						return eObject.eClass().getEAllReferences().stream()
								.filter(r -> r.getName().startsWith("originalObject"))
								.findFirst().map(r -> eObject.eGet(r)).orElse(null);
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
								List<String> strings = o_cast.stream()
										.map(o -> getObjectDescription(o))
										.collect(Collectors.toList());
								return strings.toString();
							}
						}
						return object.toString();
					}
					
					@Override
					public String getValueLabel(int traceIndex) {
						String attributeName = "";
						if (traceIndex > -1 && traceIndex < valueTraces.size()) {
							final List<? extends «valueFQN»> valueTrace = valueTraces.get(traceIndex);
							if (valueTrace.isEmpty()) {
								return "";
							}
							if (valueTrace instanceof EcoreEList) {
								final EcoreEList<?> eList = (EcoreEList<?>) valueTrace;
								final EObject owner = eList.getEObject();
								final List<String> attributes = owner.eClass().getEAllReferences().stream()
										.filter(r -> r.getName().endsWith("Sequence"))
										.map(r -> r.getName().substring(0, r.getName().length() - 8)).collect(Collectors.toList());
								final Object originalObject = getOriginalObject(owner);
								if (!attributes.isEmpty()) {
									String n = eList.data().getClass().getComponentType().getName();
									attributeName = attributes.stream().filter(s -> n.contains("_" + s + "_")).findFirst().orElse("");
								}
								if (originalObject != null) {
									if (originalObject instanceof EObject) {
										final EObject eObject = (EObject) originalObject;
										if (eObject.eIsProxy()) {
											final String proxyToString = eObject.toString();
											final int idx = proxyToString.indexOf("eProxyURI: ") + 11;
											final String s = proxyToString.substring(idx, proxyToString.length() - 1);
											return attributeName + " (" + s + ")";
										}
										final QualifiedName qname = nameProvider.getFullyQualifiedName(eObject);
										if (qname != null) {
											return attributeName + " (" + qname.toString() + " :" + eObject.eClass().getName() + ")";
										}
									}
									return attributeName + " (" + originalObject.toString() + ")";
								}
							}
						}
						return attributeName;
					}
					
					@Override
					public String getStateDescription(int stateIndex) {
						String result = "";
						for (int i = 0; i < valueTraces.size(); i++) {
							if (!isValueTraceIgnored(i)) {
								String description = getValueDescription(i, stateIndex);
								result += (description == null ? "" : (result.length() == 0 ? "" : "\n") + description);
							}
						}
						return result;
					}
					
					@Override
					public String getValueDescription(int traceIndex, int stateIndex) {
						final EObject value = getValueAt(traceIndex, stateIndex);
						if (value == null) {
							return null;
						}
						String description = getValueLabel(traceIndex) + " : ";
						final String attributeName = getValueName(value);
						if (attributeName.length() > 0) {
							final Optional<EStructuralFeature> attribute = value.eClass()
									.getEAllStructuralFeatures().stream()
									.filter(r -> r.getName().equals(attributeName)).findFirst();
							if (attribute.isPresent()) {
								final Object o = value.eGet(attribute.get());
								return description + getObjectDescription(o);
							}
						}
						return description + value;
					}
					
					@Override
					public LaunchConfiguration getLaunchConfiguration() {
						return traceRoot.getLaunchconfiguration();
					}
					
					@Override
					public void ignoreValueTrace(int trace, boolean ignore) {
						if (trace > -1 && trace < valueTraces.size()) {
							ignoredValueTraces.put(trace, ignore);
							cachedMaskedStateEquivalenceClasses.clear();
							notifyListeners();
						}
					}
					
					@Override
					public boolean isValueTraceIgnored(int trace) {
						Boolean result = null;
						if (trace > -1 && trace < valueTraces.size()) {
							result = ignoredValueTraces.get(trace);
						}
						return result != null && result;
					}
					
					@Override
					public void statesAdded(List<State> states) {
						updateEquivalenceClasses(states.stream()
								.map(e -> («stateFQN») e).collect(Collectors.toList()));
						notifyListeners();
					}
					
					private Map<EObject,Map<EReference,List<EObject>>> valuesTracesMap = new HashMap<>();
					
					private Map<ITraceViewListener,Set<TraceViewCommand>> listeners = new HashMap<>();
					
					@Override
					public void valuesAdded(List<Value> values) {
«««						for (EObject value : values) {
«««							final EReference r = value.eContainmentFeature();
«««							final EObject c = value.eContainer();
«««							List<EObject> l = null;
«««							Map<EReference,List<EObject>> m = valuesTracesMap.get(c);
«««							if (m == null) {
«««								m = new HashMap<>();
«««								l = Collections.synchronizedList(new ArrayList<>());
«««								m.put(r, l);
«««								valuesTracesMap.put(c, m);
«««							} else {
«««								l = m.get(r);
«««								if (l == null) {
«««									l = Collections.synchronizedList(new ArrayList<>());
«««									m.put(r, l);
«««								}
«««							}
«««							l.add(value);
«««						}
						// Nothing to do here.
					}
					
					@Override
					public void dimensionsAdded(List<Dimension<? extends Value>> dimensions) {
						if (!dimensions.isEmpty()) {
							valueTraces.clear();
							cachedMaskedStateEquivalenceClasses.clear();
							valueTraces.addAll(getAllValueTraces());
							final List<Integer> insertedTracesIndexes = new ArrayList<>();
							for (Dimension<? extends Value> valueTrace : dimensions) {
								final int i = valueTraces.indexOf(valueTrace);
								insertedTracesIndexes.add(i);
							}
							Collections.sort(insertedTracesIndexes);
							final List<List<Integer>> keys = new ArrayList<>(stateEquivalenceClasses.keySet());
							for (List<Integer> key : keys) {
								List<EObject> states = stateEquivalenceClasses.remove(key);
								for (Integer i : insertedTracesIndexes) {
									key.add(i, -1);
								}
								stateEquivalenceClasses.put(key, states);
							}
							List<Integer> ignoredTracesIndexes = new ArrayList<>(ignoredValueTraces.keySet());
							Collections.sort(ignoredTracesIndexes);
							while (!ignoredTracesIndexes.isEmpty()) {
								int i = ignoredTracesIndexes.remove(0);
								if (insertedTracesIndexes.get(0) <= i) {
									for (int j = ignoredTracesIndexes.size() - 1; j >= 0; j--) {
										final Integer idx = ignoredTracesIndexes.get(j);
										ignoredValueTraces.put(idx+1, ignoredValueTraces.remove(idx));
									}
									ignoredTracesIndexes = ignoredTracesIndexes.stream().map(idx -> idx+1).collect(Collectors.toList());
									ignoredValueTraces.put(i+1, ignoredValueTraces.remove(i));
									insertedTracesIndexes.remove(0);
								}
							}
						}
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
					public void stepsStarted(List<Step> steps) {
						// Nothing to do here.
					}
					
					@Override
					public void stepsEnded(List<Step> steps) {
						// Nothing to do here.
					}
				'''
	}
	
	private def String generateTraceExtractorClass() {
		return 
				'''
					package «packageQN»;
					
					«generateImports»
					
					public class «className» implements ITraceExtractor {
					
						«generateFields»
						«generateConstructors»
						«generateValueUtilities»
						«generateStateUtilities»
						«generateStepUtilities»
						«generateLoadTraceUtilities»
						«generateAPI»
					}
				'''
	}
}
