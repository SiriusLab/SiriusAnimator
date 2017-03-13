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
import fr.inria.diverse.trace.commons.CodeGenUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.metamodel.generator.TraceMMGenerationTraceability
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

class StateManagerGeneratorJava {
	
	// Inputs
	private val String className
	private val String packageQN
	private val EPackage traceMM
	private val TraceMMGenerationTraceability traceability
	private val Set<GenPackage> refGenPackages
	
	// Shortcuts
	private val String stateFQN
	private val String valueFQN
	
	private boolean getTracedToExeUsed = false
	
	public def String getClassName() {
		return className
	}
	
	new(String languageName, String packageQN, EPackage traceMM, TraceMMGenerationTraceability traceability, Set<GenPackage> refGenPackages) {
		this.traceMM = traceMM
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "StateManager"
		this.packageQN = packageQN
		this.traceability = traceability
		this.refGenPackages = refGenPackages
		
		stateFQN = getJavaFQN(traceability.traceMMExplorer.getSpecificStateClass)
		valueFQN = getJavaFQN(traceability.traceMMExplorer.specificValueClass)
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
		val String code = generateStateManagerClass()
		try {
			return CodeGenUtil.formatJavaCode(code)
		} catch (Throwable t) {
			return code
		}
	}
	
	private def String stringGetterExeValue(String javaVarName, EStructuralFeature p, EClass typeToCastTo) {
		return '''
		«IF (p instanceof EReference && traceability.hasTracedClass(p.EType as EClass))»
		
		««« If many elements are in this fields, we have to cast the element with a collection
		«IF p.many»
		(Collection<? extends «getJavaFQN(p.EType,true)»>) 
		«ELSE»
		(«getJavaFQN(p.EType, true)»)
		«ENDIF»
		«getTracedToExeMethodName»(((«getJavaFQN(typeToCastTo)») «javaVarName»).«EcoreCraftingUtil.stringGetter(p)»)
		«ELSE»
		((«getJavaFQN(typeToCastTo)») «javaVarName»).«EcoreCraftingUtil.stringGetter(p)»
		«ENDIF»'''
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
	
	private def String getTracedToExeMethodName() {
		getTracedToExeUsed = true
		return "getTracedToExe"
	}
	
	private def String generateImports() {
		return
				'''
					import java.util.ArrayList;
					import java.util.Collection;
					import java.util.Map;
					
					import org.eclipse.emf.ecore.EObject;
					import org.eclipse.emf.ecore.resource.Resource;
					import org.eclipse.emf.transaction.RecordingCommand;
					import org.eclipse.emf.transaction.TransactionalEditingDomain;
					import org.eclipse.emf.transaction.util.TransactionUtil;
					import org.gemoc.executionframework.engine.core.CommandExecution;
					
					import fr.inria.diverse.trace.commons.model.trace.State;
					import fr.inria.diverse.trace.commons.model.trace.TracedObject;
					import fr.inria.diverse.trace.gemoc.api.IStateManager;
				'''
	}
	
	private def String generateFields() {
		return
				'''
					private final Resource modelResource;
					
					private final Map<TracedObject<?>, EObject> tracedToExe;
				'''
	}
	
	private def String generateConstructors() {
		return
				'''
					public «className»(Resource modelResource, Map<TracedObject<?>, EObject> tracedToExe) {
						this.modelResource = modelResource;
						this.tracedToExe = tracedToExe;
					}
				'''
	}
	
	private def String generateMethods() {
		return
				'''
					@Override
					public void restoreState(State<?, ?> state) {
						if (modelResource != null && state instanceof «stateFQN») {
							try {
								final TransactionalEditingDomain ed = TransactionUtil.getEditingDomain(modelResource);
								if (ed != null) {
									final RecordingCommand command = new RecordingCommand(ed, "") {
										protected void doExecute() {
											restoreStateExecute((«stateFQN») state);
										}
									};
									CommandExecution.execute(ed, command);
								}
							} catch (Exception e) {
								throw e;
							}
						}
					}
					
					private EObject getTracedToExe(EObject tracedObject) {
						return tracedToExe.get(tracedObject);
					}
					
					private Collection<? extends EObject> getTracedToExe(Collection<? extends EObject> tracedObjects) {
						Collection<EObject> result = new ArrayList<EObject>();
						for (EObject tracedObject : tracedObjects) {
							result.add(tracedToExe.get(tracedObject));
						}
						return result;
					}
					
					@SuppressWarnings("unchecked")
					private void restoreStateExecute(«stateFQN» state) {
						for («valueFQN» value : state.getValues()) {
							«FOR p : traceability.allMutableProperties.sortBy[FQN] SEPARATOR "else"»
							«val EReference pdimension = traceability.getDimensionRef(p)»
							«val EClass tracedObjectClass = pdimension.getEContainingClass»
							«val EClass valueClass = traceability.getValueClass(p)»
							if (value instanceof «getJavaFQN(valueClass)») {
								final «getJavaFQN(tracedObjectClass)» tracedObject = («getJavaFQN(tracedObjectClass)») value.eContainer().eContainer();
								«IF p.eContainer instanceof ClassExtension»
								«val extendedClass = (p.eContainer as ClassExtension).extendedExistingClass»
								final «getJavaFQN(extendedClass)» originalObject = («getJavaFQN(extendedClass)») tracedToExe.get(tracedObject);
								«IF p.many»
								originalObject.«EcoreCraftingUtil.stringGetter(p)».clear();
								originalObject.«EcoreCraftingUtil.stringGetter(p)».addAll(«stringGetterExeValue("value", p, valueClass)»);
								«ELSE»
								final «getJavaFQN(p.EType)» toSet = «stringGetterExeValue("value", p, valueClass)»;
								final «getJavaFQN(p.EType)» current = originalObject.«EcoreCraftingUtil.stringGetter(p)»;
								if (current != toSet) {
									originalObject.«EcoreCraftingUtil.stringSetter(p, "toSet", refGenPackages)»;
								}
								«ENDIF»
								«ELSEIF p.eContainer instanceof EClass»
								«val containingClass = p.EContainingClass»
								«getJavaFQN(containingClass)» exeObject = («getJavaFQN(containingClass)») «getTracedToExeMethodName»(tracedObject);
								«IF p.many»
								exeObject.«EcoreCraftingUtil.stringGetter(p)».clear();
								«IF p instanceof EReference»
								exeObject.«EcoreCraftingUtil.stringGetter(p)».addAll((Collection<? extends «getJavaFQN(p.EType,true)»>) «getTracedToExeMethodName»(((«getJavaFQN(valueClass)») value).«EcoreCraftingUtil.stringGetter(p)»));
								«ELSE»
								exeObject.«EcoreCraftingUtil.stringGetter(p)».addAll((Collection<? extends «getJavaFQN(p.EType,true)»>) ((«getJavaFQN(valueClass)») value).«EcoreCraftingUtil.stringGetter(p)»);
								«ENDIF»
								«ELSE»
								exeObject.«EcoreCraftingUtil.stringSetter(p, stringGetterExeValue("value", p, valueClass), refGenPackages)»;
								«ENDIF»
								«ENDIF»
							}
							«ENDFOR»
						}
					}
				'''
	}
	
	private def String generateStateManagerClass() {
		return
				'''
					package «packageQN»;
					
					«generateImports»
										
					public class «className» implements IStateManager<State<?,?>> {
						
						«generateFields»
						«generateConstructors»
						«generateMethods»
					}
				'''
	}
	
}