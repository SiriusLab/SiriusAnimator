/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage
 * @generated
 */
public interface LaunchconfigurationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LaunchconfigurationFactory eINSTANCE = fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Launch Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Launch Configuration</em>'.
	 * @generated
	 */
	LaunchConfiguration createLaunchConfiguration();

	/**
	 * Returns a new object of class '<em>Language Name Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Language Name Parameter</em>'.
	 * @generated
	 */
	LanguageNameParameter createLanguageNameParameter();

	/**
	 * Returns a new object of class '<em>Addon Extension Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Addon Extension Parameter</em>'.
	 * @generated
	 */
	AddonExtensionParameter createAddonExtensionParameter();

	/**
	 * Returns a new object of class '<em>Model URI Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model URI Parameter</em>'.
	 * @generated
	 */
	ModelURIParameter createModelURIParameter();

	/**
	 * Returns a new object of class '<em>Animator URI Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Animator URI Parameter</em>'.
	 * @generated
	 */
	AnimatorURIParameter createAnimatorURIParameter();

	/**
	 * Returns a new object of class '<em>Entry Point Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entry Point Parameter</em>'.
	 * @generated
	 */
	EntryPointParameter createEntryPointParameter();

	/**
	 * Returns a new object of class '<em>Initialization Arguments Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initialization Arguments Parameter</em>'.
	 * @generated
	 */
	InitializationArgumentsParameter createInitializationArgumentsParameter();

	/**
	 * Returns a new object of class '<em>Model Root Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Root Parameter</em>'.
	 * @generated
	 */
	ModelRootParameter createModelRootParameter();

	/**
	 * Returns a new object of class '<em>Initialization Method Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initialization Method Parameter</em>'.
	 * @generated
	 */
	InitializationMethodParameter createInitializationMethodParameter();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LaunchconfigurationPackage getLaunchconfigurationPackage();

} //LaunchconfigurationFactory
