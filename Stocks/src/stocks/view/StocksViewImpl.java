package stocks.view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stocks.controller.StocksController;

import stocks.controller.StocksControllerImpl;
import stocks.model.Portfolio;

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

  /**
   * The printMenu method has been updated for the new features.
   */
  @Override
  public void printMenu() throws IllegalStateException {
    writeMessage("Supported user instructions are: \n");
    writeMessage("select-stock stock-symbol (select a stock to see functions) \n");
    writeMessage("create-portfolio portfolio-name (creates a new empty portfolio) \n");
    writeMessage("check-portfolio portfolio-name (checks portfolio value at a specific date) \n");
    writeMessage("sell-stock stock-symbol number-of-shares (sell stock shares) \n");
    writeMessage("composition portfolio-name (displays composition of a portfolio) \n");
    writeMessage("distribution portfolio-name (displays distribution of a portfolio) \n");
    writeMessage("balance portfolio-name (re-balances the portfolio with given weights) \n");
    writeMessage("bar-chart portfolio-name (outputs a par chart displaying "
            + "the performance of a portfolio int the given range) \n");
    writeMessage("menu (Print supported instruction list) \n");
    writeMessage("q or quit (quit the program) \n");
  }

  /**
   * The printStockMenu method has been updated for the new features.
   */
  @Override
  public void printStockMenu() throws IllegalStateException {
    writeMessage("Supported user instructions for selected stock are: \n");
    writeMessage("check-gain-loss number-of-days (checks gains or losses for stock in "
            + "specific date range) \n");
    writeMessage("moving-average number-of-days (checks x-day moving average "
            + "for specified x day count and date) \n");
    writeMessage("check-crossovers number-of-days (checks x-day crossovers for"
            + " specified x day count in specific date range) \n");
    writeMessage("buy-stock number-of-shares (buy stock shares) \n");
    writeMessage("stock-menu (Print supported stocks instruction list) \n");
    writeMessage("menu (return to previous menu) \n");
    writeMessage("q or quit (quit the program) \n");
  }

  @Override
  public void returnResult(String input) {
    writeMessage(input + " \n");
  }

  @Override
  public void portfolioException(boolean buy) {
    if (buy) {
      writeMessage("There may be an input error, or you may not have created this portfolio yet. "
              + "Please try again. \n");
    }
    else {
      writeMessage("There may be an input error, you may not have created this portfolio yet, or "
              + "you may not own enough stocks in it. Please try again. \n");
    }
  }

  @Override
  public void formattedReturn(Double inp) {
    writeMessage("$" + String.format("%,.2f", inp) + " \n");
  }

  @Override
  public void portfolioCreationMessage(String name) {
    writeMessage("PortfolioImpl " + name + " created. \n");
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
    writeMessage("Please input new weight for " + stock + " : ");
  }

  @Override
  public void balanceInstruction() {
    writeMessage("Please enter weights as doubles. For example: enter [ 25.0 ] to represent 25%, or"
            + "[ 33.33 ] to represent 33.33%. \n");
  }

  @Override
  public void listWrite(Map<String, Double> input, String type) {
    for (Map.Entry<String, Double> entry : input.entrySet()) {
      if (type.equals("comp")) {
        writeMessage("Stock: " + entry.getKey() + " - Shares: "
                + String.format("%,.4f", entry.getValue()) + "\n");
      }
      else if (type.equals("dist")) {
        writeMessage("Stock: " + entry.getKey() + " - Value: $"
                + String.format("%,.2f", entry.getValue()) + "\n");
      }
    }
  }

  @Override
  public void barWrite(String name, String date1, String date2, HashMap<String, Double> input,
                       Integer scale, List<String> order) {
    int asterisk = 0;
    writeMessage("Performance of " + name + " from " + date1 + " to " + date2 + "\n \n");
    for (String s : order) {
      asterisk = (int) Math.round(input.get(s) / scale);
      writeMessage(s + " : ");
      for (int i = 0; i < asterisk; i++) {
        writeMessage("*");
      }
      writeMessage("\n");
    }
    writeMessage("\nScale : * = $" + scale + "\n");
  }

  @Override
  public void invalidDate(String type) {
    if (type.equals("day") || type.equals("month") || type.equals("year")) {
      writeMessage("Invalid " + type + ", please enter a new " + type + " value: ");
    }
    else {
      writeMessage("Inputted date is not a market day, using closest market date: " + type + " \n");
    }
  }

  @Override
  public void askDate(String type) {
    writeMessage("Please input a " + type + " value: ");
  }

  @Override
  public void whichPortfolio() {
    writeMessage("Enter the portfolio you'd like to perform this action to: ");
  }

  @Override
  public void rebalanced(String portfolioName) {
    writeMessage("PortfolioImpl " + portfolioName + " rebalanced. \n");
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
  public String getPortfolio() {
    return "";
  }

  @Override
  public void setFieldBlank(String place) {
  }

  @Override
  public void createHelpWindow() {

  }

  @Override
  public String loadFileWindow() {
    return "";
  }

  @Override
  public void namePortfolioWindow() {

  }
}
