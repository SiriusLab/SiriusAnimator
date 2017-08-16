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
package org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.dsl.debug.ide.adapter.IDSLCurrentInstructionListener;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.services.AbstractDSLDebuggerServices;
import org.eclipse.gemoc.dsl.debug.ide.ui.launch.DSLLaunchConfigurationTab;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.ui.PlatformUI;

/**
 * A Sirius implementation of
 * {@link org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate
 * AbstractDSLLaunchConfigurationDelegate} with {@link org.eclipse.debug.ui.ILaunchShortcut ILaunchShortcut}
 * support.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractDSLLaunchConfigurationDelegateSiriusUI extends org.eclipse.gemoc.dsl.debug.ide.ui.launch.AbstractDSLLaunchConfigurationDelegateUI {

	/**
	 * The Sirius {@link org.eclipse.emf.ecore.resource.Resource Resource}
	 * {@link org.eclipse.emf.ecore.resource.Resource#getURI() URI}.
	 */
	public static final String SIRIUS_RESOURCE_URI = SiriusUtil.SESSION_RESOURCE_EXTENSION + RESOURCE_URI;

	@Override
	protected ILaunchConfiguration[] createLaunchConfiguration(IResource file, EObject firstInstruction,
			String mode) throws CoreException {
		final ILaunchConfiguration[] res;
		if (SiriusUtil.SESSION_RESOURCE_EXTENSION.equals(file.getFileExtension())) {
			final IResource semanticFile;
			if (firstInstruction != null) {
				semanticFile = getLaunchableResource(new StructuredSelection(firstInstruction));
				res = super.createLaunchConfiguration(semanticFile, firstInstruction, mode);
			} else {
				final Session session = SessionManager.INSTANCE.getSession(URI.createPlatformResourceURI(file
						.getFullPath().toString(), true), new NullProgressMonitor());
				session.getSemanticResources();
				// prompt the selection of an instruction from the Session EResources
				EObject selectedInstruction = DSLLaunchConfigurationTab.openFirstInstructionSelection(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), session
								.getTransactionalEditingDomain().getResourceSet());
				if (selectedInstruction != null) {
					semanticFile = getLaunchableResource(new StructuredSelection(selectedInstruction));
					res = super.createLaunchConfiguration(semanticFile, selectedInstruction, mode);
				} else {
					// TODO we have to few information to create the launch configuration prompt the user for
					// aird + first instruction ?
					semanticFile = null;
					res = super.createLaunchConfiguration(file, firstInstruction, mode);
				}
			}
			for (ILaunchConfiguration config : res) {
				if (config instanceof ILaunchConfigurationWorkingCopy) {
					((ILaunchConfigurationWorkingCopy)config).setAttribute(SIRIUS_RESOURCE_URI, file
							.getFullPath().toString());
					if (semanticFile != null) {
						IResource[] resources = config.getMappedResources();
						resources = Arrays.copyOf(resources, resources.length + 1);
						resources[resources.length - 1] = file;
						((ILaunchConfigurationWorkingCopy)config).setMappedResources(resources);
					}
					((ILaunchConfigurationWorkingCopy)config).doSave();
				}
			}
		} else {
			// TODO? assume the file is the first instruction resource
			res = super.createLaunchConfiguration(file, firstInstruction, mode);
		}
		return res;
	}

	@Override
	protected List<IDSLCurrentInstructionListener> getCurrentInstructionListeners() {
		final List<IDSLCurrentInstructionListener> res = super.getCurrentInstructionListeners();
		res.add(AbstractDSLDebuggerServices.LISTENER);
		return res;
	}

	// @Override
	// protected void launch(EObject firstInstruction, ILaunchConfiguration configuration, String mode,
	// ILaunch launch, IProgressMonitor monitor) throws CoreException {
	// final URI sessionResourceURI = URI.createPlatformResourceURI(configuration.getAttribute(
	// SIRIUS_RESOURCE_URI, ""), true);
	// super.launch(firstInstruction, configuration, mode, launch, monitor);
	// }
}
