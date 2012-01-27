package Controller;

import javax.xml.transform.*;
import java.net.*;
import java.io.*;

public class JanusTransformer {
	String xmlFile;
	String xslFile;
	String htmlFile;
	public JanusTransformer(String xmlFile, String xslFile, String htmlFile){
		try {

		    TransformerFactory tFactory = TransformerFactory.newInstance();

		    Transformer transformer =
		      tFactory.newTransformer
		         (new javax.xml.transform.stream.StreamSource
		            ("../Model/ClientData/ChatLog.xsl"));

		    transformer.transform
		      (new javax.xml.transform.stream.StreamSource
		            ("../Model/ClientData/TextLog.xml"),
		       new javax.xml.transform.stream.StreamResult
		            ( new FileOutputStream("../Model/ClientData/test.html")));
		    }
		  catch (Exception e) {
		    e.printStackTrace( );
		    }
	}
	
  }