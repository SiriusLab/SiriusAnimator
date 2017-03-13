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
package org.gemoc.execution.sequential.javaengine;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.gemoc.executionframework.engine.commons.MelangeHelper;
import org.gemoc.executionframework.engine.core.AbstractCommandBasedSequentialExecutionEngine;
import org.gemoc.executionframework.engine.core.EngineStoppedException;
import org.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.gemoc.xdsmlframework.api.core.IRunConfiguration;
import org.kermeta.utils.provisionner4eclipse.Provisionner;
import org.osgi.framework.Bundle;

import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand;
import fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry;
import fr.inria.diverse.melange.adapters.EObjectAdapter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration;
import fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationFactory;
import fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter;
import fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter;

/**
 * Implementation of the GEMOC Execution engine dedicated to run Kermeta 3 operational semantic
 * 
 * @author Didier Vojtisek<didier.vojtisek@inria.fr>
 *
 */
public class PlainK3ExecutionEngine extends AbstractCommandBasedSequentialExecutionEngine implements IStepManager {

	private Method initializeMethod;
	private List<Object> initializeMethodParameters;
	private Method entryPointMethod;
	private List<Object> entryPointMethodParameters;
	private Class<?> entryPointClass;

	private static final String LAUNCH_CONFIGURATION_TYPE = "org.gemoc.execution.sequential.javaengine.ui.launcher";

	@Override
	public String engineKindName() {
		return "GEMOC Kermeta Sequential Engine";
	}

	/**
	 * Constructs a PlainK3 execution engine using an entry point (~ a main
	 * operation) The entrypoint will register itself as a StepManager into the
	 * K3 step manager registry, and unregister itself at the end. As a
	 * StepManager, the PlainK3ExecutionEngine will receive callbacks through
	 * its "executeStep" operation.
	 */
	@Override
	protected void prepareEntryPoint(IExecutionContext executionContext) {
		/*
		 * Get info from the RunConfiguration
		 */
		String entryPoint = executionContext.getRunConfiguration().getExecutionEntryPoint();
		String mainModelElementURI = executionContext.getRunConfiguration().getModelEntryPoint();

		/*
		 * Find the entry point in the workspace
		 */
		final String prefix = "public static void ";
		int startName = prefix.length();
		int endName = entryPoint.lastIndexOf("(");
		String methodFullName = entryPoint.substring(startName, endName);

		String aspectClassName = methodFullName.substring(0, methodFullName.lastIndexOf("."));
		String methodName = methodFullName.substring(methodFullName.lastIndexOf(".") + 1);

		Bundle bundle = findBundle(executionContext, aspectClassName);
		if (bundle == null)
			throw new RuntimeException("Could not find bundle for language \""
					+ executionContext.getRunConfiguration().getLanguageName() + "\"");

		// search the class
		try {
			entryPointClass = bundle.loadClass(aspectClassName);
		} catch (ClassNotFoundException e) {
			String bundleName = bundle.getHeaders().get("Bundle-Name");
			e.printStackTrace();
			throw new RuntimeException(
					"Could not find class " + executionContext.getRunConfiguration().getExecutionEntryPoint()
							+ " in bundle " + bundleName + ".");
		}

		// search the method
		this.entryPointMethodParameters = new ArrayList<>();
		EObject root = executionContext.getResourceModel().getEObject(mainModelElementURI);
		if (root instanceof EObjectAdapter) {
			entryPointMethodParameters.add(((EObjectAdapter<?>) root).getAdaptee());
		} else {
			entryPointMethodParameters.add(root);
		}
		try {
			this.entryPointMethod = MelangeHelper.findMethod(entryPointClass, root, methodName);
		} catch (Exception e) {
			String msg = "There is no \"" + methodName + "\" method in " + entryPointClass.getName()
					+ " with first parameter able to handle " + entryPointMethodParameters.get(0).toString();
			msg += " from " + ((EObject) entryPointMethodParameters.get(0)).eClass().getEPackage().getNsURI();
			Activator.error(msg, e);
			throw new RuntimeException("Could not find method main with correct parameters.");
		}
	}

