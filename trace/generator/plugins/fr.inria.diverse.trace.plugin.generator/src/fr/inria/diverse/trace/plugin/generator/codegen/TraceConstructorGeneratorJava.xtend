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


import ecorext.ClassExtension
import ecorext.Rule
import fr.inria.diverse.trace.commons.CodeGenUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.commons.tracemetamodel.StepStrings
import fr.inria.diverse.trace.metamodel.generator.TraceMMGenerationTraceability
import fr.inria.diverse.trace.metamodel.generator.TraceMMStrings
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import fr.inria.diverse.trace.commons.model.trace.TracePackage

class TraceConstructorGeneratorJava {

	// Inputs
	private val String className
	private val String packageQN
	private val EPackage traceMM
	private val EPackage abstractSyntax
	private val TraceMMGenerationTraceability traceability
	private val Set<GenPackage> refGenPackages
	private val boolean gemoc
	private val boolean partialTraceManagement
	
	// Transient
	private boolean getExeToTracedUsed = false
	
	
	// Shortcuts
	private val EClass stateClass
	private val EClass specificStepClass
	
	private val String stateFQN
	private val String specificStepFQN

	public def String getClassName() {
		return className
	}

	new(String languageName, String packageQN, EPackage traceMM, TraceMMGenerationTraceability traceability,
		Set<GenPackage> refGenPackages, boolean gemoc, EPackage abstractSyntax, boolean partialTraceManagement) {
		this.traceMM = traceMM
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "Constructor"
		this.packageQN = packageQN
		this.traceability = traceability
		this.refGenPackages = refGenPackages
		this.gemoc = gemoc
		this.abstractSyntax = abstractSyntax
		this.stateClass = traceability.traceMMExplorer.getSpecificStateClass
		this.specificStepClass = traceability.traceMMExplorer.getSpecificStepClass
		this.partialTraceManagement=partialTraceManagement
		this.stateFQN = getJavaFQN(stateClass)
		this.specificStepFQN = getJavaFQN(specificStepClass)
	}

	private def String getActualFQN(EClass c, Rule r) {
		val EOperation o = r.operation
		return EcoreCraftingUtil.getBaseFQN(c) + "." + o.name
	}
	
	def Set<EClass> findAllConcreteSubTypes(EClass clazz) {
		traceability.allMutableClasses.filter[c|!c.isAbstract && c.EAllSuperTypes.contains(clazz)].toSet
	}
	
	def Set<EClass> findAllDirectConcreteSubTypes(EClass clazz) {
		traceability.allMutableClasses.filter[c|!c.isAbstract && c.ESuperTypes.contains(clazz)].toSet
	}
	
	def Set<EClass> findAllDirectSubTypes(EClass clazz) {
		traceability.allMutableClasses.filter[c|c.ESuperTypes.contains(clazz)].toSet
	}
	
	private def String getFQN(EStructuralFeature eFeature) {
		return EcoreCraftingUtil.getBaseFQN(eFeature.EContainingClass) + "." + eFeature.name
	}

	private static def boolean isNotSuperTypeOf(EClass c, Collection<EClass> eclasses) {
		for (eclass : eclasses) {
			if (eclass.EAllSuperTypes.contains(c))
				return false
		}
		return true
	}
	
	private static def boolean hasSuperTypeIn(EClass c, Collection<EClass> eclasses) {
		for (eclass : eclasses) {
			if (c.EAllSuperTypes.contains(eclass))
				return true
		}
		return false
	}
	
	private def String getTracedJavaFQN(EClassifier c) {
		return getTracedJavaFQN(c,false)
	}
	
	private def String getTracedJavaFQN(EClassifier c, boolean enforcePrimitiveJavaClass) {
		if (c instanceof EClass) {
			val tracedClass = traceability.getTracedClass(c)
			if (tracedClass != null)
				return getJavaFQN(traceability.getTracedClass(c),enforcePrimitiveJavaClass)
			else
				return getJavaFQN(c,enforcePrimitiveJavaClass)
		} else {
			return getJavaFQN(c,enforcePrimitiveJavaClass)
		}
	}
	
	private def String getJavaFQN(EClassifier c) {
		return getJavaFQN(c,false)
	}
	
	private def String getJavaFQN(EClassifier c, boolean enforcePrimitiveJavaClass) {
		return EcoreCraftingUtil.getJavaFQN(c,refGenPackages,enforcePrimitiveJavaClass)
	}
	
	
	private def String stringSetter(EStructuralFeature f, String value) {
		EcoreCraftingUtil.stringSetter(f,value,refGenPackages)
	}
	
	private def String stringSetter(String f, String value) {
		EcoreCraftingUtil.stringSetter(f,value)
	}
			 
	
	private static def Set<EClass> findTopSuperClasses(Iterable<EClass> eclasses) {
		val res = new HashSet<EClass>
		for (c : eclasses) {
			// TODO ugly fix to not include AS classes in the XMOF case, to remove at some point 
			val filtered = c.ESuperTypes.filter[s|! ( c.name.endsWith("Configuration") && c.name.startsWith(s.name))]
			if (filtered.empty) {
				res.add(c) 
			} else {				
				val candidates = findTopSuperClasses(filtered)
				res.addAll(candidates)
			}
		}
		return res
	}
	
	private static def List<EClass> partialOrderSort (Iterable<EClass> eclasses) {
		val List<EClass> result = new ArrayList<EClass>
		for (ci : eclasses) {
			if (result.isEmpty)
				result.add(ci)
			else {
				var boolean found = false
				for (var int i = 0; i < result.size && !found; i++) {
					val Set<EClass> followings = result.subList(i, result.size).toSet
					if (ci.isNotSuperTypeOf(followings)) {
						result.add(i, ci)
						found = true
					}
				}

				if (!found)
					result.add(ci)
			}
		}
		return result

	}
	
	public def String generateCode() {
		val String code = generateTraceConstructorClass()
		try {
			return CodeGenUtil.formatJavaCode(code)
		} catch (Throwable t) {
			return code
		}
	}

	private Map<String, Integer> counters = new HashMap

	private def String uniqueVar(String s) {
		if (!counters.containsKey(s)) {
			counters.put(s, 0)
		}
		return s + counters.get(s)
	}

	private def void incVar(String s) {
		if (!counters.containsKey(s)) {
			counters.put(s, 0)
		}
		counters.put(s, counters.get(s) + 1)
	}
	
