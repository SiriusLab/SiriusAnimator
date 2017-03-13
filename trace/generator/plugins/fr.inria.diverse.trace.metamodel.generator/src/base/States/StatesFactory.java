/**
 */
package base.States;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see base.States.StatesPackage
 * @generated
 */
public interface StatesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StatesFactory eINSTANCE = base.States.impl.StatesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Specific State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Specific State</em>'.
	 * @generated
	 */
	SpecificState createSpecificState();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StatesPackage getStatesPackage();

} //StatesFactory
