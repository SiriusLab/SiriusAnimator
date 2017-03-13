/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Launch Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getParameters <em>Parameters</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#getLaunchConfiguration()
 * @model
 * @generated
 */
public interface LaunchConfiguration extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#getLaunchConfiguration_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<LaunchConfigurationParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#getLaunchConfiguration_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // LaunchConfiguration
