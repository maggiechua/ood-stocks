package stocks;

public interface StocksView {

  public void welcomeMessage();

  public void typeInstruct();

  public void undefined(String input);

  public void farewellMessage();

  public void printMenu();

  public void printStockMenu();

  public void returnResult(String input);

  public void portfolioException();

  public void formattedReturn(Double inp);

  public void portfolioCreationMessage(String name);

  public void buySellMessage(Integer quantity, String stock, String name, boolean sell);
  }