	@Override
	protected void prepareInitializeModel(IExecutionContext executionContext) {

		// try to get the initializeModelRunnable
		String modelInitializationMethodQName = executionContext.getRunConfiguration().getModelInitializationMethod();
		if (!modelInitializationMethodQName.isEmpty()) {
			// the current system supposes that the modelInitialization method
			// is in the same class as the entry point
			String modelInitializationMethodName = modelInitializationMethodQName
					.substring(modelInitializationMethodQName.lastIndexOf(".") + 1);
			boolean isListArgs = false;
			boolean isEListArgs = false;
			boolean isFound = false;
			try {
				Class<?>[] modelInitializationParamType = new Class[] {
						entryPointMethodParameters.get(0).getClass().getInterfaces()[0], String[].class };
				initializeMethod = entryPointClass.getMethod(modelInitializationMethodName,
						modelInitializationParamType);
				isListArgs = false; // this is a java array
				isFound = true;
			} catch (Exception e) {

			}
			if (!isFound) {
				try {
					Class<?>[] modelInitializationParamType = new Class[] {
							entryPointMethodParameters.get(0).getClass().getInterfaces()[0], List.class };
					initializeMethod = entryPointClass.getMethod(modelInitializationMethodName,
							modelInitializationParamType);
					isListArgs = true; // this is a List
					isFound = true;
				} catch (Exception e) {

				}
			}
			if (!isFound) {
				try {
					Class<?>[] modelInitializationParamType = new Class[] {
							entryPointMethodParameters.get(0).getClass().getInterfaces()[0], EList.class };
					this.initializeMethod = entryPointClass.getMethod(modelInitializationMethodName,
							modelInitializationParamType);
					isEListArgs = true; // this is an EList
				} catch (Exception e) {
					String msg = "There is no \"" + modelInitializationMethodName + "\" method in "
							+ entryPointClass.getName() + " with first parameter able to handle "
							+ entryPointMethodParameters.get(0).toString();
					msg += " and String[] or List<String> or EList<String> args as second parameter";
					msg += " from " + ((EObject) entryPointMethodParameters.get(0)).eClass().getEPackage().getNsURI();
					Activator.error(msg, e);
					// ((EObject)parameters.get(0)).eClass().getEPackage().getNsURI()
					throw new RuntimeException(
							"Could not find method " + modelInitializationMethodName + " with correct parameters.");
				}
			}
			final boolean finalIsListArgs = isListArgs;
			final boolean finalIsEListArgs = isEListArgs;
			this.initializeMethodParameters = new ArrayList<>();
			initializeMethodParameters.add(entryPointMethodParameters.get(0));
			if (finalIsListArgs) {
				final ArrayList<Object> modelInitializationListParameters = new ArrayList<>();
				for (String s : executionContext.getRunConfiguration().getModelInitializationArguments()
						.split("\\r?\\n")) {
					modelInitializationListParameters.add(s);
				}
				initializeMethodParameters.add(modelInitializationListParameters);
			} else if (finalIsEListArgs) {
				final EList<Object> modelInitializationListParameters = new BasicEList<>();
				for (String s : executionContext.getRunConfiguration().getModelInitializationArguments()
						.split("\\r?\\n")) {
					modelInitializationListParameters.add(s);
				}
				initializeMethodParameters.add(modelInitializationListParameters);
			} else {
				initializeMethodParameters
						.add(executionContext.getRunConfiguration().getModelInitializationArguments().split("\\r?\\n"));
			}
		}
	}

