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

import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EFactory
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtend.lib.annotations.Accessors

class TraceMMExplorer {

	private val EPackage tracemm

	// Base classes
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass stateClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificStateClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificTraceClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificTracedObjectClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificDimensionClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificStepClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificRootStepClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificValueClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificAttributeValueClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EClass specificReferenceValueClass
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EReference dimensionsReference
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EPackage stepsPackage
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) protected val EPackage statesPackage

	protected val EFactory rootFactory;
	protected val EFactory stepFactory;

	// protected EFactory tracedFactory;
	protected val EFactory stateFactory

	/**
	 * Here we focus on the part of the base trace mm, because TraceMMExplorer is
	 * used in the TraceMMGenerator as well.
	 */
	new(EPackage traceMetamodel) {
		this.tracemm = traceMetamodel

		// Find the Trace class
		specificTraceClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_Trace)
		] as EClass

		// Find the SpecificState class
		specificStateClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_SpecificState)
		] as EClass

		// Find the State class
		stateClass = specificStateClass.EAllSuperTypes.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_State)
		] as EClass
		
		// Find the SpecificTracedObject class
		specificTracedObjectClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_TracedObject)
		] as EClass
		
		dimensionsReference = specificTracedObjectClass.EAllContainments.findFirst [ r |
			r.name.equals(TraceMMStrings.ref_Dimensions)
		]
		
		// Find the SpecificValue class
		specificDimensionClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_Dimension)
		] as EClass
		
		// Find the SpecificValue class
		specificValueClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_Value)
		] as EClass
		
		// Find the AttributeValue class
		specificAttributeValueClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_AttributeValue)
		] as EClass
		
		// Find the ReferenceValue class
		specificReferenceValueClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_ReferenceValue)
		] as EClass

		// Find the SpecificStep class
		specificStepClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_Step)
		] as EClass
		stepsPackage = specificStepClass.EPackage

		// Find the SpecificRootStep class
		specificRootStepClass = tracemm.eAllContents.filter(EClass).findFirst [ c |
			c.name.equals(TraceMMStrings.class_RootStep)
		] as EClass

		// Find the States package
		statesPackage = tracemm.eAllContents.filter(EPackage).findFirst [ p |
			p.name.equals(TraceMMStrings.package_States)
		] as EPackage

		rootFactory = tracemm.EFactoryInstance
		stepFactory = stepsPackage.EFactoryInstance
		stateFactory = statesPackage.EFactoryInstance
	}

	private var initDone = false

	def void init() {
		if (!initDone) {

			stepClassesCache = new HashSet
			stepClassesCache.addAll(stepsPackage.eAllContents.filter(EClass).filter [ c |
				c != specificStepClass
			].toSet)

			refs_valueRefsFromStateClassCache = specificStateClass.getEAllReferences.filter [ r |
				!r.name.equals(TraceMMStrings.ref_ValueToStates)
			].toSet

			initDone = true
		}

	}

	private Set<EClass> stepClassesCache = null

	public def Set<EClass> stepClasses() {
		init()
		return stepClassesCache
	}

	private val Map<EClass, EReference> stepSequenceRefOfCache = new HashMap

	public def EReference stepSequenceRefOf(EClass stepClass) {

		if (!stepSequenceRefOfCache.containsKey(stepClass)) {
			stepSequenceRefOfCache.put(stepClass, specificTraceClass.EReferences.findFirst [ r |
				r.name.equals(TraceMMStrings.ref_createTraceClassToStepClass(stepClass))
			])
		}

		return stepSequenceRefOfCache.get(stepClass)
	}

	def EObject createEventOccurrence(EClass stepClass) {
		return stepFactory.create(stepClass)
	}

	def EObject createTracedObject(EClass tracedClass) {

		// TODO provide somewhere a generic create method? not related to trace mm explorer
		return tracedClass.EPackage.EFactoryInstance.create(tracedClass)
	}

	def EObject createState(EClass stateClass) {
		return stateFactory.create(stateClass)
	}

	// References to state classes from the global state class
	private var Set<EReference> refs_valueRefsFromStateClassCache

	def Set<EReference> refs_valueRefsFromStateClass() {
		init()
		return refs_valueRefsFromStateClassCache
	}

	private val Map<EClass, Set<EReference>> refs_originalObjectCache = new HashMap

	def Set<EReference> refs_originalObject(EClass traceClass) {

		if (!refs_originalObjectCache.containsKey(traceClass)) {
			refs_originalObjectCache.put(traceClass, traceClass.EAllReferences.filter [ r |
				r.name.startsWith(TraceMMStrings.ref_OriginalObject)
			].toSet)
		}

		return refs_originalObjectCache.get(traceClass)
	}

}
