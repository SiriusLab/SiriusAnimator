package org.eclipse.gemoc.executionframework.test.lib.impl

import org.eclipse.gemoc.executionframework.test.lib.impl.TestHelper.TestResult
import org.eclipse.gemoc.trace.commons.model.trace.Step
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.DefaultEngineAddon
import org.eclipse.xtend.lib.annotations.Accessors

class TestEngineAddon extends DefaultEngineAddon {

	val int shouldStopAfter
	var long timeStart

	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	val TestResult testResult

	new(int shouldStopAfter) {
		this.shouldStopAfter = shouldStopAfter
		this.testResult = new TestResult
	}

	override engineAboutToDispose(IExecutionEngine engine) {
		testResult.engineAboutToDispose = true
	}

	override engineAboutToStart(IExecutionEngine engine) {
		testResult.engineAboutToStart = true
	}

	override engineAboutToStop(IExecutionEngine engine) {
		testResult.engineAboutToStop = true
		val timeEnd = System.nanoTime
		testResult.executionDuration = timeEnd - timeStart
	}

	override engineStarted(IExecutionEngine executionEngine) {
		testResult.engineStarted = true
		timeStart = System.nanoTime
	}

	override engineStopped(IExecutionEngine engine) {
		testResult.engineStopped = true
	}

	override stepExecuted(IExecutionEngine engine, Step<?> stepExecuted) {
		testResult.amountOfStepsExecuted++
		if (shouldStopAfter != -1 && shouldStopAfter < testResult.amountOfStepsExecuted) {
			engine.stop
		}
	}

}
