/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Trace#getRootStep <em>Root Step</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Trace#getTracedObjects <em>Traced Objects</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Trace#getStates <em>States</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.Trace#getLaunchconfiguration <em>Launchconfiguration</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getTrace()
 * @model abstract="true"
 * @generated
 */
public interface Trace<StepSubType extends Step<?>, TracedObjectSubtype extends TracedObject<?>, StateSubType extends State<?, ?>> extends EObject {
	/**
	 * Returns the value of the '<em><b>Root Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Step</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Step</em>' containment reference.
	 * @see #setRootStep(Step)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getTrace_RootStep()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StepSubType getRootStep();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getRootStep <em>Root Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Step</em>' containment reference.
	 * @see #getRootStep()
	 * @generated
	 */
	void setRootStep(StepSubType value);

	/**
	 * Returns the value of the '<em><b>Traced Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traced Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traced Objects</em>' containment reference list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getTrace_TracedObjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<TracedObjectSubtype> getTracedObjects();

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getTrace_States()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateSubType> getStates();

	/**
	 * Returns the value of the '<em><b>Launchconfiguration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Launchconfiguration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Launchconfiguration</em>' containment reference.
	 * @see #setLaunchconfiguration(LaunchConfiguration)
	 * @see fr.inria.diverse.trace.commons.model.trace.TracePackage#getTrace_Launchconfiguration()
	 * @model containment="true" required="true"
	 * @generated
	 */
	LaunchConfiguration getLaunchconfiguration();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getLaunchconfiguration <em>Launchconfiguration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Launchconfiguration</em>' containment reference.
	 * @see #getLaunchconfiguration()
	 * @generated
	 */
	void setLaunchconfiguration(LaunchConfiguration value);

} // Trace
