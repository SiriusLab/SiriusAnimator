package fr.inria.diverse.trace.benchmark

import fr.inria.diverse.trace.benchmark.memory.MemoryAnalyzer
import fr.inria.diverse.trace.benchmark.utils.MemoryCSVHelper
import fr.inria.diverse.trace.commons.testutil.EclipseTestUtil
import fr.inria.diverse.trace.commons.testutil.Investigation
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Collection
import java.util.List
import java.util.Random
import java.util.function.Consumer
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import fr.inria.diverse.trace.commons.model.generictrace.GenericTrace
import org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel

@RunWith(Parameterized)
class BenchmarkMemoryTestSuite {

	val String tmpFolderContainer = "/home/dorian/tmp/yay"

	@Rule public TemporaryFolder tmpFolderCreator = new TemporaryFolder(new File(tmpFolderContainer));

	private def File createTmpFolder() {
		// return tmpFolderCreator.newFolder
		val rand = new Random
		val id = rand.nextInt(1000)
		val fileFriendlyTestCaseName = this.testCaseName.replaceAll(",", "-").replaceAll("/", "_")
		val folder = new File(tmpFolderContainer, fileFriendlyTestCaseName + "_" + id)
		folder.mkdirs
		return folder
	}

	// Parameters specific to each test
	val String testCaseName
	val String traceType
	val List<String> tracePackages
	val URI traceURI

	// Common to all tests
	static var File outputFolder
	static var File outputCSV
	static var PrintWriter outputCSVWriter
	static var FileOutputStream outputCSVStream
	static var MemoryCSVHelper csv

	new(String testCaseName, String traceType, List<String> tracePackages, URI traceURI) {
		this.traceURI = traceURI
		this.traceType = traceType
		this.tracePackages = tracePackages
		this.testCaseName = testCaseName
	}

	@BeforeClass
	def static void before() {
		// Create output folder in test project
		val Calendar currentDate = Calendar::getInstance();
		val SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY_HH-mm-ss");
		val String dateNow = formatter.format(currentDate.getTime());
		outputFolder = new File("output_memory" + "_" + dateNow)
		if (!outputFolder.exists)
			outputFolder.mkdir

		// Prepare CSV file in output folder
		outputCSV = new File(outputFolder, "results.csv")
		outputCSVStream = new FileOutputStream(outputCSV)
		outputCSVWriter = new PrintWriter(outputCSVStream, true)
		// Prepare csv helper
		csv = new MemoryCSVHelper
		csv.traceTypes.addAll("SpecificTraceImpl", "GenericTraceImpl", "ExecutionTraceModelImpl")
	}

	@AfterClass
	def static void after() {
		outputCSVWriter.println(csv.exportLines)
		outputCSVStream.close
		outputCSVWriter.close
		EclipseTestUtil.waitForJobs
	}

	@Test
	def void test() {


		// Loading
		println("Loading model")
		val ResourceSet rs = new ResourceSetImpl
		val Resource traceResource = rs.createResource(traceURI)
		traceResource.load(null)
		EcoreUtil::resolveAll(traceResource)

		// Confuse the memory by preserving refs to all EClasses
		val List<EClass> allEClasses = new ArrayList
		val allStuff = Investigation::findAllReachableObjects(traceResource)
		for (o : allStuff) {
			allEClasses.add(o?.eClass)
		}
		allStuff.clear
		
		// GC (in case...)
		println("GC...")
		System.gc

		// Measure memory
		// Dump memory and compute memory usage of the trace
		val heapFolder = createTmpFolder
		val heap = new File(heapFolder, traceType)
		println("Dumping memory")
		MemoryAnalyzer.dumpHeap(heap)
		println("Analyzing dump")
		
		var lMemory = csv.memoryFootprints.get(traceType)
		if (lMemory == null) {
			lMemory = new ArrayList
			csv.memoryFootprints.put(traceType, lMemory)
		}
		lMemory.add(computeMemoryUsage(heap))
		
		var nbStates = 0
		switch (traceType) {
			case "SpecificTraceImpl": nbStates = (traceResource.contents.get(0) as activitydiagramTrace.SpecificTrace).states.length
			case "GenericTraceImpl": nbStates = (traceResource.contents.get(0) as GenericTrace).states.length
			case "ExecutionTraceModelImpl": nbStates = (traceResource.contents.get(0) as ExecutionTraceModel).reachedStates.length
		}
		
		var lStates = csv.numberOfStates.get(traceType)
		if (lStates == null) {
			lStates = new ArrayList
			csv.numberOfStates.put(traceType, lStates)
		}
		lStates.add(nbStates)
		
		// Unloading
		println("Unloading model")
		for (c : traceResource.allContents.toSet) {
			c.eAdapters.clear
		}
		traceResource.eAdapters.clear
		traceResource.contents.clear
		traceResource.unload
		rs.resources.clear

	}
	
	static val String queryStart = '''SELECT * FROM "'''
	static val String queryEnd = '''"'''
	
	static val String queryAllUtil = '''SELECT * FROM ".*(PackageImpl|FactoryImpl|AdapterFactory|Switch)$"'''

	static def String createQuery(String... packagesNames) {
		'''«queryStart»(«packagesNames.map[s|'''.*«s».*'''].join("|")»)«queryEnd»'''
	}
	
	def long computeMemoryUsage(File heap) {
		val analyzer = new MemoryAnalyzer(heap)

		// First we make sure that there is only one trace
		val String queryCheck = '''SELECT * FROM ".*«traceType».*"'''
		println(queryCheck)
		val resCheck = analyzer.computeRetainedSizeWithOQLQuery(queryCheck)
		if (resCheck.nbElements != 1) {
			throw new Exception("Wrong number of traces: " + resCheck.nbElements)
		}
		
		val List<String> queries = new ArrayList<String>()
		val query = '''«queryStart»(«tracePackages.map[s|'''.*«s».*'''].join("|")»)«queryEnd»'''
		queries.add(query)
		
		val resquery = analyzer.computeRetainedSizeWithOQLQuery(queries, #[queryAllUtil])

		analyzer.cleanUp

		return resquery.memorySum
	}

	@Parameters(name="{0}")
	public def static Collection<Object[]> data() {

		val Collection<Object[]> data = new ArrayList<Object[]>();

		for (entry : BenchmarkData::tracePackages.entrySet) {

			val File traceFolder = new File("model_traces")
			if (traceFolder.exists) {
				val File traceTypeFolder = new File(traceFolder, entry.key)
				if (traceTypeFolder.exists) {
					val forEachFunc = new Consumer<Path>() {
						override accept(Path f) {
							val URI traceURI = URI.createFileURI(f.toAbsolutePath.toString)
							// Preparing test case name
							val String testCaseName = f.toString
							// Creating test case input data
							val Object[] testCaseData = #[
								testCaseName,
								entry.key,
								entry.value,
								traceURI
							];
							data.add(testCaseData)
						}
					}
					Files.walk(traceTypeFolder.toPath).filter([f|Files::isRegularFile(f)]).forEach(forEachFunc);
				}
			}
		}

		return data

	}

}
