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
package fr.inria.diverse.trace.plugin.generator.clean

import fr.inria.diverse.trace.plugin.generator.AbstractEMFProjectGenerator
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.jar.Manifest
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.emf.codegen.ecore.generator.Generator
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage
import org.eclipse.emf.codegen.ecore.genmodel.GenParameter
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil
import org.eclipse.emf.codegen.util.CodeGenUtil
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.util.UniqueEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EParameter
import org.eclipse.emf.ecore.EStructuralFeature.Setting
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.plugin.EcorePlugin
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

public class StandaloneEMFProjectGenerator extends AbstractEMFProjectGenerator {

	public static val String MODEL_GEN_FOLDER = "model"

	protected IProgressMonitor progressMonitor

	protected ResourceSet resourceSet

	protected URI modelGenFolderURI
	protected String srcFolderPathString

	protected Resource ecoreModelResource

	new(String projectName, EPackage p) {
		super(projectName, p)
	}

	override generateBaseEMFProject(IProgressMonitor m) {

		this.progressMonitor = m;
		this.resourceSet = new ResourceSetImpl

		// Create new EMF project
		this.project = PluginProjectHelper.createPluginProject(projectName, // Project name
		#["src"], // Src folders
		#[], // referenced projects
		#{}, // required bundles (plugins) TODO
		#[], // exported packages TODO
		m)

		// setup model-gen folder for saving temporary models
		this.modelGenFolderURI = setupModelGenFolder();

		// setup src folder for model code generation
		this.srcFolderPathString = setupSrcFolder();

		// Serialize epackage in the project
		ecoreModelResource = resourceSet.createResource(
			URI.createPlatformResourceURI('''«projectName»/«MODEL_GEN_FOLDER»/«ecoreModel.name».ecore''', true))
		ecoreModelResource.contents.add(ecoreModel)
		ecoreModelResource.save

		ecoreModelResource.unload
		ecoreModelResource.load(null)

		ecoreModelResource.unload
		ecoreModelResource.load(null)
		
		// Check that all required ecore models are available 
		checkReferencedPackages(ecoreModelResource);

		// Creates the output gen model
		this.genModel = generateGenModel((ecoreModelResource.getContents().get(0)) as EPackage, modelGenFolderURI);
		this.rootPackages.addAll(ecoreModelResource.contents.filter(EPackage).toSet)
	}

	override generateModelCode(IProgressMonitor m) {
		generateCode(progressMonitor);
	}

	private def URI setupModelGenFolder() {
		var URI modelGenFolderURI = null;
		val IFolder modelGenFolder = project.getFolder(MODEL_GEN_FOLDER);
		if (!modelGenFolder.exists()) {
			try {
				modelGenFolder.create(true, true, null);
			} catch (CoreException e) {
				throw new RuntimeException('''The folder '«MODEL_GEN_FOLDER»' could not be created.''', e);
			}
		}
		modelGenFolderURI = URI.createPlatformResourceURI(modelGenFolder.getFullPath().toString(), true);
		return modelGenFolderURI;
	}

	private def String setupSrcFolder() {
		var String srcFolderPathString = null;
		val IFolder srcFolder = project.getFolder("src");
		if (!srcFolder.exists()) {
			try {
				srcFolder.create(true, true, null);
			} catch (CoreException e) {
				throw new RuntimeException("The source folder \'src\' could not be created.", e);
			}
		}
		srcFolderPathString = srcFolder.getFullPath().toString();
		return srcFolderPathString;
	}

	private def void checkReferencedPackages(Resource xmofModelResource) {
		val Set<URI> missingPackages = new HashSet<URI>();
		val Map<EObject, Collection<Setting>> externalCrossReferences = EcoreUtil.ExternalCrossReferencer.find(
			xmofModelResource);
		for (EObject eObject : externalCrossReferences.keySet()) {
			if (eObject.eIsProxy()) {
				missingPackages.add(EcoreUtil.getURI(eObject).trimFragment());
			}
		}

		if (missingPackages.size() > 0) {

			val String message = "Unable to load the following referenced resource" +
				(if(missingPackages.size() == 1) "" else "s") + ": " + missingPackages.toString()

			throw new RuntimeException(message);
		}
	}

