package stocks;

import java.io.IOException;

public class StocksViewImpl implements StocksView {
  private Appendable appendable;

  public StocksViewImpl(Appendable appendable) {
    this.appendable = appendable;
  }

  private void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the stocks application! \n");
    printMenu();
  }

  public void typeInstruct() throws IllegalStateException {
    writeMessage("Type instruction: ");
  }

  public void undefined() throws IllegalStateException {
    writeMessage("Invalid input: Please try again." + System.lineSeparator());
  }

  public void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program! \n");
  }

  public void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: \n");
    writeMessage("select-stock stock-symbol (select a stock to see functions) \n");
    writeMessage("create-portfolio portfolio-name (creates a new empty portfolio) \n");
    writeMessage("check-portfolio portfolio-name date " +
            "(checks portfolio value at a specific date) \n");
    writeMessage("sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares) \n");
    writeMessage("menu (Print supported instruction list) \n");
    writeMessage("q or quit (quit the program) \n");
    writeMessage("[Please enter all dates in: YYYY-MM-DD format.] \n");
  }

  public void printStockMenu() throws IllegalStateException {
    writeMessage("Supported user instructions for selected stock are: \n");
    writeMessage("check-gain-loss number-of-days start-date (checks gains or losses for stock in "
            + "specific date range) \n");
    writeMessage("moving-average number-of-days date (checks x-day moving average " +
            "for specified x day count and date) \n");
    writeMessage("check-crossovers number-of-days start-date (checks x-day crossovers for" +
            " specified x day count in specific date range) \n");
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
  public void portfolioException() {
    writeMessage("You may not have created this portfolio yet. \n");
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
      writeMessage(quantity + " of " + stock + " sold from " + name + "." + System.lineSeparator());
    }
    else {
      writeMessage(quantity + " of " + stock + " bought to " + name + "." + System.lineSeparator());
    }
  }
}
