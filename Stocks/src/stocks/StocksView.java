package stocks;

import java.util.HashMap;

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
   */
  public void listWrite(HashMap<String, Double> input);

  /**
   * the barWrite method adds the bar graph to the appendable in the class.
   * @param input the map of stocks with each value of the full stock
   * @param scale the scale of the chart
   */
  public void barWrite(HashMap<String, Double> input, Integer scale);
}
