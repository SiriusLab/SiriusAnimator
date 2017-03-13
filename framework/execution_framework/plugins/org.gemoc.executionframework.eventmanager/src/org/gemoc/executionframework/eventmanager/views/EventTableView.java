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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IEventManager;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class EventTableView extends TableView<EObject> {

	private final EClass eventClass;

	private final EFactory factory;

	private final Resource executedModel;

	private final List<EClass> eventParameterClasses = new ArrayList<>();

	private final Map<EReference, List<EObject>> referenceToMatchingModelElements = new HashMap<>();

	private final ObservableList<EObject> events = FXCollections.observableArrayList();

	private final Function<EObject, Boolean> canDisplayEventFunction;

	public EventTableView(final EClass eventClass, final Resource executedModel, final IEventManager eventManager) {
		this.eventClass = eventClass;
		this.factory = eventClass.getEPackage().getEFactoryInstance();
		this.executedModel = executedModel;
		eventParameterClasses
				.addAll(eventClass.getEReferences().stream().map(r -> r.eClass()).collect(Collectors.toList()));
		setItems(events);

		canDisplayEventFunction = (event) -> {
			return eventManager.canSendEvent(event);
		};

		final List<TableColumn<EObject, String>> columns = new ArrayList<>();
		eventClass.getEReferences().stream().forEach(r -> {
			final TableColumn<EObject, String> col = new TableColumn<EObject, String>(r.getName());
			col.setCellValueFactory(new EObjectPropertyValueFactory(r));
			columns.add(col);
		});

		setRowFactory(tv -> {
			TableRow<EObject> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					EObject rowData = row.getItem();
					eventManager.sendEvent(rowData);
				}
			});
			return row;
		});

		getColumns().setAll(columns);

	}

	public void refreshEvents() {
		Platform.runLater(() -> {
			referenceToMatchingModelElements.clear();
			gatherPotentialParameters();
			
//			final List<EObject> newEvents = computeAllPossibleEvents().stream().filter(m -> {
//				return !events.stream().anyMatch(event -> {
//					return m.entrySet().stream().allMatch(entry -> {
//						final EReference ref = entry.getKey();
//						final Object val1 = event.eGet(ref);
//						final EObject val2 = entry.getValue();
//						return val1 == val2;
//					});
//				});
//			}).map(m -> {
//				final EObject event = factory.create(eventClass);
//				m.entrySet().forEach(entry -> {
//					event.eSet(entry.getKey(), entry.getValue());
//				});
//				return event;
//			}).filter(event -> canDisplayEventFunction.apply(event)).collect(Collectors.toList());
//			
//			final List<EObject> toRemove = events.stream().filter(event -> canDisplayEventFunction.apply(event)).collect(Collectors.toList());
//			
//			events.removeAll(toRemove);
//			events.addAll(newEvents);
		
			events.clear();
			events.addAll(computeAllPossibleEvents().stream().map(m -> {
				final EObject event = factory.create(eventClass);
				m.entrySet().forEach(entry -> {
					event.eSet(entry.getKey(), entry.getValue());
				});
				return event;
			}).filter(event -> canDisplayEventFunction.apply(event)).collect(Collectors.toList()));
		});
	}

	private void gatherPotentialParameters() {
		final List<EReference> eventParameters = eventClass.getEReferences();
		executedModel.getAllContents().forEachRemaining(modelElement -> {
			final EClass elementClass = modelElement.eClass();
			final List<EReference> matchingParameters = eventParameters.stream().filter(r -> {
				return elementClass.getClassifierID() == r.getEType().getClassifierID()
						|| elementClass.getEAllSuperTypes().contains(r.getEType());
			}).collect(Collectors.toList());
			matchingParameters.forEach(r -> {
				List<EObject> elements = referenceToMatchingModelElements.get(r);
				if (elements == null) {
					elements = new ArrayList<>();
					referenceToMatchingModelElements.put(r, elements);
				}
				elements.add(modelElement);
			});
		});
	}

	private List<Map<EReference, EObject>> computeAllPossibleEvents() {
		final List<Map<EReference, EObject>> result = new ArrayList<>();
		final int nbEvents = referenceToMatchingModelElements.values().stream().map(l -> l.size())
				.reduce((i1, i2) -> i1 * i2).orElse(0);
		final List<Map.Entry<EReference, List<EObject>>> entries = new ArrayList<>(
				referenceToMatchingModelElements.entrySet());
		for (int i = 0; i < nbEvents; i++) {
			int j = 1;
			final Map<EReference, EObject> parametersAssociation = new HashMap<>();
			for (Map.Entry<EReference, List<EObject>> entry : entries) {
				final List<EObject> modelElements = entry.getValue();
				parametersAssociation.put(entry.getKey(), modelElements.get((i / j) % modelElements.size()));
				j *= modelElements.size();
			}
			result.add(parametersAssociation);
		}
		return result;
	}

	static class EObjectPropertyValueFactory
			implements Callback<CellDataFeatures<EObject, String>, ObservableValue<String>> {

		private final EReference reference;
		private final Function<Object, String> stringGetter;
		private final DefaultDeclarativeQualifiedNameProvider nameprovider = new DefaultDeclarativeQualifiedNameProvider();

		public EObjectPropertyValueFactory(EReference reference) {
			this.reference = reference;
			stringGetter = (o) -> {
				if (o instanceof EObject) {
					QualifiedName qname = nameprovider.getFullyQualifiedName((EObject) o);
					if (qname != null) {
						return qname.toString();
					}
				}
				final String string = o.toString();
				return string.substring(string.lastIndexOf(".") + 1);
			};
		}

		@Override
		public ObservableValue<String> call(CellDataFeatures<EObject, String> p) {
			EObject object = p.getValue();
			Object refValue = object.eGet(reference);
			if (refValue != null) {
				String string = stringGetter.apply(refValue);
				ObservableValue<String> result = new ReadOnlyObjectWrapper<String>(string);
				return result;
			}
			return null;
		}
	}
}
