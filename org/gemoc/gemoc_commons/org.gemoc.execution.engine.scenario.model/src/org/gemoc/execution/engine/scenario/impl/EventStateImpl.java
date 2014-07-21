/**
 */
package org.gemoc.execution.engine.scenario.impl;

import fr.inria.aoste.timesquare.ccslkernel.model.TimeModel.Clock;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.gemoc.execution.engine.scenario.EventState;
import org.gemoc.execution.engine.scenario.ScenarioPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.gemoc.execution.engine.scenario.impl.EventStateImpl#isIsForced <em>Is Forced</em>}</li>
 *   <li>{@link org.gemoc.execution.engine.scenario.impl.EventStateImpl#getClock <em>Clock</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventStateImpl extends MinimalEObjectImpl.Container implements EventState {
	/**
	 * The default value of the '{@link #isIsForced() <em>Is Forced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsForced()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_FORCED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsForced() <em>Is Forced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsForced()
	 * @generated
	 * @ordered
	 */
	protected boolean isForced = IS_FORCED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClock() <em>Clock</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClock()
	 * @generated
	 * @ordered
	 */
	protected Clock clock;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScenarioPackage.Literals.EVENT_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsForced() {
		return isForced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsForced(boolean newIsForced) {
		boolean oldIsForced = isForced;
		isForced = newIsForced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScenarioPackage.EVENT_STATE__IS_FORCED, oldIsForced, isForced));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clock getClock() {
		if (clock != null && clock.eIsProxy()) {
			InternalEObject oldClock = (InternalEObject)clock;
			clock = (Clock)eResolveProxy(oldClock);
			if (clock != oldClock) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ScenarioPackage.EVENT_STATE__CLOCK, oldClock, clock));
			}
		}
		return clock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clock basicGetClock() {
		return clock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClock(Clock newClock) {
		Clock oldClock = clock;
		clock = newClock;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScenarioPackage.EVENT_STATE__CLOCK, oldClock, clock));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ScenarioPackage.EVENT_STATE__IS_FORCED:
				return isIsForced();
			case ScenarioPackage.EVENT_STATE__CLOCK:
				if (resolve) return getClock();
				return basicGetClock();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ScenarioPackage.EVENT_STATE__IS_FORCED:
				setIsForced((Boolean)newValue);
				return;
			case ScenarioPackage.EVENT_STATE__CLOCK:
				setClock((Clock)newValue);
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
			case ScenarioPackage.EVENT_STATE__IS_FORCED:
				setIsForced(IS_FORCED_EDEFAULT);
				return;
			case ScenarioPackage.EVENT_STATE__CLOCK:
				setClock((Clock)null);
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
			case ScenarioPackage.EVENT_STATE__IS_FORCED:
				return isForced != IS_FORCED_EDEFAULT;
			case ScenarioPackage.EVENT_STATE__CLOCK:
				return clock != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isForced: ");
		result.append(isForced);
		result.append(')');
		return result.toString();
	}

} //EventStateImpl