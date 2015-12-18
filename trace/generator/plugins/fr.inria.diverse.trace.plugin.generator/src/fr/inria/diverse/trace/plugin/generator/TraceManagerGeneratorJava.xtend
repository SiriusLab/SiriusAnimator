package fr.inria.diverse.trace.plugin.generator

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

class TraceManagerGeneratorJava {

	// Inputs
	private val String className
	private val String packageQN
	private val EPackage traceMM
	private val EPackage abstractSyntax
	private val TraceMMGenerationTraceability traceability
	private val Set<GenPackage> refGenPackages
	private val boolean gemoc

	public def String getClassName() {
		return className
	}

	new(String languageName, String packageQN, EPackage traceMM, TraceMMGenerationTraceability traceability,
		Set<GenPackage> refGenPackages, boolean gemoc, EPackage abstractSyntax) {
		this.traceMM = traceMM
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "Manager"
		this.packageQN = packageQN
		this.traceability = traceability
		this.refGenPackages = refGenPackages
		this.gemoc = gemoc
		this.abstractSyntax = abstractSyntax
	}

	private def String getActualFQN(EClass c, Rule r) {
		val EOperation o = r.operation
		return EcoreCraftingUtil.getBaseFQN(c) + "." + o.name
	}

	private static def boolean isNotSuperTypeOf(EClass c, Collection<EClass> eclasses) {
		for (eclass : eclasses) {
			if (eclass.EAllSuperTypes.contains(c))
				return false
		}
		return true
	}
	
	private def String getTracedJavaFQN(EClassifier c) {
		if (c instanceof EClass) {
			val tracedClass = traceability.getTracedClass(c)
			if (tracedClass != null)
				return getJavaFQN(traceability.getTracedClass(c))
			else
				return getJavaFQN(c)
		} else {
			return getJavaFQN(c)
		}
	}
	
	private def String getJavaFQN(EClassifier c) {
		return EcoreCraftingUtil.getJavaFQN(c,refGenPackages)
	}
	
	private static def List<EClass> partialOrderSort (List<EClass> eclasses) {
		val List<EClass> result = new ArrayList<EClass>
		for (ci : eclasses) {
			if (result.isEmpty)
				result.add(ci)
			else {
				var boolean found = false
				for (var int i = 0; i < result.size && !found; i++) {
					val Set<EClass> followings = result.subList(i, result.size).toSet
					if (ci.isNotSuperTypeOf(followings)) {
						result.add(0, ci)
						found = true
					}
				}

				if (!found)
					result.add(ci)
			}
		}
		return result

	}
	
	/*
	private  def String getEOperationGetCode (Rule r) {
		val o = r.operation
		val eclass = r.containingClass
		val epackage = eclass.EPackage
		val res = '''«getJavaFQN(epackage)».«epackage.name.toFirstUpper»Package.eINSTANCE.get«eclass.name»__«o.name.toFirstUpper»()'''
		return res
	}
*/
 
	

	public def String generateCode() {
		val String code = generateTraceManagerClass()
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
			val potentialRealRuntimeClass = traceability.getMutableClass(f.EReferenceType)
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
		if (p instanceof EReference && traceability.hasTracedClass(p.EType as EClass))
			return '''((«getJavaFQN(traceability.getTracedClass(p.EType as EClass))»)exeToTraced.get(«javaVarName».«EcoreCraftingUtil.stringGetter(
				p)»))'''
		else
			return javaVarName + "." + EcoreCraftingUtil.stringGetter(p)
	}

	private def String stringGetterExeValue(String javaVarName, EStructuralFeature p) {
		if (p instanceof EReference && traceability.hasTracedClass(p.EType as EClass))
			return "((" + getJavaFQN(p.EType as EClass) + ")getTracedToExe(" + javaVarName + "." + EcoreCraftingUtil.stringGetter(p) + "))"
		else
			return javaVarName + "." + EcoreCraftingUtil.stringGetter(p)
	}

	private def Set<EClass> getConcreteSubtypesTraceClassOf(EClass tracedClass) {
		val Set<EClass> result = new HashSet()
		result.addAll(this.traceMM.eAllContents.filter(EClass).filter [ c |
			!c.abstract && c.EAllSuperTypes.contains(tracedClass)
		].toSet)
		if (!tracedClass.abstract)
			result.add(tracedClass)
		return result
	}

	private def Set<EStructuralFeature> getAllMutablePropertiesOf(EClass exeClass) {
		val Set<EStructuralFeature> res = new HashSet<EStructuralFeature>
		res.addAll(traceability.getMutablePropertiesOf(exeClass))
		res.addAll(exeClass.EAllSuperTypes.map[s|traceability.getMutablePropertiesOf(s)].flatten.toSet);
		return res
	}

	private def Set<EClass> getAllMutableClasses() {
		return traceability.allMutableClasses.filter[c|!c.allMutablePropertiesOf.empty].toSet
	}


	private def String generateImports() {
		return '''
import fr.inria.diverse.trace.api.IValueTrace;
import fr.inria.diverse.trace.api.impl.GenericValueTrace;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
«««import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.TreeIterator;
		'''
	}
	