	protected def GenModel generateGenModel(EPackage rootEPackage, URI modelGenFolderURI) {
		val Resource genModelResource = createGenModel(rootEPackage);
		val GenModel genModel = genModelResource.getContents().get(0) as GenModel;
		setInitializeByLoad(genModel);
		save(genModelResource);
		return genModel;
	}

	protected def Resource createGenModel(EPackage rootEPackage) {
		val Resource ecoreModelResource = rootEPackage.eResource();
		val String genModelFileName = ecoreModelResource.getURI().trimFileExtension().appendFileExtension("genmodel").
			lastSegment().toString();
		val URI genModelURI = this.modelGenFolderURI.appendSegment(genModelFileName);

		val Resource genModelResource = resourceSet.createResource(genModelURI);
		val GenModel genModel = GenModelFactory.eINSTANCE.createGenModel();
		genModelResource.getContents().add(genModel);

		val IFolder srcFolder = project.getFolder("src");
		genModel.setModelDirectory(srcFolder.getFullPath().toString());
		genModel.getForeignModel().add(ecoreModelResource.getURI().toString());
		genModel.setModelName(getModelName(genModelURI));
		genModel.setModelPluginID(getPluginID(genModelURI));

		// genModel.setOperationReflection(true);
		// genModel.setMinimalReflectiveMethods(true);
		genModel.setRootExtendsClass("org.eclipse.emf.ecore.impl.MinimalEObjectImpl$Container");
		genModel.setComplianceLevel(getComplicanceLevel());
		genModel.setImportOrganizing(true);

		genModel.initialize(Collections.singleton(rootEPackage));
		setMissingParameterTypes(genModel);

		val List<GenPackage> missingGenPackages = computeMissingGenPackages(genModel);
		referencedGenPackages.addAll(missingGenPackages)
		referencedGenPackages.addAll(genModel.genPackages)
		genModel.getUsedGenPackages().addAll(missingGenPackages);

		return genModelResource;
	}

	protected def String getModelName(URI genModelURI) {
		val String genModelFileName = genModelURI.trimFileExtension().lastSegment();
		val String modelName = genModelFileName.substring(0, 1).toUpperCase() + genModelFileName.substring(1);
		return modelName;
	}

	protected def String getPluginID(URI uri) {
		var String pluginID = "";
		val IFile manifestFile = project.getFolder("META-INF").getFile("MANIFEST.MF");
		try {
			val Manifest manifest = new Manifest(manifestFile.getContents());
			var String symbolicName = manifest.getMainAttributes().getValue("Bundle-SymbolicName");
			if (symbolicName != null) {
				val int index = symbolicName.indexOf(";");
				if (index > 0) {
					symbolicName = symbolicName.substring(0, index);
				}
				pluginID = symbolicName.trim();
			}
		} catch (Exception e) {
			throw new RuntimeException(
				"Could not find manifest file \'" + manifestFile.getFullPath().toString() + "\'.", e);
		}
		return pluginID;
	}

	private def GenJDKLevel getComplicanceLevel() {
		val String complianceLevel = CodeGenUtil.EclipseUtil.getJavaComplianceLevel(project);
		if ("1.4".equals(complianceLevel)) {
			return GenJDKLevel.JDK14_LITERAL;
		} else if ("1.5".equals(complianceLevel)) {
			return GenJDKLevel.JDK50_LITERAL;
		} else if ("1.6".equals(complianceLevel)) {
			return GenJDKLevel.JDK60_LITERAL;
		} else if ("1.7".equals(complianceLevel)) {
			return GenJDKLevel.JDK70_LITERAL;
		} else {
			return GenJDKLevel.JDK80_LITERAL;
		}
	}

	/**
	 * In case of missing parameter types, the types are temporarily set to
	 * EObject
	 * 
	 * @param genModel
	 */
	private def void setMissingParameterTypes(GenModel genModel) {
		for (genModelElement : genModel.eAllContents().filter(GenParameter).toSet) {
			val GenParameter genParameter = genModelElement as GenParameter;
			val EParameter ecoreParameter = genParameter.getEcoreParameter();
			if (ecoreParameter.getEType() == null) {
				ecoreParameter.setEType(EcorePackage.eINSTANCE.getEObject());
			}
		}
	}

