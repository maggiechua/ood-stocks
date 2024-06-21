package stocks.controller;

import java.nio.file.Path;

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
    model = model.loadPortfolios();
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
    switch (stockAction) {
      case "portfolio value":
        model.portfolioValue(portfolioName, date);
        break;
      case "portfolio composition":
        model.composition(portfolioName, date);
        break;
      case "buy stock":
        model.stockSelect(stockName);
        model.buy(value, date, portfolioName);
        break;
      case "sell stock":
        model.sell(stockName, valueInt, date, portfolioName);
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
    view.namePortfolioWindow();
    model.createPortfolio("");
  }

  private void setLoad() {
    Path filePath = Path.of(view.loadFileWindow());
    // model.loadPortfolios();
    System.out.println("PLEASE PLEAS PLEA");
  }

  private String searchStock() {
    return view.getStock();
  }

  private double valueEntered() {
    Double value = 0.0;
    try {
      value = Double.parseDouble(view.getValue());
    }
    catch (NumberFormatException e) {
      view.setFieldBlank("value");
      System.out.println("Please enter a number???");
    }
    return value;
  }
}
