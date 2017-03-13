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

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Value;

public interface ITraceListener {
	
	void statesAdded(List<State<?,?>> states);
	
	void stepsStarted(List<Step<?>> steps);
	
	void stepsEnded(List<Step<?>> steps);
	
	void valuesAdded(List<Value<?>> values);
	
	void dimensionsAdded(List<Dimension<?>> dimensions);
}
