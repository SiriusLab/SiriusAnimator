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

import org.eclipse.gemoc.dsl.debug.ide.Activator;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.ui.launch.FilteredFileContentProvider;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.window.Window;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A Sirius implementation of {@link org.eclipse.gemoc.dsl.debug.ide.ui.launch.DSLLaunchConfigurationTab
 * DSLLaunchConfigurationTab}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLLaunchConfigurationTab extends org.eclipse.gemoc.dsl.debug.ide.ui.launch.DSLLaunchConfigurationTab {

	/**
	 * The {@link Text} used for the {@link AbstractDSLLaunchConfigurationDelegateSiriusUI#SIRIUS_RESOURCE_URI}.
	 */
	private Text siriusResourceURIText;

	/**
	 * Constructor.
	 * 
	 * @param extensions
	 *            supported file extensions
	 */
	public DSLLaunchConfigurationTab(String[] extensions) {
		super(extensions);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI, "");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(final ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		disableUpdate = true;

		siriusResourceURIText.setText("");

		try {
			siriusResourceURIText.setText(configuration.getAttribute(
					AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, ""));
		} catch (CoreException e) {
			Activator.getDefault().error(e);
		}

		disableUpdate = false;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI,
				siriusResourceURIText.getText());
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean res = super.isValid(launchConfig);
		String errorMessage = null;

		try {
			if (res) {
				String siriusResourceURI = launchConfig.getAttribute(
						AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI, "");
				IFile resourceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(siriusResourceURI));
				Resource resource = null;
				if (resourceFile.exists()) {
					final ResourceSet rs = new ResourceSetImpl();
					try {
						resource = rs.getResource(URI.createPlatformResourceURI(siriusResourceURI, true),
								true);
					} catch (WrappedException e) {
						errorMessage = "Sirius session model can't be loaded: "
								+ ((WrappedException)e).exception().getLocalizedMessage() + ".";
					}
				}
				res = resource != null;
				setErrorMessage(errorMessage);
			}
		} catch (IllegalArgumentException e) {
			setErrorMessage("Invalid model file selected.");
		} catch (CoreException e) {
			setErrorMessage("Invalid model file selected.");
		}

		return res;
	}

	@Override
	public boolean canSave() {
		return super.canSave() && (!siriusResourceURIText.getText().isEmpty());
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		Composite topControl = new Composite(parent, SWT.NONE);
		topControl.setLayout(new GridLayout(1, false));

		final Group mgroup = new Group(topControl, SWT.NONE);
		mgroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		mgroup.setText("Model");
		mgroup.setLayout(new GridLayout(2, false));

		siriusResourceURIText = new Text(mgroup, SWT.BORDER);
		siriusResourceURIText.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(final ModifyEvent e) {
				if (!disableUpdate) {
					updateLaunchConfigurationDialog();
				}
			}
		});
		siriusResourceURIText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Button browseResourceButton = new Button(mgroup, SWT.NONE);
		browseResourceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				openSiriusModelSelection(parent);
			}

		});
		browseResourceButton.setText("Browse...");
	}

	/**
	 * Opens the model selection dialog.
	 * 
	 * @param parent
	 *            the parent {@link Composite}
	 */
	private void openSiriusModelSelection(final Composite parent) {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(parent.getShell(),
				new WorkbenchLabelProvider(), new FilteredFileContentProvider(
						new String[] {SiriusUtil.SESSION_RESOURCE_EXTENSION }));
		dialog.setTitle("Select model file");
		dialog.setMessage("Select the model file to execute:");
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (dialog.open() == Window.OK) {
			siriusResourceURIText.setText(((IFile)dialog.getFirstResult()).getFullPath().toString());
		}
	}

}
