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
package org.gemoc.sequential_addons.multidimensional.timeline.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.gemoc.executionframework.engine.ui.debug.AbstractGemocDebugger;
import org.gemoc.executionframework.engine.ui.launcher.AbstractGemocLauncher;
import org.gemoc.executionframework.ui.views.engine.EngineSelectionDependentViewPart;
import org.gemoc.executionframework.ui.views.engine.actions.AbstractEngineAction;
import org.gemoc.sequential_addons.multidimensional.timeline.Activator;
import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.gemoc.xdsmlframework.api.core.IRunConfiguration;

import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon;
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor;
import fr.inria.diverse.trace.gemoc.traceaddon.AbstractTraceAddon;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class MultidimensionalTimelineViewPart extends EngineSelectionDependentViewPart {

	public static final String ID = "org.gemoc.sequential_addons.multidimensional.timeline.views.timeline.MultidimensionalTimeLineView";

	private FXCanvas fxCanvas;
	
	private MultidimensionalTimelineRenderer timelineRenderer;

	private IMultiDimensionalTraceAddon<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceAddon;

	final private List<EObject> statesToBreakTo = new ArrayList<>();
	
	private AbstractGemocDebugger debugger = null;
	
	private IExecutionEngine engine = null;

	public List<EObject> getStatesToBreakTo() {
		return Collections.unmodifiableList(statesToBreakTo);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		timelineRenderer.setTraceExplorer(null);
		timelineRenderer.setTraceExtractor(null);
	}

	@Override
	public void createPartControl(Composite parent) {
		fxCanvas = new FXCanvas(parent, SWT.NONE);
		timelineRenderer = new MultidimensionalTimelineRenderer();
		Scene scene = new Scene(timelineRenderer);
		fxCanvas.setScene(scene);
		parent.getShell().addListener(SWT.Resize, (e) -> {
			timelineRenderer.refresh();
		});
		buildMenu(parent.getShell());

		final Supplier<Integer> getLastClickedState = timelineRenderer.getLastClickedStateSupplier();

		final Menu menu = new Menu(fxCanvas);
		MenuItem launchAndBreakAtStateMenuItem = new MenuItem(menu, SWT.NONE);
		launchAndBreakAtStateMenuItem.setText("Relaunch and break at this state");
		launchAndBreakAtStateMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				breakAtStateIndex = getLastClickedState.get();
				if (debugger != null && !debugger.isTerminated()) {
					debugger.terminate();
				}
				if (engine != null) {
					engine.stop();
				}
				launchConfigFromTrace();
			}
		});
		MenuItem launchAndBreakAtVectorMenuItem = new MenuItem(menu, SWT.NONE);
		launchAndBreakAtVectorMenuItem.setText("Relaunch and break at this value vector");
		launchAndBreakAtVectorMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final State<?,?> state = traceAddon.getTraceExtractor().getState(getLastClickedState.get());
				breakAtVectorState = state;
				if (debugger != null && !debugger.isTerminated()) {
					debugger.terminate();
				}
				if (engine != null) {
					engine.stop();
				}
				launchConfigFromTrace();
			}
		});

		menu.addListener(SWT.Show, (event) -> {
			menu.setVisible(true);
		});
		
		Consumer<List<Boolean>> displayMenu = (l) -> {
			int i = 0;
			for (boolean b : l) {
				menu.getItem(i).setEnabled(b);
				i++;
			}
			Event event = new Event();
			event.type = SWT.Show;
			event.button = SWT.BUTTON2;
			menu.notifyListeners(SWT.Show, event);
		};

		timelineRenderer.setMenuDisplayer(displayMenu);
	}

	private void buildMenu(Shell shell) {

		addActionToToolbar(new AbstractEngineAction(Action.AS_PUSH_BUTTON) {

			private FileDialog saveAsDialog;

			@Override
			protected void init() {
				super.init();
				setText("Save Trace");
				setToolTipText("Save Trace");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/save.gif");
				setImageDescriptor(id);
				setEnabled(true);

				saveAsDialog = new FileDialog(shell, SWT.SAVE);
				saveAsDialog.setFilterNames(new String[] { "Trace Files", "All Files (*.*)" });
				saveAsDialog.setFilterExtensions(new String[] { "*.trace", "*.*" });
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				saveAsDialog.setText("Save As");
				String filePath = saveAsDialog.open();
				
				URI uri = URI.createFileURI(filePath);
				traceAddon.getTraceConstructor().save(uri);
			}
		});

		addActionToToolbar(new AbstractEngineAction(Action.AS_PUSH_BUTTON) {

			@Override
			protected void init() {
				super.init();
				setText("Remove Trace");
				setToolTipText("Remove Trace");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/remove.gif");
				setImageDescriptor(id);
				setEnabled(true);
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				traceAddon = null;
				timelineRenderer.setTraceExtractor(null);
				timelineRenderer.setTraceExplorer(null);
			}
		});

		addActionToToolbar(new AbstractEngineAction(Action.AS_PUSH_BUTTON) {

			private FileDialog fileDialog;

			@Override
			protected void init() {
				super.init();
				setText("Open Trace");
				setToolTipText("Open Trace");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/jload_obj.gif");
				setImageDescriptor(id);
				setEnabled(true);

				fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.trace" });
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				fileDialog.setText("Open Trace");
				String filePath = fileDialog.open();

				if (filePath != null && !filePath.equals("")) {
					Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
					Map<String, Object> m = reg.getExtensionToFactoryMap();
					m.put("trace", new XMIResourceFactoryImpl());

					// Obtain a new resource set
					ResourceSet resSet = new ResourceSetImpl();

					// Get the resources
					URI filePath1URI = URI.createFileURI(filePath);
					Resource traceResource = resSet.getResource(filePath1URI, true);
					EcoreUtil.resolveAll(traceResource);
					AbstractTraceAddon newTraceAddon = null;
					try {
						IExtensionRegistry extReg = Platform.getExtensionRegistry();
						IExtensionPoint ep = extReg
								.getExtensionPoint("org.gemoc.gemoc_language_workbench.engine_addon");
						IExtension[] extensions = ep.getExtensions();
						for (int i = 0; i < extensions.length && newTraceAddon == null; i++) {
							IExtension ext = extensions[i];
							IConfigurationElement[] confElements = ext.getConfigurationElements();
							for (int j = 0; j < confElements.length; j++) {
								IConfigurationElement confElement = confElements[j];
								String attr = confElement.getAttribute("Class");
								if (attr != null) {
									Object obj = confElement.createExecutableExtension("Class");
									if (obj instanceof AbstractTraceAddon) {
										AbstractTraceAddon obj_cast = (AbstractTraceAddon) obj;
										if (obj_cast.isAddonForTrace(traceResource.getContents().get(0))) {
											newTraceAddon = obj_cast;
											break;
										}
									}
								}
							}
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}

					if (newTraceAddon != null) {
						traceAddon = newTraceAddon;
						newTraceAddon.load(traceResource);
						timelineRenderer.setTraceExtractor(traceAddon.getTraceExtractor());
						timelineRenderer.setTraceExplorer(traceAddon.getTraceExplorer());
					}
				}
			}
		});

		addActionToToolbar(new AbstractEngineAction(Action.AS_CHECK_BOX) {
			@Override
			protected void init() {
				super.init();
				setText("Scroll Lock");
				setToolTipText("Scroll Lock");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/lock_co.gif");
				setImageDescriptor(id);
				setEnabled(true);
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				timelineRenderer.setScrollLock(isChecked());
			}
		});
		
		addActionToToolbar(new AbstractEngineAction(Action.AS_CHECK_BOX) {
			@Override
			protected void init() {
				super.init();
				setText("State Coloration");
				setToolTipText("State Coloration");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/state_coloration.gif");
				setImageDescriptor(id);
				setEnabled(true);
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				timelineRenderer.setStateColoration(isChecked());
			}
		});

		addActionToToolbar(new AbstractEngineAction(Action.AS_PUSH_BUTTON) {

			private InputDialog dialog;

			@Override
			protected void init() {
				super.init();
				setText("Jump to state");
				setToolTipText("Jump To State");
				ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/runtoline_co.gif");
				setImageDescriptor(id);
				setEnabled(true);

				dialog = new InputDialog(shell, "Jump to state", "Enter the desired state", "0", s -> {
					ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> extractor = traceAddon.getTraceExtractor();
					if (extractor == null) {
						return "Not trace currently loaded";
					}
					try {
						int i = Integer.parseInt(s);
						if (i > -1 && i < extractor.getStatesTraceLength()) {
							return null;
						}
						return "Not a valid state";
					} catch (NumberFormatException e) {
						return "Not a valid state";
					}
				});
			}

			@Override
			public void engineSelectionChanged(IExecutionEngine engine) {
			}

			@Override
			public void run() {
				dialog.open();
				if (dialog.getReturnCode() == Window.OK) {
					int state = Integer.parseInt(dialog.getValue());
					timelineRenderer.getJumpConsumer().accept(state);
				}
			}
		});
	}

	private void addActionToToolbar(Action action) {
		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add(action);
	}

	@Override
	public void setFocus() {
		if (fxCanvas != null) {
			fxCanvas.setFocus();
		}
	}

	private boolean canDisplayTimeline(IExecutionEngine engine) {
		if (engine.getExecutionContext().getExecutionMode().equals(ExecutionMode.Run)
				&& engine.getRunningStatus().equals(RunStatus.Stopped)) {
			return true;
		}
		if (engine.getExecutionContext().getExecutionMode().equals(ExecutionMode.Animation)) {
			return true;
		}
		return false;
	}

	private State<?,?> breakAtVectorState = null;
	private int breakAtStateIndex = -1;
	
	@Override
	public void engineSelectionChanged(IExecutionEngine engine) {
		if (engine != null) {
			this.engine = engine;
			if (canDisplayTimeline(engine)) {
				@SuppressWarnings("rawtypes")
				Set<IMultiDimensionalTraceAddon> traceAddons = engine
						.getAddonsTypedBy(IMultiDimensionalTraceAddon.class);
				if (!traceAddons.isEmpty()) {
					@SuppressWarnings("unchecked")
					final IMultiDimensionalTraceAddon<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceAddon = traceAddons.iterator().next();
					final ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> extractor = traceAddon.getTraceExtractor();
					final Collection<AbstractGemocDebugger> debuggers = engine.getAddonsTypedBy(AbstractGemocDebugger.class);
					if (!debuggers.isEmpty()) {
						debugger = debuggers.stream().findFirst().get();
						if (breakAtVectorState != null) {
							BiPredicate<IExecutionEngine, MSEOccurrence> predicate = new BiPredicate<IExecutionEngine, MSEOccurrence>() {
								final State<?,?> baseState = breakAtVectorState;
								@Override
								public boolean test(IExecutionEngine executionEngine, MSEOccurrence mseOccurrence) {
									final ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor = traceAddon.getTraceExtractor();
									final int lastStateIndex = traceExtractor.getStatesTraceLength() - 1;
									final State<?,?> state = traceExtractor.getState(lastStateIndex);
									return traceExtractor.compareStates(baseState, state, true);
								}
							};
							debugger.addPredicateBreak(predicate);
							breakAtVectorState = null;
						}
						if (breakAtStateIndex != -1) {
							BiPredicate<IExecutionEngine, MSEOccurrence> predicate = new BiPredicate<IExecutionEngine, MSEOccurrence>() {
								final int stateToBreakTo = breakAtStateIndex;
								@Override
								public boolean test(IExecutionEngine executionEngine, MSEOccurrence mseOccurrence) {
									final int traceLength = extractor.getStatesTraceLength();
									final int stateToBreakTo = this.stateToBreakTo;
									final boolean result = traceLength == stateToBreakTo + 1;
									return result;
								}
							};
							debugger.addPredicateBreak(predicate);
							breakAtStateIndex = -1;
						}
					}
					this.traceAddon = traceAddon;
					timelineRenderer.setTraceExtractor(traceAddon.getTraceExtractor());
					timelineRenderer.setTraceExplorer(traceAddon.getTraceExplorer());
				}
			} else {
				// TODO
			}
		}
	}
	
	private void launchConfigFromTrace() {
		final LaunchConfiguration launchConfiguration = traceAddon.getTraceExtractor().getLaunchConfiguration();
		final String launchConfigurationType = launchConfiguration.getType();
		final IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.debug.core.launchConfigurationTypes");
		final Optional<IConfigurationElement> optElt = Arrays.asList(elements)
				.stream().filter(e->e.getAttribute("id").equals(launchConfigurationType)).findFirst();
		if (optElt.isPresent()) {
			final IConfigurationElement elt = optElt.get();
			try {
				final Object obj = elt.createExecutableExtension("delegate");
				final AbstractGemocLauncher launcher = (AbstractGemocLauncher) obj;
				final Map<String,Object> parameters = launcher.parseLaunchConfiguration(launchConfiguration);
				final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				final String launchName = manager.generateLaunchConfigurationName((String) parameters
						.get(IRunConfiguration.LAUNCH_SELECTED_LANGUAGE));
				final ILaunchConfigurationType type = manager
						.getLaunchConfigurationType(launchConfigurationType);
				try {
					final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, launchName);
					workingCopy.setAttributes(parameters);
					workingCopy.launch("debug", null, false, true);
				} catch (CoreException e) {
					Activator.error(e.getMessage(), e);
				}
			} catch (CoreException e1) {
				Activator.error(e1.getMessage(), e1);
			}
		}
	}
}
