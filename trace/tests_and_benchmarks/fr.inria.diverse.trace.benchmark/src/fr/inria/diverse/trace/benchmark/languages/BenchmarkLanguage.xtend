package fr.inria.diverse.trace.benchmark.languages

import java.util.Set

abstract class BenchmarkLanguage {

	val Set<String> models

	new(Set<String> models) {
		this.models = models
	}

	def getModels() {
		return models
	}

	def getJavaTraceRootName() {
		"SpecificTraceImpl"
	}
	
	def String getEntryPoint()
	
	def String getLanguageName()

	def String getFolderName()
	
	def String getInitializationMethod()

}
