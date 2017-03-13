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

import fr.inria.diverse.trace.commons.CodeGenUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.metamodel.generator.TraceMMGenerationTraceability
import java.util.HashSet
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

class TraceNotifierGeneratorJava {
	
	// Inputs
	private val String className
	private val String packageQN
	private val TraceMMGenerationTraceability traceability
	private val Set<GenPackage> refGenPackages
	
	// Shortcuts
	private val String stateFQN
	private val String valueFQN
	private val String specificStepFQN
	
	public def String getClassName() {
		return className
	}
	
	new(String languageName, String packageQN, TraceMMGenerationTraceability traceability, Set<GenPackage> refGenPackages) {
		this.className = languageName.replaceAll(" ", "").toFirstUpper + "Notifier"
		this.packageQN = packageQN
		this.traceability = traceability
		this.refGenPackages = refGenPackages
		
		stateFQN = getJavaFQN(traceability.traceMMExplorer.getSpecificStateClass)
		valueFQN = getJavaFQN(traceability.traceMMExplorer.getSpecificValueClass)
		specificStepFQN = getJavaFQN(traceability.traceMMExplorer.specificStepClass)
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
		val String code = generateTraceNotifierClass()
		try {
			return CodeGenUtil.formatJavaCode(code)
		} catch (Throwable t) {
			return code
		}
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
					import java.util.List;
					
					import org.eclipse.emf.ecore.EObject;
					import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.BatchModelChangeListener;
					import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange;
					import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NewObjectModelChange;
					
					import fr.inria.diverse.trace.commons.model.trace.Dimension;
					import fr.inria.diverse.trace.commons.model.trace.State;
					import fr.inria.diverse.trace.commons.model.trace.Step;
					import fr.inria.diverse.trace.commons.model.trace.Value;
					import fr.inria.diverse.trace.gemoc.api.ITraceListener;
					import fr.inria.diverse.trace.gemoc.api.ITraceNotifier;
				'''
	}
	
	private def String generateFields() {
		return
				'''
					private BatchModelChangeListener traceListener;
					
					private final List<ITraceListener> listeners = new ArrayList<>();
				'''
	}
	
	private def String generateConstructors() {
		return
				'''
					public «className»(BatchModelChangeListener traceListener) {
						this.traceListener = traceListener;
					}
				'''
	}
	
	private def String generateMethods() {
		return
				'''
					@Override
					public void notifyListeners() {
						for (ITraceListener listener : listeners) {
							notifyListener(listener);
						}
					}
					
					@Override
					public void notifyListener(ITraceListener listener) {
						final List<ModelChange> changes = traceListener.getChanges(listener);
						if (!changes.isEmpty()) {
							final List<Step> startedSteps = new ArrayList<>();
							final List<Step> endedSteps = new ArrayList<>();
							final List<State> newStates = new ArrayList<>();
							final List<Value> newValues = new ArrayList<>();
							final List<Dimension<? extends Value>> newDimensions = new ArrayList<>();
							changes.forEach(c -> {
								if (c instanceof NewObjectModelChange) {
									final EObject o = c.getChangedObject();
									if (o instanceof «valueFQN») {
										newValues.add((«valueFQN») o);
									} else if (o instanceof «specificStepFQN») {
										startedSteps.add((«specificStepFQN») o);
									} else if (o instanceof «stateFQN») {
										final «stateFQN» newState = («stateFQN») o;
										newStates.add(newState);
										endedSteps.addAll(newState.getEndedSteps());
									«FOR mutClass : traceability.allMutableClasses.filter[c|!c.isAbstract].sortBy[name]»
									«val traced = traceability.getTracedClass(mutClass)»
									«val mutProps = getAllMutablePropertiesOf(mutClass).sortBy[FQN]»
									«IF !mutProps.empty»
									} else if (o instanceof «getJavaFQN(traced)») {
									«FOR p : mutProps»
									«val EReference pdimension = traceability.getDimensionRef(p)»
										newDimensions.add(((«getJavaFQN(traced)») o).«EcoreCraftingUtil.stringGetter(pdimension)»);
									«ENDFOR»
									«ENDIF»
									«ENDFOR»
									}
								}
							});
							listener.valuesAdded(newValues);
							listener.dimensionsAdded(newDimensions);
							listener.statesAdded(newStates);
							listener.stepsStarted(startedSteps);
							listener.stepsEnded(endedSteps);
						}
					}
					
					@Override
					public void addListener(ITraceListener listener) {
						listeners.add(listener);
						traceListener.registerObserver(listener);
					}
					
					@Override
					public void removeListener(ITraceListener listener) {
						listeners.remove(listener);
						traceListener.removeObserver(listener);
					}
				'''
	}
	
	private def String generateTraceNotifierClass() {
		return
				'''
					package «packageQN»;
					
					«generateImports»
										
					public class «className» implements ITraceNotifier {
						
						«generateFields»
						«generateConstructors»
						«generateMethods»
					}
				'''
	}
	
}