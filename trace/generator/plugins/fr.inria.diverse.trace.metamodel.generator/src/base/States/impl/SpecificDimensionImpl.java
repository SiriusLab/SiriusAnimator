/**
 */
package base.States.impl;

import base.States.SpecificDimension;
import base.States.SpecificValue;
import base.States.StatesPackage;

import fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specific Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class SpecificDimensionImpl<ValueSubType extends SpecificValue> extends DimensionImpl<ValueSubType> implements SpecificDimension<ValueSubType> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SpecificDimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatesPackage.Literals.SPECIFIC_DIMENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific element type known in this context.
	 * @generated
	 */
	@Override
	public EList<ValueSubType> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<ValueSubType>(SpecificValue.class, this, StatesPackage.SPECIFIC_DIMENSION__VALUES);
		}
		return values;
	}

} //SpecificDimensionImpl
