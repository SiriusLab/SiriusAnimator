package org.gemoc.executionframework.test.lib.impl

import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon
import org.gemoc.xdsmlframework.api.core.IExecutionEngine
import fr.inria.diverse.trace.commons.model.trace.Step
import java.util.Collection
import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

class TestEngineAddon implements IEngineAddon {

	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var int amountOfStepsExecuted = 0
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var boolean engineAboutToStart = false
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var boolean engineAboutToStop = false
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var boolean engineStarted = false
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var boolean engineStopped = false
	@Accessors(PRIVATE_SETTER,PUBLIC_GETTER)
	var boolean engineAboutToDispose = false

	val int shouldStopAfter

	new(int shouldStopAfter) {
		this.shouldStopAfter = shouldStopAfter
	}

	override aboutToExecuteStep(IExecutionEngine engine, Step<?> stepToExecute) {
	}

	override aboutToSelectStep(IExecutionEngine engine, Collection<Step<?>> steps) {
	}

	override engineAboutToDispose(IExecutionEngine engine) {
		engineAboutToDispose = true
	}

	override engineAboutToStart(IExecutionEngine engine) {
		engineAboutToStart = true
	}

	override engineAboutToStop(IExecutionEngine engine) {
		engineAboutToStop = true
	}

	override engineStarted(IExecutionEngine executionEngine) {
		engineStarted = true
	}

	override engineStatusChanged(IExecutionEngine engine, RunStatus newStatus) {
	}

	override engineStopped(IExecutionEngine engine) {
		engineStopped = true
	}

	override proposedStepsChanged(IExecutionEngine engine, Collection<Step<?>> steps) {
	}

	override stepExecuted(IExecutionEngine engine, Step<?> stepExecuted) {
		amountOfStepsExecuted++
		if (shouldStopAfter != -1 && shouldStopAfter < amountOfStepsExecuted) {
			engine.stop
		}
	}

	override stepSelected(IExecutionEngine engine, Step<?> selectedStep) {
	}

	override validate(List<IEngineAddon> otherAddons) {
	}

}
