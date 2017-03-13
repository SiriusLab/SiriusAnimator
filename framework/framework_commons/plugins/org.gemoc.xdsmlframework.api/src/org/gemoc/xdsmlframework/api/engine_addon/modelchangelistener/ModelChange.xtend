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
package org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.List
import org.eclipse.emf.common.notify.Notification

abstract class ModelChange {

	@Accessors(PROTECTED_SETTER,PUBLIC_GETTER)
	EObject changedObject

	new(EObject changedObject) {
		this.changedObject = changedObject
	}

}

class NewObjectModelChange extends ModelChange {

	new(EObject changedObject) {
		super(changedObject)
	}

}

class RemovedObjectModelChange extends ModelChange {

	new(EObject changedObject) {
		super(changedObject)
	}

}

abstract class FieldModelChange extends ModelChange {

	@Accessors(PROTECTED_SETTER,PUBLIC_GETTER)
	EStructuralFeature changedField

	new(EObject changedObject, EStructuralFeature changedField) {
		super(changedObject)
		this.changedField = changedField

	}

}

class PotentialCollectionFieldModelChange extends FieldModelChange {

	@Accessors(PROTECTED_SETTER,PUBLIC_GETTER)
	Object emfNotifications

	new(EObject changedObject, EStructuralFeature changedField, List<Notification> notifications) {
		super(changedObject, changedField)
		this.emfNotifications = notifications
	}

}

class NonCollectionFieldModelChange extends FieldModelChange {


	new(EObject changedObject, EStructuralFeature changedField) {
		super(changedObject, changedField)
	}

}
 