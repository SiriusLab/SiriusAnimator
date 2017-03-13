/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Step</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StepImpl#getMseoccurrence <em>Mseoccurrence</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StepImpl#getStartingState <em>Starting State</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.StepImpl#getEndingState <em>Ending State</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class StepImpl<StateSubType extends State<?, ?>> extends MinimalEObjectImpl.Container implements Step<StateSubType> {
	/**
	 * The cached value of the '{@link #getMseoccurrence() <em>Mseoccurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMseoccurrence()
	 * @generated
	 * @ordered
	 */
	protected MSEOccurrence mseoccurrence;

	/**
	 * The cached value of the '{@link #getStartingState() <em>Starting State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartingState()
	 * @generated
	 * @ordered
	 */
	protected StateSubType startingState;

	/**
	 * The cached value of the '{@link #getEndingState() <em>Ending State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndingState()
	 * @generated
	 * @ordered
	 */
	protected StateSubType endingState;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.STEP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MSEOccurrence getMseoccurrence() {
		return mseoccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMseoccurrence(MSEOccurrence newMseoccurrence, NotificationChain msgs) {
		MSEOccurrence oldMseoccurrence = mseoccurrence;
		mseoccurrence = newMseoccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TracePackage.STEP__MSEOCCURRENCE, oldMseoccurrence, newMseoccurrence);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMseoccurrence(MSEOccurrence newMseoccurrence) {
		if (newMseoccurrence != mseoccurrence) {
			NotificationChain msgs = null;
			if (mseoccurrence != null)
				msgs = ((InternalEObject)mseoccurrence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TracePackage.STEP__MSEOCCURRENCE, null, msgs);
			if (newMseoccurrence != null)
				msgs = ((InternalEObject)newMseoccurrence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TracePackage.STEP__MSEOCCURRENCE, null, msgs);
			msgs = basicSetMseoccurrence(newMseoccurrence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.STEP__MSEOCCURRENCE, newMseoccurrence, newMseoccurrence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public StateSubType getStartingState() {
		if (startingState != null && startingState.eIsProxy()) {
			InternalEObject oldStartingState = (InternalEObject)startingState;
			startingState = (StateSubType)eResolveProxy(oldStartingState);
			if (startingState != oldStartingState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.STEP__STARTING_STATE, oldStartingState, startingState));
			}
		}
		return startingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSubType basicGetStartingState() {
		return startingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartingState(StateSubType newStartingState, NotificationChain msgs) {
		StateSubType oldStartingState = startingState;
		startingState = newStartingState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TracePackage.STEP__STARTING_STATE, oldStartingState, newStartingState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartingState(StateSubType newStartingState) {
		if (newStartingState != startingState) {
			NotificationChain msgs = null;
			if (startingState != null)
				msgs = ((InternalEObject)startingState).eInverseRemove(this, TracePackage.STATE__STARTED_STEPS, State.class, msgs);
			if (newStartingState != null)
				msgs = ((InternalEObject)newStartingState).eInverseAdd(this, TracePackage.STATE__STARTED_STEPS, State.class, msgs);
			msgs = basicSetStartingState(newStartingState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.STEP__STARTING_STATE, newStartingState, newStartingState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public StateSubType getEndingState() {
		if (endingState != null && endingState.eIsProxy()) {
			InternalEObject oldEndingState = (InternalEObject)endingState;
			endingState = (StateSubType)eResolveProxy(oldEndingState);
			if (endingState != oldEndingState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.STEP__ENDING_STATE, oldEndingState, endingState));
			}
		}
		return endingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSubType basicGetEndingState() {
		return endingState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEndingState(StateSubType newEndingState, NotificationChain msgs) {
		StateSubType oldEndingState = endingState;
		endingState = newEndingState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TracePackage.STEP__ENDING_STATE, oldEndingState, newEndingState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndingState(StateSubType newEndingState) {
		if (newEndingState != endingState) {
			NotificationChain msgs = null;
			if (endingState != null)
				msgs = ((InternalEObject)endingState).eInverseRemove(this, TracePackage.STATE__ENDED_STEPS, State.class, msgs);
			if (newEndingState != null)
				msgs = ((InternalEObject)newEndingState).eInverseAdd(this, TracePackage.STATE__ENDED_STEPS, State.class, msgs);
			msgs = basicSetEndingState(newEndingState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.STEP__ENDING_STATE, newEndingState, newEndingState));
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
			case TracePackage.STEP__STARTING_STATE:
				if (startingState != null)
					msgs = ((InternalEObject)startingState).eInverseRemove(this, TracePackage.STATE__STARTED_STEPS, State.class, msgs);
				return basicSetStartingState((StateSubType)otherEnd, msgs);
			case TracePackage.STEP__ENDING_STATE:
				if (endingState != null)
					msgs = ((InternalEObject)endingState).eInverseRemove(this, TracePackage.STATE__ENDED_STEPS, State.class, msgs);
				return basicSetEndingState((StateSubType)otherEnd, msgs);
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
			case TracePackage.STEP__MSEOCCURRENCE:
				return basicSetMseoccurrence(null, msgs);
			case TracePackage.STEP__STARTING_STATE:
				return basicSetStartingState(null, msgs);
			case TracePackage.STEP__ENDING_STATE:
				return basicSetEndingState(null, msgs);
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
			case TracePackage.STEP__MSEOCCURRENCE:
				return getMseoccurrence();
			case TracePackage.STEP__STARTING_STATE:
				if (resolve) return getStartingState();
				return basicGetStartingState();
			case TracePackage.STEP__ENDING_STATE:
				if (resolve) return getEndingState();
				return basicGetEndingState();
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
			case TracePackage.STEP__MSEOCCURRENCE:
				setMseoccurrence((MSEOccurrence)newValue);
				return;
			case TracePackage.STEP__STARTING_STATE:
				setStartingState((StateSubType)newValue);
				return;
			case TracePackage.STEP__ENDING_STATE:
				setEndingState((StateSubType)newValue);
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
			case TracePackage.STEP__MSEOCCURRENCE:
				setMseoccurrence((MSEOccurrence)null);
				return;
			case TracePackage.STEP__STARTING_STATE:
				setStartingState((StateSubType)null);
				return;
			case TracePackage.STEP__ENDING_STATE:
				setEndingState((StateSubType)null);
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
			case TracePackage.STEP__MSEOCCURRENCE:
				return mseoccurrence != null;
			case TracePackage.STEP__STARTING_STATE:
				return startingState != null;
			case TracePackage.STEP__ENDING_STATE:
				return endingState != null;
		}
		return super.eIsSet(featureID);
	}

} //StepImpl
