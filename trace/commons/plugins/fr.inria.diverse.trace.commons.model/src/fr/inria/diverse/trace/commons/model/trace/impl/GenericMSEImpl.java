/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.GenericMSE;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic MSE</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl#getCallerReference <em>Caller Reference</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl#getActionReference <em>Action Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericMSEImpl extends MSEImpl implements GenericMSE {
	/**
	 * The cached value of the '{@link #getCallerReference() <em>Caller Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallerReference()
	 * @generated
	 * @ordered
	 */
	protected EObject callerReference;

	/**
	 * The cached value of the '{@link #getActionReference() <em>Action Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionReference()
	 * @generated
	 * @ordered
	 */
	protected EOperation actionReference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericMSEImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.GENERIC_MSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getCallerReference() {
		if (callerReference != null && callerReference.eIsProxy()) {
			InternalEObject oldCallerReference = (InternalEObject)callerReference;
			callerReference = eResolveProxy(oldCallerReference);
			if (callerReference != oldCallerReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.GENERIC_MSE__CALLER_REFERENCE, oldCallerReference, callerReference));
			}
		}
		return callerReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetCallerReference() {
		return callerReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallerReference(EObject newCallerReference) {
		EObject oldCallerReference = callerReference;
		callerReference = newCallerReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.GENERIC_MSE__CALLER_REFERENCE, oldCallerReference, callerReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getActionReference() {
		if (actionReference != null && actionReference.eIsProxy()) {
			InternalEObject oldActionReference = (InternalEObject)actionReference;
			actionReference = (EOperation)eResolveProxy(oldActionReference);
			if (actionReference != oldActionReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.GENERIC_MSE__ACTION_REFERENCE, oldActionReference, actionReference));
			}
		}
		return actionReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation basicGetActionReference() {
		return actionReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionReference(EOperation newActionReference) {
		EOperation oldActionReference = actionReference;
		actionReference = newActionReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.GENERIC_MSE__ACTION_REFERENCE, oldActionReference, actionReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getCaller() {
		return callerReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAction() {
		return actionReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.GENERIC_MSE__CALLER_REFERENCE:
				if (resolve) return getCallerReference();
				return basicGetCallerReference();
			case TracePackage.GENERIC_MSE__ACTION_REFERENCE:
				if (resolve) return getActionReference();
				return basicGetActionReference();
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
			case TracePackage.GENERIC_MSE__CALLER_REFERENCE:
				setCallerReference((EObject)newValue);
				return;
			case TracePackage.GENERIC_MSE__ACTION_REFERENCE:
				setActionReference((EOperation)newValue);
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
			case TracePackage.GENERIC_MSE__CALLER_REFERENCE:
				setCallerReference((EObject)null);
				return;
			case TracePackage.GENERIC_MSE__ACTION_REFERENCE:
				setActionReference((EOperation)null);
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
			case TracePackage.GENERIC_MSE__CALLER_REFERENCE:
				return callerReference != null;
			case TracePackage.GENERIC_MSE__ACTION_REFERENCE:
				return actionReference != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TracePackage.GENERIC_MSE___GET_CALLER:
				return getCaller();
			case TracePackage.GENERIC_MSE___GET_ACTION:
				return getAction();
		}
		return super.eInvoke(operationID, arguments);
	}

} //GenericMSEImpl