	private def String generateFields() {
		return '''
		
	private  «getJavaFQN(traceability.traceMMExplorer.traceClass)» traceRoot;
	private  Resource executedModel;
	
	««« TODO one map per type? So that we can completely stop manipulationg eobjects
	private  Map<EObject, EObject> exeToTraced;
	
	private  «getJavaFQN(traceability.traceMMExplorer.getStateClass)» lastState;
	private List<IValueTrace> traces;

	private Resource traceResource;
	private Deque<«getJavaFQN(traceability.traceMMExplorer.stepClass)»> context = new LinkedList<«getJavaFQN(
			traceability.traceMMExplorer.stepClass)»>();
	private static final List<String> bigSteps = Arrays
			.asList(
				«FOR bigStepClass : traceability.getBigStepClasses SEPARATOR ","»
				"«bigStepClass.name»"
				«ENDFOR»
			);
		'''
	}
	
	private def String generateConstructor() {
		return '''
	public «className» (Resource exeModel, Resource traceResource) {
		this.traceResource = traceResource;
		this.executedModel = exeModel;
		this.traces = new ArrayList<IValueTrace>();
	}'''
	}


	private def String generateExeToFromTracedGenericMethods() {
		return '''
		private Collection<? extends EObject> getExeToTraced(Collection<? extends EObject> exeObjects) {
		Collection<EObject> result = new ArrayList<EObject>();
		for(EObject exeObject : exeObjects) {
			storeAsTracedObject(exeObject);
			result.add(exeToTraced.get(exeObject));
		}
		return result;
	}	
	
	private Collection<? extends EObject> getTracedToExe(
			Collection<? extends EObject> tracedObjects) {
		Collection<EObject> result = new ArrayList<EObject>();
		for (EObject tracedObject : tracedObjects) {
			result.add(getTracedToExe(tracedObject));
		}
		return result;
	}

	private EObject getTracedToExe(EObject tracedObject) {
		for (EObject key : exeToTraced.keySet()) {
			if (exeToTraced.get(key) == tracedObject)
				return key;
		}
		return null;
	}
		'''
	}
	
