package fr.inria.diverse.trace.metamodel.generator

import ecorext.Ecorext
import ecorext.Rule
import fr.inria.diverse.trace.commons.EMFUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.commons.tracemetamodel.StepStrings
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

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

	new(Ecorext mmext, EPackage tracemmresult, TraceMMGenerationTraceability traceability,
		TraceMMExplorer traceMMExplorer, boolean gemoc) {
		this.traceability = traceability
		this.tracemmresult = tracemmresult
		this.traceMMExplorer = traceMMExplorer
		this.mmext = mmext
		this.gemoc = gemoc
	}

	private def void debug(Object stuff) {
		// println(stuff)
	}

	private def Set<Rule> gatherOverrides(Rule rule) {
		val Set<Rule> result = new HashSet
		result.add(rule)
		result.addAll(rule.overridenBy)
		for (ov : rule.overridenBy) {
			result.addAll(gatherOverrides(ov))
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
		// In the context of gemoc, a step is an MSEOccurrence
		if (gemoc) {
			val ResourceSet rs = new ResourceSetImpl
			val Resource mseMetamodel = EMFUtil.loadModelURI(
				URI.createPlatformPluginURI(
					"/org.gemoc.executionframework.engine.mse.model/model/GemocExecutionEngineMSE.ecore", true), rs)
			val mseOccurrenceClass = mseMetamodel.allContents.filter(EClass).findFirst[c|c.name.equals("MSEOccurrence")]
			traceMMExplorer.getStepClass.ESuperTypes.add(mseOccurrenceClass)
		}

		val stepRules = mmext.rules.filter[r|r.isStepRule].toSet

		// Flatten the rule graph regarding function overrides
		for (rule : mmext.rules) {
			val overrides = gatherOverrides(rule)
			for (ov : overrides) {
				rule.calledRules.addAll(ov.calledRules)
			}
			val ruleCN = if (rule.containingClass != null)
					rule.containingClass.name + "."
				else
					""
			println("Rule " + ruleCN + rule.operation.name)
			for (calledRule : rule.calledRules) {
				val calledCN = if (calledRule.containingClass != null)
						calledRule.containingClass.name + "."
					else
						""
				println("\tCalled rule: " + calledCN + calledRule.operation.name)
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

		// Remove abstract rules
		stepRules.removeAll(stepRules.filter[r|r.abstract])
		// Change the collection of rules of mmext (for later use in other stuff)
		// So that it only contains concrete steps
		mmext.rules.clear
		mmext.rules.addAll(stepRules)

		val prettyStepRules = stepRules.map [ r |
			r.containingClass.name + "." + r.operation.name + ": " + !r.calledRules.empty
		]
		debug(prettyStepRules)

		// Now "stepRules" contains a set of step rules that only call other step rules
		// We directly have the information for the big/small steps creation
		// -----------------------------------------
		for (stepRule : stepRules) {

			// Creation of the step class (or reuse)
			val stepClass = getStepClass(stepRule)

			// TODO use in the caller operation / reference, but for now creates issues
			var EClass stepContainingClassInTrace = traceability.getTracedClass(stepRule.containingClass)
			if (stepContainingClassInTrace == null)
				stepContainingClassInTrace = stepRule.containingClass

			// Default basic name
			stepClass.name = stepRule.operation.name

			// If in the context of gemoc, we implement a "getCaller" eoperation that is well typed
			if (gemoc && stepRule.containingClass != null) {
				val EOperation getCallerEOperation = EcoreFactory.eINSTANCE.createEOperation
				getCallerEOperation.EType = stepRule.containingClass
				getCallerEOperation.lowerBound = 1
				getCallerEOperation.upperBound = 1
				getCallerEOperation.name = "getCaller"
				val bodyAnnotation = EcoreFactory.eINSTANCE.createEAnnotation
				bodyAnnotation.source = GenModelPackage.eNS_URI
				bodyAnnotation.details.put("body", '''
					return («(stepRule.containingClass.name)») this.getMse().getCaller();
				''')
				getCallerEOperation.EAnnotations.add(bodyAnnotation)
				stepClass.EOperations.add(getCallerEOperation)
			} // Else we put a single "this" parameter
			else {
				EcoreCraftingUtil.addReferenceToClass(stepClass, "this", stepRule.containingClass)
			}

			// And a FQN name
			setClassNameWithoutConflict(stepClass,
				StepStrings.stepClassName(stepRule.containingClass, stepRule.operation))

			// Link Trace -> Step class (new dimension)
			val ref = addReferenceToClass(traceMMExplorer.traceClass,
				TraceMMStrings.ref_createTraceClassToStepClass(stepClass), stepClass)

			ref.lowerBound = 0
			ref.upperBound = -1
			ref.containment = false
			traceability.addStepSequence(stepClass, ref)
			traceability.addStepClass(stepClass)

			// Case Small Step
			if (stepRule.calledRules.isEmpty) {

				// Adding inheritance to SmallStep class
				stepClass.ESuperTypes.add(traceMMExplorer.smallStepClass)

			} // Case Big Step
			else {

				traceability.addBigStepClass(stepClass)

				// Adding inheritance to BigStep abstract class
				stepClass.ESuperTypes.add(traceMMExplorer.bigStepClass)

				// SubStepSuperClass
				val EClass subStepSuperClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.stepsPackage.EClassifiers.add(subStepSuperClass)
				setClassNameWithoutConflict(subStepSuperClass,
					StepStrings.abstractSubStepClassName(stepRule.containingClass, stepRule.operation))
				subStepSuperClass.abstract = true

				// Link StepClass -> SubStepSuperClass
				val ref2 = EcoreCraftingUtil.addReferenceToClass(stepClass, StepStrings.ref_BigStepToSub,
					subStepSuperClass)
				ref2.ordered = true
				ref2.containment = true
				ref2.lowerBound = 0
				ref2.upperBound = -1

				// Fill step class
				val EClass implicitStepClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.stepsPackage.EClassifiers.add(implicitStepClass)
				setClassNameWithoutConflict(implicitStepClass,
					StepStrings.implicitStepClassName(stepRule.containingClass, stepRule.operation))

				// Inheritance Fill > SubStepSuper
				implicitStepClass.ESuperTypes.addAll(subStepSuperClass, traceMMExplorer.smallStepClass)

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
}