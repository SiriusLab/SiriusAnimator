/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package fr.inria.diverse.trace.metamodel.generator

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EPackage

class TraceMMStrings {

	public static val String class_Trace = "SpecificTrace"

	public static val String class_State = "State"

	public static val String class_SpecificState = "SpecificState"

	public static val String class_TracedObject = "SpecificTracedObject"

	public static val String class_Dimension = "SpecificDimension"

	public static val String class_Value = "SpecificValue"

	public static val String class_AttributeValue = "SpecificAttributeValue"

	public static val String class_ReferenceValue = "SpecificReferenceValue"

	public static val String class_Step = "SpecificStep"

	public static val String class_RootStep = "SpecificRootStep"

	public static val String package_States = "States"

	public static val String package_Steps = "Steps"

	public static val String ref_Dimensions = "dimensions"

	public static val String ref_OriginalObject = "originalObject"

	public static val String ref_ValueToTrace = "parent"

	public static val String ref_ValueToStates = "statesRef"

	public static val String ref_TraceToStates = "statesTrace"

	public static val String ref_StateToStep_started = "startedStepsRef"

	public static val String ref_StateToStep_ended = "endedStepsRef"

	public static val String ref_StepToState_starting = "startingStateRef"

	public static val String ref_StepToState_ending = "endingStateRef"

	static def String class_createTraceClassName(EClass runtimeClass) { return "Traced" + runtimeClass.name }

	static def String ref_createTraceClassToTracedClass(EClass tracedClass) {
		return tracedClass.EPackage.name.toFirstLower + "_" + tracedClass.name.toFirstLower + "s"
	}

	static def String class_createStateClassName(EClass runtimeClass, EStructuralFeature runtimeProperty) {
		return runtimeClass.name + "_" + runtimeProperty.name + "_Value"
	}

	static def String class_createDimensionClassName(EClass runtimeClass, EStructuralFeature runtimeProperty) {
		return runtimeClass.name + "_" + runtimeProperty.name + "_Dimension"
	}

	static def String ref_createTraceClassToValueClass(EStructuralFeature runtimeProperty) {
		return runtimeProperty.name + "Sequence"
	}

	static def String ref_createGlobalToState(EClass stateClass) { return stateClass.name.toFirstLower + "s" }

	static def String ref_createTraceClassToStepClass(EClass stepClass) { return stepClass.name + "_Sequence" }

	static def String package_createTracedPackage(EPackage runtimePackage) {
		return runtimePackage.name
	}

	static def String ref_OriginalObject_MultipleInheritance(EClass originalClass) {
		return ref_OriginalObject + "_" + originalClass.name
	}

}
