package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javafx.util.Pair;
import logging.TableContentLayout;
import parser.MydslParsingException;

public class FileIO {
	private String fileName;
	private ResourceHandler resourceHandler = new ResourceHandler();
	private static Logger log = Logger.getLogger(FileIO.class);

	public static void init() {
		SimpleLayout layout = new TableContentLayout();
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		log.removeAllAppenders();
		log.setAdditivity(false);
		log.addAppender(consoleAppender);
	}

	public FileIO(String fileName) {
		this.fileName = fileName;
	}

	public Document readDocumentFromFile() throws IOException {
		log.info(new Pair<String, String>("File:" + fileName + ".xml", "Document"));

		Resource resourceOutput = resourceHandler.getResourceFrom(fileName + ".xml", false);
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		return ((XMLResource) resourceOutput).save(null, options, null);
	}

	public EObject readModelFromFile(String fileType) throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String, String>("File:" + fileName + "." + fileType, "Model"));

		Resource resourceInput = resourceHandler.getResourceFrom(fileName + "." + fileType, false);
		checkForResourceErrors(fileType, resourceInput);
		try {
			resourceInput.load(resourceHandler.getLoadOptions());
		} catch (IOException e) {
			resourceInput.delete(Collections.EMPTY_MAP);
			throw e;
		}
		return resourceInput.getContents().get(0);
	}

	private void checkForResourceErrors(String fileType, Resource resourceInput)
			throws MydslParsingException, SAXException, IOException {
		EList<Diagnostic> errors = resourceInput.getErrors();
		for (Diagnostic diag : errors) {
			if (fileType.equals("mydsl")) {
				resourceInput.delete(Collections.EMPTY_MAP);
				throw new MydslParsingException(diag.getMessage());
			} else {
				resourceInput.delete(Collections.EMPTY_MAP);
				throw new SAXException();
			}
		}
	}

	public InputStream readInputStreamFromFile(String fileType)
			throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String, String>("File:" + fileName + "." + fileType, "InputStream"));

		Resource resourceOutput = resourceHandler.getResourceFrom(fileName + "." + fileType, false);
		checkForResourceErrors(fileType, resourceOutput);
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			resourceOutput.save(out, Collections.EMPTY_MAP);

			ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

			return inputStream;
		} catch (IOException e) {
			resourceOutput.delete(Collections.EMPTY_MAP);
			throw e;
		}

	}

	public void writeStreamToFile(String fileType, InputStream inputStream)
			throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String, String>("InputStream", "File:" + fileName + "." + fileType));

		Resource resourceOutput;
		resourceOutput = resourceHandler.getResourceFrom(fileName + "." + fileType, true);

		try {
			resourceOutput.load(inputStream, resourceHandler.getLoadOptions());
			checkForResourceErrors(fileType, resourceOutput);
			resourceOutput.save(Collections.EMPTY_MAP);
			checkForResourceErrors(fileType, resourceOutput);
		} catch (IOException e) {
			resourceOutput.delete(Collections.EMPTY_MAP);
			throw e;
		}

	}

	public void writeModelToFile(String fileType, EObject model)
			throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String, String>("Model", "File:" + fileName + "." + fileType));

		Resource resourceOutput = resourceHandler.getResourceFrom(fileName + "." + fileType, true);
		checkForResourceErrors(fileType, resourceOutput);
		resourceOutput.getContents().clear();
		resourceOutput.getContents().add(model);
		try {
			resourceOutput.save(Collections.EMPTY_MAP);
			checkForResourceErrors(fileType, resourceOutput);
		} catch (IOException e) {
			resourceOutput.delete(Collections.EMPTY_MAP);
			throw e;
		}
	}

	public void writeDocumentToFile(String fileType, Document doc)
			throws IOException, MydslParsingException, SAXException {
		InputStream inputStream = transformDocumentToStream(doc);
		writeStreamToFile(fileType, inputStream);
	}

	private InputStream transformDocumentToStream(Document doc) {
		log.info(new Pair<String, String>("Document", "InputStream"));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Source xmlSource = new DOMSource(doc);
		Result outputTarget = new StreamResult(outputStream);
		try {
			TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
		} catch (TransformerException | TransformerFactoryConfigurationError e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}
		InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
		return is;
	}

	public void cleanUp(String fileType) throws IOException {
		Resource resourceOutput = resourceHandler.getResourceFrom(fileName + "." + fileType, true);
		resourceOutput.delete(Collections.EMPTY_MAP);
	}
}
