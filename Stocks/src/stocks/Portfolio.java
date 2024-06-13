package stocks;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class constructs a Portfolio object containing a name, contents, and a log of transactions.
 */
public class Portfolio {
  private String name;
  private Map<String, Double> contents;
  private List<Transaction> transactions;
  private FileCreator fc;
  private FileParser fp;
  private CompareTransaction cp;

  /**
   * This constructor creates a Portfolio object.
   * @param name the given name of the portfolio
   * @param contents the existing contents of the portfolio
   * @param transactions a list of transactions for this portfolio
   */
  public Portfolio(String name, Map<String, Double> contents, List<Transaction> transactions) {
    this.name = name;
    this.contents = contents;
    this.transactions = transactions;
    this.fc = new FileCreator();
    this.fp = new FileParser();
    this.cp = new CompareTransaction();
  }

  public String getName() {
    return this.name;
  }

  public Map<String, Double> getPortfolioContents() {
    return this.contents;
  }

  public List<Transaction> getTransactionsLog() {
    return this.transactions;
  }

  /**
   * the loadPortfolio method loads data from this portfolio into Portfolio objects local
   * to the program up to a specific date.
   * @param date the given date to load the portfolio to
   */
  public void loadPortfolio(Path path, String date) {

  }

  /**
   * the savePortfolio method saves this portfolio.
   */
  public void savePortfolio() {
    List<Transaction> sortedTransactions = this.sortTransactions();
    fc.createNewPortfolioFile(this.name, sortedTransactions);
  }

  /**
   * The following method adds a transaction to the transaction log.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares
   */
  public void addTransaction(String stock, String date, double shares) {
    Transaction t = new Transaction(stock, shares, date,
            Double.parseDouble(fp.getStockPrice(stock, date)));
    transactions.add(t);
  }

  /**
   * The following method sorts this portfolio's lists of transactions in chronological order.
   * @return a list of sorted transactions
   */
  public List<Transaction> sortTransactions() {
    List<Transaction> lot = transactions;
    Collections.sort(lot, cp);
    return lot;
  }

  /**
   * The following method loads the contents of the portfolio according to the given list of
   * transactions.
   * @param transactions a list of transactions made in a portfolio
   */
  public void loadContents(List<Transaction> transactions) {
    //TODO: also pass in a hashset to represent stocks 
  }

  /**
   * The following method adds purchased shares to the portfolio.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio addToPortfolio(String stock, String date, double shares) {
    Map<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      currentPortfolio.put(stock, currentPortfolio.get(stock) + shares);
    }
    else {
      currentPortfolio.put(stock, shares);
    }
    this.addTransaction(stock, date, shares);
    return new Portfolio(this.name, currentPortfolio, this.transactions);
  }

  /**
   * The following method removes sold shares from the portfolio.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio removeFromPortfolio(String stock, String date, double shares) {
    Map<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      if (currentPortfolio.get(stock) >= shares) {
        currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
        this.addTransaction(stock, date, -shares);
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
