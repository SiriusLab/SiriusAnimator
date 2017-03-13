/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericParallelStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSmallStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTrace;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictraceFactory;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;
import fr.inria.diverse.trace.commons.model.generictrace.IntegerAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyBooleanAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyIntegerAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyStringAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.StringAttributeValue;

import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenerictracePackageImpl extends EPackageImpl implements GenerictracePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericSequentialStepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericParallelStepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericSmallStepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericReferenceValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericDimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericTracedObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericTraceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyBooleanAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyIntegerAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyStringAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericStepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleReferenceValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manyReferenceValueEClass = null;

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
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GenerictracePackageImpl() {
		super(eNS_URI, GenerictraceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GenerictracePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GenerictracePackage init() {
		if (isInited) return (GenerictracePackage)EPackage.Registry.INSTANCE.getEPackage(GenerictracePackage.eNS_URI);

		// Obtain or create and register package
		GenerictracePackageImpl theGenerictracePackage = (GenerictracePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GenerictracePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GenerictracePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TracePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGenerictracePackage.createPackageContents();

		// Initialize created meta-data
		theGenerictracePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGenerictracePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GenerictracePackage.eNS_URI, theGenerictracePackage);
		return theGenerictracePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericSequentialStep() {
		return genericSequentialStepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericParallelStep() {
		return genericParallelStepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericSmallStep() {
		return genericSmallStepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericReferenceValue() {
		return genericReferenceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericDimension() {
		return genericDimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericDimension_DynamicProperty() {
		return (EReference)genericDimensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericTracedObject() {
		return genericTracedObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericTracedObject_OriginalObject() {
		return (EReference)genericTracedObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericTracedObject_AllDimensions() {
		return (EReference)genericTracedObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getGenericTracedObject__GetDimensionsInternal() {
		return genericTracedObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericState() {
		return genericStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericTrace() {
		return genericTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericAttributeValue() {
		return genericAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanAttributeValue() {
		return booleanAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanAttributeValue_AttributeValue() {
		return (EAttribute)booleanAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerAttributeValue() {
		return integerAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerAttributeValue_AttributeValue() {
		return (EAttribute)integerAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringAttributeValue() {
		return stringAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringAttributeValue_AttributeValue() {
		return (EAttribute)stringAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManyBooleanAttributeValue() {
		return manyBooleanAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManyBooleanAttributeValue_AttributeValue() {
		return (EAttribute)manyBooleanAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManyIntegerAttributeValue() {
		return manyIntegerAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManyIntegerAttributeValue_AttributeValue() {
		return (EAttribute)manyIntegerAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManyStringAttributeValue() {
		return manyStringAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManyStringAttributeValue_AttributeValue() {
		return (EAttribute)manyStringAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericStep() {
		return genericStepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericValue() {
		return genericValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSingleReferenceValue() {
		return singleReferenceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSingleReferenceValue_ReferenceValue() {
		return (EReference)singleReferenceValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManyReferenceValue() {
		return manyReferenceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManyReferenceValue_ReferenceValues() {
		return (EReference)manyReferenceValueEClass.getEStructuralFeatures().get(0);
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
	public GenerictraceFactory getGenerictraceFactory() {
		return (GenerictraceFactory)getEFactoryInstance();
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
		genericSequentialStepEClass = createEClass(GENERIC_SEQUENTIAL_STEP);

		genericParallelStepEClass = createEClass(GENERIC_PARALLEL_STEP);

		genericSmallStepEClass = createEClass(GENERIC_SMALL_STEP);

		genericReferenceValueEClass = createEClass(GENERIC_REFERENCE_VALUE);

		genericDimensionEClass = createEClass(GENERIC_DIMENSION);
		createEReference(genericDimensionEClass, GENERIC_DIMENSION__DYNAMIC_PROPERTY);

		genericTracedObjectEClass = createEClass(GENERIC_TRACED_OBJECT);
		createEReference(genericTracedObjectEClass, GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT);
		createEReference(genericTracedObjectEClass, GENERIC_TRACED_OBJECT__ALL_DIMENSIONS);
		createEOperation(genericTracedObjectEClass, GENERIC_TRACED_OBJECT___GET_DIMENSIONS_INTERNAL);

		genericStateEClass = createEClass(GENERIC_STATE);

		genericTraceEClass = createEClass(GENERIC_TRACE);

		genericAttributeValueEClass = createEClass(GENERIC_ATTRIBUTE_VALUE);

		booleanAttributeValueEClass = createEClass(BOOLEAN_ATTRIBUTE_VALUE);
		createEAttribute(booleanAttributeValueEClass, BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		integerAttributeValueEClass = createEClass(INTEGER_ATTRIBUTE_VALUE);
		createEAttribute(integerAttributeValueEClass, INTEGER_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		stringAttributeValueEClass = createEClass(STRING_ATTRIBUTE_VALUE);
		createEAttribute(stringAttributeValueEClass, STRING_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		manyBooleanAttributeValueEClass = createEClass(MANY_BOOLEAN_ATTRIBUTE_VALUE);
		createEAttribute(manyBooleanAttributeValueEClass, MANY_BOOLEAN_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		manyIntegerAttributeValueEClass = createEClass(MANY_INTEGER_ATTRIBUTE_VALUE);
		createEAttribute(manyIntegerAttributeValueEClass, MANY_INTEGER_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		manyStringAttributeValueEClass = createEClass(MANY_STRING_ATTRIBUTE_VALUE);
		createEAttribute(manyStringAttributeValueEClass, MANY_STRING_ATTRIBUTE_VALUE__ATTRIBUTE_VALUE);

		genericStepEClass = createEClass(GENERIC_STEP);

		genericValueEClass = createEClass(GENERIC_VALUE);

		singleReferenceValueEClass = createEClass(SINGLE_REFERENCE_VALUE);
		createEReference(singleReferenceValueEClass, SINGLE_REFERENCE_VALUE__REFERENCE_VALUE);

		manyReferenceValueEClass = createEClass(MANY_REFERENCE_VALUE);
		createEReference(manyReferenceValueEClass, MANY_REFERENCE_VALUE__REFERENCE_VALUES);

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

		// Obtain other dependent packages
		TracePackage theTracePackage = (TracePackage)EPackage.Registry.INSTANCE.getEPackage(TracePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		ETypeParameter genericTraceEClass_StepSubType = addETypeParameter(genericTraceEClass, "StepSubType");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getGenericStep());
		genericTraceEClass_StepSubType.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(this.getGenericStep());
		genericSequentialStepEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getSequentialStep());
		EGenericType g2 = createEGenericType(this.getGenericStep());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getGenericState());
		g1.getETypeArguments().add(g2);
		genericSequentialStepEClass.getEGenericSuperTypes().add(g1);
		genericParallelStepEClass.getESuperTypes().add(this.getGenericStep());
		genericSmallStepEClass.getESuperTypes().add(this.getGenericStep());
		genericReferenceValueEClass.getESuperTypes().add(this.getGenericValue());
		g1 = createEGenericType(theTracePackage.getDimension());
		g2 = createEGenericType(this.getGenericValue());
		g1.getETypeArguments().add(g2);
		genericDimensionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getTracedObject());
		g2 = createEGenericType(this.getGenericDimension());
		g1.getETypeArguments().add(g2);
		genericTracedObjectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getState());
		g2 = createEGenericType(this.getGenericStep());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getGenericValue());
		g1.getETypeArguments().add(g2);
		genericStateEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getTrace());
		g2 = createEGenericType(genericTraceEClass_StepSubType);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getGenericTracedObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getGenericState());
		g1.getETypeArguments().add(g2);
		genericTraceEClass.getEGenericSuperTypes().add(g1);
		genericAttributeValueEClass.getESuperTypes().add(this.getGenericValue());
		booleanAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		integerAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		stringAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		manyBooleanAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		manyIntegerAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		manyStringAttributeValueEClass.getESuperTypes().add(this.getGenericAttributeValue());
		g1 = createEGenericType(theTracePackage.getStep());
		g2 = createEGenericType(this.getGenericState());
		g1.getETypeArguments().add(g2);
		genericStepEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getValue());
		g2 = createEGenericType(this.getGenericState());
		g1.getETypeArguments().add(g2);
		genericValueEClass.getEGenericSuperTypes().add(g1);
		singleReferenceValueEClass.getESuperTypes().add(this.getGenericReferenceValue());
		manyReferenceValueEClass.getESuperTypes().add(this.getGenericReferenceValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(genericSequentialStepEClass, GenericSequentialStep.class, "GenericSequentialStep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericParallelStepEClass, GenericParallelStep.class, "GenericParallelStep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericSmallStepEClass, GenericSmallStep.class, "GenericSmallStep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericReferenceValueEClass, GenericReferenceValue.class, "GenericReferenceValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericDimensionEClass, GenericDimension.class, "GenericDimension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericDimension_DynamicProperty(), theEcorePackage.getEStructuralFeature(), null, "dynamicProperty", null, 0, 1, GenericDimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericTracedObjectEClass, GenericTracedObject.class, "GenericTracedObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericTracedObject_OriginalObject(), theEcorePackage.getEObject(), null, "originalObject", null, 0, 1, GenericTracedObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGenericTracedObject_AllDimensions(), this.getGenericDimension(), null, "allDimensions", null, 0, -1, GenericTracedObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getGenericTracedObject__GetDimensionsInternal(), this.getGenericDimension(), "getDimensionsInternal", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(genericStateEClass, GenericState.class, "GenericState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericTraceEClass, GenericTrace.class, "GenericTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericAttributeValueEClass, GenericAttributeValue.class, "GenericAttributeValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanAttributeValueEClass, BooleanAttributeValue.class, "BooleanAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanAttributeValue_AttributeValue(), ecorePackage.getEBoolean(), "attributeValue", "false", 0, 1, BooleanAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerAttributeValueEClass, IntegerAttributeValue.class, "IntegerAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerAttributeValue_AttributeValue(), ecorePackage.getEInt(), "attributeValue", null, 0, 1, IntegerAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringAttributeValueEClass, StringAttributeValue.class, "StringAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringAttributeValue_AttributeValue(), ecorePackage.getEString(), "attributeValue", null, 0, 1, StringAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyBooleanAttributeValueEClass, ManyBooleanAttributeValue.class, "ManyBooleanAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManyBooleanAttributeValue_AttributeValue(), ecorePackage.getEBoolean(), "attributeValue", "false", 0, -1, ManyBooleanAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyIntegerAttributeValueEClass, ManyIntegerAttributeValue.class, "ManyIntegerAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManyIntegerAttributeValue_AttributeValue(), ecorePackage.getEInt(), "attributeValue", null, 0, -1, ManyIntegerAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyStringAttributeValueEClass, ManyStringAttributeValue.class, "ManyStringAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManyStringAttributeValue_AttributeValue(), ecorePackage.getEString(), "attributeValue", null, 0, -1, ManyStringAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericStepEClass, GenericStep.class, "GenericStep", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericValueEClass, GenericValue.class, "GenericValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(singleReferenceValueEClass, SingleReferenceValue.class, "SingleReferenceValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSingleReferenceValue_ReferenceValue(), ecorePackage.getEObject(), null, "referenceValue", null, 0, 1, SingleReferenceValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manyReferenceValueEClass, ManyReferenceValue.class, "ManyReferenceValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getManyReferenceValue_ReferenceValues(), ecorePackage.getEObject(), null, "referenceValues", null, 0, -1, ManyReferenceValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(iSerializableEDataType, byte[].class, "ISerializable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //GenerictracePackageImpl
