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
package org.gemoc.executionframework.eventmanager.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.EventManagerRegistry;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IEventManager;
import fr.inria.diverse.trace.commons.model.trace.Step;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class EventManagerRenderer extends Pane implements IEngineAddon {

	private IEventManager eventManager;

	private Resource executedModel;

	private final ObservableList<EClass> eventList = FXCollections.observableArrayList();

	private final ObservableList<EObject> pushedEvents = FXCollections.observableArrayList();

	private final Map<EClass, EventTableView> eventTypeToEventTableView = new HashMap<>();

	private final Map<EClass, List<EObject>> eventTypeToSelectedEvents = new HashMap<>();

	private final ScrollPane scrollPane = new ScrollPane();

	private final ListView<EClass> eventListView = new ListView<>(eventList);

	private final ListView<EObject> pushedEventListView = new ListView<>(pushedEvents);

	private final BorderPane borderPane = new BorderPane();

	private final Button pushButton = new Button("Push");

	private final Button sendButton = new Button("Send");

	private final HBox header = new HBox();

	public EventManagerRenderer() {
		getChildren().add(borderPane);

		borderPane.minWidthProperty().bind(widthProperty());
		borderPane.maxWidthProperty().bind(widthProperty());
		borderPane.minHeightProperty().bind(heightProperty());
		borderPane.maxHeightProperty().bind(heightProperty());

		pushButton.setOnAction(e -> {
			eventTypeToSelectedEvents.get(eventListView.getSelectionModel().getSelectedItem()).forEach(event -> {
				pushedEvents.add(event);
			});
		});

		sendButton.setOnAction(e -> {
			pushedEvents.forEach(eventManager::sendEvent);
			pushedEvents.clear();
		});

		header.getChildren().addAll(pushButton, sendButton);

		eventListView.setCellFactory((l) -> new ComboBoxListCell<EClass>(new StringConverter<EClass>() {
			@Override
			public String toString(EClass object) {
				return object.getName();
			}

			@Override
			public EClass fromString(String string) {
				return null;
			}
		}));

		eventListView.getSelectionModel().selectedItemProperty().addListener((c, o, n) -> {
			scrollPane.setContent(eventTypeToEventTableView.get(n));
		});

		final VBox leftPanel = new VBox();
		leftPanel.getChildren().addAll(eventListView, pushedEventListView);
		
		borderPane.setTop(header);
		borderPane.setLeft(leftPanel);
		borderPane.setCenter(scrollPane);

		scrollPane.setFitToWidth(true);
		scrollPane.setBorder(Border.EMPTY);
		scrollPane.minHeightProperty().bind(leftPanel.heightProperty());
		scrollPane.maxHeightProperty().bind(leftPanel.heightProperty());
		final ListChangeListener<EClass> eventTypesChangeListener = c -> {
			while (c.next()) {
				c.getRemoved().stream().forEach(e -> {
					eventTypeToEventTableView.remove(e);
					eventTypeToSelectedEvents.remove(e);
				});
				c.getAddedSubList().stream().forEach(e -> {
					final EventTableView tableView = new EventTableView(e, executedModel, eventManager);
					eventTypeToEventTableView.put(e, tableView);
					final List<EObject> selectedEvents = new ArrayList<>();
					eventTypeToSelectedEvents.put(e, selectedEvents);

					final ListChangeListener<EObject> selectedEventsChangeListener = c1 -> {
						while (c1.next()) {
							selectedEvents.removeAll(c1.getRemoved());
							selectedEvents.addAll(c1.getAddedSubList());
						}
					};

					tableView.getSelectionModel().getSelectedItems().addListener(selectedEventsChangeListener);
					tableView.refreshEvents();
					tableView.minHeightProperty().bind(scrollPane.heightProperty().subtract(2));
				});
			}
		};
		eventList.addListener(eventTypesChangeListener);
	}

	public void setEventManager(IEventManager eventManager) {
		Runnable runnable = () -> {
			this.eventManager = eventManager;
			eventList.clear();
			if (eventManager != null) {
				eventList.addAll(this.eventManager.getEventClasses());
			}
		};
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(runnable);
		} else {
			runnable.run();
		}
	}

	public void setExecutedModel(Resource executedModel) {
		this.executedModel = executedModel;
	}

	private void refreshEvents() {
		eventTypeToEventTableView.entrySet().forEach(e -> {
			final EventTableView tableView = e.getValue();
			tableView.refreshEvents();
		});
		final EClass selectedItem = eventListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			final EventTableView selectedTableView = eventTypeToEventTableView.get(selectedItem);
			if (selectedTableView != null) {
				scrollPane.setContent(selectedTableView);
			} else {
				scrollPane.setContent(null);
			}
		}
	}

	@Override
	public void engineAboutToStart(IExecutionEngine engine) {
	}

	@Override
	public void engineStarted(IExecutionEngine executionEngine) {
		executedModel = executionEngine.getExecutionContext().getResourceModel();
	}

	@Override
	public void engineInitialized(IExecutionEngine executionEngine) {
		setEventManager(EventManagerRegistry.getInstance().findEventManager());
	}

	@Override
	public void engineAboutToStop(IExecutionEngine engine) {
	}

	@Override
	public void engineStopped(IExecutionEngine engine) {
		executedModel = null;
		eventTypeToEventTableView.clear();
		Platform.runLater(() -> {
			eventList.clear();
			scrollPane.setContent(null);
		});
	}

	@Override
	public void engineAboutToDispose(IExecutionEngine engine) {
	}

	@Override
	public void stepSelected(IExecutionEngine engine, Step selectedStep) {
	}

	@Override
	public void aboutToExecuteStep(IExecutionEngine engine, Step stepToExecute) {
		refreshEvents();
	}

	@Override
	public void stepExecuted(IExecutionEngine engine, Step stepExecuted) {
		refreshEvents();
	}

	@Override
	public void engineStatusChanged(IExecutionEngine engine, RunStatus newStatus) {
	}

	@Override
	public List<String> validate(List<IEngineAddon> otherAddons) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aboutToSelectStep(IExecutionEngine engine, Collection<Step> steps) {
	}

	@Override
	public void proposedStepsChanged(IExecutionEngine engine, Collection<Step> steps) {
	}
}
