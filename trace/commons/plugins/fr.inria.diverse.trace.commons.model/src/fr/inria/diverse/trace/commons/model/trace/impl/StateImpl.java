/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;
import fr.inria.diverse.trace.commons.model.trace.Value;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StateImpl#getStartedSteps <em>Started Steps</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StateImpl#getEndedSteps <em>Ended Steps</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StateImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class StateImpl<StepSubType extends Step<?>, ValueSubType extends Value<?>> extends MinimalEObjectImpl.Container implements State<StepSubType, ValueSubType> {
	/**
	 * The cached value of the '{@link #getStartedSteps() <em>Started Steps</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartedSteps()
	 * @generated
	 * @ordered
	 */
	protected EList<StepSubType> startedSteps;

	/**
	 * The cached value of the '{@link #getEndedSteps() <em>Ended Steps</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndedSteps()
	 * @generated
	 * @ordered
	 */
	protected EList<StepSubType> endedSteps;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<ValueSubType> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StepSubType> getStartedSteps() {
		if (startedSteps == null) {
			startedSteps = new EObjectWithInverseResolvingEList<StepSubType>(Step.class, this, TracePackage.STATE__STARTED_STEPS, TracePackage.STEP__STARTING_STATE);
		}
		return startedSteps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StepSubType> getEndedSteps() {
		if (endedSteps == null) {
			endedSteps = new EObjectWithInverseResolvingEList<StepSubType>(Step.class, this, TracePackage.STATE__ENDED_STEPS, TracePackage.STEP__ENDING_STATE);
		}
		return endedSteps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ValueSubType> getValues() {
		if (values == null) {
			values = new EObjectWithInverseResolvingEList.ManyInverse<ValueSubType>(Value.class, this, TracePackage.STATE__VALUES, TracePackage.VALUE__STATES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStartedSteps()).basicAdd(otherEnd, msgs);
			case TracePackage.STATE__ENDED_STEPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEndedSteps()).basicAdd(otherEnd, msgs);
			case TracePackage.STATE__VALUES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getValues()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				return ((InternalEList<?>)getStartedSteps()).basicRemove(otherEnd, msgs);
			case TracePackage.STATE__ENDED_STEPS:
				return ((InternalEList<?>)getEndedSteps()).basicRemove(otherEnd, msgs);
			case TracePackage.STATE__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				return getStartedSteps();
			case TracePackage.STATE__ENDED_STEPS:
				return getEndedSteps();
			case TracePackage.STATE__VALUES:
				return getValues();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				getStartedSteps().clear();
				getStartedSteps().addAll((Collection<? extends StepSubType>)newValue);
				return;
			case TracePackage.STATE__ENDED_STEPS:
				getEndedSteps().clear();
				getEndedSteps().addAll((Collection<? extends StepSubType>)newValue);
				return;
			case TracePackage.STATE__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends ValueSubType>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				getStartedSteps().clear();
				return;
			case TracePackage.STATE__ENDED_STEPS:
				getEndedSteps().clear();
				return;
			case TracePackage.STATE__VALUES:
				getValues().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TracePackage.STATE__STARTED_STEPS:
				return startedSteps != null && !startedSteps.isEmpty();
			case TracePackage.STATE__ENDED_STEPS:
				return endedSteps != null && !endedSteps.isEmpty();
			case TracePackage.STATE__VALUES:
				return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
