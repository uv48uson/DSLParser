package parser;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public interface Parser {
	public Document parse(InputStream io) throws IOException, MydslParsingException, SAXException;
	public InputStream deparse(Document doc) throws IOException, MydslParsingException, SAXException;
}
