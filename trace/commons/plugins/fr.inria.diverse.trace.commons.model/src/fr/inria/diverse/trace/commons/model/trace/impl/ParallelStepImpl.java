/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.trace.ParallelStep;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parallel Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ParallelStepImpl<StepSubtype extends Step<StateSubType>, StateSubType extends State<StepSubtype, ?>> extends BigStepImpl<StepSubtype, StateSubType> implements ParallelStep<StepSubtype, StateSubType> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParallelStepImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.PARALLEL_STEP;
	}

} //ParallelStepImpl
