import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.controller.StocksController;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;
import stocks.view.StocksView;

/**
 * The StocksControllerMock is a class that represents an imitation of our controller for
 * testing purposes.
 */
public class StocksControllerMock implements StocksController {
  // Note: although fields are called viewMock and modelMock, when testing for the view,
  // we actually pass in the real View/Model
  StocksModel modelMock;
  StocksView viewMock;
  String input;
  String curStock;
  boolean testingModel;
  Appendable ap;
  Map<String, Double> weights;

  /**
   * A StocksControllerMock takes in a model, view, input, and testingModel to aid in testing
   * model and view components in isolation.
   * @param m represents the given model
   * @param s represents the given view
   * @param input represents the given input case that's being tested
   * @param testingModel represents which component that is being tested
   */
  public StocksControllerMock(StocksModel m, StocksView s, String input, boolean testingModel,
                              Appendable ap) {
    this.modelMock = m;
    this.viewMock = s;
    this.input = input;
    this.curStock = "";
    this.testingModel = testingModel;
    this.ap = ap;
    this.weights = new HashMap<>();
  }

  /**
   * Returns the appendable result of method called in the model.
   * @return the result of a model computation as a string
   */
  public String returnComputation() {
    return ap.toString();
  }

  @Override
  public void execute() {
    String in = "";
    String[] commands = new String[]{};
    try {
      commands = input.split(" ");
      in = commands[0];
    }
    catch (Exception e) {
      in = input;
    }
    if (testingModel) {
      this.modelTesting(in, commands);
    }
    else {
      this.viewTesting(in, commands);
    }
  }

  // MODEL METHOD CALLS
  /**
   * The modelTesting represents the the different method calls to test the model
   * when valid inputs from the controller.
   * @param in the given stock operation
   * @param commands the remaining information inputted
   */
  public void modelTesting(String in, String[] commands) {
    switch (in) {
      case "menu":
        modelMock = new StocksModelImpl("", modelMock.getPortfolios());
        break;
      case "select-stock":
        modelMock = this.callSelectStock(commands[1]);
        break;
      // stock stats methods
      case "check-gain-loss":
        this.callGainLoss(Integer.parseInt(commands[1]), commands[2]);
        break;
      case "moving-average":
        this.callMovingAvg(Integer.parseInt(commands[1]), commands[2]);
        break;
      case "check-crossovers":
        this.callCrossovers(Integer.parseInt(commands[1]), commands[2]);
        break;
      case "buy-stock":
        this.curStock = commands[1];
        this.callBuyStock(Double.valueOf(commands[2]), commands[3], commands[4]);
        break;
      // portfolio methods
      case "create-portfolio":
        this.callCreatePortfolio(commands[1]);
        break;
      case "check-portfolio":
        this.callCheckPortfolio(commands[1], commands[2]);
        break;
      case "sell-stock":
        this.callSellStock(commands[1], Integer.parseInt(commands[2]), commands[3], commands[4]);
        break;
      case "distribution":
        this.callDistribution(commands[1], commands[2]);
        break;
      case "composition":
        this.callComposition(commands[1], commands[2]);
        break;
      case "balance":
        for (int i = 3; i < commands.length - 1; i = i + 2) {
          weights.put(commands[i], Double.valueOf(commands[i + 1]));
        }
        this.callBalance(commands[1], commands[2], weights);
        break;
      default:
        break;
    }
  }

