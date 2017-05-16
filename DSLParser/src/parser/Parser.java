package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

public interface Parser {
	public Document parse(InputStream io) throws IOException;
	public OutputStream deparse(Document doc) throws IOException;
}
