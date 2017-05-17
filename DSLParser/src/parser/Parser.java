package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public interface Parser {
	public Document parse(InputStream io) throws IOException, MydslParsingException, SAXException;
	public OutputStream deparse(Document doc) throws IOException, MydslParsingException, SAXException;
}
