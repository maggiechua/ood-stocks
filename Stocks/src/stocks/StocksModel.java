package stocks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This interface represents the methods for the model section of the program.
 */
public interface StocksModel extends ReadOnlyModel {
  /**
   * the stockSelect method saves a stock to the class.
   * @param stock a string representing the stock symbol
   * @return a new StocksModelImpl with the stock saved
   */
  public StocksModelImpl stockSelect(String stock);

  /**
   * the gainLoss method calculates the gain or loss of the stock saved in the class.
   * @param numOfDays the number of days in the range the user is searching the gain or loss of
   * @param date the starting date for the range
   * @return a Double representing the gain or loss of the stock
   */
  public Double gainLoss(Integer numOfDays, String date);

  /**
   * the movingAvg method calculates the x-day moving average of the stock saved in the class.
   * @param numOfDays the specific x value in the x-day moving average
   * @param date the specified date the user is checking
   * @return a Double representing the x-day moving average of the stock
   */
  public Double movingAvg(Integer numOfDays, String date);

  /**
   * the crossovers method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param numOfDays the specific x value in the x-day crossover
   * @param date the specified date the user is checking
   * @return a String representing whether the day is an x-day crossover.
   */
  public String crossovers(Integer numOfDays, String date);

  /**
   * the createPortfolio method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param name the name for the portfolio
   * @return a StocksModelImpl with the new portfolio saved
   */
  public StocksModelImpl createPortfolio(String name);

  /**
   * the buy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param shares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   * @return a StocksModelImpl with the changed portfolio data
   */
  public StocksModelImpl buy(Integer shares, String date, String portfolioName);

  /**
   * the buy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param stock the symbol of the stock
   * @param shares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   * @return a StocksModelImpl with the changed portfolio data
   */
  public StocksModelImpl sell(String stock, Integer shares, String date, String portfolioName);


  /**
   * the buy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param portfolioName the name of the portfolio
   * @param date the date the user is checking the portfolio value of
   * @return a Double of the portfolio value
   */
  public Double portfolioValue(String portfolioName, String date);

  /**
   * the composition method produces a list of stocks with their shares.
   * @param portfolioName the name of the portfolio
   * @param date the date the user is checking the portfolio composition of
   * @return a HashMap of one portfolio
   */
  public HashMap<String, Double> composition(String portfolioName, String date);

  /**
   * the distribution method produces a list of stocks and their respective values in the portfolio.
   * @param portfolioName the name of the portfolio
   * @param date the date the user is checking the portfolio distribution of
   * @return a HashMap of stocks with their values
   */
  public HashMap<String, Double> distribution(String portfolioName, String date);

  /**
   * the bar method calculates the performance of the portfolio over time.
   * @param portfolioName the name of the portfolio
   * @param date1 the starting date the user is checking the portfolio performance of
   * @param date2 the end date the user is checking the portfolio performance of
   * @return a HashMap of dates with values
   */
  public HashMap<String, Double> bar(String portfolioName, String date1, String date2)
          throws ParseException;

  /**
   * the balance method re-balances a portfolio.
   * @param portfolioName the name of the portfolio
   * @param date the date to re-balance the portfolio
   * @param weights the weights of each stock
   * @return a new StocksModelImpl with the changed portfolio data
   */
  public StocksModelImpl balance(String portfolioName, String date, HashMap<String,
          Double> weights);

  /**
   * the stockCount gets all the stocks of a portfolio into one list.
   * @param portfolioName the name of the portfolio
   * @return an ArrayList of the stocks in a portfolio
   */
  public ArrayList<String> stockCount(String portfolioName);
}
