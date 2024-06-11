package stocks;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This is a class that parses and manipulates XML files.
 */
public class XMLParser {


  public void parseXMLDocument(String path) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(path);
      doc.getDocumentElement().normalize();
      NodeList stockList = doc.getElementsByTagName("stock");
      for (int i = 0; i < stockList.getLength(); i++) {
        Node transaction = stockList.item(i);

      }
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }
}
