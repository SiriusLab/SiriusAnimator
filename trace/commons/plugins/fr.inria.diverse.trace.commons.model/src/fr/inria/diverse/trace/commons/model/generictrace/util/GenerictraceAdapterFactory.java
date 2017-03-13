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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage
 * @generated
 */
public class GenerictraceAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenerictracePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenerictraceAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GenerictracePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenerictraceSwitch<Adapter> modelSwitch =
		new GenerictraceSwitch<Adapter>() {
			@Override
			public Adapter caseGenericSequentialStep(GenericSequentialStep object) {
				return createGenericSequentialStepAdapter();
			}
			@Override
			public Adapter caseGenericParallelStep(GenericParallelStep object) {
				return createGenericParallelStepAdapter();
			}
			@Override
			public Adapter caseGenericSmallStep(GenericSmallStep object) {
				return createGenericSmallStepAdapter();
			}
			@Override
			public Adapter caseGenericReferenceValue(GenericReferenceValue object) {
				return createGenericReferenceValueAdapter();
			}
			@Override
			public Adapter caseGenericDimension(GenericDimension object) {
				return createGenericDimensionAdapter();
			}
			@Override
			public Adapter caseGenericTracedObject(GenericTracedObject object) {
				return createGenericTracedObjectAdapter();
			}
			@Override
			public Adapter caseGenericState(GenericState object) {
				return createGenericStateAdapter();
			}
			@Override
			public <StepSubType extends GenericStep> Adapter caseGenericTrace(GenericTrace<StepSubType> object) {
				return createGenericTraceAdapter();
			}
			@Override
			public Adapter caseGenericAttributeValue(GenericAttributeValue object) {
				return createGenericAttributeValueAdapter();
			}
			@Override
			public Adapter caseBooleanAttributeValue(BooleanAttributeValue object) {
				return createBooleanAttributeValueAdapter();
			}
			@Override
			public Adapter caseIntegerAttributeValue(IntegerAttributeValue object) {
				return createIntegerAttributeValueAdapter();
			}
			@Override
			public Adapter caseStringAttributeValue(StringAttributeValue object) {
				return createStringAttributeValueAdapter();
			}
			@Override
			public Adapter caseManyBooleanAttributeValue(ManyBooleanAttributeValue object) {
				return createManyBooleanAttributeValueAdapter();
			}
			@Override
			public Adapter caseManyIntegerAttributeValue(ManyIntegerAttributeValue object) {
				return createManyIntegerAttributeValueAdapter();
			}
			@Override
			public Adapter caseManyStringAttributeValue(ManyStringAttributeValue object) {
				return createManyStringAttributeValueAdapter();
			}
			@Override
			public Adapter caseGenericStep(GenericStep object) {
				return createGenericStepAdapter();
			}
			@Override
			public Adapter caseGenericValue(GenericValue object) {
				return createGenericValueAdapter();
			}
			@Override
			public Adapter caseSingleReferenceValue(SingleReferenceValue object) {
				return createSingleReferenceValueAdapter();
			}
			@Override
			public Adapter caseManyReferenceValue(ManyReferenceValue object) {
				return createManyReferenceValueAdapter();
			}
			@Override
			public <StateSubType extends State<?, ?>> Adapter caseStep(Step<StateSubType> object) {
				return createStepAdapter();
			}
			@Override
			public <StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> Adapter caseBigStep(BigStep<StepSubtype, StateSubType> object) {
				return createBigStepAdapter();
			}
			@Override
			public <StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> Adapter caseSequentialStep(SequentialStep<StepSubtype, StateSubType> object) {
				return createSequentialStepAdapter();
			}
			@Override
			public <StateSubType extends State<?, ?>> Adapter caseValue(Value<StateSubType> object) {
				return createValueAdapter();
			}
			@Override
			public <ValueSubType extends Value<?>> Adapter caseDimension(Dimension<ValueSubType> object) {
				return createDimensionAdapter();
			}
			@Override
			public <DimensionSubType extends Dimension<?>> Adapter caseTracedObject(TracedObject<DimensionSubType> object) {
				return createTracedObjectAdapter();
			}
			@Override
			public <StepSubType extends Step<?>, ValueSubType extends Value<?>> Adapter caseState(State<StepSubType, ValueSubType> object) {
				return createStateAdapter();
			}
			@Override
			public <StepSubType extends Step<?>, TracedObjectSubtype extends TracedObject<?>, StateSubType extends State<?, ?>> Adapter caseTrace(Trace<StepSubType, TracedObjectSubtype, StateSubType> object) {
				return createTraceAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep <em>Generic Sequential Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep
	 * @generated
	 */
	public Adapter createGenericSequentialStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericParallelStep <em>Generic Parallel Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericParallelStep
	 * @generated
	 */
	public Adapter createGenericParallelStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericSmallStep <em>Generic Small Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericSmallStep
	 * @generated
	 */
	public Adapter createGenericSmallStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericReferenceValue <em>Generic Reference Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericReferenceValue
	 * @generated
	 */
	public Adapter createGenericReferenceValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericDimension <em>Generic Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericDimension
	 * @generated
	 */
	public Adapter createGenericDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject <em>Generic Traced Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject
	 * @generated
	 */
	public Adapter createGenericTracedObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericState <em>Generic State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericState
	 * @generated
	 */
	public Adapter createGenericStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericTrace <em>Generic Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericTrace
	 * @generated
	 */
	public Adapter createGenericTraceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericAttributeValue <em>Generic Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericAttributeValue
	 * @generated
	 */
	public Adapter createGenericAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue <em>Boolean Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue
	 * @generated
	 */
	public Adapter createBooleanAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.IntegerAttributeValue <em>Integer Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.IntegerAttributeValue
	 * @generated
	 */
	public Adapter createIntegerAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.StringAttributeValue <em>String Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.StringAttributeValue
	 * @generated
	 */
	public Adapter createStringAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.ManyBooleanAttributeValue <em>Many Boolean Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.ManyBooleanAttributeValue
	 * @generated
	 */
	public Adapter createManyBooleanAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.ManyIntegerAttributeValue <em>Many Integer Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.ManyIntegerAttributeValue
	 * @generated
	 */
	public Adapter createManyIntegerAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.ManyStringAttributeValue <em>Many String Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.ManyStringAttributeValue
	 * @generated
	 */
	public Adapter createManyStringAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericStep <em>Generic Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericStep
	 * @generated
	 */
	public Adapter createGenericStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.GenericValue <em>Generic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.GenericValue
	 * @generated
	 */
	public Adapter createGenericValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue <em>Single Reference Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue
	 * @generated
	 */
	public Adapter createSingleReferenceValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue <em>Many Reference Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue
	 * @generated
	 */
	public Adapter createManyReferenceValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.Step <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.Step
	 * @generated
	 */
	public Adapter createStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.BigStep <em>Big Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.BigStep
	 * @generated
	 */
	public Adapter createBigStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.SequentialStep <em>Sequential Step</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.SequentialStep
	 * @generated
	 */
	public Adapter createSequentialStepAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.Value
	 * @generated
	 */
	public Adapter createValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.TracedObject <em>Traced Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracedObject
	 * @generated
	 */
	public Adapter createTracedObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.State
	 * @generated
	 */
	public Adapter createStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.trace.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace
	 * @generated
	 */
	public Adapter createTraceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //GenerictraceAdapterFactory
