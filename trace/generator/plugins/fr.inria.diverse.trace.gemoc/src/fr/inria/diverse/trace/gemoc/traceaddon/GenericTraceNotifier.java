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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.BatchModelChangeListener;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NewObjectModelChange;

import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;
import fr.inria.diverse.trace.gemoc.api.ITraceListener;
import fr.inria.diverse.trace.gemoc.api.ITraceNotifier;

public class GenericTraceNotifier implements ITraceNotifier {

	private BatchModelChangeListener traceListener;

	private final List<ITraceListener> listeners = new ArrayList<>();

	public GenericTraceNotifier(BatchModelChangeListener traceListener) {
		this.traceListener = traceListener;
	}

	@Override
	public void notifyListeners() {
		for (ITraceListener listener : listeners) {
			notifyListener(listener);
		}
	}

	@Override
	public void notifyListener(ITraceListener listener) {
		final List<ModelChange> changes = traceListener.getChanges(listener);
		if (!changes.isEmpty()) {
			final List<Step<?>> startedSteps = new ArrayList<>();
			final List<Step<?>> endedSteps = new ArrayList<>();
			final List<State<?,?>> newStates = new ArrayList<>();
			final List<Value<?>> newValues = new ArrayList<>();
			final List<Dimension<?>> newDimensions = new ArrayList<>();
			changes.forEach(c -> {
				if (c instanceof NewObjectModelChange) {
					final EObject o = c.getChangedObject();
					if (o instanceof Value<?>) {
						newValues.add((Value<?>) o);
					} else if (o instanceof Step<?>) {
						startedSteps.add((Step<?>) o);
					} else if (o instanceof State<?,?>) {
						final State<?,?> newState = (State<?,?>) o;
						newStates.add(newState);
						endedSteps.addAll(newState.getEndedSteps());
					} else if (o instanceof TracedObject<?>) {
						newDimensions.addAll(((TracedObject<?>) o).getDimensions());
					}
				}
			});
			if (!newValues.isEmpty()) {
				listener.valuesAdded(newValues);
			}
			if (!newDimensions.isEmpty()) {
				listener.dimensionsAdded(newDimensions);
			}
			if (!newStates.isEmpty()) {
				listener.statesAdded(newStates);
			}
			if (!startedSteps.isEmpty()) {
				listener.stepsStarted(startedSteps);
			}
			if (!endedSteps.isEmpty()) {
				listener.stepsEnded(endedSteps);
			}
		}
	}

	@Override
	public void addListener(ITraceListener listener) {
		listeners.add(listener);
		traceListener.registerObserver(listener);
	}

	@Override
	public void removeListener(ITraceListener listener) {
		listeners.remove(listener);
		traceListener.removeObserver(listener);
	}
}
