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
package fr.inria.diverse.trace.benchmark.api;

import java.io.File;
import java.util.Collection;

import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;

import fr.inria.diverse.trace.benchmark.Language;

public interface IDebuggerHelper {

	boolean canJump();

	void jump(int i);

	int getTraceSize();

	int getTraceMemoryFootprint(Language l, File dumpFolder, int traceSize)  throws Exception ;
	
	int computeTraceMemoryFootprint(Language l, File dumpFile, int traceSize) throws Exception;

	String getDebuggerName();

	Collection<? extends String> getAddons();

	void setExecutionEngine(PlainK3ExecutionEngine _executionEngine);

	void init();
	
	void unloadTraceResource();

}