/**
 */
package fr.inria.diverse.trace.commons.model.generictrace;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many String Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.ManyStringAttributeValue#getAttributeValue <em>Attribute Value</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getManyStringAttributeValue()
 * @model
 * @generated
 */
public interface ManyStringAttributeValue extends GenericAttributeValue {
	/**
	 * Returns the value of the '<em><b>Attribute Value</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Value</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Value</em>' attribute list.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getManyStringAttributeValue_AttributeValue()
	 * @model
	 * @generated
	 */
	EList<String> getAttributeValue();

} // ManyStringAttributeValue
