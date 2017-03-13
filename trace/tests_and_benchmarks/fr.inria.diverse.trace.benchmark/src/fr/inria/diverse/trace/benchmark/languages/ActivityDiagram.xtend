package fr.inria.diverse.trace.benchmark.languages

import java.util.Set

class ActivityDiagram extends BenchmarkLanguage {
	
	new(Set<String> models) {
		super(models)
	}
	
	override getFolderName() {
		"activitydiagram"
	}
	
	override getEntryPoint() {
		"public static void org.gemoc.activitydiagram.sequential.xactivitydiagram.aspects.ActivityAspect.main(org.gemoc.activitydiagram.sequential.xactivitydiagram.activitydiagram.Activity)"
	}
	
	override getLanguageName() {
		"org.gemoc.activitydiagram.sequential.XActivityDiagram"
	}
	
	override getInitializationMethod() {
		"org.gemoc.activitydiagram.sequential.xactivitydiagram.aspects.ActivityAspect.initializeModel"
	}
	
}