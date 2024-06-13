import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.Port;

import stocks.Portfolio;
import stocks.StocksController;
import stocks.StocksModel;
import stocks.StocksModelImpl;
import stocks.StocksView;
import stocks.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//TODO: debug the portfolio value test

/**
 * A JUnit Test Class for the Stocks Model Implementation.
 */
public class StocksModelImplTest {
  private Appendable ap;
  private StocksView view;
  private StocksModel model;
  private StocksController controller;

  @Before
  public void setUp() {
    ap = new StringBuilder();
    view = new StocksViewMock(ap);
    model = new StocksModelImpl("GOOG", new ArrayList<Portfolio>());
  }

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

  // PORTFOLIO METHODS
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

  @Test
  public void testCreatePortfolio() {
    String input = "create-portfolio a";
    List<Portfolio> expectedPortfolios = new ArrayList<>();
    assertEquals(expectedPortfolios, model.getPortfolios());
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    Portfolio expectedPortfolio = new Portfolio("a", new HashMap<String, Double>(),
            new ArrayList<Transaction>());
    assertEquals(expectedPortfolio.getName(), model.getPortfolios().get(0).getName());
    assertEquals(expectedPortfolio.getPortfolioContents(),
            model.getPortfolios().get(0).getPortfolioContents());
  }

  @Test
  public void testBuyStock() {
    Portfolio p = new Portfolio("a", new HashMap<String, Double>(),
            new ArrayList<Transaction>());
    p.addToPortfolio("GOOG", "2024-05-31", 15.0);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String input = "buy-stock 15 2024-05-31 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
    assertTrue(this.compareTransactions(p.getTransactionsLog().get(0),
            model.getPortfolios().get(0).getTransactionsLog().get(0)));
  }

  @Test
  public void testSellStockHaveEnoughShares() {
    Portfolio p = new Portfolio("a", new HashMap<String, Double>(),
            new ArrayList<Transaction>());
    p.addToPortfolio("GOOG", "2024-05-30", 6);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String setup3 = "buy-stock 15 2024-05-30 a";
    controller = new StocksControllerMock(model, view, setup3, true, ap);
    controller.execute();

    String input = "sell-stock GOOG 9 2024-05-31 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    assertEquals(p.getPortfolioContents().get("GOOG"),
            model.getPortfolios().get(0).getPortfolioContents().get("GOOG"));
//    assertEquals(p.getTransactionsLog().size(),
//            model.getPortfolios().get(0).getTransactionsLog().size());
    assertTrue(this.compareTransactions(p.getTransactionsLog().get(0),
            model.getPortfolios().get(0).getTransactionsLog().get(0)));
    assertTrue(this.compareTransactions(p.getTransactionsLog().get(1),
            model.getPortfolios().get(0).getTransactionsLog().get(1)));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSellStockDontOwnStock() {
    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String input = "sell-stock GOOG 9 2024-05-31 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSellStockNotEnoughShares() {
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("a", new HashMap<String, Integer>());
    expectedPortfolio.get("a").put("GOOG", 6);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String input = "sell-stock GOOG 9 2024-05-31 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

//  @Test
//  public void testSellStockNotEnoughSharesDoesNotSubtractFromPortfolio() {
//    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
//    expectedPortfolio.put("a", new HashMap<String, Integer>());
//    expectedPortfolio.get("a").put("GOOG", 6);
//
//    String setup1 = "create-portfolio a";
//    controller = new StocksControllerMock(model, view, setup1, true, ap);
//    controller.execute();
//
//    String setup2 = "select-stock GOOG";
//    controller = new StocksControllerMock(model, view, setup2, true, ap);
//    controller.execute();
//
//    String setup3 = "buy-stock 6 a";
//    controller = new StocksControllerMock(model, view, setup3, true, ap);
//    controller.execute();
//    assertEquals(expectedPortfolio.get("a").get("GOOG"),
//            model.getPortfolios().get("a").get("GOOG"));
//
//    String input = "sell-stock GOOG 9 a";
//    controller = new StocksControllerMock(model, view, input, true, ap);
//    try {
//      controller.execute();
//    }
//    catch (Exception e) {
//      //
//    }
//    assertEquals(expectedPortfolio.get("a").get("GOOG"),
//            model.getPortfolios().get("a").get("GOOG"));
//  }
//
//  @Test
//  public void testEmptyPortfolioValue() {
//    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
//    expectedPortfolio.put("a", new HashMap<String, Integer>());
//    double expectedPortfolioValue = 0.0;
//
//    String setup1 = "create-portfolio a";
//    controller = new StocksControllerMock(model, view, setup1, true, ap);
//    controller.execute();
//
//    double portfolioValue = model.getPortfolioValue("a", "2024-05-29");
//    assertEquals(expectedPortfolioValue, portfolioValue, 0.01);
//  }
//
//  // still incomplete
//  @Test
//  public void testPortfolioValueWithStocks() {
//    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
//    expectedPortfolio.put("a", new HashMap<String, Integer>());
//    expectedPortfolio.get("a").put("GOOG", 5);
//    expectedPortfolio.get("a").put("NVDA", 25);
//    expectedPortfolio.get("a").put("MSFT", 20);
//    double expectedPortfolioValue = 38176.65;
//
//    String setup1 = "create-portfolio a";
//    controller = new StocksControllerMock(model, view, setup1, true, ap);
//    controller.execute();
//
//    String setup2 = "select-stock GOOG";
//    controller = new StocksControllerMock(model, view, setup2, true, ap);
//    controller.execute();
//
//    String setup3 = "buy-stock 5 a";
//    controller = new StocksControllerMock(model, view, setup3, true, ap);
//    controller.execute();
//
//    String setup4 = "quit";
//    controller = new StocksControllerMock(model, view, setup4, true, ap);
//    controller.execute();
//
//    String setup5 = "select-stock NVDA";
//    controller = new StocksControllerMock(model, view, setup5, true, ap);
//    controller.execute();
//
//    String setup6 = "buy-stock 25 a";
//    controller = new StocksControllerMock(model, view, setup6, true, ap);
//    controller.execute();
//
//    String setup7 = "quit";
//    controller = new StocksControllerMock(model, view, setup7, true, ap);
//    controller.execute();
//
//    String setup8 = "select-stock MSFT";
//    controller = new StocksControllerMock(model, view, setup8, true, ap);
//    controller.execute();
//
//    String setup9 = "buy-stock 20 a";
//    controller = new StocksControllerMock(model, view, setup9, true, ap);
//    controller.execute();
//
//    // check that the portfolio contains the expected number of shares for each stock
////    assertEquals(expectedPortfolio.get("a").get("GOOG"),
////            model.getPortfolios().get("a").get("GOOG"));
////    assertEquals(expectedPortfolio.get("a").get("NVDA"),
////            model.getPortfolios().get("a").get("NVDA"));
////    assertEquals(expectedPortfolio.get("a").get("MSFT"),
////            model.getPortfolios().get("a").get("MSFT"));
//
//    String setup10 = "menu";
//    controller = new StocksControllerMock(model, view, setup10, true, ap);
//    controller.execute();
//
//    String input = "check-portfolio a 2024-05-29";
//    controller = new StocksControllerMock(model, view, input, true, ap);
//    controller.execute();
//    double portfolioValue = model.getPortfolioValue("a", "2024-05-29");
//    assertEquals(expectedPortfolio.get("a"),
//            model.getPortfolios().get("a"));
//    assertEquals(expectedPortfolioValue, portfolioValue, 0.01);
//  }
}