import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import stocks.CompareTransaction;
import stocks.FileCreator;
import stocks.FileParser;
import stocks.Portfolio;
import stocks.StocksModel;
import stocks.StocksModelImpl;
import stocks.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit Test Class for testing FileParser and FileCreator classes.
 */
public class FilesTest {
  private FileParser fp;
  private FileCreator fc;
  private StocksModel model;
  private CompareTransaction ct;

  @Before
  public void setUp() {
    fp = new FileParser();
    fc = new FileCreator();
    model = new StocksModelImpl("", new ArrayList<Portfolio>());
    ct = new CompareTransaction();
    this.createTestingFile();
  }

  /**
   * The following method compares the given two transactions by their contents.
   * @param t1 the first transaction
   * @param t2 the second transaction
   * @return a boolean if both transactions are equivalent, otherwise false
   */
  public boolean compareTransactions(Transaction t1, Transaction t2) {
    return t1.getStock().equals(t2.getStock())
            && t1.getShares().equals(t2.getShares())
            && t1.getDate().equals(t2.getDate())
            && t1.getPrice().equals(t2.getPrice());
  }

  /**
   * The following method is used to create a testing file consisting of randomized transactions
   * sorted in chronological order.
   */
  public void createTestingFile() {
    Map<String, Double> p = new HashMap<>();
    List<Transaction> sampleTransactions = new ArrayList<>();
    List<String> stocks = new ArrayList<>(Arrays.asList(
            "AAPL", "AMZN", "GOOG", "MSFT", "NVDA", "TSLA"));
    Random rand = new Random(20);
    int numTransactions = rand.nextInt(500);
    for (int i = 0; i < numTransactions; i++) {
      String stock = stocks.get(rand.nextInt(stocks.size()));
      Path path = fp.retrievePath(fp.getOSType(), stock, "data/", ".csv");
      int lineCount = 0;
      try {
        lineCount = Math.toIntExact(Files.lines(Paths.get(path.toString())).count() - 3);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      int line = rand.nextInt(lineCount) + 2;
      int buySell = rand.nextInt(2);
      int val = 0;
      if (buySell == 0) { val = 1; }
      else { val = -1; }
      double shares = val * rand.nextInt(500) * rand.nextDouble();
      String date = null;
      double price = 0.0;
      try {
        date = String.valueOf(Files.lines(Paths.get(path.toString()))
                .skip(line - 1).findFirst());
        String[] removeOptional = date.split("Optional");
        String[] dateSplit = removeOptional[1].substring(1).split(",");
        date = dateSplit[0];
        price = Double.valueOf(dateSplit[4]);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      Transaction t = new Transaction(stock, shares, date, price);
      sampleTransactions.add(t);
    }
    sampleTransactions.sort(ct);
    List<Transaction> validT = new ArrayList<>();
    for (Transaction t : sampleTransactions) {
      if (t.getShares() < 0) {
        for (Map.Entry<String, Double> entry : p.entrySet()) {
          if (entry.getKey().equals(t.getStock())) {
            if (entry.getValue() >= t.getShares()) {
              validT.add(t);
            }
          }
        }
      }
      else {
        validT.add(t);
      }
      if (!p.containsKey(t.getStock()) && t.getShares() > 0) {
        p.put(t.getStock(), t.getShares());
      }
      for (Map.Entry<String, Double> entry : p.entrySet()) {
        if (entry.getKey().equals(t.getStock())) {
          p.put(entry.getKey(), entry.getValue() + t.getShares());
        }
      }
    }
    fc.createNewPortfolioFile("testing", validT);
  }

  @Test
  public void testReadXMLFile() {
    Path path = fp.retrievePath(fp.getOSType(), "testing", "portfolios/", ".xml");
    List<Transaction> expectedT = this.expectedTransactionsList();

    List<Transaction> readT = fp.parsePortfolioTransactions(path, "2005-03-09");

    int index = 0;
    for (Transaction e : expectedT) {
      assertTrue(this.compareTransactions(e, readT.get(index)));
      index++;
    }
    assertEquals(expectedT.size(), readT.size());
  }

  public List<Transaction> expectedTransactionsList() {
    List<Transaction> expectedT = new ArrayList<>();
    expectedT.add(new Transaction(
            "AMZN", 220.59879313864548, "2000-05-11", 54.88));
    expectedT.add(new Transaction(
            "NVDA", 367.5897437590797, "2000-05-17", 110.5));
    expectedT.add(new Transaction(
            "AMZN", -87.38533623853627, "2000-07-07", 36.13));
    expectedT.add(new Transaction(
            "AMZN", 128.10737861428512, "2000-09-07", 43.5));
    expectedT.add(new Transaction(
            "AAPL", 164.56142862382467, "2000-09-25", 53.5));
    expectedT.add(new Transaction(
            "NVDA", -174.0856803019214, "2000-12-18", 38.67));
    expectedT.add(new Transaction(
            "AMZN", 63.11264944241152, "2001-01-09", 16.38));
    expectedT.add(new Transaction(
            "AMZN", -18.709303829608388, "2001-06-05", 16.44));
    expectedT.add(new Transaction(
            "AAPL", -191.0119840212909, "2001-06-08", 21.32));
    expectedT.add(new Transaction(
            "AMZN", 292.8662145241113, "2001-07-09", 15.81));
    expectedT.add(new Transaction(
            "AMZN", -212.62641056268453, "2001-07-24", 12.06));
    expectedT.add(new Transaction(
            "AMZN", 214.63805728495873, "2001-08-10", 9.95));
    expectedT.add(new Transaction(
            "AMZN", 156.78633162926516, "2001-09-24", 7.46));
    expectedT.add(new Transaction(
            "AAPL", 5.84525067214678, "2001-09-27", 15.51));
    expectedT.add(new Transaction(
            "AAPL", 28.660563420366252, "2002-02-05", 25.45));
    expectedT.add(new Transaction(
            "NVDA", 55.24910646143612, "2002-04-12", 36.68));
    expectedT.add(new Transaction(
            "MSFT", 33.48723469966959, "2002-04-30", 52.26));
    expectedT.add(new Transaction(
            "MSFT", -2.5681262682685215, "2002-07-11", 52.91));
    expectedT.add(new Transaction(
            "AMZN", -5.226577905143058, "2002-08-05", 12.87));
    expectedT.add(new Transaction(
            "MSFT", 71.73261761166253, "2002-08-06", 45.67));
    expectedT.add(new Transaction(
            "NVDA", 195.5493202362089, "2002-12-19", 12.65));
    expectedT.add(new Transaction(
            "AMZN", -418.63201159799894, "2003-01-21", 21.08));
    expectedT.add(new Transaction(
            "AAPL", 126.33143133727171, "2003-02-07", 14.15));
    expectedT.add(new Transaction(
            "NVDA", 131.57297165428707, "2003-02-13", 9.87));
    expectedT.add(new Transaction(
            "AMZN", 244.14083975653952, "2003-03-03", 21.81));
    expectedT.add(new Transaction(
            "MSFT", -0.2533034164386625, "2003-05-08", 25.74));
    expectedT.add(new Transaction(
            "AMZN", -99.6982340590818, "2003-06-27", 36.3));
    expectedT.add(new Transaction(
            "NVDA", -11.754950728940821, "2003-07-17", 22.0));
    expectedT.add(new Transaction(
            "MSFT", 339.345413947359, "2003-07-22", 26.38));
    expectedT.add(new Transaction(
            "AMZN", -217.7844672177314, "2003-12-05", 51.56));
    expectedT.add(new Transaction(
            "NVDA", 156.2899147973765, "2004-01-27", 22.63));
    expectedT.add(new Transaction(
            "MSFT", -32.25783656427379, "2004-02-03", 27.29));
    expectedT.add(new Transaction(
            "AMZN", 28.576582534226322, "2004-03-18", 43.01));
    expectedT.add(new Transaction(
            "AMZN", -15.908718820883585, "2004-06-17", 49.77));
    expectedT.add(new Transaction(
            "MSFT", 458.47522177939214, "2004-10-22", 27.74));
    expectedT.add(new Transaction(
            "NVDA", -236.04727828571205, "2004-11-09", 17.72));
    expectedT.add(new Transaction(
            "NVDA", -142.65499977302264, "2005-03-09", 25.75));

    return expectedT;
  }
  
}
