package test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
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
	public void test_simpleParseTest() throws IOException, MydslParsingException, SAXException {
		checkParsing("simpleParseTestControl");
	}
	
	@Test
	public void test_simpleDeparseTest() throws IOException, MydslParsingException, SAXException {
		checkDeparsing("simpleDeparseTestControl");
	}
	
	@Test
	public void test_combinedParseTest() throws IOException, MydslParsingException, SAXException {
		checkParsing("combinedParseTestControl_update");
	}
	
	@Test
	public void test_combinedDeparseTest() throws IOException, MydslParsingException, SAXException {
		checkDeparsing("combinedDeparseTestControl_update");
	}
	
	private void checkParsing(String fileName) throws IOException, MydslParsingException, SAXException{
		FileIO fileIOControl = new FileIO(fileName);
		Document controlDoc = fileIOControl.readDocumentFromFile();
		
		Parser parser = new ParserImpl();
		Document resultDoc = parser.parse(fileIOControl.readInputStreamFromFile("mydsl"));
		
		XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        
        assertXMLEqual(controlDoc, resultDoc);
	}
	
	private void checkDeparsing(String fileName) throws IOException, MydslParsingException, SAXException{
		FileIO fileIOControl = new FileIO(fileName);
		InputStream controlStream = fileIOControl.readInputStreamFromFile("mydsl");
		
		Parser parser = new ParserImpl();
		InputStream resultStream = parser.deparse(fileIOControl.readDocumentFromFile());
		
        assertTrue(IOUtils.contentEquals( controlStream, resultStream));
	}
}
