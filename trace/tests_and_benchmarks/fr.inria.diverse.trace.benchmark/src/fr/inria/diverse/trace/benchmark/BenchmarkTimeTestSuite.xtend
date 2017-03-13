package fr.inria.diverse.trace.benchmark

import fr.inria.diverse.trace.benchmark.languages.BenchmarkLanguage
import fr.inria.diverse.trace.benchmark.utils.PDETestResultsCollector
import java.io.File
import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

import static fr.inria.diverse.trace.benchmark.BenchmarkData.*

@RunWith(Parameterized)
class BenchmarkTimeTestSuite {

	@Rule
	public TemporaryFolder tmpFolderCreator = new TemporaryFolder();

	// Parameters specific to each test
	val BenchmarkLanguage language
	val String tracingCase
	val String model
	val String testCaseName
	val int modelID

	var String outputFolderPath
	
	// Constants
	static val int port = 7777
	static val int minMemory = 1024
	static val int maxMemory = 5120
	static val String javaHome = "/usr/lib/jvm/java-8-oracle/bin/java"
	static val String wsPath = "file:/home/dorian/workspace"
	static val String rWdPath = "/home/dorian/workspace/R"
	static val String outputRPath = "results"

	// Test case constructor
	new(String testCaseName, int modelID, BenchmarkLanguage language, String tracingCase, String model) {
		this.modelID = modelID
		this.language = language
		this.tracingCase = tracingCase
		this.model = model
		this.testCaseName = testCaseName
	}

	static def String prepareProperty(String key, String value) {
		return '''-D«key»=«value»'''
	}

	@Before
	def void prepareOutput() {
		// Create output folder
		val languageSimpleName = language.languageName.substring(language.languageName.lastIndexOf(".")+1)
		val modelSimpleName = model.substring(0,model.lastIndexOf("."))
		val outputFolder = new File(rWdPath + "/" + outputRPath + "/" + languageSimpleName + "_" + modelSimpleName)
		if (!outputFolder.exists)
			outputFolder.mkdir
		outputFolderPath = outputFolder.absolutePath
	}

	public def void log(String s) {
		println("### [" + testCaseName + "] " + s)
	}

	@Test
	def void test() {
		// These params are completely specific to an environment,
		// and they need the PDE test suite (there, BenchmarkPhase1SingleJVMTestSuite) to have
		// been executed at least one before via Eclispe, so that its conf files
		// are ready.
		// NOTE: requires passwordless sudo!
		
		log ("Preparing tmp folder")
		val tmpWs = tmpFolderCreator.root

		val List<String> params = #["sudo", "ionice", "-c", "2", "-n", "0", "nice", "-19",
				javaHome, "-Xms" + minMemory + "m", "-Xmx" + maxMemory + "m",
				"-Declipse.pde.launch=true", "-Declipse.p2.data.area=@config.dir/p2", "-Dfile.encoding=UTF-8",
				prepareProperty(BenchmarkSingleJVMTestSuite::modelProperty, model),
				prepareProperty(BenchmarkSingleJVMTestSuite::languageProperty, language.class.simpleName),
				prepareProperty(BenchmarkSingleJVMTestSuite::tracingCaseProperty, tracingCase),
				prepareProperty(BenchmarkSingleJVMTestSuite::outputFolderProperty, outputFolderPath), "-classpath",
				"/home/dorian/Downloads/gemoc-studio/plugins/org.eclipse.equinox.launcher_1.3.201.v20161025-1711.jar",
				"org.eclipse.equinox.launcher.Main", "-os", "linux", "-ws", "gtk", "-arch", "x86_64", "-nl", "fr_FR",
				"-consoleLog", "-version", "3", "-port", port.toString, "-testLoaderClass",
				"org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader", "-loaderpluginname",
				"org.eclipse.jdt.junit4.runtime", "-classNames", BenchmarkSingleJVMTestSuite.name, "-application",
				"org.eclipse.pde.junit.runtime.uitestapplication", "-product org.eclipse.platform.ide",
				"-testApplication", "org.eclipse.ui.ide.workbench", "-data", tmpWs.absolutePath, "-configuration",
				wsPath + "/.metadata/.plugins/org.eclipse.pde.core/pde-junit/", "-dev",
				wsPath + "/.metadata/.plugins/org.eclipse.pde.core/pde-junit/dev.properties",
				"-os", "linux", "-ws", "gtk", "-arch", "x86_64", "-nl", "fr_FR", "-consoleLog", "-testpluginname",
				"fr.inria.diverse.trace.benchmark"]
			
		// Start Junit listener in separate job
		log ("Start dummy junit listener")
		val junitListener = new Runnable() {
			override run() {
				val PDETestResultsCollector collector = new PDETestResultsCollector(
					"listening for measure")
				collector.run(port);
			}
		}
		val junitListenerThread = new Thread(junitListener)
		junitListenerThread.start

		// Run actual test
		log ("Start test in dedicated JVM")
		val ProcessBuilder processBuilder = new ProcessBuilder(params)
		val Process process = processBuilder.start()
		process.waitFor()

		// Finish test listener
		log ("Kill dummy junit listener")
		junitListenerThread.stop
	}

	@Parameters(name="{0}")
	public def static Collection<Object[]> data() {

		val Collection<Object[]> data = new ArrayList<Object[]>();
		
		// For each language
		for (language : languages) {

			// For each kind of trace metamodel
			for (tracingCase : tracingCases) {
				
				var int i = 1;

				// For each model
				for (model : language.models) {
					// Preparing test case name
					val testCaseNameElements = new ArrayList
					testCaseNameElements.addAll(#[i, language.folderName, tracingCase, model])
					
					val testCaseName = testCaseNameElements.join(",")

					// Creating test case input data
					val Object[] testCaseData = #[
						testCaseName,
						i,
						language,
						tracingCase,
						model
					];
					data.add(testCaseData)
					i++
				}
			}
		}
		return data
	}
}
