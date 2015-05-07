package fr.inria.diverse.trace.plugin.generator

import ecorext.ClassExtension
import fr.inria.diverse.trace.commons.CodeGenUtil
import fr.inria.diverse.trace.metamodel.generator.TraceMMGenerationTraceability
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import fr.inria.diverse.trace.metamodel.generator.TraceMMStrings
import fr.inria.diverse.trace.plaink3.tracematerialextractor.Plaink3MaterialStrings
import java.util.Collection
import java.util.HashSet

class TraceManagerGeneratorJava {

	// Inputs
	private val String className
	private val String packageQN
	private val EPackage traceMM
	private val EPackage executionMM
	private val TraceMMGenerationTraceability traceability
	private val List<GenPackage> refGenPackages

	public def String getClassName() {
		return className
	}

	new(String languageName, String packageQN, EPackage traceMM, EPackage executionMM,
		TraceMMGenerationTraceability traceability, List<GenPackage> refGenPackages) {
		this.traceMM = traceMM
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "Manager"
		this.packageQN = packageQN
		this.executionMM = executionMM
		this.traceability = traceability
		this.refGenPackages = refGenPackages
	}

	private def String baseGetFQN(EClassifier c) {
		val EPackage p = c.getEPackage
		if(p != null) {
			return getEPackageFQN(p) + "." + c.name
		} else {
			return c.name
		}
	}

	private def String getFQN(EClassifier c) {
		var String base = ""
		val gc = getGenClassifier(c)
		if(gc != null) {
			if(gc.genPackage.basePackage != null) {
				base = gc.genPackage.basePackage + "."
			}
		}
		return base + baseGetFQN(c);
	}

	private def String getEClassFQN(EClass c) {
		return getFQN(c)
	}

	private def GenClassifier getGenClassifier(EClassifier c) {
		if(c != null) {
			for (gp : refGenPackages) {
				for (gc : gp.eAllContents.filter(GenClassifier).toSet) {
					val ecoreClass = gc.ecoreClassifier
					if(ecoreClass != null) {
						val s1 = baseGetFQN(ecoreClass)
						val s2 = baseGetFQN(c)
						if(s1 != null && s2 != null && s1.equals(s2)) {
							return gc
						}
					}
				}
			}

		}
		return null

	}

	private def String getEPackageFQN(EPackage p) {
		val EPackage superP = p.getESuperPackage
		if(superP != null) {
			return getEPackageFQN(superP) + "." + p.name
		} else {
			return p.name
		}
	}

	private def String stringCreate(EClass c) {
		val EPackage p = c.EPackage
		return getEPackageFQN(p) + "." + p.name.toFirstUpper + "Factory.eINSTANCE.create" + c.name + "()"
	}

	private def String stringGetter(EStructuralFeature f) {
		if(f instanceof EAttribute) {
			if(f.EAttributeType.name.equals("EBoolean")) {
				return "is" + f.name.toFirstUpper + "()"
			}
		}
		return "get" + f.name.toFirstUpper + "()"
	}

	private def String stringGetter(String s) {
		return "get" + s.toFirstUpper + "()"
	}

	private def stringSetter(EStructuralFeature f, String value) {
		return "set" + f.name.toFirstUpper + "(" + value + ")"
	}

	private def stringSetter(String f, String value) {
		return "set" + f.toFirstUpper + "(" + value + ")"
	}

	public def String generateCode() {
		val String code = generateUglyCode()
		try {
			return CodeGenUtil.formatJavaCode(code)
		} catch(Throwable t) {
			return code
		}

	}

	private Map<String, Integer> counters = new HashMap

	private def String uniqueVar(String s) {
		if(!counters.containsKey(s)) {
			counters.put(s, 0)
		}
		return s + counters.get(s)
	}

	private def void incVar(String s) {
		if(!counters.containsKey(s)) {
			counters.put(s, 0)
		}
		counters.put(s, counters.get(s) + 1)
	}

