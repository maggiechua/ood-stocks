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
  private CompareDate cp;

  public FileParser() {
    this.cp = new CompareDate();
  }

  // This method returns OS type. If the user OS type does not match the return, PLEASE change it.
  // "mac" for mac.
  // "windows" for windows.
  // This is an error to do with file path and the location of directories- we have not been able to
  // find a reason for this discrepancy, nor a solution for it.
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
   * @param month the integer representation to determine where to split the string representation
   *             of a date
   * @param year the given integer representation of the time period (month/year)
   * @return the closing price of the last market day as a String
   */
  protected String getLastWorkingDay(String stock, int month, int year) {
    Path path = this.retrievePath(this.getOSType(), stock, "data/", ".csv");
    File file = path.toFile();
    boolean checkMonth = false;

    if (month != 0) {
      checkMonth = true;
    }

    String lastDay = "";
    String currDay = "";
    String nextDay = "";
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        currDay = line.split(",")[0];
        String[] lineInfo = line.split(",");
        if (sc.hasNextLine()) {
          String nextLine = sc.nextLine();
          String[] nextDaySplit = nextLine.split(",");
          nextDay = nextDaySplit[0];
          String[] nDaySplit = nextDay.split("-");
          if (Integer.parseInt(nDaySplit[0]) == year) {
            if (checkMonth && Integer.parseInt(nDaySplit[1]) == month) {
              lastDay = currDay;
              break;
            }
            else if (!checkMonth) {
              lastDay = nextDay;
              break;
            }
          }
        }
      }
    }
    catch (IOException e) {
      System.out.println("The stock did not exist based on the given time period.");
    }
    return lastDay;
  }

  /**
   * The following method gets the next market day based on the given date.
   * @param stock the given stock
   * @param date the given date
   * @return the next market day as a String
   */
  public String getNextMarketDay(String stock, String date) {
    Path path = this.retrievePath(this.getOSType(), stock, "data/", ".csv");
    File file = path.toFile();

    String nextMDay = "";
    int smallestDif = 100;
    try {
      Scanner sc = new Scanner(file);
      sc.nextLine();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        String curDay = lineInfo[0];
        int dayDif = cp.compare(curDay, date);
        if (dayDif < smallestDif && dayDif > 0) {
          smallestDif = dayDif;
          nextMDay = curDay;
        }
        else if (dayDif < 0) {
          break;
        }
      }
    }
    catch (IOException e) {
      System.out.println("The next market day is in the future.");
    }
    return nextMDay;
  }

  /**
   * The following method retrieves all the files in the portfolios directory.
   * @return all portfolio files as a set
   */
  public Set<Path> retrieveFilesInDirectory() {
    Set<Path> files = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(
            this.retrievePath(
                    this.getOSType(), "", "portfolios/", "").toString()))) {
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
   * @param upToDate the given date to retrieve transactions up to
   */
  public List<Transaction> parsePortfolioTransactions(Path path, String upToDate) {
    List<Transaction> transactionList = new ArrayList<>();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(path.toString());
      doc.getDocumentElement().normalize();

      NodeList byDate = doc.getElementsByTagName("date"); // <year></year>
      for (int i = 0; i < byDate.getLength(); i++) {
        Node dateNode = byDate.item(i);
        int year = Integer.parseInt(
                dateNode.getAttributes().getNamedItem("year").getNodeValue());
        int month = Integer.parseInt(
                dateNode.getAttributes().getNamedItem("month").getNodeValue());
        int day = Integer.parseInt(
                dateNode.getAttributes().getNamedItem("day").getNodeValue());
        String date = String.format("%04d-%02d-%02d", year, month, day);
        boolean addT = false;
        if (!upToDate.isEmpty()) {
          if (cp.compare(upToDate, date) >= 0) {
            addT = true;
          }
        }
        if (upToDate.isEmpty() || addT) {
          NodeList tr = dateNode.getChildNodes(); // <transaction></transaction>
          String[] trInfo = tr.item(1).getTextContent().split("\n            ");
          String stock = trInfo[1];
          Double shares = Double.valueOf(trInfo[2]);
          Double price = Double.valueOf(trInfo[3]);
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