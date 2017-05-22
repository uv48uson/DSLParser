package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import io.FileIO;
import io.TableEdgeLayout;
import javafx.util.Pair;

public class ParserImpl implements Parser {
	private static Logger log = Logger.getLogger( ParserImpl.class );
	FileIO fileIO;
	
	public ParserImpl(){
		fileIO = new FileIO("tmp");
	}
	
	public ParserImpl(String fileName){
		fileIO = new FileIO(fileName);
	}
	
	public static void init(){
		log.removeAllAppenders();
		log.addAppender(new ConsoleAppender(new TableEdgeLayout()));
		log.setAdditivity(false);
	}

	@Override
	public Document parse(InputStream inputStream) throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String,String>("Start Parsing", "Start"));
		
		fileIO.writeStreamToFile("mydsl", inputStream);
		EObject model = fileIO.readModelFromFile("mydsl");
		fileIO.writeModelToFile("xml", model);
		Document doc = fileIO.readDocumentFromFile();
		fileIO.cleanUp();
		
		log.info(new Pair<String,String>("Finished Parsing", "End"));
		return doc;
	}

	public OutputStream deparse(Document doc) throws IOException, MydslParsingException, SAXException {
		log.info(new Pair<String,String>("Start Deparsing", "Start"));
		
		fileIO.writeDocumentToFile("xml", doc);
		EObject model = fileIO.readModelFromFile("xml");
		fileIO.writeModelToFile("mydsl", model);
		OutputStream out = fileIO.readOutputStreamFromFile("mydsl");
		fileIO.cleanUp();
		
		log.info(new Pair<String,String>("Finished Deparsing", "End"));
		return out;
	}

}
