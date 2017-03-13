/**
 */
package fr.inria.diverse.trace.commons.model.generictrace;

import fr.inria.diverse.trace.commons.model.trace.Dimension;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.GenericDimension#getDynamicProperty <em>Dynamic Property</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getGenericDimension()
 * @model
 * @generated
 */
public interface GenericDimension extends Dimension<GenericValue> {
	/**
	 * Returns the value of the '<em><b>Dynamic Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dynamic Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dynamic Property</em>' reference.
	 * @see #setDynamicProperty(EStructuralFeature)
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getGenericDimension_DynamicProperty()
	 * @model
	 * @generated
	 */
	EStructuralFeature getDynamicProperty();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericDimension#getDynamicProperty <em>Dynamic Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dynamic Property</em>' reference.
	 * @see #getDynamicProperty()
	 * @generated
	 */
	void setDynamicProperty(EStructuralFeature value);

} // GenericDimension
