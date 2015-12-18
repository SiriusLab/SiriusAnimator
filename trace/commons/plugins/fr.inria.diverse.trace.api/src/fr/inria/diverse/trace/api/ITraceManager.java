package fr.inria.diverse.trace.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface ITraceManager {

	void save();

	void addState();

	boolean addStateIfChanged();

	void addStep(String stepRuleFQN, Map<String, Object> params);

	void endStep(String stepNameFQN, Object returnValue);

	void initTrace();

	int getTraceSize();

	void goTo(int index);

	void goTo(EObject stateOrValue);

	EObject getExecutionState(int index);

	String getDescriptionOfExecutionState(int index);

	String currentBigStep();

	int getNumberOfValueTraces();

	List<IValueTrace> getAllValueTraces();

	String getDescriptionOfValue(EObject value);

	Set<EObject> getAllCurrentValues(int stateIndex);

	int getStateOrValueIndex(EObject stateOrValue);

	List<fr.inria.diverse.trace.api.IStep> getStackForwardAfterState(int stateIndex);

	List<fr.inria.diverse.trace.api.IStep> getStackForwardBeforeState(int stateIndex);

	List<fr.inria.diverse.trace.api.IStep> getStackBackward(int stateIndex);
	
	List<fr.inria.diverse.trace.api.IStep.StepEvent> getEventsForState(int stateIndex);

}