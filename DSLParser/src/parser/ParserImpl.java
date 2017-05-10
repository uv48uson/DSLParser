package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.w3c.dom.Document;
import org.xtext.example.mydsl.myDsl.Model;

public class ParserImpl implements Parser {
	ResourceHandler resourceHandler = new ResourceHandler();

	@Override
	public Document parse(InputStream io) {
		Model model = readFromStream(io);
		
		Resource resourceOutput = resourceHandler.createResourceAt("tmp.xml");
		resourceOutput.getContents().add(model);
		
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		Document doc=((XMLResource)resourceOutput).save(null, options, null);
		return doc;
	}
	
	private Model readFromStream(InputStream inputStream) {
		
		Resource resourceInput = resourceHandler.createResourceAt("tmp.mydsl");
		
		try {
			resourceInput.load(inputStream, resourceHandler.createResourceSet().getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (Model) resourceInput.getContents().get(0);
	}
	
	public static void main(String[] args) throws IOException {
		Parser parser = new ParserImpl();
		
		InputStream is;

		try {
			is = new FileInputStream("C://Users/z003rrpp.AD001/workspace/Java Standalone Application Test/src/test/test.mydsl");
			parser.parse(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Success");
	}

}
