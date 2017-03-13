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
package fr.inria.diverse.trace.plaink3.tracematerialextractor

import ecorext.Ecorext
import ecorext.EcorextFactory
import ecorext.Rule
import fr.inria.diverse.commons.eclipse.callgraph.CallHierarchyHelper
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.jdt.core.IAnnotation
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.IType
class K3StepExtractor {

	// Input
	private val Set<IType> allClasses
	private val EPackage extendedMetamodel

	// Input / Output
	private val Ecorext ecoreExtension

	// Transient
	private val Map<IType, EClass> stepAspectsClassToAspectedClasses = new HashMap
	private val Set<IMethod> allMethods = new HashSet
	private val Set<IMethod> allk3Methods = new HashSet
	private val Set<IMethod> allSuperMethods = new HashSet
	private val Set<IMethod> stepFunctions = new HashSet
	private val Set<IMethod> eventFunctions = new HashSet
	private val Map<IMethod, Rule> functionToRule = new HashMap
	private val Set<IType> inspectedClasses = new HashSet
	
	private val Map<IMethod,IMethod> methodToK3Method = new HashMap
	private val Map<IMethod,IMethod> k3MethodToMethod = new HashMap
	private val Map<IMethod,IMethod> superMethodTok3Method = new HashMap
	private val Map<IMethod,Set<IMethod>> k3MethodToCalledMethods = new HashMap
	private val Map<IMethod,Set<IMethod>> methodToOverridingMethods = new HashMap
	private val Map<IMethod,Set<IMethod>> callGraph = new HashMap
	private val Map<IType, Set<IType>> classToSubClasses = new HashMap
	private val Map<IType, Set<IType>> classToSuperClasses = new HashMap
	

	new(Set<IType> aspects, String languageName, EPackage extendedMetamodel, Ecorext inConstructionEcorext) {
		this.allClasses = aspects
		this.extendedMetamodel = extendedMetamodel
		this.ecoreExtension = inConstructionEcorext
	}

	public def void generate() {
		generateStepFromXtend(allClasses)
	}

	private def Rule getRuleOfFunction(IMethod function) {
		if (functionToRule.containsKey(function))
			return functionToRule.get(function)
		else {
			val Rule rule = EcorextFactory.eINSTANCE.createRule;
			this.ecoreExtension.rules.add(rule)

			// We find the ecore class matching the aspected java class 
			val containingClass = function.declaringType
			rule.containingClass = stepAspectsClassToAspectedClasses.get(containingClass)

			var EOperation candidate = null
			if (rule.containingClass != null) {
				candidate = rule.containingClass.EAllOperations.findFirst [ o |
					o.name.equals(function.elementName)
				]

			}
			if (candidate != null) {
				rule.operation = candidate
			} else {
				rule.operation = xtendFunctionToEOperation(function)
			}

			rule.stepRule = stepFunctions.contains(function)
			rule.main = isMain(function)
			functionToRule.put(function, rule)
			return rule
		}
	}

	private def void inspectForBigStep(IMethod function) {

		// We consider that each Kermeta function is a transformation rule (even through we cannot know if it modifies anything)
		val Rule rule = getRuleOfFunction(function)

		// We retrieve which functions are called by the function
		val calledFunctions = callGraph.get(function)
		if (calledFunctions != null) {
			for (calledFunction : calledFunctions) {
				if (calledFunction !== null) {
					val Rule calledRule = getRuleOfFunction(calledFunction)
					rule.calledRules.add(calledRule)
				}
			}
		}

		// Finally we look if this function was overriden/implemented by subtypes
		// TODO use annotation?
		val subtypes = classToSubClasses.get(function.declaringType)
		if (subtypes != null) {
			for (t : subtypes) {
				for (f : t.methods) {
					if (f.elementName.equals(function.elementName)) {
						val Rule overridingRule = getRuleOfFunction(f)
						rule.overridenBy.add(overridingRule)
					}
				}
			}
		}
	}

