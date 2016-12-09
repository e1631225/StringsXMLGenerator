import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;

public class Main {
	// Enter your strings.xml file path here
	// ex: "C:/dev/AndroidStudioProjects/Catalog/app/src/main/res/values/strings.xml"	
	public static final String STRINGS_XML_FILE_PATH = "C:/dev/AndroidStudioProjects/Catalog/app/src/main/res/values/strings.xml";

	public static void main(String[] args) throws Exception {
		StringsFilesGenerator generator = new StringsFilesGenerator();
		generator.execute();
	}

}