  /**
   * The following method appends the command called by the controller to the log.
   * @param result given string to add to the log of called commands.
   */
  public void appendResult(String result) {
    try {
      ap.append(result);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * the callSelectStock changes the selected stock to the given one.
   * @param stock the given stock to query
   */
  public StocksModel callSelectStock(String stock) {
    return modelMock.stockSelect(stock);
  }

  /**
   * the callGainLoss method calculates the gain or loss of the stock saved in the class.
   * @param numDays the number of days in the range the user is searching the gain or loss of
   * @param date the starting date for the range
   */
  public void callGainLoss(int numDays, String date) {
    String result = Double.toString(modelMock.gainLoss(numDays, date));
    this.appendResult(result);
  }

  /**
   * the callMovingAvg method calculates the x-day moving average of the stock saved in the class.
   * @param numDays the specific x value in the x-day moving average
   * @param date the specified date the user is checking
   */
  public void callMovingAvg(int numDays, String date) {
    String result = Double.toString(modelMock.movingAvg(numDays, date));
    this.appendResult(result);
  }

  /**
   * the callCrossovers method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param numDays the specific x value in the x-day crossover
   * @param date the specified date the user is checking
   */
  public void callCrossovers(int numDays, String date) {
    String result = modelMock.crossovers(numDays, date);
    this.appendResult(result);
  }

  /**
   * the callBuy method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param numShares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   */
  public void callBuyStock(double numShares, String date, String portfolioName) {
    modelMock = modelMock.stockSelect(curStock);
    modelMock.buy(numShares, date, portfolioName);
  }

  /**
   * the callCreatePortfolio method determines whether a given date is an x-day crossover for the
   * stock.
   * saved in the class.
   * @param portfolioName the name for the portfolio
   */
  public void callCreatePortfolio(String portfolioName) {
    modelMock.createPortfolio(portfolioName);
  }

  /**
   * the callCheckPortfolio method determines whether a given date is an x-day crossover for the
   * stock.
   * saved in the class.
   * @param portfolioName the name of the portfolio
   * @param date the date the user is checking the portfolio value of
   */
  public void callCheckPortfolio(String portfolioName, String date) {
    String result = Double.toString(modelMock.portfolioValue(portfolioName, date));
    this.appendResult(result);
  }

  /**
   * the callSellStock method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param stock the symbol of the stock
   * @param numShares the number of shares the user intends ot buy
   * @param portfolioName the name of the portfolio which is storing this data
   */
  public void callSellStock(String stock, int numShares, String date, String portfolioName) {
    modelMock.sell(stock, numShares, date, portfolioName);
  }

  /**
   * the callDistribution method calls the distribution method inside the model
   * @param portfolioName the name of the portfolio
   * @param date the given date
   */
  public void callDistribution(String portfolioName, String date) {
    modelMock.distribution(portfolioName, date);
  }

  /**
   * the callComposition method calls the composition method inside the model.
   * @param portfolioName the name of the portfolio
   * @param date the given date
   */
  public void callComposition(String portfolioName, String date) {
    modelMock.composition(portfolioName, date);
  }

  /**
   * the callBalance method calls the balance method inside the model.
   * @param portfolioName the given portfolio name
   * @param date the given date
   * @param weights a map consisting of the stock and the desired weight
   */
  public void callBalance(String portfolioName, String date, Map<String,
          Double> weights) {
    modelMock.balance(portfolioName, date, weights);
  }

  // VIEW METHOD CALLS
  /**
   * The viewTesting represents the different method calls to test the view
   * when valid inputs from the controller.
   * @param in the given stock operation
   * @param commands the remaining information inputted
   */
  public void viewTesting(String in, String[] commands) {
    this.callWelcomeMessage();
    switch (in) {
      case "":
        // do nothing
      case "quit":
        this.callFarewellMessage();
        break;
      case "menu":
        this.callMenu();
        break;
      case "stock-menu":
        this.callStockMenu();
        break;
      case "select-stock":
        this.callStockMenu();
        this.callTypeInstruct();
        break;
      case "create-portfolio":
        this.createPortfolio(commands[1]);
        break;
      case "check-portfolio":
        this.checkPortfolio(commands[1], commands[2]);
        break;
      case "check-crossovers":
        this.checkCrossovers(commands[1], commands[2]);
        break;
      case "buy-stock":
        // directly placing GOOG stock ticker since we're assuming that the user has already
        // selected GOOG to access this command
        this.checkBuySellMessage(Integer.parseInt(commands[1]), "GOOG",
                commands[2], false);
        break;
      case "sell-stock":
        this.checkBuySellMessage(Integer.parseInt(commands[2]), commands[1],
                commands[3], true);
        break;
      default:
        this.callUndefined();
        this.callTypeInstruct();
        break;
    }
  }

  /**
   * the callWelcomeMessage method adds the welcome message to the appendable in the class.
   */
  public void callWelcomeMessage() {
    viewMock.welcomeMessage();
  }

  /**
   * the callFarewellMessage method adds a farewell message to the appendable in the class.
   */
  public void callFarewellMessage() {
    viewMock.farewellMessage();
  }

  /**
   * the callMenu method adds the initial menu text to the appendable in the class.
   */
  public void callMenu() {
    viewMock.printMenu();
  }

  /**
   * the callStockMenu method adds the stock menu text to the appendable in the class.
   */
  public void callStockMenu() {
    viewMock.printStockMenu();
  }

  /**
   * the callTypeInstruct method adds a user instruction frame message to the appendable
   * in the class.
   */
  public void callTypeInstruct() {
    viewMock.typeInstruct();
  }

  /**
   * the callUndefined method returns an undefined instruction message to the appendable
   * in the class.
   */
  public void callUndefined() {
    viewMock.undefined();
  }

  /**
   * the createPortfolio adds the portfolio creation text to the appendable in the class.
   * @param name the name of the portfolio
   */
  public void createPortfolio(String name) {
    viewMock.portfolioCreationMessage(name);
  }

  /**
   * the checkPortfolio method checks that the correct message is returned when a given
   * portfolio's value is asked to be checked.
   * @param name of the given portfolio
   * @param date the given day
   */
  public void checkPortfolio(String name, String date) {
    // sample portfolio list placeholders to demonstrate that view does output correct
    // messages accordingly
    List<String> existingPortfolios = Arrays.asList("kiki", "college", "bakery");
    boolean exists = false;
    for (int i = 0; i < existingPortfolios.size(); i++) {
      if (name.equals(existingPortfolios.get(i))) {
        exists = true;
        // passed in value is just provided to ensure that given data is printed
        // and not representative of an actual portfolio
        viewMock.formattedReturn(250.5);
      }
    }
    if (!exists) {
      viewMock.portfolioException(true);
    }
  }

  /**
   * the checkCrossOvers method shows that the view can output both possible
   * cases where either a given date is determined to be a crossover or not one
   * for the given range of days.
   * @param num the moving average range of days to retrieve
   * @param date the given date to be checked
   */
  public void checkCrossovers(String num, String date) {
    String[] dateSplit = date.split("-");
    String year = dateSplit[0];
    if (Integer.parseInt(year) < 2024) {
      viewMock.returnResult("No, this date is not a " + num + "-day crossover.");
    }
    else {
      viewMock.returnResult("Yes, this date is a " + num + "-day crossover.");
    }
  }

  /**
   * the checkBuySellMessage adds the buying or selling shares text to the appendable in the class.
   * @param quantity the quantity of shares being sold or bought
   * @param stock the stock symbol
   * @param portfolio the name of the portfolio
   * @param sell a boolean for if the shares are being bought or sold (true is sold)
   */
  public void checkBuySellMessage(Integer quantity, String stock, String portfolio, boolean sell) {
    viewMock.buySellMessage(quantity, stock, portfolio, sell);
  }
}
