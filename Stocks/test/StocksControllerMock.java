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
    switch (input) {
      case "quit":
        this.callFarewellMessage();
        break;
      case "menu":
        this.callMenu();
      case "stock-menu":
        this.callStockMenu();
      case "select-stock":
        this.callStockMenu();
        this.callTypeInstruct();
      default:
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
}
