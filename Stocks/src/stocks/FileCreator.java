package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Attribute;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class represents file making data. Its purpose is to create saved files for the program.
 */
public class FileCreator {
  XMLParser parse = new XMLParser();

  /**
   * Connect to AlphaVantageAPI and create a new CSV file with stock data.
   *
   * @param stockSymbol the inputted stockSymbol
   * @param apiKey      the API key to access the API data
   */
  public void createStockCSVFile(String stockSymbol, String apiKey) {
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    String directoryPath = "Stocks/res/data/";
    String fileName = stockSymbol + ".csv";
    String path = directoryPath + fileName;
    this.createFile(path, output, true);
  }

  /**
   * This is a method to create a file at the given path location containing the given data.
   *
   * @param path the given path of the file
   * @param data the given data to be written into the file
   */
  public void createFile(String path, StringBuilder data, boolean csv) {
    try {
      File file = new File(path);
      FileWriter writer = new FileWriter(file);
      writer.write(data.toString());
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This is a method to create a new portfolio file with the given name.
   *
   * @param portfolioName the name of the given portfolio
   */
  public void createNewPortfolioFile(String portfolioName) {
    StringBuilder header = new StringBuilder();
    String heading = "Date, Stock, Share(s)";
    for (int i = 0; i < heading.length(); i++) {
      header.append(heading.charAt(i));
    }
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = "/Stocks/res/portfolios/";
    String fileName = portfolioName + ".xml";
    String path = userDirectory + directoryPath + fileName;
    this.createXMLFile(path, portfolioName);
  }

  /**
   * This is a method to write update the data on an XML file by writing the new changes to it.
   *
   * @param filePath the given path to the desired file
   * @param doc      the given XML Document
   */
  public void writeToXMLFile(String filePath, Document doc) {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = null;
    try {
      transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    } catch (TransformerConfigurationException e) {
      throw new RuntimeException(e);
    }
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(filePath));
    try {
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This is a method that creates XML files to represent portfolios.
   */
  public void createXMLFile(String path, String portfolioName) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      //root elements
      Document doc = docBuilder.newDocument();
      Element rootElement = doc.createElement(portfolioName);
      doc.appendChild(rootElement);

      //write the content into xml file
      this.writeToXMLFile(path, doc);

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }

  /**
   * This is a method that adds a new year node to a portfolio if the given year doesn't already
   * exist.
   * @param path the given path to the XML document
   * @param doc the given XML document for the portfolio
   * @param year the given year
   */
  public void addNewYearTag(String path, Document doc, String year) {
    Element yearTag = doc.createElement("year");
    Attr newYear = doc.createAttribute("y");
    newYear.setValue(year);
    yearTag.appendChild(newYear);
    this.writeToXMLFile(path, doc);
  }

  /**
   * This is a method that adds a new month node to a portfolio if the given month doesn't already
   * exist.
   * @param path the given path to the XML document
   * @param doc the given XML document for the portfolio
   * @param month the given month
   */
  public void addNewMonthTag(String path, Document doc, String month) {
    Element monthTag = doc.createElement("month");
    Attr newMonth = doc.createAttribute("m");
    newMonth.setValue(month);
    monthTag.appendChild(newMonth);
    this.writeToXMLFile(path, doc);
  }

  /**
   * This is a method that adds a new stock node to a portfolio if it doesn't already exist.
   *
   * @param doc   the given XML document for the portfolio
   * @param root  the given root tag in the XML document
   * @param stock the stock symbol for the given stock
   */
  public void addNewStockTag(String path, Document doc, Element root, String stock,
                             int shares, String date) {
    Element stockTag = doc.createElement("stock");
    root.appendChild(stockTag);
    Attr newStock = doc.createAttribute("symbol");
    newStock.setValue(stock);
    stockTag.setAttributeNode(newStock);
    this.addNewTransactionTag(doc, stockTag, shares, date);
    this.writeToXMLFile(path, doc);
  }

  /**
   * This is a method that adds a new transaction node to a portfolio when the user decides
   * to buy/sell/re-balance their portfolio.
   *
   * @param doc         the given XML document for the portfolio
   * @param targetStock the given node for the stock
   * @param shares      the given number of shares bought/sold
   * @param date        the given date that a purchase/sale was made
   */
  public void addNewTransactionTag(Document doc, Node targetStock, int shares, String date) {
    Element transactionTag = null;
    transactionTag = doc.createElement("transaction");
    targetStock.appendChild(transactionTag);
    String transactionType = "";
    if (shares > 0) {
      transactionType = "buy";
    } else {
      transactionType = "sell";
    }
    Attr type = doc.createAttribute("type");
    type.setValue(transactionType);
    transactionTag.setAttributeNode(type);

    Element sharesTag = doc.createElement("shares");
    sharesTag.appendChild(doc.createTextNode("" + shares));
    transactionTag.appendChild(sharesTag);

    Element dateTag = doc.createElement("date");
    dateTag.appendChild(doc.createTextNode(date));
    transactionTag.appendChild(dateTag);
  }

  /**
   * This is a method to determine if the given stock already has a parent node created for it.
   * @param doc represents the given XML document of the portfolio
   * @param stock the given stock symbol
   * @return the index of the target stock node in the list of nodes currently in the XML document
   * as an integer
   */
  public int findTargetStockNode(Document doc, String stock) {
    int existingTagIndex = -1;
    NodeList nodes = doc.getElementsByTagName(stock);
    for (int n = 0; n < nodes.getLength(); n++) {
      Node curNode = nodes.item(n);
      if (curNode.getAttributes().getNamedItem("symbol").getNodeValue().equals(stock)) {
        existingTagIndex = n;
      }
    }
    return existingTagIndex;
  }

  /**
   * Add the given stock to the given portfolio XML file.
   * @param stock  the given stock symbol
   * @param shares the given number of shares
   */
  public void addStockToPortfolioFile(String portfolio, String stock, int shares, String date) {
    String filePath = System.getProperty("user.dir") + "/Stocks/res/portfolios/"
            + portfolio + ".xml";
    String[] dateSplit = date.split("-");
    String year = dateSplit[0];
    String month = dateSplit[1];
    String day = dateSplit[2];

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = null;
    try {
      documentBuilder = docFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    try {
      Document doc = documentBuilder.parse(filePath);
      Element root = doc.getDocumentElement();

      int existingTagIndex = this.findTargetStockNode(doc, stock);
      NodeList nodes = doc.getElementsByTagName("stock");

      Node targetStock;
      if (existingTagIndex == -1) {
        this.addNewStockTag(filePath, doc, root, stock, shares, date);
      }
      else {
        targetStock = nodes.item(existingTagIndex);
        this.addNewTransactionTag(doc, targetStock, shares, date);
      }

      this.writeToXMLFile(filePath, doc);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

// REFERENCE CODE:
//stock elements
//      Element stock = doc.createElement("stock");
//      rootElement.appendChild(stock);

//      //set attribute to staff element
//      Attr nvda = doc.createAttribute("id");
//      nvda.setValue("nvda");
//      stock.setAttributeNode(nvda);
//
//      //shorten way
//      //staff.setAttribute("id", "1");
//
//      //firstname elements
//      Element firstname = doc.createElement("firstname");
//      firstname.appendChild(doc.createTextNode("yong"));
//      stock.appendChild(firstname);
//
//      //lastname elements
//      Element lastname = doc.createElement("lastname");
//      lastname.appendChild(doc.createTextNode("mook kim"));
//      stock.appendChild(lastname);
//
//      //nickname elements
//      Element nickname = doc.createElement("nickname");
//      nickname.appendChild(doc.createTextNode("mkyong"));
//      stock.appendChild(nickname);
//
//      //salary elements
//      Element salary = doc.createElement("salary");
//      salary.appendChild(doc.createTextNode("100000"));
//      stock.appendChild(salary);