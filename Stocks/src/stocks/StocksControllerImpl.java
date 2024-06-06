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
          while (!miniquit) {
            output.printStockMenu();
            output.typeInstruct();
            String nextInput = sc.next();
            switch (nextInput) {
              case "check-gain-loss" :
                numOfDays = sc.nextInt();
                date = sc.next();
                try {
                  output.formattedReturn(stock.gainLoss(numOfDays, date));
                }
               catch (Exception e) {
                  output.undefined(date);
                }
                break;
              case "moving-average" :
                numOfDays = sc.nextInt();
                date = sc.next();
                try {
                  output.formattedReturn(stock.movingAvg(numOfDays, date));
                }
                catch (Exception e) {
                  output.undefined(date);
                }
                break;
              case "check-crossovers" :
                numOfDays = sc.nextInt();
                date = sc.next();
                try {
                  output.returnResult(stock.crossovers(numOfDays, date));
                }
                catch (Exception e) {
                  output.undefined(date);
                }
                break;
              case "buy-stock" :
                numOfShares = sc.nextInt();
                portfolioName = sc.next();
                try {
                  stock.buy(numOfShares, portfolioName);
                  output.buySellMessage(numOfShares, stockName, portfolioName, false);
                }
                catch (Exception e) {
                  output.portfolioException();
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
                output.undefined(input);
            }
          }
          break;
        case "create-portfolio" :
          portfolioName = sc.next();
          stock.createPortfolio(portfolioName);
          output.portfolioCreationMessage(portfolioName);
          break;
        case "check-portfolio" :
          portfolioName = sc.next();
          date = sc.next();
          try {
            output.formattedReturn(stock.portfolioValue(portfolioName, date));
          }
          catch (Exception e) {
            output.portfolioException();
          }
          break;
        case "sell-stock" :
          stockName = sc.next();
          numOfShares = sc.nextInt();
          portfolioName = sc.next();
          try {
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
          output.undefined(input);
      }
    }
  }
}
