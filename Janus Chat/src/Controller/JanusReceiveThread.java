package Controller;

import java.io.ObjectInputStream;
import java.net.Socket;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class JanusReceiveThread extends Thread {

	private Socket socket;

	public JanusReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream reader = new ObjectInputStream(
					socket.getInputStream());

			try {
				Document document = (Document) reader.readObject();

				JanusProcessor jp = new JanusProcessor(document);
				String chatMessage = (String) ((NodeList) jp
						.xpathQuery("/message/chatMessage/text()")).item(0)
						.getTextContent();

				String sender = (String) ((NodeList) jp
						.xpathQuery("/message/sn/text()")).item(0)
						.getTextContent();

				String font = (String) ((NodeList) jp
						.xpathQuery("/message/preferences/@font")).item(0)
						.getTextContent();

				String color = (String) ((NodeList) jp
						.xpathQuery("/message/preferences/@color")).item(0)
						.getTextContent();

				String size = (String) ((NodeList) jp
						.xpathQuery("/message/preferences/@size")).item(0)
						.getTextContent();

				// Update text log
				JanusLogUpdater.update(sender, chatMessage, font, color, size);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
