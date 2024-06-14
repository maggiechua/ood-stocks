package stocks;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class constructs a PortfolioImpl containing a name, contents, and a log of transactions.
 */
public class PortfolioImpl implements Portfolio {
  private String name;
  private Map<String, Double> contents;
  private List<Transaction> transactions;
  private FileCreator fc;
  private FileParser fp;
  private CompareTransaction cp;

  /**
   * This constructor creates a PortfolioImpl object.
   * @param name the given name of the portfolio
   * @param contents the existing contents of the portfolio
   * @param transactions a list of transactions for this portfolio
   */
  public PortfolioImpl(String name, Map<String, Double> contents, List<Transaction> transactions) {
    this.name = name;
    this.contents = contents;
    this.transactions = transactions;
    this.fc = new FileCreator();
    this.fp = new FileParser();
    this.cp = new CompareTransaction();
    transactions.sort(this.cp);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Map<String, Double> getPortfolioContents() {
    return this.contents;
  }

  @Override
  public List<Transaction> getTransactionsLog() {
    return this.transactions;
  }

  @Override
  public void loadPortfolio(Path path, String date) {
    // TODO: this
  }

  @Override
  public void savePortfolio() {
    List<Transaction> sortedTransactions = this.sortTransactions();
    fc.createNewPortfolioFile(this.name, sortedTransactions);
  }

  @Override
  public void addTransaction(String stock, String date, double shares) {
    Transaction t = new Transaction(stock, shares, date,
            Double.parseDouble(fp.getStockPrice(stock, date)));
    transactions.add(t);
  }

  @Override
  public List<Transaction> sortTransactions() {
    List<Transaction> lot = transactions;
    Collections.sort(lot, cp);
    return lot;
  }

  @Override
  public Set<String> getStocksList() {
    Set<String> stocks = new HashSet<>();
    for (Transaction t : transactions) {
      stocks.add(t.getStock());
    }
    return stocks;
  }

  @Override
  public void loadContents(Set<String> stocks) {
    for (String stock : stocks) {
      double numShares = 0.0;
      for (Transaction t : transactions) {
        if (t.getStock().equals(stock)) {
          numShares += t.getShares();
        }
      }
      contents.put(stock, numShares);
    }
  }

  @Override
  public PortfolioImpl addToPortfolio(String stock, String date, double shares) {
    Map<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      currentPortfolio.put(stock, currentPortfolio.get(stock) + shares);
    }
    else {
      currentPortfolio.put(stock, shares);
    }
    this.addTransaction(stock, date, shares);
    return new PortfolioImpl(this.name, currentPortfolio, this.transactions);
  }

  @Override
  public PortfolioImpl removeFromPortfolio(String stock, String date, double shares) {
    Map<String, Double> currentPortfolio = this.contents;
    if (currentPortfolio.containsKey(stock)) {
      if (currentPortfolio.get(stock) >= shares) {
        currentPortfolio.put(stock, currentPortfolio.get(stock) - shares);
        this.addTransaction(stock, date, -shares);
      } else {
        throw new IllegalArgumentException("not enough shares to sell");
      }
    } else {
      throw new IllegalArgumentException("you don't own this stock");
    }
    return new PortfolioImpl(this.name, currentPortfolio, this.transactions);
  }

  @Override
  public double calculateValue(String date) {
    double portfolioValue = 0.0;
    for (Map.Entry<String, Double> stock: contents.entrySet()) {
      Double stockPrice = Double.parseDouble(fp.getStockPrice(stock.getKey(), date));
      portfolioValue += stockPrice * stock.getValue();
    }
    return portfolioValue;
  }

  @Override
  public double calculateLastValue(String timePeriod, int time) {
    double portfolioValue = 0.0;
    for (Map.Entry<String, Double> stock: contents.entrySet()) {
      Double endPrice = Double.parseDouble(fp.getLastWorkingDay(stock.getKey(), timePeriod, time));
      portfolioValue += endPrice * stock.getValue();
    }
    return portfolioValue;
  }

  @Override
  public HashMap<String, Double> findDistribution(String date) {
    HashMap<String, Double> dist = new HashMap<>();
    for (Map.Entry<String, Double> stock: this.contents.entrySet()) {
      Double stockPrice = Double.parseDouble(fp.getStockPrice(stock.getKey(), date));
      dist.put(stock.getKey(), stock.getValue() * stockPrice);
    }
    return dist;
  }
}
