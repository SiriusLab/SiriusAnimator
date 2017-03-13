/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.gemoc.xdsmlframework.api.engine_addon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;

import fr.inria.diverse.trace.commons.model.trace.Step;

public class DefaultEngineAddon implements IEngineAddon {

	@Override
	public void engineAboutToStart(IExecutionEngine engine) {
	}

	@Override
	public void engineStarted(IExecutionEngine executionEngine) {
	}

	@Override
	public void aboutToSelectStep(IExecutionEngine engine, Collection<Step> steps) {
	}

	@Override
	public void stepSelected(IExecutionEngine engine, Step<?> selectedStep) {
	}

	@Override
	public void engineStopped(IExecutionEngine engine) {
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine executionEngine, Step<?> stepToApply) {
	}

	@Override
	public void engineStatusChanged(IExecutionEngine engineRunnable, RunStatus newStatus) {
	}

	@Override
	public void engineAboutToStop(IExecutionEngine engine) {
	}

	@Override
	public void stepExecuted(IExecutionEngine engine, Step<?> stepExecuted) {
	}

	@Override
	public void proposedStepsChanged(IExecutionEngine engine, Collection<Step> steps) {
	}

	@Override
	public void engineAboutToDispose(IExecutionEngine engine) {
	}

	@Override
	public List<String> validate(List<IEngineAddon> otherAddons) {
		return new ArrayList<String>();
	}
}
