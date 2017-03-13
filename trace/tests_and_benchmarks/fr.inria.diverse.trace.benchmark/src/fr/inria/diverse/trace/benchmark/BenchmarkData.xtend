package fr.inria.diverse.trace.benchmark

import fr.inria.diverse.trace.benchmark.languages.ActivityDiagram

class BenchmarkData {

	// Constants
	public static val String modelFolderName = "models"
	public static val String outputFolderName = "output"
	public static val int NBMEASURES = 1
	public static val String projectName = "benchmark-project"

	static val activitydiagram = new ActivityDiagram(#{
		"2557-1_1.xsad",
		"2557-1_2.xsad",
		"2557-1_3.xsad",
		"2557-1_4.xsad",
		"2557-1_5.xsad",
		"2557-1_6.xsad",
		"2557-2_1.xsad",
		"2557-2_2.xsad",
		"2557-2_3.xsad",
		"2557-2_4.xsad",
		"2557-2_5.xsad",
		"2557-2_6.xsad",
		"3561-1_1.xsad",
		"3561-1_2.xsad",
		"3561-1_3.xsad",
		"3561-2_1.xsad",
		"3561-2_2.xsad",
		"3561-2_3.xsad",
		"ExampleBV1_false_false.xsad",
		"ExampleBV1_false_true.xsad",
		"ExampleBV1_true_false.xsad",
		"ExampleBV1_true_true.xsad",
		"ExampleBV2_false_false.xsad",
		"ExampleBV2_false_true.xsad",
		"ExampleBV2_true_false.xsad",
		"ExampleBV2_true_true.xsad",
		"ExampleBV3_false_false_false.xsad",
		"ExampleBV3_false_false_true.xsad",
		"ExampleBV3_false_true_false.xsad",
		"ExampleBV3_false_true_true.xsad",
		"ExampleBV3_true_false_false.xsad",
		"ExampleBV3_true_false_true.xsad",
		"ExampleBV3_true_true_false.xsad",
		"ExampleBV3_true_true_true.xsad",
		"hireV1_false.xsad",
		"hireV1_true.xsad",
		"hireV2_false.xsad",
		"hireV2_true.xsad",
		"hireV3_false.xsad",
		"hireV3_true.xsad",
		"hireV4_false.xsad",
		"hireV4_true.xsad"
	})

	// Input data for all tests
	public static val tracingCases = #[
//		"Clone",
		"None"
//		"Generic",
//		"Generated"
	]
	public static val languages = #{activitydiagram}
	
	public static val tracePackages = #{
		"SpecificTraceImpl" -> #["activitydiagramTrace.impl",
			"activitydiagramTrace.States.impl",
			"activitydiagramTrace.States.activitydiagram.impl",
			"activitydiagramTrace.Steps.impl"],
		"GenericTraceImpl" -> #["generictrace.impl"],
		"ExecutionTraceModelImpl" -> #["org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl",
			"org.gemoc.activitydiagram.sequential.xactivitydiagram.activitydiagram.impl"]
	}
}
