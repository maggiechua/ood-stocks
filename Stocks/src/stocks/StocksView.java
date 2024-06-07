package stocks;

public interface StocksView {

  public void welcomeMessage();

  public void typeInstruct();

  public void undefined();

  public void farewellMessage();

  public void printMenu();

  public void printStockMenu();

  public void returnResult(String input);

  public void portfolioException(boolean buy);

  public void formattedReturn(Double inp);

  public void portfolioCreationMessage(String name);

  public void buySellMessage(Integer quantity, String stock, String name, boolean sell);
  }
