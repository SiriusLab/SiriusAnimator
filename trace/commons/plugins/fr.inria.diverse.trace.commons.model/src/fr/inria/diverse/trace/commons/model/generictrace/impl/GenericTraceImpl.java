/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTrace;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class GenericTraceImpl<StepSubType extends GenericStep> extends TraceImpl<StepSubType, GenericTracedObject, GenericState> implements GenericTrace<StepSubType> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.GENERIC_TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetRootStep(StepSubType newRootStep, NotificationChain msgs) {
		return super.basicSetRootStep(newRootStep, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<GenericTracedObject> getTracedObjects() {
		if (tracedObjects == null) {
			tracedObjects = new EObjectContainmentEList<GenericTracedObject>(GenericTracedObject.class, this, GenerictracePackage.GENERIC_TRACE__TRACED_OBJECTS);
		}
		return tracedObjects;
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
			states = new EObjectContainmentEList<GenericState>(GenericState.class, this, GenerictracePackage.GENERIC_TRACE__STATES);
		}
		return states;
	}

} //GenericTraceImpl
