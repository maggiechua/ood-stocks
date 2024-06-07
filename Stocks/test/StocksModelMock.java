import java.util.HashMap;

import stocks.StocksModel;
import stocks.StocksModelImpl;

/**
 * The StocksModelMock is a class that represents an imitation of our model for
 * testing purposes.
 */
public class StocksModelMock implements StocksModel {
  @Override
  public StocksModelImpl stockSelect(String stock) {
    return null;
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    return 0.0;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    return 0.0;
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    return "";
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    return null;
  }

  @Override
  public StocksModelImpl buy(Integer shares, String portfolioName) {
    return null;
  }

  @Override
  public StocksModelImpl sell(String stock, Integer shares, String portfolioName) {
    return null;
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    return 0.0;
  }

  @Override
  public String getStock() {
    return "";
  }

  @Override
  public HashMap<String, HashMap<String, Integer>> getPortfolios() {
    return null;
  }
}
