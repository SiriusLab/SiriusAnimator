package fr.inria.diverse.trace.benchmark.utils

import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.ArrayList

class MemoryCSVHelper {

	public val Map<String,List<Long>> memoryFootprints = new HashMap
	public val Map<String,List<Integer>> numberOfStates = new HashMap
	public val List<String> traceTypes = new ArrayList
	
	public def exportLines() {
		val List<List<Long>> mem = new ArrayList
		val List<List<Integer>> states = new ArrayList
		val lines = new ArrayList
		var header = ""
		for (String traceType : traceTypes) {
			mem.add(memoryFootprints.get(traceType))
			states.add(numberOfStates.get(traceType))
			header += traceType + "Memory," + traceType + "States,"
		}
		header = header.substring(0,header.length-1)
		lines.add(header)
		for (var i = 0; i < mem.head.size; i++) {
			var s = ""
			for (var j = 0; j < traceTypes.size; j++) {
				s += mem.get(j).get(i) + "," + states.get(j).get(i) + ","
			}
			s = s.substring(0,s.length-1)
			lines.add(s)
		}
		return lines.join("\n")
	}
}
