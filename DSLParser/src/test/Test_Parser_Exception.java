package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;
import parser.MydslParsingException;
import parser.Parser;
import parser.ParserImpl;

public class Test_Parser_Exception {
	private static final String PATH_TO_RESOURCES = "/resources/";
	
	@BeforeClass
	public void setUp(){
		ParserImpl.init();
		FileIO.init();
	}
	
	@Test(expected=MydslParsingException.class)
	public void test_IncorrectParsingInput() throws IOException, MydslParsingException, SAXException {
		Parser parser = new ParserImpl();
		URL url = getClass().getResource(PATH_TO_RESOURCES + "IncorrectParsing.mydsl");
		File incorrectMydslFile = new File(url.getPath());
		InputStream stream = new FileInputStream(incorrectMydslFile);
		parser.parse(stream);
	}
	
	@Test(expected=IOException.class)
	public void test_IncorrectDeparsingInput() throws IOException, MydslParsingException, SAXException, ParserConfigurationException {
		URL url = getClass().getResource(PATH_TO_RESOURCES + "IncorrectDeparsing.xml");
		File incorrectXMLFile = new File(url.getPath());
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(incorrectXMLFile);
		
		Parser parser = new ParserImpl("test");
		parser.deparse(doc);
	}

}
