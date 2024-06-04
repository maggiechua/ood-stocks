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

    // print welcome message
    output.welcomeMessage();

    while (!quit) {
      output.typeInstruct();
      String input = sc.next();
      switch (input) {
        case "select-stock" :
          while (!quit) {
            output.printStockMenu();
            String nextinput = sc.next();
            switch (nextinput) {
              case "check-gain-loss" :
                break;
              case "moving-average" :
                break;
              case "check-crossovers" :
                break;
              case "buy-stock" :
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
          break;
        case "check-portfolio" :
          break;
        case "sell-stock" :
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