	private def String generateStoreAsTracedMethods() {
		return '''    «FOR mutClass : traceability.allMutableClasses.filter[c|!c.isAbstract]»

private void storeAsTracedObject(«getJavaFQN(mutClass)» o) {
			«val traced = traceability.getTracedClass(mutClass)»
		
			// First we find the traced object, and we create it if required
			«getJavaFQN(traced)» tracedObject;
			if (!exeToTraced.containsKey(o)) {
			tracedObject = «EcoreCraftingUtil.stringCreate(traced)»; 
			«val Set<EReference> origRefs1 = traceability.getRefs_originalObject(traced)»
			«FOR EReference origRef : origRefs1» 
			tracedObject.«EcoreCraftingUtil.stringSetter(origRef, "o")»;
			«ENDFOR»
			exeToTraced.put(o, tracedObject);
			traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_createTraceClassToTracedClass(traced))».add(tracedObject);
			
			«FOR p : getAllMutablePropertiesOf(mutClass)»
			«val EReference ptrace = traceability.getTraceOf(p)»
			traces.add(new GenericValueTrace(tracedObject.«EcoreCraftingUtil.stringGetter(ptrace)», this));
			«ENDFOR»
		}
	}
	
    «ENDFOR»

private void storeAsTracedObject(EObject o) {
 «FOR mutClass : partialOrderSort(traceability.allMutableClasses.filter[c|!c.isAbstract].toList) SEPARATOR "\n else "»
if (o instanceof «getJavaFQN(mutClass)») {
	storeAsTracedObject((«getJavaFQN(mutClass)»)o);
}
«ENDFOR»
}'''
	}

private def String generateAddStateMethods() {
	return '''
	
	@Override
	public boolean addStateIfChanged() {
		return addState(true);
	}

	@Override
	public void addState() {
		addState(false);
	}
	
	@SuppressWarnings("unchecked")
	private boolean addState(boolean onlyIfChange) {
		
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» newState = «EcoreCraftingUtil.stringCreate(
			traceability.traceMMExplorer.getStateClass)»;
		boolean changed = false;
		
		// We look at each object instance of a class with mutable properties 
		// Each of these objects should eventually become a traced object
		
			
		Set<Resource> allResources = new HashSet<>();
		allResources.add(executedModel);
		«IF gemoc»
		allResources.addAll(org.gemoc.commons.eclipse.emf.EMFResource.getRelatedResources(executedModel));
		«ENDIF»
		for (Resource r : allResources)
		for (TreeIterator<EObject> i = r.getAllContents(); i.hasNext();){
			EObject o = i.next();
		
		
			
			«FOR c : partialOrderSort(getAllMutableClasses.filter[c|!c.isAbstract].toList) SEPARATOR "\n else "»
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
				«FOR p : getAllMutablePropertiesOf(c)»
				«val EReference ptrace = traceability.getTraceOf(p)»
				«val EClass stateClass = ptrace.getEType as EClass»
				«incVar("localTrace")»
				«val EReference refGlobalToState = traceability.getStateClassToValueClass(p)»
				«incVar("previousValue")»
				«incVar("noChange")»


				// Then we compare the value of the field with the last stored value
				// If same value, we create no local state and we refer to the previous
				««« TODO to change if we switch from refering the exeMM to refering the AS (as in the ECMFA paper) -> need to compare to refs to origobjs/tracedobj
				««« TODO handle collections of datatypes
				List<«getJavaFQN(stateClass)»> «uniqueVar("localTrace")» = tracedObject.«EcoreCraftingUtil.stringGetter(ptrace)»;
				«getJavaFQN(stateClass)» «uniqueVar("previousValue")» = null;
				if (!«uniqueVar("localTrace")».isEmpty())
					«uniqueVar("previousValue")» = «uniqueVar("localTrace")».get(«uniqueVar("localTrace")».size() - 1);
				
				««« Case many
				«IF p.many»
				
					««« If instances of new class, we have to make sure that there are traced versions 
					«IF traceability.allMutableClasses.contains(p.EType)»
						
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
						java.util.Iterator<«getJavaFQN(p.EType)»> it = o_cast.«EcoreCraftingUtil.stringGetter(p)».iterator();
						for («getJavaFQN(traceability.getTracedClass(p.EType as EClass))» aPreviousValue : «uniqueVar("previousValue")»
								.«EcoreCraftingUtil.stringGetter(p)») {
							«getJavaFQN(p.EType)» aCurrentValue = it.next();
							if (aPreviousValue != exeToTraced.get(aCurrentValue)) {
								«uniqueVar("noChange")» = false;
								break;
							}
						}
						
						««« Else we simply check that the content is the same
						«ELSE»	
						«uniqueVar("noChange")» = «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)».containsAll(getExeToTraced(o_cast.«EcoreCraftingUtil.stringGetter(
			p)»));
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
					«IF traceability.allMutableClasses.contains(p.EType)»
					storeAsTracedObject(o_cast.«EcoreCraftingUtil.stringGetter(p)»);			
					«ENDIF»
					
					
					««« Getting the content of the field
					«incVar("content")»
					«««
					««« Case reference
					«IF p instanceof EReference»
					«getTracedJavaFQN(p.EType)» «uniqueVar("content")» = null;
					if (o_cast.«EcoreCraftingUtil.stringGetter(p)» != null)
						«uniqueVar("content")» = «stringGetterTracedValue("o_cast", p)»;
					«««
					««« Case datatype
					«ELSEIF p instanceof EAttribute» 
					«getJavaFQN(p.EType)» «uniqueVar("content")» = o_cast.«EcoreCraftingUtil.stringGetter(p)»;
					«ENDIF»
					««« end declaring/getting content
				
						
					boolean «uniqueVar("noChange")» = «uniqueVar("previousValue")» != null 
						&& «uniqueVar("previousValue")».«EcoreCraftingUtil.stringGetter(p)» == «uniqueVar("content")»;
						
					
				«ENDIF»
				««« end collection/single
					
					
					
				if («uniqueVar("noChange")») {
					newState.«EcoreCraftingUtil.stringGetter(refGlobalToState)».add(«uniqueVar("previousValue")»);

				} // Else we create one
				else {
					changed = true;
					«getJavaFQN(stateClass)» newValue = «EcoreCraftingUtil.stringCreate(stateClass)»;
					
					
					
					««« Case collection
					««« TODO: handle collections of datatypes!
					«IF p.many»
						 
						newValue.«EcoreCraftingUtil.stringGetter(p)».addAll((Collection<? extends «getJavaFQN(traceability.getTracedClass(p.EType as EClass))»>) getExeToTraced(o_cast.«EcoreCraftingUtil.stringGetter(
			p)»));
			
					««« Case single
					«ELSE»
					
						newValue.«EcoreCraftingUtil.stringSetter(p, uniqueVar("content"))»;
					
									
					«ENDIF»
					««« end collection/Single
					
					tracedObject.«EcoreCraftingUtil.stringGetter(ptrace)».add(newValue);
					newState.«EcoreCraftingUtil.stringGetter(refGlobalToState)».add(newValue);
				}
				
				«ENDFOR»
				}
			«ENDFOR»
			}
			
			boolean createNewState = lastState == null || (!onlyIfChange || changed);
			if (createNewState) {
				
				final «getJavaFQN(traceability.traceMMExplorer.stepClass)» currentStep = context.peekFirst();
				if (currentStep != null && currentStep instanceof «getJavaFQN(traceability.traceMMExplorer.bigStepClass)») {
					final «getJavaFQN(traceability.traceMMExplorer.stateClass)» startingState = lastState;
					final «getJavaFQN(traceability.traceMMExplorer.stateClass)» endingState = newState;
					addImplicitStep(currentStep, startingState, endingState);
				}
				
				lastState = newState;
				traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_TraceToStates)».add(lastState);
			}
			
			// Undoing the new state created for nothing
			else {
			
			newState.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StateToStep_started)».clear();
			newState.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StateToStep_ended)».clear();	
			
			«FOR p : traceability.allMutableProperties»
			«val EReference tuple = traceability.getStateClassToValueClass(p)»
			newState.«EcoreCraftingUtil.stringGetter(tuple)».clear();
			«ENDFOR»
			}
			
			return createNewState;
			
	}'''
}

private def String generateGoToMethods() {
	return '''
	@SuppressWarnings("unchecked")
	@Override
	public void goTo(EObject state) {
		
		if (state instanceof «getJavaFQN(traceability.traceMMExplorer.stateClass)») {
			«getJavaFQN(traceability.traceMMExplorer.stateClass)» stateToGo = («getJavaFQN(
			traceability.traceMMExplorer.stateClass)») state;

		«FOR p : traceability.allMutableProperties»
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		
		for («getJavaFQN(stateClass)» value : stateToGo.«EcoreCraftingUtil.stringGetter(
			TraceMMStrings.ref_createGlobalToState(stateClass))») {
				
				
		««« Case in which we can use the "originalObject" reference and simply set its values
		«IF p.eContainer instanceof ClassExtension»
			
			««« We have to test at runtime because we can't know at design time the type of the object containing the property
			««« The reason is that we keep the same class hierarchy in the trace. Maybe we should remove that. 
			«FOR concreteSubType : getConcreteSubtypesTraceClassOf(ptrace.getEContainingClass)»
			if (value.«EcoreCraftingUtil.stringGetter("parent")» instanceof «getJavaFQN(concreteSubType)») {
				«val Collection<EReference> origRefs = traceability.getRefs_originalObject(concreteSubType)»
				«getJavaFQN(concreteSubType)» parent_cast = («getJavaFQN(concreteSubType)») value.«EcoreCraftingUtil.stringGetter("parent")»;
				«IF !origRefs.isEmpty»
					«val EReference origRef = origRefs.get(0)»
					«IF p.many»
						parent_cast.«EcoreCraftingUtil.stringGetter(origRef)».«EcoreCraftingUtil.stringGetter(p)».clear();
						parent_cast.«EcoreCraftingUtil.stringGetter(origRef)».«EcoreCraftingUtil.stringGetter(p)».addAll((Collection<? extends «getJavaFQN(p.EType)»>) getTracedToExe(value.«EcoreCraftingUtil.stringGetter(
			p)»));
					«ELSE»
						«getJavaFQN(p.EType)» toset = «stringGetterExeValue("value", p)»;
						«getJavaFQN(p.EType)» current = ((«getJavaFQN((p.eContainer as ClassExtension).extendedExistingClass)»)parent_cast.«EcoreCraftingUtil.stringGetter(
			origRef)»).«EcoreCraftingUtil.stringGetter(p)»;
						if (current != toset)
							((«getJavaFQN((p.eContainer as ClassExtension).extendedExistingClass)»)parent_cast.«EcoreCraftingUtil.stringGetter(origRef)»).«EcoreCraftingUtil.stringSetter(
			p, "toset")»;
					«ENDIF»
				«ENDIF»
					
			}
			«ENDFOR»
			
		««« Case in which we have to recreate/restore execution objects in the model
		«ELSEIF p.eContainer instanceof EClass»
			«getJavaFQN(p.EContainingClass)» exeObject = («getJavaFQN(p.EContainingClass)») getTracedToExe(value.getParent());
			«IF p.many»
				exeObject.«EcoreCraftingUtil.stringGetter(p)».clear();
				exeObject.«EcoreCraftingUtil.stringGetter(p)».addAll((Collection<? extends «getJavaFQN(p.EType)»>) getTracedToExe(value.«EcoreCraftingUtil.stringGetter(p)»));
			«ELSE»
				exeObject.«EcoreCraftingUtil.stringSetter(p, stringGetterExeValue("value",p))»;
			«ENDIF»
			
		«ENDIF»  
			
			
			
		}
		
		

		«ENDFOR»
		} else {
			goToValue(state);
		}
	}

	@Override
	public void goTo(int stepNumber) {
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» stateToGo = traceRoot.«EcoreCraftingUtil.stringGetter(
			TraceMMStrings.ref_TraceToStates)».get(stepNumber);
		goTo(stateToGo);
	}
	
	private void goToValue(EObject value) {
		Object states = emfGet(value, "states");
		if (states != null) {
			if (states instanceof List<?>) {
				// We get the first state in which this value existed
				Object state = ((List<?>) states).get(0);
				if (state instanceof «getJavaFQN(traceability.traceMMExplorer.getStateClass)») {
					goTo((«getJavaFQN(traceability.traceMMExplorer.getStateClass)») state);
				}
			}
		}
	}'''
}

private def String generateGenericEMFHelperMethods() {
	return '''
	@SuppressWarnings("unchecked")
	private static void emfAdd(EObject o, String property, Object value) {
		for (EReference r : o.eClass().getEAllReferences()) {
			if (r.getName().equalsIgnoreCase(property)) {
				Object coll = o.eGet(r);
				if (coll instanceof Collection) {
					((Collection<Object>) coll).add(value);
					return;
				}
			}
		}
	}
	
	private static Object emfGet(EObject o, String property) {
		for (EReference r : o.eClass().getEAllReferences()) {
			if (r.getName().equalsIgnoreCase(property)) {
				return o.eGet(r);
			}
		}
		return null;
	}'''
}

private def String generateAddStepMethods() {
	return '''
	
	@Override
	public void addStep(String stepRule, Map<String, Object> params) {
		addStep(stepRule, params, this.getTraceSize()-1);
	}
	
	«««TODO how to get the parameters of the operation call? Not possible with current gemoc
	private void addStep(String stepRule, Map<String, Object> params, int stateIndex) {
		
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» toPush = null;
		
		if (stateIndex >= 0) {
		
		«getJavaFQN(traceability.traceMMExplorer.stateClass)» state = this.traceRoot.getStatesTrace().get(stateIndex);
		
		
		«val stepRules = traceability.mmext.rules»
		«IF !stepRules.empty»
		«FOR stepRule : stepRules SEPARATOR "else"»
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
			«FOR possibleCallerClass: possibleCallerClasses SEPARATOR " || "»
				stepRule.equalsIgnoreCase("«getActualFQN(possibleCallerClass, stepRule)»")
			«ENDFOR»
			) {
			«ENDIF»
			// First we create the step
			«getJavaFQN(stepClass)» «varName» = «EcoreCraftingUtil.stringCreate(stepClass)»;
			«varName».«EcoreCraftingUtil.stringSetter(TraceMMStrings.ref_StepToState_starting, "state")»;
			
			if (!context.isEmpty() && context.getFirst() != null){
				emfAdd(context.getFirst(), "«StepStrings.ref_BigStepToSub»", «varName»);
			} else {
				traceRoot.getRootSteps().add(«varName»);
			}
			toPush = «varName»;
			
			««« TODO if we want to use this method in the context of gemoc, need to fill the MSEOccurrence params with those from here
			«IF !gemoc»
			««« TODO rely on information in Rule instead of the structural features?
			«val properties = stepClass.EAllStructuralFeatures.filter[f|
			!traceability.traceMMExplorer.smallStepClass.EStructuralFeatures.contains(f) &&
				!traceability.traceMMExplorer.bigStepClass.EStructuralFeatures.contains(f) &&
				!traceability.traceMMExplorer.stepClass.EStructuralFeatures.contains(f) &&
				!f.name.equals(StepStrings.ref_BigStepToSub)
				&& !f.EContainingClass.name.equals("MSEOccurrence")]»
			«IF !properties.empty»
			if (params != null) {
				for (String k : params.keySet()) {
					
					switch(k) {
					«FOR p : properties»
					case "«p.name»":
						Object «uniqueVar("v")» = params.get(k);
						«val type = getEventParamRuntimeType(p)»
						if («uniqueVar("v")» instanceof «getJavaFQN(type)»)
							«IF type == p.EType»
							«varName».«EcoreCraftingUtil.stringSetter(p, "(" + getJavaFQN(p.EType) + ")"+uniqueVar("v"))»;
							«ELSE»
							«varName».«EcoreCraftingUtil.stringSetter(p, "(" + getJavaFQN(p.EType) + ")exeToTraced.get("+uniqueVar("v"+")"))»;
							«ENDIF»
					
						break;
					
					«incVar("v")»
					«ENDFOR»
						}
				}
			}
			«ENDIF»
			«ENDIF»

			// Then we add it to its trace
			this.traceRoot.«EcoreCraftingUtil.stringGetter(traceability.getStepSequence(stepClass))».add(«varName»);
			}
			«ENDFOR»
		
		
		«ENDIF»
		
		}

		context.push(toPush);
		
	}
	
	private void addImplicitStep(«getJavaFQN(traceability.traceMMExplorer.stepClass)» currentStep,
			«getJavaFQN(traceability.traceMMExplorer.stateClass)» startingState,
			«getJavaFQN(traceability.traceMMExplorer.stateClass)» endingState) {
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» implicitStep = null;
		«IF !stepRules.empty»
			«FOR bigStepClass : traceability.bigStepClasses SEPARATOR "else"»
				if (currentStep instanceof «getJavaFQN(bigStepClass)») {
					implicitStep = «EcoreCraftingUtil.stringCreateImplicitStep(bigStepClass)»;
				}
			«ENDFOR»
		«ENDIF»
		if (implicitStep != null) {
			implicitStep.setStartingState(startingState);
			implicitStep.setEndingState(endingState);
			emfAdd(currentStep, "subSteps", implicitStep);
		}
	}
	
	«IF gemoc»
	@Override
	public boolean addStep(org.gemoc.execution.engine.mse.engine_mse.MSEOccurrence mseOccurrence) {
		
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» step = null;
		
		if (mseOccurrence != null && mseOccurrence instanceof «getJavaFQN(traceability.traceMMExplorer.stepClass)») {
			
			step = («getJavaFQN(traceability.traceMMExplorer.stepClass)») mseOccurrence;
	
			// Creating generic (or almost generic) links
			«getJavaFQN(traceability.traceMMExplorer.stateClass)» state = this.traceRoot.getStatesTrace().get(this.getTraceSize()-1);
			step.setStartingState(state);
			if (!context.isEmpty() && context.getFirst() != null) {
				emfAdd(context.getFirst(), "subSteps", step);
			} else {
				traceRoot.getRootSteps().add(step);
			}
			
			// Adding step in its dedicated sequence/dimension
			«IF !stepRules.empty»
			«FOR stepRule : stepRules SEPARATOR "else"»
				«val EClass stepClass = traceability.getStepClassFromStepRule(stepRule)»
				«val String varName = stepClass.name.toFirstLower.replace(" ", "") + "Instance"»
				if (step instanceof «getJavaFQN(stepClass)») {
					«getJavaFQN(stepClass)» «varName» = («getJavaFQN(stepClass)») step;
					this.traceRoot.«EcoreCraftingUtil.stringGetter(traceability.getStepSequence(stepClass))».add(«varName»);
				}
				«ENDFOR»
			«ENDIF»
		}
		context.push(step);
		
		return (step != null);
	}
	
	«ENDIF»


	@Override
	public void endStep(String stepRule, Object returnValue) {
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» popped = context.pop();
		if (popped != null)
			popped.«EcoreCraftingUtil.stringSetter(TraceMMStrings.ref_StepToState_ending, "lastState")»;
	}'''
}

