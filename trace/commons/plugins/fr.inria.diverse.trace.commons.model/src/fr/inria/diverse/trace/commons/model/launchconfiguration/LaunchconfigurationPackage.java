/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface LaunchconfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "launchconfiguration";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.gemoc.org/launch_configuration";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "launchconfiguration";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LaunchconfigurationPackage eINSTANCE = fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationImpl <em>Launch Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLaunchConfiguration()
	 * @generated
	 */
	int LAUNCH_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Launch Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Launch Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationParameterImpl <em>Launch Configuration Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLaunchConfigurationParameter()
	 * @generated
	 */
	int LAUNCH_CONFIGURATION_PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION_PARAMETER__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Launch Configuration Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Launch Configuration Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LanguageNameParameterImpl <em>Language Name Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LanguageNameParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLanguageNameParameter()
	 * @generated
	 */
	int LANGUAGE_NAME_PARAMETER = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_NAME_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Language Name Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_NAME_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Language Name Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LANGUAGE_NAME_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AddonExtensionParameterImpl <em>Addon Extension Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AddonExtensionParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getAddonExtensionParameter()
	 * @generated
	 */
	int ADDON_EXTENSION_PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDON_EXTENSION_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Addon Extension Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDON_EXTENSION_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Addon Extension Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDON_EXTENSION_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelURIParameterImpl <em>Model URI Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelURIParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getModelURIParameter()
	 * @generated
	 */
	int MODEL_URI_PARAMETER = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_URI_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Model URI Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_URI_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Model URI Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_URI_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AnimatorURIParameterImpl <em>Animator URI Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AnimatorURIParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getAnimatorURIParameter()
	 * @generated
	 */
	int ANIMATOR_URI_PARAMETER = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANIMATOR_URI_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Animator URI Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANIMATOR_URI_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Animator URI Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANIMATOR_URI_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.EntryPointParameterImpl <em>Entry Point Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.EntryPointParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getEntryPointParameter()
	 * @generated
	 */
	int ENTRY_POINT_PARAMETER = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Entry Point Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Entry Point Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationArgumentsParameterImpl <em>Initialization Arguments Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationArgumentsParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getInitializationArgumentsParameter()
	 * @generated
	 */
	int INITIALIZATION_ARGUMENTS_PARAMETER = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_ARGUMENTS_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Initialization Arguments Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_ARGUMENTS_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Initialization Arguments Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_ARGUMENTS_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelRootParameterImpl <em>Model Root Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelRootParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getModelRootParameter()
	 * @generated
	 */
	int MODEL_ROOT_PARAMETER = 8;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ROOT_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Model Root Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ROOT_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Model Root Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ROOT_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationMethodParameterImpl <em>Initialization Method Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationMethodParameterImpl
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getInitializationMethodParameter()
	 * @generated
	 */
	int INITIALIZATION_METHOD_PARAMETER = 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_METHOD_PARAMETER__VALUE = LAUNCH_CONFIGURATION_PARAMETER__VALUE;

	/**
	 * The number of structural features of the '<em>Initialization Method Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_METHOD_PARAMETER_FEATURE_COUNT = LAUNCH_CONFIGURATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Initialization Method Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_METHOD_PARAMETER_OPERATION_COUNT = LAUNCH_CONFIGURATION_PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>ISerializable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getISerializable()
	 * @generated
	 */
	int ISERIALIZABLE = 10;


	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration <em>Launch Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Launch Configuration</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration
	 * @generated
	 */
	EClass getLaunchConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getParameters()
	 * @see #getLaunchConfiguration()
	 * @generated
	 */
	EReference getLaunchConfiguration_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration#getType()
	 * @see #getLaunchConfiguration()
	 * @generated
	 */
	EAttribute getLaunchConfiguration_Type();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter <em>Launch Configuration Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Launch Configuration Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter
	 * @generated
	 */
	EClass getLaunchConfigurationParameter();

	/**
	 * Returns the meta object for the attribute '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter#getValue()
	 * @see #getLaunchConfigurationParameter()
	 * @generated
	 */
	EAttribute getLaunchConfigurationParameter_Value();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter <em>Language Name Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Language Name Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter
	 * @generated
	 */
	EClass getLanguageNameParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter <em>Addon Extension Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Addon Extension Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter
	 * @generated
	 */
	EClass getAddonExtensionParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter <em>Model URI Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model URI Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter
	 * @generated
	 */
	EClass getModelURIParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter <em>Animator URI Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Animator URI Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter
	 * @generated
	 */
	EClass getAnimatorURIParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter <em>Entry Point Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Point Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter
	 * @generated
	 */
	EClass getEntryPointParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter <em>Initialization Arguments Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initialization Arguments Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter
	 * @generated
	 */
	EClass getInitializationArgumentsParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter <em>Model Root Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Root Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter
	 * @generated
	 */
	EClass getModelRootParameter();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter <em>Initialization Method Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initialization Method Parameter</em>'.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter
	 * @generated
	 */
	EClass getInitializationMethodParameter();

	/**
	 * Returns the meta object for data type '<em>ISerializable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISerializable</em>'.
	 * @model instanceClass="byte[]"
	 * @generated
	 */
	EDataType getISerializable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LaunchconfigurationFactory getLaunchconfigurationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationImpl <em>Launch Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLaunchConfiguration()
		 * @generated
		 */
		EClass LAUNCH_CONFIGURATION = eINSTANCE.getLaunchConfiguration();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LAUNCH_CONFIGURATION__PARAMETERS = eINSTANCE.getLaunchConfiguration_Parameters();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LAUNCH_CONFIGURATION__TYPE = eINSTANCE.getLaunchConfiguration_Type();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationParameterImpl <em>Launch Configuration Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchConfigurationParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLaunchConfigurationParameter()
		 * @generated
		 */
		EClass LAUNCH_CONFIGURATION_PARAMETER = eINSTANCE.getLaunchConfigurationParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LAUNCH_CONFIGURATION_PARAMETER__VALUE = eINSTANCE.getLaunchConfigurationParameter_Value();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LanguageNameParameterImpl <em>Language Name Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LanguageNameParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getLanguageNameParameter()
		 * @generated
		 */
		EClass LANGUAGE_NAME_PARAMETER = eINSTANCE.getLanguageNameParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AddonExtensionParameterImpl <em>Addon Extension Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AddonExtensionParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getAddonExtensionParameter()
		 * @generated
		 */
		EClass ADDON_EXTENSION_PARAMETER = eINSTANCE.getAddonExtensionParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelURIParameterImpl <em>Model URI Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelURIParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getModelURIParameter()
		 * @generated
		 */
		EClass MODEL_URI_PARAMETER = eINSTANCE.getModelURIParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AnimatorURIParameterImpl <em>Animator URI Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.AnimatorURIParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getAnimatorURIParameter()
		 * @generated
		 */
		EClass ANIMATOR_URI_PARAMETER = eINSTANCE.getAnimatorURIParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.EntryPointParameterImpl <em>Entry Point Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.EntryPointParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getEntryPointParameter()
		 * @generated
		 */
		EClass ENTRY_POINT_PARAMETER = eINSTANCE.getEntryPointParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationArgumentsParameterImpl <em>Initialization Arguments Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationArgumentsParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getInitializationArgumentsParameter()
		 * @generated
		 */
		EClass INITIALIZATION_ARGUMENTS_PARAMETER = eINSTANCE.getInitializationArgumentsParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelRootParameterImpl <em>Model Root Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.ModelRootParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getModelRootParameter()
		 * @generated
		 */
		EClass MODEL_ROOT_PARAMETER = eINSTANCE.getModelRootParameter();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationMethodParameterImpl <em>Initialization Method Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.InitializationMethodParameterImpl
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getInitializationMethodParameter()
		 * @generated
		 */
		EClass INITIALIZATION_METHOD_PARAMETER = eINSTANCE.getInitializationMethodParameter();

		/**
		 * The meta object literal for the '<em>ISerializable</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.impl.LaunchconfigurationPackageImpl#getISerializable()
		 * @generated
		 */
		EDataType ISERIALIZABLE = eINSTANCE.getISerializable();

	}

} //LaunchconfigurationPackage
