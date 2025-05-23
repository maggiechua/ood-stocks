import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.Portfolio;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;

/**
 * The StocksModelMock is a class that represents an imitation of our model for
 * testing purposes.
 */
public class StocksModelMock implements StocksModel {
  private Appendable log;

  /**
   * StocksModelMock is a mock created purposely for testing.
   * @param ap a StringBuilder that represents all the commands that the controller
   *           calls when it receives inputs
   */
  StocksModelMock(Appendable ap) {
    this.log = ap;
  }

  /**
   * The following method appends the command called by the controller to the log.
   * @param result given string to add to the log of called commands.
   */
  public void appendResult(String result) {
    try {
      log.append(result);
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

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
  public StocksModel loadPortfolios() {
    return this;
  }

  @Override
  public void savePortfolios() {
    // TODO: this
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    return null;
  }

  @Override
  public StocksModelImpl buy(double shares, String date, String portfolioName) {
    return null;
  }

  @Override
  public StocksModelImpl sell(String stock, Integer shares, String date, String portfolioName) {
    return null;
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    return 0.0;
  }

  @Override
  public HashMap<String, Double> composition(String portfolioName, String date) {
    return null;
  }

  @Override
  public HashMap<String, Double> distribution(String portfolioName, String date) {
    return null;
  }

  @Override
  public HashMap<String, Double> bar(String portfolioName, String date1, String date2) {
    return null;
  }

  @Override
  public StocksModelImpl balance(String portfolioName, String date, Map<String,
            Double> weights) {
    return null;
  }

  @Override
  public ArrayList<String> stockCount(String portfolioName) {
    return null;
  }

  @Override
  public Integer makeScale(HashMap<String, Double> barData) {
    return 0;
  }

  @Override
  public boolean validMarketDay(String date) {
    return false;
  }

  @Override
  public String nextMarketDay(String date) {
    return "";
  }

  @Override
  public ArrayList<String> reorder(Map<String, Double> input) {
    return null;
  }

  @Override
  public String getStock() {
    return null;
  }

  @Override
  public List<Portfolio> getPortfolios() {
    return null;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    return 0.0;
  }
}
