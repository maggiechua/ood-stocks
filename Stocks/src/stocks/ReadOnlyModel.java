package stocks;

import java.util.HashMap;

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
  public HashMap<String, HashMap<String, Integer>> getPortfolios();
}
