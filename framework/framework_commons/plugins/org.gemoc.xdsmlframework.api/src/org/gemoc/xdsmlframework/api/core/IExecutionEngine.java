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
package org.gemoc.xdsmlframework.api.core;

import java.util.Deque;
import java.util.Set;

import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;

/**
 * The interface of the GEMOC Execution Engine. The Execution Engine is an
 * entity able to execute models conforming to an xDSML as defined in the GEMOC
 * ANR INS project. This API allows the caller to initialize the engine for a
 * given model, and to run the engine in different ways. It also allows the
 * caller to influence the constraints of the MoC at runtime.
 * 
 * @author Didier Vojtisek<didier.vojtisek@inria.fr>
 * 
 */
public interface IExecutionEngine extends IDisposable {
	
	/**
	 * In case of nested calls, indicate the current stack of model specific event occurrences.
	 * @return the current stack of {@link MSEOccurrence}
	 */
	Deque<MSEOccurrence> getCurrentStack();

	/**
	 * Provides the model specific event occurrence of the current step 
	 * @return the current MSEOccurrence
	 */
	MSEOccurrence getCurrentMSEOccurrence();

	/**
	 * Starts the {@link IExecutionEngine}.
	 */
	void start();

	/**
	 * Asks the engine to stop
	 */
	void stop();

	/**
	 * indicates the engine status {@link EngineStatus}
	 * @return the engine status
	 */
	EngineStatus getEngineStatus();
	
	/**
	 * set the engine status {@link EngineStatus}
	 * @param status the new status
	 */
	void setEngineStatus(RunStatus status);

	/**
	 * Indicates if an {@link IEngineAddon} of the given type is linked to the engine
	 * @param type of the searched addon
	 * @return true if the engine has the addon, false otherwise.
	 */
	<T extends IEngineAddon> boolean hasAddon(Class<T> type);

	/**
	 * get the first {@link IEngineAddon} of the required type associated to this engine.
	 * @param type searched type
	 * @return The {@link IEngineAddon} of the given type if it exists.
	 */
	<T extends IEngineAddon> T getAddon(Class<T> type);

	/**
	 * get the execution context
	 * @return the {@link IExecutionContext}
	 */
	IExecutionContext getExecutionContext();

	/**
	 * get the run status
	 * @return the {@link RunStatus}
	 */
	RunStatus getRunningStatus();

	/**
	 * get all the {@link IEngineAddon} of the required type associated to this engine.
	 * @param type searched type
	 * @return a set of {@link IEngineAddon} of the given type.
	 */
	<T> Set<T> getAddonsTypedBy(Class<T> type);

	/**
	 * Ask the engine to initialize
	 * @param executionContext the {@link IExecutionContext}
	 */
	void initialize(IExecutionContext executionContext);
	
	/**
	 * Create a {@link LaunchConfiguration} for the Trace based on the engine RunConfiguration.
	 * @return a {@link fr.inria.diverse.trace.commons.model.trace.LaunchConfiguration}
	 */
	default LaunchConfiguration extractLaunchConfiguration() {
		return null;
	}

	/**
	 * get the engine kind name
	 * @return a user display name for the engine kind (will be used to compute
	 *         the full name of the engine instance)
	 */
	String engineKindName();

	/**
	 * get the engine name
	 * @return a display name to identify this engine
	 */
	String getName();
}