	public static def String getBaseFQN(Rule r) {
		val EOperation o = r.operation
		val EClass c = r.containingClass
		return EcoreCraftingUtil.getBaseFQN(c) + "." + o.name
	}

	private def EClassifier getEventParamRuntimeType(EStructuralFeature f) {
		var EClass res = null
		if (f instanceof EAttribute) {
			// TODO
		} else if (f instanceof EReference) {
			val potentialRealRuntimeClass = traceability.getRealMutableClass(f.EReferenceType)
			if (potentialRealRuntimeClass != null) {
				// TODO here in the general case we need to find the exe class
				res = potentialRealRuntimeClass
			} else {
				// TODO same here
				res = f.EReferenceType
			}
		}
		return res
	}

	private def String stringGetterTracedValue(String javaVarName, EStructuralFeature p) {
		val pRealType = traceability.getRealMutableClass(p.EType)
		if (p instanceof EReference && traceability.hasTracedClass(pRealType as EClass)) {
			return '''
				((«getJavaFQN(traceability.getTracedClass(pRealType as EClass))»)exeToTraced.get(«javaVarName».«EcoreCraftingUtil.stringGetter(p)»))
			'''
		} else {
			return javaVarName + "." + EcoreCraftingUtil.stringGetter(p)
		}
	}

	private def Set<EStructuralFeature> getAllMutablePropertiesOf(EClass exeClass) {
		val Set<EStructuralFeature> res = new HashSet<EStructuralFeature>
		res.addAll(traceability.getMutablePropertiesOf(exeClass))
		res.addAll(exeClass.EAllSuperTypes.map[s|traceability.getMutablePropertiesOf(s)].flatten.toSet);
		return res
	}

	private def Set<EClass> getAllNonEmptyMutableClasses() {
		return traceability.allMutableClasses.filter[c|!c.allMutablePropertiesOf.empty].toSet
	}


	private def String generateImports() {
		return
				'''
					«IF getExeToTracedUsed»
					import java.util.ArrayList;
					«ENDIF»
					import java.util.Collection;
					import java.util.Deque;
					import java.util.HashSet;
					import java.util.LinkedList;
					import java.util.List;
					import java.util.Map;
					import java.util.Set;
					
					import org.eclipse.emf.common.util.TreeIterator;
					import org.eclipse.emf.common.util.URI;
					import org.eclipse.emf.ecore.EObject;
					import org.eclipse.emf.ecore.resource.Resource;
					
					import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
					import fr.inria.diverse.trace.commons.model.trace.MSEModel;
					import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
					import fr.inria.diverse.trace.commons.model.trace.TracedObject;
					import fr.inria.diverse.trace.gemoc.api.ITraceConstructor;
				'''
	}
	
	private def String generateFields() {
		return
				'''
					private «getJavaFQN(traceability.traceMMExplorer.getSpecificTraceClass)» traceRoot;
					private MSEModel mseModel;
					private Resource executedModel;
					private final Map<EObject, TracedObject<?>> exeToTraced;
					
					private «stateFQN» lastState;
					
					private Resource traceResource;
					private final Deque<«specificStepFQN»> context = new LinkedList<«specificStepFQN»>();
				'''
	}
	
	private def String generateConstructor() {
		return
				'''
					public «className» (Resource exeModel, Resource traceResource, Map<EObject, TracedObject<?>> exeToTraced) {
						this.traceResource = traceResource;
						this.executedModel = exeModel;
						this.exeToTraced = exeToTraced;
					}
				'''
	}

	
	private def String getExeToTracedMethodName() {
		getExeToTracedUsed = true
		return "getExeToTraced"
	}
	
	private def String generateStoreAsTracedMethods() {
		return
				'''
					
					«FOR mutClass : traceability.allMutableClasses.filter[c|!c.isAbstract].sortBy[name]»
					private void storeAsTracedObject(«getJavaFQN(mutClass)» o) {
						
						«FOR subType : partialOrderSort(findAllConcreteSubTypes(mutClass))  »
						if (o instanceof «getJavaFQN(subType)») {
							storeAsTracedObject((«getJavaFQN(subType)»)o);
						} else 
						«ENDFOR»
						
						«val traced = traceability.getTracedClass(mutClass)»
						
						if (!exeToTraced.containsKey(o)) {
							«getJavaFQN(traced)» tracedObject;
							tracedObject = «EcoreCraftingUtil.stringCreate(traced)»; 
							«val Set<EReference> origRefs1 = traceability.getRefs_originalObject(traced)»
							«FOR EReference origRef : origRefs1.sortBy[name]» 
							tracedObject.«stringSetter(origRef, "o")»;
							«ENDFOR»
							exeToTraced.put(o, tracedObject);
							traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_createTraceClassToTracedClass(traced))».add(tracedObject);
						}
					}
					«ENDFOR»
					«IF getExeToTracedUsed»
					private void storeAsTracedObject(EObject o) {
						 «FOR mutClass : partialOrderSort(traceability.allMutableClasses.filter[c|!c.isAbstract].toList.sortBy[name]) SEPARATOR "\n else "»
						if (o instanceof «getJavaFQN(mutClass)») {
							storeAsTracedObject((«getJavaFQN(mutClass)»)o);
						}
						«ENDFOR»
					}
					«ENDIF»
				'''
	}
	

	
	private def String stringFeatureID(EStructuralFeature p) {
		val containingClass = if (p.eContainer instanceof EClass) p.eContainer as EClassifier else (p.eContainer as ClassExtension).extendedExistingClass
		return EcoreCraftingUtil.stringFeatureID(p,containingClass,refGenPackages)
	}
	
	private def String generateGetAllResourcesMethod() {
		return
				'''
					private Set<Resource> getAllExecutedModelResources() {
						Set<Resource> allResources = new HashSet<>();
						allResources.add(executedModel);
						«IF gemoc»
						allResources.addAll(org.gemoc.commons.eclipse.emf.EMFResource.getRelatedResources(executedModel));
						allResources.removeIf(r -> r== null);
						«ENDIF»
						return allResources;
					}
				'''
	}
	
