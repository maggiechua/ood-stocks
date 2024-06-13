package stocks;

import java.util.HashMap;
import java.util.Scanner;

/**
 * This class represents the controller of the stocks program.
 * This controller offers a simple text interface in which the user can enter given instructions to.
 * run the program as intended.
 * This controller works with any Readable to read its inputs.
 * Dates are taken in YYYY-MM-DD format.
 */
public class StocksControllerImpl implements StocksController {
  private Readable rd;
  private StocksModel stock;
  private StocksView output;

  /**
   * This makes a new StockControllerImpl.
   * @param stock the StocksModel connection (connects to methods to enact based on user inputs)
   * @param rd something to read
   * @param output the StocksView connection (connects to methods to append words)
   */
  public StocksControllerImpl(StocksModel stock, Readable rd, StocksView output)
          throws IllegalArgumentException {
    if ((stock == null) || (rd == null)) {
      throw new IllegalArgumentException("Stock or Readable is null");
    }
    this.stock = stock;
    this.rd = rd;
    this.output = output;
  }

  @Override
  public void execute() {
    Scanner sc = new Scanner(rd);
    boolean quit = false;
    boolean miniquit = false;
    String stockName;
    String portfolioName;
    String date;
    Integer numOfDays;
    Integer numOfShares;
    HashMap<String, Double> weights = new HashMap<>();
    Double weight;
    String date2;

    // print welcome message
    output.welcomeMessage();
    // load portfolio(s)' contents into the program if they exist
    stock.loadPortfolios();

    while (!quit) {
      output.typeInstruct();
      String input = sc.next();
      switch (input) {
        case "select-stock" :
          miniquit = false;
          stockName = sc.next();
          stock = stock.stockSelect(stockName);
          output.printStockMenu();
          while (!miniquit) {
            output.typeInstruct();
            String nextInput = sc.next();
            switch (nextInput) {
              case "check-gain-loss" :
                try {
                  numOfDays = sc.nextInt();
                  date = sc.next();
                  output.formattedReturn(stock.gainLoss(numOfDays, date));
                }
                catch (Exception ignored) {
                }
                break;
              case "moving-average" :
                try {
                  numOfDays = sc.nextInt();
                  date = sc.next();
                  output.formattedReturn(stock.movingAvg(numOfDays, date));
                }
                catch (Exception ignored) {
                }
                break;
              case "check-crossovers" :
                try {
                  numOfDays = sc.nextInt();
                  date = sc.next();
                  output.returnResult(stock.crossovers(numOfDays, date));
                }
                catch (Exception ignored) {
                }
                break;
              case "buy-stock" :
                try {
                  numOfShares = sc.nextInt();
                  date = sc.next();
                  portfolioName = sc.next();
                  stock.buy(numOfShares, date, portfolioName);
                  output.buySellMessage(numOfShares, stockName, portfolioName, false);
                }
                catch (Exception e) {
                  output.portfolioException(true);
                }
                break;
              case "stock-menu" :
                output.printStockMenu();
                break;
              case "menu" :
                miniquit = true;
                output.printMenu();
                break;
              case "q" :
              case "quit" :
                output.printMenu();
                miniquit = true;
                break;
              default :
                output.undefined();
            }
          }
          break;
        case "create-portfolio" :
          portfolioName = sc.next();
          stock.createPortfolio(portfolioName);
          output.portfolioCreationMessage(portfolioName);
          break;
        case "check-portfolio" :
          try {
            portfolioName = sc.next();
            date = sc.next();
            output.formattedReturn(stock.portfolioValue(portfolioName, date));
          }
          catch (Exception e) {
            output.portfolioException(true);
          }
          break;
        case "sell-stock" :
          try {
            stockName = sc.next();
            numOfShares = sc.nextInt();
            date = sc.next();
            portfolioName = sc.next();
            stock.sell(stockName, numOfShares, date, portfolioName);
            output.buySellMessage(numOfShares, stockName, portfolioName, true);
          }
          catch (Exception e) {
            output.portfolioException(false);
          }
          break;
        case "composition" :
          try {
            portfolioName = sc.next();
            date = sc.next();
            stock.composition(portfolioName, date);
            output.listWrite(stock.composition(portfolioName, date));;
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
        case "distribution" :
          try {
            portfolioName = sc.next();
            date = sc.next();
            output.listWrite(stock.distribution(portfolioName, date));
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
        case "balance" :
          try {
            portfolioName = sc.next();
            date = sc.next();
            output.balanceInstruction();
            for (int i = 0; i <= stock.stockCount(portfolioName).size(); i++) {
              String thisStock = stock.stockCount(portfolioName).get(i);
              output.askBalance(thisStock);
              weight = sc.nextDouble();
              weights.put(thisStock, weight);
            }
            stock.balance(portfolioName, date, weights);
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
        case "bar-chart" :
          try {
            portfolioName = sc.next();
            date = sc.next();
            date2 = sc.next();
            // TODO: output
            HashMap<String, Double> chartData = (HashMap<String, Double>)
                    stock.bar(portfolioName, date, date2);
            output.barWrite(chartData, stock.makeScale(chartData));
          }
          catch (Exception e) {
            // TODO: what exceptions: length of time incorrect
          }
        case "menu" :
          output.printMenu();
          break;
        case "q" :
        case "quit" :
          stock.savePortfolios();
          output.farewellMessage();
          quit = true;
          break;
        default :
          output.undefined();
      }
    }
  }
}
