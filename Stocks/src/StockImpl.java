import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class StockImpl implements Stock {
  Stock stock;
  HashMap<String,HashMap<Stock, Integer>> portfolios;

  public StockImpl() {
    this.portfolios = new HashMap<String, HashMap<Stock, Integer>>();
  }

  public StockImpl(Stock stock) {
    this.stock = stock;
  }

  protected StockImpl(Stock stock, HashMap<String, HashMap<Stock, Integer>> portfolios) {
    this.stock = stock;
    this.portfolios = portfolios;
  }

  public static void API() {
    String apiKey = "5APRD6N4EPK0WCIS";
  }

  public void getFileInfo(String stockSymbol) {
    int year = 2020;
    int month = 1;
    int day = 1;
    String directoryPath = "Stocks/data/";
    String fileName = stockSymbol + ".csv";
    Path path = Path.of(directoryPath + fileName);
    File file = path.toFile();

    if (!path.toFile().exists()) {
      // call API to generate file
    }
    StringBuilder output = new StringBuilder();
    try {
      String today = String.format("%04d-%02d-%02d", year, month, day);
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] lineInfo = line.split(",");
        for (int i = 0; i < lineInfo.length; i++) {
          System.out.println("Line Info: " + lineInfo[i]);
        }
        if (lineInfo[0].equals(today)) {
          System.out.println(today + " Opening Price: " + lineInfo[1]);
        }
      }
    }
    catch (IOException e) {
      System.out.println("catch");
    }
  }

  @Override
  public StockImpl stockSelect(Stock stock) {
    return new StockImpl(stock);
  }

  @Override
  public Double gainLoss(String date1, String date2) {
    return null;
  }

  @Override
  public Double movingAvg(Integer numOfDays, String date) {
    return null;
  }

  @Override
  public String crossovers(Integer numOfDays, String date1, String date2) {
    return null;
  }

  @Override
  public StockImpl createPortfolio(String name) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> newp = new HashMap<>();
    pfs.put(name, newp);
    return new StockImpl(this, pfs);
  }

  @Override
  public StockImpl buy(Stock stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> currentportfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    if (currentportfolio.containsKey(stock)) {
      currentportfolio.put(stock, currentportfolio.get(stock) + shares);
    }
    else {
      currentportfolio.put(stock, shares);
    }
    pfs.put(portfolioName, currentportfolio);
    return new StockImpl(stock, pfs);
  }

  @Override
  public StockImpl sell(Stock stock, Integer shares, String portfolioName) {
    HashMap<String, HashMap<Stock, Integer>> pfs = this.portfolios;
    HashMap<Stock, Integer> currentportfolio = pfs.get(portfolioName);
    pfs.remove(portfolioName);
    try {
      if (currentportfolio.containsKey(stock)) {
        if (currentportfolio.get(stock) >= shares) {
          currentportfolio.put(stock, currentportfolio.get(stock) - shares);
        } else {
          throw new IllegalArgumentException("not enough shares to sell");
        }
      } else {
        throw new IllegalArgumentException("you don't own this stock");
      }
    }
    catch (Exception e) {
      System.err.println("Not enough shares of this stock in this portfolio to sell.");
    }
    pfs.put(portfolioName, currentportfolio);
    return new StockImpl(stock, pfs);
  }

  @Override
  public Double portfolioValue(String portfolioName, String date) {
    return null;
  }
}
