package fr.inria.diverse.trace.gemoc.traceaddon

import fr.inria.diverse.trace.api.IStep
import fr.inria.diverse.trace.api.ITraceManager
import fr.inria.diverse.trace.api.IValueTrace
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer.StateWrapper
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import fr.inria.diverse.trace.gemoc.api.ITraceListener

class SequentialTimelineExplorer implements ITraceExplorer {
	
	private val nameprovider = new DefaultDeclarativeQualifiedNameProvider();
	private var ITraceManager traceManager;
	private var boolean inThePast;
	private var int lastJumpIndex;
	
	private val List<ITraceListener> listeners = new ArrayList
	
	override addListener(ITraceListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener)
		}
	}
	
	override removeListener(ITraceListener listener) {
		listeners.remove(listener)
	}
	
	override notifyListeners() {
		listeners.forEach[l|l.update]
	}
	
	override getAt(int traceIndex, int indexInTrace) {
		if (traceIndex == 0) {
			if (traceManager.traceSize > indexInTrace) {
				return traceManager.getExecutionState(indexInTrace)
			}
		} else {
			if (traceManager.allValueTraces.size > indexInTrace) {
				return traceManager.allValueTraces.get(traceIndex - 1).getValue(indexInTrace)
			}
		}
		return null;
	}
	
	override getCurrentStateIndex() {
		if (lastJumpIndex != -1)
			return lastJumpIndex
		else
			return getLastIndex
	}
	
	override getNumberOfTraces() {
		if (traceManager == null)
			return 1;
		return traceManager.numberOfValueTraces + 1
	}
	
	override getStatesOrValues(int line, int start, int end) {
		if (traceManager == null)
			return Collections.EMPTY_LIST
		
		val List<StateWrapper> result = new ArrayList
		val startStateIndex = Math.max(0, start)
		val endStateIndex = Math.min(traceManager.traceSize, end)
		
		if (line == 0) {
			for (var i=startStateIndex;i<endStateIndex;i++) {
				result.add(new StateWrapper(traceManager.getExecutionState(i), i, i, i))
			}
		} else if (line-1<traceManager.getNumberOfValueTraces()) {
			// Getting the trace we want to gather values from
			val valueTrace = traceManager.getAllValueTraces().get(line - 1)
			// Initializing the index of the current value
			var valueStartIndex = -1
			for (var i=startStateIndex;i<endStateIndex;i++) {
				// We get the starting index of the current value in the value trace.
				val startIndex = valueTrace.getActiveValueStartingState(i)
				if (startIndex != valueStartIndex) {
					// If it points to a new value
					if (valueStartIndex != -1) {
						// If we have a current value
						result.add(new StateWrapper(valueTrace.getActiveValue(valueStartIndex),
								valueStartIndex, valueTrace.getActiveValueIndex(valueStartIndex), i - 1))
					}
					valueStartIndex = startIndex
				}
			}
			// If the last value does not end before the endStateIndex parameter,
			// we iterate until we find the actual end of the value.
			if (valueStartIndex != -1) {
				var i = endStateIndex
				var endIndex = traceManager.getTraceSize() - 1
				var found = false
				while (i < traceManager.getTraceSize() && !found) {
					val startIndex = valueTrace.getActiveValueStartingState(i)
					if (startIndex != valueStartIndex) {
						endIndex = i - 1
						found = true
					}
					i++
				}
				result.add(new StateWrapper(valueTrace.getActiveValue(valueStartIndex),
						valueStartIndex, valueTrace.getActiveValueIndex(valueStartIndex), endIndex))
			}
		}
		return result
	}
	
	override getStepsForStates(int startingState, int endingState) {
		if (traceManager == null)
			return Collections.EMPTY_LIST
		return traceManager.getStepsForStates(startingState,endingState)
	}
	
	override getTextAt(int traceIndex) {
		if (traceIndex == 0) {
			return "All execution states (" + traceManager.getTraceSize() + ")"

		} else {
			
			val trace = traceManager.allValueTraces.get(traceIndex - 1)
			val value = trace.getValue(0)
			if (value == null) {
				return ""
			}
			val container = value.eContainer()
			val List<String> attributes = container.eClass().getEAllReferences()
					.filter[r|r.getName().endsWith("Sequence")]
					.map[r|r.getName().substring(0,r.getName().length()-8)]
					.toList
			var attributeName = ""
			if (!attributes.isEmpty()) {
				attributeName = attributes.filter[s|value.class.name.contains("_"+s+"_")].get(0)
			}
			val originalObject = container.eClass.EAllReferences.findFirst[r|r.name.equals("originalObject")]
			if (originalObject != null) {
				val o = container.eGet(originalObject)
				if (o instanceof EObject) {
					val eObject = o as EObject
					val qname = nameprovider.getFullyQualifiedName(eObject)
					if(qname == null) {
						return attributeName + " (" + eObject.toString() + ")"
					} else {
						return attributeName + " (" + qname.toString() + " :" + eObject.eClass.name + ")"
					}
				}
			}
			
			return attributeName
		}
	}
	
	override getTextAt(int traceIndex, int indexInTrace) {
		if (traceIndex == 0) {
			return traceManager.getDescriptionOfExecutionState(indexInTrace)
		} else {
			var result = ""
			try {
				result += traceManager.getContainedValue(traceManager.allValueTraces.get(traceIndex - 1).getValue(indexInTrace))
			} catch (IllegalStateException e) {
				e.printStackTrace()
				result = traceManager.getDescriptionOfValue(traceManager.allValueTraces.get(traceIndex - 1).getValue(indexInTrace))
			}
			return result
		}
	}
	
	override getTraceLength(int traceIndex) {
		if (traceManager == null) {
			return 0
		}
		if (traceIndex == 0)
			return traceManager.traceSize
		else
			return traceManager.allValueTraces.get(traceIndex - 1).size
	}
	
	override isInReplayMode() {
		return inThePast
	}
	
	override jump(EObject o) {
		if (traceManager == null)
			return;
		jump(traceManager.getStateOrValueIndex(o))
	}
	
	override jump(int i) {
		if (traceManager == null)
			return;
		if (i < traceManager.traceSize) {
			val List<IStep> rootSteps = traceManager.getStepsForStates(i,i)
			val List<IStep> searchPath = new ArrayList
			var IStep firstStateStep = null
 			if (!rootSteps.empty) {
				var IStep currentStep = rootSteps.get(0)
				var List<IStep> siblingSteps = rootSteps
				while (firstStateStep == null) {
					if (currentStep.startingIndex < i && (currentStep.endingIndex > i || currentStep.endingIndex == -1)) {
						if (currentStep.subSteps.empty) {
							throw new IllegalStateException("Unreachable state")
						} else {
							searchPath.add(0,currentStep)
							siblingSteps = currentStep.subSteps
							currentStep = siblingSteps.get(0)
						}
					} else if (currentStep.endingIndex == i && currentStep.startingIndex != i) {
						if (currentStep.subSteps.empty) {
							// We need to explore the next sibling step
							var tmp = currentStep
							currentStep = null
							while (currentStep == null) {
								val idx = siblingSteps.indexOf(tmp) + 1
								if (idx < siblingSteps.size) {
									currentStep = siblingSteps.get(idx)
								} else {
									if (searchPath.empty) {
										throw new IllegalStateException("Unreachable state")
									} else {
										tmp = searchPath.remove(0)
										if (searchPath.empty) {
											siblingSteps = rootSteps
										} else {
											siblingSteps = searchPath.get(0).subSteps
										}
									}
								}
							}
						} else {
							// We need to explore the substeps in case one of them starts on i
							searchPath.add(0,currentStep)
							siblingSteps = currentStep.subSteps
							currentStep = siblingSteps.get(0)
						}
					} else if (currentStep.startingIndex == i) {
						firstStateStep = currentStep
					}
				}
			}			
			jumpBeforeStep(firstStateStep)
		}
	}
	
	override setTraceManager(ITraceManager traceManager) {
		this.traceManager = traceManager
	}
	
	private var IStep stepIntoResult
	private var IStep stepOverResult
	private var IStep stepReturnResult
	
	private var IStep backIntoResult
	private var IStep backOverResult
	private var IStep backOutResult
	
	private var List<IStep> callStack
	
	def private void doStuff(List<Object> path, boolean updateStack) {
		val List<IStep> rootSteps = traceManager.getStepsForStates(0,traceManager.traceSize)
		val List<IStep> stepPath = new ArrayList;
		val List<IStep> currentSteps = new ArrayList;
		currentSteps.addAll(rootSteps);
		path.forEach[o|
			val IStep step = currentSteps.findFirst[s|s.getParameters().get("this") == o]
			currentSteps.clear();
			if (step != null) {
				stepPath.add(step);
				currentSteps.addAll(step.subSteps);
			}
		]
		
		val stepPathUnmodifiable = stepPath.unmodifiableView
		
		stepIntoResult = computeStepInto(stepPathUnmodifiable,rootSteps)
		stepOverResult = computeStepOver(stepPathUnmodifiable,rootSteps)
		stepReturnResult = computeStepReturn(stepPathUnmodifiable,rootSteps)
		
		backIntoResult = computeBackInto(stepPathUnmodifiable,rootSteps)
		backOverResult = computeBackOver(stepPathUnmodifiable,rootSteps)
		backOutResult = computeBackOut(stepPathUnmodifiable,rootSteps)
		
		callStack = stepPathUnmodifiable
	}
	
	override public loadLastState() {
		var steps = getStepsForStates(lastIndex,lastIndex)
		var IStep lastStep = null
		while (!steps.empty) {
			lastStep = steps.last
			steps = lastStep.subSteps
		}
		if (lastStep.endingIndex == -1)
			jumpBeforeStep(lastStep)
	}
	
	def computeBackInto(List<IStep> stepPath, List<IStep> rootSteps) {
		var IStep result = null
		if (stepPath.size > 1) {
			val reversedPath = stepPath.reverseView
			val currentStep = reversedPath.get(0)
			val parentStep = reversedPath.get(1)
			val parentSubSteps = parentStep.subSteps
			val idx = parentSubSteps.indexOf(currentStep)
			if (idx == 0) {
				// If the current step is the first in its parents substeps, return parent step
				result = parentStep
			} else {
				// Otherwise, return the deepest substep in the previous sibling step
				val previousStep = parentSubSteps.get(idx-1)
				var tmpStep = previousStep
				var tmpSubSteps = tmpStep.subSteps
				while (!tmpSubSteps.empty) {
					tmpStep = tmpSubSteps.last
					tmpSubSteps = tmpStep.subSteps
				}
				result = tmpStep
			}
		} else if (stepPath.size == 1) {
			val currentStep = stepPath.get(0)
			val idx = rootSteps.indexOf(currentStep)
			if (idx > 0) {
				val previousStep = rootSteps.get(idx-1)
				var tmpStep = previousStep
				var tmpSubSteps = tmpStep.subSteps
				while (!tmpSubSteps.empty) {
					tmpStep = tmpSubSteps.last
					tmpSubSteps = tmpStep.subSteps
				}
				result = tmpStep
			}
		}
		return result
	}
	
	def computeBackOver(List<IStep> stepPath, List<IStep> rootSteps) {
		if (!stepPath.empty) {
			val reversedPath = stepPath.reverseView
			return findPreviousStep(reversedPath,rootSteps,reversedPath.get(0),1)
		}
		return null
	}
	
	def computeBackOut(List<IStep> stepPath, List<IStep> rootSteps) {
		if (stepPath.size > 1) {
			val reversedPath = stepPath.reverseView
			return findPreviousStep(reversedPath,rootSteps,reversedPath.get(1),2)
		}
		return null
	}
	
	def findPreviousStep(List<IStep> stepPath, List<IStep> rootSteps, IStep previousStep, int start) {
		var IStep result = null
		var i = start
		var previous = previousStep
		while (result == null && i < stepPath.size) {
			val currentStep = stepPath.get(i)
			val currentSubSteps = currentStep.subSteps
			var idx = currentSubSteps.indexOf(previous) - 1
			if (idx > 0) {
				result = currentSubSteps.get(idx)
			} else {
				previous = currentStep
			}
			i++
		}
		if (result == null) {
			val idx = rootSteps.indexOf(previous) - 1
			if (idx > 0) {
				result = rootSteps.get(idx)
			}
		}
		return result
	}
	
	def findNextStep(List<IStep> stepPath, List<IStep> rootSteps, IStep previousStep, int start) {
		var IStep result = null
		var i = start
		var previous = previousStep
		while (result == null && i < stepPath.size) {
			val currentStep = stepPath.get(i)
			val currentSubSteps = currentStep.subSteps
			if (currentSubSteps.empty) {
				// No substep to step into, we thus have to explore the substeps of the parent step
				previous = currentStep
			} else {
				if (previous == null) {
					// First time we step into 
					result = currentSubSteps.get(0)
				} else {
					val idx = currentSubSteps.indexOf(previous) + 1
					if (idx < currentSubSteps.size) {
						// We step into the next step
						result = currentSubSteps.get(idx)
					} else {
						previous = currentStep
					}
				}
			}
			i++
		}
		if (result == null) {
			val idx = rootSteps.indexOf(previous) + 1
			if (idx < rootSteps.size) {
				result = rootSteps.get(idx)
			}
		}
		return result
	}
	
	def computeStepInto(List<IStep> stepPath, List<IStep> rootSteps) {
		return findNextStep(stepPath.reverseView,rootSteps,null,0)
	}
	
	def computeStepOver(List<IStep> stepPath, List<IStep> rootSteps) {
		if (!stepPath.empty) {
			val reversedPath = stepPath.reverseView
			return findNextStep(reversedPath,rootSteps,reversedPath.get(0),1)
		}
		return null
	}
	
	def computeStepReturn(List<IStep> stepPath, List<IStep> rootSteps) {
		if (stepPath.size > 1) {
			val reversedPath = stepPath.reverseView
			return findNextStep(reversedPath,rootSteps,reversedPath.get(1),2)
		}
		return null
	}
	
	override public boolean canStepBackInto() {
		return backIntoResult != null
	}
	
	override public boolean canStepBackOver() {
		return backOverResult != null
	}
	
	override public boolean canStepBackOut() {
		return backOutResult != null
	}
	
	override public boolean stepBackInto() {
		if (backIntoResult != null) {
			jumpBeforeStep(backIntoResult)
			inThePast = true
			return true
		}
		return false
	}
	
	override public boolean stepBackOver() {
		if (backOverResult != null) {
			jumpBeforeStep(backOverResult)
			inThePast = true
			return true
		}
		return false
	}
	
	override public boolean stepBackOut() {
		if (backOutResult != null) {
			jumpBeforeStep(backOutResult)
			inThePast = true
			return true
		}
		return false
	}
	
	override public boolean stepInto() {
		if (stepIntoResult != null) {
			jumpBeforeStep(stepIntoResult)
			inThePast = stepIntoResult == null
			return true
		}
		return false
	}
	
	override public boolean stepOver() {
		if (stepOverResult != null) {
			jumpBeforeStep(stepOverResult)
			inThePast = stepIntoResult == null
			return true
		}
		return false
	}
	
	override public boolean stepReturn() {
		if (stepReturnResult != null) {
			jumpBeforeStep(stepReturnResult)
			inThePast = stepIntoResult == null
			return true
		}
		return false
	}
	
	def private void jumpBeforeStep(IStep step) {
		if (step != null) {
			//Jumping to the correct state
			val i = step.startingIndex
			if (i == lastIndex) {
				lastJumpIndex = -1
			} else {
				lastJumpIndex = i
			}
			traceManager.goTo(i)
			//Computing the new callstack
			val newPath = new ArrayList
			newPath.add(step.parameters.get("this"))
			var parent = step.parentStep
			while (parent != null) {
				newPath.add(0,parent.parameters.get("this"))
				parent = parent.parentStep
			}
			doStuff(newPath,true)
			inThePast = stepIntoResult == null
		}
	}
	
	def private getPreviousValueIndex(IValueTrace valueTrace) {
		val currentValueIndex = valueTrace.getActiveValueIndex(currentStateIndex)
		var stateIndex = currentStateIndex - 1
		var valueIndex = valueTrace.getActiveValueIndex(stateIndex)
		while (stateIndex>0 && (valueIndex == currentValueIndex || valueIndex == -1)) {
			stateIndex--
			valueIndex = valueTrace.getActiveValueIndex(stateIndex)
		}
		return valueIndex
	}
	
	def public canBackValue(int traceIndex) {
		val allValueTraces = traceManager.allValueTraces
		if (traceIndex < allValueTraces.size && traceIndex > -1) {
			val valueTrace = allValueTraces.get(traceIndex)
			val currentValueIndex = valueTrace.getActiveValueIndex(currentStateIndex)
			var stateIndex = currentStateIndex
			var valueIndex = valueTrace.getActiveValueIndex(stateIndex)
			while (stateIndex>0 && (valueIndex == currentValueIndex || valueIndex == -1)) {
				stateIndex--
				valueIndex = valueTrace.getActiveValueIndex(stateIndex)
			}
			return valueIndex != currentValueIndex && valueIndex != -1
		}
		return false
	}
	
	def public backValue(int traceIndex) {
		val valueTrace = traceManager.allValueTraces.get(traceIndex)
		jump(valueTrace.getValue(getPreviousValueIndex(valueTrace)))
	}
	
	def private getNextValueIndex(IValueTrace valueTrace) {
		var stateIndex = currentStateIndex
		val currentValueIndex = valueTrace.getActiveValueIndex(stateIndex)
		var valueIndex = currentValueIndex
		while (stateIndex<lastIndex && (valueIndex == currentValueIndex || valueIndex == -1)) {
			stateIndex++
			valueIndex = valueTrace.getActiveValueIndex(stateIndex)
		}
		if (valueIndex == currentValueIndex || valueIndex == -1) {
			return valueTrace.size
		}
		return valueIndex
	}
	
	def public canStepValue(int traceIndex) {
		return true
	}
	
	def public stepValue(int traceIndex) {
		val valueTrace = traceManager.allValueTraces.get(traceIndex)
		val i = getNextValueIndex(valueTrace)
		if (i < valueTrace.size && i != -1) {
			jump(valueTrace.getValue(i))
		} else {
			//TODO notify stuff
		}
	}
	
	def private int getLastIndex() {
		return traceManager.traceSize - 1
	}
	
	override getCallStack() {
		return callStack
	}
	
}