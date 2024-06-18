package stocks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents the model of the stock program. It stores stock dara and portfolios for
 * the user as well as accesses the API and saved files for stock data.
 */
public class StocksModelImpl implements StocksModel {
  private String stock;
  private List<Portfolio> portfolios;
  private FileCreator fc;
  private FileParser fp;

  /**
   * The portfolios object was previously a HashMap, where now it is a List of a PortfolioImpl
   * class we created to assist in saving portfolios and retrieving portfolio data.
   * The fc and fp objects were created for the purpose of accessing and saving file data.
   * ---
   * This makes a new StocksModel Implementation.
   * @param stock the String representing the stock symbol
   * @param portfolios the hashmap holding the portfolios of the user
   */
  public StocksModelImpl(String stock, List<Portfolio> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
    this.fc = new FileCreator();
    this.fp = new FileParser();
  }

  @Override
  public StocksModel loadPortfolios() {
    Set<Path> files = fp.retrieveFilesInDirectory();
    List<Portfolio> portfolios = new ArrayList<>();
    for (Path path : files) {
      String portfolioName = path.getFileName().toString();
      Map<String, Double> contents = new HashMap<>();
      List<Transaction> transactions = fp.parsePortfolioTransactions(path, "");
      Portfolio p = new PortfolioImpl(portfolioName, contents, transactions);
      Set<String> stocks = p.getStocksList(transactions);
      p.loadContents(stocks, transactions, "");
      portfolios.add(p);
    }
    return new StocksModelImpl(this.stock, portfolios);
  }

  @Override
  public void savePortfolios() {
    for (int p = 0; p < portfolios.size(); p++) {
      Portfolio curPortfolio = portfolios.get(p);
      curPortfolio.savePortfolio();
    }
  }

  @Override
  public StocksModelImpl stockSelect(String s) {
    return new StocksModelImpl(s, this.portfolios);
  }

