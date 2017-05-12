package org.gemoc.executionframework.test.lib.impl

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.Map.Entry
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.core.runtime.Platform
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.IWindowListener
import org.eclipse.ui.IWorkbenchWindow
import org.eclipse.ui.PlatformUI
import org.gemoc.executionframework.engine.Activator
import org.gemoc.xdsmlframework.api.core.EngineStatus
import org.gemoc.xdsmlframework.api.core.IExecutionEngine
import org.osgi.framework.Bundle

class TestUtil {

	public static def void copyFileInWS(File file, IFolder destination, IProgressMonitor m) {
		val fileInProject = destination.getFile(file.name)
		if (!fileInProject.exists)
			fileInProject.create(new FileInputStream(file), true, m);
	}

	public static def IFolder copyFolderInWS(File folder, IResource destination, IProgressMonitor m) {
		val folderCopy = if (destination instanceof IProject) {
				destination.getFolder(folder.name)
			} else if (destination instanceof IFolder) {
				destination.getFolder(folder.name)
			} else
				null

		if (!folderCopy.exists)
			folderCopy.create(true, true, m)
		for (File f : folder.listFiles) {
			if (f.isFile) {
				copyFileInWS(f, folderCopy, m)
			} else if (f.isDirectory) {
				copyFolderInWS(f, folderCopy, m)
			}
		}
		return folderCopy
	}

	public static def void waitForJobs() {
		while (!Job.getJobManager().isIdle())
			delay(100);
	}

	public static def void waitForJobsThenWindowClosed() {
		waitForJobsThenWait(1000000000);
	}

	public static def void waitForJobsThenWait(long waitTimeMillis) {
		waitForJobs();
		delay(waitTimeMillis);
	}

	public static def void waitUIThread(long waitTimeMillis) {
		delay(waitTimeMillis);
	}

	static var closed = false;

	private static def void delay(long waitTimeMillis) {
		val Display display = Display.getCurrent();

		// We try to capture when the window is closed by the tester
		PlatformUI.getWorkbench.addWindowListener(
			new IWindowListener() {

				override windowActivated(IWorkbenchWindow window) {
				}

				override windowClosed(IWorkbenchWindow window) {
					closed = true
				}

				override windowDeactivated(IWorkbenchWindow window) {
				}

				override windowOpened(IWorkbenchWindow window) {
				}

			}
		)

		// If this is the UI thread,
		// then process input.
		if (display != null) {
			val long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis && !closed) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.update();
		} // Otherwise, perform a simple sleep.
		else {
			try {
				Thread.sleep(waitTimeMillis);
			} catch (InterruptedException e) {
				// Ignored.
			}
		}
	}

	def static public void removeStoppedEngines() {
		val registry = Activator.getDefault().gemocRunningEngineRegistry
		for (Entry<String, IExecutionEngine> engineEntry : registry.getRunningEngines().entrySet()) {
			if (engineEntry.value.runningStatus.equals(EngineStatus.RunStatus.Stopped)) {
				registry.unregisterEngine(engineEntry.getKey())
			}

		}
	}

	def static copyFileFromPlugin(String pluginName, String pathInPlugin, IFile targetFile, IProgressMonitor m) {
		val Bundle bundle = Platform::getBundle(pluginName);
		val path = new Path(pathInPlugin)
		val InputStream stream = FileLocator::openStream(bundle, path, false);
		targetFile.create(stream, true, m);
	}
	
	
}
