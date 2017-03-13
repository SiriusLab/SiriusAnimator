/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MSE Occurrence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getMse <em>Mse</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getParameters <em>Parameters</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getResult <em>Result</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getMSEOccurrence()
 * @model
 * @generated
 */
public interface MSEOccurrence extends EObject {
	/**
	 * Returns the value of the '<em><b>Mse</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mse</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mse</em>' reference.
	 * @see #setMse(MSE)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getMSEOccurrence_Mse()
	 * @model required="true"
	 * @generated
	 */
	MSE getMse();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getMse <em>Mse</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mse</em>' reference.
	 * @see #getMse()
	 * @generated
	 */
	void setMse(MSE value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getMSEOccurrence_Parameters()
	 * @model
	 * @generated
	 */
	EList<Object> getParameters();

	/**
	 * Returns the value of the '<em><b>Result</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' attribute list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getMSEOccurrence_Result()
	 * @model
	 * @generated
	 */
	EList<Object> getResult();

} // MSEOccurrence
