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
			List<String> wordList = new ArrayList<String>();
			wordList.addAll(this.wordList);
			wordList.addAll(abbNamePair.values());
//			abbNamePair.clear();
			abbNamePair.put("tr", "Turkish");
//			File file = new File("C:\\Users\\Lenovo\\Desktop\\test");
//			file.mkdir();
			for (String language : abbNamePair.keySet()) {
				File file2 = new File(Const.DESTINATION_PATH + "test\\values-" + language + "\\strings.xml");
				file2.getParentFile().mkdirs();
				file2.createNewFile();
				List<ThreadPool> threads = new ArrayList<>();
				System.out.println("-----------------" + abbNamePair.get(language) + "-------------------");
				for (String word : wordList) {
					threads.add(new ThreadPool(handler, "en", language, word));
				}
				for(ThreadPool threadPool : threads) {
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
				 PrintWriter writer = new PrintWriter(file2.getAbsolutePath(), "UTF-8");
				    
				for(ThreadPool pool : threads) {
					System.out.println(pool.getResultWord());
					writer.println(pool.getResultWord());
				}
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

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

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
					wordList.add(eElement.getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
