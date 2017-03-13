/**
 */
package fr.inria.diverse.trace.commons.model.generictrace.impl;

import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage;

import fr.inria.diverse.trace.commons.model.trace.TracePackage;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.commons.model.trace.impl.TracedObjectImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Traced Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.impl.GenericTracedObjectImpl#getOriginalObject <em>Original Object</em>}</li>
 *   <li>{@link fr.inria.diverse.trace.commons.model.generictrace.impl.GenericTracedObjectImpl#getAllDimensions <em>All Dimensions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericTracedObjectImpl extends TracedObjectImpl<GenericDimension> implements GenericTracedObject {
	/**
	 * The cached value of the '{@link #getOriginalObject() <em>Original Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalObject()
	 * @generated
	 * @ordered
	 */
	protected EObject originalObject;

	/**
	 * The cached value of the '{@link #getAllDimensions() <em>All Dimensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllDimensions()
	 * @generated
	 * @ordered
	 */
	protected EList<GenericDimension> allDimensions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericTracedObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenerictracePackage.Literals.GENERIC_TRACED_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOriginalObject() {
		if (originalObject != null && originalObject.eIsProxy()) {
			InternalEObject oldOriginalObject = (InternalEObject)originalObject;
			originalObject = eResolveProxy(oldOriginalObject);
			if (originalObject != oldOriginalObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT, oldOriginalObject, originalObject));
			}
		}
		return originalObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetOriginalObject() {
		return originalObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalObject(EObject newOriginalObject) {
		EObject oldOriginalObject = originalObject;
		originalObject = newOriginalObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT, oldOriginalObject, originalObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenericDimension> getAllDimensions() {
		if (allDimensions == null) {
			allDimensions = new EObjectContainmentEList<GenericDimension>(GenericDimension.class, this, GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS);
		}
		return allDimensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenericDimension> getDimensionsInternal() {
		final EList<GenericDimension> result = new org.eclipse.emf.ecore.util.BasicInternalEList<GenericDimension>(Object.class);
		result.addAll(super.getDimensionsInternal());
		result.addAll(getAllDimensions());
		return result;
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS:
				return ((InternalEList<?>)getAllDimensions()).basicRemove(otherEnd, msgs);
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
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT:
				if (resolve) return getOriginalObject();
				return basicGetOriginalObject();
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS:
				return getAllDimensions();
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
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT:
				setOriginalObject((EObject)newValue);
				return;
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS:
				getAllDimensions().clear();
				getAllDimensions().addAll((Collection<? extends GenericDimension>)newValue);
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
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT:
				setOriginalObject((EObject)null);
				return;
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS:
				getAllDimensions().clear();
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
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ORIGINAL_OBJECT:
				return originalObject != null;
			case GenerictracePackage.GENERIC_TRACED_OBJECT__ALL_DIMENSIONS:
				return allDimensions != null && !allDimensions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TracedObject.class) {
			switch (baseOperationID) {
				case TracePackage.TRACED_OBJECT___GET_DIMENSIONS_INTERNAL: return GenerictracePackage.GENERIC_TRACED_OBJECT___GET_DIMENSIONS_INTERNAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case GenerictracePackage.GENERIC_TRACED_OBJECT___GET_DIMENSIONS_INTERNAL:
				return getDimensionsInternal();
		}
		return super.eInvoke(operationID, arguments);
	}

} //GenericTracedObjectImpl
