/**
 */
package base.impl;

import base.BasePackage;
import base.SpecificTrace;

import base.States.SpecificDimension;
import base.States.SpecificState;
import base.States.SpecificTracedObject;
import base.States.SpecificValue;

import base.Steps.SpecificStep;

import fr.inria.diverse.trace.commons.model.trace.SequentialStep;

import fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specific Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class SpecificTraceImpl extends TraceImpl<SequentialStep<? extends SpecificStep, ? extends SpecificState>, SpecificTracedObject<? extends SpecificDimension<? extends SpecificValue>>, SpecificState> implements SpecificTrace {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecificTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.SPECIFIC_TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetRootStep(SequentialStep<? extends SpecificStep, ? extends SpecificState> newRootStep, NotificationChain msgs) {
		return super.basicSetRootStep(newRootStep, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<SpecificTracedObject<? extends SpecificDimension<? extends SpecificValue>>> getTracedObjects() {
		if (tracedObjects == null) {
			tracedObjects = new EObjectContainmentEList<SpecificTracedObject<? extends SpecificDimension<? extends SpecificValue>>>(SpecificTracedObject.class, this, BasePackage.SPECIFIC_TRACE__TRACED_OBJECTS);
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
	public EList<SpecificState> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<SpecificState>(SpecificState.class, this, BasePackage.SPECIFIC_TRACE__STATES);
		}
		return states;
	}

} //SpecificTraceImpl
