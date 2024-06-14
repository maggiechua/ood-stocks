package stocks;

import java.util.List;

/**
 * This class represents a read-only version of the model where only the fields of the model can
 * be accessed, but not mutated.
 */
public interface ReadOnlyModel {
  /**
   * A getter method that returns the selected stock.
   * @return the selected stock
   */
  public String getStock();

  /**
   * A getter method that returns existing portfolios.
   * @return a hashmap of all the users' existing portfolios
   */
  public List<Portfolio> getPortfolios();

  /**
   * A getter method that returns the existing portfolio value based on the given date.
   * @param name the given name of the portfolio
   * @param date the given date to check the portfolio value of
   * @return the existing portfolio value as a double.
   */
  public Double getPortfolioValue(String name, String date);
}
