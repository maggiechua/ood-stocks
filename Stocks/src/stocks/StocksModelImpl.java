package stocks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//TODO: UPDATE PORTFOLIO FEATURES
  //TODO: purchase specific stock on a specified date
  //TODO: sell stock on a specified date (allows user to sell fractional shares)
//TODO: NEW PORTFOLIO FEATURES
  //TODO: composition of portfolio on a specific date
  //  [a) list of stocks b) # of shares per stock]
  //TODO: distribution of value on a specific date
  //  [a) list of stocks b) value of each individual stock in the portfolio]
  //TODO: portfolio can be saved/loaded (recommend standard file formats: XML/JSON)
  //TODO: re-balancing a portfolio
  //  user gives an ideal distribution of stock values (i.e. 40/20/20/20)
  //  can result in fractional ownership of stocks that can be sold
//TODO: PERFORMANCE OVER TIME
// # of lines for timestamps must be min 5, max 30 (minimum can be below 5 for day)
// must include the following timestamps: day/month/year
//    day -> computed with closing price
//    month -> computed with last working day closing price of month
//    year -> computed with last working day closing price of year
// relative scale may be needed (given * = 1000), but scale must be shown on all charts

/**
 * This class represents the model of the stock program. It stores stock dara and portfolios for
 * the user as well as accesses the API and saved files for stock data.
 */
public class StocksModelImpl implements StocksModel {
  private String stock;
  private HashMap<String,HashMap<String, Double>> portfolios;
  private FileCreator fc;
  private FileParser fp;

  /**
   * This makes a new StocksModel Implementation.
   * @param stock the String representing the stock symbol
   * @param portfolios the hashmap holding the portfolios of the user
   */
  public StocksModelImpl(String stock, HashMap<String, HashMap<String, Double>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
    this.fc = new FileCreator();
    this.fp = new FileParser();
  }

  /**
   * the getAPIKey method stores and returns the API key used for the program.
   */
  private String getAPIKey() {
    return "HI5ADT0RWWANGUID";
  }

