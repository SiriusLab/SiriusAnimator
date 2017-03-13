package fr.inria.diverse.trace.benchmark.utils

import java.util.List
import java.util.ArrayList

//model_size,nbMut,timeStep,traceSize,traceMemoryFootprint,meanJumpTime,allJumpTimes
class CSVLine {

	public String languageName = ""
	public String traceMetamodel = ""
	public String modelName = ""
	public String inputName = ""
	public Integer modelID = -1
	public Long timeExe = new Long(0)
	public List<Long> timeWarms = new ArrayList()
	public List<Long> timeExes = new ArrayList()
	public Integer traceNbStates = 0
	public Long traceMemoryFootprint = new Long(0)
	public Integer nbWarmups = -1
	public Integer nbMeasurements = -1

	private static val separator = ";";

	private static def getAllFields() {
		CSVLine.declaredFields.filter[f|!f.name.contentEquals("separator")]
	}

	def static String getColumnNames() {
		val allNames = getAllFields.map[f|f.name]
		return allNames.join(separator)
	}

	def String customToString() {
		return getAllFields.map[f|f.get(this)].join(separator)
	}

}
