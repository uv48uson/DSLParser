package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;

import io.FileIO;

public class ParserImpl implements Parser {
	FileIO fileIO;
	
	public ParserImpl(){
		fileIO = new FileIO("tmp");
	}
	
	public ParserImpl(String fileName){
		fileIO = new FileIO(fileName);
	}

	@Override
	public Document parse(InputStream inputStream) throws IOException {
		fileIO.writeStreamToFile("mydsl", inputStream);
		EObject model = fileIO.readModelFromFile("mydsl");
		fileIO.writeModelToFile("xml", model);
		return fileIO.readDocumentFromFile();
	}

	public OutputStream deparse(Document doc) throws IOException {
		fileIO.writeDocumentToFile("xml", doc);
		EObject model = fileIO.readModelFromFile("xml");
		fileIO.writeModelToFile("mydsl", model);
		return fileIO.readOutputStreamFromFile("mydsl");
	}
	public static void main(String[] args) throws IOException {
		FileIO fileIO = new FileIO("test");
		//-------------------------------------------PARSE------------------------------------------------
		
		Parser parser = new ParserImpl("simpleTestParse");
		
		InputStream inputStream = fileIO.readInputStreamFromFile("mydsl");
		Document doc = parser.parse(inputStream);
		fileIO.writeDocumentToFile("xml", doc);

		//-------------------------------------------DEPARSE------------------------------------------------
		
		parser = new ParserImpl("simpleTestDeparse");
		
		doc = fileIO.readDocumentFromFile();
		parser.deparse(doc);
		
		//-----------------------------------------------------------------------------------------------
		
		System.out.println("Success");
	}

}
