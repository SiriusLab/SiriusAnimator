package fr.inria.diverse.trace.benchmark.utils

import org.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext
import org.gemoc.executionframework.engine.commons.EngineContextException
import org.gemoc.xdsmlframework.api.core.ExecutionMode
import org.gemoc.xdsmlframework.api.core.IRunConfiguration

class BenchmarkExecutionModelContext extends SequentialModelExecutionContext {
	
	new(IRunConfiguration runConfiguration) throws EngineContextException {
		super(runConfiguration, ExecutionMode.Run)
	}
	
}
