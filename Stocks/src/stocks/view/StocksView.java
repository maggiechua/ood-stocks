package stocks.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

/**
 * This interface represents the methods for the view section of the program.
 */
public interface StocksView {
  /**
   * the welcomeMessage method adds the welcome message to the appendable in the class.
   */
  public void welcomeMessage();

  /**
   * the typeInstruct method adds a user instruction frame message to the appendable in the class.
   */
  public void typeInstruct();

  /**
   * the undefined method returns an undefined instruction message to the appendable in the class.
   */
  public void undefined();

  /**
   * the farewellMessage method adds a farewell message to the appendable in the class.
   */
  public void farewellMessage();

  /**
   * the printMenu method adds the initial menu text to the appendable in the class.
   */
  public void printMenu();

  /**
   * the printStockMenu method adds the stock menu text to the appendable in the class.
   */
  public void printStockMenu();

  /**
   * the returnResult method adds inputted text to the appendable in the class.
   * @param input the result to append
   */
  public void returnResult(String input);

  /**
   * the portfolioException method adds minor portfolio error text to the appendable in the class.
   * @param buy a boolean for if the shares are being bought or sold (true is bought)
   */
  public void portfolioException(boolean buy);

  /**
   * the formattedResult method adds formatted inputted text to the appendable in the class.
   * @param inp the Double result to format and append
   */
  public void formattedReturn(Double inp);

  /**
   * the portfolioCreationMessage method adds creation text to the appendable in the class.
   * @param name the name of the portfolio
   */
  public void portfolioCreationMessage(String name);

  /**
   * the buySellMessage method adds buying or selling shares text to the appendable in the class.
   * @param quantity the quantity of shares being sold or bought
   * @param stock the stock symbol
   * @param name the name of the portfolio
   * @param sell a boolean for if the shares are being bought or sold (true is sold)
   */
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell);

  /**
   * the askBalance method asks for the specific weight for a stock.
   * @param stock the stock symbol
   */
  public void askBalance(String stock);

  /**
   * the balanceInstruction method returns the instructions for inputting weights.
   */
  public void balanceInstruction();

  /**
   * the listWrite method adds the list text to the appendable in the class.
   * @param input the map of stocks with their respective share counts
   * @param type the type of list: either distribution or composition
   */
  public void listWrite(Map<String, Double> input, String type);

  /**
   * the barWrite method adds the bar graph to the appendable in the class.
   * @param name the name of the stock or portfolio to make a chart for
   * @param date1 the starting date
   * @param date2 the ending date
   * @param input the data input
   * @param scale the scale for the asterisks
   * @param order the chronological order of the dates
   */
  public void barWrite(String name, String date1, String date2, HashMap<String, Double> input,
                       Integer scale, ArrayList<String> order);

  /**
   * the invalidDate method adds an error message for an invalid date to the appendable.
   * @param type the part of the date (ie. year, month, day) which is incorrect
   */
  public void invalidDate(String type);
  
  /**
   * the askDate method adds a message to ask for the date to the appendable.
   * @param type the part of the date (ie. year, month, day)
   */
  public void askDate(String type);

  /**
   * the askDate method adds a message to ask for the portfolio to the appendable.
   */
  public void whichPortfolio();

  /**
   * the rebalanced method adds a message returning re-balancing weights to the appendable.
   * @param portfolioName the name of the portfolio being rebalanced
   */
  public void rebalanced(String portfolioName);
}
