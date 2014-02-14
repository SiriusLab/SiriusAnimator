package org.gemoc.gemoc_language_workbench.api.moc;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.Resource;

import fr.inria.aoste.trace.EventOccurrence;
import fr.inria.aoste.trace.LogicalStep;
import glml.MocEvent;

/**
 * A Solver is the visible interface of any constraint solver system that runs
 * on its corresponding input based on a Model of Execution, returns Steps upon
 * requests and provides an API to influence the constraint-solving.
 * 
 * TODO : EventOccurrences should refer (in the context ?) (by name ?) to the
 * clock which they represent. Same for mapping the feedback to the right
 * clocks.
 * 
 * @author flatombe
 */
public interface Solver {
	/**
	 * Forces the underlying MoC structure to forbid the future occurrences of a
	 * Model-Specific Event
	 * 
	 * TODO : change the method signature when the DSE mapping is refined.
	 * 
	 * @param event
	 *            Model-Specific Event to forbid.
	 */
	public void forbidEventOccurrence(EventOccurrence eventOccurrence);

	/**
	 * Forces the underlying MoC structure to trigger an occurrence of a
	 * Model-Specific Event
	 * 
	 * TODO : change the method signature when the DSE mapping is refined.
	 * 
	 * @param event
	 *            Model-Specific Event to force.
	 */
	public void forceEventOccurrence(EventOccurrence eventOccurrence);

	/**
	 * Returns the next step on the MoC's agenda.
	 * 
	 * @return a LogicalStep conforming to the (fr.inria.aoste.trace) scheduling
	 *         trace metamodel.
	 */
	public LogicalStep getNextStep();

	/**
	 * Returns the facility that allows the Execution Engine to create an input
	 * acceptable for the solver based on the Model-Specific Events file.
	 * 
	 * @return the SolverInputBuilder for this solver.
	 */
	public SolverInputBuilder getSolverInputBuilder();

	/**
	 * Sets the input (constraints instanciated for the model) for this solver.
	 * We use a file URI to allow different formats.
	 * 
	 * @param solverInputURI
	 */
	public void setSolverInputFile(URI solverInputURI);

	/**
	 * Returns the instance of MocEvent for the EObject target.
	 * 
	 * @param mocEvent
	 * @param target
	 * @return
	 */
	public EventOccurrence getCorrespondingEventOccurrence(MocEvent mocEvent,
			EObject target);

	public Map<String, MocEvent> createMocEventsRegistry(Resource mocEventsResource);
}
