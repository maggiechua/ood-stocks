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
  public void undefined() {
    this.appendResult("Undefined Message printed in view. \n");
  }

  @Override
  public void farewellMessage() {
    this.appendResult("Farewell Message printed in view. \n");
  }

  @Override
  public void printMenu() {
    this.appendResult("Initial Menu printed in view. \n");
  }

  @Override
  public void printStockMenu() {
    this.appendResult("Stock Menu printed in view. \n");
  }

  @Override
  public void returnResult(String input) {
    this.appendResult("Result printed in view. \n");
  }

  @Override
  public void portfolioException(boolean buy) {
    this.appendResult("PortfolioException printed in view. \n");
  }

  @Override
  public void formattedReturn(Double inp) {
    this.appendResult("Formatted result printed in view. \n");
  }

  @Override
  public void portfolioCreationMessage(String name) {
    this.appendResult("Portfolio created printed in view. \n");
  }

  @Override
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell) {
    this.appendResult("Buying or selling message printed in view. \n");
  }
}
