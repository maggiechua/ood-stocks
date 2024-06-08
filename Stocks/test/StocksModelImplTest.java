import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import stocks.StocksController;
import stocks.StocksModel;
import stocks.StocksModelImpl;
import stocks.StocksView;

import static org.junit.Assert.assertEquals;

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
    model = new StocksModelImpl("GOOG", new HashMap<String,HashMap<String, Integer>>());
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
  @Test
  public void testGainLossFutureDate() throws IllegalArgumentException {
    String input = "check-gain-loss 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();

  }

  @Test
  public void testGainLossDateStockDidNotExistYet() throws IllegalArgumentException {
    String input = "check-gain-loss 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testGainLossNonMarketDay() throws IllegalArgumentException {
    String input = "check-gain-loss 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
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
  @Test
  public void testMovingAvgFutureDate() throws IllegalArgumentException {
    String input = "moving-average 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testMovingAvgDidNotExistYet() throws IllegalArgumentException {
    String input = "moving-average 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testMovingAvgNonMarketDay() throws IllegalArgumentException {
    String input = "moving-average 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
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
  @Test
  public void testCrossOversFutureDate() throws IllegalArgumentException {
    String input = "check-crossovers 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testCrossOversDateStockDidNotExistYet() throws IllegalArgumentException {
    String input = "check-crossover 5 2000-05-09";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testCrossOversNonMarketDay() throws IllegalArgumentException {
    String input = "check-crossover 5 2024-05-25";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testCrossOversBeyondScope() {
    // basically that the time period to calculate has a final day that does not exist
    // in the stock data
    String input = "check-crossover 5 2014-03-27";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  // PORTFOLIO METHODS
  @Test
  public void testCreatePortfolio() {
    String input = "create-portfolio a";
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    assertEquals(expectedPortfolio, model.getPortfolios());
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    expectedPortfolio.put("a", new HashMap<String, Integer>());
    assertEquals(expectedPortfolio, model.getPortfolios());
  }

  @Test
  public void testBuyStock() {
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("a", new HashMap<String, Integer>());
    expectedPortfolio.get("a").put("GOOG", 15);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String input = "buy-stock 15 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    assertEquals(expectedPortfolio.get("a").get("GOOG"),
            model.getPortfolios().get("a").get("GOOG"));
  }

  @Test
  public void testSellStockHaveEnoughShares() {
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("a", new HashMap<String, Integer>());
    expectedPortfolio.get("a").put("GOOG", 6);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String setup3 = "buy-stock 15 a";
    controller = new StocksControllerMock(model, view, setup3, true, ap);
    controller.execute();

    String input = "sell-stock GOOG 9 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
    assertEquals(expectedPortfolio.get("a").get("GOOG"),
            model.getPortfolios().get("a").get("GOOG"));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSellStockDontOwnStock() {
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("a", new HashMap<String, Integer>());

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String input = "sell-stock GOOG 9 a";
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

    String input = "sell-stock GOOG 9 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }

  @Test
  public void testSellStockNotEnoughSharesDoesNotSubtractFromPortfolio() {
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    expectedPortfolio.put("a", new HashMap<String, Integer>());
    expectedPortfolio.get("a").put("GOOG", 6);

    String setup1 = "create-portfolio a";
    controller = new StocksControllerMock(model, view, setup1, true, ap);
    controller.execute();

    String setup2 = "select-stock GOOG";
    controller = new StocksControllerMock(model, view, setup2, true, ap);
    controller.execute();

    String setup3 = "buy-stock 6 a";
    controller = new StocksControllerMock(model, view, setup3, true, ap);
    controller.execute();
    assertEquals(expectedPortfolio.get("a").get("GOOG"),
            model.getPortfolios().get("a").get("GOOG"));

    String input = "sell-stock GOOG 9 a";
    controller = new StocksControllerMock(model, view, input, true, ap);
    try {
      controller.execute();
    }
    catch (Exception e) {
      //
    }
    assertEquals(expectedPortfolio.get("a").get("GOOG"),
            model.getPortfolios().get("a").get("GOOG"));
  }

  @Test
  public void testPortfolioValue() {
    String input = "check-portfolio a";
    HashMap<String,HashMap<String, Integer>> expectedPortfolio = new HashMap<>();
    controller = new StocksControllerMock(model, view, input, true, ap);
    controller.execute();
  }
}