package main;

import java.io.File;
import java.io.PrintWriter;
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

import object.ThreadPool;

public class StringsFilesGenerator {

//	List<String> wordList = new ArrayList<String>();
	Map<String, String> tagValueMap = new LinkedHashMap<String, String>(); // tag == attribute name, value = word to be translated
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
			Map<String, String> wordList = new LinkedHashMap<String, String>(); // tag == attribute name, value = word to be translated
			wordList.putAll(this.tagValueMap);
			for(String lang : abbNamePair.values()) {
				wordList.put(lang, lang);
			}
			// abbNamePair.clear();
			abbNamePair.put("default", "English");
			// File file = new File("C:\\Users\\Lenovo\\Desktop\\test");
			// file.mkdir();
			for (String language : abbNamePair.keySet()) {
				File file2;
				if (language.equals("default")) {
					file2 = new File(Const.DESTINATION_PATH + "values\\values\\strings.xml");
				} else {
					file2 = new File(Const.DESTINATION_PATH + "values\\values-" + language + "\\strings.xml");
				}
						
				file2.getParentFile().mkdirs();
				file2.createNewFile();
				List<ThreadPool> threads = new ArrayList<>();
				System.out.println("-----------------"
						+ abbNamePair.get(language) + "-------------------");
				for (String attr : wordList.keySet()) {
					threads.add(new ThreadPool(handler, "en", language, wordList.get(attr), attr)) ;
				}
				for (ThreadPool threadPool : threads) {
					threadPool.run();
				}
				boolean isAllDead = false;
				while (!isAllDead) {
					for (ThreadPool thread : threads) {
						if (thread.isAlive()) {
							isAllDead = false;
							break;
						} else {
							isAllDead = true;
						}
					}
					if (!isAllDead)
						Thread.sleep(200L);
				}
				PrintWriter writer = new PrintWriter(file2.getAbsolutePath(),
						"UTF-8");
				writer.println("<resources>");
				for (ThreadPool pool : threads) {
					System.out.println(pool.getResultWord());
					writer.println(Util.getXmlFormattedLine(pool.getResultWord(), pool.getAttr()));
				}
				writer.print("</resources>");
				writer.close();
				threads.clear();

			}

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
					String tag = eElement.getAttribute("name").toString();
					String value = eElement.getTextContent();
					System.out.println(tag + ", " + value);
					tagValueMap.put(tag, value);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
