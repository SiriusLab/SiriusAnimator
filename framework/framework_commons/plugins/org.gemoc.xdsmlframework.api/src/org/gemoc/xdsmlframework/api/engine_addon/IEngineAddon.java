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

import java.util.Collection;
import java.util.List;

import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;

import fr.inria.diverse.trace.commons.model.trace.Step;

public interface IEngineAddon {

	/**
	 * Operation called before the engine starts
	 */
	public void engineAboutToStart(IExecutionEngine engine);

	/**
	 * Operation called after the engine have started
	 */
	public void engineStarted(IExecutionEngine executionEngine);

	default public void engineInitialized(IExecutionEngine executionEngine) {
		
	}
	
	public void engineAboutToStop(IExecutionEngine engine);

	/**
	 * Operation called after the engine has been stopped
	 */
	public void engineStopped(IExecutionEngine engine);

	
	/**
	 * Operation before the engine has been disposed (and after the engine has
	 * been stopped)
	 */
	public void engineAboutToDispose(IExecutionEngine engine);

	/**
	 * Operation called before the Step has been chosen
	 */
	public void aboutToSelectStep(IExecutionEngine engine, Collection<Step> steps);

	public void proposedStepsChanged(IExecutionEngine engine, Collection<Step> steps);

	/**
	 * Operation called after the Step has been chosen It also returns the
	 * chosen Step
	 */
	public void stepSelected(IExecutionEngine engine, Step<?> selectedStep);

	public void aboutToExecuteStep(IExecutionEngine engine, Step<?> stepToExecute);

	public void stepExecuted(IExecutionEngine engine, Step<?> stepExecuted);

	public void engineStatusChanged(IExecutionEngine engine, RunStatus newStatus);

	/**
	 * This operation check the current addon compatibility with elements in
	 * 'otherAddons'
	 * 
	 * @return A list of error messages if the check failed or an empty list
	 *         otherwise.
	 */
	public List<String> validate(List<IEngineAddon> otherAddons);

}
