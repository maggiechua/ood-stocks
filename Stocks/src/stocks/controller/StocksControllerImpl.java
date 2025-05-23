package stocks.controller;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Scanner;

import stocks.view.StocksView;
import stocks.model.StocksModel;

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
  private String stockAction;

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
    this.stockAction = "";
  }

  private void showHelp() {
    output.createHelpWindow();
    System.out.println("PLEASE PLEAS PLEA");
  }

  private void setStockAction(String command) {
    this.stockAction = command;
  }

  private void setCreatePortfolio() {
//    stock.createPortfolio("");
  }

  private void setLoad() {
    output.loadFileWindow();
    System.out.println("PLEASE PLEAS PLEA");
  }

  private String searchStock() {
    return output.getStock();
  }

  private double valueEntered() {
    Double value;
    try {
      value = Double.parseDouble(output.getValue());
    }
    catch (NumberFormatException e) {
      value = null;
      output.setFieldBlank("value");
      System.out.println("Please enter a number???");
    }
    return value;
  }

  private String date() {
    String year = output.getYear();
    String month = output.getMonth();
    String day = output.getDay();
    return year + "-" + month + "-" + day;
  }

  private void search() {
    String stockName = searchStock();
    String portfolioName = "";
    String date = date();
    double value = valueEntered();
    int valueInt = (int) value;
    switch (stockAction) {
      case "portfolio value":
        stock.portfolioValue(portfolioName, date);
        break;
      case "portfolio composition":
        stock.composition(portfolioName, date);
        break;
      case "buy stock":
        stock.stockSelect(stockName);
        stock.buy(value, date, portfolioName);
        break;
      case "sell stock":
        stock.sell(stockName, valueInt, date, portfolioName);
        break;
      default:
        break;
    }
  }

  /**
   * There are minor changes in this method as compared to the previous submission, for the sake of
   * separation into the stockActions method, but also in the way dates are taken in, and to ensure
   * that invalid inputs are erased after setting to the default case. There are some added lines
   * for extra outputs: for example, asking for the portfolio name in a separate line. Additionally,
   * there are extra lines to load and save portfolios.
   */
  @Override
  public void execute() {
    Scanner sc = new Scanner(rd);
    boolean quit = false;
    boolean miniquit;
    String stockName;
    String portfolioName;
    String date;
    Integer numOfShares;
    HashMap<String, Double> weights = new HashMap<>();
    Double weight;
    String date2;
    Integer whatNext;
    String thisStock;

    output.welcomeMessage();
    stock = stock.loadPortfolios();

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
            whatNext = stockActions(sc, nextInput, stockName);
            if (whatNext == 1) {
              miniquit = true;
            }
            if (whatNext == 2) {
              quit = true;
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
            date = dateProcess(sc, false);
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
            date = dateProcess(sc, true);
            output.whichPortfolio();
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
            date = dateProcess(sc, false);
            output.listWrite(stock.composition(portfolioName, date), "comp");
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
          break;
        case "distribution" :
          try {
            portfolioName = sc.next();
            date = dateProcess(sc, false);
            output.listWrite(stock.distribution(portfolioName, date), "dist");
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
          break;
        case "balance" :
          try {
            portfolioName = sc.next();
            int size = stock.stockCount(portfolioName).size();
            if (size == 0) {
              output.portfolioException(false);
            }
            else {
              date = dateProcess(sc, true);
              output.balanceInstruction();
              for (int i = 0; i < size; i++) {
                thisStock = stock.stockCount(portfolioName).get(i);
                output.askBalance(thisStock);
                weight = sc.nextDouble();
                weights.put(thisStock, weight);
              }
              stock.balance(portfolioName, date, weights);
              output.rebalanced(portfolioName);
            }
          }
          catch (Exception e) {
            // TODO: what exceptions
          }
          break;
        case "bar-chart" :
          try {
            String name = sc.next();
            date = dateProcess(sc, true);
            date2 = dateProcess(sc, true);
            HashMap<String, Double> chartData = (HashMap<String, Double>)
                    stock.bar(name, date, date2);
            output.barWrite(name, date, date2, chartData, stock.makeScale(chartData),
                    stock.reorder(chartData));
          }
          catch (Exception e) {
            System.out.println("rip");
          }
          break;
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
          sc.nextLine();
      }
    }
  }

  /**
   * The stockActions method was previously combined with the execute method, but has been split
   * so that the methods are easier to read and understand.
   * ---
   * the stockActions method goes through the controls for the stock menu.
   * @param sc the scanner being used
   * @param nextInput the user input
   * @param stockName the stock inputted by the user
   * @return an Integer value to tell the main execute function what to do next
   */
  private Integer stockActions(Scanner sc, String nextInput, String stockName) {
    boolean quit = false;
    boolean miniquit = false;
    String portfolioName;
    String date;
    Integer numOfDays;
    Integer numOfShares;
    Integer whatNext = 0;
    switch (nextInput) {
      case "check-gain-loss" :
        try {
          numOfDays = sc.nextInt();
          date = dateProcess(sc, true);
          output.formattedReturn(stock.gainLoss(numOfDays, date));
        }
        catch (Exception ignored) {
        }
        break;
      case "moving-average" :
        try {
          numOfDays = sc.nextInt();
          date = dateProcess(sc, true);
          output.formattedReturn(stock.movingAvg(numOfDays, date));
        }
        catch (Exception ignored) {
        }
        break;
      case "check-crossovers" :
        try {
          numOfDays = sc.nextInt();
          date = dateProcess(sc, true);
          output.returnResult(stock.crossovers(numOfDays, date));
        }
        catch (Exception ignored) {
        }
        break;
      case "buy-stock" :
        try {
          numOfShares = sc.nextInt();
          date = dateProcess(sc, true);
          output.whichPortfolio();
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
        miniquit = true;
        quit = true;
        break;
      default :
        output.undefined();
        sc.nextLine();
    }
    if (miniquit) {
      whatNext = 1;
    }
    if (quit) {
      whatNext = 2;
    }
    return whatNext;
  }

  /**
   * the dateProcess method asks for each part of the date separately and checks each to ensure it
   * is valid.
   * @param sc the scanner being used
   * @return a string for the date
   */
  private String dateProcess(Scanner sc, boolean check) {
    String date;
    String day = "";
    String month = "";
    String year = "";
    boolean yearCheck = false;
    boolean monthCheck = false;
    boolean dayCheck = false;
    while (!yearCheck) {
      output.askDate("year");
      year = sc.next();
      try {
        yearCheck = checkDate(year, 2);
      }
      catch (Exception e) {
        yearCheck = false;
      }
      if (!yearCheck) {
        output.invalidDate("year");
      }
    }
    while (!monthCheck) {
      output.askDate("month");
      month = sc.next();
      try {
        monthCheck = checkDate(month, 1);
      }
      catch (Exception e) {
        monthCheck = false;
      }
      if (!monthCheck) {
        output.invalidDate("month");
      }
    }
    while (!dayCheck) {
      output.askDate("date");
      day = sc.next();
      try {
        dayCheck = checkDate(day, 0);
      }
      catch (Exception e) {
        dayCheck = false;
      }
      if (!dayCheck) {
        output.invalidDate("day");
      }
    }
    date = String.format("%04d-%02d-%02d",
            Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    if (check) {
      if (!stock.validMarketDay(date)) {
        date = stock.nextMarketDay(date);
        output.invalidDate(date);
      }
    }
    return date;
  }

  /**
   * the checkDate method checks to ensure that a date inputted is valid.
   * @param input the value input
   * @param type the part of the date (i.e. year, month, day)
   * @return a boolean to state whether the given value is valid
   */
  private boolean checkDate(String input, Integer type) {
    Integer dateCheck = Integer.parseInt(input);
    boolean r = true;
    if ((type == 0) && (dateCheck > 31 || dateCheck < 0)) {
      r = false;
    }
    else if ((type == 1) && (dateCheck > 12 || dateCheck < 0)) {
      r = false;
    }
    else if ((type == 2) && (dateCheck > 2024 || dateCheck < 2000)) {
      r = false;
    }
    return r;
  }
}