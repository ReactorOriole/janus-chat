import org.w3c.dom.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import java.io.IOException;
import org.xml.sax.SAXException;

public class JanusProcessor {
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = domFactory.newDocumentBuilder();
	Document doc =null;
	public JanusProcessor(String file){
		domFactory.setNamespaceAware(true); 
		Document doc = builder.parse(file);
	}
	public NodeList xpathQuery(String query){
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(query);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
	}
}