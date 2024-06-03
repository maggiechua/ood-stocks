package stocks;

import java.io.IOException;

public class StocksView {
  private Appendable appendable;

  public void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  protected void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the stocks application!" + System.lineSeparator());
    printMenu();
  }

  protected void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program!");
  }

  protected void printMenu() throws IllegalStateException {
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
  }

  protected void printStockMenu() throws IllegalStateException {
    writeMessage("Supported user instructions for selected stock are: " + System.lineSeparator());
    writeMessage("check-gain-loss start-date end-date (checks gains or losses for stock in " +
            "specific date range)"
            + System.lineSeparator());
    writeMessage("moving-average number-of-days date (checks x-day moving average for specified x day " +
            "count and date)"
            + System.lineSeparator());
    writeMessage("check-crossovers number-of-days start-date end-date (checks x-day crossovers for" +
            " specified x day count in specific date range)"
            + System.lineSeparator());
    writeMessage("buy-stock number-of-shares portfolio-name (buy stock shares)"
            + System.lineSeparator());
    writeMessage("stock-menu (Print supported stocks instruction list)" + System.lineSeparator());
    writeMessage("return-to-menu (return to previous menu)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
  }
}
