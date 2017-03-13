package fr.inria.diverse.trace.benchmark.runconf

import fr.inria.diverse.trace.benchmark.languages.BenchmarkLanguage
import java.util.ArrayList
import java.util.List
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.gemoc.activitydiagram.sequential.xactivitydiagram.activitydiagram.NamedElement;
import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine
import org.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtension
import org.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint

class BenchmarkRunConfiguration implements IRunConfiguration {

	private val BenchmarkLanguage language
	private val URI model
	private val Set<String> addonsToLoad

	new(URI model, BenchmarkLanguage language, Set<String> addonsToLoad) {
		this.language = language
		this.model = model
		this.addonsToLoad = addonsToLoad
	}

	override getAnimationDelay() {
		return 0;
	}

	override getAnimatorURI() {
		return null;
	}

	override getDeadlockDetectionDepth() {
		return 0;
	}
	
	override getEngineAddonExtensions() {
		val List<EngineAddonSpecificationExtension> result = new ArrayList<EngineAddonSpecificationExtension>();
		val loadedAddons = EngineAddonSpecificationExtensionPoint.getSpecifications()
		for (ext : loadedAddons) {
			if(addonsToLoad.contains(ext.name)) {
				result.add(ext)
			}
		}
		return result
	}

	override getExecutedModelAsMelangeURI() {
		getExecutedModelURI();
	}

	override getExecutedModelURI() {
		model
	}

	override getExecutionEntryPoint() {
		language.entryPoint
	}

	override getLanguageName() {
		language.languageName
	}

	override getMelangeQuery() {
		""
	}
	
	override getBreakStart() {
		false
	}
	
	override getDebugModelID() {
		"org.gemoc.execution.sequential.javaengine.ui.debugModel"
	}
	
	override getModelEntryPoint() {
		val modelResource = PlainK3ExecutionEngine.loadModel(model)
		modelResource.allContents
			.filter(NamedElement)
			.findFirst[true].name
	}
	
	override getModelInitializationArguments() {
		""
	}
	
	override getModelInitializationMethod() {
		language.initializationMethod
	}

}
