package stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class represents the API data. Its purpose is to connect to the AlphaVantage API.
 */
public class AlphaVantageDemo {
  /**
   * Get the number at the specified cell.
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
              + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
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

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }

    String directoryPath = "Stocks/res/data/";
    try {
      String fileName = stockSymbol + ".csv";
      File file = new File(directoryPath + fileName);
      FileWriter writer = new FileWriter(file);
      writer.write(output.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
