package Controller;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.w3c.dom.Document;
import org.w3c.dom.*;

public abstract class JanusMessageService {

	static boolean sendServerMessage( String message )
	{
		try
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse( new File( "src/Model/ServerConfigs.xml" ) );
            
            doc.getDocumentElement().normalize();
            
            Node server = doc.getElementsByTagName( "server" ).item( 0 );
            if( server.getNodeType() == Node.ELEMENT_NODE )
            {
            	Element serverConfigs = ( Element )server;
            	
            	//-------
                NodeList ipList = serverConfigs.getElementsByTagName( "ip" );
                Element ipElement = ( Element )ipList.item( 0 );

                NodeList textIPList = ipElement.getChildNodes();
                System.out.println("IP Address: " + 
                       ( ( Node )textIPList.item( 0 ) ).getNodeValue().trim() );

                //-------
                NodeList portList = serverConfigs.getElementsByTagName( "port" );
                Element portElement = ( Element )portList.item( 0 );

                NodeList textPList = portElement.getChildNodes();
                System.out.println( "Port: " + 
                       ( ( Node )textPList.item( 0 ) ).getNodeValue().trim() );
            }
			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	boolean sendClientMessage( String ClientIP, String message )
	{
		
		return false;
	}
	
}
