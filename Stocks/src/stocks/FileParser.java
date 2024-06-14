package stocks;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
   * This method returns OS type. If the user OS type does not match the return, PLEASE change it.
   * "mac" for mac.
   * "windows" for windows.
   * This is an error to do with file path and the location of directories- we have not been able to
   * find a reason for this discrepancy, nor a solution for it.
   */
  public String getOSType() {
    return "mac";
  }

  /**
   * The retrievePath method returns the path to a selected file in a directory.
   * @param os OS for user computer
   * @param name name of the file
   * @param dir the directory the file is located in
   * @param fileType type of file the user is querying
   * @returns a path to the file
   */
  public Path retrievePath(String os, String name, String dir, String fileType) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = "";
    String fileName = name + fileType;
    if (os.equals("windows")) {
      directoryPath = userDirectory + "/Stocks/res/" + dir;
    }
    else if (os.equals("mac")) {
      directoryPath = userDirectory + "/res/" + dir;
    }
    Path path = Paths.get(directoryPath + fileName);
    return path;
  }

  /**
   * This is a method that retrieves the closing stock price for a given stock on a given day. 
   * @param stockSymbol the given stock symbol
   * @param date the given date expressed as YYYY-MM-DD
   * @return the given date's closing stock price as a String
   */
  protected String getStockPrice(String stockSymbol, String date) {
    Path path = this.retrievePath(this.getOSType(), stockSymbol, "data/", ".csv");
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
   * The following method gets the closing price of the last market day of a time period
   * (month/year).
   * @param stock the given stock
   * @param type the integer representation to determine where to split the string representation
   *             of a date
   * @param time the given integer representation of the time period (month/year)
   * @return the closing price of the last market day as a String
   */
  private String getLastDay(String stock, int type, int time) {
    Path path = this.retrievePath(this.getOSType(), stock, "data/", ".csv");
    File file = path.toFile();

    String stockPrice = "";
    String curDay = "";
    String nextDay = "";
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        curDay = lineInfo[0];
        if (sc.hasNextLine()) {
          String nextLine = sc.nextLine();
          String[] nextDaySplit = nextLine.split(",");
          nextDay = nextDaySplit[0];
          String[] nDaySplit = nextDay.split("-");
          if (Integer.parseInt(nDaySplit[type]) == time + 1) {
            stockPrice = curDay;
            break;
          }
        }
      }
    }
    catch (IOException e) {
      System.out.println("The stock did not exist based on the given time period.");
    }
    return stockPrice;
  }

  /**
   * The following method gets the last working day of the given stock for the given time period.
   * @param stock the given stock
   * @param timePeriod the given time period (either month/year) represented as a String
   * @param time the given integer representation of the month/year
   * @return the last working day as a String
   */
  public String getLastWorkingDay(String stock, String timePeriod, int time) {
    String lastDayPrice = "";
    if (timePeriod.equals("year")) {
      lastDayPrice = this.getLastDay(stock, 0, time);
    }
    else if (timePeriod.equals("month")) {
      lastDayPrice = this.getLastDay(stock, 1, time);
    }
    return lastDayPrice;
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

      NodeList byYear = doc.getElementsByTagName("year"); // <year></year>
      NodeList byT = doc.getElementsByTagName("date");
      for (int i = 0; i < byYear.getLength(); i++) {
        NodeList transactions = byYear.item(i).getChildNodes(); // <date></date>
        int year = Integer.parseInt(
                transactions.item(i).getAttributes().getNamedItem("y").getNodeValue());
        for (int s = 0; s < transactions.getLength(); s++) {
          int month = Integer.parseInt(
                  transactions.item(s).getAttributes().getNamedItem("month").getNodeValue());
          int day = Integer.parseInt(
                  transactions.item(s).getAttributes().getNamedItem("day").getNodeValue());
          String date = String.format("%04d-%02d-%02d", year, month, day);
          //<date day="09" month="03">
          //            <transaction type="buy">
          //                <stock symbol="NVDA"/>
          //                <shares>200.0</shares>
          //                <price>245.4400</price>
          //            </transaction>
          //        </date>
          NodeList t = transactions.item(i).getChildNodes(); // <transaction></transaction>
          String stock = t.item(0).toString();
          Double shares = Double.parseDouble(t.item(1).getNodeValue());
          Double price = Double.parseDouble(t.item(2).getNodeValue());
          transactionList.add(new Transaction(stock, shares, date, price));
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
