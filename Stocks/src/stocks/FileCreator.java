package stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents file making data. Its purpose is to create saved files for the program.
 */
public class FileCreator {
  /**
   * Connect to AlphaVantageAPI and create a new CSV file with stock data.
   * @param stockSymbol the inputted stockSymbol
   * @param apiKey the API key to access the API data
   */
  public void createStockCSVFile(String stockSymbol, String apiKey) {
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    String directoryPath = "Stocks/res/data/";
    String fileName = stockSymbol + ".csv";
    String path = directoryPath + fileName;
    this.createFile(path, output);
  }

  /**
   * This is a method to create a file at the given path location containing the given data.
   * @param path the given path of the file
   * @param data the given data to be written into the file
   */
  public void createFile(String path, StringBuilder data) {
    try {
      File file = new File(path);
      FileWriter writer = new FileWriter(file);
      writer.write(data.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

//   public void createPortfolioFile(String portfolioName, String stockSymbol, Integer shares,
//                                   String date) {
//     String userDirectory = System.getProperty("user.dir");
//     String directoryPath = userDirectory + "/Stocks/res/";
//     String fileName = portfolioName + ".csv";
//     Path path = Paths.get(directoryPath + fileName);
//     File file = path.toFile();

//     if (!file.exists()) {
//       StringBuilder output = new StringBuilder();
//       output.append(stockSymbol + "," + shares + "," + date + "\n");
//       try {
//         file = new File(directoryPath + fileName);
//         FileWriter writer = new FileWriter(file);
//         writer.write(output.toString());
//       } catch (IOException e) {
//         throw new RuntimeException(e);
//       }
//     }
//     else {

//     }
  
  /**
   * This is a method to create a new portfolio file with the given name.
   * @param portfolioName the name of the given portfolio
   */
  public void createNewPortfolioFile(String portfolioName) {
    StringBuilder header = new StringBuilder();
    header.append("Stock, Share(s), Date");
    String directoryPath = "/Stocks/res/portfolios/";
    String fileName = portfolioName + ".txt";
    String path = directoryPath + fileName;
    this.createFile(path, header);
  }
}
