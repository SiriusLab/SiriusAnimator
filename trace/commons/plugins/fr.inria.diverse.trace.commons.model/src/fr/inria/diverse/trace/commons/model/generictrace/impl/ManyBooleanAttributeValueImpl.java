/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;
import fr.inria.diverse.trace.commons.model.generictrace.ManyBooleanAttributeValue;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Many Boolean Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.impl.ManyBooleanAttributeValueImpl#getAttributeValue <em>Attribute Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManyBooleanAttributeValueImpl extends GenericAttributeValueImpl implements ManyBooleanAttributeValue {
	/**
	 * The cached value of the '{@link #getAttributeValue() <em>Attribute Value</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected EList<Boolean> attributeValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManyBooleanAttributeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.MANY_BOOLEAN_ATTRIBUTE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Boolean> getAttributeValue() {
		if (attributeValue == null) {
			attributeValue = new EDataTypeUniqueEList<Boolean>(Boolean.class, this, GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);
		}
		return attributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				return getAttributeValue();
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
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				getAttributeValue().clear();
				getAttributeValue().addAll((Collection<? extends Boolean>)newValue);
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
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				getAttributeValue().clear();
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
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE:
				return attributeValue != null && !attributeValue.isEmpty();
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

} //ManyBooleanAttributeValueImpl
