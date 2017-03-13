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
package fr.inria.diverse.trace.benchmark.utils;

import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext;
import org.gemoc.executionframework.engine.Activator;
import org.gemoc.executionframework.engine.commons.EngineContextException;
import org.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

import fr.inria.diverse.trace.benchmark.runconf.BenchmarkRunConfiguration;
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon;

public class EngineHelper {

	static class DefaultSearchRequestor extends SearchRequestor {

		public IType _binaryType;

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			_binaryType = (IType) match.getElement();
			System.out.println(match.getElement());
		}
	}

	private PlainK3ExecutionEngine _executionEngine;

	// Parameter to create execution engine
	private IExecutionContext executionContext;

	public void prepareEngine(BenchmarkRunConfiguration runConf) throws CoreException, EngineContextException {
		ExecutionMode executionMode = ExecutionMode.Run;
		executionContext = new SequentialModelExecutionContext(runConf, executionMode);
		executionContext.initializeResourceModel();
		_executionEngine = new PlainK3ExecutionEngine();
		_executionEngine.initialize(executionContext);
		_executionEngine.stopOnAddonError = true;
	}

	public void execute() {
		_executionEngine.start();
		_executionEngine.joinThread();
	}
	
	public IMultiDimensionalTraceAddon getEngineAddon() {
		return _executionEngine.getAddon(IMultiDimensionalTraceAddon.class);
	}

	public Resource getModel() {
		return _executionEngine.getExecutionContext().getResourceModel();
	}

	public void removeStoppedEngines() {
		for (Entry<String, IExecutionEngine> engineEntry : Activator.getDefault().gemocRunningEngineRegistry.getRunningEngines().entrySet()) {
			switch (engineEntry.getValue().getRunningStatus()) {
			case Stopped:
				Activator.getDefault().gemocRunningEngineRegistry.unregisterEngine(engineEntry.getKey());
				break;
			default:
			}
		}
	}

	public void clearCommandStackAndAdapters() {
		TransactionUtil.getEditingDomain(executionContext.getResourceModel()).getCommandStack().flush();
		executionContext.getResourceModel().eAdapters().clear();
		executionContext.getResourceModel().unload();
	}
}
