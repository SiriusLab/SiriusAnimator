/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Boolean Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.impl.BooleanAttributeValueImpl#isAttributeValue <em>Attribute Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BooleanAttributeValueImpl extends GenericAttributeValueImpl implements BooleanAttributeValue {
	/**
	 * The default value of the '{@link #isAttributeValue() <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ATTRIBUTE_VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAttributeValue() <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected boolean attributeValue = ATTRIBUTE_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanAttributeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.BOOLEAN_ATTRIBUTE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAttributeValue() {
		return attributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeValue(boolean newAttributeValue) {
		boolean oldAttributeValue = attributeValue;
		attributeValue = newAttributeValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE, oldAttributeValue, attributeValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				return isAttributeValue();
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
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				setAttributeValue((Boolean)newValue);
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
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				setAttributeValue(ATTRIBUTE_VALUE_EDEFAULT);
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
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				return attributeValue != ATTRIBUTE_VALUE_EDEFAULT;
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
		result.append(" (attributeValue: ");
		result.append(attributeValue);
		result.append(')');
		return result.toString();
	}

} //BooleanAttributeValueImpl
