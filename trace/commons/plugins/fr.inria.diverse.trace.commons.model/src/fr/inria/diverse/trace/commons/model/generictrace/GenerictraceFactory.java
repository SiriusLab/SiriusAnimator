/**
 */
package fr.inria.diverse.trace.commons.model.generictrace;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.generictrace.GenerictracePackage
 * @generated
 */
public interface GenerictraceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenerictraceFactory eINSTANCE = fr.inria.diverse.trace.commons.model.generictrace.impl.GenerictraceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Generic Sequential Step</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Sequential Step</em>'.
	 * @generated
	 */
	GenericSequentialStep createGenericSequentialStep();

	/**
	 * Returns a new object of class '<em>Generic Parallel Step</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Parallel Step</em>'.
	 * @generated
	 */
	GenericParallelStep createGenericParallelStep();

	/**
	 * Returns a new object of class '<em>Generic Small Step</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Small Step</em>'.
	 * @generated
	 */
	GenericSmallStep createGenericSmallStep();

	/**
	 * Returns a new object of class '<em>Generic Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Dimension</em>'.
	 * @generated
	 */
	GenericDimension createGenericDimension();

	/**
	 * Returns a new object of class '<em>Generic Traced Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Traced Object</em>'.
	 * @generated
	 */
	GenericTracedObject createGenericTracedObject();

	/**
	 * Returns a new object of class '<em>Generic State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic State</em>'.
	 * @generated
	 */
	GenericState createGenericState();

	/**
	 * Returns a new object of class '<em>Generic Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Trace</em>'.
	 * @generated
	 */
	<StepSubType extends GenericStep> GenericTrace<StepSubType> createGenericTrace();

	/**
	 * Returns a new object of class '<em>Boolean Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Attribute Value</em>'.
	 * @generated
	 */
	BooleanAttributeValue createBooleanAttributeValue();

	/**
	 * Returns a new object of class '<em>Integer Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Attribute Value</em>'.
	 * @generated
	 */
	IntegerAttributeValue createIntegerAttributeValue();

	/**
	 * Returns a new object of class '<em>String Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Attribute Value</em>'.
	 * @generated
	 */
	StringAttributeValue createStringAttributeValue();

	/**
	 * Returns a new object of class '<em>Many Boolean Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many Boolean Attribute Value</em>'.
	 * @generated
	 */
	ManyBooleanAttributeValue createManyBooleanAttributeValue();

	/**
	 * Returns a new object of class '<em>Many Integer Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many Integer Attribute Value</em>'.
	 * @generated
	 */
	ManyIntegerAttributeValue createManyIntegerAttributeValue();

	/**
	 * Returns a new object of class '<em>Many String Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many String Attribute Value</em>'.
	 * @generated
	 */
	ManyStringAttributeValue createManyStringAttributeValue();

	/**
	 * Returns a new object of class '<em>Single Reference Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Single Reference Value</em>'.
	 * @generated
	 */
	SingleReferenceValue createSingleReferenceValue();

	/**
	 * Returns a new object of class '<em>Many Reference Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Many Reference Value</em>'.
	 * @generated
	 */
	ManyReferenceValue createManyReferenceValue();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GenerictracePackage getGenerictracePackage();

} //GenerictraceFactory
