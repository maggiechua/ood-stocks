package stocks;

import java.util.Scanner;

public class StocksControllerImpl implements StocksController {
  private Readable rd;
  private StocksModel stock;
  private StocksView output;

  public StocksControllerImpl(StocksModel stock, Readable rd, StocksView output) throws IllegalArgumentException {
    if ((stock == null) || (rd == null)) {
      throw new IllegalArgumentException("Stock or Readable is null");
    }
    this.stock = stock;
    this.rd = rd;
    this.output = output;
  }

  public void execute() {
    Scanner sc = new Scanner(rd);
    boolean quit = false;
    boolean miniquit = false;
    String stockName;
    String portfolioName;
    String date;
    Integer numOfDays;
    Integer numOfShares;

    // print welcome message
    output.welcomeMessage();

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
                  portfolioName = sc.next();
                  stock.buy(numOfShares, portfolioName);
                  output.buySellMessage(numOfShares, stockName, portfolioName, false);
                }
                catch (Exception ignored) {
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
                output.farewellMessage();
                miniquit = true;
                quit = true;
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
            output.portfolioException();
          }
          break;
        case "sell-stock" :
          try {
            stockName = sc.next();
            numOfShares = sc.nextInt();
            portfolioName = sc.next();
            stock.sell(stockName, numOfShares, portfolioName);
            output.buySellMessage(numOfShares, stockName, portfolioName, true);
          }
          catch (Exception e) {
            output.portfolioException();
          }
          break;
        case "menu" :
          output.welcomeMessage();
          break;
        case "q" :
        case "quit" :
          output.farewellMessage();
          quit = true;
          break;
        default :
          output.undefined();
      }
    }
  }
}
