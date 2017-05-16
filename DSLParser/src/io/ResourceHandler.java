package io;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.xtext.example.mydsl.MyDslStandaloneSetup;

import com.google.inject.Injector;

public class ResourceHandler {

	public Injector injector;
	ResourceSet resourceSet = null;

	public ResourceHandler() {
		new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../../../workspace");
		injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	public ResourceSet createResourceSet() {
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		
		return resourceSet;
	}

	/*
	 * uri should start at from workspace
	 */
	public Resource getResourceFrom(String uri) {
		if(resourceSet == null){
			resourceSet = createResourceSet();
		}
		Resource resource = resourceSet.getResource(
				URI.createURI("platform:/resource/Java Standalone Application Test/src/test/" + uri), true);
		return resource;
	}

	public Resource createResourceAt(String uri) {
		if(resourceSet == null){
			resourceSet = createResourceSet();
		}
		Resource resource = resourceSet
				.createResource(URI.createURI("platform:/resource/Java Standalone Application Test/src/test/" + uri));
		return resource;
	}

	public File getFileFrom(String uri) {
		return new File("C://Users/z003rrpp.AD001/workspace/Java Standalone Application Test/src/test/" + uri);
	}
}
