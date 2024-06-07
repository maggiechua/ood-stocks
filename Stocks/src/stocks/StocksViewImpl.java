package stocks;

import java.io.IOException;

public class StocksViewImpl implements StocksView {
  private Appendable appendable;

  StocksViewImpl(Appendable appendable) {
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
    writeMessage("Welcome to the stocks application!" + System.lineSeparator());
    printMenu();
  }

  public void typeInstruct() throws IllegalStateException {
    writeMessage("Type instruction: ");
  }

  public void undefined() throws IllegalStateException {
    writeMessage("Invalid input: Please try again." + System.lineSeparator());
  }

  public void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program!");
  }

  public void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: " + System.lineSeparator());
    writeMessage("select-stock stock-symbol (select a stock to see functions)"
            + System.lineSeparator());
    writeMessage("create-portfolio portfolio-name (creates a new empty portfolio)"
            + System.lineSeparator());
    writeMessage("check-portfolio portfolio-name date (checks portfolio value at a specific date)"
            + System.lineSeparator());
    writeMessage("sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares)"
            + System.lineSeparator());
    writeMessage("menu (Print supported instruction list)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
    writeMessage("[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator());
  }

  public void printStockMenu() throws IllegalStateException {
    writeMessage("Supported user instructions for selected stock are: " + System.lineSeparator());
    writeMessage("check-gain-loss number-of-days start-date (checks gains or losses for stock in " +
            "specific date range)"
            + System.lineSeparator());
    writeMessage("moving-average number-of-days date (checks x-day moving average for specified x day " +
            "count and date)"
            + System.lineSeparator());
    writeMessage("check-crossovers number-of-days start-date (checks x-day crossovers for" +
            " specified x day count in specific date range)"
            + System.lineSeparator());
    writeMessage("buy-stock number-of-shares portfolio-name (buy stock shares)"
            + System.lineSeparator());
    writeMessage("stock-menu (Print supported stocks instruction list)" + System.lineSeparator());
    writeMessage("menu (return to previous menu)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
    writeMessage("[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator());
  }

  @Override
  public void returnResult(String input) {
    writeMessage(input + System.lineSeparator());
  }

  @Override
  public void portfolioException() {
    writeMessage("You may not have created this portfolio yet." + System.lineSeparator());
  }

  @Override
  public void formattedReturn(Double inp) {
    writeMessage(String.format("%,.2f", inp) + System.lineSeparator());
  }

  @Override
  public void portfolioCreationMessage(String name) {
    writeMessage("Portfolio " + name + " created." + System.lineSeparator());
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
