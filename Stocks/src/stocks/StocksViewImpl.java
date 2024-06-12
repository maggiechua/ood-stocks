package stocks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO: PERFORMANCE OVER TIME
// barchart must show the following:
// - specified name of stock/portfolio and time range
// - time stamps MUST all align (i.e. Jan 2010:, Feb 2010:, etc.)
// - scale of the *

/**
 * This class represents the view of the stock program. It updates the appendable for what the user
 * should be able to see in the program.
 */
public class StocksViewImpl implements StocksView {
  private Appendable appendable;

  /**
   * This makes a new StockViewImpl.
   * @param appendable the appendable all the text is appended to
   */
  public StocksViewImpl(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * the writeMessage method adds inputted text to the appendable.
   * @param message the message to append
   * @throws IllegalStateException if there are input errors
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the stocks application! \n");
    printMenu();
  }

  @Override
  public void typeInstruct() throws IllegalStateException {
    writeMessage("Type instruction: ");
  }

  @Override
  public void undefined() throws IllegalStateException {
    writeMessage("Invalid input: Please try again. \n");
  }

  @Override
  public void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program! \n");
  }

  @Override
  public void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: \n");
    writeMessage("select-stock stock-symbol (select a stock to see functions) \n");
    writeMessage("create-portfolio portfolio-name (creates a new empty portfolio) \n");
    writeMessage("check-portfolio portfolio-name date "
            + "(checks portfolio value at a specific date) \n");
    writeMessage("sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares) \n");
    writeMessage("composition portfolio-name date (displays composition of a portfolio) \n");
    writeMessage("distribution portfolio-name date (displays distribution of a portfolio) \n");
    writeMessage("balance portfolio-name date weights "
            + "(re-balances the portfolio with given weights) \n");
    writeMessage("bar-chart portfolio-name initial-date end-date (outputs a par chart displaying " +
            "the performance of a portfolio int the given range) \n");
    writeMessage("menu (Print supported instruction list) \n");
    writeMessage("q or quit (quit the program) \n");
    writeMessage("[Please enter all dates in: YYYY-MM-DD format.] \n");
  }

  @Override
  public void printStockMenu() throws IllegalStateException {
    writeMessage("Supported user instructions for selected stock are: \n");
    writeMessage("check-gain-loss number-of-days start-date (checks gains or losses for stock in "
            + "specific date range) \n");
    writeMessage("moving-average number-of-days date (checks x-day moving average "
            + "for specified x day count and date) \n");
    writeMessage("check-crossovers number-of-days start-date (checks x-day crossovers for"
            + " specified x day count in specific date range) \n");
    writeMessage("buy-stock number-of-shares portfolio-name (buy stock shares) \n");
    writeMessage("stock-menu (Print supported stocks instruction list) \n");
    writeMessage("menu (return to previous menu) \n");
    writeMessage("q or quit (quit the program) \n");
    writeMessage("[Please enter all dates in: YYYY-MM-DD format.] \n");
  }

  @Override
  public void returnResult(String input) {
    writeMessage(input + " \n");
  }

  @Override
  public void portfolioException(boolean buy) {
    if (buy) {
      writeMessage("There may be an input error, or you may not have created this portfolio yet. " +
              "Please try again. \n");
    }
    else {
      writeMessage("There may be an input error, you may not have created this portfolio yet, or " +
              "you may not own enough shares to sell. Please try again. \n");
    }
  }

  @Override
  public void formattedReturn(Double inp) {
    writeMessage(String.format("%,.2f", inp) + " \n");
  }

  @Override
  public void portfolioCreationMessage(String name) {
    writeMessage("Portfolio " + name + " created. \n");
  }

  @Override
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell) {
    if (sell) {
      writeMessage(quantity + " of " + stock + " sold from " + name + ". \n");
    }
    else {
      writeMessage(quantity + " of " + stock + " bought to " + name + ". \n");
    }
  }

  @Override
  public void askBalance(String stock) {
    writeMessage("Please input new weight for " + stock + " : \n");
  }

  @Override
  public void balanceInstruction() {
    writeMessage("Please enter weights as doubles. For example: enter [ 25.0 ] to represent 25%, or" +
            "[ 33.33 ] to represent 33.33%. \n");
  }

  @Override
  public void listWrite(HashMap<String, Double> input) {
    for (Map.Entry<String, Double> entry : input.entrySet()) {
      writeMessage("Stock: " + entry.getKey() + " - Shares/Value: " + entry.getValue() + "\n");
    }
  }

  @Override
  public void barWrite(HashMap<String, Double> input, Integer scale) {
    int asterisk = 0;
    for (Map.Entry<String, Double> entry : input.entrySet()) {
      asterisk = (int) Math.round(entry.getValue()/scale);
      writeMessage(entry.getKey() + " : ");
      for (int i = 0; i < asterisk; i++) {
        writeMessage("*");
      }
      writeMessage("\n");
    }
    writeMessage("Scale : * = " + scale + "\n");
  }
}
