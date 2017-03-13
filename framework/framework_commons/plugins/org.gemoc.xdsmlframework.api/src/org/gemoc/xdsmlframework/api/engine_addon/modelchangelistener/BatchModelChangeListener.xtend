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
package org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener;

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EContentAdapter

/**
 * This model listener gathers EMF notifications, and computes when asked a
 * set of ModelChange objects to reflect what happened in a more abstract
 * and concise way.
 * 
 * For instance, if a field changed multiple times in between two queries to
 * the model listener, a single ModelChange object will be computed to reflect that change,
 * instead of a list of many EMF Notifications.
 * 
 * A ModelChange can be a new/removed object in the model, or a change in a field.
 * See associated class.
 * 
 */
public class BatchModelChangeListener {

	private EContentAdapter adapter;
	private Map<Object, List<Notification>> changes = new HashMap
	private Set<Object> registeredObservers = new HashSet
	private Set<Resource> observedResources

	public new(Set<Resource> resources) {
		/*
		 * We create an adapter that stores and sort all the notifications for each object and field.
		 * This avoids us to sort everything afterwards.
		 */
		this.adapter = new EContentAdapter() {
			override void notifyChanged(Notification notification) {
				super.notifyChanged(notification);
				for (obs : registeredObservers) {
					changes.get(obs).add(notification)
				}
			}
		};
		
		observedResources = resources
		
		observedResources.forEach [ r |
			if (r != null) {
				r.eAdapters().add(adapter);
			}
		]
	}

	/**
	 * When an observer asks for the changes, we process all the notifications gathered for it since the last time. 
	 */
	def List<ModelChange> getChanges(Object addon) {
		val List<ModelChange> result = new ArrayList()
		val List<Notification> allNotifs = changes.get(addon);
		if (registeredObservers.contains(addon)) {
			changes.put(
				addon,
				new ArrayList<Notification>()
			);
		}

		// First we sort everything per object and field
		val Map<EObject, Map<EStructuralFeature, List<Notification>>> sortedNotifications = new HashMap
		val Map<Resource, List<Notification>> resourcesNotifications = new HashMap
		for (Notification notification : allNotifs) {
			val int eventType = notification.getEventType();
			if (eventType < Notification.EVENT_TYPE_COUNT && !notification.isTouch()) {

				if (notification.getNotifier() instanceof EObject &&
					notification.getFeature() instanceof EStructuralFeature) {
					val EStructuralFeature feature = notification.getFeature() as EStructuralFeature;
					val EObject changedObject = notification.getNotifier() as EObject;
					if (!sortedNotifications.containsKey(changedObject)) {
						sortedNotifications.put(changedObject, new HashMap)
					}
					val Map<EStructuralFeature, List<Notification>> objectsNotifications = sortedNotifications.get(
						changedObject);
					if (!objectsNotifications.containsKey(feature)) {
						objectsNotifications.put(feature, new ArrayList)
					}
					val List<Notification> fieldNotifications = objectsNotifications.get(feature);
					fieldNotifications.add(notification);
				} else if (notification.getNotifier() instanceof Resource) {
					val Resource resource = notification.notifier as Resource
					if (!resourcesNotifications.containsKey(resource))
						resourcesNotifications.put(resource, new ArrayList)
					val resourceNotifications = resourcesNotifications.get(resource)
					resourceNotifications.add(notification)
				}
			}
		}

		val newObjects = new HashSet<EObject>
		val removedObjects = new HashSet<EObject>

		// First we find new objects added or removed at the root of the resource
		for (resource : resourcesNotifications.keySet) {
			val resourceNotifications = resourcesNotifications.get(resource)
			for (Notification notif : resourceNotifications) {
				BatchModelChangeListener.
					manageCollectionContainmentNotification(removedObjects, newObjects, notif)
			}
		}

		// Next we read all that and try to interpret everything as coarse grained model changes
		for (object : sortedNotifications.keySet) {
			val featureMap = sortedNotifications.get(object)
			for (feature : featureMap.keySet) {
				val notifs = featureMap.get(feature)

				// Case multiplicity 0..1: we compare the original value and the new one at the end of the step
				if (!feature.isMany) {

					val previousValue = notifs.head.oldValue
					val newValue = notifs.last.newValue

					// Case objects: we compare references
					if (feature instanceof EReference) {
						if (previousValue != newValue) {

							// Register model change
							result.add(new NonCollectionFieldModelChange(object, feature))

							// Register potentially new or removed object
							if ((feature as EReference).containment) {
								if (previousValue != null && previousValue instanceof EObject)
									addToRemovedObjects(removedObjects, newObjects, previousValue as EObject)
								if (newValue != null && newValue instanceof EObject)
									addToNewObjects(removedObjects, newObjects, newValue as EObject)
							}
						}
					} // Case data types: we compare values
					else if (if (previousValue == null) {
						newValue != null
					} else {
						!previousValue.equals(newValue)
					}) {

						// Register model change
						result.add(new NonCollectionFieldModelChange(object, feature))
					}

				} // Case multiplicity 0..*: we consider that there was a potential change, but maybe following
				// all the adds ands remove, the collection went back to its state before the step
				else {

					// Very hard to decide if a collection has changed or not based on the notifications,
					// (e.g. if we remove and add the same object, the collection in fact doesn't change)
					// and we don't have a direct access to the previous content of the collection to compare 
					// similarly to what we do in a trace manager. 
					// So for now we simply state a "potential change", and the trace manager will have to compute
					// itself if there was a real change.
					result.add(new PotentialCollectionFieldModelChange(object, feature, notifs))

					// Yet we must still find new/removed objects
					for (notif : notifs) {

						if (feature instanceof EReference && (feature as EReference).containment) {
							BatchModelChangeListener.
								manageCollectionContainmentNotification(removedObjects, newObjects, notif)
						}
					}
				}
			}
		}

		// Finally we register the new and removed objects from the model
		for (newObject : newObjects) {
			result.add(0, new NewObjectModelChange(newObject))
		}
		for (removedObject : removedObjects) {
			result.add(0, new RemovedObjectModelChange(removedObject))
		}

		return result;
	}