	private def EOperation xtendFunctionToEOperation(IMethod function) {
		val result = EcoreFactory.eINSTANCE.createEOperation
		result.name = function.elementName
		// TODO finish the translation and/or ask Thomas
		// TODO or consider it is already in the ecore?
		return result
	}

	private def void inspectClass(IType type) {
		if (!inspectedClasses.contains(type)) {
			// Gather all the actual bodies of the methods (_privk3_ methods)
			val typeK3Methods = type.methods.filter[elementName.startsWith("_privk3_")]
			allk3Methods.addAll(typeK3Methods)
			
			// Gather the methods calling those k3 methods
			val typeMethods = type.methods.filter[m|typeK3Methods.exists[c|
				c.elementName.substring(8).equals(m.elementName)
			]]
			allMethods.addAll(typeMethods)
			
			typeMethods.forEach[m|
				val k3m = typeK3Methods.findFirst[c|c.elementName.substring(8).equals(m.elementName)]
				k3MethodToMethod.put(k3m,m)
				methodToK3Method.put(m,k3m)
			]
			
			val Set<IMethod> candidateSupers = new HashSet
			candidateSupers.addAll(type.methods.filter[elementName.startsWith("super_")])
			
			// Gather all k3 generated super methods
			allSuperMethods.addAll(candidateSupers.filter[c|type.methods.exists[m|
				c.elementName.substring(6).equals(m.elementName)
			]])

			// For each aspect annotation of the class
			for (a : getAspectAnnotations(type)) {

				// We find the JVM aspected class
				val aspectedEClass = getAspectized(a)

				// We store the aspect class and the aspected class
				stepAspectsClassToAspectedClasses.put(type, aspectedEClass)

				// We store all the functions with @Step
				stepFunctions.addAll(type.methods.filter[isStep])
				
				// And we store all the functions with @EventProcessor
				eventFunctions.addAll(type.methods.filter[isEvent])
			}
			inspectedClasses.add(type)
		}
	}
	
	private def void gatherCallsFromK3(IMethod function) {
		val callingSites = CallHierarchyHelper.getCallLocationsOf(function)
		callingSites.forEach[cl|
			val f = function
			val member = cl.member
			val method = getContainingAspectMethod(member as IMethod)
			allk3Methods
					// Filter out non-k3 methods
					.filter[m|m == method]
					// And add 'function' to the called methods of each calling k3 method
					.forEach[m|
						var calledMethods = k3MethodToCalledMethods.get(m)
						if (calledMethods == null) {
							calledMethods = new HashSet
							k3MethodToCalledMethods.put(m,calledMethods)
						}
						calledMethods.add(f)
					]
		]
	}
	
	private def void gatherCallsFromSuper(IMethod function) {
		val callingSites = CallHierarchyHelper.getCallLocationsOf(function)
		callingSites.forEach[cl|
			allSuperMethods
					// TODO unfold lambdas
					
					// Filter out non-super methods
					.filter[m|m == cl.member]
					// And set 'function' to be the called method of each calling super method
					.forEach[m|superMethodTok3Method.put(m,function)]
		]
	}
	
	private def void gatherOverridenMethods(IMethod method) {
		if (method.override) {
			val methodName = method.elementName
			val declaringType = method.declaringType
			val superClasses = classToSuperClasses.get(declaringType)
			if (superClasses != null) {
				superClasses.forEach[c|
					val overridenMethod = c.methods.findFirst[m|m.elementName.equals(methodName)]
					if (overridenMethod != null) {
						var overridingMethods = methodToOverridingMethods.get(overridenMethod)
						if (overridingMethods == null) {
							overridingMethods = new HashSet
							methodToOverridingMethods.put(overridenMethod,overridingMethods)
						}
						overridingMethods.add(method)
					}
				]
			}
		}
	}
	
