import stocks.StocksView;

/**
 * The StocksViewMock is a class that represents an imitation of our view for
 * testing purposes.
 */
public class StocksViewMock implements StocksView {
  public StringBuilder outputMessage = new StringBuilder();

  // create appendable log
  StocksViewMock(Appendable ap) {
    this.outputMessage = new StringBuilder();
  }

  public StringBuilder getOutputMessage() {
    return outputMessage;
  }

  @Override
  public void welcomeMessage() {
    // instead of actually printing the message, just write something like "command welcomeMessage()
    // executed"
    outputMessage.append("Welcome to Stocks View!\n");
    this.printMenu();
  }

  @Override
  public void typeInstruct() {

  }

  @Override
  public void undefined(String input) {

  }

  @Override
  public void farewellMessage() {

  }

  @Override
  public void printMenu() {
    outputMessage.append("Supported user instructions are: ")
            .append(System.lineSeparator())
            .append("select-stock stock-symbol (select a stock to see functions)")
            .append(System.lineSeparator())
            .append("create-portfolio portfolio-name (creates a new empty portfolio)")
            .append(System.lineSeparator())
            .append("check-portfolio portfolio-name date " +
                    "(checks portfolio value at a specific date)")
            .append(System.lineSeparator())
            .append("sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares)")
            .append(System.lineSeparator())
            .append("menu (Print supported instruction list)")
            .append(System.lineSeparator())
            .append("q or quit (quit the program) ")
            .append(System.lineSeparator())
            .append("[Please enter all dates in: YYYY-MM-DD format.]")
            .append(System.lineSeparator());
  }

  @Override
  public void printStockMenu() {

  }

  @Override
  public void returnResult(String input) {

  }

  @Override
  public void portfolioException() {

  }

  @Override
  public void formattedReturn(Double inp) {

  }

  @Override
  public void portfolioCreationMessage(String name) {

  }

  @Override
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell) {

  }
}
