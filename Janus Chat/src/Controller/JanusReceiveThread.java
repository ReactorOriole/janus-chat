package Controller;

import java.io.File;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JanusReceiveThread extends Thread {
	
	private Socket socket;
	
	public JanusReceiveThread( Socket socket )
	{
		this.socket = socket;
	}

	public void run()
	{
		try
		{
			ObjectInputStream reader = new ObjectInputStream( socket.getInputStream() );
			
			try {
				
				while (true)
				{
					Document document = ( Document )reader.readObject();
					
					Element messageElement = ( Element ) document.getElementsByTagName( "message" ).item( 0 );
					Element senderElement = ( Element ) document.getElementsByTagName( "sn" ).item( 0 );
					
					// Time element
					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = docBuilderFactory
							.newDocumentBuilder();
					Document doc = docBuilder.parse(new File(
							"src/Model/ClientData/TextLog.xml"));
					doc.getDocumentElement().normalize();
					Node conversation = doc
							.getElementsByTagName("conversation").item(0);
					Element textLog = (Element) conversation;
					Element time = doc.createElement("time");
					// create time attribute
					Attr timeAttribute = doc.createAttribute("time");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					timeAttribute.setValue(sdf.format(cal.getTime()));
					// append attribute to time element
					time.setAttributeNode(timeAttribute);
					// create message attribute
					Element message = doc.createElement("message");
					Attr msgAttribute = doc.createAttribute("message");
					msgAttribute.setValue( messageElement.getTextContent() );
					// create sender attribute
					Attr senderAttribute = doc.createAttribute("sender");
					senderAttribute.setValue( senderElement.getTextContent() );
					message.setAttributeNode(senderAttribute);
					message.setAttributeNode(msgAttribute);
					time.appendChild(message);
					// read text preferences
					JanusProcessor jp = new JanusProcessor( new File( "src/Model/ClientData/TextPreferences.xml" ) );
					NodeList nodes = ( NodeList )jp.xpathQuery( "/defaults/font/text()" );
					String font = nodes.item( 0 ).getNodeValue();
					nodes = ( NodeList )jp.xpathQuery( "/defaults/size/text()" );
					String size = nodes.item( 0 ).getNodeValue();
					nodes = ( NodeList )jp.xpathQuery( "/defaults/color/text()" );
					String color = nodes.item( 0 ).getNodeValue();
					// create preferences element
					Element preferences = doc.createElement( "preferences" );
					Attr fontAttr = doc.createAttribute( "font" );
					fontAttr.setValue( font );
					preferences.setAttributeNode( fontAttr );
					Attr colorAttr = doc.createAttribute( "color" );
					colorAttr.setValue( color );
					preferences.setAttributeNode( colorAttr );
					Attr sizeAttr = doc.createAttribute( "size" );
					sizeAttr.setValue( size );
					preferences.setAttributeNode( sizeAttr );
					time.appendChild( preferences );
					textLog.appendChild( time );

					 // Prepare the DOM document for writing
			        Source source = new DOMSource(doc);

			        // Prepare the output file
			        File file = new File( "src/Model/ClientData/TextLog.xml" );
			        Result result = new StreamResult(file);
			        
			     // Write the DOM document to the file
			        Transformer xformer = TransformerFactory.newInstance().newTransformer();
			        xformer.transform(source, result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch( Exception e )
		{
			System.err.println( e.getMessage() );
		}
	}
}