	/**
	 * Invoke the initialize method 
	 */
	private void callInitializeModel() {
		try {
			initializeMethod.invoke(null, initializeMethodParameters.toArray());
		} catch (EngineStoppedException stopExeception) {
			// not really an error, simply forward the stop exception
			throw stopExeception;
		} catch (java.lang.reflect.InvocationTargetException ite) {
			// not really an error, simply forward the stop exception
			if (ite.getCause() instanceof EngineStoppedException) {
				throw (EngineStoppedException) ite.getCause();
			} else {
				throw new RuntimeException(ite);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void initializeModel() {
		if(initializeMethod != null){
			StepManagerRegistry.getInstance().registerManager(PlainK3ExecutionEngine.this);
			try {
				final boolean isStepMethod =	initializeMethod.isAnnotationPresent(fr.inria.diverse.k3.al.annotationprocessor.Step.class);
				if(!isStepMethod){
					fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
				    	@Override
				    	public void execute() {
				    		callInitializeModel();
				    	}
				    };
				    fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = PlainK3ExecutionEngine.this;
				    stepManager.executeStep(entryPointMethodParameters.get(0),command,entryPointClass.getName(),initializeMethod.getName());
				} else {
					callInitializeModel();
				}
			} finally {
				StepManagerRegistry.getInstance().unregisterManager(PlainK3ExecutionEngine.this);
			}
		}
	}

	@Override
	protected void executeEntryPoint() {
		StepManagerRegistry.getInstance().registerManager(PlainK3ExecutionEngine.this);
		try {
			// since aspect's methods are static, first arg is null
			entryPointMethod.invoke(null, entryPointMethodParameters.get(0));
		} catch (EngineStoppedException stopExeception) {
			// not really an error, simply forward the stop exception
			throw stopExeception;
		} catch (java.lang.reflect.InvocationTargetException ite) {
			// not really an error, simply forward the stop exception
			if (ite.getCause() instanceof EngineStoppedException) {
				throw (EngineStoppedException) ite.getCause();
			} else {
				throw new RuntimeException(ite);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			StepManagerRegistry.getInstance().unregisterManager(PlainK3ExecutionEngine.this);
		}
	}

	@Override
	/*
	 * This is the operation called from K3 code. We use this callback to pass
	 * the command to the generic executeOperation operation. (non-Javadoc)
	 * 
	 * @see fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager#
	 * executeStep(java.lang.Object,
	 * fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand,
	 * java.lang.String)
	 */
	public void executeStep(Object caller, final StepCommand command, String className, String methodName) {
		executeOperation(caller, className, methodName, new Runnable() {
			@Override
			public void run() {
				command.execute();
			}
		});
	}

	@Override
	/*
	 * This is the operation used to act as a StepManager in K3. We return true
	 * if we have the same editing domain as the object. (non-Javadoc)
	 * 
	 * @see fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager#
	 * canHandle (java.lang.Object)
	 */
	public boolean canHandle(Object caller) {
		if (caller instanceof EObject) {
			EObject eObj = (EObject) caller;
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = getEditingDomain(eObj);
			return editingDomain == this.editingDomain;

		}
		return false;
	}

	/**
	 * Return a bundle containing 'aspectClassName'.
	 * 
	 * Return null if not found.
	 */
	private Bundle findBundle(final IExecutionContext executionContext, String aspectClassName) {

		// first look using JavaWorkspaceScope as this is safer and will look in
		// dependencies
		IType mainIType = getITypeMainByWorkspaceScope(aspectClassName);

		Bundle bundle = null;
		String bundleName = null;
		if (mainIType != null) {
			IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot) mainIType.getPackageFragment()
					.getParent();

			bundleName = packageFragmentRoot.getPath().removeLastSegments(1).lastSegment().toString();
			if (bundleName != null) {

				// First we try to look into an already loaded bundle
				bundle = Platform.getBundle(bundleName);

				// If this doesn't work, we use the provisioner to load
				// the corresponding project
				if (bundle == null) {

					String projectName = mainIType.getJavaProject().getElementName();
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
					if (project != null && project.exists()
							&& !project.getFullPath().equals(executionContext.getWorkspace().getProjectPath())) {
						Provisionner p = new Provisionner();
						IStatus status = p.provisionFromProject(project, null);
						if (!status.isOK()) {
							// return status;
							throw new RuntimeException("Coudln't provision project.");
						}
					}
					bundleName = project.getName();
					bundle = Platform.getBundle(bundleName);
				}
			}
		} else {
			// the main isn't visible directly from the workspace, try another
			// method
			bundle = _executionContext.getMelangeBundle();
		}

		return bundle;
	}

	/**
	 * search the bundle that contains the Main class. The search is done in the
	 * workspace scope (ie. if it is defined in the current workspace it will
	 * find it
	 * 
	 * @return the name of the bundle containing the Main class or null if not
	 *         found
	 */
	private IType getITypeMainByWorkspaceScope(String className) {
		SearchPattern pattern = SearchPattern.createPattern(className, IJavaSearchConstants.CLASS,
				IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		IJavaSearchScope scope = SearchEngine.createWorkspaceScope();

		final List<IType> binaryType = new ArrayList<IType>();

		SearchRequestor requestor = new SearchRequestor() {
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				binaryType.add((IType) match.getElement());
			}
		};
		SearchEngine engine = new SearchEngine();

		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope,
					requestor, null);
		} catch (CoreException e1) {
			throw new RuntimeException("Error while searching the bundle: " + e1.getMessage());
			// return new Status(IStatus.ERROR, Activator.PLUGIN_ID, );
		}

		return binaryType.isEmpty() ? null : binaryType.get(0);
	}

