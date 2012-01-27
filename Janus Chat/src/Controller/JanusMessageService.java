package Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

import Model.MessageTypes;

public abstract class JanusMessageService {
	
	static boolean sendHelloServerMessage( String screenName, String ipAddress )
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element message = doc.createElement( "message" );
			doc.appendChild( message );
			Element type = doc.createElement( "type" );
			Element sn = doc.createElement( "sn" );
			Element clientip = doc.createElement( "clientip" );
			message.appendChild( type );
			message.appendChild( sn );
			message.appendChild( clientip );
			Text text = doc.createTextNode( Integer.toString( MessageTypes.HELLO_SERVER ) );
			type.appendChild( text );
			text = doc.createTextNode( screenName );
			sn.appendChild( text );
			text = doc.createTextNode( ipAddress );
			clientip.appendChild( text );
			
			return sendServerMessage( doc );
		} catch (Exception e)
		{
			return false;
		}
	}

	static boolean sendServerMessage( Document document )
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
                String IPAddress = ( ( Node )textIPList.item( 0 ) ).getNodeValue().trim();

                //-------
                NodeList portList = serverConfigs.getElementsByTagName( "port" );
                Element portElement = ( Element )portList.item( 0 );

                NodeList textPList = portElement.getChildNodes();
                String port = ( ( Node )textPList.item( 0 ) ).getNodeValue().trim();
                
                Socket s = new Socket( IPAddress, Integer.valueOf( port ) );
                PrintWriter out = new PrintWriter( s.getOutputStream(), true );
                out.println( document );
            }
			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}
	
	static boolean sendClientMessage( String ClientIP, String MyIP, String chatMessage ) throws IOException
	{
		Socket s = null;
		PrintWriter out = null;
		
		try
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element message = doc.createElement( "message" );
			doc.appendChild( message );
			Element fromIP = doc.createElement( "fromIP" );
			Element chatText = doc.createElement( "chatMessage" );
			message.appendChild( fromIP );
			message.appendChild( chatText );
			Text text = doc.createTextNode( MyIP );
			fromIP.appendChild( text );
			text = doc.createTextNode( chatMessage );
			chatText.appendChild( text );
			
			s = new Socket( ClientIP, 999 );
			out = new PrintWriter( s.getOutputStream(), true );
			out.println( doc );
			return true;
		}
		catch( UnknownHostException e )
		{
			System.err.println( "Don't know about: " + ClientIP );
			return false;
		}
		catch( IOException e )
		{
			System.err.println( "Couldn't get I/O for the connection to: " + ClientIP );
			return false;
		} catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			out.close();
			s.close();
		}
	}
	
}
