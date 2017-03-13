/**
 */
package fr.inria.diverse.trace.commons.model.generictrace;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue#isAttributeValue <em>Attribute Value</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getBooleanAttributeValue()
 * @model
 * @generated
 */
public interface BooleanAttributeValue extends GenericAttributeValue {
	/**
	 * Returns the value of the '<em><b>Attribute Value</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Value</em>' attribute.
	 * @see #setAttributeValue(boolean)
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getBooleanAttributeValue_AttributeValue()
	 * @model default="false"
	 * @generated
	 */
	boolean isAttributeValue();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue#isAttributeValue <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Value</em>' attribute.
	 * @see #isAttributeValue()
	 * @generated
	 */
	void setAttributeValue(boolean value);

} // BooleanAttributeValue
