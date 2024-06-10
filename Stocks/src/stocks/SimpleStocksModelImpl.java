package stocks;

import java.util.HashMap;

//TODO: UPDATE PORTFOLIO FEATURES
  //TODO: purchase specific stock on a specified date
  //TODO: sell stock on a specified date (allows user to sell fractional shares)
//TODO: NEW PORTFOLIO FEATURES
  //TODO: portfolio can be saved/loaded (recommend standard file formats: XML/JSON)
  //TODO: re-balancing a portfolio
  //  user gives an ideal distribution of stock values (i.e. 40/20/20/20)
  //  can result in fractional ownership of stocks that can be sold
//TODO: PERFORMANCE OVER TIME
// # of lines for timestamps must be min 5, max 30 (minimum can be below 5 for day)
// must include the following timestamps: day/month/year
//    day -> computed with closing price
//    month -> computed with last working day closing price of month
//    year -> computed with last working day closing price of year
// relative scale may be needed (given * = 1000), but scale must be shown on all charts

/**
 * This class represents a simple version of the stocks program. It accesses stock data through
 * the use of the AlphaVantage API and stores it locally for further use. Additionally, it allows
 * for the creation of portfolios and buying/selling of whole-number shares.
 */
public class SimpleStocksModelImpl extends AbstractStocksModel {

  /**
   * This makes a new SimpleStocksModel Implementation.
   * @param stock the String representing the stock symbol
   * @param portfolios the hashmap holding the portfolios of the user
   */
  public SimpleStocksModelImpl(String stock, HashMap<String, HashMap<String, Integer>> portfolios) {
    super(stock, portfolios);
  }

  @Override
  public SimpleStocksModelImpl buy(Integer shares, String portfolioName) {
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
    return new SimpleStocksModelImpl(this.stock, pfs);
  }

  @Override
  public SimpleStocksModelImpl sell(String stock, Integer shares, String portfolioName) {
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
    return new SimpleStocksModelImpl(stock, pfs);
  }
}
