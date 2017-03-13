/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration.util;

import fr.inria.diverse.trace.commons.model.launchconfiguration.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage
 * @generated
 */
public class LaunchconfigurationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LaunchconfigurationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchconfigurationSwitch() {
		if (modelPackage == null) {
			modelPackage = LaunchconfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case LaunchconfigurationPackage.LAUNCH_CONFIGURATION: {
				LaunchConfiguration launchConfiguration = (LaunchConfiguration)theEObject;
				T result = caseLaunchConfiguration(launchConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.LAUNCH_CONFIGURATION_PARAMETER: {
				LaunchConfigurationParameter launchConfigurationParameter = (LaunchConfigurationParameter)theEObject;
				T result = caseLaunchConfigurationParameter(launchConfigurationParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.LANGUAGE_NAME_PARAMETER: {
				LanguageNameParameter languageNameParameter = (LanguageNameParameter)theEObject;
				T result = caseLanguageNameParameter(languageNameParameter);
				if (result == null) result = caseLaunchConfigurationParameter(languageNameParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.ADDON_EXTENSION_PARAMETER: {
				AddonExtensionParameter addonExtensionParameter = (AddonExtensionParameter)theEObject;
				T result = caseAddonExtensionParameter(addonExtensionParameter);
				if (result == null) result = caseLaunchConfigurationParameter(addonExtensionParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.MODEL_URI_PARAMETER: {
				ModelURIParameter modelURIParameter = (ModelURIParameter)theEObject;
				T result = caseModelURIParameter(modelURIParameter);
				if (result == null) result = caseLaunchConfigurationParameter(modelURIParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.ANIMATOR_URI_PARAMETER: {
				AnimatorURIParameter animatorURIParameter = (AnimatorURIParameter)theEObject;
				T result = caseAnimatorURIParameter(animatorURIParameter);
				if (result == null) result = caseLaunchConfigurationParameter(animatorURIParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.ENTRY_POINT_PARAMETER: {
				EntryPointParameter entryPointParameter = (EntryPointParameter)theEObject;
				T result = caseEntryPointParameter(entryPointParameter);
				if (result == null) result = caseLaunchConfigurationParameter(entryPointParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.INITIALIZATION_ARGUMENTS_PARAMETER: {
				InitializationArgumentsParameter initializationArgumentsParameter = (InitializationArgumentsParameter)theEObject;
				T result = caseInitializationArgumentsParameter(initializationArgumentsParameter);
				if (result == null) result = caseLaunchConfigurationParameter(initializationArgumentsParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.MODEL_ROOT_PARAMETER: {
				ModelRootParameter modelRootParameter = (ModelRootParameter)theEObject;
				T result = caseModelRootParameter(modelRootParameter);
				if (result == null) result = caseLaunchConfigurationParameter(modelRootParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case LaunchconfigurationPackage.INITIALIZATION_METHOD_PARAMETER: {
				InitializationMethodParameter initializationMethodParameter = (InitializationMethodParameter)theEObject;
				T result = caseInitializationMethodParameter(initializationMethodParameter);
				if (result == null) result = caseLaunchConfigurationParameter(initializationMethodParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Launch Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Launch Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLaunchConfiguration(LaunchConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Launch Configuration Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Launch Configuration Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLaunchConfigurationParameter(LaunchConfigurationParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Language Name Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Language Name Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLanguageNameParameter(LanguageNameParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Addon Extension Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Addon Extension Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddonExtensionParameter(AddonExtensionParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model URI Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model URI Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelURIParameter(ModelURIParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Animator URI Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Animator URI Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnimatorURIParameter(AnimatorURIParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry Point Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry Point Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntryPointParameter(EntryPointParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initialization Arguments Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initialization Arguments Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitializationArgumentsParameter(InitializationArgumentsParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Root Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Root Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelRootParameter(ModelRootParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Initialization Method Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Initialization Method Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitializationMethodParameter(InitializationMethodParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //LaunchconfigurationSwitch
