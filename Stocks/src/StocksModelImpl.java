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
    return null;
  }

  @Override
  public StocksModelImpl sell(StocksModel stock, Integer shares, String portfolioName) {
    return null;
  }
}
