package stocks.controller;

import stocks.model.StocksModel;
import stocks.view.StocksView;

/**
 * The following class represents a controller that delegates operations when the view is a
 * GUI.
 */
public class StocksGUIController implements StocksController {
  private Readable rd;
  private StocksModel model;
  private StocksView view;
  private String stockAction;

  /**
   * This makes a new StockControllerImpl.
   * @param model the StocksModel connection (connects to methods to enact based on user inputs)
   * @param rd something to read
   * @param view the StocksView connection (connects to methods to append words)
   */
  public StocksGUIController(StocksModel model, Readable rd, StocksView view)
          throws IllegalArgumentException {
    if ((model == null) || (rd == null)) {
      throw new IllegalArgumentException("Stock or Readable is null");
    }
    this.model = model;
    this.rd = rd;
    this.view = view;
    this.stockAction = "";
  }

  @Override
  public void execute() {
    this.setUpListeners();
  }

  private void setUpListeners() {
    view.setHelpListener(e -> view.createHelpWindow());
    view.setStockActionListener(e -> setStockAction(e.getActionCommand()));
    view.setCreatePortfolioListener(e -> setCreatePortfolio());
    view.setLoadListener(e -> setLoad());
    view.setStockSearchListener(e -> searchStock());
    view.setEnterValueListener(e -> valueEntered());
    view.setYearsListener(e -> date());
    view.setMonthsListener(e -> date());
    view.setDaysListener(e -> date());
    view.setSearchListener(e -> search());
  }

  private void search() {
    String stockName = searchStock();
    String portfolioName = "";
    String date = date();
    double value = valueEntered();
    int valueInt = (int) value;
    String answer;
    switch (stockAction) {
      case "portfolio value":
        answer = model.portfolioValue(portfolioName, date).toString();
        view.returnResult(stockAction + ":" + answer);
        break;
      case "portfolio composition":
        view.listWrite(model.composition(portfolioName, date), stockAction);
        break;
      case "buy stock":
        model.stockSelect(stockName);
        answer = model.buy(value, date, portfolioName).toString();
        view.returnResult(stockAction + ":" + answer  + ":" + portfolioName);
        break;
      case "sell stock":
        answer = model.sell(stockName, valueInt, date, portfolioName).toString();
        view.returnResult(stockAction + ":" + answer  + ":" + portfolioName);
        break;
      default:
        break;
    }
  }

  private String date() {
    String year = view.getYear();
    String month = view.getMonth();
    String day = view.getDay();
    return year + "-" + month + "-" + day;
  }

  private void showHelp() {
    view.createHelpWindow();
    System.out.println("PLEASE PLEAS PLEA");
  }

  private void setStockAction(String command) {
    this.stockAction = command;
  }

  private void setCreatePortfolio() {
//    stock.createPortfolio("");
    view.setStockOrPortfolio();
  }

  private void setLoad() {
    view.loadFileWindow();
    System.out.println("PLEASE PLEAS PLEA");
  }

  private String searchStock() {
    return view.getStock();
  }

  private double valueEntered() {
    double value;
    try {
      value = Double.parseDouble(view.getValue());
    }
    catch (NumberFormatException e) {
      value = 0;
    }
    return value;
  }
}
