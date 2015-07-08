package fr.inria.diverse.tracemm.xmof2tracematerial

import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil.Copier
import org.eclipse.xtend.lib.annotations.Accessors
import org.modelexecution.xmof.Syntax.Activities.IntermediateActivities.Activity
import org.modelexecution.xmof.Syntax.Classes.Kernel.BehavioredEClass
import org.modelexecution.xmof.Syntax.Classes.Kernel.ParameterDirectionKind

class XmofEventsMetamodelGenerator {

	protected val Resource xmofModel
	protected boolean done = false
	protected Copier copier

	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) EPackage eventsmmResult

	new(Set<EPackage> ecore, Resource xmofModel, Copier copier) {
		this.xmofModel = xmofModel
		this.copier = copier
	}

	new(Resource ecoreModel, Resource xmofModel, Copier copier) {
		this(ecoreModel.contents.filter(EPackage).toSet, xmofModel, copier)
	}

	protected def void computeEventMM() {

		// Init ecore model
		eventsmmResult = EcoreFactory.eINSTANCE.createEPackage
		eventsmmResult.name = "eventsMM"
		eventsmmResult.nsPrefix = "eventsMM"
		eventsmmResult.nsURI = "http://eventsMM/1.0"

		// find all xmof activities, 
		for (confClass : xmofModel.allContents.filter(BehavioredEClass).toSet) {

			for (Activity activity : confClass.ownedBehavior.filter(Activity)) {

				println("Found xmof activity!" + activity)

				// create an entry event class
				val entryEventClass = EcoreFactory.eINSTANCE.createEClass
				entryEventClass.name = ExtractorStringsCreator.class_createEntryEventClassName(confClass, activity)
				eventsmmResult.EClassifiers.add(entryEventClass)

				// create an exit event class
				val exitEventClass = EcoreFactory.eINSTANCE.createEClass
				exitEventClass.name = ExtractorStringsCreator.class_createExitEventClassName(confClass, activity)
				eventsmmResult.EClassifiers.add(entryEventClass)
				EcoreCraftingUtil.addReferenceToClass(exitEventClass, ExtractorStringsCreator.ref_ExitToEntry,
					entryEventClass)
				eventsmmResult.EClassifiers.add(exitEventClass)

				// we add a param property for the caller element ("this"), thus typed by the original class
				EcoreCraftingUtil.addReferenceToClass(entryEventClass, ExtractorStringsCreator.ref_EventToThis,
					copier.get(confClass) as EClass)

				// For each activity param, create a property in the class
				for (param : activity.ownedParameter) {

					// Either this is a known class of the metamodel, in which case we take the copy/original
					var EClassifier paramType = null
					if(copier.keySet.contains(param.EType))
						paramType = copier.get(param.EType) as EClass
					// Or not (eg EInt), and we use the type directly
					else
						paramType = param.EType

					// If there is no type, then we put a generic ref, to be able to point to any element from the original model or from runtime classes
					if(paramType == null)
						paramType = EcorePackage.eINSTANCE.EObject

					// Then we construct the structuralfeature 
					var EStructuralFeature paramFeature = null
					val entryName = ExtractorStringsCreator.ref_createEntryToParam(param)
					val exitName = ExtractorStringsCreator.ref_createExitToReturn(param)

					// Case input param
					if(param.direction == ParameterDirectionKind.IN ||
						param.direction == ParameterDirectionKind.INOUT) {
						paramFeature = EcoreCraftingUtil.addFeatureToClass(entryEventClass, entryName, paramType)
					} 
					 
					// Case output param
					else if(param.direction == ParameterDirectionKind.OUT ||
						param.direction == ParameterDirectionKind.RETURN) {
						paramFeature = EcoreCraftingUtil.addFeatureToClass(exitEventClass, exitName, paramType)
					}

					// The param has the same characteristics as the xmof param
					paramFeature.unique = param.unique
					paramFeature.ordered = param.ordered
					paramFeature.lowerBound = param.lowerBound
					paramFeature.upperBound = param.upperBound

				}
			}
		}
	}
}
