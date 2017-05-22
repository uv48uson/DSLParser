package parser;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;

public class CreateTestFiles {

	public static void main(String[] args) throws IOException, MydslParsingException, SAXException {
		initLogger();
		
		//***********************************************SIMPLE_TEST_CONTROL*******************************************

		FileIO fileIO = new FileIO("simpleTestControl");
		fileIO.cleanUp();
		InputStream inputStream = fileIO.readInputStreamFromFile("mydsl");		
		
		Parser parser = new ParserImpl("tmp");
		Document doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);

		
		//***********************************************COMBINED_TEST_CONTROL*******************************************
		
		fileIO = new FileIO("base");
		fileIO.cleanUp();
		inputStream = fileIO.readInputStreamFromFile("mydsl");
		
		parser = new ParserImpl("tmp");
		doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);
		
		fileIO = new FileIO("combinedTestControl");
		fileIO.cleanUp();
		inputStream = fileIO.readInputStreamFromFile("mydsl");
		
		parser = new ParserImpl("tmp");
		doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);
		

		System.out.println("Success");
	}
	
	private static void initLogger(){
		ParserImpl.init();
		FileIO.init();
	}

}
