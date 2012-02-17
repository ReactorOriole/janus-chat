package Controller;

import java.io.ObjectInputStream;
import java.net.Socket;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

				while (true) {
					Document document = (Document) reader.readObject();

					Element message = (Element) document.getElementsByTagName(
							"message").item(0);
					Element sender = (Element) document.getElementsByTagName(
							"sn").item(0);

					// Update text log
					JanusLogUpdater.update(message.getNodeValue(),
							sender.getNodeValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
