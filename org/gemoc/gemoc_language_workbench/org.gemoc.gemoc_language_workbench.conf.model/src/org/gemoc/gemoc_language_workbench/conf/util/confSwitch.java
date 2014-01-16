/**
 */
package org.gemoc.gemoc_language_workbench.conf.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.gemoc.gemoc_language_workbench.conf.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.gemoc.gemoc_language_workbench.conf.confPackage
 * @generated
 */
public class confSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static confPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public confSwitch() {
		if (modelPackage == null) {
			modelPackage = confPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case confPackage.GEMOC_LANGUAGE_WORKBENCH_CONFIGURATION: {
				GemocLanguageWorkbenchConfiguration gemocLanguageWorkbenchConfiguration = (GemocLanguageWorkbenchConfiguration)theEObject;
				T result = caseGemocLanguageWorkbenchConfiguration(gemocLanguageWorkbenchConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.PROJECT_RESOURCE: {
				ProjectResource projectResource = (ProjectResource)theEObject;
				T result = caseProjectResource(projectResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.DSA_PROJECT: {
				DSAProject dsaProject = (DSAProject)theEObject;
				T result = caseDSAProject(dsaProject);
				if (result == null) result = caseProjectResource(dsaProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.KERMETA2_DSA_PROJECT: {
				Kermeta2DSAProject kermeta2DSAProject = (Kermeta2DSAProject)theEObject;
				T result = caseKermeta2DSAProject(kermeta2DSAProject);
				if (result == null) result = caseDSAProject(kermeta2DSAProject);
				if (result == null) result = caseProjectResource(kermeta2DSAProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.LANGUAGE_DEFINITION: {
				LanguageDefinition languageDefinition = (LanguageDefinition)theEObject;
				T result = caseLanguageDefinition(languageDefinition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.EDITOR_PROJECT: {
				EditorProject editorProject = (EditorProject)theEObject;
				T result = caseEditorProject(editorProject);
				if (result == null) result = caseProjectResource(editorProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.DOMAIN_MODEL_PROJECT: {
				DomainModelProject domainModelProject = (DomainModelProject)theEObject;
				T result = caseDomainModelProject(domainModelProject);
				if (result == null) result = caseProjectResource(domainModelProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.MO_CPROJECT: {
				MoCProject moCProject = (MoCProject)theEObject;
				T result = caseMoCProject(moCProject);
				if (result == null) result = caseProjectResource(moCProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.CCSL_MO_CPROJECT: {
				CCSLMoCProject ccslMoCProject = (CCSLMoCProject)theEObject;
				T result = caseCCSLMoCProject(ccslMoCProject);
				if (result == null) result = caseMoCProject(ccslMoCProject);
				if (result == null) result = caseProjectResource(ccslMoCProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.ANIMATOR_PROJECT: {
				AnimatorProject animatorProject = (AnimatorProject)theEObject;
				T result = caseAnimatorProject(animatorProject);
				if (result == null) result = caseProjectResource(animatorProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.EMF_ECORE_PROJECT: {
				EMFEcoreProject emfEcoreProject = (EMFEcoreProject)theEObject;
				T result = caseEMFEcoreProject(emfEcoreProject);
				if (result == null) result = caseDomainModelProject(emfEcoreProject);
				if (result == null) result = caseProjectResource(emfEcoreProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.OD_PROJECT: {
				ODProject odProject = (ODProject)theEObject;
				T result = caseODProject(odProject);
				if (result == null) result = caseEditorProject(odProject);
				if (result == null) result = caseProjectResource(odProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.SIRIUS_ANIMATOR_PROJECT: {
				SiriusAnimatorProject siriusAnimatorProject = (SiriusAnimatorProject)theEObject;
				T result = caseSiriusAnimatorProject(siriusAnimatorProject);
				if (result == null) result = caseAnimatorProject(siriusAnimatorProject);
				if (result == null) result = caseProjectResource(siriusAnimatorProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.MOD_HEL_XMO_CPROJECT: {
				ModHelXMoCProject modHelXMoCProject = (ModHelXMoCProject)theEObject;
				T result = caseModHelXMoCProject(modHelXMoCProject);
				if (result == null) result = caseMoCProject(modHelXMoCProject);
				if (result == null) result = caseProjectResource(modHelXMoCProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.DSE_PROJECT: {
				DSEProject dseProject = (DSEProject)theEObject;
				T result = caseDSEProject(dseProject);
				if (result == null) result = caseProjectResource(dseProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.ECL_PROJECT: {
				ECLProject eclProject = (ECLProject)theEObject;
				T result = caseECLProject(eclProject);
				if (result == null) result = caseDSEProject(eclProject);
				if (result == null) result = caseProjectResource(eclProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.TREE_EDITOR_PROJECT: {
				TreeEditorProject treeEditorProject = (TreeEditorProject)theEObject;
				T result = caseTreeEditorProject(treeEditorProject);
				if (result == null) result = caseEditorProject(treeEditorProject);
				if (result == null) result = caseProjectResource(treeEditorProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.EMF_GENMODEL: {
				EMFGenmodel emfGenmodel = (EMFGenmodel)theEObject;
				T result = caseEMFGenmodel(emfGenmodel);
				if (result == null) result = caseFileResource(emfGenmodel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.FILE_RESOURCE: {
				FileResource fileResource = (FileResource)theEObject;
				T result = caseFileResource(fileResource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.XTEXT_EDITOR_PROJECT: {
				XTextEditorProject xTextEditorProject = (XTextEditorProject)theEObject;
				T result = caseXTextEditorProject(xTextEditorProject);
				if (result == null) result = caseEditorProject(xTextEditorProject);
				if (result == null) result = caseProjectResource(xTextEditorProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.K3DSA_PROJECT: {
				K3DSAProject k3DSAProject = (K3DSAProject)theEObject;
				T result = caseK3DSAProject(k3DSAProject);
				if (result == null) result = caseDSAProject(k3DSAProject);
				if (result == null) result = caseProjectResource(k3DSAProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case confPackage.ECL_FILE: {
				ECLFile eclFile = (ECLFile)theEObject;
				T result = caseECLFile(eclFile);
				if (result == null) result = caseFileResource(eclFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Gemoc Language Workbench Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Gemoc Language Workbench Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGemocLanguageWorkbenchConfiguration(GemocLanguageWorkbenchConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Project Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProjectResource(ProjectResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DSA Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DSA Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDSAProject(DSAProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Kermeta2 DSA Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Kermeta2 DSA Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKermeta2DSAProject(Kermeta2DSAProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Language Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Language Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLanguageDefinition(LanguageDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Editor Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Editor Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditorProject(EditorProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Model Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Model Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainModelProject(DomainModelProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mo CProject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mo CProject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMoCProject(MoCProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CCSL Mo CProject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CCSL Mo CProject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCCSLMoCProject(CCSLMoCProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Animator Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Animator Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnimatorProject(AnimatorProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EMF Ecore Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EMF Ecore Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEMFEcoreProject(EMFEcoreProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>OD Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>OD Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseODProject(ODProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sirius Animator Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sirius Animator Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSiriusAnimatorProject(SiriusAnimatorProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mod Hel XMo CProject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mod Hel XMo CProject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModHelXMoCProject(ModHelXMoCProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DSE Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DSE Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDSEProject(DSEProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ECL Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ECL Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECLProject(ECLProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tree Editor Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tree Editor Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTreeEditorProject(TreeEditorProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EMF Genmodel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EMF Genmodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEMFGenmodel(EMFGenmodel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileResource(FileResource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>XText Editor Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>XText Editor Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXTextEditorProject(XTextEditorProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>K3DSA Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>K3DSA Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseK3DSAProject(K3DSAProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ECL File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ECL File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseECLFile(ECLFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //confSwitch