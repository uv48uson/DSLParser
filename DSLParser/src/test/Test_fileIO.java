package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.common.util.WrappedException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import io.FileIO;
import parser.MydslParsingException;
import parser.ParserImpl;

public class Test_fileIO {
	@BeforeClass
	public void setUp(){
		ParserImpl.init();
		FileIO.init();
	}

	@Test(expected = FileNotFoundException.class) 
	public void test_notExistingFile() throws IOException, MydslParsingException, SAXException { 
		FileIO fileIOControl = new FileIO("notExsistingFile");
		fileIOControl.readInputStreamFromFile("mydsl");
	}
	
	@Test(expected = WrappedException.class) 
	public void test_IncorrectXMLFile() throws IOException, MydslParsingException, SAXException { 
		FileIO fileIOControl = new FileIO("IncorrectXML");
		fileIOControl.readDocumentFromFile();
	}
}
