package parser;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;

public class CreateTestFiles {

	public static void main(String[] args) throws IOException, MydslParsingException, SAXException {
		initLogger();

		FileIO fileIO = new FileIO("test");
		InputStream inputStream = fileIO.readInputStreamFromFile("mydsl");
		
		//***********************************************PARSE*******************************************
		
		Parser parser = new ParserImpl("simpleTestParse");
		Document doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);

		//**********************************************DEPARSE******************************************
		
		parser = new ParserImpl("simpleTestDeparse");
		parser.deparse(doc);
		
		//***********************************************************************************************
		
		System.out.println("Success");
	}
	
	private static void initLogger(){
		ParserImpl.init();
		FileIO.init();
	}

}
