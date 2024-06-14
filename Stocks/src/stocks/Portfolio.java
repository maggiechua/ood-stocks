package stocks;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * @param date the given date to load the portfolio to
   */
  public void loadPortfolio(Path path, String date);

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
   * The following method loads the contents of the portfolio according to the given list of
   * transactions.
   * @param transactions a list of transactions made in a portfolio
   */
  public void loadContents(List<Transaction> transactions);

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
   * The following method finds the distribution of value within this portfolio.
   * @param date the given date
   * @return a hashmap of the portfolio showing the monetary value for each stock
   */
  public HashMap<String, Double> findDistribution(String date);


}