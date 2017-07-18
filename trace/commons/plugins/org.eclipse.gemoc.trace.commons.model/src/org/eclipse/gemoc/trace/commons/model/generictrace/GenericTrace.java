/*******************************************************************************
 * Copyright (c) 2017 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.gemoc.trace.commons.model.generictrace;

import org.eclipse.gemoc.trace.commons.model.trace.Trace;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.gemoc.trace.commons.model.generictrace.GenerictracePackage#getGenericTrace()
 * @model
 * @generated
 */
public interface GenericTrace<StepSubType extends GenericStep> extends Trace<StepSubType, GenericTracedObject, GenericState> {
} // GenericTrace