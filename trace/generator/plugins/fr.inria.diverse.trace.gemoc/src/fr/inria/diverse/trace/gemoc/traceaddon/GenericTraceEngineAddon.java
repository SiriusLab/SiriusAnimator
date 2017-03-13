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
package fr.inria.diverse.trace.gemoc.traceaddon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.gemoc.api.IStateManager;
import fr.inria.diverse.trace.gemoc.api.IStepFactory;
import fr.inria.diverse.trace.gemoc.api.ITraceConstructor;

public class GenericTraceEngineAddon extends AbstractTraceAddon {

	private GenericTraceStepFactory factory = null;
	
	private final String tmpFilePath = System.getProperty("tmpFileProperty");
	private File outputTmp;
	private FileOutputStream outputTmpStream;
	private PrintWriter outputTmpWriter;
	
	@Override
	public IStepFactory getFactory() {
		if (factory == null) {
			factory = new GenericTraceStepFactory();
		}
		return factory;
	}

	@Override
	public ITraceConstructor constructTraceConstructor(Resource modelResource,
			Resource traceResource, Map<EObject, TracedObject<?>> exeToTraced) {
		return new GenericTraceConstructor(modelResource, traceResource, exeToTraced);
	}

	@Override
	public boolean isAddonForTrace(EObject traceRoot) {
		return traceRoot instanceof Trace;
	}

	@Override
	public IStateManager<State<?, ?>> constructStateManager(Resource modelResource, Map<TracedObject<?>, EObject> tracedToExe) {
		return new GenericStateManager(modelResource, tracedToExe, restoreLogger);
	}

}
