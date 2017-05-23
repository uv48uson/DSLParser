package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

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
	private static final String RESOURCE_DIRECTORY_URL_FRAGMENT = "/resources/";

	public ResourceHandler() {
		new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../");
		injector = new MyDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		this.resourceSet = resourceSet;
		openAvailableResources();
	}

	public Map<Object, Object> getLoadOptions() {
		return resourceSet.getLoadOptions();
	}

	public void openAvailableResources() {
		java.net.URI directoryURL;

		try {
			directoryURL = ResourceHandler.class.getResource(RESOURCE_DIRECTORY_URL_FRAGMENT).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}

		Path myPath;
		if (directoryURL.getScheme().equals("jar")) {
			FileSystem fileSystem;
			try {
				fileSystem = FileSystems.newFileSystem(directoryURL, Collections.<String, Object>emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			myPath = fileSystem.getPath(RESOURCE_DIRECTORY_URL_FRAGMENT);
		} else {
			myPath = Paths.get(directoryURL);
		}

		Stream<Path> walk;
		try {
			walk = Files.walk(myPath, 1);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		ArrayList<Path> paths = new ArrayList<>();
		walk.forEach(paths::add);
		walk.close();

		for (Path path : paths) {
			if (path.toString().contains(".mydsl")) {
				try {
					getResourceFrom(path.getFileName().toString(), false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Resource getResourceFrom(String uri, boolean loadOnDemand) throws FileNotFoundException {
		URL fileURL;

		fileURL = ResourceHandler.class.getResource(RESOURCE_DIRECTORY_URL_FRAGMENT + uri);
		if (fileURL == null) {
			if (loadOnDemand) {
				return createResource(uri);
			}
			throw new FileNotFoundException(uri);
		}

		URI fileURI = URI.createFileURI(fileURL.getPath());
		Resource resource = resourceSet.getResource(fileURI, true);
		return resource;
	}

	private Resource createResource(String uri) {
		URL directoryURL = ResourceHandler.class.getResource(RESOURCE_DIRECTORY_URL_FRAGMENT);
		URI fileURI = URI.createFileURI(directoryURL.getPath() + uri);
		Resource resource = resourceSet.createResource(fileURI);
		return resource;
	}
}
