package org.gemoc.executionframework.test.javaengine.tests

import org.gemoc.executionframework.test.javaengine.JavaEngineWrapper
import org.gemoc.executionframework.test.javaengine.languages.LegacyFSM
import org.gemoc.executionframework.test.javaengine.languages.TFSM
import org.gemoc.executionframework.test.lib.impl.TestHelper
import org.gemoc.executionframework.test.lib.impl.TestModel
import org.junit.Test

class JavaEngineTests {

	public static val tfsmModelsPlugin = "org.gemoc.sample.tfsm.sequential.single_traffic_light_sample"
	public static val legacyFsmModelsPlugin = "org.gemoc.sample.legacyfsm.model_examples"

	@Test
	def void testTFSM() {
		TestHelper::testWithoutExtraAddons(new JavaEngineWrapper(), new TFSM(),
			new TestModel(tfsmModelsPlugin, "/", "single_traffic_light.xtfsm", "",""))
	}

	@Test
	def void testTFSMGenericTrace() {
		TestHelper::testWithGenericTrace(new JavaEngineWrapper(), new TFSM(),
			new TestModel(tfsmModelsPlugin, "/", "single_traffic_light.xtfsm", "",""))
	}

	@Test
	def void testLegacyFSM() {
		TestHelper::testWithGenericTrace(new JavaEngineWrapper(), new LegacyFSM(),
			new TestModel(legacyFsmModelsPlugin, "/", "BitShifting.fsm", "000101010","?lang=org.gemoc.sample.legacyfsm.xsfsm.XSFSM"))
	}

	@Test
	def void testLegacyFSMGenericTrace() {
		TestHelper::testWithoutExtraAddons(new JavaEngineWrapper(), new LegacyFSM(),
			new TestModel(legacyFsmModelsPlugin, "/", "BitShifting.fsm", "000101010","?lang=org.gemoc.sample.legacyfsm.xsfsm.XSFSM"))
	}

}
