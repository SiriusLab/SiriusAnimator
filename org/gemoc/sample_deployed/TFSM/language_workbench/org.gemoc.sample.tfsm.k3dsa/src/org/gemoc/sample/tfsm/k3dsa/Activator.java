package org.gemoc.sample.tfsm.k3dsa;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import fr.inria.diverse.commons.messagingsystem.api.MessagingSystem;
import fr.inria.diverse.commons.messagingsystem.api.impl.StdioSimpleMessagingSystem;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.gemoc.execution.engine"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	protected static MessagingSystem messagingSystem = null;

	public static MessagingSystem getMessagingSystem() {
		if (messagingSystem == null) {
			messagingSystem = new StdioSimpleMessagingSystem();
			//messagingSystem = new EclipseMessagingSystem(PLUGIN_ID,
			//		"GEMOC Execution Engine");
			//((EclipseMessagingSystem) messagingSystem)
			//		.setConsoleLogLevel(ConsoleLogLevel.DEV_DEBUG);
		}
		return messagingSystem;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return Activator.plugin;
	}

//	/**
//	 * Returns an image descriptor for the image file at the given plug-in
//	 * relative path
//	 * 
//	 * @param path
//	 *            the path
//	 * @return the image descriptor
//	 */
//	public static ImageDescriptor getImageDescriptor(String path) {
//		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
//				path);
//	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Activator.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		Activator.plugin = null;
		super.stop(context);
	}


	
	public static void warn(String msg, Throwable e) {
		Activator.getDefault().getLog()
				.log(new Status(Status.WARNING, PLUGIN_ID, Status.OK, msg, e));
	}

	public static void error(String msg, Throwable e) {
		Activator.getDefault().getLog()
				.log(new Status(Status.ERROR, PLUGIN_ID, Status.OK, msg, e));
	}
	
	
}