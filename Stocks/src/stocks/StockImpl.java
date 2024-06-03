package stocks;

import java.util.HashMap;

public class StockImpl implements Stock {
  Stock stock;
  HashMap<String,HashMap<Stock, Integer>> portfolios;

  public StockImpl(Stock stock) {
    this.stock = stock;
  }

  protected StockImpl(Stock stock, HashMap<String, HashMap<Stock, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
  }

  public static void API() {
    String apiKey = "5APRD6N4EPK0WCIS";
  }

  @Override
  public StockImpl stockSelect(Stock stock) {
    return new StockImpl(stock);
  }

  @Override
  public Double gainLoss(String date1, String date2) {
    return null;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    return null;
  }

  @Override
  public String crossovers(Integer numOfDays, String date1, String date2) {
    return null;
  }

  @Override
  public StockImpl createPortfolio(String name) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> newp = new HashMap<>();
    pfs.put(name, newp);
    return new StockImpl(this, pfs);
  }

  @Override
  public StockImpl buy(Integer shares, String portfolioName) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> currentportfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    if (currentportfolio.containsKey(this.stock)) {
      currentportfolio.put(this.stock, currentportfolio.get(this.stock) + shares);
    }
    else {
      currentportfolio.put(this.stock, shares);
    }
    pfs.put(portfolioName, currentportfolio);
    return new StockImpl(this.stock, pfs);
  }

  @Override
  public StockImpl sell(Stock stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> currentportfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    try {
      if (currentportfolio.containsKey(stock)) {
        if (currentportfolio.get(stock) >= shares) {
          currentportfolio.put(stock, currentportfolio.get(stock) - shares);
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
    pfs.put(portfolioName, currentportfolio);
    return new StockImpl(stock, pfs);
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    return null;
  }
}
