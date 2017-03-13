/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.impl.GenericDimensionImpl#getDynamicProperty <em>Dynamic Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericDimensionImpl extends DimensionImpl<GenericValue> implements GenericDimension {
	/**
	 * The cached value of the '{@link #getDynamicProperty() <em>Dynamic Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDynamicProperty()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature dynamicProperty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericDimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.GENERIC_DIMENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericValue> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<GenericValue>(GenericValue.class, this, GenerictracePackage.GENERIC_DIMENSION__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getDynamicProperty() {
		if (dynamicProperty != null && dynamicProperty.eIsProxy()) {
			InternalEObject oldDynamicProperty = (InternalEObject)dynamicProperty;
			dynamicProperty = (EStructuralFeature)eResolveProxy(oldDynamicProperty);
			if (dynamicProperty != oldDynamicProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY, oldDynamicProperty, dynamicProperty));
			}
		}
		return dynamicProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetDynamicProperty() {
		return dynamicProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDynamicProperty(EStructuralFeature newDynamicProperty) {
		EStructuralFeature oldDynamicProperty = dynamicProperty;
		dynamicProperty = newDynamicProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY, oldDynamicProperty, dynamicProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY:
				if (resolve) return getDynamicProperty();
				return basicGetDynamicProperty();
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
			case GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY:
				setDynamicProperty((EStructuralFeature)newValue);
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
			case GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY:
				setDynamicProperty((EStructuralFeature)null);
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
			case GenerictracePackage.GENERIC_DIMENSION__DYNAMIC_PROPERTY:
				return dynamicProperty != null;
		}
		return super.eIsSet(featureID);
	}

} //GenericDimensionImpl
