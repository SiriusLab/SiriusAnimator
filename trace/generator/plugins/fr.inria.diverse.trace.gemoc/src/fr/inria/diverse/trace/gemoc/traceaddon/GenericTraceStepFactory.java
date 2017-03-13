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

import java.util.List;

import fr.inria.diverse.trace.commons.model.trace.MSE;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictraceFactory;
import fr.inria.diverse.trace.gemoc.api.IStepFactory;

public class GenericTraceStepFactory implements IStepFactory {

	@Override
	public Step<?> createStep(MSE mse, List<Object> parameters, List<Object> result) {
		final GenericSequentialStep step = GenerictraceFactory.eINSTANCE.createGenericSequentialStep();
		fr.inria.diverse.trace.commons.model.trace.MSEOccurrence mseocc = fr.inria.diverse.trace.commons.model.trace.TraceFactory.eINSTANCE.createMSEOccurrence();
		mseocc.setMse(mse);
		mseocc.getParameters().addAll(parameters);
		mseocc.getResult().addAll(result);
		step.setMseoccurrence(mseocc);
		return step;
	}

}
