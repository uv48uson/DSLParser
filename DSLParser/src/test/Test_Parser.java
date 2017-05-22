package test;

import java.io.IOException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;
import parser.MydslParsingException;
import parser.Parser;
import parser.ParserImpl;

public class Test_Parser extends XMLTestCase{
	Parser parser = new ParserImpl();
	
	@BeforeClass
	public void setUp(){
		ParserImpl.init();
		FileIO.init();
	}
	
	@Test
	public void test_simpleTest() throws IOException, MydslParsingException, SAXException {
		FileIO fileIOControl = new FileIO("simpleTestControl");
		Document controlDoc = fileIOControl.readDocumentFromFile();
		
		Parser parser = new ParserImpl();
		Document resultDoc = parser.parse(fileIOControl.readInputStreamFromFile("mydsl"));
		
		XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        
        assertXMLEqual(controlDoc, resultDoc);
	}
	
	@Test
	public void test_combinedTest() throws IOException, MydslParsingException, SAXException {
		FileIO fileIOControl = new FileIO("combinedTestControl");
		Document controlDoc = fileIOControl.readDocumentFromFile();
		
		Parser parser = new ParserImpl();
		Document resultDoc = parser.parse(fileIOControl.readInputStreamFromFile("mydsl"));
		
		XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        
        assertXMLEqual(controlDoc, resultDoc);
	}
}
