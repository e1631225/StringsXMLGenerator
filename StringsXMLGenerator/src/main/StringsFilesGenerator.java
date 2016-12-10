package main;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StringsFilesGenerator {

	List<String> wordList = new ArrayList<String>();
	private Map<String, String> abbNamePair = new LinkedHashMap<String, String>();
	HTTPRequestHandler handler;

	public void execute() {
		handler = new HTTPRequestHandler();
		readStringsFile();
		getLanguageList();
		getTranslations();
	}

	private void getTranslations() {
		try {
			handler.getTranslation(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getLanguageList() {
		try {
			abbNamePair = handler.getLanguageList();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readStringsFile() {
		// Parse strings.xml file and system.out all words;
		try {

			File fXmlFile = new File(Const.STRINGS_XML_FILE_PATH);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("string");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				// System.out.println("\nCurrent Element :" +
				// nNode.getNodeName() + " value" + nNode.getNodeValue() + " " +
				// nNode.getFirstChild().getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println(eElement.getTextContent());
					// System.out.println("Staff id : " +
					// eElement.getAttribute("id"));
					// System.out.println("First Name : " +
					// eElement.getElementsByTagName("name").item(0).getTextContent());
					// System.out.println("Last Name : " +
					// eElement.getElementsByTagName("lastname").item(0).getTextContent());
					// System.out.println("Nick Name : " +
					// eElement.getElementsByTagName("nickname").item(0).getTextContent());
					// System.out.println("Salary : " +
					// eElement.getElementsByTagName("salary").item(0).getTextContent());
					wordList.add(eElement.getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