	private def String generateAddInitialStateMethod() {
		return '''
		
		private void addInitialState() {
			if (lastState == null) {
		// Creation of the initial state
		Set<Resource> allResources = getAllExecutedModelResources();
		lastState =  «EcoreCraftingUtil.stringCreate(stateClass)»;
		for (Resource r : allResources) {
			for (TreeIterator<EObject> i = r.getAllContents(); i.hasNext();) {
				EObject o = i.next();
					«FOR c : partialOrderSort(findTopSuperClasses(traceability.allMutableClasses.filter[c|!c.isAbstract].sortBy[name].toSet)) SEPARATOR "else"»
						
						if (o instanceof «getJavaFQN(c)») {
							«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
							addNewObjectToState(o_cast, lastState);
						}
					«ENDFOR»
			}
		}
		this.traceRoot.getStates().add(lastState);
		}}
		'''
	}
	
	private def boolean shouldHaveAddNewObjectToStateMethod(EClass c){
		val subTypes = findAllDirectSubTypes(c)
		
		if (!c.abstract)
			return true
		if (subTypes.empty && c.abstract)
			return false
		else if (!subTypes.empty && c.abstract) {
			val validSubTypes = subTypes.filter[s|shouldHaveAddNewObjectToStateMethod(s)]
			if (!validSubTypes.empty)
				return true
		} 
		
		return true
	}
	
