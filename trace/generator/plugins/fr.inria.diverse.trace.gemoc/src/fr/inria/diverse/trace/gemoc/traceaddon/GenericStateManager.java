package fr.inria.diverse.trace.gemoc.traceaddon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.gemoc.executionframework.engine.core.CommandExecution;

import fr.inria.diverse.trace.commons.model.generictrace.BooleanAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.GenericDimension;
import fr.inria.diverse.trace.commons.model.generictrace.GenericState;
import fr.inria.diverse.trace.commons.model.generictrace.GenericTracedObject;
import fr.inria.diverse.trace.commons.model.generictrace.IntegerAttributeValue;
import fr.inria.diverse.trace.commons.model.generictrace.ManyReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.SingleReferenceValue;
import fr.inria.diverse.trace.commons.model.generictrace.StringAttributeValue;
import fr.inria.diverse.trace.commons.model.trace.State;
import fr.inria.diverse.trace.commons.model.trace.TracedObject;
import fr.inria.diverse.trace.gemoc.api.IStateManager;

public class GenericStateManager implements IStateManager<State<?, ?>> {
	
	private final Resource modelResource;
	
	private final Map<TracedObject<?>, EObject> tracedToExe;
	
	private final Consumer<Long> logger;
	
	public GenericStateManager(Resource modelResource, Map<TracedObject<?>, EObject> tracedToExe, Consumer<Long> logger) {
		this.modelResource = modelResource;
		this.tracedToExe = tracedToExe;
		this.logger = logger;
	}
	
	@Override
	public void restoreState(State<?, ?> state) {
		
		if (modelResource != null && state instanceof GenericState) {
			try {
				final TransactionalEditingDomain ed = TransactionUtil.getEditingDomain(modelResource);
				if (ed != null) {
					final RecordingCommand command = new RecordingCommand(ed, "") {
						protected void doExecute() {
							if (logger != null) {
								long t = System.nanoTime();
								restoreStateExecute((GenericState) state);
								logger.accept((System.nanoTime() - t));
							} else {
								restoreStateExecute((GenericState) state);
							}
						}
					};
					CommandExecution.execute(ed, command);
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	public void restoreStateExecute(GenericState state) {
		state.getValues().forEach(v -> {
			GenericDimension dimension = (GenericDimension) v.eContainer();
			GenericTracedObject tracedObject = (GenericTracedObject) dimension.eContainer();
			EObject originalObject = tracedObject.getOriginalObject();
			if (originalObject == null) {
				originalObject = tracedToExe.get(tracedObject);
			}
			if (v instanceof GenericAttributeValue) {
				if (v instanceof IntegerAttributeValue) {
					originalObject.eSet(dimension.getDynamicProperty(), ((IntegerAttributeValue) v).getAttributeValue());
				} else if (v instanceof BooleanAttributeValue) {
					originalObject.eSet(dimension.getDynamicProperty(), ((BooleanAttributeValue) v).isAttributeValue());
				} else {
					originalObject.eSet(dimension.getDynamicProperty(), ((StringAttributeValue) v).getAttributeValue());
				}
			} else {
				if (v instanceof SingleReferenceValue) {
					final EObject refVal = ((SingleReferenceValue) v).getReferenceValue();
					if (refVal instanceof GenericTracedObject) {
						final EObject exe = tracedToExe.get(refVal);
						originalObject.eSet(dimension.getDynamicProperty(), exe);
					} else {
						originalObject.eSet(dimension.getDynamicProperty(), refVal);
					}
				} else {
					final List<EObject> values = new BasicEList<EObject>();
					values.addAll(((ManyReferenceValue) v).getReferenceValues().stream().map(refVal -> {
						if (refVal instanceof GenericTracedObject) {
							return tracedToExe.get(refVal);
						} else {
							return refVal;
						}
					}).collect(Collectors.toList()));
					originalObject.eSet(dimension.getDynamicProperty(), values);
				}
			}
		});
	}
}
