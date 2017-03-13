/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Step#getMseoccurrence <em>Mseoccurrence</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Step#getStartingState <em>Starting State</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Step#getEndingState <em>Ending State</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getStep()
 * @model abstract="true"
 * @generated
 */
public interface Step<StateSubType extends State<?, ?>> extends EObject {
	/**
	 * Returns the value of the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mseoccurrence</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mseoccurrence</em>' containment reference.
	 * @see #setMseoccurrence(MSEOccurrence)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getStep_Mseoccurrence()
	 * @model containment="true"
	 * @generated
	 */
	MSEOccurrence getMseoccurrence();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.Step#getMseoccurrence <em>Mseoccurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mseoccurrence</em>' containment reference.
	 * @see #getMseoccurrence()
	 * @generated
	 */
	void setMseoccurrence(MSEOccurrence value);

	/**
	 * Returns the value of the '<em><b>Starting State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link fr.inria.diverse.trace.commons.model.trace.State#getStartedSteps <em>Started Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starting State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Starting State</em>' reference.
	 * @see #setStartingState(State)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getStep_StartingState()
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getStartedSteps
	 * @model opposite="startedSteps" required="true"
	 * @generated
	 */
	StateSubType getStartingState();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.Step#getStartingState <em>Starting State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Starting State</em>' reference.
	 * @see #getStartingState()
	 * @generated
	 */
	void setStartingState(StateSubType value);

	/**
	 * Returns the value of the '<em><b>Ending State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link fr.inria.diverse.trace.commons.model.trace.State#getEndedSteps <em>Ended Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ending State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ending State</em>' reference.
	 * @see #setEndingState(State)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getStep_EndingState()
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getEndedSteps
	 * @model opposite="endedSteps"
	 * @generated
	 */
	StateSubType getEndingState();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.Step#getEndingState <em>Ending State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ending State</em>' reference.
	 * @see #getEndingState()
	 * @generated
	 */
	void setEndingState(StateSubType value);

} // Step