	private def generateStepFromXtend(Set<IType> files) {
		// First we store the class hierarchy of each class.
		allClasses.forEach[c|
			val allSuperClasses = c.allSuperClasses.filter[t|allClasses.contains(t)].toSet
			classToSuperClasses.put(c,allSuperClasses)
			val allSubClasses = c.allSubClasses.filter[t|allClasses.contains(t)].toSet
			classToSubClasses.put(c,allSubClasses)
		]
		
		// Then we look for functions, step aspects and step functions
		for (c : allClasses) {
			inspectClass(c)
		}
		
		allk3Methods.forEach[gatherCallsFromSuper]
		allMethods.forEach[gatherOverridenMethods]
		allMethods.forEach[gatherCallsFromK3]
		allSuperMethods.forEach[gatherCallsFromK3]
		
		// We establish the base callgraph.
		allMethods.forEach[m|
			val k3m = methodToK3Method.get(m)
			if (k3m != null) {
				val calledMethods = k3MethodToCalledMethods.get(k3m)
				if (calledMethods != null) {
					calledMethods.forEach[c|
						if (allMethods.contains(c)) {
							var tmp = callGraph.get(m)
							if (tmp == null) {
								tmp = new HashSet
								callGraph.put(m,tmp)
							}
							tmp.add(c)
						}
					]
				}
			}
		]
		
		val callGraphTotalLengthComputer = [|callGraph.values.map[s|s.size].reduce[i1, i2|i1 + i2]]
		
		// For each method, we add to its called methods the methods that can be called
		// from each of its overriding methods.
		var totalLength = callGraphTotalLengthComputer.apply()
		var previousTotalLength = -1
		while(totalLength > previousTotalLength) {
			allMethods.forEach[m|
				val calledMethods =
						if (callGraph.get(m) == null) {
							val tmp = new HashSet
							callGraph.put(m,tmp)
							tmp
						} else {
							callGraph.get(m)
						}
				val overridingMethods = methodToOverridingMethods.get(m)
				if (overridingMethods != null) {
					overridingMethods.forEach[n|
						val calledByOverride = callGraph.get(n)
						if (calledByOverride != null) {
							calledMethods.addAll(calledByOverride)
						}
					]
				}
			]
			previousTotalLength = totalLength
			totalLength = callGraphTotalLengthComputer.apply()
		}
		
		// For each method, we add to their called methods the methods overriding
		// those called methods.
		totalLength = callGraphTotalLengthComputer.apply()
		previousTotalLength = -1
		while(totalLength > previousTotalLength) {
			allMethods.forEach[m|
				val calledMethods = callGraph.get(m)
				if (calledMethods != null) {
					val tmp = new HashSet
					calledMethods.forEach[n|
						val overridingMethods = methodToOverridingMethods.get(n)
						if (overridingMethods != null) {
							tmp.addAll(overridingMethods)
						}
					]
					calledMethods.addAll(tmp)
				}
			]
			previousTotalLength = totalLength
			totalLength = callGraphTotalLengthComputer.apply()
		}
		
		allMethods.forEach[m|
			var calledMethods = callGraph.get(m)
			if (calledMethods == null) {
				calledMethods = new HashSet
				callGraph.put(m,calledMethods)
			}
			calledMethods.addAll(eventFunctions)
		]
		
		// We then add in the support for calls to super methods.
		allMethods.forEach[m|
			val k3m = methodToK3Method.get(m)
			if (k3m != null) {
				val calledMethods = k3MethodToCalledMethods.get(k3m)
				if (calledMethods != null) {
					calledMethods.forEach[c|
						if (allSuperMethods.contains(c)) {
							val actualk3Method = superMethodTok3Method.get(c)
							if (actualk3Method != null) {
								val actualMethod = k3MethodToMethod.get(actualk3Method)
								if (actualMethod != null && allMethods.contains(actualMethod)) {
									var tmp = callGraph.get(m)
									if (tmp == null) {
										tmp = new HashSet
										callGraph.put(m,tmp)
									}
									tmp.add(actualMethod)
								}
							}
						}
					]
				}
			}
		]
		
		println("Callgraph : \n\n")
		callGraph.forEach[m,s|
			println(m.declaringType.elementName + "." + m.elementName + " : \n" + s.map[n|
				n.declaringType.elementName + "." + n.elementName
			].reduce[s1, s2|s1 + ", " + s2] + "\n")
		]
		
		// Next we create the Rule objects with all that
		for (function : allMethods) {
			inspectForBigStep(function)
		}
	}

