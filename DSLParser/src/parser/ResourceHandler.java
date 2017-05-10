package parser;



import org.xtext.example.mydsl.MyDslStandaloneSetup;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

class ResourceHandler {

	public Injector injector;

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
		ResourceSet resourceSet = createResourceSet();
		Resource resource = resourceSet.getResource(
				URI.createURI("platform:/resource/Java Standalone Application Test/src/test/" + uri), true);
		return resource;
	}

	public Resource createResourceAt(String uri) {
		ResourceSet resourceSet = createResourceSet();
		Resource resource = resourceSet
				.createResource(URI.createURI("platform:/resource/Java Standalone Application Test/src/test/" + uri));
		return resource;
	}
}
