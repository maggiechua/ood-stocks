package stocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
   */

  /**
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
    String directoryPath;
    if (fp.getOSType().equals("mac")) {
      directoryPath = userDirectory + "/res/data/";
    }
    else {
      directoryPath = userDirectory + "/Stocks/res/data/";
    }
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
  public StocksModel loadPortfolios() {
    Set<Path> files = fp.retrieveFilesInDirectory();
    List<Portfolio> portfolios = new ArrayList<>();
    for (Path path : files) {
      String portfolioName = path.getFileName().toString();
      Map<String, Double> contents = new HashMap<>();
      List<Transaction> transactions = fp.parsePortfolioTransactions(path, "");
      Portfolio p = new PortfolioImpl(portfolioName, contents, transactions);
      Set<String> stocks = p.getStocksList(transactions);
      p.loadContents(stocks);
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
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
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
    List<Double> priceData = this.getStockInfo(this.stock, numOfDays, date);
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
    this.loadPortfolios();
    return portfolio.calculateValue(date);
  }

  @Override
  public HashMap<String, Double> composition(String portfolioName, String date) {
    List<Portfolio> pfs = this.portfolios;
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    return (HashMap<String, Double>) pfs.get(pIndex).getPortfolioContents();
  }

  @Override
  public HashMap<String, Double> distribution(String portfolioName, String date) {
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
      orgBarData(dateOne, dateTwo, name, 0, 1);
    }
    else if (diffDays > 30) {
      if (diffWeeks < 5) {
        s = newSet(diffDays);
        orgBarData(dateOne, dateTwo, name, 0, s);
      }
      else if (diffWeeks > 5 && diffWeeks < 30) {
        orgBarData(dateTwo, dateOne, name, 1, 1);
      }
      else if (diffWeeks > 30) {
        if (diffMonths < 5) {
          s = newSet(diffWeeks);
          orgBarData(dateOne, dateTwo, name, 1, s);
        }
        else if (diffMonths > 5 && diffMonths < 30) {
          orgBarData(dateOne, dateTwo, name, 2, 1);
        }
        else if (diffMonths > 30) {
          if (diffYears < 5) {
            s = newSet(diffMonths);
            orgBarData(dateOne, dateTwo, name, 2, s);
          }
          else if (diffYears > 5 && diffYears < 30) {
            orgBarData(dateOne, dateTwo, name, 3, 1);
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
    while (set < 5 || set > 30) {
      for (int i = 1; i < 30; i++) {
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
  private Map<String, Double> orgBarData(LocalDate one, LocalDate two, String name,
                                             int time, long setValue) {
    Map<String, Double> barValues = new HashMap<>();
    Double valueGet;
    String [] date = one.toString().split("-");
    String dateOut;
    if (time == 2) {
      int month = Integer.parseInt(date[1]);
      if (portfolios.contains(name)) {
        int pIndex = this.retrievePortfolioIndex(name);
        Portfolio portfolio = portfolios.get(pIndex);
        valueGet = portfolio.calculateLastValue("month", month);
      }
      else {
        valueGet = Double.parseDouble(fp.getLastWorkingDay(name, "month", month));
      }
    }
    else if (time == 3) {
      int year = Integer.parseInt(date[2]);
      if (portfolios.contains(name)) {
        int pIndex = this.retrievePortfolioIndex(name);
        Portfolio portfolio = portfolios.get(pIndex);
        valueGet = portfolio.calculateLastValue("year", year);
      }
      else {
        valueGet = Double.parseDouble(fp.getLastWorkingDay(name, "year", year));
      }
    }
    else {
      if (portfolios.contains(name)) {
        valueGet = portfolioValue(name, one.toString());
      }
      else {
        valueGet = this.getStockInfo(name, 1, one.toString()).get(0);
      }
    }
    while (one != two) {
      dateOut = organizeDate(one, time);
      barValues.put(dateOut, valueGet);
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
      if (portfolios.contains(name)) {
        valueGet = portfolioValue(name, one.toString());
      }
      else {
        valueGet = this.getStockInfo(name, 1, one.toString()).get(0);
      }
    }
    return barValues;
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
    if (type == 3) {
      formatter = DateTimeFormatter.ofPattern("yyyy");
    }
    else {
      formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    }
    return formatter.format(date);
  }

  @Override
  public StocksModelImpl balance(String portfolioName, String date, HashMap<String,
          Double> weights) {
    List<Portfolio> pfs = this.portfolios;
    int pIndex = this.retrievePortfolioIndex(portfolioName);
    Portfolio p = pfs.remove(pIndex);
    double max = portfolioValue(portfolioName, date);
    for (String a : weights.keySet()) {
      Double shareCount = pfs.get(pIndex).getPortfolioContents().get(a);
      Double perc = weights.get(a);
      List<Double> stockValue = this.getStockInfo(a, 1, date);
      Double newVal = (perc / 100) * max;
      newVal = newVal / stockValue.get(0);
      shareCount = shareCount - newVal;
      if (shareCount < 0) {
        p.addToPortfolio(stock, date, Math.abs(shareCount));
      }
      else if (shareCount > 0) {
        p.removeFromPortfolio(stock, date, shareCount);
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
      stockList.add(stock.getKey().toString());
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
  public List<Portfolio> getPortfolios() {
    return portfolios;
  }

  @Override
  public Double getPortfolioValue(String name, String date) {
    Double value = this.portfolioValue(name, date);
    return value;
  }
}
