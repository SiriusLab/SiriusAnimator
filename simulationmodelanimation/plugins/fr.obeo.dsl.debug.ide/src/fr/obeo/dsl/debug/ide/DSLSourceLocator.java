/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.dsl.debug.ide;

import fr.obeo.dsl.debug.ide.adapter.DSLStackFrameAdapter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2;
import org.eclipse.emf.ecore.EObject;

/**
 * DSL debugger {@link org.eclipse.debug.core.model.ISourceLocator ISourceLocator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLSourceLocator implements IPersistableSourceLocator2 {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISourceLocator#getSourceElement(org.eclipse.debug.core.model.IStackFrame)
	 */
	public Object getSourceElement(IStackFrame stackFrame) {
		final Object res;
		if (stackFrame instanceof DSLStackFrameAdapter) {
			final DSLStackFrameAdapter eStackFrame = (DSLStackFrameAdapter)stackFrame;
			final EObject instruction = eStackFrame.getCurrentInstruction();
			if (instruction != null) {
				res = instruction;
			} else {
				res = eStackFrame.getContext();
			}
		} else {
			res = null;
		}
		return res;
	}

	/**
	 * {@inheritDoc} Unused method of IPersistableSourceLocator2
	 * 
	 * @see org.eclipse.debug.core.model.IPersistableSourceLocator#getMemento()
	 */
	public String getMemento() throws CoreException {
		return "";
	}

	/**
	 * {@inheritDoc} Unused method of IPersistableSourceLocator2
	 *
	 * @see org.eclipse.debug.core.model.IPersistableSourceLocator#initializeFromMemento(java.lang.String)
	 */
	public void initializeFromMemento(String memento) throws CoreException {
	}

	/**
	 * {@inheritDoc} Unused method of IPersistableSourceLocator2
	 *
	 * @see org.eclipse.debug.core.model.IPersistableSourceLocator#initializeDefaults(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeDefaults(ILaunchConfiguration configuration) throws CoreException {
	}

	/**
	 * {@inheritDoc} Unused method of IPersistableSourceLocator2
	 *
	 * @see org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2#initializeFromMemento(java.lang.String,
	 *      org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFromMemento(String memento, ILaunchConfiguration configuration)
			throws CoreException {
	}

	/**
	 * {@inheritDoc} Unused method of IPersistableSourceLocator2
	 *
	 * @see org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
