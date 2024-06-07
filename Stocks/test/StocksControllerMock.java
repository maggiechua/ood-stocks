import java.util.Arrays;
import java.util.List;

import stocks.StocksController;
import stocks.StocksModel;
import stocks.StocksView;

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
  boolean testingModel;

  /**
   * A StocksControllerMock takes in a model, view, input, and testingModel to aid in testing
   * model and view components in isolation.
   * @param m represents the given model
   * @param s represents the given view
   * @param input represents the given input case that's being tested
   * @param testingModel represents which component that is being tested
   */
  public StocksControllerMock(StocksModel m, StocksView s, String input, boolean testingModel) {
    this.modelMock = m;
    this.viewMock = s;
    this.input = input;
    this.testingModel = testingModel;
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
  public void modelTesting(String in, String[] commands) {
    switch (in) {
      case "":
        break;
      default:
        break;
    }
  }

  // VIEW METHOD CALLS
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

  public void callWelcomeMessage() {
    viewMock.welcomeMessage();
  }

  public void callFarewellMessage() {
    viewMock.farewellMessage();
  }

  public void callMenu() {
    viewMock.printMenu();
  }

  public void callStockMenu() {
    viewMock.printStockMenu();
  }

  public void callTypeInstruct() {
    viewMock.typeInstruct();
  }

  public void callUndefined() {
    viewMock.undefined();
  }

  public void createPortfolio(String name) {
    viewMock.portfolioCreationMessage(name);
  }

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

  public void checkBuySellMessage(Integer quantity, String stock, String portfolio, boolean sell) {
    viewMock.buySellMessage(quantity, stock, portfolio, sell);
  }
}
