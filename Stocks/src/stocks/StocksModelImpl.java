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

  public StocksModelImpl() {
    this.stock = "";
    this.portfolios = new HashMap<String, HashMap<String, Integer>>();
  }

  public StocksModelImpl(String stock) {
    this.stock = stock;
  }

  protected StocksModelImpl(String stock, HashMap<String, HashMap<String, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
  }

  public static void API() {
    String apiKey = "5APRD6N4EPK0WCIS";
  }

  public List<Double> getStockInfo(String stockSymbol, Integer numOfDays, String date) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = userDirectory + "/Stocks/data/";
    String fileName = stockSymbol + ".csv";
    Path path = Paths.get(directoryPath + fileName);
    File file = path.toFile();

    List<Double> output = new ArrayList<>();
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        if (lineInfo[0].equals(date)) {
          output.add(Double.parseDouble(lineInfo[4]));
          for (int i = 0; i < numOfDays - 1; i++) {
            output.add(Double.parseDouble(lineInfo[4]));
          }
        }
      }
    }
    catch (IOException e) {
      System.out.println("catch");
    }
    return output;
  }

  @Override
  public StocksModelImpl stockSelect(String stock) {
    return new StocksModelImpl(stock);
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    List<Double> priceData = this.getStockInfo("NVDA", numOfDays, date);
    Double lastDate = priceData.get(priceData.size() - 1);
    Double startDate = priceData.get(0);
    return lastDate - startDate;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    List<Double> priceData = this.getStockInfo("NVDA", numOfDays, date);
    Double sum = 0.0;
    for (Double price : priceData) {
      sum += price;
    }
    return sum / priceData.size();
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    Double movingAvg =  this.movingAvg(numOfDays, date);
    List<Double> priceData = this.getStockInfo("NVDA", 1, date);
    if (priceData.get(0) > movingAvg) {
      return "yes";
    }
    return "no";
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    HashMap<String, HashMap<String, Integer>> pfs = this.portfolios;
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
