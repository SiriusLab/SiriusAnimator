/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration.impl;

import fr.inria.diverse.trace.commons.model.launchconfiguration.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LaunchconfigurationFactoryImpl extends EFactoryImpl implements LaunchconfigurationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LaunchconfigurationFactory init() {
		try {
			LaunchconfigurationFactory theLaunchconfigurationFactory = (LaunchconfigurationFactory)EPackage.Registry.INSTANCE.getEFactory(LaunchconfigurationPackage.eNS_URI);
			if (theLaunchconfigurationFactory != null) {
				return theLaunchconfigurationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LaunchconfigurationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchconfigurationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case LaunchconfigurationPackage.LAUNCH_CONFIGURATION: return createLaunchConfiguration();
			case LaunchconfigurationPackage.LANGUAGE_NAME_PARAMETER: return createLanguageNameParameter();
			case LaunchconfigurationPackage.ADDON_EXTENSION_PARAMETER: return createAddonExtensionParameter();
			case LaunchconfigurationPackage.MODEL_URI_PARAMETER: return createModelURIParameter();
			case LaunchconfigurationPackage.ANIMATOR_URI_PARAMETER: return createAnimatorURIParameter();
			case LaunchconfigurationPackage.ENTRY_POINT_PARAMETER: return createEntryPointParameter();
			case LaunchconfigurationPackage.INITIALIZATION_ARGUMENTS_PARAMETER: return createInitializationArgumentsParameter();
			case LaunchconfigurationPackage.MODEL_ROOT_PARAMETER: return createModelRootParameter();
			case LaunchconfigurationPackage.INITIALIZATION_METHOD_PARAMETER: return createInitializationMethodParameter();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case LaunchconfigurationPackage.ISERIALIZABLE:
				return createISerializableFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case LaunchconfigurationPackage.ISERIALIZABLE:
				return convertISerializableToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchConfiguration createLaunchConfiguration() {
		LaunchConfigurationImpl launchConfiguration = new LaunchConfigurationImpl();
		return launchConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageNameParameter createLanguageNameParameter() {
		LanguageNameParameterImpl languageNameParameter = new LanguageNameParameterImpl();
		return languageNameParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddonExtensionParameter createAddonExtensionParameter() {
		AddonExtensionParameterImpl addonExtensionParameter = new AddonExtensionParameterImpl();
		return addonExtensionParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelURIParameter createModelURIParameter() {
		ModelURIParameterImpl modelURIParameter = new ModelURIParameterImpl();
		return modelURIParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnimatorURIParameter createAnimatorURIParameter() {
		AnimatorURIParameterImpl animatorURIParameter = new AnimatorURIParameterImpl();
		return animatorURIParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryPointParameter createEntryPointParameter() {
		EntryPointParameterImpl entryPointParameter = new EntryPointParameterImpl();
		return entryPointParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitializationArgumentsParameter createInitializationArgumentsParameter() {
		InitializationArgumentsParameterImpl initializationArgumentsParameter = new InitializationArgumentsParameterImpl();
		return initializationArgumentsParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelRootParameter createModelRootParameter() {
		ModelRootParameterImpl modelRootParameter = new ModelRootParameterImpl();
		return modelRootParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitializationMethodParameter createInitializationMethodParameter() {
		InitializationMethodParameterImpl initializationMethodParameter = new InitializationMethodParameterImpl();
		return initializationMethodParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public byte[] createISerializableFromString(EDataType eDataType, String initialValue) {
		return (byte[])super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertISerializableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchconfigurationPackage getLaunchconfigurationPackage() {
		return (LaunchconfigurationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LaunchconfigurationPackage getPackage() {
		return LaunchconfigurationPackage.eINSTANCE;
	}

} //LaunchconfigurationFactoryImpl