	private def String generateInitAndSaveTraceMethods() {
		return '''
		
	@Override
	public void initTrace() {
		// Create root
		this.traceRoot = «EcoreCraftingUtil.stringCreate(traceability.traceMMExplorer.traceClass)»;
		
		// Put in the resource
		traceResource.getContents().add(traceRoot);

		// Initializing the map exeobject -> tracedobject
		this.exeToTraced = new HashMap<EObject, EObject>();
	}

	@Override
	public void save() {
		//try {
		//	traceResource.save(null);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
		'''
	}
	
	private def String generateGetDescriptionMethods() {
		return '''
		
	@Override
	public String getDescriptionOfExecutionState(int index) {
		StringBuilder result = new StringBuilder();
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» gs = traceRoot.«EcoreCraftingUtil.stringGetter(
			TraceMMStrings.ref_TraceToStates)».get(index);
		
		«FOR p : traceability.allMutableProperties» 
		«val EReference refGlobalToState = traceability.getStateClassToValueClass(p)»
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		
		if (!gs.«EcoreCraftingUtil.stringGetter(refGlobalToState)».isEmpty())
			result.append("\n«p.name.toFirstUpper» values:");
		for («getJavaFQN(stateClass)» currenState : gs.«EcoreCraftingUtil.stringGetter(refGlobalToState)») {
			result.append("\n\t" + currenState.«EcoreCraftingUtil.stringGetter(p)»);
		}
		«ENDFOR»
		
	
		if (!gs.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StateToStep_started)».isEmpty()) {
			result.append("\n\nStarting steps: ");
			for («getJavaFQN(traceability.traceMMExplorer.stepClass)» m : gs.«EcoreCraftingUtil.stringGetter(
			TraceMMStrings.ref_StateToStep_started)») {
				result.append("\n\t" + m.eClass().getName());
				if (m.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StepToState_ending)» != null) {
					result.append(" (ends at state "+ traceRoot.getStatesTrace().indexOf(m.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_StepToState_ending)») +")");
				}
			}
		}
		
		result.deleteCharAt(0);
		return result.toString();
	}
	
		@Override
	public String getDescriptionOfValue(EObject eObject) {
		«FOR p : traceability.allMutableProperties SEPARATOR " else " AFTER " else "»
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		if (eObject instanceof «getJavaFQN(stateClass)») {
			return "«getJavaFQN(stateClass)»: "+ ((«getJavaFQN(stateClass)»)eObject).«EcoreCraftingUtil.stringGetter(p)»;			
		}
		«ENDFOR»
		return "ERROR";
	}
	
		'''
	}
	
