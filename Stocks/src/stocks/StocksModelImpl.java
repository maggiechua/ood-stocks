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

public class StocksModelImpl implements StocksModel {
  String stock;
  HashMap<String,HashMap<String, Integer>> portfolios;
  AlphaVantageDemo api;

  public StocksModelImpl(String stock, HashMap<String, HashMap<String, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
    this.api = new AlphaVantageDemo();
  }

  public String getAPIKey() {
    return "5APRD6N4EPK0WCIS";
  }

  protected List<Double> getStockInfo(String stockSymbol, Integer numOfDays, String date) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = userDirectory + "/res/data/";
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
        else if (counter == numOfDays) {
          break;
        }
        else if (counter > 0) {
          output.add(Double.parseDouble(lineInfo[4]));
          counter++;
        }
      }
    }
    catch (IOException e) {
      System.out.println("Stock not found.");
    }
    return output;
  }

  @Override
  public StocksModelImpl stockSelect(String s) {
    return new StocksModelImpl(s, this.portfolios);
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    Double lastDate = priceData.get(priceData.size() - 1);
    Double startDate = priceData.get(0);
    return startDate - lastDate;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    Double sum = 0.0;
    for (Double price : priceData) {
      sum += price;
    }
    return sum / priceData.size();
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    Double movingAvg =  this.movingAvg(numOfDays, date);
    List<Double> priceData = this.getStockInfo(this.stock, 1, date);
    if (priceData.get(0) > movingAvg) {
      return "Yes, this date is a " + numOfDays + "-day crossover.";
    }
    return "No, this date is not a " + numOfDays + "-day crossover.";
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
    pfs.remove(portfolioName);
    try {
      if (currentPortfolio.containsKey(stock)) {
        if (currentPortfolio.get(stock) >= shares) {
          currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
        } else {
          throw new IllegalArgumentException("not enough shares to sell");
        }
      } else {
        throw new IllegalArgumentException("you don't own this stock");
      }
    }
    catch (Exception e) {
      System.err.println("Not enough shares of this stock in this portfolio to sell.");
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
}
