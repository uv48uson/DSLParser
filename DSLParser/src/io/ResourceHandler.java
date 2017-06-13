package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.xtext.example.mydsl.MyDslStandaloneSetup;

import com.google.inject.Injector;

public class ResourceHandler {

	private Injector injector;
	private ResourceSet resourceSet;

	public ResourceHandler() {
		new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../");
		injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		this.resourceSet = resourceSet;
	}

	public Map<Object, Object> getLoadOptions() {
		return resourceSet.getLoadOptions();
	}

	public Resource getResourceFrom(File file, boolean loadOnDemand) throws FileNotFoundException {
		Path path = file.toPath();
		URI uri = URI.createFileURI(path.normalize().toAbsolutePath().toString());
		if (!file.exists()) {
			if (loadOnDemand) {
				Resource resource = resourceSet.createResource(uri);
				return resource;
			}
			throw new FileNotFoundException();
		} else {
			Resource resource = resourceSet.getResource(uri, true);
			return resource;
		}
	}
	
	public List<Resource> getResources(){
		return resourceSet.getResources();
	}
}
