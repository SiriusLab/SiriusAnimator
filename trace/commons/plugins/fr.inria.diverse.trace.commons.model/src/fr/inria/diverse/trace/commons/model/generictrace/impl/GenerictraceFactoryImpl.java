/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.*;

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
public class GenerictraceFactoryImpl extends EFactoryImpl implements GenerictraceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GenerictraceFactory init() {
		try {
			GenerictraceFactory theGenerictraceFactory = (GenerictraceFactory)EPackage.Registry.INSTANCE.getEFactory(GenerictracePackage.eNS_URI);
			if (theGenerictraceFactory != null) {
				return theGenerictraceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GenerictraceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerictraceFactoryImpl() {
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
			case GenerictracePackage.GENERIC_SEQUENTIAL_STEP: return createGenericSequentialStep();
			case GenerictracePackage.GENERIC_PARALLEL_STEP: return createGenericParallelStep();
			case GenerictracePackage.GENERIC_SMALL_STEP: return createGenericSmallStep();
			case GenerictracePackage.GENERIC_DIMENSION: return createGenericDimension();
			case GenerictracePackage.GENERIC_TRACED_OBJECT: return createGenericTracedObject();
			case GenerictracePackage.GENERIC_STATE: return createGenericState();
			case GenerictracePackage.GENERIC_TRACE: return createGenericTrace();
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE: return createBooleanAttributeValue();
			case GenerictracePackage.INTEGER_ATTRIBUTE_VALUE: return createIntegerAttributeValue();
			case GenerictracePackage.STRING_ATTRIBUTE_VALUE: return createStringAttributeValue();
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE: return createManyBooleanAttributeValue();
			case GenerictracePackage.MANY_INTEGER_ATTRIBUTE_VALUE: return createManyIntegerAttributeValue();
			case GenerictracePackage.MANY_STRING_ATTRIBUTE_VALUE: return createManyStringAttributeValue();
			case GenerictracePackage.SINGLE_REFERENCE_VALUE: return createSingleReferenceValue();
			case GenerictracePackage.MANY_REFERENCE_VALUE: return createManyReferenceValue();
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
			case GenerictracePackage.ISERIALIZABLE:
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
			case GenerictracePackage.ISERIALIZABLE:
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
	public GenericSequentialStep createGenericSequentialStep() {
		GenericSequentialStepImpl genericSequentialStep = new GenericSequentialStepImpl();
		return genericSequentialStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericParallelStep createGenericParallelStep() {
		GenericParallelStepImpl genericParallelStep = new GenericParallelStepImpl();
		return genericParallelStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericSmallStep createGenericSmallStep() {
		GenericSmallStepImpl genericSmallStep = new GenericSmallStepImpl();
		return genericSmallStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericDimension createGenericDimension() {
		GenericDimensionImpl genericDimension = new GenericDimensionImpl();
		return genericDimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericTracedObject createGenericTracedObject() {
		GenericTracedObjectImpl genericTracedObject = new GenericTracedObjectImpl();
		return genericTracedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericState createGenericState() {
		GenericStateImpl genericState = new GenericStateImpl();
		return genericState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <StepSubType extends GenericStep> GenericTrace<StepSubType> createGenericTrace() {
		GenericTraceImpl<StepSubType> genericTrace = new GenericTraceImpl<StepSubType>();
		return genericTrace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanAttributeValue createBooleanAttributeValue() {
		BooleanAttributeValueImpl booleanAttributeValue = new BooleanAttributeValueImpl();
		return booleanAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerAttributeValue createIntegerAttributeValue() {
		IntegerAttributeValueImpl integerAttributeValue = new IntegerAttributeValueImpl();
		return integerAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringAttributeValue createStringAttributeValue() {
		StringAttributeValueImpl stringAttributeValue = new StringAttributeValueImpl();
		return stringAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyBooleanAttributeValue createManyBooleanAttributeValue() {
		ManyBooleanAttributeValueImpl manyBooleanAttributeValue = new ManyBooleanAttributeValueImpl();
		return manyBooleanAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyIntegerAttributeValue createManyIntegerAttributeValue() {
		ManyIntegerAttributeValueImpl manyIntegerAttributeValue = new ManyIntegerAttributeValueImpl();
		return manyIntegerAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyStringAttributeValue createManyStringAttributeValue() {
		ManyStringAttributeValueImpl manyStringAttributeValue = new ManyStringAttributeValueImpl();
		return manyStringAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleReferenceValue createSingleReferenceValue() {
		SingleReferenceValueImpl singleReferenceValue = new SingleReferenceValueImpl();
		return singleReferenceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManyReferenceValue createManyReferenceValue() {
		ManyReferenceValueImpl manyReferenceValue = new ManyReferenceValueImpl();
		return manyReferenceValue;
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
	public GenerictracePackage getGenerictracePackage() {
		return (GenerictracePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GenerictracePackage getPackage() {
		return GenerictracePackage.eINSTANCE;
	}

} //GenerictraceFactoryImpl
