package org.gemoc.executionframework.test.lib.impl

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Random
import java.util.Set
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.util.URI
import org.gemoc.executionframework.test.lib.IEngineWrapper
import org.gemoc.executionframework.test.lib.IExecutableModel
import org.gemoc.executionframework.test.lib.ILanguageWrapper

import static org.junit.Assert.*

class TestHelper {

	private static def testInternal(IProgressMonitor m, IEngineWrapper engine, ILanguageWrapper language,
		Set<String> addons, IExecutableModel model) {

		// Create eclipse project in test WS
		val eclipseProject = ResourcesPlugin::getWorkspace().getRoot().getProject(Math::abs(new Random().nextInt).toString);
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

		// Execute engine
		engine.run
		
		// Dispose engine
		engine.realEngine.dispose
		
		// Generic oracle using test addon
		assertTrue("No steps were executed", testAddon.amountOfStepsExecuted > 0)
		assertTrue("engineAboutToStart never performed", testAddon.engineAboutToStart)
		assertTrue("engineStarted never performed", testAddon.engineStarted)
		assertTrue("engineAboutToStop never performed", testAddon.engineAboutToStop)
		assertTrue("engineStopped never performed", testAddon.engineStopped)
		assertTrue("engineAboutToDispose never performed", testAddon.engineAboutToDispose)
		
		// Done
		return Status.OK_STATUS
	}

	def static void testWithAddons(IEngineWrapper engine, ILanguageWrapper language, Set<String> addons,
		IExecutableModel model) {
		val job = new Job("single test case") {

			override protected run(IProgressMonitor m) {
				try {
					return testInternal(m, engine, language, addons, model)
				} catch (Throwable t) {
					t.printStackTrace
					val StringWriter sw = new StringWriter();
					t.printStackTrace(new PrintWriter(sw));
					val errorStatus = new Status(Status.ERROR, "trace test", "An error occured in the test case", t)
					return errorStatus
				}
			}
		}
		job.schedule
		TestUtil::waitForJobs
		if (job.result != null && job.result.exception != null) {
			throw job.result.exception
		}
	}

	def static void testWithGenericTrace(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model) {
		testWithAddons(engine, language, #{"Generic MultiDimensional Trace"}, model)
	}

	def static void testWithoutExtraAddons(IEngineWrapper engine, ILanguageWrapper language, IExecutableModel model) {
		testWithAddons(engine, language, #{}, model)
	}

}
