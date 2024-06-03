import java.util.HashMap;
import java.util.Map;

public class StocksModelImpl implements StocksModel {
  StocksModel stock;
  HashMap<String,HashMap<StocksModel, Integer>> portfolios;

  public StocksModelImpl(StocksModel stock) {
    this.stock = stock;
  }

  protected StocksModelImpl(StocksModel stock, HashMap<String, HashMap<StocksModel, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
  }

  public static void API() {
    String apiKey = "5APRD6N4EPK0WCIS";
  }

  @Override
  public StocksModelImpl stockSelect(StocksModel stock) {
    return new StocksModelImpl(stock);
  }

  @Override
  public Integer gainLoss(String date1, String date2) {
    return null;
  }

  @Override
  public Integer movingAvg(Integer numOfDays, String date) {
    return null;
  }

  @Override
  public String crossovers(Integer numOfDays, String date1, String date2) {
    return null;
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    HashMap<String, HashMap<StocksModel, Integer>> pfs = this.portfolios;
    HashMap<StocksModel, Integer> newp = new HashMap<>();
    pfs.put(name, newp);
    return new StocksModelImpl(this, pfs);
  }

  @Override
  public StocksModelImpl buy(StocksModel stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<StocksModel, Integer>> pfs = this.portfolios;
    HashMap<StocksModel, Integer> currentportfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    if (currentportfolio.containsKey(stock)) {
      currentportfolio.put(stock, currentportfolio.get(stock) + shares);
    }
    else {
      currentportfolio.put(stock, shares);
    }
    pfs.put(portfolioName, currentportfolio);
    return new StocksModelImpl(stock, pfs);
  }

  @Override
  public StocksModelImpl sell(StocksModel stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<StocksModel, Integer>> pfs = this.portfolios;
    HashMap<StocksModel, Integer> currentportfolio = pfs.get(portfolioName);
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
    return new StocksModelImpl(stock, pfs);
  }
}
