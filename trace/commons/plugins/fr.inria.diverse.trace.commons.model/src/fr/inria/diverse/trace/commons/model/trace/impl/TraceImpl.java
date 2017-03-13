/**
 */
package fr.inria.diverse.trace.commons.model.trace.impl;

import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;

import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracePackage;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl#getRootStep <em>Root Step</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl#getTracedObjects <em>Traced Objects</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl#getStates <em>States</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl#getLaunchconfiguration <em>Launchconfiguration</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TraceImpl<StepSubType extends Step<?>, TracedObjectSubtype extends TracedObject<?>, StateSubType extends State<?, ?>> extends MinimalEObjectImpl.Container implements Trace<StepSubType, TracedObjectSubtype, StateSubType> {
	/**
	 * The cached value of the '{@link #getRootStep() <em>Root Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootStep()
	 * @generated
	 * @ordered
	 */
	protected StepSubType rootStep;

	/**
	 * The cached value of the '{@link #getTracedObjects() <em>Traced Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTracedObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<TracedObjectSubtype> tracedObjects;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<StateSubType> states;

	/**
	 * The cached value of the '{@link #getLaunchconfiguration() <em>Launchconfiguration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLaunchconfiguration()
	 * @generated
	 * @ordered
	 */
	protected LaunchConfiguration launchconfiguration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TracePackage.Literals.TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StepSubType getRootStep() {
		return rootStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootStep(StepSubType newRootStep, NotificationChain msgs) {
		StepSubType oldRootStep = rootStep;
		rootStep = newRootStep;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__ROOT_STEP, oldRootStep, newRootStep);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootStep(StepSubType newRootStep) {
		if (newRootStep != rootStep) {
			NotificationChain msgs = null;
			if (rootStep != null)
				msgs = ((InternalEObject)rootStep).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TracePackage.TRACE__ROOT_STEP, null, msgs);
			if (newRootStep != null)
				msgs = ((InternalEObject)newRootStep).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TracePackage.TRACE__ROOT_STEP, null, msgs);
			msgs = basicSetRootStep(newRootStep, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__ROOT_STEP, newRootStep, newRootStep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TracedObjectSubtype> getTracedObjects() {
		if (tracedObjects == null) {
			tracedObjects = new EObjectContainmentEList<TracedObjectSubtype>(TracedObject.class, this, TracePackage.TRACE__TRACED_OBJECTS);
		}
		return tracedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StateSubType> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<StateSubType>(State.class, this, TracePackage.TRACE__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchConfiguration getLaunchconfiguration() {
		return launchconfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLaunchconfiguration(LaunchConfiguration newLaunchconfiguration, NotificationChain msgs) {
		LaunchConfiguration oldLaunchconfiguration = launchconfiguration;
		launchconfiguration = newLaunchconfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__LAUNCHCONFIGURATION, oldLaunchconfiguration, newLaunchconfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLaunchconfiguration(LaunchConfiguration newLaunchconfiguration) {
		if (newLaunchconfiguration != launchconfiguration) {
			NotificationChain msgs = null;
			if (launchconfiguration != null)
				msgs = ((InternalEObject)launchconfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TracePackage.TRACE__LAUNCHCONFIGURATION, null, msgs);
			if (newLaunchconfiguration != null)
				msgs = ((InternalEObject)newLaunchconfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TracePackage.TRACE__LAUNCHCONFIGURATION, null, msgs);
			msgs = basicSetLaunchconfiguration(newLaunchconfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__LAUNCHCONFIGURATION, newLaunchconfiguration, newLaunchconfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.TRACE__ROOT_STEP:
				return basicSetRootStep(null, msgs);
			case TracePackage.TRACE__TRACED_OBJECTS:
				return ((InternalEList<?>)getTracedObjects()).basicRemove(otherEnd, msgs);
			case TracePackage.TRACE__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case TracePackage.TRACE__LAUNCHCONFIGURATION:
				return basicSetLaunchconfiguration(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.TRACE__ROOT_STEP:
				return getRootStep();
			case TracePackage.TRACE__TRACED_OBJECTS:
				return getTracedObjects();
			case TracePackage.TRACE__STATES:
				return getStates();
			case TracePackage.TRACE__LAUNCHCONFIGURATION:
				return getLaunchconfiguration();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TracePackage.TRACE__ROOT_STEP:
				setRootStep((StepSubType)newValue);
				return;
			case TracePackage.TRACE__TRACED_OBJECTS:
				getTracedObjects().clear();
				getTracedObjects().addAll((Collection<? extends TracedObjectSubtype>)newValue);
				return;
			case TracePackage.TRACE__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends StateSubType>)newValue);
				return;
			case TracePackage.TRACE__LAUNCHCONFIGURATION:
				setLaunchconfiguration((LaunchConfiguration)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TracePackage.TRACE__ROOT_STEP:
				setRootStep((StepSubType)null);
				return;
			case TracePackage.TRACE__TRACED_OBJECTS:
				getTracedObjects().clear();
				return;
			case TracePackage.TRACE__STATES:
				getStates().clear();
				return;
			case TracePackage.TRACE__LAUNCHCONFIGURATION:
				setLaunchconfiguration((LaunchConfiguration)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TracePackage.TRACE__ROOT_STEP:
				return rootStep != null;
			case TracePackage.TRACE__TRACED_OBJECTS:
				return tracedObjects != null && !tracedObjects.isEmpty();
			case TracePackage.TRACE__STATES:
				return states != null && !states.isEmpty();
			case TracePackage.TRACE__LAUNCHCONFIGURATION:
				return launchconfiguration != null;
		}
		return super.eIsSet(featureID);
	}

} //TraceImpl