	private def String generateStateQueryMethods() {
		return '''
	@Override
	public EObject getExecutionState(int index) {
		return traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_TraceToStates)».get(index);
	}


	@Override
	public int getTraceSize() {
		return traceRoot.«EcoreCraftingUtil.stringGetter(TraceMMStrings.ref_TraceToStates)».size();
	}
	
	
		
	@Override
	public int getNumberOfValueTraces() {
		return traces.size();
	}
	
	@Override
	public Set<EObject> getAllCurrentValues(int stateIndex) {
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» currentState = this.traceRoot.getStatesTrace().get(stateIndex);
		// We find all current values
		Set<EObject> currentValues = new HashSet<EObject>();
		if (currentState != null) {
			«FOR p : traceability.allMutableProperties»
			«val EReference refGlobalToState = traceability.getStateClassToValueClass(p)»
			currentValues.addAll(currentState.«EcoreCraftingUtil.stringGetter(refGlobalToState)»);
			«ENDFOR»
		}
		return currentValues;
	}
	
	public List<IValueTrace> getAllValueTraces() {
		return traces;
	}

	@Override
	public int getStateOrValueIndex(EObject stateOrValue) {
		int idx = traceRoot.getStatesTrace().indexOf(stateOrValue);
		if (idx == -1) {
			final Object states = emfGet(stateOrValue, "states");
			if (states != null) {
				if (states instanceof List<?>) {
					// We get the first state in which this value existed
					Object valueState = ((List<?>) states).get(0);
					if (valueState instanceof «getJavaFQN(traceability.traceMMExplorer.getStateClass)») {
						idx = traceRoot.getStatesTrace().indexOf(valueState);
					}
				}
			}
		}
		return idx;
	}'''
	}
	
