/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Launch Configuration Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#getLaunchConfigurationParameter()
 * @model abstract="true"
 * @generated
 */
public interface LaunchConfigurationParameter extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#getLaunchConfigurationParameter_Value()
	 * @model default=""
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // LaunchConfigurationParameter
