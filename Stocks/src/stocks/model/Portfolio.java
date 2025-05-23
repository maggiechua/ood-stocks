package stocks.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface represents the methods for a portfolio.
 */
public interface Portfolio {

  /**
   * The following method gets the name of the portfolio.
   */
  public String getName();

  /**
   * The following method gets the contents of the portfolio.
   */
  public Map<String, Double> getPortfolioContents();

  /**
   * The following method gets the transaction log.
   */
  public List<Transaction> getTransactionsLog();

  /**
   * the loadPortfolio method loads data from this portfolio into Portfolio objects local
   * to the program up to a specific date.
   * @param path the given path to the portfolio file
   * @param date the given date to load the portfolio to
   * @param reload for distribution method
   * @return a map representing the portfolio up to a given date
   */
  public Map<String, Double> loadPortfolio(Path path, String date, boolean reload);

  /**
   * the savePortfolio method saves this portfolio.
   */
  public void savePortfolio();

  /**
   * The following method adds a transaction to the transaction log.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares
   */
  public void addTransaction(String stock, String date, double shares);

  /**
   * The following method sorts this portfolio's lists of transactions in chronological order.
   * @return a list of sorted transactions
   */
  public List<Transaction> sortTransactions();

  /**
   * The following method gets all the stock names that exist in a portfolio without duplicates.
   * @param tr a given list of transactions
   * @return a set of stocks represented as strings
   */
  public Set<String> getStocksList(List<Transaction> tr);

  /**
   * The following method loads the contents of the portfolio according to the given list of
   * transactions up to a given date.
   * @param stocks a set of stocks
   * @param transactions a given list of transactions
   * @param date a given date
   * @return a Map representing the portfolio up to a specified period.
   */
  public Map<String, Double> loadContents(Set<String> stocks, List<Transaction> transactions,
                                          String date);

  /**
   * The following method loads the total cost of a stock.
   * @param stocks a set of stocks
   * @param transactions a given list of transactions
   * @param date a given date
   * @return a Map representing the portfolio up to a specified period.
   */
  Map<String, Double> reloadContents(Set<String> stocks, List<Transaction> transactions,
                                     String date);

  /**
   * The following method adds purchased shares to the portfolio.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio addToPortfolio(String stock, String date, double shares);

  /**
   * The following method removes sold shares from the portfolio.
   * @param stock the given stock
   * @param date the given date
   * @param shares the number of shares purchased
   * @return a new updated portfolio
   */
  public Portfolio removeFromPortfolio(String stock, String date, double shares);

  /**
   * The following method calculates the monetary value of a portfolio based on a specified date.
   * @param date the given date
   * @return the total value of the portfolio based on the given date
   */
  public double calculateValue(String date);

  /**
   * The following method calculates the last value of a subset of time.
   * @param date the given date
   * @param type month or year
   * @returns a double for the value
   */
  public double calculateLastValue(String date, String type);

  /**
   * The following method finds the distribution of value within this portfolio.
   * @param date the given date
   * @return a map of the portfolio showing the monetary value for each stock
   */
  public Map<String, Double> findDistribution(String date);


}