	protected def List<GenPackage> computeMissingGenPackages(GenModel genModel) {
		val List<GenPackage> missingGenPackages = new UniqueEList<GenPackage>();
		val Map<String, URI> genModelLocationMapTargetEnvironment = EcorePlugin.
			getEPackageNsURIToGenModelLocationMap(true);
		val Map<String, URI> genModelLocationMapEnvironment = EcorePlugin.getEPackageNsURIToGenModelLocationMap(false);
		for (EPackage ePackage : genModel.getMissingPackages()) {
			if (ePackage != null) { // happens for activities
				var URI missingGenModelURI = genModelLocationMapEnvironment.get(ePackage.getNsURI());
				if (missingGenModelURI == null) {
					missingGenModelURI = genModelLocationMapTargetEnvironment.get(ePackage.getNsURI());
				}
				if (missingGenModelURI == null) {
					throw new RuntimeException(
						"Unable to load generator model of required package \'" + ePackage.getNsURI() + "\'.");
				}
				var Resource missingGenModelResource = null;
				try {
					missingGenModelResource = resourceSet.getResource(missingGenModelURI, true);
				} catch (RuntimeException e) {
					throw new RuntimeException(
						"Unable to load generator model of required package \'" + ePackage.getNsURI() + "\'.");
				}
				val GenModel missingGenModel = missingGenModelResource.getContents().get(0) as GenModel;
				missingGenPackages.addAll(missingGenModel.getGenPackages());
			}
		}
		return missingGenPackages;
	}

	protected def void setInitializeByLoad(GenModel genModel) {
		for (GenPackage genPackage : genModel.getGenPackages()) {
			setInitializeByLoad(genPackage);
		}
	}

	private def void setInitializeByLoad(GenPackage genPackage) {
		genPackage.setLoadInitialization(false);
		for (GenPackage subGenPackage : genPackage.getSubGenPackages()) {
			setInitializeByLoad(subGenPackage);
		}
	}

	private def List<Diagnostic> expandDiagnostics(Diagnostic d) {
		val result = new ArrayList<Diagnostic>
		result.add(d)
		result.addAll(d.children)
		for (c : d.children) {
			result.addAll(expandDiagnostics(c))
		}
		return result
	}

	private def String exceptionToStackString(Throwable e) {
		val StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		val String exceptionAsString = sw.toString()
		return exceptionAsString
	}

	private def String diagnosticErrorsToString(Diagnostic diagnostic) {

		val errors = expandDiagnostics(diagnostic).filter[d|d.severity == Diagnostic.ERROR].toSet
		val exceptions = errors.filter[e|e.exception != null].map[e|exceptionToStackString(e.exception)].toSet
		return '''
			«FOR e : exceptions BEFORE "Encountered exceptions:\n" SEPARATOR "\n" AFTER "\n"»
				- «e»
			«ENDFOR»
			«FOR e : errors BEFORE "Encountered diagnostic errors:\n" SEPARATOR "\n" AFTER "\n"»
				- «e.message»
			«ENDFOR»
		'''
	}

	protected def void generateCode(IProgressMonitor progressMonitor) throws Exception {
		var boolean success = false;
		prepareGenModelForCodeGeneration(genModel);
		val Generator generator = GenModelUtil.createGenerator(genModel);
		val boolean canGenerate = generator.canGenerate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE);
		var String message = '''Cannot generate code of EPackage «this.ecoreModel.name»'''
		if (canGenerate) {
			val Diagnostic diagnostic = generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
				BasicMonitor.toMonitor(progressMonitor));
			if (diagnostic.getSeverity() == Diagnostic.OK) {
				success = true;
			} else {
				message += ''': «diagnosticErrorsToString(diagnostic)».'''
			}
		} else {
			message += "generator.canGenerate returns false."
		}
		if (!success)
			throw new Exception(message)
	}

	protected def void prepareGenModelForCodeGeneration(GenModel genModel) {
		genModel.reconcile();
		genModel.setCanGenerate(true);
	}

	private def void save(Resource resource) {
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			throw new RuntimeException("Could not save resource \'" + resource.getURI() + "\'.", e);
		}
	}

}
