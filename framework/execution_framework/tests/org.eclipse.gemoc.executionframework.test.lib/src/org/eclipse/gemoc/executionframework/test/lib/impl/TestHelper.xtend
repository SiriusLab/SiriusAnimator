package org.eclipse.gemoc.executionframework.test.lib.impl

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.HashSet
import java.util.Random
import java.util.Set
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.util.URI
import org.eclipse.gemoc.executionframework.test.lib.IEngineWrapper
import org.eclipse.gemoc.executionframework.test.lib.IExecutableModel
import org.eclipse.gemoc.executionframework.test.lib.ILanguageWrapper
import org.eclipse.gemoc.trace.commons.model.trace.Trace
import org.eclipse.gemoc.trace.gemoc.traceaddon.GenericTraceEngineAddon
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon

import static org.junit.Assert.*

class TestHelper {

	static class TestResult {
		public var long executionDuration
		public var Trace<?, ?, ?> trace
		public var int amountOfStepsExecuted = 0
		public var boolean engineAboutToStart = false
		public var boolean engineAboutToStop = false
		public var boolean engineStarted = false
		public var boolean engineStopped = false
		public var boolean engineAboutToDispose = false
	}

	private static def testInternal(IProgressMonitor m, IEngineWrapper engine, ILanguageWrapper language,
		Set<String> addons, Set<IEngineAddon> otherAddons, IExecutableModel model, boolean cleanup) {
		// Create eclipse project in test WS
		val eclipseProject = ResourcesPlugin::getWorkspace().getRoot().getProject(
			Math::abs(new Random().nextInt).toString);
		if (eclipseProject.exists)
			eclipseProject.delete(true, m)
		eclipseProject.create(m)
		eclipseProject.open(m)

		// Copy model to execute in WS
		val IFile file = eclipseProject.getFile(model.getFileName());
		val String filePath = new File(new File(model.getFolderPath()), model.getFileName()).absolutePath
		TestUtil::copyFileFromPlugin(model.pluginName, filePath, file, m)

		// Create model URI
		val modelURI = URI::createPlatformResourceURI(file.fullPath.toString, true)

		// Prepare engine
		val TestEngineAddon testAddon = new TestEngineAddon(model.shouldStopAfter)
		engine.prepare(language, model, addons, modelURI)
		engine.realEngine.executionContext.executionPlatform.addEngineAddon(testAddon)
		for (otherAddon : otherAddons) {
			engine.realEngine.executionContext.executionPlatform.addEngineAddon(otherAddon)
		}

		// Execute engine
		engine.run

		// Dispose engine
		engine.realEngine.dispose

		// Generic oracle using test addon
		val testResult = testAddon.testResult
//		genericAsserts(testResult)
		if (cleanup) {
			eclipseProject.delete(true, true, m)
		}

		// Return
		return testResult
	}

	def static testWithJob(IEngineWrapper engine, ILanguageWrapper language, Set<String> addons,
		Set<IEngineAddon> otherAddons, IExecutableModel model, boolean cleanup) {
		val Set<TestResult> out = new HashSet
		val job = new Job("single test case") {

			override protected run(IProgressMonitor m) {
				try {
					val testResult = testInternal(m, engine, language, addons, otherAddons, model, cleanup)
					out.add(testResult)
					return Status.OK_STATUS
				} catch (Throwable t) {
					t.printStackTrace
					val StringWriter sw = new StringWriter();
					t.printStackTrace(new PrintWriter(sw));
					val errorStatus = new Status(Status.ERROR, "test", "An error occured in the test case", t)
					return errorStatus
				}
			}
		}
		job.schedule
		TestUtil::waitForJobs
		if (job.result != null && job.result.exception != null) {
			throw job.result.exception
		}
		return out.head
	}

	def static testWithGenericTrace(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model,
		boolean cleanup) {
		val traceAddon = new GenericTraceEngineAddon()
		val testResult = testWithJob(engine, language, #{}, #{traceAddon}, model, cleanup)
		// TODO when other PR is merged
		//testResult.trace = traceAddon.trace
		return testResult
	}

	def static testWithGenericTrace(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model) {
		testWithGenericTrace(engine, language, model, false)
	}

	def static testWithoutExtraAddons(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model,
		boolean cleanup) {
		return testWithJob(engine, language, #{}, #{}, model, cleanup)
	}

	def static testWithoutExtraAddons(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model) {
		return testWithoutExtraAddons(engine, language, model, false)
	}

	def static testNoAssert(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model, boolean cleanup) {
		val res = testWithJob(engine, language, #{}, #{}, model, cleanup)

		return res
	}

	public def static genericAsserts(TestResult testResult) {
		assertTrue("No steps were executed", testResult.amountOfStepsExecuted > 0)
		assertTrue("engineAboutToStart never performed", testResult.engineAboutToStart)
		assertTrue("engineStarted never performed", testResult.engineStarted)
		assertTrue("engineAboutToStop never performed", testResult.engineAboutToStop)
		assertTrue("engineStopped never performed", testResult.engineStopped)
		assertTrue("engineAboutToDispose never performed", testResult.engineAboutToDispose)
	}

}