	def boolean registerObserver(Object observer) {
		val boolean res = registeredObservers.add(observer);
		if (res) {
			changes.put(observer, new ArrayList<Notification>());
		}
		return res;
	}
	
	def void removeObserver(Object observer) {
		changes.remove(observer)
		registeredObservers.remove(observer)
	}

	private static def void addToNewObjects(Collection<EObject> removedObjects, Collection<EObject> newObjects,
		EObject object) {
		if (object != null) {
			val hasMoved = removedObjects.remove(object)
			if (!hasMoved)
				newObjects.add(object)
		}
	}

	private static def void addToRemovedObjects(Collection<EObject> removedObjects, Collection<EObject> newObjects,
		EObject object) {
		if (object != null) {
			val hasMoved = newObjects.remove(object)
			if (!hasMoved)
				removedObjects.add(object)

		}
	}

	// TODO manage objects already contained in new objects ... ?
	private static def void manageCollectionContainmentNotification(Collection<EObject> removedObjects,
		Collection<EObject> newObjects, Notification notif) {
		switch (notif.eventType) {
			case Notification.ADD:
				addToNewObjects(removedObjects, newObjects, notif.newValue as EObject)
			case Notification.ADD_MANY:
				for (add : notif.newValue as List<EObject>)
					addToNewObjects(removedObjects, newObjects, add)
			case Notification.REMOVE:
				addToRemovedObjects(removedObjects, newObjects, notif.oldValue as EObject)
			case Notification.REMOVE_MANY:
				for (remove : notif.oldValue as List<EObject>)
					addToNewObjects(removedObjects, newObjects, remove)
		}
	}
	
	public def void cleanUp() {
		for (r : observedResources.filter[r|r != null]) {
			r.eAdapters().remove(adapter);
		}
	}
}
