/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Dimension#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getDimension()
 * @model abstract="true"
 * @generated
 */
public interface Dimension<ValueSubType extends Value<?>> extends EObject {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getDimension_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<ValueSubType> getValues();

} // Dimension
