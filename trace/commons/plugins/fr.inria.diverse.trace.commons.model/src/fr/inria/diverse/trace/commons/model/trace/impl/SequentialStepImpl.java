/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sequential Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class SequentialStepImpl<StepSubtype extends Step<StateSubType>, StateSubType extends State<?, ?>> extends BigStepImpl<StepSubtype, StateSubType> implements SequentialStep<StepSubtype, StateSubType> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SequentialStepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.SEQUENTIAL_STEP;
	}

} //SequentialStepImpl
