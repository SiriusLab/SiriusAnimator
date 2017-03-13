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
package org.gemoc.executionframework.extensions.sirius.services;

import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

import fr.inria.diverse.trace.commons.model.trace.Step;

/**
 * Animator interface.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * 
 */
public interface IModelAnimator extends IEngineAddon {

	/**
	 * Activates the given {@link LogicalStep}.
	 * 
	 * @param step
	 *            the {@link LogicalStep}
	 * @param context
	 *            the context {@link Object}
	 */
	void activate(Object context, Step<?> step);

	/**
	 * Clears {@link IModelAnimator#activate(LogicalStep) activated}
	 * {@link LogicalStep}.
	 * 
	 * @param context
	 *            the context {@link Object}
	 */
	void clear(Object context);

}
