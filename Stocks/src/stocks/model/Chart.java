package stocks.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Chart {
  /**
   * the makeScale method calculates a scale for the bar chart based on its data.
   * @param barData the data of the bar chart
   * @return an integer for the scale
   */
  public Integer makeScale(HashMap<String,Double> barData);

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
   * the stockCount method gets all the stocks of a portfolio into one list.
   * @param portfolioName the name of the portfolio
   * @return an ArrayList of the stocks in a portfolio
   */
  public ArrayList<String> stockCount(String portfolioName);
}
