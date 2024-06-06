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
}
