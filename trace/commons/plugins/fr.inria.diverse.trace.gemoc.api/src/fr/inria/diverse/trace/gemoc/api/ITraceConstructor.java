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
package fr.inria.diverse.trace.gemoc.api;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange;

import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.trace.Step;

public interface ITraceConstructor {

	/**
	 * Records a new state in the trace if the provided model changes actually lead to a new state.
	 * @param modelChanges The list of model changes that happened since the last recorded state was created
	 */
	void addState(List<ModelChange> modelChanges);

	/**
	 * Records the start of a new step.
	 * @param step The step that has been started
	 */
	void addStep(Step<?> step);

	/**
	 * Records the end of the top step in the call stack.
	 * @param step The step that is supposed to end
	 */
	void endStep(Step<?> step);

	/**
	 * Creates and returns a new trace with the given launch configuration.
	 * @param launchConfiguration The launch configuration that was used to launch the execution recorded by the trace
	 * @return The newly created trace
	 */
	EObject initTrace(LaunchConfiguration launchConfiguration);

	/**
	 * Serializes the trace to a predefined URI.
	 */
	void save();

	/**
	 * Serializes the trace to the provided URI.
	 * @param uri The URI to save the trace to
	 */
	void save(URI uri);

	/**
	 * @return Whether this trace constructor creates a trace for all dynamic data of the language or not
	 */
	boolean isPartialTraceConstructor();

}
