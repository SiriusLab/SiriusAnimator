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
package fr.inria.diverse.trace.commons.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.MSE;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.SmallStep;
import fr.inria.diverse.trace.commons.model.trace.Step;


public class StepHelper {

	public static String getStepName(Step<?> step) {
		//returns the emf id or the java id if the emf id is null
		return "LogicalStep ["+(EcoreUtil.getID(step) !=  null ? EcoreUtil.getID(step):System.identityHashCode(step)) +"]";
	}

	public static List<MSE> getMSEs(Step<?> instruction) {
		ArrayList<MSE> l = new ArrayList<MSE>();
		for (MSEOccurrence o : collectAllMSEOccurrences(instruction))
		{
			l.add(o.getMse());
		}
		return l;
	}
	
	
	public static List<MSEOccurrence> collectAllMSEOccurrences(Step<?> instruction) {
		List<MSEOccurrence> res = new ArrayList<MSEOccurrence>();
		if (instruction instanceof SmallStep){
			res.add(((SmallStep<?>)instruction).getMseoccurrence());
			return res;
		}
		if (instruction instanceof BigStep){
			for(Object o : ((BigStep<?,?>)instruction).getSubSteps()){
				Step<?> s = (Step<?>)o;
				res.addAll(collectAllMSEOccurrences(s));
			}
			return res;
		}
		return res;
	}
	
	public static List<MSE> collectAllMSEs(Step<?> instruction) {
		List<MSE> res = new ArrayList<MSE>();
		for (MSEOccurrence occ : collectAllMSEOccurrences(instruction)){
			res.add(occ.getMse());
		}
		return res;
	}
}
