package tfsmextended.k3dsa;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.codehaus.groovy.runtime.InvokerInvocationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;


public class GroovyRunner {

	public static final String DSA_PREFIX = "dsa.";
	public static final String GROOVY_SCRIPT_FILE = "groovy.script.file";
	
	public static String absolutePathToGroovyControl;

	public static String getGroovyScriptPathFromReferenceEObjectModelElement(EObject referenceModelElement){
		Properties properties = new Properties();
		String cleanPath = referenceModelElement.eResource().getURI().trimFileExtension().toString().replace("melange", "platform");
		URI cleanURI = URI.createURI(cleanPath.substring(0, cleanPath.indexOf('?')));		
		String propertyPath = cleanURI.toPlatformString(true).toString()+".properties";
		IFile propertyFile = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember(propertyPath);
		if(propertyFile != null && propertyFile.exists()){
			InputStream inStream;
			try {
				inStream = propertyFile.getContents();
			
				properties.load(inStream);
				
				String groovyScript = properties.getProperty(DSA_PREFIX+GROOVY_SCRIPT_FILE);
				String groovyScriptAbsolutePath = groovyScript.startsWith("platform") ? ResourcesPlugin.getWorkspace().getRoot().findMember(URI.createURI(groovyScript).toPlatformString(true)).getLocation().toString() : groovyScript;
				
				return groovyScriptAbsolutePath;
			} catch (Exception e) {
				String errorMessage = e.getClass().getSimpleName() + " when trying to property file referencing the groovy script. "+e.getMessage();
				 Activator.getMessagingSystem().error(errorMessage,
				 Activator.PLUGIN_ID);
				 Activator.getDefault().error(errorMessage, e);
			}
		}
		return null;	
	}
	
	public static Object executeScript(String expression, EObject referenceModelElement){
		return executeScript(expression, getGroovyScriptPathFromReferenceEObjectModelElement(referenceModelElement));
	}
	public static Object executeScript(String expression, String absolutePathToGroovyControl){
		if(absolutePathToGroovyControl == null) return null;
		try {
			GroovyObject groovyObj;
			// use this class class loader, this isn't perfect since the script may use more classe, but this is a start ...
			GroovyClassLoader gcl = new GroovyClassLoader(GroovyRunner.class.getClassLoader());
			Class<?> clazz = null;

			clazz = gcl.parseClass(new File(absolutePathToGroovyControl));

			groovyObj = (GroovyObject) clazz.newInstance();
			Object r = groovyObj.invokeMethod(expression, new Object[] {});
			return r;
		} catch (Exception e) {
			String errorMessage = e.getClass().getSimpleName() + " when trying to execute a Groovy expression";
			 Activator.getMessagingSystem().error(errorMessage,
			 Activator.PLUGIN_ID);
			 Activator.getDefault().error(errorMessage, e);
		}
		return null;
	}

}
