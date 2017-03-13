package fr.inria.diverse.trace.benchmark.utils

import java.util.List
import java.util.ArrayList

class CSVHelper {

	public List<List<Long>> addStateExecutionTimes = new ArrayList
	public List<List<Long>> restoreStateExecutionTimes = new ArrayList
	public List<Long> totalExecutionTimes = new ArrayList
	public List<Long> initializationTimes = new ArrayList
	public Long memoryFootprint = 0L
	
	public def exportAddStateExecutionTimes() {
		val List<String> result = new ArrayList
		val maxSize = addStateExecutionTimes.maxBy[l|l.size].size
		for (var i = 0; i < maxSize; i++) {
			val List<Long> line = new ArrayList
			for (List<Long> l : addStateExecutionTimes) {
				line.add(if (i < l.size) l.get(i) else -1L)
			}
			result.add(line.map[t|if(t == -1L) "" else (t/1000000.0)].join(","))
		}
		return result.join("\n")
	}
	
	public def exportRestoreStateExecutionTimes() {
		val List<String> result = new ArrayList
		val maxSize = restoreStateExecutionTimes.maxBy[l|l.size].size
		for (var i = 0; i < maxSize; i++) {
			val List<Long> line = new ArrayList
			for (List<Long> l : restoreStateExecutionTimes) {
				line.add(if (i < l.size) l.get(i) else -1L)
			}
			result.add(line.map[t|if(t == -1L) "" else (t/1000000.0)].join(","))
		}
		return result.join("\n")
	}
	
	public def exportExecutionTimes() {
		totalExecutionTimes.map[t|t/1000000.0].join("\n")
	}
	
	public def exportInitializationTimes() {
		initializationTimes.map[t|t/1000000.0].join("\n")
	}
	
	public def exportMemoryFootprint() {
		memoryFootprint
	}
}
