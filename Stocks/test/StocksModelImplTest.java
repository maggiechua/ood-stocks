import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.Portfolio;
import stocks.PortfolioImpl;
import stocks.StocksController;
import stocks.StocksModel;
import stocks.StocksModelImpl;
import stocks.StocksView;
import stocks.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * A JUnit Test Class for the Stocks Model Implementation.
 */
public class StocksModelImplTest {
  private Appendable ap;
  private StocksView view;
  private StocksModel model;
  private StocksController controller;
  private PortfolioImpl p;

  @Before
  public void setUp() {
    ap = new StringBuilder();
    view = new StocksViewMock(ap);
    model = new StocksModelImpl("GOOG", new ArrayList<Portfolio>());
    p = new PortfolioImpl("a", new HashMap<String, Double>(),
            new ArrayList<Transaction>());
  }

  /**
   * The following method is used to aid in testing different test cases in the Stocks Model.
   * @param commands a list of commands to be sent to the mock controller to be executed.
   */
  public void testingHelper(List<String> commands) {
    for (String command : commands) {
      controller = new StocksControllerMock(model, view, command, true, ap);
      controller.execute();
    }
  }

  // ALL PORTFOLIO METHODS TESTS >>>
  // Testing Methods:
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

  // Portfolio Transactions:
  // Note: Since we have a while loop in the original controller that allows the user to exit the
  // stock menu, we prove that those work in the view tests, but for the purposes of the model,
  // we simplify the information transfer process to prove that the model methods work, which is
  // why the inputs given do not match 1:1
  @Test
  public void testCreatePortfolio() {
    List<String> input = new ArrayList<>(Arrays.asList("create-portfolio a"));
    List<PortfolioImpl> expectedPortfolios = new ArrayList<>();
    assertEquals(expectedPortfolios, model.getPortfolios());
    this.testingHelper(input);
    expectedPortfolios.add(p);
    assertEquals(expectedPortfolios.get(0).getName(), model.getPortfolios().get(0).getName());
    assertEquals(expectedPortfolios.get(0).getPortfolioContents(),
            model.getPortfolios().get(0).getPortfolioContents());
  }

  @Test
  public void testBuyStock() {
    p.addToPortfolio("GOOG", "2024-05-31", 15.0);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG", "buy-stock GOOG 15 2024-05-31 a"));
    this.testingHelper(commands);

    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertEquals(p.getTransactionsLog().size(),
            model.getPortfolios().get(0).getTransactionsLog().size());
    assertTrue(this.compareTransactions(p.getTransactionsLog().get(0),
            model.getPortfolios().get(0).getTransactionsLog().get(0)));
  }

  @Test
  public void testBuyStockNonMarketDay() {
    p.addToPortfolio("GOOG", "2024-05-28", 15.0);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG", "buy-stock GOOG 15 2024-05-25 a"));
    this.testingHelper(commands);

    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertEquals(p.getTransactionsLog().get(0).getDate(),
            model.getPortfolios().get(0).getTransactionsLog().get(0).getDate());
    System.out.println(model.getPortfolios().get(0).getTransactionsLog().get(0).getDate());
  }

