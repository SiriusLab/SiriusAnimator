/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package fr.inria.diverse.trace.gemoc.traceaddon

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import fr.inria.diverse.trace.commons.model.trace.Dimension
import fr.inria.diverse.trace.commons.model.trace.State
import fr.inria.diverse.trace.commons.model.trace.Step
import fr.inria.diverse.trace.commons.model.trace.Trace
import fr.inria.diverse.trace.commons.model.trace.TracedObject
import fr.inria.diverse.trace.commons.model.trace.Value
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon
import fr.inria.diverse.trace.gemoc.api.IStateManager
import fr.inria.diverse.trace.gemoc.api.ITraceConstructor
import fr.inria.diverse.trace.gemoc.api.ITraceExplorer
import fr.inria.diverse.trace.gemoc.api.ITraceExtractor
import fr.inria.diverse.trace.gemoc.api.ITraceListener
import fr.inria.diverse.trace.gemoc.api.ITraceNotifier
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Consumer
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.emf.transaction.util.TransactionUtil
import org.gemoc.commons.eclipse.emf.EMFResource
import org.gemoc.executionframework.engine.core.CommandExecution
import org.gemoc.xdsmlframework.api.core.IExecutionContext
import org.gemoc.xdsmlframework.api.core.IExecutionEngine
import org.gemoc.xdsmlframework.api.engine_addon.DefaultEngineAddon
import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon
import org.gemoc.xdsmlframework.api.engine_addon.modelchangelistener.BatchModelChangeListener
import org.gemoc.xdsmlframework.api.extensions.engine_addon.EngineAddonSpecificationExtensionPoint

abstract class AbstractTraceAddon extends DefaultEngineAddon implements IMultiDimensionalTraceAddon<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> {

	private IExecutionContext _executionContext
	private ITraceExplorer<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExplorer
	private ITraceExtractor<Step<?>, State<?,?>, TracedObject<?>, Dimension<?>, Value<?>> traceExtractor
	private ITraceConstructor traceConstructor
	private ITraceNotifier traceNotifier
	private BatchModelChangeListener traceListener
	private boolean shouldSave = true
	private var boolean needTransaction = true

	private BatchModelChangeListener listenerAddon
	
	public static AbstractTraceAddon instance

	abstract def ITraceConstructor constructTraceConstructor(Resource modelResource, Resource traceResource, Map<EObject, TracedObject<?>> exeToTraced)
	
	abstract def IStateManager<State<?,?>> constructStateManager(Resource modelResource, Map<TracedObject<?>, EObject> tracedToExe)

	abstract def boolean isAddonForTrace(EObject traceRoot)

	override getTraceExplorer() {
		return traceExplorer
	}

	override getTraceConstructor() {
		return traceConstructor
	}

	override getTraceExtractor() {
		return traceExtractor
	}
	
	override getTraceNotifier() {
		return traceNotifier
	}

	private val List<ITraceListener> listeners = new ArrayList

	def void addListener(ITraceListener listener) { listeners.add(listener) }

	def void removeListener(ITraceListener listener) { listeners.remove(listener) }

	public def void disableTraceSaving() {
		shouldSave = false
	}

	public def void load(Resource traceResource) {
		val root = traceResource.contents.head
		if (root instanceof Trace<?,?,?>) {
			val trace = root as Trace<Step<?>,TracedObject<?>,State<?,?>>
			traceExplorer = new GenericTraceExplorer(trace)
			traceExtractor = new GenericTraceExtractor(trace)
		} else {
			traceExplorer = null
			traceExtractor = null
		}
	}

	private static def String getEPackageFQN(EPackage p, String separator) {
		val EPackage superP = p.getESuperPackage
		if (superP != null) {
			return getEPackageFQN(superP, separator) + separator + p.name
		} else {
			return p.name.toFirstUpper
		}
	}

	override aboutToExecuteStep(IExecutionEngine executionEngine, Step<?> step) {
		manageStep(step, true)
	}

	override stepExecuted(IExecutionEngine engine, Step<?> step) {
		manageStep(step, false)
	}

	private var String tmpAddFilePath = System.getProperty("tmpAddFileProperty")
	private var File outputAddTmp
	private var FileOutputStream outputAddTmpStream
	private var PrintWriter outputAddTmpWriter
	private var String tmpRestoreFilePath = System.getProperty("tmpRestoreFileProperty")
	private var File outputRestoreTmp
	private var FileOutputStream outputRestoreTmpStream
	private var PrintWriter outputRestoreTmpWriter
	protected var Consumer<Long> restoreLogger
	
	protected def manageStep(Step<?> step, boolean add) {
		if (step != null) {
			modifyTrace([
				if (outputAddTmpWriter != null) {
					val t = System.nanoTime
					traceConstructor.addState(listenerAddon.getChanges(this))
					outputAddTmpWriter.println((System.nanoTime - t))
				} else {
					traceConstructor.addState(listenerAddon.getChanges(this))
				}
				
				if (add) {
					traceConstructor.addStep(step)
				} else {
					traceConstructor.endStep(step)
				}
				
				// Updating the trace extractor and explorer with the last changes
				traceNotifier.notifyListener(traceExtractor)
				traceNotifier.notifyListener(traceExplorer)
				// Updating other trace listeners with the last changes
				traceNotifier.notifyListeners
				// Updating the state of the trace explorer
				traceExplorer.updateCallStack(step)
			])

			if (shouldSave) {
//				traceConstructor.save()
			}
		}
	}
	
