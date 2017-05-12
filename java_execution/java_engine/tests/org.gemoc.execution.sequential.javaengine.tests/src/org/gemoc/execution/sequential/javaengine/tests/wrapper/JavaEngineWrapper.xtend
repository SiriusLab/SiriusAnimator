package org.gemoc.execution.sequential.javaengine.tests.wrapper

import java.util.Set
import org.eclipse.emf.common.util.URI
import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine
import org.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext
import org.gemoc.executionframework.test.lib.IEngineWrapper
import org.gemoc.executionframework.test.lib.ILanguageWrapper
import org.gemoc.executionframework.test.lib.impl.TestRunConfiguration
import org.gemoc.xdsmlframework.api.core.ExecutionMode
import org.gemoc.xdsmlframework.api.core.IExecutionContext
import org.gemoc.xdsmlframework.api.core.IRunConfiguration
import org.gemoc.executionframework.test.lib.IExecutableModel

class JavaEngineWrapper implements IEngineWrapper {

	private PlainK3ExecutionEngine engine

	override run() {
		engine.start();
		engine.joinThread();
		if (engine.error != null)
			throw engine.error
	}

	override prepare(ILanguageWrapper wrapper, IExecutableModel model, Set<String> addons, URI uri) {
		engine = new PlainK3ExecutionEngine()
		val IRunConfiguration runConf = new TestRunConfiguration(model, uri,wrapper,addons)
		val IExecutionContext exeContext = new SequentialModelExecutionContext(runConf, ExecutionMode::Run);
		exeContext.initializeResourceModel();
		engine.initialize(exeContext)
		engine.stopOnAddonError = true;
	}
	
	override getRealEngine() {
		return engine
	}
	

}