	private def String generateStepQueryMethods() {
		return '''
	@Override
	public String currentBigStep() {
		if(!context.isEmpty() && context.getFirst() != null)
			return context.getFirst().eClass().getName();
		else
			return null;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<fr.inria.diverse.trace.api.IStep> getStackForwardAfterState(int stateIndex) {
		List<fr.inria.diverse.trace.api.IStep> result = new ArrayList<fr.inria.diverse.trace.api.IStep>();
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» currentState = this.traceRoot.getStatesTrace().get(stateIndex);

		// We start at the top of the forward stack, and we go downward
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» itStep = currentState.getStartedSteps().get(0);
		while (itStep != null) {
			«getJavaFQN(traceability.traceMMExplorer.stepClass)» itStep_prev = itStep;
			if (itStep instanceof «getJavaFQN(traceability.traceMMExplorer.stepClass)») {
				result.add(createGenericStep((«getJavaFQN(traceability.traceMMExplorer.stepClass)») itStep));
				List<«getJavaFQN(traceability.traceMMExplorer.stepClass)»> subSteps = (List<«getJavaFQN(traceability.traceMMExplorer.stepClass)»>) emfGet(itStep,
						"subSteps");
				if (subSteps != null) {
					itStep = subSteps.get(0);
				}
			}

			// If we didn't find anything new, we stop
			if (itStep == itStep_prev)
				itStep = null;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<fr.inria.diverse.trace.api.IStep> getStackBackward(int stateIndex) {
		List<fr.inria.diverse.trace.api.IStep> result = new ArrayList<fr.inria.diverse.trace.api.IStep>();
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» currentState = this.traceRoot.getStatesTrace().get(stateIndex);

		// We start at the top of the backward stack, and we go downward
		«getJavaFQN(traceability.traceMMExplorer.stepClass)» itStep = currentState.getEndedSteps().get(0);
		while (itStep != null) {
			«getJavaFQN(traceability.traceMMExplorer.stepClass)» itStep_prev = itStep;
			if (itStep instanceof «getJavaFQN(traceability.traceMMExplorer.stepClass)») {
				result.add(createGenericStep((«getJavaFQN(traceability.traceMMExplorer.stepClass)») itStep));
				List<«getJavaFQN(traceability.traceMMExplorer.stepClass)»> subSteps = (List<«getJavaFQN(traceability.traceMMExplorer.stepClass)»>) emfGet(itStep,
						"subSteps");
				if (subSteps != null) {
					itStep = subSteps.get(subSteps.size() - 1);
				}
			}

			// If we didn't find anything new, we stop
			if (itStep == itStep_prev)
				itStep = null;
		}
		return result;
	}
	
	@Override
	public List<fr.inria.diverse.trace.api.IStep> getStackForwardBeforeState(int stateIndex) {
		LinkedList<fr.inria.diverse.trace.api.IStep> result = new LinkedList<fr.inria.diverse.trace.api.IStep>();
		«getJavaFQN(traceability.traceMMExplorer.getStateClass)» currentState = this.traceRoot.getStatesTrace().get(stateIndex);
		List<«getJavaFQN(traceability.traceMMExplorer.stepClass)»> endedSteps = currentState.getEndedSteps();

		if (!endedSteps.isEmpty()) {
			final «getJavaFQN(traceability.traceMMExplorer.stepClass)» endedStep = endedSteps.get(0);
			if (endedStep.getStartingState() != currentState) {
				result.addFirst(createGenericStep(endedStep));
			}
			EObject itStep = endedStep.eContainer();
			while (itStep != null) {
				if (itStep instanceof «getJavaFQN(traceability.traceMMExplorer.stepClass)») {
					«getJavaFQN(traceability.traceMMExplorer.stepClass)» step = («getJavaFQN(traceability.traceMMExplorer.stepClass)») itStep;
					if (step.getStartingState() != currentState) {
						result.addFirst(createGenericStep(step));
					}
					itStep = itStep.eContainer();
				} else {
					itStep = null;
				}
			}
		}
		return result;
	}
	
	@Override
	public List<fr.inria.diverse.trace.api.IStep.StepEvent> getEventsForState(int stateIndex) {
		final Map<«getJavaFQN(traceability.traceMMExplorer.stepClass)», fr.inria.diverse.trace.api.IStep> step2IStep = new HashMap<>();

		final «getJavaFQN(traceability.traceMMExplorer.getStateClass)» currentState = this.traceRoot.getStatesTrace().get(stateIndex);

		final List<fr.inria.diverse.trace.api.IStep> endedSteps = currentState.getEndedSteps().stream().map(s -> {
			return step2IStep.computeIfAbsent(s, k -> createGenericStep(k));
		}).collect(Collectors.toList());

		final List<fr.inria.diverse.trace.api.IStep> startedSteps = currentState.getStartedSteps().stream().map(s -> {
			return step2IStep.computeIfAbsent(s, k -> createGenericStep(k));
		}).collect(Collectors.toList());

		final List<fr.inria.diverse.trace.api.IStep.StepEvent> events = new ArrayList<>();
		final LinkedList<fr.inria.diverse.trace.api.IStep> virtualStack = new LinkedList<>();

		final Iterator<fr.inria.diverse.trace.api.IStep> itEnding = endedSteps.iterator();
		final Iterator<fr.inria.diverse.trace.api.IStep> itStarting = startedSteps.iterator();

		while (itEnding.hasNext()) {
			// As long as we have ending steps
			final fr.inria.diverse.trace.api.IStep endedIStep = itEnding.next();
			if (endedIStep.getStartingIndex() != stateIndex) {
				events.add(new fr.inria.diverse.trace.api.IStep.StepEvent(endedIStep, false));
			} else {
				if (virtualStack.peek() == endedIStep) {
					// A start event on this step has already been treated,
					// we thus can treat this end event.
					events.add(new fr.inria.diverse.trace.api.IStep.StepEvent(endedIStep, false));
					virtualStack.pop();
				} else {
					while (virtualStack.peek() != endedIStep && itStarting.hasNext()) {
						// Pushing steps that start onto the stack until we pushed the one
						// we are trying to pop or we run out of steps.
						final fr.inria.diverse.trace.api.IStep startedIStep = itStarting.next();
						virtualStack.push(startedIStep);
						events.add(new fr.inria.diverse.trace.api.IStep.StepEvent(startedIStep, true));
					}
					if (virtualStack.peek() == endedIStep) {
						// We try again.
						events.add(new fr.inria.diverse.trace.api.IStep.StepEvent(endedIStep, false));
						virtualStack.pop();
					} else {
						// If it failed again, throw an exception. Not supposed to happen.
						throw new IllegalStateException();
					}
				}
			}
		}
		
		while (itStarting.hasNext()) {
			final fr.inria.diverse.trace.api.IStep startedIStep = itStarting.next();
			events.add(new fr.inria.diverse.trace.api.IStep.StepEvent(startedIStep, true));
		}

		return events;
	}

	««« TODO in the context of gemoc, a GemocGenericStep could be created instead, which would not contain any params (already in the MSEOccurrence/Step object, generically)
	private fr.inria.diverse.trace.api.IStep generateStep(«getJavaFQN(traceability.traceMMExplorer.stepClass)» step) {
		fr.inria.diverse.trace.api.IStep result = null;
		
		fr.inria.diverse.trace.api.IStep parentStep = null;
		if (step.eContainer() instanceof «getJavaFQN(traceability.traceMMExplorer.stepClass)») {
			parentStep = createGenericStep((«getJavaFQN(traceability.traceMMExplorer.stepClass)») step.eContainer()); 
		}
		
		«FOR Rule r : this.traceability.mmext.rules SEPARATOR "else" »
		«val stepClass = this.traceability.getStepClassFromStepRule(r)»
		if (step instanceof «getJavaFQN(stepClass)») {
			«getJavaFQN(stepClass)» step_cast =  («getJavaFQN(stepClass)») step;
			int startIndex = this.traceRoot.getStatesTrace().indexOf(step.getStartingState());
			int endIndex = this.traceRoot.getStatesTrace().indexOf(step.getEndingState());
			
			result = new fr.inria.diverse.trace.api.impl.GenericStep("«getJavaFQN(r.containingClass)»", "«r.operation.name»",startIndex,endIndex,parentStep);
			«IF r.containingClass != null»
				result.addParameter("caller", (step_cast.getCaller()));
			«ENDIF»
			result.addParameter("this", step);
			
			«FOR a : r.operation.EParameters»
				««« TODO
			«ENDFOR»
		}
		«ENDFOR»
		else
		«FOR implicitStepClass : this.traceability.implicitStepClasses SEPARATOR "else" »
		if (step instanceof «getJavaFQN(implicitStepClass)») {
			int startIndex = this.traceRoot.getStatesTrace().indexOf(step.getStartingState());
			int endIndex = this.traceRoot.getStatesTrace().indexOf(step.getEndingState());
			
			result = new fr.inria.diverse.trace.api.impl.GenericStep("«getJavaFQN(traceability.getImplicitStepContainingClass(implicitStepClass))»", "implicitStep",startIndex,endIndex,parentStep);
			result.addParameter("this", step);
		}
		«ENDFOR»
		
		return result;
	}
	
	private fr.inria.diverse.trace.api.IStep createGenericStep(«getJavaFQN(traceability.traceMMExplorer.stepClass)» step) {
		return new fr.inria.diverse.trace.api.impl.LazyGenericStep(() -> {
			return generateStep(step);
		});
		
	}
	
	'''
	}	
	
	private def String generateTraceManagerClass() {
		return '''package «packageQN»;
		
		«generateImports»

public class «className» implements «IF gemoc» fr.inria.diverse.trace.gemoc.api.IGemocTraceManager «ELSE» ITraceManager «ENDIF»{

	«generateFields»
	«generateConstructor»
	«generateAddStateMethods»
	«generateAddStepMethods»
	«generateGoToMethods»
	«generateInitAndSaveTraceMethods»
	«generateGetDescriptionMethods»
	«generateStoreAsTracedMethods»	
	«generateExeToFromTracedGenericMethods»
	«generateGenericEMFHelperMethods»
	«generateStateQueryMethods»
	«generateStepQueryMethods»
}
		'''
	}

}