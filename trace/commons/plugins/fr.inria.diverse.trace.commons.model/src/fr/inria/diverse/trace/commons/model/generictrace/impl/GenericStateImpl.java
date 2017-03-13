/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;
import fr.inria.diverse.trace.commons.model.trace.Value;

import fr.inria.diverse.trace.commons.model.trace.impl.StateImpl;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic State</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class GenericStateImpl extends StateImpl<GenericStep, GenericValue> implements GenericState {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.GENERIC_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericStep> getStartedSteps() {
		if (startedSteps == null) {
			startedSteps = new EObjectWithInverseResolvingEList<GenericStep>(GenericStep.class, this, GenerictracePackage.GENERIC_STATE__STARTED_STEPS, TracePackage.STEP__STARTING_STATE) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return Step.class; } };
		}
		return startedSteps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericStep> getEndedSteps() {
		if (endedSteps == null) {
			endedSteps = new EObjectWithInverseResolvingEList<GenericStep>(GenericStep.class, this, GenerictracePackage.GENERIC_STATE__ENDED_STEPS, TracePackage.STEP__ENDING_STATE) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return Step.class; } };
		}
		return endedSteps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericValue> getValues() {
		if (values == null) {
			values = new EObjectWithInverseResolvingEList.ManyInverse<GenericValue>(GenericValue.class, this, GenerictracePackage.GENERIC_STATE__VALUES, TracePackage.VALUE__STATES) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return Value.class; } };
		}
		return values;
	}

} //GenericStateImpl