  /**
   * the createPortfolio method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param stockSymbol the stock symbol
   * @param numOfDays the number of days in the range to search for
   * @param date the date to find the data of
   * @return a List of data in Double format
   */
  protected List<Double> getStockInfo(String stockSymbol, Integer numOfDays, String date) {
    String userDirectory = System.getProperty("user.dir");
    String directoryPath = userDirectory + "/Stocks/res/data/";
    String fileName = stockSymbol + ".csv";
    Path path = Paths.get(directoryPath + fileName);
    File file = path.toFile();

    List<Double> output = new ArrayList<>();
    if (!file.exists()) {
      fc.createStockCSVFile(stock, this.getAPIKey());
    }
    try {
      Scanner sc = new Scanner(file);
      int counter = 0;
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        if (lineInfo[0].equals(date)) {
          output.add(Double.parseDouble(lineInfo[4]));
          counter++;
        }
        else if (counter - 1 == numOfDays) {
          break;
        }
        else if (counter > 0) {
          output.add(Double.parseDouble(lineInfo[4]));
          counter++;
        }
      }
    }
    catch (IOException e) {
      System.out.println("The stock did not exist on the date given.");
    }
    return output;
  }

  @Override
  public StocksModelImpl stockSelect(String s) {
    return new StocksModelImpl(s, this.portfolios);
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    Double lastDate = 0.0;
    Double startDate = 0.0;
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    try {
      lastDate = priceData.get(priceData.size() - 1);
      startDate = priceData.get(0);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The gain-loss cannot be calculated as " +
              "the given date does not exist or does not contain a valid range.");
    }
    return startDate - lastDate;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    Double sum = 0.0;
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
    try {
      priceData.remove(priceData.size() - 1);
      for (Double price : priceData) {
        sum += price;
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The moving average cannot be calculated as " +
              "the given date does not exist or does not contain a valid range.");
    }
    return sum / numOfDays;
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    String message = "";
    Double movingAvg =  this.movingAvg(numOfDays, date);
    List<Double> priceData = this.getStockInfo(this.stock, 1, date);
    try {
      if (priceData.get(0) > movingAvg) {
        message = "Yes, this date is a " + numOfDays + "-day crossover.";
      }
      else {
        message = "No, this date is not a " + numOfDays + "-day crossover.";
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The moving average cannot be calculated as "
              + "the given date does not exist or does not contain a valid range.");
    }
    return message;
  }

  @Override
  public StocksModelImpl createPortfolio(String name) {
    HashMap<String, HashMap<String, Double>> pfs;
    if (this.portfolios == null) {
      pfs = new HashMap<String, HashMap<String, Double>>();
    }
    else {
      pfs = this.portfolios;
    }
    fc.createNewPortfolioFile(name);
    HashMap<String, Double> newp = new HashMap<>();
    pfs.put(name, newp);
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public StocksModelImpl buy(Integer shares, String date, String portfolioName) {
    HashMap<String, HashMap<String, Double>> pfs = this.portfolios;
    HashMap<String, Double> currentPortfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    if (currentPortfolio.containsKey(this.stock)) {
      currentPortfolio.put(this.stock, currentPortfolio.get(this.stock) + shares);
    }
    else {
      currentPortfolio.put(this.stock, Double.valueOf(shares));
    }
    fc.addStockToPortfolioFile(portfolioName, this.stock, shares, date);
    pfs.put(portfolioName, currentPortfolio);
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public StocksModelImpl sell(String stock, Integer shares, String date, String portfolioName) {
    HashMap<String, HashMap<String, Double>> pfs = this.portfolios;
    HashMap<String, Double> currentPortfolio = pfs.get(portfolioName);
    if (currentPortfolio.containsKey(stock)) {
      if (currentPortfolio.get(stock) >= shares) {
        currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
      } else {
        throw new IllegalArgumentException("not enough shares to sell");
      }
    } else {
      throw new IllegalArgumentException("you don't own this stock");
    }
    pfs.put(portfolioName, currentPortfolio);
    return new StocksModelImpl(stock, pfs);
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    HashMap<String, Double> portfolio = portfolios.get(portfolioName);
    double portfolioValue = 0.0;
    for (Map.Entry<String, Double> stock: portfolio.entrySet()) {
      List<Double> stockValue = this.getStockInfo(stock.getKey(), 1, date);
      portfolioValue += stockValue.get(0) * stock.getValue();
    }
    return portfolioValue;
  }

  @Override
  public HashMap<String, Double> composition(String portfolioName, String date) {
    // TODO: reset portfolios for date
    HashMap<String, HashMap<String, Double>> pfs = this.portfolios;
    return pfs.get(portfolioName);
  }

  @Override
  public HashMap<String, Double> distribution(String portfolioName, String date) {
    // TODO: reset portfolios for date
    HashMap<String, Double> pf = this.portfolios.get(portfolioName);
    HashMap<String, Double> dist = new HashMap<>();
    for (Map.Entry<String, Double> stock: pf.entrySet()) {
      List<Double> stockValue = this.getStockInfo(stock.getKey(), 1, date);
      dist.put(stock.getKey(), stock.getValue() * stockValue.get(0) );
    }
    return dist;
  }

  @Override
  public HashMap<String, Double> bar(String portfolioName, String date1, String date2)
  throws ParseException {
    HashMap<String, Double> barValues = new HashMap<>();

    LocalDate dateOne = LocalDate.parse(date1);
    LocalDate dateTwo = LocalDate.parse(date2);

    long diffDays = ChronoUnit.DAYS.between(dateOne, dateTwo);
    long diffMonths = ChronoUnit.MONTHS.between(dateOne, dateTwo);
    long diffYears = ChronoUnit.YEARS.between(dateOne, dateTwo);

    if (diffDays < 5) {
      throw new IllegalArgumentException();
    }
    else if (diffDays > 5 && diffDays < 30) {
      while (dateOne != dateTwo) {
        barValues.put(dateOne.toString(), portfolioValue(portfolioName, dateOne.toString()));
        dateOne = dateOne.plusDays(1);
      }
    }
    else if (diffDays > 30) {
      if (diffMonths < 5) {
        double set = 0;
        int days = (int) diffDays;
        while (set < 5 || set > 30) {
          for (int i = 1; i < 30; i++) {
            set = days / i;
          }
        }
        long s = (long) set;
        while (dateOne != dateTwo) {
          barValues.put(dateOne.toString(), portfolioValue(portfolioName, dateOne.toString()));
          dateOne = dateOne.plusDays(s);
        }
      }
      else if (diffMonths > 5 && diffMonths < 30) {
        while (dateOne != dateTwo) {
          barValues.put(dateOne.toString(), portfolioValue(portfolioName, dateOne.toString()));
          dateOne = dateOne.plusMonths(1);
        }
      }
      else if (diffMonths > 30) {
        if (diffYears < 5) {
          double set = 0;
          int months = (int) diffMonths;
          while (set < 5 || set > 30) {
            for (int i = 1; i < 30; i++) {
              set = months / i;
            }
          }
          long s = (long) set;
          while (dateOne != dateTwo) {
            barValues.put(dateOne.toString(), portfolioValue(portfolioName, dateOne.toString()));
            dateOne = dateOne.plusMonths(s);
          }
        }
        else if (diffYears > 5 && diffYears < 30) {
          while (dateOne != dateTwo) {
            barValues.put(dateOne.toString(), portfolioValue(portfolioName, dateOne.toString()));
            dateOne = dateOne.plusYears(1);
          }
        }
        else {
          throw new IllegalArgumentException();
        }
      }
    }
    return barValues;
  }

  @Override
  public StocksModelImpl balance(String portfolioName, String date, HashMap<String,
          Double> weights) {
    //TODO: adjusted portfolio values based on file, new method to update portfolios? or auto?
    HashMap<String, HashMap<String, Double>> pfs = this.portfolios;
    double max = portfolioValue(portfolioName, date);
    for (String a : weights.keySet()) {
      Double shareCount = pfs.get(portfolioName).get(a);
      Double perc = weights.get(a);
      List<Double> stockValue = this.getStockInfo(a, 1, date);
      Double newVal = (perc/100) * max;
      newVal = newVal/stockValue.get(0);
      shareCount = shareCount - newVal;
      if (shareCount < 0) {
        //TODO: add transaction mess for buy
      }
      else if (shareCount > 0) {
        //TODO: add transaction mess for sell
      }
      pfs.get(portfolioName).put(a, newVal);
    }
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public ArrayList<String> stockCount(String portfolioName) {
    HashMap<String, Double> pf = this.portfolios.get(portfolioName);
    ArrayList<String> stockList = new ArrayList<>();
    for (Map.Entry<String, Double> stock: pf.entrySet()) {
      stockList.add(stock.getValue().toString());
    }
    return stockList;
  }

  @Override
  public Integer makeScale(HashMap<String, Double> barData) {
    int scale = 1;
    Double max = 0.0;
    boolean pass = false;
    for (Map.Entry<String, Double> bar: barData.entrySet()) {
      if (bar.getValue() > max) {
        max = bar.getValue();
      }
    }
    while (!pass) {
      for (int i = 0; i < Integer.MAX_VALUE; i++) {
        if (max < 5 * (10 ^ i)) {
          scale = (10 ^ (i - 1));
          pass = true;
        }
      }
    }
    return scale;
  }

  @Override
  public String getStock() {
    return stock;
  }

  @Override
  public HashMap<String, HashMap<String, Double>> getPortfolios() {
    return portfolios;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    Double value = this.portfolioValue(name, date);
    return value;
  }
}
