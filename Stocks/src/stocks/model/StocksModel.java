package stocks.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface represents the model section of the StocksProgram, allowing for
 * performing functions such as determining the gain-loss, moving-average, crossovers for a stock;
 * the portfolio value, composition, and distribution; and displaying the value of a
 * stock/portfolio over a specified date range.
 */
public interface StocksModel extends ReadOnlyModel {
  /**
   * the stockSelect method saves a stock to the class.
   * @param stock a string representing the stock symbol
   * @return a new StocksModelImpl with the stock saved
   */
  public StocksModel stockSelect(String stock);

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
   * the loadPortfolios method loads the saved portfolio(s)' contents from XML files into
   * PortfolioImpl objects.
   */
  public StocksModel loadPortfolios();

  /**
   * the savePortfolios method saves all the portfolios contents within the program into XML files.
   */
  public void savePortfolios();

  /**
   * the createPortfolio method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param name the name for the portfolio
   * @return a StocksModelImpl with the new portfolio saved
   */
  public StocksModel createPortfolio(String name);

  /**
   * the buy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param shares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   * @return a StocksModel with the changed portfolio data
   */
  public StocksModel buy(double shares, String date, String portfolioName);

  /**
   * the buy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param stock the symbol of the stock
   * @param shares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   * @return a StocksModel with the changed portfolio data
   */
  public StocksModel sell(String stock, Integer shares, String date, String portfolioName);


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
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date the user is checking the portfolio composition of
   * @return a Map of one portfolio
   */
  public Map<String, Double> composition(String portfolioName, String date);

  /**
   * the distribution method produces a list of stocks and their respective values in the portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date the user is checking the portfolio distribution of
   * @return a Map of stocks with their values
   */
  public Map<String, Double> distribution(String portfolioName, String date);

  /**
   * the bar method calculates the performance of the portfolio over time.
   * @param portfolioName the name of the portfolio
   * @param date1 the starting date the user is checking the portfolio performance of
   * @param date2 the end date the user is checking the portfolio performance of
   * @return a HashMap of dates with values
   */
  public Map<String, Double> bar(String portfolioName, String date1, String date2)
          throws ParseException;

  /**
   * the balance method re-balances a portfolio.
   * @param portfolioName the name of the portfolio
   * @param date the date to re-balance the portfolio
   * @param weights the weights of each stock
   * @return a new StocksModelImpl with the changed portfolio data
   */
  public StocksModel balance(String portfolioName, String date, Map<String,
          Double> weights);

  /**
   * the stockCount method gets all the stocks of a portfolio into one list.
   * @param portfolioName the name of the portfolio
   * @return an ArrayList of the stocks in a portfolio
   */
  public List<String> stockCount(String portfolioName);

  /**
   * the makeScale method calculates a scale for the bar chart based on its data.
   * @param barData the data of the bar chart
   * @return an integer for the scale
   */
  public Integer makeScale(HashMap<String,Double> barData);

  /**
   * The following method determines if the given date is a valid market day.
   * @param date the given date
   * @return a boolean where true is a valid market day and false is a non-market day
   */
  public boolean validMarketDay(String date);

  /**
   * The following method returns the next valid market day.
   * @param date the given date
   * @return a string of the next valid market date
   */
  public String nextMarketDay(String date);

  /**
   * The following method reorders the hashmap in chronological order.
   * @param input the hashmap of bar data
   * @return an arraylist of the dates in order
   */
  public List<String> reorder(Map<String, Double> input) throws ParseException;
}
