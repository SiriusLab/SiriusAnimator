/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Value#getStates <em>States</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getValue()
 * @model abstract="true"
 * @generated
 */
public interface Value<StateSubType extends State<?, ?>> extends EObject {
	/**
	 * Returns the value of the '<em><b>States</b></em>' reference list.
	 * It is bidirectional and its opposite is '{@link fr.inria.diverse.trace.commons.model.trace.State#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' reference list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getValue_States()
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getValues
	 * @model opposite="values"
	 * @generated
	 */
	EList<StateSubType> getStates();

} // Value
