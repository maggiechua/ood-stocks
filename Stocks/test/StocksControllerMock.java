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
}
