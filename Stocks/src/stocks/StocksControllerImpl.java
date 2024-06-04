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
          stockName = sc.next();
          stock.stockSelect(stockName);
          while (!quit) {
            output.printStockMenu();
            String nextInput = sc.next();
            switch (nextInput) {
              case "check-gain-loss" :
                numOfDays = sc.nextInt();
                date = sc.next();
                stock.gainLoss(numOfDays, date);
                break;
              case "moving-average" :
                numOfDays = sc.nextInt();
                date = sc.next();
                stock.movingAvg(numOfDays, date);
                break;
              case "check-crossovers" :
                numOfDays = sc.nextInt();
                date = sc.next();
                stock.crossovers(numOfDays, date);
                break;
              case "buy-stock" :
                numOfShares = sc.nextInt();
                portfolioName = sc.next();
                stock.buy(numOfShares, portfolioName);
                break;
              case "stock-menu" :
                output.printStockMenu();
                break;
              case "return-to-menu" :
                output.printMenu();
                break;
              case "q" :
              case "quit" :
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
          break;
        case "check-portfolio" :
          portfolioName = sc.next();
          date = sc.next();
          stock.portfolioValue(portfolioName, date);
          break;
        case "sell-stock" :
          stockName = sc.next();
          numOfShares = sc.nextInt();
          portfolioName = sc.next();
          stock.sell(stockName, numOfShares, portfolioName);
          break;
        case "menu" :
          output.welcomeMessage();
          break;
        case "q" :
        case "quit" :
          quit = true;
          break;
        default :
          output.undefined(input);
      }
    }
  }
}
