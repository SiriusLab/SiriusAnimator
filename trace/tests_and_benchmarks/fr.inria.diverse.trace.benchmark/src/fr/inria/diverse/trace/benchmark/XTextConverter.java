package fr.inria.diverse.trace.benchmark;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.ISerializer;
import org.gemoc.activitydiagram.sequential.xactivitydiagram.ActivityDiagramStandaloneSetup;
import org.junit.Test;

import com.google.inject.Injector;

public class XTextConverter {

	@Test
	public void test() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());
	    ResourceSet resSet = new ResourceSetImpl();
	    Resource resource = resSet.getResource(URI.createURI("models/hireV4.xmi"), true);
	    XtextResource xtextResource = new XtextResource(URI.createURI("models/hireV4_false.xsad"));
	    try {
			resource.load(Collections.EMPTY_MAP);
			xtextResource.getContents().addAll(resource.getContents());
			Map<String, String> options = new HashMap<>();
			options.put(XtextResource.OPTION_ENCODING, "UTF-8");
			
			Injector injector = new ActivityDiagramStandaloneSetup().createInjectorAndDoEMFRegistration();
			ISerializer serializer = injector.getInstance(ISerializer.class);
			
			xtextResource.setSerializer(serializer);
		    xtextResource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
