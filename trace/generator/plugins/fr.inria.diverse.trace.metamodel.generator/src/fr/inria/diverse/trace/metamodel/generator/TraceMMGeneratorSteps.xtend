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
package fr.inria.diverse.trace.metamodel.generator

import ecorext.Ecorext
import ecorext.Rule
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.commons.tracemetamodel.StepStrings
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Random
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory

import static fr.inria.diverse.trace.commons.EcoreCraftingUtil.*

class TraceMMGeneratorSteps {

	// Inputs
	private val Ecorext mmext
	private val TraceMMExplorer traceMMExplorer
	private val boolean gemoc

	// Inputs/Outputs
	private val EPackage tracemmresult
	private val TraceMMGenerationTraceability traceability

	// Transient
	private val Map<Rule, EClass> stepRuleToClass = new HashMap
	private val String randomStringToFindGetCallerAnnotations = new Random(10000).nextInt.toString

	// Constant
	private static val String GET_CALLER_OPERATION_NAME = "getCaller";

	new(Ecorext mmext, EPackage tracemmresult, TraceMMGenerationTraceability traceability,
		TraceMMExplorer traceMMExplorer, boolean gemoc) {
		this.traceability = traceability
		this.tracemmresult = tracemmresult
		this.traceMMExplorer = traceMMExplorer
		this.mmext = mmext
		this.gemoc = gemoc
	}

	private def void debug(Object stuff) {
//		 println(stuff)
	}

	private def Set<Rule> gatherRulesThatOverride(Rule rule) {
		val Set<Rule> result = new HashSet
		result.add(rule)
		result.addAll(rule.overridenBy)
		for (ov : rule.overridenBy) {
			result.addAll(gatherRulesThatOverride(ov))
		}
		return result
	}

	private def Set<Rule> gatherStepCalls(Rule rule, Set<Rule> inProgress) {
		val Set<Rule> result = new HashSet
		// To avoid cycles
		if (!inProgress.contains(rule)) {
			inProgress.add(rule)
			// Case step rule: stop
			if (rule.isStepRule) {
				result.add(rule)
			} // Case non step, recursive
			else {
				for (called : rule.calledRules) {
					val gathered = gatherStepCalls(called, inProgress)
					result.addAll(gathered)
				}
			}
		}

		return result
	}

	private def getStepClass(Rule stepRule) {
		if (stepRuleToClass.containsKey(stepRule)) {
			return stepRuleToClass.get(stepRule)
		} else {
			val stepClass = EcoreFactory.eINSTANCE.createEClass
			traceMMExplorer.stepsPackage.EClassifiers.add(stepClass)
			stepRuleToClass.put(stepRule, stepClass)
			traceability.addStepRuleToStepClass(stepRule, stepClass)
			return stepClass
		}
	}

	private def setClassNameWithoutConflict(EClass clazz, String name) {
		val nbExistingClassesWithName = this.tracemmresult.eAllContents.toSet.filter(EClass).filter [ c |
			c.name != null && c.name.startsWith(name)
		].size
		if (nbExistingClassesWithName > 0)
			clazz.name = name + "_" + nbExistingClassesWithName
		else
			clazz.name = name
	}