	private def EClassifier getEventParamRuntimeType(EStructuralFeature f) {
		var EClass res = null
		if(f instanceof EAttribute) {
			//TODO
		} else if(f instanceof EReference) {
			val potentialRealRuntimeClass = traceability.getMutableClass(f.EReferenceType)
			if(potentialRealRuntimeClass != null) {

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
		if(p instanceof EReference && traceability.hasTracedClass(p.EType as EClass))
			return "((" + getFQN(traceability.getTracedClass(p.EType as EClass)) + ")exeToTraced.get("+javaVarName +"."+ stringGetter(p) + "))"
		else
			return javaVarName+"." + stringGetter(p)
	}
	
	private def String stringGetterExeValue(String javaVarName, EStructuralFeature p) {
		if(p instanceof EReference && traceability.hasTracedClass(p.EType as EClass))
			return "((" + getFQN(p.EType as EClass) + ")getTracedToExe("+javaVarName +"."+ stringGetter(p) + "))"
		else
			return javaVarName+"." + stringGetter(p)
	}
	
		
	private def Set<EClass> getConcreteSubtypesTraceClassOf(EClass tracedClass) {
		val Set<EClass> result = new HashSet()
		result.addAll(this.traceMM.eAllContents.filter(EClass).filter[c|!c.abstract && c.EAllSuperTypes.contains(tracedClass)].toSet)
		if (!tracedClass.abstract)
			result.add(tracedClass)
		return result 
	} 

	private def String generateUglyCode() {
		return '''package «packageQN»;


import fr.inria.diverse.trace.api.ITraceManager;
import fr.inria.diverse.trace.api.IValueTrace;
import fr.inria.diverse.trace.api.impl.GenericValueTrace;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.TreeIterator;

public class «className» implements ITraceManager {

	private  «getEClassFQN(traceability.traceMMExplorer.traceClass)» traceRoot;
	private  «getEClassFQN(traceability.traceMMExplorer.tracedObjectsClass)» tracedObjects;
	private  «getEClassFQN(traceability.traceMMExplorer.eventsClass)» events;
	private  Resource executedModel;
	private  Map<EObject, EObject> exeToTraced;
	private  «getEClassFQN(traceability.traceMMExplorer.globalStateClass)» lastState;
	private  «getEClassFQN(traceability.traceMMExplorer.globalStateClass)» currentState;
	private List<IValueTrace> traces;

	private Resource traceResource;
	private Deque<«getFQN(traceability.traceMMExplorer.macroEventClass)»> context = new ArrayDeque<«getFQN(
			traceability.traceMMExplorer.macroEventClass)»>();
	private static final List<String> macroEvents = Arrays
			.asList(
				«FOR macroEventClass : traceability.macroEventClasses SEPARATOR ","»
				"«macroEventClass.name»"
				«ENDFOR»
			);
	

	public «className» (Resource exeModel, Resource traceResource) {
		this.traceResource = traceResource;
		this.executedModel = exeModel;
		this.traces = new ArrayList<IValueTrace>();
	}
	
	private Collection<? extends EObject> getExeToTraced(Collection<? extends EObject> exeObjects) {
		Collection<EObject> result = new ArrayList<EObject>();
		for(EObject exeObject : exeObjects) {
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


	 @SuppressWarnings("unchecked")
	private boolean addState(boolean onlyIfChange) {
		
		«getFQN(traceability.traceMMExplorer.globalStateClass)» newState = «stringCreate(
			traceability.traceMMExplorer.globalStateClass)»;
		boolean changed = false;
		
		// We look at each object instance of a class with mutable properties 
		// Each of these objects should eventually become a traced object
		
		
		for (TreeIterator<EObject> i = executedModel.getAllContents(); i.hasNext();){
			EObject o = i.next();
		
			«FOR c : traceability.runtimeClasses.filter[c|!c.isAbstract]»
			«val traced = traceability.getTracedClass(c)»

			/**
			 * Storing the state of a «getEClassFQN(c)» object
			 */
			if (o instanceof «getEClassFQN(c)») {

				«getEClassFQN(c)» o_cast = («getEClassFQN(c)») o;

				// First we find the traced object, and we create it if required
				«getEClassFQN(traced)» tracedObject;
				if (!exeToTraced.containsKey(o)) {
					tracedObject = «stringCreate(traced)»;
					«val Set<EReference> origRefs = traceability.getRefs_originalObject(traced)»
					«FOR EReference origRef : origRefs» 
					tracedObject.«stringSetter(origRef, "o_cast")»;
					«ENDFOR»
					exeToTraced.put(o, tracedObject);
					tracedObjects.«stringGetter(TraceMMStrings.ref_createTracedObjectsToTrace(traced))».add(tracedObject);
					«FOR p : traceability.getMutablePropertiesOf(c)»
					«val EReference ptrace = traceability.getTraceOf(p)»
					traces.add(new GenericValueTrace(tracedObject.«stringGetter(ptrace)», this));
					«ENDFOR»
				} else {
					tracedObject = («getEClassFQN(traced)») exeToTraced.get(o);
				}
				«FOR p : traceability.getMutablePropertiesOf(c)»
				«val EReference ptrace = traceability.getTraceOf(p)»
				«val EClass stateClass = ptrace.getEType as EClass»
				«val EReference refGlobalToState = traceability.getGlobalToState(p)»
				«incVar("localTrace")»
				«incVar("previousValue")»
				«incVar("noChange")»

				// Then we compare the value of the field with the last stored value
				// If same value, we create no local state and we refer to the previous
				««« TODO to change if we switch from refering the exeMM to refering the AS (as in the ECMFA paper) -> need to compare to refs to origobjs/tracedobj
				««« TODO handle collections of datatypes
				List<«getEClassFQN(stateClass)»> «uniqueVar("localTrace")» = tracedObject.«stringGetter(ptrace)»;
				«getEClassFQN(stateClass)» «uniqueVar("previousValue")» = null;
				if (!«uniqueVar("localTrace")».isEmpty())
					«uniqueVar("previousValue")» = «uniqueVar("localTrace")».get(«uniqueVar("localTrace")».size() - 1);
					
				«IF p.many»
				boolean «uniqueVar("noChange")»= true;
				if («uniqueVar("previousValue")» != null) {

					if («uniqueVar("previousValue")».«stringGetter(p)».size() == o_cast
							.«stringGetter(p)».size()) {

						«IF p.ordered»
						java.util.Iterator<«getFQN(p.EType)»> it = o_cast.«stringGetter(p)».iterator();
						for («getFQN(traceability.getTracedClass(p.EType as EClass))» aPreviousValue : «uniqueVar("previousValue")»
								.«stringGetter(p)») {
							«getFQN(p.EType)» aCurrentValue = it.next();
							if (aPreviousValue != exeToTraced.get(aCurrentValue)) {
								«uniqueVar("noChange")» = false;
								break;
							}
						}
						
						«ELSE»	
						«uniqueVar("noChange")» = «uniqueVar("previousValue")».«stringGetter(p)».containsAll(getExeToTraced(o_cast.«stringGetter(p)»));
						«ENDIF»

					} else {
						«uniqueVar("noChange")» = false;
					}
				}
					
				
				«ELSE»
				boolean «uniqueVar("noChange")» = «uniqueVar("previousValue")» != null && «uniqueVar("previousValue")».«stringGetter(p)» == «stringGetterTracedValue("o_cast",p)»;
				«ENDIF»
					
				if («uniqueVar("noChange")») {
					newState.«stringGetter(refGlobalToState)».add(«uniqueVar("previousValue")»);

				} // Else we create one
				else {
					changed = true;
					«getEClassFQN(stateClass)» newValue = «stringCreate(stateClass)»;
					«IF p.many»
						««« TODO: handle collections of datatypes! 
						newValue.«stringGetter(p)».addAll((Collection<? extends «getFQN(traceability.getTracedClass(p.EType as EClass))»>) getExeToTraced(o_cast.«stringGetter(p)»));
					«ELSE»
						newValue.«stringSetter(p, stringGetterTracedValue("o_cast",p))»;
					«ENDIF»
					tracedObject.«stringGetter(ptrace)».add(newValue);
					newState.«stringGetter(refGlobalToState)».add(newValue);
				}
				
				«ENDFOR»
				}
			«ENDFOR»
			}
			
			boolean createNewState = lastState == null || (!onlyIfChange || changed);
			if (createNewState) {
				lastState = newState;
				currentState = lastState;
				traceRoot.«stringGetter(TraceMMStrings.ref_SystemToGlobal)».add(lastState);
			}
			
			// Undoing the new state created for nothing
			else {
			
			newState.getStartedBigSteps().clear();	
			newState.getEndedBigSteps().clear();
			newState.setFollowingStep(null);
		
			«FOR p : traceability.allMutableProperties»
			«val EReference tuple = traceability.getGlobalToState(p)»
			newState.«stringGetter(tuple)».clear();
			«ENDFOR»
			}
			
			return createNewState;
			
	}
			
			

	@SuppressWarnings("unchecked")
	@Override
	public void goTo(EObject state) {
		
		if (state instanceof «getEClassFQN(traceability.traceMMExplorer.globalStateClass)») {
			«getEClassFQN(traceability.traceMMExplorer.globalStateClass)» stateToGo = («getEClassFQN(
			traceability.traceMMExplorer.globalStateClass)») state;

		«FOR p : traceability.allMutableProperties»
		«var String pResolvedValue»
		««« TODO resolve the value so that we use/create a real counterpart to the traced object
		
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		
		
		
		for («getEClassFQN(stateClass)» value : stateToGo.«stringGetter(
			TraceMMStrings.ref_createGlobalToState(stateClass))») {
				
				
		««« Case in which we can use the "originalObject" reference and simply set its values
		«IF p.eContainer instanceof ClassExtension»
			
			««« We have to test at runtime because we can't know at design time the type of the object containing the property
			««« The reason is that we keep the same class hierarchy in the trace. Maybe we should remove that. 
			«FOR concreteSubType : getConcreteSubtypesTraceClassOf(ptrace.getEContainingClass)»
			if (value.«stringGetter("parent")» instanceof «getFQN(concreteSubType)») {
				«val Collection<EReference> origRefs = traceability.getRefs_originalObject(concreteSubType)»
				«getFQN(concreteSubType)» parent_cast = («getFQN(concreteSubType)») value.«stringGetter("parent")»;
				«IF !origRefs.isEmpty»
					«val EReference origRef = origRefs.get(0)»
					«IF p.many»
						parent_cast.«stringGetter(origRef)».«stringGetter(p)».addAll((Collection<? extends «getFQN(p.EType)»>) getTracedToExe(value.«stringGetter(p)»));
					«ELSE»

						parent_cast.«stringGetter(
						origRef)».«stringSetter(p, stringGetterExeValue("value",p))»;
					«ENDIF»
				«ENDIF»
					
			}
			«ENDFOR»
			
		««« Case in which we have to recreate/restore execution objects in the model
		«ELSEIF p.eContainer instanceof EClass»
			«getFQN(p.EContainingClass)» exeObject = («getFQN(p.EContainingClass)») getTracedToExe(value.getParent());
			«IF p.many»
				exeObject.«stringGetter(p)».addAll((Collection<? extends «getFQN(p.EType)»>) getTracedToExe(value.«stringGetter(p)»));
			«ELSE»
				exeObject.«stringSetter(p, stringGetterExeValue("value",p))»;
			«ENDIF»
			
		«ENDIF»  
			
			
			
		}
		
		

		«ENDFOR»
		currentState = stateToGo;
		} else {
			goToValue(state);
		}
	}

	@Override
	public void goTo(int stepNumber) {
		«getEClassFQN(traceability.traceMMExplorer.globalStateClass)» stateToGo = traceRoot.«stringGetter(
			TraceMMStrings.ref_SystemToGlobal)».get(stepNumber);
		goTo(stateToGo);
	}
	
	private void goToValue(EObject value) {
		Object states = emfGet(value, "states");
		if (states != null) {
			if (states instanceof List<?>) {
				// We get the first state in which this value existed
				Object state = ((List<?>) states).get(0);
				if (state instanceof «getEClassFQN(traceability.traceMMExplorer.globalStateClass)») {
					goTo((«getEClassFQN(traceability.traceMMExplorer.globalStateClass)») state);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void emfAdd(EObject o, String property, Object value) {
		for (EReference r : o.eClass().getEAllReferences()) {
			if (r.getName().equals(property)) {
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
			if (r.getName().equals(property)) {
				return o.eGet(r);
			}
		}
		return null;
	}
	
	
	@Override
	public void retroAddEvent(String eventName, Map<String, Object> params) {
		addEvent(eventName, params, this.getTraceSize()-2);
		
	}

	
	@Override
	public void addEvent(String eventName, Map<String, Object> params) {
		addEvent(eventName, params, this.getTraceSize()-1);
	}
	
	/**
	 * TODO how to get the parameters of the operation call? Not possible with current gemoc
	 * TRACE MM DEPENDENT
	 */
	private void addEvent(String eventName, Map<String, Object> params, int stateIndex) {
		
		if (stateIndex >= 0) {
		
		«getEClassFQN(traceability.traceMMExplorer.globalStateClass)» state = this.traceRoot.getStatesTrace().get(stateIndex);
		
		«IF !traceability.eventClasses.empty»
				
		switch(eventName) {
			
			«FOR e : traceability.eventClasses.filter[c|!c.abstract]»

			«val String varName = e.name.toFirstLower.replace(" ", "") + "Instance"»
		
			case "«e.name»":
			
			// First we create the event
			«getEClassFQN(e)» «varName» = «stringCreate(e)»;
			«IF traceability.macroEventClasses.contains(e)»
			«varName».«stringSetter(TraceMMStrings.ref_BigStepToState_starting, "state")»;
			«ELSE»
			«varName».«stringSetter(TraceMMStrings.ref_EventToGlobal, "state")»;
			«ENDIF»
			
			// TODO only generate this code is the event is indeed potentially part of a macro event
			if (!context.isEmpty()){
				emfAdd(context.getFirst(), "«Plaink3MaterialStrings.ref_BigStepToSub»", «varName»);
			}
			«IF traceability.macroEventClasses.contains(e)»
			context.push(«varName»);
			«ENDIF»
			«val properties = e.EAllStructuralFeatures.filter[f|
			!traceability.traceMMExplorer.eventOccClass.EStructuralFeatures.contains(f) &&
				!traceability.traceMMExplorer.macroEventClass.EStructuralFeatures.contains(f)
			// TODO store the subEvents string somewhere else?
				&& !f.name.equals(Plaink3MaterialStrings.ref_BigStepToSub)]»
			«IF !properties.empty»
			if (params != null) {
				for (String k : params.keySet()) {
					
					switch(k) {
					«FOR p : properties»
					case "«p.name»":
						Object v = params.get(k);
						«val type = getEventParamRuntimeType(p)»
						if (v instanceof «getFQN(type)»)
							«IF type == p.EType»
							«varName».«stringSetter(p, "(" + getFQN(p.EType) + ")v")»;
							«ELSE»
							«varName».«stringSetter(p, "(" + getFQN(p.EType) + ")exeToTraced.get(v)")»;
							«ENDIF»
					
						break;
					
				
					«ENDFOR»
						}
				}
			}
			«ENDIF»

			// Then we add it to its trace
			this.events.«stringGetter(traceability.getEventTrace(e))».add(«varName»);
			break;
			«ENDFOR»
		}
		
		«ENDIF»
		}
	}


	@Override
	public void endEvent(String eventName, Object returnValue) {
		if (isMacro(eventName)) {
			context.pop().«stringSetter(traceability.traceMMExplorer.ref_BigStepToState_ending, "lastState")»;
		}
	}
	
	
	@Override
	public void initTrace() {
		// Create root
		this.traceRoot = «stringCreate(traceability.traceMMExplorer.traceClass)»;
		
		// Put in the resource
		traceResource.getContents().add(traceRoot);

		// Create objects storage
		this.tracedObjects = «stringCreate(traceability.traceMMExplorer.tracedObjectsClass)»;
		this.traceRoot.«stringSetter(TraceMMStrings.ref_SystemToTracedObjects, "tracedObjects")»;

		// Create events storage
		this.events = «stringCreate(traceability.traceMMExplorer.eventsClass)»;
		this.traceRoot.«stringSetter(TraceMMStrings.ref_SystemToEvents, "events")»;

		// Initializing the map exeobject -> tracedobject
		this.exeToTraced = new HashMap<EObject, EObject>();
	}

	@Override
	public void save() {
		try {
			traceResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public EObject getExecutionState(int index) {
		return traceRoot.«stringGetter(TraceMMStrings.ref_SystemToGlobal)».get(index);
	}

	@Override
	public String getDescriptionOfExecutionState(int index) {
		StringBuilder result = new StringBuilder();
		«getEClassFQN(traceability.traceMMExplorer.globalStateClass)» gs = traceRoot.«stringGetter(
			TraceMMStrings.ref_SystemToGlobal)».get(index);
		
		«FOR p : traceability.allMutableProperties» 
		«val EReference refGlobalToState = traceability.getGlobalToState(p)»
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		
		result.append("\n«p.name.toFirstUpper» values:");
		for («getEClassFQN(stateClass)» currenState : gs.«stringGetter(refGlobalToState)») {
			result.append("\n\t" + currenState.«stringGetter(p)»);
		}
		«ENDFOR»
		
		//TODO instead of generic display, use instanceof and access to event parameters
		if(gs.«stringGetter(traceability.traceMMExplorer.ref_StateToSmallStep)» != null)
			result.append("\n\nFollowing small step: "+gs.«stringGetter(traceability.traceMMExplorer.ref_StateToSmallStep)».eClass().getName());
		if (!gs.«stringGetter(traceability.traceMMExplorer.ref_StateToBigStep_ended)».isEmpty()) {
			result.append("\n\nFinished big steps: ");
			for («getEClassFQN(traceability.traceMMExplorer.macroEventClass)» m : gs.«stringGetter(
			traceability.traceMMExplorer.ref_StateToBigStep_ended)») {
				result.append("\n\t" + m.eClass().getName());
				result.append(" (began at state "
						+ traceRoot.getStatesTrace().indexOf(
								m.«stringGetter(traceability.traceMMExplorer.ref_BigStepToState_starting)») + ")");
			}
		}
		if (!gs.«stringGetter(traceability.traceMMExplorer.ref_StateToBigStep_started)».isEmpty()) {
			result.append("\n\nStarting big steps: ");
			for («getEClassFQN(traceability.traceMMExplorer.macroEventClass)» m : gs.«stringGetter(
			traceability.traceMMExplorer.ref_StateToBigStep_started)») {
				result.append("\n\t" + m.eClass().getName());
				if (m.«stringGetter(traceability.traceMMExplorer.ref_BigStepToState_ending)» != null) {
					result.append(" (ends at state "+ traceRoot.getStatesTrace().indexOf(m.«stringGetter(
			traceability.traceMMExplorer.ref_BigStepToState_ending)») +")");
				}
			}
		}
		
		result.deleteCharAt(0);
		return result.toString();
	}

	@Override
	public int getTraceSize() {
		return traceRoot.«stringGetter(TraceMMStrings.ref_SystemToGlobal)».size();
	}
	
	@Override
	public boolean isMacro(String string) {
		return macroEvents.contains(string);
	}

	@Override
	public boolean addStateIfChanged() {
		return addState(true);
	}

	@Override
	public void addState() {
		addState(false);
	}
	
	
	@Override
	public String currentMacro() {
		if(!context.isEmpty())
			return context.getFirst().eClass().getName();
		else
			return null;
	}
	
	
	@Override
	public int getNumberOfValueTraces() {
		return getAllValueTraces().size();
	}
	
	@Override
	public Set<EObject> getAllCurrentValues() {
		// We find all current values
		Set<EObject> currentValues = new HashSet<EObject>();
		if (currentState != null) {
			«FOR p : traceability.allMutableProperties»
			«val EReference refGlobalToState = traceability.getGlobalToState(p)»
			currentValues.addAll(currentState.«stringGetter(refGlobalToState)»);
			«ENDFOR»
		}
		return currentValues;
	}
	
	public List<IValueTrace> getAllValueTraces() {
		return traces;
	}

	@Override
	public String getDescriptionOfValue(EObject eObject) {
		«FOR p : traceability.allMutableProperties SEPARATOR " else " AFTER " else "»
		«val EReference ptrace = traceability.getTraceOf(p)»
		«val EClass stateClass = ptrace.getEType as EClass»
		if (eObject instanceof «getEClassFQN(stateClass)») {
			return "«getEClassFQN(stateClass)»: "+ ((«getEClassFQN(stateClass)»)eObject).«stringGetter(p)»;			
		}
		«ENDFOR»
		return "ERROR";
	}
	
	@Override
	public int getCurrentIndex() {
		return traceRoot.getStatesTrace().indexOf(currentState);
	}

}
		'''
	}

}
