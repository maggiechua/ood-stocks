package stocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class constructs a Portfolio object containing a name, contents, and a log of transactions.
 */
public class Portfolio {
  private String name;
  private HashMap<String, Double> contents;
  private List<Transaction> transactions;
  private FileParser fp;

  /**
   * This constructor creates a Portfolio object.
   * @param name the given name of the portfolio
   * @param contents the existing contents of the portfolio
   * @param transactions a list of transactions for this portfolio
   */
  public Portfolio(String name, HashMap<String, Double> contents, List<Transaction> transactions) {
    this.name = name;
    this.contents = contents;
    this.transactions = transactions;
    this.fp = new FileParser();
  }

  public String getName() {
    return this.name;
  }

  public HashMap<String, Double> getPortfolioContents() {
    return this.contents;
  }

  public List<Transaction> getTransactionsLog() {
    return this.transactions;
  }

  /**
   * The following method adds purchased shares to the portfolio.
   * @param stock the given stock
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio addToPortfolio(String stock, double shares) {
    HashMap<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      currentPortfolio.put(stock, currentPortfolio.get(stock) + shares);
    }
    else {
      currentPortfolio.put(stock, shares);
    }
    return new Portfolio(this.name, currentPortfolio, this.transactions);
  }

  /**
   * The following method removes sold shares from the portfolio.
   * @param stock the given stock
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio removeFromPortfolio(String stock, double shares) {
    HashMap<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      if (currentPortfolio.get(stock) >= shares) {
        currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
      } else {
        throw new IllegalArgumentException("not enough shares to sell");
      }
    } else {
      throw new IllegalArgumentException("you don't own this stock");
    }
    return new Portfolio(this.name, currentPortfolio, this.transactions);
  }

  /**
   * The following method calculates the monetary value of a portfolio based on a specified date.
   * @param date the given date
   * @return the total value of the portfolio based on the given date
   */
  public double calculateValue(String date) {
    double portfolioValue = 0.0;
    for (Map.Entry<String, Double> stock: contents.entrySet()) {
      Double stockPrice = Double.parseDouble(fp.getStockPrice(stock.getKey(), date));
      portfolioValue += stockPrice * stock.getValue();
    }
    return portfolioValue;
  }

  /**
   * The following method finds the distribution of value within this portfolio.
   * @param date the given date
   * @return a hashmap of the portfolio showing the monetary value for each stock
   */
  public HashMap<String, Double> findDistribution(String date) {
    HashMap<String, Double> dist = new HashMap<>();
    for (Map.Entry<String, Double> stock: this.contents.entrySet()) {
      Double stockPrice = Double.parseDouble(fp.getStockPrice(stock.getKey(), date));
      dist.put(stock.getKey(), stock.getValue() * stockPrice);
    }
    return dist;
  }

}
