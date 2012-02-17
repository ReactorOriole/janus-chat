package Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import View.ChatWindow;

public class JanusLogUpdater {

	public static void update(String sender, String message)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File(
				"src/Model/ClientData/TextLog.xml"));
		doc.getDocumentElement().normalize();
		Node conversation = doc.getElementsByTagName("conversation").item(0);
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
		Element messageElement = doc.createElement("message");
		Attr msgAttribute = doc.createAttribute("message");
		msgAttribute.setValue(message);
		// create sender attribute
		Attr senderAttribute = doc.createAttribute("sender");
		senderAttribute.setValue(sender);
		messageElement.setAttributeNode(senderAttribute);
		messageElement.setAttributeNode(msgAttribute);
		time.appendChild(messageElement);
		// read text preferences
		JanusProcessor jp = new JanusProcessor(new File(
				"src/Model/ClientData/TextPreferences.xml"));
		NodeList nodes = jp.xpathQuery("/defaults/font/text()");
		String font = nodes.item(0).getNodeValue();
		nodes = jp.xpathQuery("/defaults/size/text()");
		String size = nodes.item(0).getNodeValue();
		nodes = jp.xpathQuery("/defaults/color/text()");
		String color = nodes.item(0).getNodeValue();
		// create preferences element
		Element preferences = doc.createElement("preferences");
		Attr fontAttr = doc.createAttribute("font");
		fontAttr.setValue(font);
		preferences.setAttributeNode(fontAttr);
		Attr colorAttr = doc.createAttribute("color");
		colorAttr.setValue(color);
		preferences.setAttributeNode(colorAttr);
		Attr sizeAttr = doc.createAttribute("size");
		sizeAttr.setValue(size);
		preferences.setAttributeNode(sizeAttr);
		time.appendChild(preferences);
		textLog.appendChild(time);

		// Prepare the DOM document for writing
		Source source = new DOMSource(doc);

		// Prepare the output file
		File file = new File("src/Model/ClientData/TextLog.xml");
		Result result = new StreamResult(file);

		// Write the DOM document to the file
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(source, result);

		ChatWindow.updateWindow();
	}
}
