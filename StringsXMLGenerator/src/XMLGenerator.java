
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;

public class XMLGenerator {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

    	XMLGenerator http = new XMLGenerator();

//        System.out.println("Testing 1 - Send Http GET request");
//        http.sendGet();
//
//        System.out.println("\nTesting 2 - Send Http POST request");
//        http.sendPost();
        init();

    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "https://translate.google.com/#en/tr/mouse";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        // add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }
    
       private static void init() {
            try {

                File fXmlFile = new File("C:/dev/AndroidStudioProjects/Catalog/app/src/main/res/values/strings.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("string");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

//                    System.out.println("\nCurrent Element :" + nNode.getNodeName() + " value" + nNode.getNodeValue() + " " + nNode.getFirstChild().getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    
                        Element eElement = (Element) nNode;
    
                        System.out.println(eElement.getTextContent());
//                        System.out.println("Staff id : " + eElement.getAttribute("id"));
//                        System.out.println("First Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
//                        System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//                        System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//                        System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
    
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "https://translate.google.com/#en/tr/mouse";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }
}