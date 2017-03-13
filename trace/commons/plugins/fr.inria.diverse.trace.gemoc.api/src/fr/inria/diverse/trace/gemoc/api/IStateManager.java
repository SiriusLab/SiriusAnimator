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

import fr.inria.diverse.trace.commons.model.trace.State;

/**
 * Classes implementing this interface are responsible for the restoration of the model in any state.
 * 
 * @author dorian
 *
 * @param <StateSubType>
 */
public interface IStateManager<StateSubType extends State<?,?>> {
	
	/**
	 * Restores the model in the provided state
	 * @param state The state to restore the model to
	 */
	void restoreState(StateSubType state);
	
}
