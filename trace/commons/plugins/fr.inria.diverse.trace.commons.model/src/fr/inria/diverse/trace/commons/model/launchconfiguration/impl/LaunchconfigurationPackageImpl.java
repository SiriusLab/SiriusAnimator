/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration.impl;

import fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationFactory;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage;
import fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LaunchconfigurationPackageImpl extends EPackageImpl implements LaunchconfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass launchConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass launchConfigurationParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageNameParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addonExtensionParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelURIParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass animatorURIParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryPointParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initializationArgumentsParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelRootParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initializationMethodParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iSerializableEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LaunchconfigurationPackageImpl() {
		super(eNS_URI, LaunchconfigurationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link LaunchconfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LaunchconfigurationPackage init() {
		if (isInited) return (LaunchconfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(LaunchconfigurationPackage.eNS_URI);

		// Obtain or create and register package
		LaunchconfigurationPackageImpl theLaunchconfigurationPackage = (LaunchconfigurationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof LaunchconfigurationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new LaunchconfigurationPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theLaunchconfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theLaunchconfigurationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLaunchconfigurationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LaunchconfigurationPackage.eNS_URI, theLaunchconfigurationPackage);
		return theLaunchconfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLaunchConfiguration() {
		return launchConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLaunchConfiguration_Parameters() {
		return (EReference)launchConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLaunchConfiguration_Type() {
		return (EAttribute)launchConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLaunchConfigurationParameter() {
		return launchConfigurationParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLaunchConfigurationParameter_Value() {
		return (EAttribute)launchConfigurationParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLanguageNameParameter() {
		return languageNameParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddonExtensionParameter() {
		return addonExtensionParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelURIParameter() {
		return modelURIParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnimatorURIParameter() {
		return animatorURIParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntryPointParameter() {
		return entryPointParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInitializationArgumentsParameter() {
		return initializationArgumentsParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelRootParameter() {
		return modelRootParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInitializationMethodParameter() {
		return initializationMethodParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getISerializable() {
		return iSerializableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchconfigurationFactory getLaunchconfigurationFactory() {
		return (LaunchconfigurationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		launchConfigurationEClass = createEClass(LAUNCH_CONFIGURATION);
		createEReference(launchConfigurationEClass, LAUNCH_CONFIGURATION__PARAMETERS);
		createEAttribute(launchConfigurationEClass, LAUNCH_CONFIGURATION__TYPE);

		launchConfigurationParameterEClass = createEClass(LAUNCH_CONFIGURATION_PARAMETER);
		createEAttribute(launchConfigurationParameterEClass, LAUNCH_CONFIGURATION_PARAMETER__VALUE);

		languageNameParameterEClass = createEClass(LANGUAGE_NAME_PARAMETER);

		addonExtensionParameterEClass = createEClass(ADDON_EXTENSION_PARAMETER);

		modelURIParameterEClass = createEClass(MODEL_URI_PARAMETER);

		animatorURIParameterEClass = createEClass(ANIMATOR_URI_PARAMETER);

		entryPointParameterEClass = createEClass(ENTRY_POINT_PARAMETER);

		initializationArgumentsParameterEClass = createEClass(INITIALIZATION_ARGUMENTS_PARAMETER);

		modelRootParameterEClass = createEClass(MODEL_ROOT_PARAMETER);

		initializationMethodParameterEClass = createEClass(INITIALIZATION_METHOD_PARAMETER);

		// Create data types
		iSerializableEDataType = createEDataType(ISERIALIZABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		languageNameParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		addonExtensionParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		modelURIParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		animatorURIParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		entryPointParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		initializationArgumentsParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		modelRootParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());
		initializationMethodParameterEClass.getESuperTypes().add(this.getLaunchConfigurationParameter());

		// Initialize classes, features, and operations; add parameters
		initEClass(launchConfigurationEClass, LaunchConfiguration.class, "LaunchConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLaunchConfiguration_Parameters(), this.getLaunchConfigurationParameter(), null, "parameters", null, 0, -1, LaunchConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLaunchConfiguration_Type(), ecorePackage.getEString(), "type", null, 1, 1, LaunchConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(launchConfigurationParameterEClass, LaunchConfigurationParameter.class, "LaunchConfigurationParameter", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLaunchConfigurationParameter_Value(), ecorePackage.getEString(), "value", "", 0, 1, LaunchConfigurationParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(languageNameParameterEClass, LanguageNameParameter.class, "LanguageNameParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(addonExtensionParameterEClass, AddonExtensionParameter.class, "AddonExtensionParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(modelURIParameterEClass, ModelURIParameter.class, "ModelURIParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(animatorURIParameterEClass, AnimatorURIParameter.class, "AnimatorURIParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(entryPointParameterEClass, EntryPointParameter.class, "EntryPointParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(initializationArgumentsParameterEClass, InitializationArgumentsParameter.class, "InitializationArgumentsParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(modelRootParameterEClass, ModelRootParameter.class, "ModelRootParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(initializationMethodParameterEClass, InitializationMethodParameter.class, "InitializationMethodParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize data types
		initEDataType(iSerializableEDataType, byte[].class, "ISerializable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //LaunchconfigurationPackageImpl
