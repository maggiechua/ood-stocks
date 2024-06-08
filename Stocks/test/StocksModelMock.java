import java.io.IOException;
import java.util.HashMap;

import stocks.StocksModel;
import stocks.StocksModelImpl;

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
    return null;
  }

  @Override
  public HashMap<String, HashMap<String, Integer>> getPortfolios() {
    return null;
  }
}
