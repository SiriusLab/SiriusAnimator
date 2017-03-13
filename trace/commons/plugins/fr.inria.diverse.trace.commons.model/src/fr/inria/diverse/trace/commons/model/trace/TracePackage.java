/**
 */
package fr.inria.diverse.trace.commons.model.trace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.trace.TraceFactory
 * @model kind="package"
 * @generated
 */
public interface TracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "trace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.gemoc.org/generic_trace";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "trace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TracePackage eINSTANCE = fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl <em>MSE Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSEOccurrence()
	 * @generated
	 */
	int MSE_OCCURRENCE = 0;

	/**
	 * The feature id for the '<em><b>Mse</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OCCURRENCE__MSE = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OCCURRENCE__PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OCCURRENCE__RESULT = 2;

	/**
	 * The number of structural features of the '<em>MSE Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OCCURRENCE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>MSE Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OCCURRENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEImpl <em>MSE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSE()
	 * @generated
	 */
	int MSE = 1;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE__EANNOTATIONS = EcorePackage.ENAMED_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE__NAME = EcorePackage.ENAMED_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>MSE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_FEATURE_COUNT = EcorePackage.ENAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE___GET_EANNOTATION__STRING = EcorePackage.ENAMED_ELEMENT___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Caller</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE___GET_CALLER = EcorePackage.ENAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE___GET_ACTION = EcorePackage.ENAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>MSE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_OPERATION_COUNT = EcorePackage.ENAMED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEModelImpl <em>MSE Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEModelImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSEModel()
	 * @generated
	 */
	int MSE_MODEL = 2;

	/**
	 * The feature id for the '<em><b>Owned MS Es</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_MODEL__OWNED_MS_ES = 0;

	/**
	 * The number of structural features of the '<em>MSE Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_MODEL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>MSE Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSE_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl <em>Generic MSE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getGenericMSE()
	 * @generated
	 */
	int GENERIC_MSE = 3;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE__EANNOTATIONS = MSE__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE__NAME = MSE__NAME;

	/**
	 * The feature id for the '<em><b>Caller Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE__CALLER_REFERENCE = MSE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Action Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE__ACTION_REFERENCE = MSE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Generic MSE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE_FEATURE_COUNT = MSE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE___GET_EANNOTATION__STRING = MSE___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Caller</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE___GET_CALLER = MSE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE___GET_ACTION = MSE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Generic MSE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_MSE_OPERATION_COUNT = MSE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.StepImpl <em>Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.StepImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getStep()
	 * @generated
	 */
	int STEP = 4;

	/**
	 * The feature id for the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__MSEOCCURRENCE = 0;

	/**
	 * The feature id for the '<em><b>Starting State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__STARTING_STATE = 1;

	/**
	 * The feature id for the '<em><b>Ending State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP__ENDING_STATE = 2;

	/**
	 * The number of structural features of the '<em>Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.BigStepImpl <em>Big Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.BigStepImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getBigStep()
	 * @generated
	 */
	int BIG_STEP = 5;

	/**
	 * The feature id for the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP__MSEOCCURRENCE = STEP__MSEOCCURRENCE;

	/**
	 * The feature id for the '<em><b>Starting State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP__STARTING_STATE = STEP__STARTING_STATE;

	/**
	 * The feature id for the '<em><b>Ending State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP__ENDING_STATE = STEP__ENDING_STATE;

	/**
	 * The feature id for the '<em><b>Sub Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP__SUB_STEPS = STEP_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Big Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP_FEATURE_COUNT = STEP_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Big Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIG_STEP_OPERATION_COUNT = STEP_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.SmallStepImpl <em>Small Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.SmallStepImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getSmallStep()
	 * @generated
	 */
	int SMALL_STEP = 6;

	/**
	 * The feature id for the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_STEP__MSEOCCURRENCE = STEP__MSEOCCURRENCE;

	/**
	 * The feature id for the '<em><b>Starting State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_STEP__STARTING_STATE = STEP__STARTING_STATE;

	/**
	 * The feature id for the '<em><b>Ending State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_STEP__ENDING_STATE = STEP__ENDING_STATE;

	/**
	 * The number of structural features of the '<em>Small Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_STEP_FEATURE_COUNT = STEP_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Small Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_STEP_OPERATION_COUNT = STEP_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.SequentialStepImpl <em>Sequential Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.SequentialStepImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getSequentialStep()
	 * @generated
	 */
	int SEQUENTIAL_STEP = 7;

	/**
	 * The feature id for the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP__MSEOCCURRENCE = BIG_STEP__MSEOCCURRENCE;

	/**
	 * The feature id for the '<em><b>Starting State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP__STARTING_STATE = BIG_STEP__STARTING_STATE;

	/**
	 * The feature id for the '<em><b>Ending State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP__ENDING_STATE = BIG_STEP__ENDING_STATE;

	/**
	 * The feature id for the '<em><b>Sub Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP__SUB_STEPS = BIG_STEP__SUB_STEPS;

	/**
	 * The number of structural features of the '<em>Sequential Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP_FEATURE_COUNT = BIG_STEP_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Sequential Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_STEP_OPERATION_COUNT = BIG_STEP_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.ParallelStepImpl <em>Parallel Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.ParallelStepImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getParallelStep()
	 * @generated
	 */
	int PARALLEL_STEP = 8;

	/**
	 * The feature id for the '<em><b>Mseoccurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP__MSEOCCURRENCE = BIG_STEP__MSEOCCURRENCE;

	/**
	 * The feature id for the '<em><b>Starting State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP__STARTING_STATE = BIG_STEP__STARTING_STATE;

	/**
	 * The feature id for the '<em><b>Ending State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP__ENDING_STATE = BIG_STEP__ENDING_STATE;

	/**
	 * The feature id for the '<em><b>Sub Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP__SUB_STEPS = BIG_STEP__SUB_STEPS;

	/**
	 * The number of structural features of the '<em>Parallel Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP_FEATURE_COUNT = BIG_STEP_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Parallel Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_STEP_OPERATION_COUNT = BIG_STEP_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 9;

	/**
	 * The feature id for the '<em><b>Root Step</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__ROOT_STEP = 0;

	/**
	 * The feature id for the '<em><b>Traced Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__TRACED_OBJECTS = 1;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__STATES = 2;

	/**
	 * The feature id for the '<em><b>Launchconfiguration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__LAUNCHCONFIGURATION = 3;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.TracedObjectImpl <em>Traced Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracedObjectImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getTracedObject()
	 * @generated
	 */
	int TRACED_OBJECT = 10;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACED_OBJECT__DIMENSIONS = 0;

	/**
	 * The number of structural features of the '<em>Traced Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACED_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get Dimensions Internal</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACED_OBJECT___GET_DIMENSIONS_INTERNAL = 0;

	/**
	 * The number of operations of the '<em>Traced Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACED_OBJECT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 11;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__VALUES = 0;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.ValueImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 12;

	/**
	 * The feature id for the '<em><b>States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__STATES = 0;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.StateImpl
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getState()
	 * @generated
	 */
	int STATE = 13;

	/**
	 * The feature id for the '<em><b>Started Steps</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__STARTED_STEPS = 0;

	/**
	 * The feature id for the '<em><b>Ended Steps</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ENDED_STEPS = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__VALUES = 2;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>ISerializable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getISerializable()
	 * @generated
	 */
	int ISERIALIZABLE = 14;


	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence <em>MSE Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MSE Occurrence</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEOccurrence
	 * @generated
	 */
	EClass getMSEOccurrence();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getMse <em>Mse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mse</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getMse()
	 * @see #getMSEOccurrence()
	 * @generated
	 */
	EReference getMSEOccurrence_Mse();

	/**
	 * Returns the meta object for the attribute list '{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getParameters()
	 * @see #getMSEOccurrence()
	 * @generated
	 */
	EAttribute getMSEOccurrence_Parameters();

	/**
	 * Returns the meta object for the attribute list '{@link fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Result</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEOccurrence#getResult()
	 * @see #getMSEOccurrence()
	 * @generated
	 */
	EAttribute getMSEOccurrence_Result();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.MSE <em>MSE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MSE</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSE
	 * @generated
	 */
	EClass getMSE();

	/**
	 * Returns the meta object for the '{@link fr.inria.diverse.trace.commons.model.trace.MSE#getCaller() <em>Get Caller</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Caller</em>' operation.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSE#getCaller()
	 * @generated
	 */
	EOperation getMSE__GetCaller();

	/**
	 * Returns the meta object for the '{@link fr.inria.diverse.trace.commons.model.trace.MSE#getAction() <em>Get Action</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Action</em>' operation.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSE#getAction()
	 * @generated
	 */
	EOperation getMSE__GetAction();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.MSEModel <em>MSE Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MSE Model</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEModel
	 * @generated
	 */
	EClass getMSEModel();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.trace.MSEModel#getOwnedMSEs <em>Owned MS Es</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned MS Es</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.MSEModel#getOwnedMSEs()
	 * @see #getMSEModel()
	 * @generated
	 */
	EReference getMSEModel_OwnedMSEs();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE <em>Generic MSE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic MSE</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.GenericMSE
	 * @generated
	 */
	EClass getGenericMSE();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCallerReference <em>Caller Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Caller Reference</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCallerReference()
	 * @see #getGenericMSE()
	 * @generated
	 */
	EReference getGenericMSE_CallerReference();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getActionReference <em>Action Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Action Reference</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.GenericMSE#getActionReference()
	 * @see #getGenericMSE()
	 * @generated
	 */
	EReference getGenericMSE_ActionReference();

	/**
	 * Returns the meta object for the '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCaller() <em>Get Caller</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Caller</em>' operation.
	 * @see fr.inria.diverse.trace.commons.model.trace.GenericMSE#getCaller()
	 * @generated
	 */
	EOperation getGenericMSE__GetCaller();

	/**
	 * Returns the meta object for the '{@link fr.inria.diverse.trace.commons.model.trace.GenericMSE#getAction() <em>Get Action</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Action</em>' operation.
	 * @see fr.inria.diverse.trace.commons.model.trace.GenericMSE#getAction()
	 * @generated
	 */
	EOperation getGenericMSE__GetAction();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.Step <em>Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Step
	 * @generated
	 */
	EClass getStep();

	/**
	 * Returns the meta object for the containment reference '{@link fr.inria.diverse.trace.commons.model.trace.Step#getMseoccurrence <em>Mseoccurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mseoccurrence</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Step#getMseoccurrence()
	 * @see #getStep()
	 * @generated
	 */
	EReference getStep_Mseoccurrence();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.diverse.trace.commons.model.trace.Step#getStartingState <em>Starting State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Starting State</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Step#getStartingState()
	 * @see #getStep()
	 * @generated
	 */
	EReference getStep_StartingState();

	/**
	 * Returns the meta object for the reference '{@link fr.inria.diverse.trace.commons.model.trace.Step#getEndingState <em>Ending State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ending State</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Step#getEndingState()
	 * @see #getStep()
	 * @generated
	 */
	EReference getStep_EndingState();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.BigStep <em>Big Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Big Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.BigStep
	 * @generated
	 */
	EClass getBigStep();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.trace.BigStep#getSubSteps <em>Sub Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Steps</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.BigStep#getSubSteps()
	 * @see #getBigStep()
	 * @generated
	 */
	EReference getBigStep_SubSteps();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.SmallStep <em>Small Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Small Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.SmallStep
	 * @generated
	 */
	EClass getSmallStep();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.SequentialStep <em>Sequential Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequential Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.SequentialStep
	 * @generated
	 */
	EClass getSequentialStep();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.ParallelStep <em>Parallel Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parallel Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.ParallelStep
	 * @generated
	 */
	EClass getParallelStep();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the containment reference '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getRootStep <em>Root Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Step</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace#getRootStep()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_RootStep();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getTracedObjects <em>Traced Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traced Objects</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace#getTracedObjects()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_TracedObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace#getStates()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_States();

	/**
	 * Returns the meta object for the containment reference '{@link fr.inria.diverse.trace.commons.model.trace.Trace#getLaunchconfiguration <em>Launchconfiguration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Launchconfiguration</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Trace#getLaunchconfiguration()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_Launchconfiguration();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.TracedObject <em>Traced Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Traced Object</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracedObject
	 * @generated
	 */
	EClass getTracedObject();

	/**
	 * Returns the meta object for the reference list '{@link fr.inria.diverse.trace.commons.model.trace.TracedObject#getDimensions <em>Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dimensions</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracedObject#getDimensions()
	 * @see #getTracedObject()
	 * @generated
	 */
	EReference getTracedObject_Dimensions();

	/**
	 * Returns the meta object for the '{@link fr.inria.diverse.trace.commons.model.trace.TracedObject#getDimensionsInternal() <em>Get Dimensions Internal</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Dimensions Internal</em>' operation.
	 * @see fr.inria.diverse.trace.commons.model.trace.TracedObject#getDimensionsInternal()
	 * @generated
	 */
	EOperation getTracedObject__GetDimensionsInternal();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.diverse.trace.commons.model.trace.Dimension#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Dimension#getValues()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Values();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the reference list '{@link fr.inria.diverse.trace.commons.model.trace.Value#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>States</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.Value#getStates()
	 * @see #getValue()
	 * @generated
	 */
	EReference getValue_States();

	/**
	 * Returns the meta object for class '{@link fr.inria.diverse.trace.commons.model.trace.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the reference list '{@link fr.inria.diverse.trace.commons.model.trace.State#getStartedSteps <em>Started Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Started Steps</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getStartedSteps()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_StartedSteps();

	/**
	 * Returns the meta object for the reference list '{@link fr.inria.diverse.trace.commons.model.trace.State#getEndedSteps <em>Ended Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ended Steps</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getEndedSteps()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_EndedSteps();

	/**
	 * Returns the meta object for the reference list '{@link fr.inria.diverse.trace.commons.model.trace.State#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see fr.inria.diverse.trace.commons.model.trace.State#getValues()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Values();

	/**
	 * Returns the meta object for data type '<em>ISerializable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISerializable</em>'.
	 * @model instanceClass="byte[]"
	 * @generated
	 */
	EDataType getISerializable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TraceFactory getTraceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl <em>MSE Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEOccurrenceImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSEOccurrence()
		 * @generated
		 */
		EClass MSE_OCCURRENCE = eINSTANCE.getMSEOccurrence();

		/**
		 * The meta object literal for the '<em><b>Mse</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSE_OCCURRENCE__MSE = eINSTANCE.getMSEOccurrence_Mse();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSE_OCCURRENCE__PARAMETERS = eINSTANCE.getMSEOccurrence_Parameters();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSE_OCCURRENCE__RESULT = eINSTANCE.getMSEOccurrence_Result();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEImpl <em>MSE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSE()
		 * @generated
		 */
		EClass MSE = eINSTANCE.getMSE();

		/**
		 * The meta object literal for the '<em><b>Get Caller</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MSE___GET_CALLER = eINSTANCE.getMSE__GetCaller();

		/**
		 * The meta object literal for the '<em><b>Get Action</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MSE___GET_ACTION = eINSTANCE.getMSE__GetAction();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.MSEModelImpl <em>MSE Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.MSEModelImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getMSEModel()
		 * @generated
		 */
		EClass MSE_MODEL = eINSTANCE.getMSEModel();

		/**
		 * The meta object literal for the '<em><b>Owned MS Es</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSE_MODEL__OWNED_MS_ES = eINSTANCE.getMSEModel_OwnedMSEs();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl <em>Generic MSE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.GenericMSEImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getGenericMSE()
		 * @generated
		 */
		EClass GENERIC_MSE = eINSTANCE.getGenericMSE();

		/**
		 * The meta object literal for the '<em><b>Caller Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_MSE__CALLER_REFERENCE = eINSTANCE.getGenericMSE_CallerReference();

		/**
		 * The meta object literal for the '<em><b>Action Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_MSE__ACTION_REFERENCE = eINSTANCE.getGenericMSE_ActionReference();

		/**
		 * The meta object literal for the '<em><b>Get Caller</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GENERIC_MSE___GET_CALLER = eINSTANCE.getGenericMSE__GetCaller();

		/**
		 * The meta object literal for the '<em><b>Get Action</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GENERIC_MSE___GET_ACTION = eINSTANCE.getGenericMSE__GetAction();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.StepImpl <em>Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.StepImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getStep()
		 * @generated
		 */
		EClass STEP = eINSTANCE.getStep();

		/**
		 * The meta object literal for the '<em><b>Mseoccurrence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STEP__MSEOCCURRENCE = eINSTANCE.getStep_Mseoccurrence();

		/**
		 * The meta object literal for the '<em><b>Starting State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STEP__STARTING_STATE = eINSTANCE.getStep_StartingState();

		/**
		 * The meta object literal for the '<em><b>Ending State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STEP__ENDING_STATE = eINSTANCE.getStep_EndingState();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.BigStepImpl <em>Big Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.BigStepImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getBigStep()
		 * @generated
		 */
		EClass BIG_STEP = eINSTANCE.getBigStep();

		/**
		 * The meta object literal for the '<em><b>Sub Steps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BIG_STEP__SUB_STEPS = eINSTANCE.getBigStep_SubSteps();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.SmallStepImpl <em>Small Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.SmallStepImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getSmallStep()
		 * @generated
		 */
		EClass SMALL_STEP = eINSTANCE.getSmallStep();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.SequentialStepImpl <em>Sequential Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.SequentialStepImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getSequentialStep()
		 * @generated
		 */
		EClass SEQUENTIAL_STEP = eINSTANCE.getSequentialStep();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.ParallelStepImpl <em>Parallel Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.ParallelStepImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getParallelStep()
		 * @generated
		 */
		EClass PARALLEL_STEP = eINSTANCE.getParallelStep();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TraceImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Root Step</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__ROOT_STEP = eINSTANCE.getTrace_RootStep();

		/**
		 * The meta object literal for the '<em><b>Traced Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__TRACED_OBJECTS = eINSTANCE.getTrace_TracedObjects();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__STATES = eINSTANCE.getTrace_States();

		/**
		 * The meta object literal for the '<em><b>Launchconfiguration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__LAUNCHCONFIGURATION = eINSTANCE.getTrace_Launchconfiguration();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.TracedObjectImpl <em>Traced Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracedObjectImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getTracedObject()
		 * @generated
		 */
		EClass TRACED_OBJECT = eINSTANCE.getTracedObject();

		/**
		 * The meta object literal for the '<em><b>Dimensions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACED_OBJECT__DIMENSIONS = eINSTANCE.getTracedObject_Dimensions();

		/**
		 * The meta object literal for the '<em><b>Get Dimensions Internal</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRACED_OBJECT___GET_DIMENSIONS_INTERNAL = eINSTANCE.getTracedObject__GetDimensionsInternal();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.DimensionImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__VALUES = eINSTANCE.getDimension_Values();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.ValueImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE__STATES = eINSTANCE.getValue_States();

		/**
		 * The meta object literal for the '{@link fr.inria.diverse.trace.commons.model.trace.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.StateImpl
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Started Steps</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__STARTED_STEPS = eINSTANCE.getState_StartedSteps();

		/**
		 * The meta object literal for the '<em><b>Ended Steps</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ENDED_STEPS = eINSTANCE.getState_EndedSteps();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__VALUES = eINSTANCE.getState_Values();

		/**
		 * The meta object literal for the '<em>ISerializable</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.diverse.trace.commons.model.trace.impl.TracePackageImpl#getISerializable()
		 * @generated
		 */
		EDataType ISERIALIZABLE = eINSTANCE.getISerializable();

	}

} //TracePackage
