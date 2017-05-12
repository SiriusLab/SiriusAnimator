package org.gemoc.executionframework.test.lib.impl

import java.util.ArrayList
import java.util.List
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.gemoc.executionframework.test.lib.ILanguageWrapper
import org.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtension
import org.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint
import org.gemoc.executionframework.test.lib.IExecutableModel

class TestRunConfiguration implements IRunConfiguration {
	private val ILanguageWrapper language
	private val IExecutableModel model
	private val Set<String> addonsToLoad
	private val URI modelURI

	new(IExecutableModel model, URI modelURI, ILanguageWrapper language, Set<String> addonsToLoad) {
		this.language = language
		this.model = model
		this.addonsToLoad = addonsToLoad
		this.modelURI = modelURI
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
			if (addonsToLoad.contains(ext.name)) {
				result.add(ext)
			}
		}
		return result
	}

	override getExecutedModelAsMelangeURI() {
		if (melangeQuery.isEmpty())
			return modelURI;
		val String melangeURIString = modelURI.toString().replace("platform:/", "melange:/") + melangeQuery;
		return URI.createURI(melangeURIString);
	}

	override getExecutedModelURI() {
		modelURI
	}

	override getExecutionEntryPoint() {
		language.entryPoint
	}

	override getLanguageName() {
		language.languageName
	}

	override getMelangeQuery() {
		model.melangeQuery
	}

	override getBreakStart() {
		false
	}

	override getDebugModelID() {
		"org.gemoc.execution.sequential.javaengine.ui.debugModel"
	}

	override getModelEntryPoint() {
		val ResourceSet resourceSet = new ResourceSetImpl();
		val Resource modelResource = resourceSet.createResource(modelURI);
		modelResource.load(null);
		val result = modelResource.getURIFragment(modelResource.contents.head)
		modelResource.unload
		return result
	}

	override getModelInitializationArguments() {
		model.initArgument
	}

	override getModelInitializationMethod() {
		language.initializationMethod
	}
}
