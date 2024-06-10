package stocks;

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
}