  @Test
  public void testSellStockNonMarketDay() {
    p.addToPortfolio("GOOG", "2024-05-24", 15.0);
    p.removeFromPortfolio("GOOG", "2024-05-28", 15.0);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG", "buy-stock GOOG 15 2024-05-24 a",
            "sell-stock GOOG 15 2024-05-25 a"));
    this.testingHelper(commands);

    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertEquals(p.getTransactionsLog().get(1).getDate(),
            model.getPortfolios().get(0).getTransactionsLog().get(1).getDate());
  }

  @Test
  public void testSellStockHaveEnoughShares() {
    p = new PortfolioImpl("a", new HashMap<String, Double>(),
            new ArrayList<Transaction>());
    p.addToPortfolio("GOOG", "2024-05-30", 15);
    p.removeFromPortfolio("GOOG", "2024-05-31", 9);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG", "buy-stock GOOG 15 2024-05-30 a",
            "sell-stock GOOG 9 2024-05-31 a"));

    this.testingHelper(commands);

    // checks that it does not subtract from portfolio if there is not enough stock
    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));

    // checks that the transaction log is the same size
    assertEquals(p.getTransactionsLog().size(),
            model.getPortfolios().get(0).getTransactionsLog().size());

    // checks that the transactions are equivalent
    assertTrue(this.compareTransactions(p.getTransactionsLog().get(0),
            model.getPortfolios().get(0).getTransactionsLog().get(0)));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSellStockDontOwnStock() {
    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "sell-stock GOOG 9 2024-05-31 a"));

    this.testingHelper(commands);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSellStockNotEnoughShares() {
    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG", "buy-stock 5 2024-05-30 a",
            "sell-stock GOOG 9 2024-05-31 a"));

    this.testingHelper(commands);
  }

  @Test
  public void testEmptyPortfolioValue() {
    double expectedPortfolioValue = p.calculateValue("2024-05-30");

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    double portfolioValue = model.getPortfolioValue("a", "2024-05-30");
    assertEquals(expectedPortfolioValue, portfolioValue, 0.01);
  }

  @Test
  public void testPortfolioValueWithStocks() {
    p.addToPortfolio("GOOG", "2024-05-30", 5);
    p.addToPortfolio("NVDA", "2024-05-30", 25);
    p.addToPortfolio("MSFT", "2024-05-30", 20);
    double expectedPortfolioValue = 38176.65;

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG",
            "buy-stock GOOG 5 2024-05-30 a",
            "buy-stock NVDA 25 2024-05-30 a",
            "buy-stock MSFT 20 2024-05-30 a"
    ));
    this.testingHelper(commands);

    // check that the portfolio contains the expected number of shares for each stock
    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertEquals(p.getPortfolioContents().get("NVDA"),
            model.getPortfolios().get(0).getPortfolioContents().get("NVDA"));
    assertEquals(p.getPortfolioContents().get("MSFT"),
            model.getPortfolios().get(0).getPortfolioContents().get("MSFT"));

    List<String> checkVal = new ArrayList<>(Arrays.asList(
            "menu", "check-portfolio a 2024-05-31"));
    this.testingHelper(checkVal);

    assertEquals(p.calculateValue("2024-05-31"),
            model.getPortfolios().get(0).calculateValue("2024-05-31"), 0.01);
  }

  @Test
  public void testReBalanceWithStocks() {
    // comments on the side represent stock price values on 2024-05-31 where 50 shares are
    // bought for the following four stocks
    p.addToPortfolio("GOOG", "2024-05-30", 134.92110255231086);
    // 8,698.00 | 173.96/s | 84.9211026
    p.addToPortfolio("NVDA", "2024-05-30", 21.408585918473452);
    // 54,816.50 | 1096.33/s | -28.5914141
    p.addToPortfolio("MSFT", "2024-05-30", 56.538614409943875);
    // 20,756.50 | 415.13/s | +6.53861441
    p.addToPortfolio("AAPL", "2024-05-30", 122.0851755526658);
    // 9,717.50 | 192.25/s | +72.0851756

    // $93,883.5 (prior) | 23,470.875 (25%)
    double expectedTotalPValue = 93883.5;

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "buy-stock GOOG 50 2024-05-30 a",
            "buy-stock NVDA 50 2024-05-30 a",
            "buy-stock MSFT 50 2024-05-30 a",
            "buy-stock AAPL 50 2024-05-30 a",
            "balance a 2024-05-31 GOOG 25.0 NVDA 25.0 MSFT 25.0 AAPL 25.0"));
    this.testingHelper(commands);

    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertEquals(p.getPortfolioContents().get("NVDA"),
            model.getPortfolios().get(0).getPortfolioContents().get("NVDA"));
    assertEquals(p.getPortfolioContents().get("MSFT"),
            model.getPortfolios().get(0).getPortfolioContents().get("MSFT"));
    assertEquals(p.getPortfolioContents().get("AAPL"),
            model.getPortfolios().get(0).getPortfolioContents().get("AAPL"));
    assertEquals(expectedTotalPValue,
            model.getPortfolios().get(0).calculateValue("2024-05-31"), 0.01);
  }

  // Portfolio Contents:
  @Test
  public void testDistribution() {
    p.addToPortfolio("GOOG", "2024-05-30", 5);
    p.addToPortfolio("NVDA", "2024-05-30", 25);
    p.addToPortfolio("MSFT", "2024-05-30", 20);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "select-stock GOOG",
            "buy-stock GOOG 5 2024-05-30 a",
            "buy-stock NVDA 25 2024-05-30 a",
            "buy-stock MSFT 20 2024-05-30 a",
            "distribution a 2024-05-31"));
    this.testingHelper(commands);

    Map<String, Double> expectedDistribution = new HashMap<>();
    expectedDistribution.put("GOOG", 869.8);
    expectedDistribution.put("NVDA", 27408.25);
    expectedDistribution.put("MSFT", 8302.6);
    Double expectedTotalPValue = 36580.65;
    Map<String, Double> testDistribution = model.getPortfolios()
            .get(0).findDistribution("2024-05-31");
    Double testTotalPValue = model.getPortfolios().get(0).calculateValue("2024-05-31");

    assertEquals(expectedDistribution.get("GOOG"), testDistribution.get("GOOG"));
    assertEquals(expectedDistribution.get("NVDA"), testDistribution.get("NVDA"));
    assertEquals(expectedDistribution.get("MSFT"), testDistribution.get("MSFT"));
    assertEquals(expectedTotalPValue, testTotalPValue);
  }

  @Test
  public void testComposition() {
    p.addToPortfolio("GOOG", "2024-05-30", 5);
    p.addToPortfolio("NVDA", "2024-05-30", 25);
    p.addToPortfolio("MSFT", "2024-05-30", 20);

    List<String> commands = new ArrayList<>(Arrays.asList(
            "create-portfolio a", "buy-stock GOOG 5 2024-05-30 a",
            "buy-stock NVDA 25 2024-05-30 a",
            "buy-stock MSFT 20 2024-05-30 a",
            "composition a 2024-05-31"));
    this.testingHelper(commands);

    Map<String, Double> expectedComposition = new HashMap<>();
    expectedComposition.put("GOOG", 5.0);
    expectedComposition.put("NVDA", 25.0);
    expectedComposition.put("MSFT", 20.0);
    Map<String, Double> testComposition = model.composition("a", "2024-05-31");

    assertEquals(expectedComposition.get("GOOG"), testComposition.get("GOOG"));
    assertEquals(expectedComposition.get("NVDA"), testComposition.get("NVDA"));
    assertEquals(expectedComposition.get("MSFT"), testComposition.get("MSFT"));
  }

  // ALL STOCK STATS CALCULATION TESTS >>>
  // GAIN-LOSS
  @Test
  public void testGainLoss5Day() {
    String input = "check-gain-loss 5 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = -2.14;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testGainLoss30Day() {
    String input = "check-gain-loss 30 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 21.4;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testGainLoss90Day() {
    String input = "check-gain-loss 90 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 29.43;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testGainLoss365Day() {
    String input = "check-gain-loss 365 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 81.55;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  // GAIN-LOSS EXCEPTIONS
  @Test (expected = IllegalArgumentException.class)
  public void testGainLossFutureDate() {
    String input = "check-gain-loss 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGainLossDateStockDidNotExistYet() {
    String input = "check-gain-loss 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGainLossNonMarketDay() {
    String input = "check-gain-loss 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGainLossBeyondScope() {
    // basically that the time period to calculate has a final day that does not exist
    // in the stock data
    String input = "check-gain-loss 5 2014-03-27";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  // MOVING-AVG
  @Test
  public void testMovingAvg5Day() {
    String input = "moving-average 5 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 176.96;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testMovingAvg30Day() {
    String input = "moving-average 30 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 169.47;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testMovingAvg90Day() {
    String input = "moving-average 90 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 154.64;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  @Test
  public void testMovingAvg365Day() {
    String input = "moving-average 365 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    double expectedResult = 128.07;
    assertEquals(expectedResult, Double.parseDouble(ap.toString()), 0.01);
  }

  // MOVING-AVG EXCEPTIONS
  @Test (expected = IllegalArgumentException.class)
  public void testMovingAvgFutureDate() {
    String input = "moving-average 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMovingAvgDidNotExistYet() {
    String input = "moving-average 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMovingAvgNonMarketDay() {
    String input = "moving-average 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMovingAvgBeyondScope() {
    // basically that the time period to calculate has a final day that does not exist
    // in the stock data
    String input = "moving-average 5 2014-03-27";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  // CROSSOVERS
  @Test
  public void testCrossOver5DayTrue() {
    String input = "check-crossovers 5 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "Yes, this date is a 5-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  @Test
  public void testCrossOver30DayTrue() {
    String input = "check-crossovers 30 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "Yes, this date is a 30-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  @Test
  public void testCrossOver5DayFalse() {
    String input = "check-crossovers 5 2024-03-06";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "No, this date is not a 5-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  @Test
  public void testCrossOver30DayFalse() {
    String input = "check-crossovers 30 2024-03-13";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "No, this date is not a 30-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  @Test
  public void testCrossOver90Day() {
    String input = "check-crossovers 90 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "Yes, this date is a 90-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  @Test
  public void testCrossOver365Day() {
    String input = "check-crossovers 365 2024-05-29";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    String expectedResult = "Yes, this date is a 365-day crossover.";
    assertEquals(expectedResult, ap.toString());
  }

  // CROSSOVER EXCEPTIONS
  @Test (expected = IllegalArgumentException.class)
  public void testCrossOversFutureDate() {
    String input = "check-crossovers 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCrossOversDateStockDidNotExistYet() {
    String input = "check-crossovers 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCrossOversNonMarketDay() {
    String input = "check-crossovers 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCrossOversBeyondScope() {
    // basically that the time period to calculate has a final day that does not exist
    // in the stock data
    String input = "check-crossovers 5 2014-03-27";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }
}