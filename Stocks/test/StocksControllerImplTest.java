import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import stocks.StocksController;
import stocks.StocksControllerImpl;
import stocks.StocksModel;
import stocks.StocksView;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Test Class for the Stocks Controller Implementation.
 */
public class StocksControllerImplTest {
  // initialize a controller and pass it the mock model and mock view
  // check that the input that it is passing the same thing that the model/view is expecting
  // to the controller and calls the appropriate method
  // create an appendable object/log that is passed to the mock model when it is created
  // and whenever the controller is called on this mock, this appendable will have a record
  // of the data being passed
  private Appendable ap;
  private StocksView view;
  private StocksModel model;
  private StocksController controller;
  private Readable rd;

  @Before
  public void setUp() {
    ap = new StringBuilder();
    view = new StocksViewMock(ap);
    model = new StocksModelMock();
  }

  // CONTROLLER SENDING INPUTS TO MODEL
  @Test
  public void testViewDisplaysWelcomeMessage() {
    String input = "q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Welcome Message printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[0]);
  }

  @Test
  public void testViewDisplaysTypeInstruction() {
    String input = "q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Type Instruction Message printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[1]);
  }

  @Test
  public void testViewDisplaysInitialMenu() {
    String input = "menu q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Initial Menu printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[2]);
  }

  @Test
  public void testViewDisplaysUndefined() {
    String input = "blank q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Undefined Message printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[2]);
  }

  @Test
  public void testViewFarewellMessage() {
    String input = "q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Farewell Message printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[2]);
  }

  @Test
  public void testViewStockMenu() {
    String input = "select-stock AAPL q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Stock Menu printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[2]);
  }

  @Test
  public void testViewResult() {
    String input = "select-stock MSFT check-crossovers 30 2019-09-09 q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Result printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[4]);
  }

  @Test
  public void testViewFormattedResult() {
    String input = "select-stock MSFT check-gain-loss 30 2019-09-09 q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Formatted result printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[4]);
  }

  @Test
  public void testViewPortfolioException() {
    String input = "sell-stock MSFT 2 a q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "PortfolioException printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[4]);
  }

  @Test
  public void testViewPortfolioCreation() {
    String input = "create-portfolio a q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Portfolio created printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[2]);
  }

  @Test
  public void testViewBuySell() {
    String input = "create-portfolio a select-stock MSFT buy-stock 2 a q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Buying or selling message printed in view. ";
    String resultString = ap.toString();
    String[] split = resultString.split("\n");
    assertEquals(expectedOutput, split[6]);
  }
}