	private def String generateAddNewObjectToStateMethods() {
		return
				'''
				«FOR c : traceability.allMutableClasses.sortBy[name]»
				«««»«FOR c : partialOrderSort(getAllNonEmptyMutableClasses.filter[c|!c.isAbstract].sortBy[name].toList)»
					«val traced = traceability.getTracedClass(c)»
					«val subTypes = partialOrderSort(findAllDirectSubTypes(c))»
					«IF shouldHaveAddNewObjectToStateMethod(c)»
					«IF getAllMutablePropertiesOf(c).exists[p|p instanceof EReference && p.many]»
					@SuppressWarnings("unchecked")
					«ENDIF»
					private boolean addNewObjectToState(«getJavaFQN(c)» o_cast, «stateFQN» newState) {
					boolean added = false; 
					«val subTypesWithMethods = subTypes.filter[sub|shouldHaveAddNewObjectToStateMethod(sub)]»
					«FOR subType : subTypesWithMethods SEPARATOR " else " »
						if (o_cast instanceof «getJavaFQN(subType)») {
							added = addNewObjectToState((«getJavaFQN(subType)»)o_cast, newState);
						}
					«ENDFOR»
						
					«IF ! c.abstract»
					
					if (!added && !exeToTraced.containsKey(o_cast)) {
						«getJavaFQN(traced)» tracedObject = «EcoreCraftingUtil.stringCreate(traced)»;
						«val Set<EReference> origRefs1 = traceability.getRefs_originalObject(traced)»
						«FOR EReference origRef : origRefs1.sortBy[name]» 
							tracedObject.«stringSetter(origRef, "o_cast")»;
						«ENDFOR»
						exeToTraced.put(o_cast, tracedObject);
						traceRoot.getTracedObjects().add(tracedObject);
						
						«FOR p : getAllMutablePropertiesOf(c).sortBy[FQN]»
						«val EClass pdimensionClass = traceability.getDimensionClass(p)»
						«val EReference pdimensionRef = traceability.getDimensionRef(p)»
						«val EClass valueClass = traceability.getValueClass(p)»
						«val valueProperty = traceability.getValuePropertyOfMutableProperty(p)»
						
						// Creation of the dimension corresponding to the field «p.name»
						tracedObject.«EcoreCraftingUtil.stringSetter(pdimensionRef, EcoreCraftingUtil.stringCreate(pdimensionClass))»;
						// Creation of the first value of the field «p.name»
						«getJavaFQN(valueClass)» firstValue_«p.name» = «EcoreCraftingUtil.stringCreate(valueClass)»;
						
						«IF p.many»
						
						«IF p instanceof EReference»
						
						
						«IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
							for(«getJavaFQN(p.EType)» aValue : o_cast.«EcoreCraftingUtil.stringGetter(p)») {
								addNewObjectToState((«getJavaFQN(traceability.getRealMutableClass(p.EType))»)aValue, newState);
							}
						«ENDIF»
						
						firstValue_«p.name».«EcoreCraftingUtil.stringGetter(p)».addAll
							(
							(Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>)
							«IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
							«getExeToTracedMethodName»(o_cast.«EcoreCraftingUtil.stringGetter(p)», newState)
							«ELSE»
							o_cast.«EcoreCraftingUtil.stringGetter(p)»
							«ENDIF»
							);
						«ELSE» ««« If attribute
						firstValue_«p.name».«EcoreCraftingUtil.stringGetter(p)».addAll
							((Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>) o_cast.«EcoreCraftingUtil.stringGetter(p)»);
						«ENDIF» ««« End IF EReference
						
						
						«ELSE» ««« If !many
						
						«IF p instanceof EReference»
						«val realMutableType = traceability.getRealMutableClass(p.EType)»
						if (o_cast.«EcoreCraftingUtil.stringGetter(p)» != null) {
							«IF traceability.allMutableClasses.contains(realMutableType)»
							addNewObjectToState((«getJavaFQN(realMutableType)»)o_cast.«EcoreCraftingUtil.stringGetter(p)», newState);
							firstValue_«p.name».«stringSetter(valueProperty,stringGetterTracedValue("o_cast", p))»;
							«ELSE»
							firstValue_«p.name».«stringSetter(valueProperty,"o_cast."+EcoreCraftingUtil.stringGetter(p))»;
							«ENDIF» ««« End IF traceability.isSomehowMutable(p.EType)
						} else {
							firstValue_«p.name».«stringSetter(valueProperty,"null")»;
						}
						
						«ELSE» ««« If attribute
						firstValue_«p.name».«stringSetter(valueProperty,"o_cast."+EcoreCraftingUtil.stringGetter(p))»;
						«ENDIF» ««« End IF EReference
						«ENDIF» ««« End IF p.many
						
						tracedObject.«EcoreCraftingUtil.stringGetter(pdimensionRef)».getValues().add(firstValue_«p.name»);
						newState.getValues().add(firstValue_«p.name»);
						
					«ENDFOR» ««« End FOR p : getAllMutableProperties
					} // end if (!exeToTraced.containsKey
					«ENDIF» ««« End IF ! c.abstract»
					return added;
					}// end addNewObjectToState
					«ENDIF»
				«ENDFOR» ««« end FOR c : traceability.allMutableClasses.sortBy[name]
				'''
	}

private def String generateAddStateUsingListenerMethods() {
	val newClassesNotEmpty = traceability.getNewClasses.filter[c|!c.EStructuralFeatures.empty].toSet
	val newAbstractClassesNotEmpty = partialOrderSort(newClassesNotEmpty.filter[c|!c.isAbstract].toSet)
	val newConcreteClassesNotEmpty = traceability.getNewClasses.filter[c|
		val superTypes = new ArrayList(c.EAllSuperTypes)
		superTypes.retainAll(newAbstractClassesNotEmpty)
		return !c.isAbstract && (!c.EStructuralFeatures.empty || !superTypes.empty)
	].toList
	//val newClassesNotEmptyWithMutableProperties = newClassesNotEmpty.filter[c|!traceability.getMutablePropertiesOf(c).empty]
//	val newClassesNotEmptyClosure = partialOrderSort(newClassesNotEmpty.filter[c|
//		val superTypes = new ArrayList(c.EAllSuperTypes)
//		superTypes.retainAll(newClassesNotEmpty)
//		!(c.EStructuralFeatures.empty && !superTypes.empty)
//	].toList)
	val allConcreteMutableClasses = partialOrderSort(getAllNonEmptyMutableClasses.filter[c|!c.isAbstract].sortBy[name].toList)
	//val mutableClassesWithNonCollectionMutableFields = allConcreteMutableClasses.filter[c|getAllMutablePropertiesOf(c).exists[p|!p.many]]
	//val mutableClassesWithCollectionMutableFields    = allConcreteMutableClasses.filter[c|getAllMutablePropertiesOf(c).exists[p| p.many]]
	val mutableClassesWithNonCollectionMutableFields = traceability.allMutableClasses.filter[c|!traceability.getMutablePropertiesOf(c).empty && traceability.getMutablePropertiesOf(c).exists[p|!p.many]]
	val mutableClassesWithCollectionMutableFields    = traceability.allMutableClasses.filter[c|!traceability.getMutablePropertiesOf(c).empty && traceability.getMutablePropertiesOf(c).exists[p|p.many]]
	return
			'''
				private boolean copiedState = false;
				
				private «stateFQN» copyState(«stateFQN»  oldState) {
					«stateFQN» newState = «EcoreCraftingUtil.stringCreate(stateClass)»;
«««					«FOR p : traceability.allMutableProperties.sortBy[FQN]»
«««					newState.«EcoreCraftingUtil.stringGetter(traceability.getStateClassToValueClass(p))».addAll(oldState.«EcoreCraftingUtil.stringGetter(traceability.getStateClassToValueClass(p))»);
«««					«ENDFOR»
					newState.getValues().addAll(oldState.getValues());
					copiedState = true;
					return newState;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public void addState(List<org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange> changes) {
					if (lastState == null) {
						addInitialState();
					}««« end if laststate null
					if (!changes.isEmpty()) {
						boolean stateChanged = false;
						// We start by a (shallow) copy of the last state
						// But we will have to rollback a little by replacing values that changed
						«stateFQN» newState = copyState(lastState);
						for (org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange modelChange : changes) {
							EObject o = modelChange.getChangedObject();
							«IF !newConcreteClassesNotEmpty.empty»
							// We only look at constructable objects that have mutable fields
							// Here we have nothing to rollback, just a new object to add
							if (modelChange instanceof org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NewObjectModelChange) {
								stateChanged = true;
								««« Loop over all classes that may be constructed and that have mutable fields
								«FOR c : partialOrderSort(findTopSuperClasses(newConcreteClassesNotEmpty))»
								if (o instanceof «getJavaFQN(c)») {
									«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
									addNewObjectToState(o_cast, newState);
								} ««« end if instanceof
								«ENDFOR»
							}««« end if NewObjectModelChange
							
							// We only look at constructable objects that have mutable fields
							// Here we must rollback to remove the values of the removed object from the copied state
							else if (modelChange instanceof org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.RemovedObjectModelChange) {
								stateChanged = true;
								««« Loop over all classes that may type something constructed and that have mutable fields
								«FOR c : partialOrderSort(newClassesNotEmpty)»
								«val traced = traceability.getTracedClass(c)»
								if (o instanceof «getJavaFQN(c)») {
									«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
									«getJavaFQN(traced)» traced = («getJavaFQN(traced)») exeToTraced.get(o_cast);
									««« Loop over the fields of this class, which are all mutable
									«FOR p : c.EStructuralFeatures»
									«val EReference pvalues = traceability.getStateClassToValueClass(p)»
									«val EReference pdimension = traceability.getDimensionRef(p)»
«««									newState.«EcoreCraftingUtil.stringGetter(pvalues)».remove(traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().get(traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().size()-1));
									newState.getValues().remove(traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().get(traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().size()-1));
									«ENDFOR»
								}««« end if instanceof
								«ENDFOR»
							}««« end if RemovedObjectModelChange
							«ENDIF»
							«IF !mutableClassesWithNonCollectionMutableFields.empty»
							// Here we must look at non-collection mutable fields
							// We must rollback the last values from the copied state, and add new values as well
							// ie. mix of remove and new
							«IF !newConcreteClassesNotEmpty.empty» else «ENDIF» if (modelChange instanceof org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NonCollectionFieldModelChange) {
								stateChanged = true;
								
								org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NonCollectionFieldModelChange modelChange_cast = (org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NonCollectionFieldModelChange) modelChange;
								«EStructuralFeature.canonicalName » p = modelChange_cast.getChangedField();
								«FOR c : partialOrderSort(mutableClassesWithNonCollectionMutableFields)»
								«val nonCollectionMutableFields = traceability.getMutablePropertiesOf(c).filter[p|!p.many]»
								
								«val traced = traceability.getTracedClass(c)»
								if (o instanceof «getJavaFQN(c)») {
									«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
									
									«FOR p : nonCollectionMutableFields SEPARATOR " else "»
									
									«val EClass valueClass = traceability.getValueClass(p)»
									«val EReference pvalues = traceability.getStateClassToValueClass(p)»
									«val EReference pdimension = traceability.getDimensionRef(p)»
									
									if (p.getFeatureID() == «stringFeatureID(p)») {
										
										// Rollback: we remove the last value of this field from the new state
										«getJavaFQN(traced)» traced = («getJavaFQN(traced)») exeToTraced.get(o);
										«getJavaFQN(valueClass)» lastValue = traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().get(traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().size()-1);
«««										newState.«EcoreCraftingUtil.stringGetter(pvalues)».remove(lastValue);
										newState.getValues().remove(lastValue);
										
										// And we create a proper new value
										««« «IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
										««« addNewObjectToState((«getJavaFQN(traceability.getRealMutableClass(p.EType))»)o_cast.«EcoreCraftingUtil.stringGetter(p)», newState);			
										««« «ENDIF»
										«getJavaFQN(valueClass)» newValue = «EcoreCraftingUtil.stringCreate(valueClass)»;
										«val valueProperty = traceability.getValuePropertyOfMutableProperty(p)»
										
										«IF p instanceof EReference»
										«getJavaFQN(valueProperty.EType)» value = null;
										if (o_cast.«EcoreCraftingUtil.stringGetter(p)» != null) {
											addNewObjectToState(o_cast.«EcoreCraftingUtil.stringGetter(p)», newState);
											value = «stringGetterTracedValue("o_cast", p)»;
										}
										«ELSE»
										«getJavaFQN(valueProperty.EType)» value = o_cast.«EcoreCraftingUtil.stringGetter(p)»;
										«ENDIF»
										
										newValue.«stringSetter(valueProperty,"value")»;
										
										traced.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().add(newValue);
«««										newState.«EcoreCraftingUtil.stringGetter(pvalues)».add(newValue);
										newState.getValues().add(newValue);
									}
									««« end if feature id
									«ENDFOR»
								}
								««« end if instance of
								«ENDFOR»
							}
							««« end if NonCollectionFieldModelChange
							«ENDIF»
							«IF !mutableClassesWithCollectionMutableFields.empty»
							// Here we look at collection mutable fields
							// We must first manually find out if the collection changed...
							// If it changed we must rollback the last values from the copied state, and add new values as well
							«IF !newConcreteClassesNotEmpty.empty || !mutableClassesWithNonCollectionMutableFields.empty » else «ENDIF» if (modelChange instanceof org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.PotentialCollectionFieldModelChange) {
								org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.PotentialCollectionFieldModelChange modelChange_cast = (org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.PotentialCollectionFieldModelChange) modelChange;
								«EStructuralFeature.canonicalName » p = modelChange_cast.getChangedField();
								«FOR c : partialOrderSort(mutableClassesWithCollectionMutableFields) »
								«val collectionMutableFields = traceability.getMutablePropertiesOf(c).filter[p|p.many]»
								«val traced = traceability.getTracedClass(c)»
								if (o instanceof «getJavaFQN(c)») {
									«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
									«getJavaFQN(traced)» tracedObject = («getJavaFQN(traced)») exeToTraced.get(o_cast);
									«FOR p : collectionMutableFields SEPARATOR " else "»
									«val EClass valueClass = traceability.getValueClass(p)»
									«val EReference pvalues = traceability.getStateClassToValueClass(p)»
									«val EReference pdimension = traceability.getDimensionRef(p)»
									if (p.getFeatureID() == «stringFeatureID(p)») {
										// We compare the last collection in the value sequence, and the current one in the potentially changed object
										List<«getJavaFQN(valueClass)»> valueSequence = tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues();
										«getJavaFQN(valueClass)» previousValue = null;
										if (!valueSequence.isEmpty()) {
											previousValue = valueSequence.get(valueSequence.size() - 1);
										}
										««« If instances of new class, we have to make sure that there are traced versions 
										«IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
										for(«getJavaFQN(p.EType)» aValue : o_cast.«EcoreCraftingUtil.stringGetter(p)») {
											addNewObjectToState((«getJavaFQN(traceability.getRealMutableClass(p.EType))»)aValue, newState);
										}««« end for loop on values
										«ENDIF»
										boolean change = false;
										if (previousValue != null) {
											if (previousValue.«EcoreCraftingUtil.stringGetter(p)».size() == o_cast
													.«EcoreCraftingUtil.stringGetter(p)».size()) {
												««« We this is an ordered collection, we have to compare in the correct order
												«IF p.ordered»
												java.util.Iterator<«getJavaFQN(p.EType,true)»> it = o_cast.«EcoreCraftingUtil.stringGetter(p)».iterator();
												for («getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)» aPreviousValue : previousValue
														.«EcoreCraftingUtil.stringGetter(p)») {
													«getJavaFQN(p.EType)» aCurrentValue = it.next();
													«IF p instanceof EReference»
													if (aPreviousValue != exeToTraced.get(aCurrentValue))
													«ELSE»
													if (!aPreviousValue.equals(aCurrentValue))
													«ENDIF»
													{
														change = true;
														break;
													}
												}
												««« Else we simply check that the content is the same
												«ELSE»	
												change = !previousValue.«EcoreCraftingUtil.stringGetter(p)».containsAll(«getExeToTracedMethodName»(o_cast.«EcoreCraftingUtil.stringGetter(p)»), newState);
												«ENDIF»
												««« end case ordered
											}««« end if same size
											else {
												change = true;
											}««« end else
										} else {
											change = true;
										}««« end else
										if (change) {
											stateChanged = true;
											// Rollback: we remove the last value of this field from the new state
											«getJavaFQN(valueClass)» lastValue = tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().get(tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().size()-1);
«««											newState.«EcoreCraftingUtil.stringGetter(pvalues)».remove(lastValue);
											newState.getValues().remove(lastValue);
											// And we create a proper new value
											«getJavaFQN(valueClass)» newValue = «EcoreCraftingUtil.stringCreate(valueClass)»;
											«val valueProperty = traceability.getValuePropertyOfMutableProperty(p)»
											«IF p.many»
											«IF p instanceof EReference»
											newValue.«EcoreCraftingUtil.stringGetter(valueProperty)».addAll
												((Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>) «getExeToTracedMethodName»(o_cast.«EcoreCraftingUtil.stringGetter(p)», newState));
											«ELSE»
											newValue.«EcoreCraftingUtil.stringGetter(valueProperty)».addAll
												((Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>) o_cast.«EcoreCraftingUtil.stringGetter(p)»);
											«ENDIF»
											«ELSE»
											newValue.«stringSetter(valueProperty,stringGetterTracedValue("o_cast", p))»;
											«ENDIF»
											tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().add(newValue);
«««											newState.«EcoreCraftingUtil.stringGetter(pvalues)».add(newValue);
											newState.getValues().add(newValue);
										}««« end if change
									}««« end if featureid
									«ENDFOR»
								} ««« end if instanceof
								«ENDFOR»
							}««« end if PotentialCollectionFieldModelChange
						«ENDIF»
						}««« end for all changes
						if (stateChanged) {
							final «specificStepFQN» currentStep = context.peekFirst();
							if (currentStep != null && currentStep instanceof «getJavaFQN(TracePackage.eINSTANCE.bigStep)») {
								final «stateFQN» startingState = lastState;
								final «stateFQN» endingState = newState;
								addImplicitStep(currentStep, startingState, endingState);
							}
							lastState = newState;
							traceRoot.getStates().add(lastState);
«««							traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_TraceToStates)».add(lastState);
						}««« end if (stateChanged)
						else if (copiedState) {
							«FOR p : traceability.allMutableProperties.sortBy[FQN]»
«««							newState.«EcoreCraftingUtil.stringGetter(traceability.getStateClassToValueClass(p))».clear();
							newState.getValues().clear();
							«ENDFOR»
						}
						copiedState = false;
					}««« end if (!changes.isEmpty())
				}««« end method
			'''
	}
	
