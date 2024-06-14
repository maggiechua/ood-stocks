package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
 * This class represents file making data. Its purpose is to create saved files for the program.
 */
public class FileCreator {
  FileParser parse;

  public FileCreator() {
    this.parse = new FileParser();
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
    String directoryPath = "Stocks/res/data/";
    String fileName = stockSymbol + ".csv";
    String path = directoryPath + fileName;
    this.createFile(path, output);
  }

  /**
   * This is a method to create a file at the given path location containing the given data.
   *
   * @param path the given path of the file
   * @param data the given data to be written into the file
   */
  public void createFile(String path, StringBuilder data) {
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
   * @param lot           the given list of sorted transactions
   */
  public void createNewPortfolioFile(String portfolioName, List<Transaction> lot) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = "Stocks/res/portfolios/";
    String fileName = portfolioName + ".xml";
    String path = userDirectory + directoryPath + fileName;
    this.createXMLFile(path, portfolioName, lot);
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
  public void createXMLFile(String path, String portfolioName, List<Transaction> lot) {
    Path filePath = Paths.get(path);
    File file = filePath.toFile();

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

      //adding the years with transactions
      SortedSet<String> yearNodes = new TreeSet<>();
      for (Transaction t : lot) {
        String[] dateSplit = t.getDate().split("-");
        yearNodes.add(dateSplit[0]);
      }
      for (String year : yearNodes) {
        this.addNewYearTag(doc, rootElement, year);
      }

      //adding the months with transactions
      List<Set<String>> monthNodesList = new ArrayList<Set<String>>();
      List<List<String>> dayNodesList = new ArrayList<>();
      List<List<Transaction>> transactionsList = new ArrayList<>();
      for (String yearNode : yearNodes) {
        SortedSet<String> monthNodes = new TreeSet<String>();
        List<String> dayNodes = new ArrayList<>();
        List<Transaction> transactionNodes = new ArrayList<>();
        for (Transaction t : lot) {
          String[] dateSplit = t.getDate().split("-");
          if (dateSplit[0].equals(yearNode)) {
            monthNodes.add(dateSplit[1]);
            dayNodes.add(dateSplit[2]);
            transactionNodes.add(t);
          }
        }
        monthNodesList.add(monthNodes);
        dayNodesList.add(dayNodes);
        transactionsList.add(transactionNodes);
      }
      NodeList yNodes = rootElement.getChildNodes();
      int index = 0;
      for (Set<String> monthNodes : monthNodesList) {
        for (String month : monthNodes) {
          for (int i = 0; i < dayNodesList.get(index).size(); i++) {
            this.addNewDateTag(doc, yNodes.item(index), month,
                    transactionsList.get(index).get(i));
          }
        }
        index++;
      }

      //write the content into xml file
      this.writeToXMLFile(path, doc);

    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }

  /**
   * This is a method that adds a new year node to a portfolio if the given year doesn't already
   * exist.
   *
   * @param doc    the given XML document for the portfolio
   * @param parent the given parent node
   * @param year   the given year
   */
  public void addNewYearTag(Document doc, Node parent, String year) {
    Element yearTag = doc.createElement("year");
    parent.appendChild(yearTag);
    Attr newYear = doc.createAttribute("y");
    newYear.setValue(year);
    yearTag.setAttributeNode(newYear);
  }

  /**
   * This is a method that adds a new month node to a portfolio if the given month doesn't already
   * exist.
   *
   * @param doc    the given XML document for the portfolio
   * @param parent the given parent node
   * @param month  the given month
   * @param t      the given transaction
   */
  public void addNewDateTag(Document doc, Node parent, String month, Transaction t) {
    Element dateTag = doc.createElement("date");
    parent.appendChild(dateTag);
    Attr newMonth = doc.createAttribute("month");
    newMonth.setValue(month);
    Attr newDay = doc.createAttribute("day");
    String[] dateSplit = t.getDate().split("-");
    newDay.setValue(dateSplit[2]);
    dateTag.setAttributeNode(newMonth);
    dateTag.setAttributeNode(newDay);
    this.addNewTransactionTag(doc, dateTag, t);
  }

  /**
   * This is a method that adds a new stock node to a portfolio if it doesn't already exist.
   *
   * @param doc    the given XML document for the portfolio
   * @param parent the given parent tag in the XML document
   * @param t      the given transaction
   */
  public void addNewStockTag(Document doc, Node parent, Transaction t) {
    Element stockTag = doc.createElement("stock");
    parent.appendChild(stockTag);
    Attr newStock = doc.createAttribute("symbol");
    newStock.setValue(t.getStock());
    stockTag.setAttributeNode(newStock);
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
    String[] dateSplit = t.getDate().split("-");
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

    this.addNewStockTag(doc, transactionTag, t);

    Element sharesTag = doc.createElement("shares");
    sharesTag.appendChild(doc.createTextNode("" + t.getShares()));
    transactionTag.appendChild(sharesTag);

    Element priceTag = doc.createElement("price");
    String price = parse.getStockPrice(t.getStock(), t.getDate());
    priceTag.appendChild(doc.createTextNode(price));
    transactionTag.appendChild(priceTag);
  }
}