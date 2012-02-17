package Controller;

import javax.xml.transform.*;

import java.net.*;
import java.io.*;

public class JanusTransformer {
	String xmlFile;
	String xslFile;
	String htmlFile;
	static public boolean transform(String xmlFile, String xslFile, String htmlFile){
		try {
			File f2 = new File(xslFile);
			File f3 = new File(xmlFile);
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Transformer transformer =
					tFactory.newTransformer
					(new javax.xml.transform.stream.StreamSource
							(f2));
			transformer.transform
			(new javax.xml.transform.stream.StreamSource
					(f3),
					new javax.xml.transform.stream.StreamResult
					( new FileOutputStream(htmlFile)));
			return true;
		}
		catch (Exception e1) {
			e1.printStackTrace( );
			return false;
		}
	}

}