	private def String generateAddStateMethods() {
		return
			'''
				@Override
				public boolean addStateIfChanged() {
					return addState(true);
				}
			
				@Override
				public void addState() {
					addState(false);
				}
				
				private boolean addState(boolean onlyIfChange) {
					
					«getJavaFQN(traceability.traceMMExplorer.getSpecificStateClass)» newState = «EcoreCraftingUtil.stringCreate(
						traceability.traceMMExplorer.getSpecificStateClass)»;
					boolean changed = false;
					
					Set<Resource> allResources = getAllExecutedModelResources();
			
					// We look at each object instance of a class with mutable properties 
					// Each of these objects should eventually become a traced object
					for (Resource r : allResources)
					for (TreeIterator<EObject> i = r.getAllContents(); i.hasNext();){
						EObject o = i.next();
					«FOR c : partialOrderSort(getAllNonEmptyMutableClasses.filter[c|!c.isAbstract].sortBy[name].toList) SEPARATOR "\n else "»
					«val traced = traceability.getTracedClass(c)»
						/**
						 * Storing the state of a «getJavaFQN(c)» object
						 */
						if (o instanceof «getJavaFQN(c)») {
							«getJavaFQN(c)» o_cast = («getJavaFQN(c)») o;
							storeAsTracedObject(o_cast);
							
							«IF !getAllMutablePropertiesOf(c).empty»
								«getJavaFQN(traced)» tracedObject = («getJavaFQN(traced)») exeToTraced.get(o);
							«ENDIF»
							«FOR p : getAllMutablePropertiesOf(c).sortBy[FQN]»
							«val EClass valueClass = traceability.getValueClass(p)»
							«val EReference pdimension = traceability.getDimensionRef(p)»
							«incVar("valueSequence")»
							«val EReference refGlobalToState = traceability.getStateClassToValueClass(p)»
							«incVar("previousValue")»
							«incVar("noChange")»
			
							// Then we compare the value of the field with the last stored value
							// If same value, we create no local state and we refer to the previous
							««« TODO to change if we switch from refering the exeMM to refering the AS (as in the ECMFA paper) -> need to compare to refs to origobjs/tracedobj
							««« TODO handle collections of datatypes
							List<«getJavaFQN(valueClass)»> «uniqueVar("valueSequence")» = tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues();
							«getJavaFQN(valueClass)» «uniqueVar("previousValue")» = null;
							if (!«uniqueVar("valueSequence")».isEmpty()) {
								«uniqueVar("previousValue")» = «uniqueVar("valueSequence")».get(«uniqueVar("valueSequence")».size() - 1);
							}
							««« Case many
							«IF p.many»
							««« If instances of new class, we have to make sure that there are traced versions 
							«IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
							for(«getJavaFQN(p.EType)» aValue : o_cast.«EcoreCraftingUtil.stringGetter(p)») {
								storeAsTracedObject(aValue);
							}
							«ENDIF»
							
							boolean «uniqueVar("noChange")»= true;
							if («uniqueVar("previousValue")» != null) {
								if («uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)».size() == o_cast
										.«EcoreCraftingUtil.stringGetter(p)».size()) {
									««« We this is an ordered collection, we have to compare in the correct order
									«IF p.ordered»
									java.util.Iterator<«getJavaFQN(p.EType,true)»> it = o_cast.«EcoreCraftingUtil.stringGetter(p)».iterator();
									for («getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)» aPreviousValue : «uniqueVar("previousValue")»
											.«EcoreCraftingUtil.stringGetter(p)») {
										«getJavaFQN(p.EType)» aCurrentValue = it.next();
										«IF p instanceof EReference»
										if (aPreviousValue != exeToTraced.get(aCurrentValue)) {
										«ELSE»
										if (!aPreviousValue.equals(aCurrentValue)) {
										«ENDIF»
											«uniqueVar("noChange")» = false;
											break;
										}
									}
									
									««« Else we simply check that the content is the same
									«ELSE»	
									«uniqueVar("noChange")» = «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)».containsAll(«getExeToTracedMethodName»(o_cast.«EcoreCraftingUtil.stringGetter(p)»));
									«ENDIF»
									««« end case ordered
								} else {
									«uniqueVar("noChange")» = false;
								}
							} else {
								«uniqueVar("noChange")» = false;
							}
							««« Case single
							«ELSE»
							««« If instance of new class, we have to make sure that there is a traced version 
							«IF traceability.allMutableClasses.contains(traceability.getRealMutableClass(p.EType))»
							storeAsTracedObject(o_cast.«EcoreCraftingUtil.stringGetter(p)»);
							«ENDIF»
							««« Getting the content of the field
							«incVar("content")»
							««« Case reference
							«IF p instanceof EReference»
							«getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType)» «uniqueVar("content")» = null;
							if (o_cast.«EcoreCraftingUtil.stringGetter(p)» != null)
								«uniqueVar("content")» = «stringGetterTracedValue("o_cast", p)»;
							«««
							««« Case datatype
							«ELSEIF p instanceof EAttribute»
								«getJavaFQN(p.EType)» «uniqueVar("content")» = o_cast.«EcoreCraftingUtil.stringGetter(p)»;
							«ENDIF»
							««« end declaring/getting content
							
							«IF getJavaFQN(p.EType) == "java.lang.Byte"
								|| getJavaFQN(p.EType) == "java.lang.Short"
								|| getJavaFQN(p.EType) == "java.lang.Integer"
								|| getJavaFQN(p.EType) == "java.lang.Long"
								|| getJavaFQN(p.EType) == "java.lang.Boolean"
								|| getJavaFQN(p.EType) == "java.lang.Float"
								|| getJavaFQN(p.EType) == "java.lang.Double"
								|| getJavaFQN(p.EType) == "java.lang.String"»
								boolean «uniqueVar("noChange")» = «uniqueVar("previousValue")» != null
									&& «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)» != null
									&& «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)».equals(«uniqueVar("content")»);
							«ELSE»
								boolean «uniqueVar("noChange")» = «uniqueVar("previousValue")» != null
									&& «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)» == «uniqueVar("content")»;
							«ENDIF»
							
							«ENDIF»
							««« end collection/single
							if («uniqueVar("noChange")») {
								newState.«EcoreCraftingUtil.stringGetter(refGlobalToState)».add(«uniqueVar("previousValue")»);
			
							} // Else we create one
							else {
								changed = true;
								«getJavaFQN(valueClass)» newValue = «EcoreCraftingUtil.stringCreate(valueClass)»;
								««« Case collection
								««« TODO: handle collections of datatypes!
								«IF p.many»
								
								«IF p instanceof EReference»
									newValue.«EcoreCraftingUtil.stringGetter(p)».addAll
										((Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>) «getExeToTracedMethodName»(o_cast.«EcoreCraftingUtil.stringGetter(p)»));
									«ELSE»
									newValue.«EcoreCraftingUtil.stringGetter(p)».addAll
										((Collection<? extends «getTracedJavaFQN(traceability.getValuePropertyOfMutableProperty(p).EType,true)»>) o_cast.«EcoreCraftingUtil.stringGetter(p)»);
									«ENDIF»
								««« Case single
								«ELSE»
								
									newValue.«stringSetter(p, uniqueVar("content"))»;
								
								«ENDIF»
								««« end collection/Single
								tracedObject.«EcoreCraftingUtil.stringGetter(pdimension)».getValues().add(newValue);
								newState.«EcoreCraftingUtil.stringGetter(refGlobalToState)».add(newValue);
							}
							
							«ENDFOR»
							}
						«ENDFOR»
						}
						
						boolean createNewState = lastState == null || (!onlyIfChange || changed);
						if (createNewState) {
							
							final «specificStepFQN» currentStep = context.peekFirst();
							if (currentStep != null && currentStep instanceof «getJavaFQN(TracePackage.eINSTANCE.bigStep)») {
								final «stateFQN» startingState = lastState;
								final «stateFQN» endingState = newState;
								addImplicitStep(currentStep, startingState, endingState);
							}
							
							lastState = newState;
							traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_TraceToStates)».add(lastState);
						}
						
						// Undoing the new state created for nothing
						else {
						
						newState.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StateToStep_started)».clear();
						newState.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StateToStep_ended)».clear();	
						
						«FOR p : traceability.allMutableProperties.sortBy[FQN]»
						«val EReference tuple = traceability.getStateClassToValueClass(p)»
						newState.«EcoreCraftingUtil.stringGetter(tuple)».clear();
						«ENDFOR»
						}
						
						return createNewState;
						
				}
			'''
	}

