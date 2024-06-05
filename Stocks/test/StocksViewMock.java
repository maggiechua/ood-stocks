import stocks.StocksView;

public class StocksViewMock implements StocksView {
  public StringBuilder outputMessage = new StringBuilder();

  StocksViewMock() {
    this.outputMessage = new StringBuilder();
  }

  public StringBuilder getOutputMessage() {
    return outputMessage;
  }

  @Override
  public void welcomeMessage() {
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
}
