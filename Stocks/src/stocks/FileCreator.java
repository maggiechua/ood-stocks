package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class was previously 'AlphaVantageDemo',and existed solely for the purpose of creating new
 * CSV files from the API. Now, it also helps in creating files to save portfolio and log data.
 * ---
 * This class represents file making data. Its purpose is to create saved files for the program.
 */
public class FileCreator {
  FileParser fp;

  public FileCreator() {
    this.fp = new FileParser();
  }

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
    Path path = fp.retrievePath(fp.getOSType(), stockSymbol, "data/",".csv");
    this.createFile(path, output);
  }

  /**
   * This is a method to create a file at the given path location containing the given data.
   *
   * @param path the given path of the file
   * @param data the given data to be written into the file
   */
  public void createFile(Path path, StringBuilder data) {
    try {
      File file = new File(path.toString());
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
   * @param lot           the given list of sorted transactions
   */
  public void createNewPortfolioFile(String portfolioName, List<Transaction> lot) {
    Path path = fp.retrievePath(fp.getOSType(), portfolioName, "portfolios/", ".xml");
    this.createXMLFile(path, portfolioName, lot);
  }

  /**
   * This is a method to write update the data on an XML file by writing the new changes to it.
   *
   * @param filePath the given path to the desired file
   * @param doc      the given XML Document
   */
  public void writeToXMLFile(Path filePath, Document doc) {
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
    StreamResult result = new StreamResult(new File(filePath.toString()));
    try {
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This is a method that creates XML files to represent portfolios.
   */
  public void createXMLFile(Path path, String portfolioName, List<Transaction> lot) {
    File file = path.toFile();

    if (file.exists()) {
      file.delete();
    }
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();

      //root elements
      Element rootElement = doc.createElement(portfolioName);
      doc.appendChild(rootElement);

      //adding the transactions in chronological order
      for (Transaction t : lot) {
        this.addNewDateTag(doc, rootElement, t);
      }

      //write the content into xml file
      this.writeToXMLFile(path, doc);

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }

  /**
   * This is a method that adds a new month node to a portfolio if the given month doesn't already
   * exist.
   *
   * @param doc    the given XML document for the portfolio
   * @param parent the given parent node
   * @param t      the given transaction
   */
  public void addNewDateTag(Document doc, Node parent, Transaction t) {
    String[] dateSplit = t.getDate().split("-");
    Element dateTag = doc.createElement("date");
    parent.appendChild(dateTag);
    Attr newYear = doc.createAttribute("year");
    newYear.setValue(dateSplit[0]);
    Attr newMonth = doc.createAttribute("month");
    newMonth.setValue(dateSplit[1]);
    Attr newDay = doc.createAttribute("day");
    newDay.setValue(dateSplit[2]);
    dateTag.setAttributeNode(newYear);
    dateTag.setAttributeNode(newMonth);
    dateTag.setAttributeNode(newDay);
    this.addNewTransactionTag(doc, dateTag, t);
  }

  /**
   * This is a method that adds a new transaction node to a portfolio when the user decides
   * to buy/sell/re-balance their portfolio.
   *
   * @param doc        the given XML document for the portfolio
   * @param parentNode the given parent node for the transaction
   * @param t          the given transaction
   */
  public void addNewTransactionTag(Document doc, Node parentNode, Transaction t) {
    Element transactionTag = null;
    transactionTag = doc.createElement("transaction");
    parentNode.appendChild(transactionTag);
    String transactionType = "";
    if (t.getShares() > 0) {
      transactionType = "buy";
    } else {
      transactionType = "sell";
    }
    Attr type = doc.createAttribute("type");
    type.setValue(transactionType);
    transactionTag.setAttributeNode(type);

    Element stockTag = doc.createElement("stock");
    stockTag.appendChild(doc.createTextNode(t.getStock()));
    transactionTag.appendChild(stockTag);

    Element sharesTag = doc.createElement("shares");
    sharesTag.appendChild(doc.createTextNode("" + t.getShares()));
    transactionTag.appendChild(sharesTag);

    Element priceTag = doc.createElement("price");
    String price = fp.getStockPrice(t.getStock(), t.getDate());
    priceTag.appendChild(doc.createTextNode(price));
    transactionTag.appendChild(priceTag);
  }
}