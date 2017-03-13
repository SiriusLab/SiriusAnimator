/**
 */
package base;

import base.States.SpecificDimension;
import base.States.SpecificState;
import base.States.SpecificTracedObject;
import base.States.SpecificValue;

import base.Steps.SpecificStep;

import fr.inria.diverse.trace.commons.model.trace.SequentialStep;
import fr.inria.diverse.trace.commons.model.trace.Trace;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specific Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see base.BasePackage#getSpecificTrace()
 * @model
 * @generated
 */
public interface SpecificTrace extends Trace<SequentialStep<? extends SpecificStep, ? extends SpecificState>, SpecificTracedObject<? extends SpecificDimension<? extends SpecificValue>>, SpecificState> {

} // SpecificTrace
