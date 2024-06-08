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
    model = new StocksModelMock(ap);
  }

  // CONTROLLER SENDING INPUTS TO MODEL


  // CONTROLLER SENDING INPUTS TO VIEW
  @Test
  public void testViewDisplaysWelcomeMessage() {
    String input = "q";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
    String expectedOutput = "Welcome Message printed in view.";
    String[] split = expectedOutput.split("\n");
    assertEquals(expectedOutput, split[0]);
  }

  @Test
  public void test1() {
    String input = "";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    rd = new InputStreamReader(in);
    controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
  }
}