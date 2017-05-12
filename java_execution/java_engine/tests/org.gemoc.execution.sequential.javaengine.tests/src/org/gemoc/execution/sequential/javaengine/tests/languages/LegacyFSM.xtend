package org.gemoc.execution.sequential.javaengine.tests.languages

import org.gemoc.executionframework.test.lib.ILanguageWrapper

class LegacyFSM implements ILanguageWrapper {
	
	override getEntryPoint() {
		"public static void org.gemoc.sample.legacyfsm.xsfsm.xsfsm.aspects.StateMachineAspect.main(org.gemoc.sample.legacyfsm.xsfsm.xsfsm.fsm.StateMachine)"
	}
	
	override getLanguageName() {
		"org.gemoc.sample.legacyfsm.xsfsm.XSFSM"
	}
	
	override getInitializationMethod() {
		"org.gemoc.sample.legacyfsm.xsfsm.xsfsm.aspects.StateMachineAspect.initializeModel"
	}
	
	
	
}
