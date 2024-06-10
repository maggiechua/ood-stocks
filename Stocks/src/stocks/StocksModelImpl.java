package stocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents the model of the stock program. It stores stock dara and portfolios for
 * the user as well as accesses the API and saved files for stock data.
 */
public class StocksModelImpl implements StocksModel {
  private String stock;
  private HashMap<String,HashMap<String, Integer>> portfolios;
  private AlphaVantageDemo api;

  /**
   * This makes a new StocksModel Implementation.
   * @param stock the String representing the stock symbol
   * @param portfolios the hashmap holding the portfolios of the user
   */
  public StocksModelImpl(String stock, HashMap<String, HashMap<String, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
    this.api = new AlphaVantageDemo();
  }

  /**
   * the getAPIKey method stores and returns the API key used for the program.
   */
  public String getAPIKey() {
    return "5APRD6N4EPK0WCIS";
  }

  /**
   * the createPortfolio method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param stockSymbol the stock symbol
   * @param numOfDays the number of days in the range to search for
   * @param date the date to find the data of
   * @return a List of data in Double format
   */
  protected List<Double> getStockInfo(String stockSymbol, Integer numOfDays, String date) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = userDirectory + "/Stocks/res/data/";
    String fileName = stockSymbol + ".csv";
    Path path = Paths.get(directoryPath + fileName);
    File file = path.toFile();

    List<Double> output = new ArrayList<>();
    if (!file.exists()) {
      api.createStockCSVFile(stock, this.getAPIKey());
    }
    try {
      Scanner sc = new Scanner(file);
      int counter = 0;
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        if (lineInfo[0].equals(date)) {
          output.add(Double.parseDouble(lineInfo[4]));
          counter++;
        }
        else if (counter - 1 == numOfDays) {
          break;
        }
        else if (counter > 0) {
          output.add(Double.parseDouble(lineInfo[4]));
          counter++;
        }
      }
    }
    catch (IOException e) {
      System.out.println("The stock did not exist on the date given.");
    }
    return output;
  }

  @Override
  public StocksModelImpl stockSelect(String s) {
    return new StocksModelImpl(s, this.portfolios);
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    Double lastDate = 0.0;
    Double startDate = 0.0;
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    try {
      lastDate = priceData.get(priceData.size() - 1);
      startDate = priceData.get(0);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The gain-loss cannot be calculated as " +
              "the given date does not exist or does not contain a valid range.");
    }
    return startDate - lastDate;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    Double sum = 0.0;
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    try {
      priceData.remove(priceData.size() - 1);
      for (Double price : priceData) {
        sum += price;
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The moving average cannot be calculated as " +
              "the given date does not exist or does not contain a valid range.");
    }
    return sum / numOfDays;
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    String message = "";
    Double movingAvg =  this.movingAvg(numOfDays, date);
    List<Double> priceData = this.getStockInfo(this.stock, 1, date);
    try {
      if (priceData.get(0) > movingAvg) {
        message = "Yes, this date is a " + numOfDays + "-day crossover.";
      }
      else {
        message = "No, this date is not a " + numOfDays + "-day crossover.";
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The moving average cannot be calculated as "
              + "the given date does not exist or does not contain a valid range.");
    }
    return message;
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    HashMap<String, HashMap<String, Integer>> pfs;
    if (this.portfolios == null) {
      pfs = new HashMap<String, HashMap<String, Integer>>();
    }
    else {
      pfs = this.portfolios;
    }
    HashMap<String, Integer> newp = new HashMap<>();
    pfs.put(name, newp);
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public StocksModelImpl buy(Integer shares, String portfolioName) {
    HashMap<String, HashMap<String, Integer>> pfs = this.portfolios;
    HashMap<String, Integer> currentPortfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    if (currentPortfolio.containsKey(this.stock)) {
      currentPortfolio.put(this.stock, currentPortfolio.get(this.stock) + shares);
    }
    else {
      currentPortfolio.put(this.stock, shares);
    }
    pfs.put(portfolioName, currentPortfolio);
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public StocksModelImpl sell(String stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<String, Integer>> pfs = this.portfolios;
    HashMap<String, Integer> currentPortfolio = pfs.get(portfolioName);
//    pfs.remove(portfolioName);
    if (currentPortfolio.containsKey(stock)) {
      if (currentPortfolio.get(stock) >= shares) {
        currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
      } else {
        throw new IllegalArgumentException("not enough shares to sell");
      }
    } else {
      throw new IllegalArgumentException("you don't own this stock");
    }
    pfs.put(portfolioName, currentPortfolio);
    return new StocksModelImpl(stock, pfs);
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    HashMap<String, Integer> portfolio = portfolios.get(portfolioName);
    double portfolioValue = 0.0;
    for (Map.Entry<String, Integer> stock: portfolio.entrySet()) {
      List<Double> stockValue = this.getStockInfo(stock.getKey(), 1, date);
      portfolioValue += stockValue.get(0) * stock.getValue().doubleValue();
    }
    return portfolioValue;
  }

  @Override
  public String getStock() {
    return stock;
  }

  @Override
  public HashMap<String, HashMap<String, Integer>> getPortfolios() {
    return portfolios;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    Double value = this.portfolioValue(name, date);
    return value;
  }
}
