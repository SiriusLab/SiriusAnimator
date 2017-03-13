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
package fr.inria.diverse.trace.gemoc.traceaddon;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.ModelChange;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NewObjectModelChange;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.NonCollectionFieldModelChange;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.PotentialCollectionFieldModelChange;
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.RemovedObjectModelChange;

import fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSequentialStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericSmallStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericStep;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.GenericValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenerictraceFactory;
import fr.inria.diverse.trace.commons.model.generictrace.IntegerAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.StringAttributeValue;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.trace.BigStep;
import fr.inria.diverse.trace.commons.model.trace.MSEModel;
import fr.inria.diverse.trace.commons.model.trace.Step;
import fr.inria.diverse.trace.commons.model.trace.Trace;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.gemoc.api.ITraceConstructor;

public class GenericTraceConstructor implements ITraceConstructor {

	private Trace<GenericSequentialStep, GenericTracedObject, GenericState> traceRoot;
	private Resource executedModel;
	private MSEModel mseModel;
	private Resource traceResource;
	private final Map<EObject, TracedObject<?>> exeToTraced;
	private final Deque<GenericSequentialStep> context = new LinkedList<>();
	private GenericState lastState;
	
	public GenericTraceConstructor(Resource executedModel, Resource traceResource, Map<EObject, TracedObject<?>> exeToTraced) {
		this.executedModel = executedModel;
		this.traceResource = traceResource;
		this.exeToTraced = exeToTraced;
	}
	
	private Set<Resource> getAllExecutedModelResources() {
		Set<Resource> allResources = new HashSet<>();
		allResources.add(executedModel);
		allResources.addAll(org.gemoc.commons.eclipse.emf.EMFResource.getRelatedResources(executedModel));
		allResources.removeIf(r -> r == null);
		return allResources;
	}
	
	public static final String DYNAMIC_ANNOTATION_URI = "aspect";

	private static boolean isDynamic(EModelElement o) {
		return o.getEAnnotations().stream().anyMatch(a -> a.getSource().equals(DYNAMIC_ANNOTATION_URI));
	}

	public static boolean isDynamic(EClass c) {
		return isDynamic((EModelElement)c);
	}

	public static boolean isDynamic(EStructuralFeature p) {
		return isDynamic((EModelElement) p) || isDynamic(p.getEContainingClass());
	}
	