	/**
	 * Find annotations "@Aspect"
	 */
	private def List<IAnnotation> getAspectAnnotations(IType type) {
		// TODO compare with: fr.inria.diverse.k3.al.annotationprocessor.Aspect
		if (type.isClass) {
			return type.annotations.filter [ annot |
				val name = annot.elementName // may be qualified
				val lastDotIndex = name.lastIndexOf('.')
				var simpleName = name
				if (lastDotIndex !== -1) {
					simpleName = name.substring(lastDotIndex + 1)
				}
				simpleName.equals("Aspect")
			].toList
		}
		return new ArrayList<IAnnotation>()
	}

	private def boolean testAnnotation(IMethod method, String annotationSimpleName) {
		// TODO compare with: fr.inria.diverse.k3.al.annotationprocessor.XXX
		return method.annotations.exists [ annot |
			val name = annot.elementName // may be qualified
			val lastDotIndex = name.lastIndexOf('.')
			var simpleName = name
			if (lastDotIndex !== -1) {
				simpleName = name.substring(lastDotIndex + 1)
			}
			return simpleName.equals(annotationSimpleName)
		]
	}

	/**
	 * Return true if 'method' is tagged with "@Step"
	 */
	private def boolean isStep(IMethod method) {
		testAnnotation(method, "Step")
	}

	/**
	 * Return true if 'method' is tagged with "@EventProcessor"
	 */
	private def boolean isEvent(IMethod method) {
		val annotation = method.annotations.findFirst[a|
			val name = a.elementName
			val lastDotIndex = name.lastIndexOf('.')
			var simpleName = name
			if (lastDotIndex !== -1) {
				simpleName = name.substring(lastDotIndex + 1)
			}
			return simpleName == "Step"
		]
		annotation != null && annotation.memberValuePairs.exists[p|
			p.memberName == "eventTriggerable" && p.value instanceof Boolean && p.value as Boolean
		]
	}

	/**
	 * Return true if 'method' is tagged with "@OverrideAspectMethod"
	 */
	private def boolean isOverride(IMethod method) {
		testAnnotation(method, "OverrideAspectMethod")
	}
	
	/**
	 * Return true if 'method' is tagged with "@Main"
	 */
	private def boolean isMain(IMethod method) {
		testAnnotation(method, "Main")
	}
	
	/**
	 * Return all sub types
	 */
	private def Set<IType> getAllSubClasses(IType type) {
		val hierarchy = type.newTypeHierarchy(new NullProgressMonitor)
		return hierarchy.getAllSubtypes(type).toSet
	}
	
	/**
	 * Return all super types
	 */
	private def Set<IType> getAllSuperClasses(IType type) {
		val hierarchy = type.newTypeHierarchy(new NullProgressMonitor)
		return hierarchy.getAllSuperclasses(type).toSet
	}
	
	private def EClass getAspectized(IAnnotation annot) {
		val aspectedClassName = annot.memberValuePairs.findFirst[p|p.memberName == "className"].value as String
		return extendedMetamodel.eAllContents.filter(EClass).findFirst[c1|aspectedClassName.equals(c1.name)]
	}
	
	/**
	 * Return the top level method in a type tagged @aspect
	 * that contains 'function'<br>
	 * <br>
	 * Return 'function' if it is already a top level method.<br>
	 * <br>
	 * Return null if not inside a type with @aspect
	 */
	private def IMethod getContainingAspectMethod(IMethod function) {
		val container = function.declaringType
		if (allClasses.contains(container)) {
			return function
		}

		// function can be in annonymous/inner classes (e.g in lamba)
		var parent = function.parent
		while (parent !== null) {
			if (parent instanceof IMethod) {
				return getContainingAspectMethod(parent)
			}
			parent = parent.parent
		}

		return null
	}
}
