/**
 */
package fr.inria.diverse.trace.commons.model.generictrace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many Reference Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue#getReferenceValues <em>Reference Values</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getManyReferenceValue()
 * @model
 * @generated
 */
public interface ManyReferenceValue extends GenericReferenceValue {
	/**
	 * Returns the value of the '<em><b>Reference Values</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Values</em>' reference list.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#getManyReferenceValue_ReferenceValues()
	 * @model
	 * @generated
	 */
	EList<EObject> getReferenceValues();

} // ManyReferenceValue
