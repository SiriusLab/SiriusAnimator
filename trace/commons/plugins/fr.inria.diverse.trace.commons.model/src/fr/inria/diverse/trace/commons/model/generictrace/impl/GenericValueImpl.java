/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;

import fr.inria.diverse.trace.commons.model.trace.impl.ValueImpl;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class GenericValueImpl extends ValueImpl<GenericState> implements GenericValue {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.GENERIC_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericState> getStates() {
		if (states == null) {
			states = new EObjectWithInverseResolvingEList.ManyInverse<GenericState>(GenericState.class, this, GenerictracePackage.GENERIC_VALUE__STATES, TracePackage.STATE__VALUES) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return State.class; } };
		}
		return states;
	}

} //GenericValueImpl
