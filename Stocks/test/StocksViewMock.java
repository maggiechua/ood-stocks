import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.view.StocksView;

/**
 * The StocksViewMock is a class that represents an imitation of our view for
 * testing purposes.
 */
public class StocksViewMock implements StocksView {
  private Appendable log;

  /**
   * StocksViewMock is a mock created purposely for testing.
   *
   * @param ap a StringBuilder that represents all the commands that the controller
   *           calls when it receives inputs
   */
  public StocksViewMock(Appendable ap) {
    this.log = ap;
  }

  /**
   * The following method appends the command called by the controller to the log.
   *
   * @param result given string to add to the log of called commands.
   */
  public void appendResult(String result) {
    try {
      log.append(result);
    } catch (IOException e) {
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
    this.appendResult("PortfolioImpl created printed in view. \n");
  }

  @Override
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell) {
    this.appendResult("Buying or selling message printed in view. \n");
  }

  @Override
  public void askBalance(String stock) {
    this.appendResult("Ask balance printed in view. \n");
  }

  @Override
  public void balanceInstruction() {
    this.appendResult("Balance instructions printed in view. \n");
  }

  @Override
  public void listWrite(Map<String, Double> input, String type) {
    this.appendResult("List printed in view. \n");
  }

  @Override
  public void barWrite(String name, String date1, String date2, HashMap<String, Double> input,
                       Integer scale, List<String> order) {
    this.appendResult("Bar chart printed in view. \n");
  }

  @Override
  public void invalidDate(String type) {
    this.appendResult("Invalid date printed in view. \n");
  }

  @Override
  public void askDate(String type) {
    this.appendResult("Asking date printed in view. \n");
  }

  @Override
  public void whichPortfolio() {
    this.appendResult("Asking portfolio printed in view. \n");
  }

  @Override
  public void rebalanced(String portfolioName) {
    this.appendResult("Rebalanced printed in view. \n");
  }

  @Override
  public void setHelpListener(ActionListener listen) {
  }

  @Override
  public void setStockActionListener(ActionListener listen) {

  }

  @Override
  public void setCreatePortfolioListener(ActionListener listen) {

  }

  @Override
  public void setStockOrPortfolio() {

  }

  @Override
  public void setLoadListener(ActionListener listen) {

  }

  @Override
  public void setStockSearchListener(ActionListener listen) {

  }

  @Override
  public void setEnterValueListener(ActionListener listen) {

  }

  @Override
  public void setYearsListener(ActionListener listen) {

  }

  @Override
  public void setMonthsListener(ActionListener listen) {

  }

  @Override
  public void setDaysListener(ActionListener listen) {

  }

  @Override
  public void setSearchListener(ActionListener listen) {

  }

  @Override
  public String getYear() {
    return "";
  }

  @Override
  public String getMonth() {
    return "";
  }

  @Override
  public String getDay() {
    return "";
  }

  @Override
  public String getStock() {
    return "";
  }

  @Override
  public String getValue() {
    return "";
  }

  @Override
  public void setFieldBlank(String place) {

  }

  @Override
  public void createHelpWindow() {
  }
  
  @Override
  public String getStockAction() {
    return "";
  }

  @Override
  public String loadFileWindow() {
    return "";
  }

  @Override
  public String getStockAction() {
    return "";
  }

  @Override
  public void namePortfolioWindow() {

  }
}