	private def String generateAddStepMethods() {
		return
				'''
					«val stepRules = traceability.mmext.rules»
					«IF gemoc»
					@SuppressWarnings("unchecked")
					@Override
					public void addStep(fr.inria.diverse.trace.commons.model.trace.Step<?> step) {
						«specificStepFQN» step_cast = null;
						if (step != null && step instanceof «specificStepFQN») {
							step_cast = («specificStepFQN») step;
							if (mseModel == null) {
								mseModel = fr.inria.diverse.trace.commons.model.trace.TraceFactory.eINSTANCE.createMSEModel();
								traceResource.getContents().add(mseModel);
							}
							mseModel.getOwnedMSEs().add(step_cast.getMseoccurrence().getMse());
					
							// Creating generic (or almost generic) links
							«stateFQN» state = traceRoot.getStates().get(traceRoot.getStates().size()-1);
							step_cast.setStartingState(state);
							if (!context.isEmpty() && context.getFirst() != null) {
								((SequentialStep<«specificStepFQN», «stateFQN»>) context.getFirst()).getSubSteps().add(step_cast);
							} else {
								((SequentialStep<«specificStepFQN», «stateFQN»>) traceRoot.getRootStep()).getSubSteps().add(step_cast);
							}
							
							// Adding step in its dedicated sequence/dimension
							«IF !stepRules.empty»
							«FOR stepRule : stepRules.sortBy[baseFQN] SEPARATOR "else"»
							«val EClass stepClass = traceability.getStepClassFromStepRule(stepRule)»
							«val String varName = stepClass.name.toFirstLower.replace(" ", "") + "Instance"»
							if (step_cast instanceof «getJavaFQN(stepClass)») {
								«getJavaFQN(stepClass)» «varName» = («getJavaFQN(stepClass)») step_cast;
								traceRoot.«EcoreCraftingUtil.stringGetter(traceability.getStepSequence(stepClass))».add(«varName»);
							}
							«ENDFOR»
							«ENDIF»
						}
						context.push(step_cast);
					}
					«ELSE»
					@Override
					public void addStep(String stepRule, Map<String, Object> params) {
						addStep(stepRule, params, this.getTraceSize()-1);
					}
					
					private void addStep(String stepRule, Map<String, Object> params, int stateIndex) {
						«specificStepFQN» toPush = null;
						if (stateIndex >= 0) {
							«stateFQN» state = this.traceRoot.getStates().get(stateIndex);
							«IF !stepRules.empty»
							«FOR stepRule : stepRules.sortBy[baseFQN] SEPARATOR "else"»
							«val stepCallerClass = stepRule.containingClass»
							«val possibleCallerClasses = abstractSyntax.EClassifiers
								.filter[c|c instanceof EClass]
								.map[c|c as EClass]
								.filter[c|c.equals(stepCallerClass)||c.EAllSuperTypes.contains(stepCallerClass)]
								.toSet»
							«val EClass stepClass = traceability.getStepClassFromStepRule(stepRule)»
							«val String varName = stepClass.name.toFirstLower.replace(" ", "") + "Instance"»
							«IF possibleCallerClasses.empty»
							if (stepRule.equalsIgnoreCase("«getBaseFQN(stepRule)»")) {
								«ELSE»
								if (
								«FOR possibleCallerClass: possibleCallerClasses.sortBy[name] SEPARATOR " || "»
									stepRule.equalsIgnoreCase("«getActualFQN(possibleCallerClass, stepRule)»")
								«ENDFOR»
								) {
								«ENDIF»
								// First we create the step
								«getJavaFQN(stepClass)» «varName» = «EcoreCraftingUtil.stringCreate(stepClass)»;
								«varName».«stringSetter(TraceMMStrings.ref_StepToState_starting, "state")»;
								
								if (!context.isEmpty() && context.getFirst() != null){
									((fr.inria.diverse.trace.commons.model.trace.SequentialStep) context.getFirst()).getSubSteps().add(«varName»);
								} else {
									traceRoot.getRootSteps().add(«varName»);
								}
								toPush = «varName»;
								««« TODO rely on information in Rule instead of the structural features?
								«val properties = stepClass.EAllStructuralFeatures.filter[f|
								!TracePackage.eINSTANCE.smallStep.EStructuralFeatures.contains(f) &&
									!TracePackage.eINSTANCE.bigStep.EStructuralFeatures.contains(f) &&
									!traceability.traceMMExplorer.getSpecificStepClass.EStructuralFeatures.contains(f) &&
									!f.name.equals(StepStrings.ref_BigStepToSub)
									&& !f.EContainingClass.name.equals("MSEOccurrence")]»
								«IF !properties.empty»
								if (params != null) {
									for (String k : params.keySet()) {
										switch(k) {
										«FOR p : properties.sortBy[name]»
										case "«p.name»":
											Object «uniqueVar("v")» = params.get(k);
											«val type = getEventParamRuntimeType(p)»
											if («uniqueVar("v")» instanceof «getJavaFQN(type)») {
												«IF type == p.EType»
												«varName».«stringSetter(p, "(" + getJavaFQN(p.EType) + ")"+uniqueVar("v"))»;
												«ELSE»
												«varName».«stringSetter(p, "(" + getJavaFQN(p.EType) + ")exeToTraced.get("+uniqueVar("v"+")"))»;
												«ENDIF»
											}
											break;
										«incVar("v")»
										«ENDFOR»
										}
									}
								}
								«ENDIF»
								// Then we add it to its trace
								this.traceRoot.«EcoreCraftingUtil.stringGetter(traceability.getStepSequence(stepClass))».add(«varName»);
								}
								«ENDFOR»
							«ENDIF»
						}
						context.push(toPush);
					}
					«ENDIF»

					@SuppressWarnings("unchecked")
					private void addImplicitStep(«specificStepFQN» currentStep,
						«stateFQN» startingState,
						«stateFQN» endingState) {
						
						«IF !stepRules.empty && !traceability.bigStepClasses.empty»
							«specificStepFQN» implicitStep = null;
							«FOR bigStepClass : traceability.bigStepClasses.sortBy[name] SEPARATOR "else"»
								if (currentStep instanceof «getJavaFQN(bigStepClass)») {
									implicitStep = «EcoreCraftingUtil.stringCreateImplicitStep(bigStepClass)»;
								}
							«ENDFOR»
						if (implicitStep != null) {
							implicitStep.setStartingState(startingState);
							implicitStep.setEndingState(endingState);
							((SequentialStep<«specificStepFQN», «stateFQN»>) currentStep).getSubSteps().add(implicitStep);
							
						}
						«ENDIF»
					}
					
					@Override
					public void endStep(fr.inria.diverse.trace.commons.model.trace.Step<?> step) {
						«specificStepFQN» popped = context.pop();
						if (popped != null)
							popped.setEndingState(lastState);
					}
				'''
	}

