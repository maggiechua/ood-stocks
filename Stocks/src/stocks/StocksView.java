package stocks;

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
   * the portfolioCreationMessage adds the portfolio creation text to the appendable in the class.
   * @param name the name of the portfolio
   */
  public void portfolioCreationMessage(String name);

  /**
   * the buySellMessage adds the buying or selling shares text to the appendable in the class.
   * @param quantity the quantity of shares being sold or bought
   * @param stock the stock symbol
   * @param name the name of the portfolio
   * @param sell a boolean for if the shares are being bought or sold (true is sold)
   */
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell);
}
