package stocks;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This is a class that parses XML files.
 */
public class FileParser {
  /**
   * This is a method that retrieves the closing stock price for a given stock on a given day. 
   * @param stockSymbol the given stock symbol
   * @param date the given date expressed as YYYY-MM-DD
   * @return the given date's closing stock price as a String
   */
  protected String getStockPrice(String stockSymbol, String date) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = userDirectory + "/res/data/";
    String fileName = stockSymbol + ".csv";
    Path path = Paths.get(directoryPath + fileName);
    File file = path.toFile();

    String stockPrice = "";
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        if (lineInfo[0].equals(date)) {
          stockPrice = lineInfo[4];
          break;
        }
      }
    }
    catch (IOException e) {
      System.out.println("The stock did not exist on the date given.");
    }
    return stockPrice;
  }

  /**
   * The following method retrieves all the files in the portfolios directory.
   * @return all portfolio files as a set
   */
  public Set<Path> retrieveFilesInDirectory() {
    Set<Path> files = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(
            "Stocks/res/portfolios/"))) {
      for (Path path : stream) {
        files.add(path);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return files;
  }

  /**
   * The following method reads the information listed on a portfolio file.
   * @param path the given path to the desired portfolio file
   */
  public List<Transaction> parsePortfolioTransactions(Path path) {
    List<Transaction> transactionList = new ArrayList<>();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(path.toString());
      doc.getDocumentElement().normalize();

      NodeList byYear = doc.getElementsByTagName("year");
      for (int i = 0; i < byYear.getLength(); i++) {
        NodeList transactions = byYear.item(i).getChildNodes();
        for (int s = 0; s < transactions.getLength(); s++) {
          NodeList t = transactions.item(i).getChildNodes();

        }

      }
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
    return transactionList;
  }
}