	private static TransactionalEditingDomain getEditingDomain(EObject o) {
		return getEditingDomain(o.eResource().getResourceSet());
	}

	private static InternalTransactionalEditingDomain getEditingDomain(ResourceSet rs) {
		TransactionalEditingDomain edomain = org.eclipse.emf.transaction.TransactionalEditingDomain.Factory.INSTANCE
				.getEditingDomain(rs);
		if (edomain instanceof InternalTransactionalEditingDomain)
			return (InternalTransactionalEditingDomain) edomain;
		else
			return null;
	}

	/**
	 * Load the model for the given URI
	 * @param modelURI to load
	 * @return the loaded resource
	 */
	public static Resource loadModel(URI modelURI) {
		Resource resource = null;
		ResourceSet resourceSet;
		resourceSet = new ResourceSetImpl();
		resource = resourceSet.createResource(modelURI);
		try {
			resource.load(null);
		} catch (IOException e) {
			// chut
		}
		return resource;
	}

	@Override
	public LaunchConfiguration extractLaunchConfiguration() {
		final IRunConfiguration configuration = getExecutionContext().getRunConfiguration();
		final LaunchConfiguration launchConfiguration = LaunchconfigurationFactory.eINSTANCE.createLaunchConfiguration();
		launchConfiguration.setType(LAUNCH_CONFIGURATION_TYPE);
		if (configuration.getLanguageName() != "") {
			final LanguageNameParameter languageNameParam = LaunchconfigurationFactory.eINSTANCE.createLanguageNameParameter();
			languageNameParam.setValue(configuration.getLanguageName());
			launchConfiguration.getParameters().add(languageNameParam);
		}
		final URI modelURI = configuration.getExecutedModelURI();
		if (modelURI != null) {
			final String scheme = modelURI.scheme() + ":/resource";
			final ModelURIParameter modelURIParam = LaunchconfigurationFactory.eINSTANCE.createModelURIParameter();
			modelURIParam.setValue(modelURI.toString().substring(scheme.length()));
			launchConfiguration.getParameters().add(modelURIParam);
		}
		final URI animatorURI = configuration.getAnimatorURI();
		if (configuration.getAnimatorURI() != null) {
			final String scheme = animatorURI.scheme() + ":/resource";
			final AnimatorURIParameter animatorURIParam = LaunchconfigurationFactory.eINSTANCE.createAnimatorURIParameter();
			animatorURIParam.setValue(animatorURI.toString().substring(scheme.length()));
			launchConfiguration.getParameters().add(animatorURIParam);
		}
		if (configuration.getExecutionEntryPoint() != null) {
			final EntryPointParameter entryPointParam = LaunchconfigurationFactory.eINSTANCE.createEntryPointParameter();
			entryPointParam.setValue(configuration.getExecutionEntryPoint());
			launchConfiguration.getParameters().add(entryPointParam);
		}
		if (configuration.getModelEntryPoint() != null) {
			final ModelRootParameter modelRootParam = LaunchconfigurationFactory.eINSTANCE.createModelRootParameter();
			modelRootParam.setValue(configuration.getModelEntryPoint());
			launchConfiguration.getParameters().add(modelRootParam);
		}
		if (configuration.getModelInitializationMethod() != null) {
			final InitializationMethodParameter initializationMethodParam = LaunchconfigurationFactory.eINSTANCE
					.createInitializationMethodParameter();
			initializationMethodParam.setValue(configuration.getModelInitializationMethod());
			launchConfiguration.getParameters().add(initializationMethodParam);
		}
		if (configuration.getModelInitializationArguments() != null) {
			final InitializationArgumentsParameter initializationArgumentsParam = LaunchconfigurationFactory.eINSTANCE
					.createInitializationArgumentsParameter();
			initializationArgumentsParam.setValue(configuration.getModelInitializationArguments());
			launchConfiguration.getParameters().add(initializationArgumentsParam);
		}
		configuration.getEngineAddonExtensions().forEach(extensionPoint -> {
			final AddonExtensionParameter addonExtensionParam = LaunchconfigurationFactory.eINSTANCE.createAddonExtensionParameter();
			addonExtensionParam.setValue(extensionPoint.getName());
			launchConfiguration.getParameters().add(addonExtensionParam);
		});
		return launchConfiguration;
	}
}
