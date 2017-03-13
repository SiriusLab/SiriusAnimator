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

import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;

public interface IMultiDimensionalTraceAddon<StepSubType extends Step<?>, StateSubType extends State<?,?>, TracedObjectSubType extends TracedObject<?>, DimensionSubType extends Dimension<?>, ValueSubType extends Value<?>> extends IEngineAddon {

	ITraceExplorer<StepSubType, StateSubType, TracedObjectSubType, DimensionSubType, ValueSubType> getTraceExplorer();
	
	ITraceConstructor getTraceConstructor();
	
	ITraceExtractor<StepSubType, StateSubType, TracedObjectSubType, DimensionSubType, ValueSubType> getTraceExtractor();
	
	ITraceNotifier getTraceNotifier();

	IStepFactory getFactory();

}
