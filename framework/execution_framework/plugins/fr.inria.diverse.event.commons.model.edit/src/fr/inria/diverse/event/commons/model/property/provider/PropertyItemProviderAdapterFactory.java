/**
 */
package fr.inria.diverse.event.commons.model.property.provider;

import fr.inria.diverse.event.commons.model.property.util.PropertyAdapterFactory;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyItemProviderAdapterFactory extends PropertyAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link fr.inria.diverse.event.commons.model.property.ContainerReferenceProperty} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainerReferencePropertyItemProvider containerReferencePropertyItemProvider;

	/**
	 * This creates an adapter for a {@link fr.inria.diverse.event.commons.model.property.ContainerReferenceProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createContainerReferencePropertyAdapter() {
		if (containerReferencePropertyItemProvider == null) {
			containerReferencePropertyItemProvider = new ContainerReferencePropertyItemProvider(this);
		}

		return containerReferencePropertyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link fr.inria.diverse.event.commons.model.property.ManyBooleanAttributeProperty} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManyBooleanAttributePropertyItemProvider manyBooleanAttributePropertyItemProvider;

	/**
	 * This creates an adapter for a {@link fr.inria.diverse.event.commons.model.property.ManyBooleanAttributeProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createManyBooleanAttributePropertyAdapter() {
		if (manyBooleanAttributePropertyItemProvider == null) {
			manyBooleanAttributePropertyItemProvider = new ManyBooleanAttributePropertyItemProvider(this);
		}

		return manyBooleanAttributePropertyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link fr.inria.diverse.event.commons.model.property.ManyIntegerAttributeProperty} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManyIntegerAttributePropertyItemProvider manyIntegerAttributePropertyItemProvider;

	/**
	 * This creates an adapter for a {@link fr.inria.diverse.event.commons.model.property.ManyIntegerAttributeProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createManyIntegerAttributePropertyAdapter() {
		if (manyIntegerAttributePropertyItemProvider == null) {
			manyIntegerAttributePropertyItemProvider = new ManyIntegerAttributePropertyItemProvider(this);
		}

		return manyIntegerAttributePropertyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link fr.inria.diverse.event.commons.model.property.ManyStringAttributeProperty} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManyStringAttributePropertyItemProvider manyStringAttributePropertyItemProvider;

	/**
	 * This creates an adapter for a {@link fr.inria.diverse.event.commons.model.property.ManyStringAttributeProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createManyStringAttributePropertyAdapter() {
		if (manyStringAttributePropertyItemProvider == null) {
			manyStringAttributePropertyItemProvider = new ManyStringAttributePropertyItemProvider(this);
		}

		return manyStringAttributePropertyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link fr.inria.diverse.event.commons.model.property.StepProperty} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StepPropertyItemProvider stepPropertyItemProvider;

	/**
	 * This creates an adapter for a {@link fr.inria.diverse.event.commons.model.property.StepProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createStepPropertyAdapter() {
		if (stepPropertyItemProvider == null) {
			stepPropertyItemProvider = new StepPropertyItemProvider(this);
		}

		return stepPropertyItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (containerReferencePropertyItemProvider != null) containerReferencePropertyItemProvider.dispose();
		if (manyBooleanAttributePropertyItemProvider != null) manyBooleanAttributePropertyItemProvider.dispose();
		if (manyIntegerAttributePropertyItemProvider != null) manyIntegerAttributePropertyItemProvider.dispose();
		if (manyStringAttributePropertyItemProvider != null) manyStringAttributePropertyItemProvider.dispose();
		if (stepPropertyItemProvider != null) stepPropertyItemProvider.dispose();
	}

}
