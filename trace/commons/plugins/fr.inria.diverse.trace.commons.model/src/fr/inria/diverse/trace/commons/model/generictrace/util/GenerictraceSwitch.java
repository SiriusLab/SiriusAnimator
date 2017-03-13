/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.util;

import fr.inria.diverse.trace.commons.model.generictrace.*;

import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.Dimension;
import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.Value;

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
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage
 * @generated
 */
public class GenerictraceSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenerictracePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerictraceSwitch() {
		if (modelPackage == null) {
			modelPackage = GenerictracePackage.eINSTANCE;
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
			case GenerictracePackage.GENERIC_SEQUENTIAL_STEP: {
				GenericSequentialStep genericSequentialStep = (GenericSequentialStep)theEObject;
				T result = caseGenericSequentialStep(genericSequentialStep);
				if (result == null) result = caseGenericStep(genericSequentialStep);
				if (result == null) result = caseSequentialStep(genericSequentialStep);
				if (result == null) result = caseBigStep(genericSequentialStep);
				if (result == null) result = caseStep(genericSequentialStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_PARALLEL_STEP: {
				GenericParallelStep genericParallelStep = (GenericParallelStep)theEObject;
				T result = caseGenericParallelStep(genericParallelStep);
				if (result == null) result = caseGenericStep(genericParallelStep);
				if (result == null) result = caseStep(genericParallelStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_SMALL_STEP: {
				GenericSmallStep genericSmallStep = (GenericSmallStep)theEObject;
				T result = caseGenericSmallStep(genericSmallStep);
				if (result == null) result = caseGenericStep(genericSmallStep);
				if (result == null) result = caseStep(genericSmallStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_REFERENCE_VALUE: {
				GenericReferenceValue genericReferenceValue = (GenericReferenceValue)theEObject;
				T result = caseGenericReferenceValue(genericReferenceValue);
				if (result == null) result = caseGenericValue(genericReferenceValue);
				if (result == null) result = caseValue(genericReferenceValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_DIMENSION: {
				GenericDimension genericDimension = (GenericDimension)theEObject;
				T result = caseGenericDimension(genericDimension);
				if (result == null) result = caseDimension(genericDimension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_TRACED_OBJECT: {
				GenericTracedObject genericTracedObject = (GenericTracedObject)theEObject;
				T result = caseGenericTracedObject(genericTracedObject);
				if (result == null) result = caseTracedObject(genericTracedObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_STATE: {
				GenericState genericState = (GenericState)theEObject;
				T result = caseGenericState(genericState);
				if (result == null) result = caseState(genericState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_TRACE: {
				GenericTrace<?> genericTrace = (GenericTrace<?>)theEObject;
				T result = caseGenericTrace(genericTrace);
				if (result == null) result = caseTrace(genericTrace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_ATTRIBUTE_VALUE: {
				GenericAttributeValue genericAttributeValue = (GenericAttributeValue)theEObject;
				T result = caseGenericAttributeValue(genericAttributeValue);
				if (result == null) result = caseGenericValue(genericAttributeValue);
				if (result == null) result = caseValue(genericAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.BOOLEAN_ATTRIBUTE_VALUE: {
				BooleanAttributeValue booleanAttributeValue = (BooleanAttributeValue)theEObject;
				T result = caseBooleanAttributeValue(booleanAttributeValue);
				if (result == null) result = caseGenericAttributeValue(booleanAttributeValue);
				if (result == null) result = caseGenericValue(booleanAttributeValue);
				if (result == null) result = caseValue(booleanAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.INTEGER_ATTRIBUTE_VALUE: {
				IntegerAttributeValue integerAttributeValue = (IntegerAttributeValue)theEObject;
				T result = caseIntegerAttributeValue(integerAttributeValue);
				if (result == null) result = caseGenericAttributeValue(integerAttributeValue);
				if (result == null) result = caseGenericValue(integerAttributeValue);
				if (result == null) result = caseValue(integerAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.STRING_ATTRIBUTE_VALUE: {
				StringAttributeValue stringAttributeValue = (StringAttributeValue)theEObject;
				T result = caseStringAttributeValue(stringAttributeValue);
				if (result == null) result = caseGenericAttributeValue(stringAttributeValue);
				if (result == null) result = caseGenericValue(stringAttributeValue);
				if (result == null) result = caseValue(stringAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.MANY_BOOLEAN_ATTRIBUTE_VALUE: {
				ManyBooleanAttributeValue manyBooleanAttributeValue = (ManyBooleanAttributeValue)theEObject;
				T result = caseManyBooleanAttributeValue(manyBooleanAttributeValue);
				if (result == null) result = caseGenericAttributeValue(manyBooleanAttributeValue);
				if (result == null) result = caseGenericValue(manyBooleanAttributeValue);
				if (result == null) result = caseValue(manyBooleanAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.MANY_INTEGER_ATTRIBUTE_VALUE: {
				ManyIntegerAttributeValue manyIntegerAttributeValue = (ManyIntegerAttributeValue)theEObject;
				T result = caseManyIntegerAttributeValue(manyIntegerAttributeValue);
				if (result == null) result = caseGenericAttributeValue(manyIntegerAttributeValue);
				if (result == null) result = caseGenericValue(manyIntegerAttributeValue);
				if (result == null) result = caseValue(manyIntegerAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.MANY_STRING_ATTRIBUTE_VALUE: {
				ManyStringAttributeValue manyStringAttributeValue = (ManyStringAttributeValue)theEObject;
				T result = caseManyStringAttributeValue(manyStringAttributeValue);
				if (result == null) result = caseGenericAttributeValue(manyStringAttributeValue);
				if (result == null) result = caseGenericValue(manyStringAttributeValue);
				if (result == null) result = caseValue(manyStringAttributeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_STEP: {
				GenericStep genericStep = (GenericStep)theEObject;
				T result = caseGenericStep(genericStep);
				if (result == null) result = caseStep(genericStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.GENERIC_VALUE: {
				GenericValue genericValue = (GenericValue)theEObject;
				T result = caseGenericValue(genericValue);
				if (result == null) result = caseValue(genericValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.SINGLE_REFERENCE_VALUE: {
				SingleReferenceValue singleReferenceValue = (SingleReferenceValue)theEObject;
				T result = caseSingleReferenceValue(singleReferenceValue);
				if (result == null) result = caseGenericReferenceValue(singleReferenceValue);
				if (result == null) result = caseGenericValue(singleReferenceValue);
				if (result == null) result = caseValue(singleReferenceValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenerictracePackage.MANY_REFERENCE_VALUE: {
				ManyReferenceValue manyReferenceValue = (ManyReferenceValue)theEObject;
				T result = caseManyReferenceValue(manyReferenceValue);
				if (result == null) result = caseGenericReferenceValue(manyReferenceValue);
				if (result == null) result = caseGenericValue(manyReferenceValue);
				if (result == null) result = caseValue(manyReferenceValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Sequential Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Sequential Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericSequentialStep(GenericSequentialStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Parallel Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Parallel Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericParallelStep(GenericParallelStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Small Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Small Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericSmallStep(GenericSmallStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Reference Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Reference Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericReferenceValue(GenericReferenceValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericDimension(GenericDimension object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Traced Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Traced Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericTracedObject(GenericTracedObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericState(GenericState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StepSubType extends GenericStep> T caseGenericTrace(GenericTrace<StepSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericAttributeValue(GenericAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanAttributeValue(BooleanAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerAttributeValue(IntegerAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringAttributeValue(StringAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many Boolean Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many Boolean Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyBooleanAttributeValue(ManyBooleanAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many Integer Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many Integer Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyIntegerAttributeValue(ManyIntegerAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many String Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many String Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyStringAttributeValue(ManyStringAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericStep(GenericStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericValue(GenericValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single Reference Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single Reference Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleReferenceValue(SingleReferenceValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many Reference Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many Reference Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyReferenceValue(ManyReferenceValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StateSubType extends State<?, ?>> T caseStep(Step<StateSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Big Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Big Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> T caseBigStep(BigStep<StepSubtype, StateSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequential Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequential Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> T caseSequentialStep(SequentialStep<StepSubtype, StateSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StateSubType extends State<?, ?>> T caseValue(Value<StateSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <ValueSubType extends Value<?>> T caseDimension(Dimension<ValueSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Traced Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Traced Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <DimensionSubType extends Dimension<?>> T caseTracedObject(TracedObject<DimensionSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StepSubType extends Step<?>, ValueSubType extends Value<?>> T caseState(State<StepSubType, ValueSubType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <StepSubType extends Step<?>, TracedObjectSubtype extends TracedObject<?>, StateSubType extends State<?, ?>> T caseTrace(Trace<StepSubType, TracedObjectSubtype, StateSubType> object) {
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

} //GenerictraceSwitch