	private boolean addNewObjectToStateIfDynamic(EObject object, GenericState state) {
		final EClass c = object.eClass();
		final List<EStructuralFeature> mutableProperties = c.getEAllStructuralFeatures().stream()
				.filter(p -> isDynamic(p))
				.collect(Collectors.toList());
		if (isDynamic(object.eClass()) || !mutableProperties.isEmpty()) {
			return addNewObjectToState(object, mutableProperties, lastState);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private GenericValue getGenericValue(EObject object, EStructuralFeature mutableProperty, GenericState state) {
		GenericValue result = null;
		if (mutableProperty instanceof EAttribute) {
			final EClassifier eType = mutableProperty.getEType();
			if (eType == EcorePackage.Literals.EINT) {
				final IntegerAttributeValue value = GenerictraceFactory.eINSTANCE.createIntegerAttributeValue();
				value.setAttributeValue((Integer) object.eGet(mutableProperty));
				result = value;
			} else if (eType == EcorePackage.Literals.EBOOLEAN) {
				final BooleanAttributeValue value = GenerictraceFactory.eINSTANCE.createBooleanAttributeValue();
				value.setAttributeValue((Boolean) object.eGet(mutableProperty));
				result = value;
			} else if (eType == EcorePackage.Literals.ESTRING) {
				final StringAttributeValue value = GenerictraceFactory.eINSTANCE.createStringAttributeValue();
				value.setAttributeValue((String) object.eGet(mutableProperty));
				result = value;
			}
		} else if (mutableProperty instanceof EReference) {
			if (mutableProperty.isMany()) {
				final List<EObject> modelElements = (List<EObject>) object.eGet(mutableProperty);
				final ManyReferenceValue value = GenerictraceFactory.eINSTANCE.createManyReferenceValue();
				for (EObject o : modelElements) {
					if (isDynamic(o.eClass())) {
						value.getReferenceValues().add(exeToTraced.get(o));
					} else {
						value.getReferenceValues().add(o);
					}
				}
				result = value;
			} else {
				final EObject o = (EObject) object.eGet(mutableProperty);
				final SingleReferenceValue value = GenerictraceFactory.eINSTANCE.createSingleReferenceValue();
				if (isDynamic(o.eClass())) {
					value.setReferenceValue(exeToTraced.get(o));
				} else {
					value.setReferenceValue(o);
				}
				result = value;
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private boolean addNewObjectToState(EObject object, List<EStructuralFeature> mutableProperties, GenericState state) {
		boolean added = false;
		if (!exeToTraced.containsKey(object)) {
			final GenericTracedObject tracedObject = GenerictraceFactory.eINSTANCE.createGenericTracedObject();
			if (!isDynamic(object.eClass())) {
				tracedObject.setOriginalObject(object);
			}
			exeToTraced.put(object, tracedObject);
			for (EStructuralFeature mutableProperty : mutableProperties) {
				final GenericDimension dimension = GenerictraceFactory.eINSTANCE.createGenericDimension();
				GenericValue firstValue = null;
				dimension.setDynamicProperty(mutableProperty);
				tracedObject.getAllDimensions().add(dimension);
				if (mutableProperty instanceof EAttribute) {
					final EClassifier eType = mutableProperty.getEType();
					if (eType == EcorePackage.Literals.EINT) {
						final IntegerAttributeValue value = GenerictraceFactory.eINSTANCE.createIntegerAttributeValue();
						value.setAttributeValue((Integer) object.eGet(mutableProperty));
						firstValue = value;
					} else if (eType == EcorePackage.Literals.EBOOLEAN) {
						final BooleanAttributeValue value = GenerictraceFactory.eINSTANCE.createBooleanAttributeValue();
						value.setAttributeValue((Boolean) object.eGet(mutableProperty));
						firstValue = value;
					} else if (eType == EcorePackage.Literals.ESTRING) {
						final StringAttributeValue value = GenerictraceFactory.eINSTANCE.createStringAttributeValue();
						value.setAttributeValue((String) object.eGet(mutableProperty));
						firstValue = value;
					}
				} else if (mutableProperty instanceof EReference) {
					if (mutableProperty.isMany()) {
						final List<EObject> modelElements = (List<EObject>) object.eGet(mutableProperty);
						final ManyReferenceValue value = GenerictraceFactory.eINSTANCE.createManyReferenceValue();
						for (EObject o : modelElements) {
							addNewObjectToStateIfDynamic(o, state);
							value.getReferenceValues().add(exeToTraced.get(o));
						}
						firstValue = value;
					} else {
						final EObject o = (EObject) object.eGet(mutableProperty);
						if (o != null) {
							addNewObjectToStateIfDynamic(o, state);
							final SingleReferenceValue value = GenerictraceFactory.eINSTANCE.createSingleReferenceValue();
							value.setReferenceValue(exeToTraced.get(o));
							firstValue = value;
						}
					}
				}
				if (firstValue != null) {
					dimension.getValues().add(firstValue);
					state.getValues().add(firstValue);
				}
			}
			traceRoot.getTracedObjects().add(tracedObject);
			added = true;
		}
		return added;
	}
	
	private void addInitialState() {
		if (lastState == null) {
			// Creation of the initial state
			Set<Resource> allResources = getAllExecutedModelResources();
			
			lastState = GenerictraceFactory.eINSTANCE.createGenericState();
			for (Resource r : allResources) {
				for (TreeIterator<EObject> i = r.getAllContents(); i.hasNext();) {
					final EObject o = i.next();
					addNewObjectToStateIfDynamic(o, lastState);
				}
			}
			this.traceRoot.getStates().add(lastState);
		}
	}
	
	private boolean copiedState = false;
	
	private GenericState copyState(GenericState oldState) {
		GenericState newState = GenerictraceFactory.eINSTANCE.createGenericState();
		newState.getValues().addAll(oldState.getValues());
		copiedState = true;
		return newState;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addState(List<ModelChange> modelChanges) {
		if (lastState == null) {
			addInitialState();
		}
		if (!modelChanges.isEmpty()) {
			boolean stateChanged = false;
			// We start by a (shallow) copy of the last state
			// But we will have to rollback a little by replacing values that changed
			GenericState newState = copyState(lastState);
			for (ModelChange modelChange : modelChanges) {
				EObject o = modelChange.getChangedObject();
				// We only look at constructable objects that have mutable fields
				// Here we have nothing to rollback, just a new object to add
				if (modelChange instanceof NewObjectModelChange) {
					stateChanged = true;
					addNewObjectToStateIfDynamic(o, newState);
				}
				// We only look at constructable objects that have mutable fields
				// Here we must rollback to remove the values of the removed object from the copied state
				else if (modelChange instanceof RemovedObjectModelChange) {
					stateChanged = true;
					final List<GenericValue> values = newState.getValues();
					((GenericTracedObject) exeToTraced.get(o)).getAllDimensions().forEach(d -> values.remove(d.getValues().get(d.getValues().size() - 1)));
				}
				// Here we must look at non-collection mutable fields
				// We must rollback the last values from the copied state, and add new values as well
				// ie. mix of remove and new
				else if (modelChange instanceof NonCollectionFieldModelChange) {
					stateChanged = true;
					EStructuralFeature p = ((NonCollectionFieldModelChange) modelChange).getChangedField();
					// Rollback: we remove the last value of this field from the new state
					final GenericTracedObject tracedObject = (GenericTracedObject) exeToTraced.get(o);
					final GenericDimension dimension = tracedObject.getAllDimensions().stream()
							.filter(d -> d.getDynamicProperty() == p)
							.findFirst().orElse(null);
					if (dimension != null) {
						final List<GenericValue> values = dimension.getValues();
						final Object pValue = o.eGet(p);
						if (pValue instanceof EObject) {
							addNewObjectToStateIfDynamic((EObject) pValue, newState);
						}
						final GenericValue lastValue = values.isEmpty() ? null : values.get(values.size() - 1);
						if (lastValue != null) {
							newState.getValues().remove(lastValue);
						}
						final GenericValue newValue = getGenericValue(o, p, newState);
						values.add(newValue);
						newState.getValues().add(newValue);
					}
				}
				// Here we look at collection mutable fields
				// We must first manually find out if the collection changed...
				// If it changed we must rollback the last values from the copied state, and add new values as well
				else if (modelChange instanceof PotentialCollectionFieldModelChange) {
					final EStructuralFeature p = ((PotentialCollectionFieldModelChange) modelChange).getChangedField();
					final GenericTracedObject tracedObject = (GenericTracedObject) exeToTraced.get(o);
					// We compare the last collection in the value sequence, and the current one in the potentially changed object
					final GenericDimension dimension = tracedObject.getAllDimensions().stream()
							.filter(d -> d.getDynamicProperty() == p)
							.findFirst().orElse(null);
					if (dimension != null) {
						final List<GenericValue> dimensionValues = dimension.getValues();
						final ManyReferenceValue lastValue = (ManyReferenceValue) dimensionValues.get(dimensionValues.size() - 1);
						final List<EObject> values = (List<EObject>) o.eGet(p);
						for (EObject eObj : values) {
							addNewObjectToStateIfDynamic(eObj, newState);
						}
						boolean change = false;
						if (lastValue != null) {
							if (lastValue.getReferenceValues().size() == values.size()) {
								java.util.Iterator<EObject> it = values.iterator();
								for (EObject aPreviousValue : lastValue.getReferenceValues()) {
									EObject aCurrentValue = it.next();
									if (aPreviousValue != exeToTraced.get(aCurrentValue)) {
										change = true;
										break;
									}
								}
							} else {
								change = true;
							}
						} else {
							change = true;
						}
						if (change) {
							stateChanged = true;
							// Rollback: we remove the last value of this field from the new state
							newState.getValues().remove(lastValue);
							// And we create a proper new value
							GenericValue newValue = getGenericValue(o, p, newState);
							dimension.getValues().add(newValue);
							newState.getValues().add(newValue);
						}
					}
				}
			}
			if (stateChanged) {
				final GenericStep currentStep = context.peekFirst();
				if (currentStep != null && currentStep instanceof BigStep) {
					final GenericState startingState = lastState;
					final GenericState endingState = newState;
					addImplicitStep((BigStep<GenericStep, GenericState>) currentStep, startingState, endingState);
				}
				lastState = newState;
				traceRoot.getStates().add(lastState);
			} else if (copiedState) {
				newState.getValues().clear();
			}
			copiedState = false;
		}
	}
	
	private void addImplicitStep(BigStep<GenericStep, GenericState> currentStep, GenericState startingState, GenericState endingState) {
		GenericSmallStep implicitStep = GenerictraceFactory.eINSTANCE.createGenericSmallStep();
		implicitStep.setStartingState(startingState);
		implicitStep.setEndingState(endingState);
		currentStep.getSubSteps().add(implicitStep);
	}
	
	@Override
	public void addStep(Step<?> step) {
		GenericSequentialStep step_cast = null;
		if (step != null && step instanceof GenericSequentialStep) {
			step_cast = (GenericSequentialStep) step;
			if (mseModel == null) {
				mseModel = fr.inria.diverse.trace.commons.model.trace.TraceFactory.eINSTANCE.createMSEModel();
				traceResource.getContents().add(mseModel);
			}
			mseModel.getOwnedMSEs().add(step_cast.getMseoccurrence().getMse());
			GenericState state = traceRoot.getStates().get(traceRoot.getStates().size() - 1);
			step_cast.setStartingState(state);
			if (!context.isEmpty() && context.getFirst() != null) {
				context.getFirst().getSubSteps().add(step_cast);
			} else {
				traceRoot.getRootStep().getSubSteps().add(step_cast);
			}
		}
		context.push(step_cast);
	}

	@Override
	public void endStep(Step<?> step) {
		GenericStep popped = context.pop();
		if (popped != null)
			popped.setEndingState(lastState);
	}

	@Override
	public EObject initTrace(LaunchConfiguration launchConfiguration) {
		// Create root
		traceRoot = GenerictraceFactory.eINSTANCE.createGenericTrace();
		traceRoot.setLaunchconfiguration(launchConfiguration);

		// Create root sequential step
		GenericSequentialStep rootStep = GenerictraceFactory.eINSTANCE.createGenericSequentialStep();
		traceRoot.setRootStep(rootStep);

		// Put in the resource
		traceResource.getContents().add(traceRoot);

		return traceRoot;
	}

	@Override
	public void save() {
		try {
			traceResource.save(null);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(URI uri) {
		try {
			traceResource.setURI(uri);
			traceResource.save(null);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isPartialTraceConstructor() {
		return false;
	}

}