	public def void process() {

		// When a rule calls another rule, it means that it may also call the rules that overrides the latter
		for (rule : mmext.rules) {
			for (calledRule : rule.calledRules.immutableCopy) {
				val overrides = gatherRulesThatOverride(calledRule)
				rule.calledRules.addAll(overrides)
			}
		}

		// Thanks to the previous pass, we can remove abstract rules and calls to them
		// This means that there might be errors if an abstract method is used but never implemented
		mmext.rules.removeIf([abstract])
		for (rule : mmext.rules) {
			rule.calledRules.removeIf([abstract])
		}

		// Next we focus on step rules
		val stepRules = mmext.rules.filter[r|r.isStepRule].toSet

		// We make the Step boolean 'inherited' by overriding methods
		for (rule : stepRules.immutableCopy) {
			val overrides = gatherRulesThatOverride(rule)
			for (o : overrides) {
				o.stepRule = true
				stepRules.add(o)
			}
		}

		// "Merge" normal rules into step rules (ie. inlining)
		for (rule : stepRules) {
			val calledNonStepRules = rule.calledRules.filter[r|!r.isStepRule].toSet
			// For each called non step rule
			for (called : calledNonStepRules) {
				val Set<Rule> inProgress = new HashSet
				// We gather all step rules transitively called
				val gathered = gatherStepCalls(called, inProgress)
				rule.calledRules.addAll(gathered)
			}
			// And we remove the calls to the non step rules
			rule.calledRules.removeAll(calledNonStepRules)
		}

		// Change the collection of rules of mmext (for later use in other stuff)
		// So that it only contains concrete steps
		// TODO use traceability object instead?
		mmext.rules.clear
		mmext.rules.addAll(stepRules)

		val prettyStepRules = stepRules.map [ r |
			r.containingClass.name + "." + r.operation.name + ": " + !r.calledRules.empty
		]
		debug(prettyStepRules)

		// We find the resource containing the gemoc mse metamodel
		// We use the super type of the SpecificTrace class
		val mseMetamodelResource = traceMMExplorer.getSpecificTraceClass.EGenericSuperTypes.head.EClassifier.eResource
		val mseSequentialStepClass = mseMetamodelResource.allContents.toSet.filter(EClass).findFirst [
			name.equals("SequentialStep")
		]
		val mseSmallStepClass = mseMetamodelResource.allContents.toSet.filter(EClass).findFirst[name.equals("SmallStep")]

		// Now "stepRules" contains a set of step rules that only call other step rules
		// We directly have the information for the big/small steps creation
		// -----------------------------------------
		for (stepRule : stepRules) {

			// Creation of the step class (or reuse)
			val stepClass = getStepClass(stepRule)

			// Default basic name
			stepClass.name = stepRule.operation.name
			// If in the context of gemoc, we implement a "getCaller" eoperation that is well typed
			if (gemoc && stepRule.containingClass != null) {
				val EOperation getCallerEOperation = EcoreFactory.eINSTANCE.createEOperation
				val tracedClass = traceability.getTracedClass(stepRule.containingClass)
				getCallerEOperation.EType = if (tracedClass == null) stepRule.containingClass else  tracedClass
				getCallerEOperation.lowerBound = 1
				getCallerEOperation.upperBound = 1
				getCallerEOperation.name = GET_CALLER_OPERATION_NAME

				val bodyAnnotation = EcoreFactory.eINSTANCE.createEAnnotation
				getCallerEOperation.EAnnotations.add(bodyAnnotation)
				bodyAnnotation.source = GenModelPackage.eNS_URI

				// We defer to later the creation of the body, through a dedicated unique annotation
				bodyAnnotation.details.put("body", randomStringToFindGetCallerAnnotations)

				stepClass.EOperations.add(getCallerEOperation)
			} // Else we put a single "this" parameter
			else {
				EcoreCraftingUtil.addReferenceToClass(stepClass, "this", stepRule.containingClass)
			}

			// And a FQN name
			setClassNameWithoutConflict(stepClass,
				StepStrings.stepClassName(stepRule.containingClass, stepRule.operation))

			// Link Trace -> Step class (new dimension)
			val ref = addReferenceToClass(traceMMExplorer.specificTraceClass,
				TraceMMStrings.ref_createTraceClassToStepClass(stepClass), stepClass)

			ref.lowerBound = 0
			ref.upperBound = -1
			ref.containment = false
			traceability.addStepSequence(stepClass, ref)
			traceability.addStepClass(stepClass)

			// In any case we inherit from our generated step class, to have links to states
			stepClass.ESuperTypes.add(traceMMExplorer.getSpecificStepClass)

			// Case Small Step
			if (stepRule.calledRules.isEmpty) {

				// Adding inheritance to SmallStep class
				val smallStepGenericSuperType = EcoreFactory.eINSTANCE.createEGenericType
				smallStepGenericSuperType.EClassifier = mseSmallStepClass
				val smallStepTypeBinding = EcoreFactory.eINSTANCE.createEGenericType
				smallStepGenericSuperType.ETypeArguments.add(smallStepTypeBinding)
				// And binds its type parameter to the generated traced object class
				smallStepTypeBinding.EClassifier = traceMMExplorer.specificStateClass
				stepClass.EGenericSuperTypes.add(smallStepGenericSuperType)

			} // Case Big Step
			else {

				traceability.addBigStepClass(stepClass)

				// Adding inheritance to SequentialStep abstract class
				val genericSuperType = EcoreFactory.eINSTANCE.createEGenericType
				genericSuperType.EClassifier = mseSequentialStepClass
				val stepTypeBinding = EcoreFactory.eINSTANCE.createEGenericType
				val stateTypeBinding = EcoreFactory.eINSTANCE.createEGenericType
				genericSuperType.ETypeArguments.addAll(stepTypeBinding, stateTypeBinding)
				stepClass.EGenericSuperTypes.add(genericSuperType)

				// SubStepSuperClass
				val EClass subStepSuperClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.stepsPackage.EClassifiers.add(subStepSuperClass)
				setClassNameWithoutConflict(subStepSuperClass,
					StepStrings.abstractSubStepClassName(stepRule.containingClass, stepRule.operation))
				subStepSuperClass.abstract = true
				subStepSuperClass.interface = true
				subStepSuperClass.ESuperTypes.add(traceMMExplorer.specificStepClass)

				// Link StepClass -> SubStepSuperClass, simply through type binding
				stepTypeBinding.EClassifier = subStepSuperClass
				
				// Link StepClass -> StateClass
				stateTypeBinding.EClassifier = traceMMExplorer.specificStateClass

				// Fill step class
				val EClass implicitStepClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.stepsPackage.EClassifiers.add(implicitStepClass)
				setClassNameWithoutConflict(implicitStepClass,
					StepStrings.implicitStepClassName(stepRule.containingClass, stepRule.operation))

				// Inheritance Fill > SubStepSuper
				implicitStepClass.ESuperTypes.add(subStepSuperClass)
				
				// Adding inheritance to SmallStep class
				val smallStepGenericSuperType = EcoreFactory.eINSTANCE.createEGenericType
				smallStepGenericSuperType.EClassifier = mseSmallStepClass
				val smallStepTypeBinding = EcoreFactory.eINSTANCE.createEGenericType
				smallStepGenericSuperType.ETypeArguments.add(smallStepTypeBinding)
				// And binds its type parameter to the generated traced object class
				smallStepTypeBinding.EClassifier = traceMMExplorer.specificStateClass
				implicitStepClass.EGenericSuperTypes.add(smallStepGenericSuperType)

				traceability.putImplicitStepClass(implicitStepClass, stepRule.containingClass)
				
				for (calledStepRule : stepRule.calledRules) {
					// For each called step rule, we create an step class (if not created already)
					val EClass subStepClass = getStepClass(calledStepRule)
					// Inheritance SubStep -> SubStepSuper
					subStepClass.ESuperTypes.add(subStepSuperClass)
				}
			}
		}
	}

	/**
	 * To generate the code of 'getCaller' operations inside the trace metamodel, thanks to the exact FQNs
	 * of the generated java classes computed using the genmodel.
	 */
	def addGetCallerEOperations(Set<EPackage> traceMetamodel, Set<GenPackage> packages) {
		for (p : traceMetamodel) {
			// We find operations that have pending body annotation with the random int
			for (operation : p.eAllContents.filter(EOperation).toSet) {
				val annotationsWithBody = operation.EAnnotations.filter[a|a.details.containsKey("body")]
				val annotationWithUniqueString = annotationsWithBody.findFirst [ a |
					a.details.get("body").equals(randomStringToFindGetCallerAnnotations)
				]
				// We put the definitive body in there
				if (annotationWithUniqueString != null) {
					annotationWithUniqueString.details.
						put(
							"body",
							'''return («EcoreCraftingUtil.getJavaFQN(operation.EType, packages)») this.getMseoccurrence().getMse().getCaller();'''
						)
				}
			}
		}
	}
}