	private def String generateInitAndSaveTraceMethods() {
		return
				'''
					@Override
					public EObject initTrace(LaunchConfiguration launchConfiguration) {
						// Create root
						traceRoot = «EcoreCraftingUtil.stringCreate(traceability.traceMMExplorer.getSpecificTraceClass)»;
						traceRoot.setLaunchconfiguration(launchConfiguration);
						
						// Create root sequential step
						fr.inria.diverse.trace.commons.model.trace.SequentialStep<«specificStepFQN», «stateFQN»> rootStep = «EcoreCraftingUtil.stringCreate(traceability.traceMMExplorer.getSpecificRootStepClass)»;
						traceRoot.setRootStep(rootStep);
						
						// Put in the resource
						traceResource.getContents().add(traceRoot);
						
						return traceRoot;
					}
					
					@Override
					public void save() {
						try {
							traceResource.save(null);
						} catch (java.io.IOException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void save(URI uri) {
						try {
							traceResource.setURI(uri);
							traceResource.save(null);
						} catch (java.io.IOException e) {
							e.printStackTrace();
						}
					}
				'''
	}
	
	private def String generateExeToFromTracedGenericMethods() {
		return
				'''
					«IF getExeToTracedUsed»
					private Collection<? extends EObject> «getExeToTracedMethodName»(Collection<? extends EObject> exeObjects, «getJavaFQN(stateClass)» newState) {
					Collection<EObject> result = new ArrayList<EObject>();
					for(EObject exeObject : exeObjects) {
						result.add(exeToTraced.get(exeObject));
					}
					return result;
					}	
					«ENDIF»
				'''
	}
	
	private def String generateTraceConstructorClass() {
		
		val body =
				'''
					public class «className» implements ITraceConstructor {
						«generateFields»
						«generateConstructor»
						«IF gemoc»
						«generateAddInitialStateMethod»
						«generateAddNewObjectToStateMethods»
						«generateAddStateUsingListenerMethods»
						«ELSE»
						«generateStoreAsTracedMethods»
						«generateAddStateMethods»
						«ENDIF»
						«generateAddStepMethods»
						«generateInitAndSaveTraceMethods»
						«generateGetAllResourcesMethod»
						«generateExeToFromTracedGenericMethods»
						
						@Override
						public boolean isPartialTraceConstructor() {
							return false;
						}
					}
				'''
		
		return
			'''
				package «packageQN»;
				
				«generateImports»
				
				«body»
			'''
	}

}
