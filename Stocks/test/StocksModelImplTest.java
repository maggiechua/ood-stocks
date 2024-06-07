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
  // check whether i'm getting the correct value and it's being displayed properly

  private Appendable ap;
  private StocksView view;
  private StocksModel model;
  private StocksController controller;
  private HashMap<String,HashMap<String, Integer>> portfolios;

  @Before
  public void setUp() {
    portfolios = new HashMap<>();
    ap = new StringBuilder();
    view = new StocksViewMock(ap);
    model = new StocksModelImpl("GOOG", portfolios);
  }

  // EXCEPTIONS
  @Test
  public void testGainLossFutureDate() throws IllegalArgumentException {
    String input = "check-gain-loss 5 2024-07-09";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  @Test
  public void testGainLossDateStockDidNotExistYet() throws IllegalArgumentException {
    String input = "check-gain-loss 5";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  @Test
  public void testGainLossBeyondScope() {
    // basically that the time period to calculate has a final day that does not exist
    // in the stock data
    String input = "check-gain-loss 5";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  // GAIN-LOSS
  @Test
  public void testGainLoss5Day() {
    String input = "check-gain-loss 5 2024-05-31";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  @Test
  public void testGainLoss30Day() {
    String input = "check-gain-loss 30 2024-05-31";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  @Test
  public void testGainLoss90Day() {
    String input = "check-gain-loss 90 2024-05-31";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }

  @Test
  public void testGainLoss365Day() {
    String input = "check-gain-loss 365 2024-05-31";
    controller = new StocksControllerMock(model, view, input, true);
    controller.execute();

  }
}