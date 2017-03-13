/**
 */
package base.States.impl;

import base.BasePackage;

import base.States.SpecificAttributeValue;
import base.States.SpecificDimension;
import base.States.SpecificReferenceValue;
import base.States.SpecificState;
import base.States.SpecificTracedObject;
import base.States.SpecificValue;
import base.States.StatesFactory;
import base.States.StatesPackage;

import base.Steps.StepsPackage;

import base.Steps.impl.StepsPackageImpl;

import base.impl.BasePackageImpl;

import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatesPackageImpl extends EPackageImpl implements StatesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificDimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificTracedObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificReferenceValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass specificValueEClass = null;

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
	 * @see base.States.StatesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StatesPackageImpl() {
		super(eNS_URI, StatesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link StatesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StatesPackage init() {
		if (isInited) return (StatesPackage)EPackage.Registry.INSTANCE.getEPackage(StatesPackage.eNS_URI);

		// Obtain or create and register package
		StatesPackageImpl theStatesPackage = (StatesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof StatesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new StatesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TracePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		BasePackageImpl theBasePackage = (BasePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) instanceof BasePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) : BasePackage.eINSTANCE);
		StepsPackageImpl theStepsPackage = (StepsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(StepsPackage.eNS_URI) instanceof StepsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(StepsPackage.eNS_URI) : StepsPackage.eINSTANCE);

		// Create package meta-data objects
		theStatesPackage.createPackageContents();
		theBasePackage.createPackageContents();
		theStepsPackage.createPackageContents();

		// Initialize created meta-data
		theStatesPackage.initializePackageContents();
		theBasePackage.initializePackageContents();
		theStepsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStatesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StatesPackage.eNS_URI, theStatesPackage);
		return theStatesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificState() {
		return specificStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificAttributeValue() {
		return specificAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificDimension() {
		return specificDimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificTracedObject() {
		return specificTracedObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificReferenceValue() {
		return specificReferenceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpecificValue() {
		return specificValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatesFactory getStatesFactory() {
		return (StatesFactory)getEFactoryInstance();
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
		specificStateEClass = createEClass(SPECIFIC_STATE);

		specificAttributeValueEClass = createEClass(SPECIFIC_ATTRIBUTE_VALUE);

		specificDimensionEClass = createEClass(SPECIFIC_DIMENSION);

		specificTracedObjectEClass = createEClass(SPECIFIC_TRACED_OBJECT);

		specificReferenceValueEClass = createEClass(SPECIFIC_REFERENCE_VALUE);

		specificValueEClass = createEClass(SPECIFIC_VALUE);
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
		StepsPackage theStepsPackage = (StepsPackage)EPackage.Registry.INSTANCE.getEPackage(StepsPackage.eNS_URI);

		// Create type parameters
		ETypeParameter specificDimensionEClass_ValueSubType = addETypeParameter(specificDimensionEClass, "ValueSubType");
		ETypeParameter specificTracedObjectEClass_DimensionSubType = addETypeParameter(specificTracedObjectEClass, "DimensionSubType");
		addETypeParameter(specificReferenceValueEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getSpecificValue());
		specificDimensionEClass_ValueSubType.getEBounds().add(g1);
		g1 = createEGenericType(this.getSpecificDimension());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getSpecificValue());
		g2.setEUpperBound(g3);
		specificTracedObjectEClass_DimensionSubType.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(theTracePackage.getState());
		g2 = createEGenericType(theStepsPackage.getSpecificStep());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getSpecificValue());
		g1.getETypeArguments().add(g2);
		specificStateEClass.getEGenericSuperTypes().add(g1);
		specificAttributeValueEClass.getESuperTypes().add(this.getSpecificValue());
		g1 = createEGenericType(theTracePackage.getDimension());
		g2 = createEGenericType(specificDimensionEClass_ValueSubType);
		g1.getETypeArguments().add(g2);
		specificDimensionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theTracePackage.getTracedObject());
		g2 = createEGenericType(specificTracedObjectEClass_DimensionSubType);
		g1.getETypeArguments().add(g2);
		specificTracedObjectEClass.getEGenericSuperTypes().add(g1);
		specificReferenceValueEClass.getESuperTypes().add(this.getSpecificValue());
		g1 = createEGenericType(theTracePackage.getValue());
		g2 = createEGenericType(this.getSpecificState());
		g1.getETypeArguments().add(g2);
		specificValueEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(specificStateEClass, SpecificState.class, "SpecificState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specificAttributeValueEClass, SpecificAttributeValue.class, "SpecificAttributeValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specificDimensionEClass, SpecificDimension.class, "SpecificDimension", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specificTracedObjectEClass, SpecificTracedObject.class, "SpecificTracedObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specificReferenceValueEClass, SpecificReferenceValue.class, "SpecificReferenceValue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(specificValueEClass, SpecificValue.class, "SpecificValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //StatesPackageImpl