	override engineAboutToStop(IExecutionEngine engine) {
//		val path = System.getProperty("saveTracePath")
//		if (path != null && path.length > 0) {
//			traceConstructor.save(URI.createFileURI(path))
//		}
		for (var i = 0; i < traceExtractor.statesTraceLength; i++) {
			traceExplorer.jump(traceExtractor.getState(i))
		}
		if (outputAddTmpWriter != null) {
			outputAddTmpStream.close
			outputAddTmpWriter.close
			outputRestoreTmpStream.close
			outputRestoreTmpWriter.close
		}
	}

	/**
	 * To construct the trace manager
	 */
	override engineAboutToStart(IExecutionEngine engine) {
		
		tmpAddFilePath = System.getProperty("tmpAddFileProperty")
		outputAddTmp = if (tmpAddFilePath != null && tmpAddFilePath.length > 0) new File(tmpAddFilePath) else null
		outputAddTmpStream = if (outputAddTmp !=null) new FileOutputStream(outputAddTmp) else null
		outputAddTmpWriter = if (outputAddTmpStream != null) new PrintWriter(outputAddTmpStream, true) else null
		tmpRestoreFilePath = System.getProperty("tmpRestoreFileProperty")
		outputRestoreTmp = if (tmpRestoreFilePath != null && tmpRestoreFilePath.length > 0) new File(tmpRestoreFilePath) else null
		outputRestoreTmpStream = if (outputRestoreTmp !=null) new FileOutputStream(outputRestoreTmp) else null
		outputRestoreTmpWriter = if (outputRestoreTmpStream != null) new PrintWriter(outputRestoreTmpStream, true) else null
		restoreLogger = [l|if (outputRestoreTmpWriter != null) outputRestoreTmpWriter.println(l)]
		
		if (_executionContext == null) {
			_executionContext = engine.executionContext

			val modelResource = _executionContext.resourceModel

			// Creating the resource of the trace
			// val ResourceSet rs = modelResource.getResourceSet()
			val ResourceSet rs = new ResourceSetImpl

			// We check whether or not we need transactions
			val ed = TransactionUtil.getEditingDomain(rs)
			needTransaction = ed != null

			val URI traceModelURI = URI.createPlatformResourceURI(
				_executionContext.getWorkspace().getExecutionPath().toString() + "/execution.trace", false)
			val Resource traceResource = rs.createResource(traceModelURI)

			// We construct a new listener addon if required
			this.listenerAddon = new BatchModelChangeListener(EMFResource.getRelatedResources(engine.executionContext.resourceModel))
			listenerAddon.registerObserver(this)

			val launchConfiguration = engine.extractLaunchConfiguration

			val BiMap<EObject, TracedObject<?>> exeToTraced = HashBiMap.create

			// We construct the trace constructor, using the concrete generated method
			traceConstructor = constructTraceConstructor(modelResource, traceResource, exeToTraced)

			// We initialize the trace
			modifyTrace([traceConstructor.initTrace(launchConfiguration)])

			// And we enable trace exploration by loading it in a new trace explorer
			val root = traceResource.contents.head
			if (root instanceof Trace<?,?,?>) {
				val trace = root as Trace<Step<?>,TracedObject<?>,State<?,?>>
				val stateManager = constructStateManager(modelResource, exeToTraced.inverse)
				traceExplorer = new GenericTraceExplorer(trace, stateManager)
				traceExtractor = new GenericTraceExtractor(trace)
				traceListener = new BatchModelChangeListener(EMFResource.getRelatedResources(traceResource))
				traceNotifier = new GenericTraceNotifier(traceListener)
				traceNotifier.addListener(traceExtractor)
				traceNotifier.addListener(traceExplorer)
			}
		}
		instance = this;
	}

	/**
	 * Wrapper using lambda to always use a RecordingCommand when modifying the trace
	 */
	private def void modifyTrace(Runnable r, String message) {
		if (needTransaction) {
			val ed = TransactionUtil.getEditingDomain(_executionContext.resourceModel)
			val Set<Throwable> catchedException = new HashSet
			var RecordingCommand command = new RecordingCommand(ed, message) {
				protected override void doExecute() {
					try {
						r.run
					} catch (Throwable t) {
						catchedException.add(t)
					}
				}
			}
			CommandExecution.execute(ed, command)
			if (!catchedException.empty)
				throw catchedException.head
		} else {
			r.run
		}
	}

	/**
	 * Same as above, but without message.
	 */
	private def void modifyTrace(Runnable r) {
		modifyTrace(r, "")
	}

	public override List<String> validate(List<IEngineAddon> otherAddons) {

		val ArrayList<String> errors = new ArrayList<String>()

		var boolean found = false
		var addonName = ""
		for (IEngineAddon iEngineAddon : otherAddons) {
			if (iEngineAddon instanceof AbstractTraceAddon && iEngineAddon !== this) {
				found = true
				addonName = EngineAddonSpecificationExtensionPoint.getName(iEngineAddon)
			}
		}

		if (found) {
			val thisName = EngineAddonSpecificationExtensionPoint.getName(this)
			errors.add(thisName + " can't run with " + addonName)
		}

		return errors
	}

}
