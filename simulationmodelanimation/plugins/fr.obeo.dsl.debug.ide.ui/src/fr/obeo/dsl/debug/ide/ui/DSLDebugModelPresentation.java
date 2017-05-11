/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.dsl.debug.ide.ui;

import fr.obeo.dsl.debug.Variable;
import fr.obeo.dsl.debug.ide.DSLBreakpoint;
import fr.obeo.dsl.debug.ide.adapter.DSLStackFrameAdapter;
import fr.obeo.dsl.debug.ide.adapter.value.DSLObjectValue;
import fr.obeo.dsl.debug.ide.adapter.variable.DSLObjectVariable;
import fr.obeo.dsl.debug.provider.CustomDebugItemProviderAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugEditorPresentation;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * The {@link IDebugModelPresentation} for the DSL debug model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLDebugModelPresentation implements IDebugModelPresentation, IDebugEditorPresentation {

	/**
	 * The {@link Image} cache.
	 */
	protected final Map<ImageDescriptor, Image> imagesCache = new HashMap<ImageDescriptor, Image>();

	/**
	 * The EMF {@link ILabelProvider}.
	 */
	private final ILabelProvider eLabelProvider;

	/**
	 * Constructor.
	 */
	public DSLDebugModelPresentation() {
		super();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		CustomDebugItemProviderAdapterFactory debugFactory = new CustomDebugItemProviderAdapterFactory();
		adapterFactory.addAdapterFactory(debugFactory);
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		eLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		eLabelProvider.addListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		for (Image cachedImage : imagesCache.values()) {
			cachedImage.dispose();
		}
		eLabelProvider.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		final boolean res;

		final Object unwrapped = unwrapp(element);
		if (unwrapped instanceof Variable) {
			res = isLabelProperty(((Variable)unwrapped).getValue(), property);
		} else {
			res = eLabelProvider.isLabelProperty(unwrapp(element), property);
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		eLabelProvider.removeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		return EMFEditorUtils.getEditorInput(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput,
	 *      java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		final String res;

		res = EMFEditorUtils.getEditorID(input, element);

		return res;
	}

	/**
	 * {@inheritDoc} Unused method.
	 *
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#getImage(java.lang.Object)
	 */
	public Image getImage(final Object element) {
		final Image res;

		final Object unwrapped = unwrapp(element);
		if (unwrapped instanceof Variable) {
			res = getImage(((Variable)unwrapped).getValue());
		} else if (element instanceof DSLBreakpoint) {
			final Object image = ((DSLBreakpoint)element).getImage();
			if (image instanceof ComposedImage) {
				((ComposedImage)image).getImages().add(DebugIdeUiPlugin.INSTANCE.getImage(
						"full/deco16/breakpoint_enabled"));
			}
			final ImageDescriptor descriptor = ExtendedImageRegistry.getInstance().getImageDescriptor(image);
			Image cachedImage = imagesCache.get(descriptor);
			if (cachedImage == null) {
				cachedImage = new Image(Display.getDefault(), descriptor.getImageData());
				imagesCache.put(descriptor, cachedImage);
			}
			res = cachedImage;
		} else {
			if (unwrapped != null) {
				synchronized(unwrapped) {
					res = eLabelProvider.getImage(unwrapped);
				}
			} else {
				res = null;
			}
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		final String res;

		final Object unwrapped = unwrapp(element);
		if (unwrapped instanceof Variable) {
			res = getText(((Variable)unwrapped).getValue());
		} else if (element instanceof DSLBreakpoint) {
			res = ((DSLBreakpoint)element).getText();
		} else {
			synchronized(unwrapped) {
				res = eLabelProvider.getText(unwrapped);
			}
		}

		return res;
	}

	/**
	 * {@inheritDoc} Unused method.
	 *
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
	 *      org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
	}

	/**
	 * Unwrap the {@link Adapter#getTarget() target} if the given element is an {@link Adapter}.
	 * 
	 * @param element
	 *            the {@link Object element}
	 * @return the {@link Adapter#getTarget() target} if the given element is an {@link Adapter}, the given
	 *         element itself otherwise
	 */
	private Object unwrapp(Object element) {
		final Object res;

		if (element instanceof DSLObjectVariable) {
			res = ((DSLObjectVariable)element).getObject();
		} else if (element instanceof DSLObjectValue) {
			res = ((DSLObjectValue)element).getValue();
		} else if (element instanceof Adapter) {
			res = ((Adapter)element).getTarget();
		} else {
			res = element;
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugEditorPresentation#addAnnotations(org.eclipse.ui.IEditorPart,
	 *      org.eclipse.debug.core.model.IStackFrame)
	 */
	public boolean addAnnotations(IEditorPart editorPart, IStackFrame frame) {
		if (frame instanceof DSLStackFrameAdapter) {
			final EObject instruction = ((DSLStackFrameAdapter)frame).getCurrentInstruction();
			final URI instructionUri = EcoreUtil.getURI(instruction);
			EMFEditorUtils.selectInstruction(editorPart, instructionUri);
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugEditorPresentation#removeAnnotations(org.eclipse.ui.IEditorPart,
	 *      org.eclipse.debug.core.model.IThread)
	 */
	public void removeAnnotations(IEditorPart editorPart, IThread thread) {
		// nothing to do here
	}

}
