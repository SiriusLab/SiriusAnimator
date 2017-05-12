package org.gemoc.executionframework.test.javaengine.languages

import org.gemoc.executionframework.test.lib.ILanguageWrapper

class TFSM implements ILanguageWrapper {

	override getEntryPoint() {
		"public static void org.gemoc.sample.tfsm.sequential.xtfsm.aspects.TimedSystemAspect.main(org.gemoc.sample.tfsm.sequential.xtfsm.tfsm.TimedSystem)"
	}

	override getLanguageName() {
		"org.gemoc.sample.tfsm.sequential.XTfsm"
	}

	override getInitializationMethod() {
		"org.gemoc.sample.tfsm.sequential.xtfsm.aspects.TimedSystemAspect.initializeModel"
	}
	

}
