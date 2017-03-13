/**
 */
package fr.inria.diverse.trace.commons.model.launchconfiguration.util;

import fr.inria.diverse.trace.commons.model.launchconfiguration.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchconfigurationPackage
 * @generated
 */
public class LaunchconfigurationAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LaunchconfigurationPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaunchconfigurationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = LaunchconfigurationPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LaunchconfigurationSwitch<Adapter> modelSwitch =
		new LaunchconfigurationSwitch<Adapter>() {
			@Override
			public Adapter caseLaunchConfiguration(LaunchConfiguration object) {
				return createLaunchConfigurationAdapter();
			}
			@Override
			public Adapter caseLaunchConfigurationParameter(LaunchConfigurationParameter object) {
				return createLaunchConfigurationParameterAdapter();
			}
			@Override
			public Adapter caseLanguageNameParameter(LanguageNameParameter object) {
				return createLanguageNameParameterAdapter();
			}
			@Override
			public Adapter caseAddonExtensionParameter(AddonExtensionParameter object) {
				return createAddonExtensionParameterAdapter();
			}
			@Override
			public Adapter caseModelURIParameter(ModelURIParameter object) {
				return createModelURIParameterAdapter();
			}
			@Override
			public Adapter caseAnimatorURIParameter(AnimatorURIParameter object) {
				return createAnimatorURIParameterAdapter();
			}
			@Override
			public Adapter caseEntryPointParameter(EntryPointParameter object) {
				return createEntryPointParameterAdapter();
			}
			@Override
			public Adapter caseInitializationArgumentsParameter(InitializationArgumentsParameter object) {
				return createInitializationArgumentsParameterAdapter();
			}
			@Override
			public Adapter caseModelRootParameter(ModelRootParameter object) {
				return createModelRootParameterAdapter();
			}
			@Override
			public Adapter caseInitializationMethodParameter(InitializationMethodParameter object) {
				return createInitializationMethodParameterAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration <em>Launch Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfiguration
	 * @generated
	 */
	public Adapter createLaunchConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter <em>Launch Configuration Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LaunchConfigurationParameter
	 * @generated
	 */
	public Adapter createLaunchConfigurationParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter <em>Language Name Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.LanguageNameParameter
	 * @generated
	 */
	public Adapter createLanguageNameParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter <em>Addon Extension Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.AddonExtensionParameter
	 * @generated
	 */
	public Adapter createAddonExtensionParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter <em>Model URI Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.ModelURIParameter
	 * @generated
	 */
	public Adapter createModelURIParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter <em>Animator URI Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.AnimatorURIParameter
	 * @generated
	 */
	public Adapter createAnimatorURIParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter <em>Entry Point Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.EntryPointParameter
	 * @generated
	 */
	public Adapter createEntryPointParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter <em>Initialization Arguments Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationArgumentsParameter
	 * @generated
	 */
	public Adapter createInitializationArgumentsParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter <em>Model Root Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.ModelRootParameter
	 * @generated
	 */
	public Adapter createModelRootParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter <em>Initialization Method Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see fr.inria.diverse.trace.commons.model.launchconfiguration.InitializationMethodParameter
	 * @generated
	 */
	public Adapter createInitializationMethodParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //LaunchconfigurationAdapterFactory
