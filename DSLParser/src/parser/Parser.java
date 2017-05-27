package parser;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public interface Parser {
	
	/**
	 * Parsing the InputStream to a w3c.dom Document, using the MyDsl Syntax. To resolve dependencies, the necessary files should
	 * be located at /TestFiles/ (TestFiles should be in the same directory as the jar itself).
	 * On Parser construction a fileIO instance with an associated ResourceHandler is created, which then tries to register all
	 * files containing a ".mydsl" char-sequence in there name and are located in the resource folder.
	 * Cross-reference resolution is resolved over the resourceSet afterwards. The files were pre-registerd not pre-loaded. They
	 * get loaded on demand.
	 * While parsing temporary files "tmp.xml" and "tmp.mydsl" are generated. These files are supposed to not exist anymore after
	 * the parsing workflow has ended.
	 * 
	 * @param io	the InputStream which gets parsed into the a w3c.dom Document
	 * @return	a Document containing a valid Mydsl AST
	 * @throws IOException	on internal error
	 * @throws MydslParsingException on InputStream that is not a valid mydsl Snippet
	 * @throws SAXException on xml Parsing-exception
	 */
	public Document parse(InputStream io) throws IOException, MydslParsingException, SAXException;
	
	/**
	 * Deparsing a w3c.dom Document containing a valid mydsl-AST into an inputStream containing the original mydsl-language-code.
	 * To resolve dependencies, the necessary "mydsl"-files should be located at /TestFiles/ (TestFiles should be in the same 
	 * directory as the jar itself). 
	 * On Parser construction a fileIO instance with an associated ResourceHandler is created, which then tries to register all 
	 * files containing a ".mydsl" char-sequence in there name and are located in the resource folder. Cross-reference resolution
	 * is resolved over the resourceSet afterwards. The files were pre-registerd not pre-loaded. They get loaded on demand.
	 * While deparsing temporary files "tmp.xml" and "tmp.mydsl" are generated. These files are supposed to not exist anymore after
	 * the deparsing workflow has ended.
	 * 
	 * @param doc	a valid mydsl-AST in xml-structur
	 * @return an InputStream containing a ".mydsl" file with all Elements from the input AST (no EOL)
	 * @throws IOException	on internal error or if doc is not a valid mydsl-AST
	 * @throws MydslParsingException	on mydsl-Parsing Error
	 * @throws SAXException on internal error
	 */
	public InputStream deparse(Document doc) throws IOException, MydslParsingException, SAXException;
}
