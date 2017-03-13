package fr.inria.diverse.trace.benchmark.utils

import java.io.IOException
import java.io.PrintStream
import java.io.OutputStream
import java.util.Locale
import java.io.File
import java.io.FileInputStream
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.emf.transaction.util.TransactionUtil
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.HashMap
import java.util.Map
import java.util.Collection
import java.util.ArrayList
import java.util.List

class BenchmarkHelpers {

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

	public static def createEmptyPrintStream() {
		val emptyOutStream = new OutputStream() {
			override write(int b) throws IOException {}
		}

		val emptyPrintStream = new PrintStream(emptyOutStream) {
			override flush() {}

			override close() {}

			override write(int b) {}

			override write(byte[] b) {}

			override write(byte[] buf, int off, int len) {}

			override print(boolean b) {}

			override print(char c) {}

			override print(int i) {}

			override print(long l) {}

			override print(float f) {}

			override print(double d) {}

			override print(char[] s) {}

			override print(String s) {}

			override print(Object obj) {}

			override println() {}

			override println(boolean x) {}

			override println(char x) {}

			override println(int x) {}

			override println(long x) {}

			override println(float x) {}

			override println(double x) {}

			override println(char[] x) {}

			override println(String x) {}

			override println(Object x) {}

			override printf(String format, Object... args) { return this; }

			override printf(Locale l, String format, Object... args) { return this; }

			override format(String format, Object... args) { return this; }

			override format(Locale l, String format, Object... args) { return this; }

			override append(CharSequence csq) { return this; }

			override append(CharSequence csq, int start, int end) { return this; }

			override append(char c) { return this; }

		}
		return emptyPrintStream
	}

	public static def void clearResourceSet(ResourceSet rs) {
		val ed = TransactionUtil.getEditingDomain(rs)
		// Clean resource
		val command = new RecordingCommand(ed, "Clean resources") {
			override protected doExecute() {
				for (c : rs.allContents.toSet)
					c.eAdapters.clear
				for (r : rs.resources) {
					r.eAdapters.clear
				}
				rs.eAdapters.clear
			}
		}
		ed.commandStack.execute(command)
	}

	public static def <T,U, V extends Collection<U>> Map<T, V> mergeMaps(Map<T, V>... maps) {
		val result = new HashMap<T, V>()
		for (m : maps) {
			for (k : m.keySet) {
				if (result.containsKey(k)) {
					val Collection<U> value = result.get(k)
					val Collection<U> test = m.get(k)
					value.addAll(test)
				} else {
					val List<U> value = new ArrayList<U>
					value.addAll(m.get(k))
					result.put(k, value as V)
				}
			}

		}
		return result
	}
}
