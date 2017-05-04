/**
 */
package fr.inria.diverse.event.commons.model.property;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.event.commons.model.property.PropertyPackage
 * @generated
 */
public interface PropertyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PropertyFactory eINSTANCE = fr.inria.diverse.event.commons.model.property.impl.PropertyFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Container Reference Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Container Reference Property</em>'.
	 * @generated
	 */
	<P extends StateProperty<?>, T> ContainerReferenceProperty<P, T> createContainerReferenceProperty();

	/**
	 * Returns a new object of class '<em>Many Boolean Attribute Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many Boolean Attribute Property</em>'.
	 * @generated
	 */
	<T> ManyBooleanAttributeProperty<T> createManyBooleanAttributeProperty();

	/**
	 * Returns a new object of class '<em>Many Integer Attribute Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many Integer Attribute Property</em>'.
	 * @generated
	 */
	<T> ManyIntegerAttributeProperty<T> createManyIntegerAttributeProperty();

	/**
	 * Returns a new object of class '<em>Many String Attribute Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many String Attribute Property</em>'.
	 * @generated
	 */
	<T> ManyStringAttributeProperty<T> createManyStringAttributeProperty();

	/**
	 * Returns a new object of class '<em>Step Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Step Property</em>'.
	 * @generated
	 */
	StepProperty createStepProperty();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PropertyPackage getPropertyPackage();

} //PropertyFactory
