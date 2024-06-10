package stocks;

import java.util.HashMap;

/**
 * This class represents an advanced version of the stocks program. It accesses stock data through
 * the use of the AlphaVantage API and stores it locally for further use. Additionally, it allows
 * for the creation of portfolios and buying/selling of fractional shares.
 */
public class AdvancedStocksModelImpl extends AbstractStocksModel {

  /**
   * This makes a new AdvancedStocksModel Implementation.
   * @param stock the String representing the stock symbol
   * @param portfolios the hashmap holding the portfolios of the user
   */
  AdvancedStocksModelImpl(String stock, HashMap<String, HashMap<String, Integer>> portfolios) {
    super(stock, portfolios);
  }

  @Override
  public AbstractStocksModel buy(Integer shares, String portfolioName) {
    return null;
  }

  @Override
  public AbstractStocksModel sell(String stock, Integer shares, String portfolioName) {
    return null;
  }
}
