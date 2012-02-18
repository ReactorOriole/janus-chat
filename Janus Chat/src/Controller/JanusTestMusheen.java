package Controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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

public class JanusTestMusheen extends JanusMessageService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// Time element
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(
					"src/Model/ClientData/TextLog.xml"));
			doc.getDocumentElement().normalize();
			Node conversation = doc.getElementsByTagName("conversation")
					.item(0);
			Element textLog = (Element) conversation;
			Element time = doc.createElement("time");
			// create time attribute
			Attr timeAttribute = doc.createAttribute("time");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			timeAttribute.setValue(sdf.format(cal.getTime()));
			// append attribute to time element
			time.setAttributeNode(timeAttribute);
			// create sender attribute
			Attr senderAttribute = doc.createAttribute("sender");
			senderAttribute.setValue("The World, Bitch");
			// create message attribute
			Element message = doc.createElement("message");
			Attr msgAttribute = doc.createAttribute("message");
			msgAttribute.setValue("Bryant is a bromosexual");
			message.setAttributeNode(senderAttribute);
			message.setAttributeNode(msgAttribute);
			time.appendChild(message);
			// read text preferences
			DocumentBuilderFactory docBuilderFactory1 = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder1 = docBuilderFactory1
					.newDocumentBuilder();
			Document prefDoc = docBuilder1.parse(new File(
					"src/Model/ClientData/TextPreferences.xml"));
			prefDoc.getDocumentElement().normalize();
			Node prefs = prefDoc.getElementsByTagName("defaults").item(0);
			Element pref = (Element) prefs;
			NodeList fontList = pref.getElementsByTagName("font");
			Element fontElement = (Element) fontList.item(0);
			NodeList sizeList = pref.getElementsByTagName("size");
			Element sizeElement = (Element) sizeList.item(0);
			NodeList colorList = pref.getElementsByTagName("color");
			Element colorElement = (Element) colorList.item(0);
			// create preferences element
			Element preferences = doc.createElement("preferences");
			Attr fontAttr = doc.createAttribute("font");
			fontAttr.setValue(fontElement.getTextContent());
			preferences.setAttributeNode(fontAttr);
			Attr colorAttr = doc.createAttribute("color");
			colorAttr.setValue(colorElement.getTextContent());
			preferences.setAttributeNode(colorAttr);
			Attr sizeAttr = doc.createAttribute("size");
			sizeAttr.setValue(sizeElement.getTextContent());
			preferences.setAttributeNode(sizeAttr);
			time.appendChild(preferences);
			textLog.appendChild(time);

			// Prepare the DOM document for writing
			Source source = new DOMSource(doc);

			// Prepare the output file
			File file = new File("src/Model/ClientData/TextLog.xml");
			Result result = new StreamResult(file);

			// Write the DOM document to the file
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
