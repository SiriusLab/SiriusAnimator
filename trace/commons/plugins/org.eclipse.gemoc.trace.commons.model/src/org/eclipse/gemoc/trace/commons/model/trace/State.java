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
package org.eclipse.gemoc.trace.commons.model.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gemoc.trace.commons.model.trace.State#getStartedSteps <em>Started Steps</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.commons.model.trace.State#getEndedSteps <em>Ended Steps</em>}</li>
 *   <li>{@link org.eclipse.gemoc.trace.commons.model.trace.State#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gemoc.trace.commons.model.trace.TracePackage#getState()
 * @model abstract="true"
 * @generated
 */
public interface State<StepSubType extends Step<?>, ValueSubType extends Value<?>> extends EObject {
	/**
	 * Returns the value of the '<em><b>Started Steps</b></em>' reference list.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.trace.commons.model.trace.Step#getStartingState <em>Starting State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Started Steps</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Started Steps</em>' reference list.
	 * @see org.eclipse.gemoc.trace.commons.model.trace.TracePackage#getState_StartedSteps()
	 * @see org.eclipse.gemoc.trace.commons.model.trace.Step#getStartingState
	 * @model opposite="startingState"
	 * @generated
	 */
	EList<StepSubType> getStartedSteps();

	/**
	 * Returns the value of the '<em><b>Ended Steps</b></em>' reference list.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.trace.commons.model.trace.Step#getEndingState <em>Ending State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ended Steps</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ended Steps</em>' reference list.
	 * @see org.eclipse.gemoc.trace.commons.model.trace.TracePackage#getState_EndedSteps()
	 * @see org.eclipse.gemoc.trace.commons.model.trace.Step#getEndingState
	 * @model opposite="endingState"
	 * @generated
	 */
	EList<StepSubType> getEndedSteps();

	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gemoc.trace.commons.model.trace.Value#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see org.eclipse.gemoc.trace.commons.model.trace.TracePackage#getState_Values()
	 * @see org.eclipse.gemoc.trace.commons.model.trace.Value#getStates
	 * @model opposite="states" ordered="false"
	 * @generated
	 */
	EList<ValueSubType> getValues();

} // State
