/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic MSE</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCallerReference <em>Caller Reference</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getActionReference <em>Action Reference</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getGenericMSE()
 * @model
 * @generated
 */
public interface GenericMSE extends MSE {
	/**
	 * Returns the value of the '<em><b>Caller Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Caller Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Caller Reference</em>' reference.
	 * @see #setCallerReference(EObject)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getGenericMSE_CallerReference()
	 * @model
	 * @generated
	 */
	EObject getCallerReference();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCallerReference <em>Caller Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Caller Reference</em>' reference.
	 * @see #getCallerReference()
	 * @generated
	 */
	void setCallerReference(EObject value);

	/**
	 * Returns the value of the '<em><b>Action Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Reference</em>' reference.
	 * @see #setActionReference(EOperation)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getGenericMSE_ActionReference()
	 * @model
	 * @generated
	 */
	EOperation getActionReference();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getActionReference <em>Action Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Reference</em>' reference.
	 * @see #getActionReference()
	 * @generated
	 */
	void setActionReference(EOperation value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return callerReference;'"
	 * @generated
	 */
	EObject getCaller();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return actionReference;'"
	 * @generated
	 */
	EOperation getAction();

} // GenericMSE
