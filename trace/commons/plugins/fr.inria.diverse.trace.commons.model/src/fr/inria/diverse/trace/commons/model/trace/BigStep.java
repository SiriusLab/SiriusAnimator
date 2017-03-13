/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Big Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.BigStep#getSubSteps <em>Sub Steps</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getBigStep()
 * @model abstract="true"
 * @generated
 */
public interface BigStep<StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> extends Step<StateSubType> {
	/**
	 * Returns the value of the '<em><b>Sub Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Steps</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Steps</em>' containment reference list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getBigStep_SubSteps()
	 * @model containment="true"
	 * @generated
	 */
	EList<StepSubtype> getSubSteps();

} // BigStep
