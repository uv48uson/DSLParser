package parser;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;

public class CreateTestFiles {
	public static Parser parser = new ParserImpl();

	public static void createTestFiles() throws IOException, MydslParsingException, SAXException {
		initLogger();
		
		//***********************************************simpleParseTestControl*******************************************

		createParseTestDocument("simpleParseTestControl");
		
		//***********************************************combinedParseTestControl*******************************************
		
		createParseTestDocument("combinedParseTestControl_base");
		
		createParseTestDocument("combinedParseTestControl_update");

		
		//***********************************************simpleDeparseTestControl*******************************************
		
		createDeparseTestDocument("simpleDeparseTestControl");
		
		//***********************************************combinedDeparseTestControl*******************************************
		
		createDeparseTestDocument("combinedDeparseTestControl_base");
		
		createDeparseTestDocument("combinedDeparseTestControl_update");
		

		System.out.println("Success");
	}

	private static void createDeparseTestDocument(String fileName) throws IOException, MydslParsingException, SAXException {
		FileIO fileIO;
		Document inputDocument;
		InputStream resultStream;
		fileIO = new FileIO(fileName);
		fileIO.cleanUp("mydsl");
		inputDocument = fileIO.readDocumentFromFile();
		
		resultStream = parser.deparse(inputDocument);
		fileIO.writeStreamToFile("mydsl", resultStream);
	}

	private static void createParseTestDocument(String fileName) throws IOException, MydslParsingException, SAXException {
		FileIO fileIO;
		InputStream inputStream;
		Document doc;
		fileIO = new FileIO(fileName);
		fileIO.cleanUp("xml");
		inputStream = fileIO.readInputStreamFromFile("mydsl");
		
		doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);
	}
	
	private static void initLogger(){
		ParserImpl.init();
		FileIO.init();
	}

}