  @Override
  public Double gainLoss(Integer numOfDays, String date) {
    Double lastDate = 0.0;
    Double startDate = 0.0;
    List<Double> priceData = fc.getStockInfo(this.stock, numOfDays, date);
    try {
      lastDate = priceData.get(priceData.size() - 1);
      startDate = priceData.get(0);
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The gain-loss cannot be calculated as "
              + "the given date does not exist or does not contain a valid range.");
    }
    return startDate - lastDate;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    Double sum = 0.0;
    List<Double> priceData = fc.getStockInfo(this.stock, numOfDays, date);
    try {
      priceData.remove(priceData.size() - 1);
      for (Double price : priceData) {
        sum += price;
      }
    }
    catch (Exception e) {
      throw new IllegalArgumentException("The moving average cannot be calculated as "
              + "the given date does not exist or does not contain a valid range.");
    }
    return sum / numOfDays;
  }

  @Override
  public String crossovers(Integer numOfDays, String date) {
    String message = "";
    Double movingAvg =  this.movingAvg(numOfDays, date);
    List<Double> priceData = fc.getStockInfo(this.stock, 1, date);
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
    List<Portfolio> pfs;
    if (this.portfolios == null) {
      pfs = new ArrayList<Portfolio>();
    }
    else {
      pfs = this.portfolios;
    }
    fc.createNewPortfolioFile(name, new ArrayList<Transaction>());
    pfs.add(new PortfolioImpl(name, new HashMap<String, Double>(), new ArrayList<Transaction>()));
    return new StocksModelImpl(this.stock, pfs);
  }

  /**
   * This is a method that finds the location of a portfolio in a list of portfolios.
   * @param name of the portfolio
   * @return the index of the portfolio in the portfolio list
   */
  private int retrievePortfolioIndex(String name) {
    int pIndex = -1;
    for (int p = 0; p < portfolios.size(); p++) {
      if (portfolios.get(p).getName().equals(name)) {
        pIndex = p;
      }
    }
    return pIndex;
  }

  @Override
  public boolean validMarketDay(String date) {
    String stockPrice = fp.getStockPrice(this.stock, date);
    return !stockPrice.isEmpty();
  }

  @Override
  public String nextMarketDay(String date) {
    return fp.getNextMarketDay(this.stock, date);
  }

  @Override
  public ArrayList<String> reorder(Map<String, Double> input) throws ParseException {
    ArrayList<LocalDate> dates = new ArrayList<>();
    ArrayList<String> orderDates = new ArrayList<>();
    SimpleDateFormat dateFormatter;
    Date dateObject;
    String[] tempDate;
    String temp;
    Integer type = 0;

    for (Map.Entry<String, Double> entry : input.entrySet()) {
      try {
        dateFormatter = new SimpleDateFormat("MMM dd yyyy");
        dateObject = dateFormatter.parse(entry.getKey());
        type = 1;
      }
      catch (ParseException e) {
        dateFormatter = new SimpleDateFormat("MMM yyyy");
        dateObject = dateFormatter.parse(entry.getKey());
        tempDate = dateObject.toString().split(" ");
        temp = tempDate[0] + " 1 " + tempDate[1];
        type = 2;
      }
      catch (Exception e) {
        dateFormatter = new SimpleDateFormat("yyyy");
        dateObject = dateFormatter.parse(entry.getKey());
        temp = "Jan 1 " + dateObject.toString();
        type = 3;
      }
      dateFormatter.applyPattern("yyyy-MM-dd");
      String result = dateFormatter.format(dateObject);
      dates.add(LocalDate.parse(result));
    }

    while (!dates.isEmpty()) {
      LocalDate smallest = dates.get(0);
      for (int i = 1; i < dates.size(); i++) {
        if (dates.get(i).isBefore(smallest)) {
          smallest = dates.get(i);
        }
      }
      dates.remove(smallest);
      orderDates.add(organizeDate(smallest, type));
    }
    return orderDates;
  }

  @Override
  public StocksModelImpl buy(double shares, String date, String portfolioName) {
    List<Portfolio> pfs = this.portfolios;
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio currentPortfolio = pfs.remove(pIndex);
    Portfolio p;
    boolean validDay = this.validMarketDay(date);
    if (!validDay) {
      String nextMDay = fp.getNextMarketDay(this.stock, date);
      p = currentPortfolio.addToPortfolio(this.stock, nextMDay, shares);
      //TODO: update buy/sell message to inform the user.
      pfs.add(currentPortfolio);
    }
    else {
      p = currentPortfolio.addToPortfolio(this.stock, date, shares);
    }
    p.savePortfolio();
    pfs.add(p);
    return new StocksModelImpl(this.stock, pfs);
  }

  /**
   * The sell method now intakes a date, for the new features.
   */
  @Override
  public StocksModelImpl sell(String stock, Integer shares, String date, String portfolioName) {
    List<Portfolio> pfs = this.portfolios;
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio currentPortfolio = pfs.remove(pIndex);
    Portfolio p;
    boolean validDay = this.validMarketDay(date);
    if (!validDay) {
      String nextMDay = fp.getNextMarketDay(this.stock, date);
      p = currentPortfolio.removeFromPortfolio(this.stock, nextMDay, shares);
      //TODO: update buy/sell message to inform the user.
    }
    else {
      p = currentPortfolio.removeFromPortfolio(this.stock, date, shares);
    }
    p.savePortfolio();
    pfs.add(p);
    return new StocksModelImpl(stock, pfs);
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio portfolio = portfolios.get(pIndex);
    return portfolio.calculateValue(date);
  }

  @Override
  public Map<String, Double> composition(String portfolioName, String date) {
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio p = portfolios.get(pIndex);
    Path path = fp.retrievePath(fp.getOSType(), portfolioName, "portfolios/", ".xml");
    return p.loadPortfolio(path, date, false);
  }

  @Override
  public Map<String, Double> distribution(String portfolioName, String date) {
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio pf = this.portfolios.get(pIndex);
    return pf.findDistribution(date);
  }

  @Override
  public HashMap<String, Double> bar(String name, String date1, String date2)
          throws ParseException {
    HashMap<String, Double> barValues = new HashMap<>();
    LocalDate dateOne = LocalDate.parse(date1);
    LocalDate dateTwo = LocalDate.parse(date2).plusDays(1);
    long diffDays = ChronoUnit.DAYS.between(dateOne, dateTwo);
    long diffWeeks = ChronoUnit.WEEKS.between(dateOne, dateTwo);
    long diffMonths = ChronoUnit.MONTHS.between(dateOne, dateTwo);
    long diffYears = ChronoUnit.YEARS.between(dateOne, dateTwo);
    long s = 0;
    if (diffDays < 5) {
      throw new IllegalArgumentException();
    }
    else if (diffDays > 5 && diffDays < 30) {
      barValues = orgBarData(dateOne, dateTwo, name, 0, 1);
    }
    else if (diffDays > 30) {
      if (diffWeeks < 5) {
        s = newSet(diffDays);
        barValues = orgBarData(dateOne, dateTwo, name, 0, s);
      }
      else if (diffWeeks > 5 && diffWeeks < 30) {
        barValues = orgBarData(dateTwo, dateOne, name, 1, 1);
      }
      else if (diffWeeks > 30) {
        if (diffMonths < 5) {
          s = newSet(diffWeeks);
          barValues = orgBarData(dateOne, dateTwo, name, 1, s);
        }
        else if (diffMonths > 5 && diffMonths < 30) {
          barValues = orgBarData(dateOne, dateTwo, name, 2, 1);
        }
        else if (diffMonths > 30) {
          if (diffYears < 5) {
            s = newSet(diffMonths);
            barValues = orgBarData(dateOne, dateTwo, name, 2, s);
          }
          else if (diffYears > 5 && diffYears < 30) {
            barValues = orgBarData(dateOne, dateTwo, name, 3, 1);
          }
          else {
            throw new IllegalArgumentException();
          }
        }
      }
    }
    return barValues;
  }

  /**
   * the newSet method calculates a working interval between each date in the bar chart.
   * @param difference the difference between the two dates
   * @return a long of the new interval
   */
  private long newSet(long difference) {
    double set = 0;
    int diff = (int) difference;
    for (int i = 1; i < 30; i++) {
      if (set < 5 || set > 30) {
        set = diff / i;
      }
    }
    return (long) set;
  }

  /**
   * the orgBarData method determines whether a given date is an x-day crossover for the stock
   * saved in the class.
   * @param one the start date
   * @param two the end date
   * @param name the name of the dataset - either stock or portfolio
   * @param time type of time to check
   * @param setValue the interval between checks
   * @return a Map of data to make the bar chart
   */
  private HashMap<String, Double> orgBarData(LocalDate one, LocalDate two, String name,
                                             int time, long setValue) {
    boolean isPortfolio = true;
    if (retrievePortfolioIndex(name) == -1) {
      isPortfolio = false;
    }

    HashMap<String, Double> barValues = new HashMap<>();
    Double valueGet;
    String dateOut;
    valueGet = addingDates(one, time, setValue, isPortfolio , name);
    boolean initial = true;
    while (one.compareTo(two) <= 0) {
      dateOut = organizeDate(one, time);
      barValues.put(dateOut, valueGet);
      if (!initial) {
        if (time == 0) {
          one = one.plusDays(setValue);
        }
        if (time == 1) {
          one = one.plusWeeks(setValue);
        }
        if (time == 2) {
          one = one.plusMonths(setValue);
        }
        if (time == 3) {
          one = one.plusYears(setValue);
        }
      }
      valueGet = addingDates(one, time, setValue, isPortfolio , name);
      initial = false;
    }
    return barValues;
  }

  /**
   * the addingDates method helps to add time to dates to get data for the bar chart.
   * saved in the class.
   * @param one the initial date
   * @param time the type of time period to add
   * @param setValue the value to add
   * @param portfolio whether name is a portfolio
   * @param name the name of the dataset - either stock or portfolio
   * @return a double of the value calculated
   */
  private Double addingDates(LocalDate one, int time, long setValue, boolean portfolio,
                             String name) {
    double value = 0;
    int pIndex = this.retrievePortfolioIndex(name);
    Portfolio pf = portfolios.get(pIndex);
    if (time < 2) {
      if (portfolio) {
        value = pf.calculateValue(one.toString());
      }
      else {
        value = Double.parseDouble(fp.getStockPrice(name, one.toString()));
      }
    }
    else {
      String [] date = one.toString().split("-");
      int month = Integer.parseInt(date[1]);
      int year = Integer.parseInt(date[0]);
      if (time == 2) {
        if (portfolio) {
          value = portfolioValue(name, fp.getLastWorkingDay("NVDA", month, year));
        } else {
          value = Double.parseDouble(fp.getStockPrice(name, fp.getLastWorkingDay(name, month, year)));
        }
      }
      if (time == 3) {
        if (portfolio) {
          value = portfolioValue(name, fp.getLastWorkingDay("NVDA", 0, year));
        }
        else {
          value = Double.parseDouble(fp.getStockPrice(name, fp.getLastWorkingDay(name, 0, year)));
        }
      }
    }
    return value;
  }

  /**
   * the organizeDate method reformats the date to an even number of characters
   * saved in the class.
   * @param date the given date
   * @return a formatted version of the date specifically for the bar chart
   */
  private String organizeDate(LocalDate date, int type) {
    DateTimeFormatter formatter;
    if (type == 2) {
      formatter = DateTimeFormatter.ofPattern("MMM yyyy");
    }
    else if (type == 3) {
      formatter = DateTimeFormatter.ofPattern("yyyy");
    }
    else {
      formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }
    return formatter.format(date);
  }

  @Override
  public StocksModelImpl balance(String portfolioName, String date, Map<String,
          Double> weights) {
    List<Portfolio> pfs = this.portfolios;
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio p = pfs.remove(pIndex);
    double max = portfolioValue(portfolioName, date);
    for (String a : weights.keySet()) {
      Double shareCount = p.getPortfolioContents().get(a);
      Double perc = weights.get(a);
      List<Double> stockValue = fc.getStockInfo(a, 1, date);
      Double newVal = (perc / 100) * max;
      newVal = newVal / stockValue.get(0);
      shareCount = shareCount - newVal;
      if (shareCount < 0) {
        p.addToPortfolio(a, date, Math.abs(shareCount));
      }
      else if (shareCount > 0) {
        p.removeFromPortfolio(a, date, shareCount);
      }
      pfs.add(p);
      pfs.get(pIndex).getPortfolioContents().put(a, newVal);
    }
    return new StocksModelImpl(this.stock, pfs);
  }

  @Override
  public ArrayList<String> stockCount(String portfolioName) {
    Portfolio pf = this.portfolios.get(this.retrievePortfolioIndex(portfolioName));
    ArrayList<String> stockList = new ArrayList<>();
    for (Map.Entry<String, Double> stock: pf.getPortfolioContents().entrySet()) {
      stockList.add(stock.getKey());
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
        if (max < 5 * Math.pow(10, i)) {
          scale = (int) Math.pow(10, (i - 1));
          pass = true;
          break;
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
  public List<Portfolio> getPortfolios() {
    return portfolios;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    Double value = this.portfolioValue(name, date);
    return value;
  }
}
