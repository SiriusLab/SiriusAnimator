/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.MSE;
import fr.inria.diverse.trace.commons.model.trace.MSEOccurrence;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MSE Occurrence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl#getMse <em>Mse</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl#getResult <em>Result</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MSEOccurrenceImpl extends MinimalEObjectImpl.Container implements MSEOccurrence {
	/**
	 * The cached value of the '{@link #getMse() <em>Mse</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMse()
	 * @generated
	 * @ordered
	 */
	protected MSE mse;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> parameters;

	/**
	 * The cached value of the '{@link #getResult() <em>Result</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResult()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> result;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MSEOccurrenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.MSE_OCCURRENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MSE getMse() {
		if (mse != null && mse.eIsProxy()) {
			InternalEObject oldMse = (InternalEObject)mse;
			mse = (MSE)eResolveProxy(oldMse);
			if (mse != oldMse) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.MSE_OCCURRENCE__MSE, oldMse, mse));
			}
		}
		return mse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MSE basicGetMse() {
		return mse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMse(MSE newMse) {
		MSE oldMse = mse;
		mse = newMse;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.MSE_OCCURRENCE__MSE, oldMse, mse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Object> getParameters() {
		if (parameters == null) {
			parameters = new EDataTypeUniqueEList<Object>(Object.class, this, TracePackage.MSE_OCCURRENCE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Object> getResult() {
		if (result == null) {
			result = new EDataTypeUniqueEList<Object>(Object.class, this, TracePackage.MSE_OCCURRENCE__RESULT);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.MSE_OCCURRENCE__MSE:
				if (resolve) return getMse();
				return basicGetMse();
			case TracePackage.MSE_OCCURRENCE__PARAMETERS:
				return getParameters();
			case TracePackage.MSE_OCCURRENCE__RESULT:
				return getResult();
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
			case TracePackage.MSE_OCCURRENCE__MSE:
				setMse((MSE)newValue);
				return;
			case TracePackage.MSE_OCCURRENCE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Object>)newValue);
				return;
			case TracePackage.MSE_OCCURRENCE__RESULT:
				getResult().clear();
				getResult().addAll((Collection<? extends Object>)newValue);
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
			case TracePackage.MSE_OCCURRENCE__MSE:
				setMse((MSE)null);
				return;
			case TracePackage.MSE_OCCURRENCE__PARAMETERS:
				getParameters().clear();
				return;
			case TracePackage.MSE_OCCURRENCE__RESULT:
				getResult().clear();
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
			case TracePackage.MSE_OCCURRENCE__MSE:
				return mse != null;
			case TracePackage.MSE_OCCURRENCE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case TracePackage.MSE_OCCURRENCE__RESULT:
				return result != null && !result.isEmpty();
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
		result.append(" (parameters: ");
		result.append(parameters);
		result.append(", result: ");
		result.append(result);
		result.append(')');
		return result.toString();
	}

} //MSEOccurrenceImpl
