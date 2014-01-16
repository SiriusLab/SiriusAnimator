package org.gemoc.gemoc_modeling_workbench.ui.launcher;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.gemoc.execution.engine.core.ExecutionEngine;
import org.gemoc.execution.engine.core.impl.GemocExecutionEngine;
import org.gemoc.gemoc_language_workbench.api.dsa.Executor;
import org.gemoc.gemoc_language_workbench.api.feedback.FeedbackPolicy;
import org.gemoc.gemoc_language_workbench.api.moc.Solver;
import org.gemoc.gemoc_language_workbench.api.utils.LanguageInitializer;
import org.gemoc.gemoc_language_workbench.api.utils.ModelLoader;
import org.gemoc.gemoc_modeling_workbench.ui.Activator;
import org.gemoc.gemoc_modeling_workbench.ui.launcher.minitestengine.MiniEngine;

public class GemocReflectiveModelLauncher implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {

		Activator.getDefault().getMessaggingSystem().showConsole();		
		/*Activator
				.getDefault()
				.getMessaggingSystem()
				.warn("Run Gemoc Model using MiniEngine for test, need to connect the real generic execution engine here",
						"");*/
		String modelPath = configuration.getAttribute(GemocModelLauncherConfigurationConstants.LAUNCH_MODEL_PATH, "");
		String languageName = configuration.getAttribute(
				GemocModelLauncherConfigurationConstants.LAUNCH_SELECTED_LANGUAGE, "");
		
		Activator.getDefault().getMessaggingSystem()
			.info("Run \""+modelPath+"\" using \""+languageName+"\" language definition...", "");

		IConfigurationElement confElement = null;
		IConfigurationElement[] confElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				"org.gemoc.gemoc_language_workbench.xdsml");
		// retrieve the extension for the chosen language
		for (int i = 0; i < confElements.length; i++) {
			if (confElements[i].getAttribute("name").equals(languageName)) {
				confElement = confElements[i];
			}
		}

		LanguageInitializer languageInitializer = null;
		ModelLoader modelLoader = null;
		Solver solver = null;
		Executor executor = null;
		FeedbackPolicy feedbackPolicy = null;
		String eclFilePath = null;

		// get the extension objects
		/* test : désactivé le temps de vérifier l'executor
		if (confElement != null) {
			try{
				final Object oinitializer = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_INITIALIZER_ATT);
				if (oinitializer instanceof LanguageInitializer) {
					languageInitializer = (LanguageInitializer) oinitializer;
				}
	
				final Object omodelLoader = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_LOADMODEL_ATT);
				if (omodelLoader instanceof ModelLoader) {
					modelLoader = (ModelLoader) omodelLoader;
				}
	
				final Object oSolver = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_SOLVER_ATT);
				if (oSolver instanceof Solver) {
					solver = (Solver) oSolver;
				}
	
				final Object oexecutor = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_EXECUTOR_ATT);
				if (oexecutor instanceof Executor) {
					executor = (Executor) oexecutor;
				}
	
				final Object oFeedbackPolicy = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_FEEDBACKPOLICY_ATT);
				if (oFeedbackPolicy instanceof FeedbackPolicy) {
					feedbackPolicy = (FeedbackPolicy) oFeedbackPolicy;
				}
				
				final Object oEclFilePath = confElement
						.createExecutableExtension(org.gemoc.gemoc_language_workbench.ui.Activator.GEMOC_LANGUAGE_EXTENSION_POINT_XDSML_DEF_ECL_FILE_PATH_ATT);
				if (oEclFilePath instanceof String) {
					eclFilePath = (String) oEclFilePath;
				}
			}catch(CoreException ce){
				Activator.getDefault()
					.getMessaggingSystem().error("Cannot run model using language \""+languageName+"\" because the language isn't fully defined. "+ce.getMessage(), "");
				throw ce;
			}
		}

		if (languageInitializer == null | modelLoader == null | solver == null | executor == null
				| feedbackPolicy == null | eclFilePath == null) {
			Activator.warn("One of the API elements is null", new NullPointerException());
		}
		try {
			ExecutionEngine engine = new GemocExecutionEngine(languageInitializer, modelLoader, solver, executor, feedbackPolicy);
			engine.initialize(modelPath, eclFilePath);
			engine.run(1);
		} catch (Throwable e) {
			Activator.error("Exception in the initialization of the engine", e);
		}LanguageInitializer
		*/
		MiniEngine engine = new MiniEngine(languageName);
		engine.launchEngine(modelPath);
		
	}
}