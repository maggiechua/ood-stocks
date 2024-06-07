import java.io.IOException;

import stocks.StocksView;

/**
 * The StocksViewMock is a class that represents an imitation of our view for
 * testing purposes.
 */
public class StocksViewMock implements StocksView {
  private Appendable log;

  // create appendable log
  StocksViewMock(Appendable ap) {
    this.log = ap;
  }

  public void appendResult(String result) {
    try {
      log.append(result);
    }
    catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void welcomeMessage() {
    this.appendResult("Welcome Message printed in view. \n");
  }

  @Override
  public void typeInstruct() {
    this.appendResult("Type Instruction Message printed in view. \n");
  }

  @Override
  public void undefined(String input) {

  }

  @Override
  public void farewellMessage() {
    this.appendResult("Farewell Message printed in view. \n");
  }

  @Override
  public void printMenu() {
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
