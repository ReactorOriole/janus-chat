package Controller;

import org.w3c.dom.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;

import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

public class JanusProcessor {
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	Document doc =null;
	
	public JanusProcessor(File file){
		domFactory.setNamespaceAware(true);
		try {
			builder = domFactory.newDocumentBuilder();
			doc = builder.parse(file.toURI().toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public NodeList xpathQuery(String query){
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr;
		Object result=null;
		try {
			expr = xpath.compile(query);
			result = expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList nodes = (NodeList) result;
		return nodes;
	}
}