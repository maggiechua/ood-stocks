import java.util.Arrays;
import java.util.List;

import stocks.StocksController;
import stocks.StocksModel;
import stocks.StocksView;

public class StocksControllerMock implements StocksController {
  StocksModel modelMock;
  StocksView viewMock;
  String input;

  public StocksControllerMock(StocksModel m, StocksView s, String input) {
    this.modelMock = m;
    this.viewMock = s;
    this.input = input;
  }

  @Override
  public void execute() {
    this.callWelcomeMessage();
    String in = "";
    String[] commands = new String[]{};
    try {
      commands = input.split(" ");
      in = commands[0];
    }
    catch (Exception e) {
      in = input;
    }

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
    viewMock.undefined(input);
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
      viewMock.portfolioException();
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
