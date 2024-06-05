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
    String res;

    // print welcome message
    output.welcomeMessage();

    while (!quit) {
      output.typeInstruct();
      String input = sc.next();
      switch (input) {
        case "select-stock" :
          output.typeInstruct();
          stockName = sc.next();
          stock.stockSelect(stockName);
          while (!quit) {
            output.printStockMenu();
            String nextInput = sc.next();
            switch (nextInput) {
              case "check-gain-loss" :
                numOfDays = sc.nextInt();
                date = sc.next();
                res = stock.gainLoss(numOfDays, date).toString();
                output.returnResult(res);
                break;
              case "moving-average" :
                numOfDays = sc.nextInt();
                date = sc.next();
                res = stock.movingAvg(numOfDays, date).toString();
                output.returnResult(res);
                break;
              case "check-crossovers" :
                numOfDays = sc.nextInt();
                date = sc.next();
                res = stock.crossovers(numOfDays, date);
                output.returnResult(res);
                break;
              case "buy-stock" :
                numOfShares = sc.nextInt();
                portfolioName = sc.next();
                stock.buy(numOfShares, portfolioName);
                res = numOfShares.toString() + " of " + stockName + " bought to " + portfolioName;
                output.returnResult(res);
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
          res = "Portfolio " + portfolioName + " created.";
          output.returnResult(res);
          break;
        case "check-portfolio" :
          portfolioName = sc.next();
          date = sc.next();
          res = stock.portfolioValue(portfolioName, date).toString();
          output.returnResult(res);
          break;
        case "sell-stock" :
          stockName = sc.next();
          numOfShares = sc.nextInt();
          portfolioName = sc.next();
          stock.sell(stockName, numOfShares, portfolioName);
          res = numOfShares.toString() + " of " + stockName + " sold from " + portfolioName;
          output.returnResult(res);
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
