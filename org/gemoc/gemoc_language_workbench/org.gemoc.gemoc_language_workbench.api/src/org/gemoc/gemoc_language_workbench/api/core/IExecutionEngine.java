package org.gemoc.gemoc_language_workbench.api.core;

import java.util.List;

import fr.inria.aoste.trace.LogicalStep;


/**
 * The interface of the GEMOC Execution Engine. The Execution Engine is an
 * entity able to execute models conforming to an xDSML as defined in the GEMOC
 * ANR INS project. This API allows the caller to initialize the engine for a
 * given model, and to run the engine in different ways. It also allows the
 * caller to influence the constraints of the MoC at runtime.
 * 
 * @author didier.vojtisek@inria.fr
 * 
 */
public interface IExecutionEngine {

	/**
	 * Starts the {@link IExecutionEngine}.
	 */
	public void start();
	
	/**
	 * Asks the engine to stop
	 */
	public void stop();
	
	public EngineStatus getEngineStatus();
	
	/**
	 * 
	 * @param type
	 * @return true if the engine has the capability, false otherwise.
	 */
	public <T extends IExecutionEngineCapability> boolean hasCapability(Class<T> type);
	/**
	 * 
	 * @param type
	 * @return The capability of the given type if it exists.
	 */
	public <T extends IExecutionEngineCapability> T getCapability(Class<T> type);
	/**
	 * Get the capability of the given type.
	 * If it does not exist, it creates it.
	 * @param type
	 * @return The capability of the given type.
	 */
	public <T extends IExecutionEngineCapability> T capability(Class<T> type);

	public IExecutionContext getExecutionContext();

	public List<LogicalStep> getPossibleLogicalSteps();

	public void addFutureAction(IFutureAction action);
	
}