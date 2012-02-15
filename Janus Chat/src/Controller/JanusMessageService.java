package Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

public abstract class JanusMessageService {
	
	static boolean sendMessage( String ClientIP, String MyIP, String chatMessage ) throws IOException
	{
		Socket s = null;
		ObjectOutputStream out = null;
		
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
			
			s = new Socket( ClientIP, 2222 );
			out = new ObjectOutputStream( s.getOutputStream() );
			out.writeObject( doc );
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
	
	static void receiveMessages()
	{
		// Default port number
		int port = 2222;
		ServerSocket serverSocket = null;
		
		try
		{
			serverSocket = new ServerSocket( port );
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		while( true )
		{	
			try {
				Socket clientSocket = serverSocket.accept();
				JanusReceiveThread thread = new JanusReceiveThread( clientSocket );
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
