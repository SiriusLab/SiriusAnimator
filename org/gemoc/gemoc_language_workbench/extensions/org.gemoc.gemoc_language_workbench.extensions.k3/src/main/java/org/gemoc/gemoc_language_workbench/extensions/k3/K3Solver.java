package org.gemoc.gemoc_language_workbench.extensions.k3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gemoc.execution.engine.core.IMSEOccurrenceListener;
import org.gemoc.execution.engine.core.MSEManager;
import org.gemoc.execution.engine.trace.gemoc_execution_trace.LogicalStep;
import org.gemoc.execution.engine.trace.gemoc_execution_trace.MSEOccurrence;
import org.gemoc.gemoc_language_workbench.api.core.IExecutionContext;
import org.gemoc.gemoc_language_workbench.api.moc.ISolver;

import fr.inria.aoste.trace.EventOccurrence;

public class K3Solver implements ISolver, IMSEOccurrenceListener
{

	@Override
	public void forbidEventOccurrence(EventOccurrence eventOccurrence) 
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void forceEventOccurrence(EventOccurrence eventOccurrence) 
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public List<LogicalStep> computeAndGetPossibleLogicalSteps() 
	{
		return updatePossibleLogicalSteps();
	}

	@Override
	public List<LogicalStep> updatePossibleLogicalSteps() 
	{ 	ArrayList<LogicalStep> l = new ArrayList<LogicalStep>();
		if (_lastOccurrence != null
			&& _lastOccurrence.getLogicalstep() != null)
		{
			l.add(_lastOccurrence.getLogicalstep());
		}
		return l;
	}

	@Override
	public LogicalStep proposeLogicalStep() 
	{
		if (_lastOccurrence.getLogicalstep() != null)
		{
			return _lastOccurrence.getLogicalstep();
		}
		return null;
	}

	@Override
	public void applyLogicalStep(LogicalStep logicalStep) 
	{
	}
	
	@Override
	public URI prepareSolverInputFileForUserModel(URI userModelURI) 
	{
		return null;
	}

	@Override
	public boolean isSolverInputFileReadyForUserModel(URI userModelURI) 
	{
		return false;
	}

	private Resource _resourceModel;
	
	@Override
	public void setSolverInputFile(ResourceSet rs, URI solverInputURI) 
	{
	}

	@Override
	public byte[] getState() 
	{
		return new byte[] {};
	}

	@Override
	public void setState(byte[] serializableModel) 
	{
	}

	@Override
	public void revertForceClockEffect() 
	{
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setUp(IExecutionContext context) 
	{
		_resourceModel = context.getResourceModel();
		MSEManager.getInstance().addListener(this);
	}

	@Override
	public void dispose() 
	{
		MSEManager.getInstance().removeListener(this);
	}

	private MSEOccurrence _lastOccurrence;
	
	@Override
	public void mseOccurenceRaised(MSEOccurrence occurrence) 
	{
		_lastOccurrence = occurrence;
		String uri = _resourceModel.getURIFragment(occurrence.getMse().getCaller());
//		String uri = engine.getExecutionContext().getResourceModel().getURIFragment(occurrence.getMse().getCaller());
		if (uri != null)
		{
			ArrayList<LogicalStep> logicalSteps = new ArrayList<LogicalStep>();
			logicalSteps.add(occurrence.getLogicalstep());
		}

	}

}
