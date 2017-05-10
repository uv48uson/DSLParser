package parser;

import java.io.InputStream;

import org.w3c.dom.Document;

public interface Parser {
	public Document parse(InputStream io);
}
