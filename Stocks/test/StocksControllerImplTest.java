import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import stocks.StocksControllerImpl;
import stocks.StocksModel;
import stocks.StocksModelImpl;
import stocks.StocksView;
import stocks.StocksViewImpl;

import static org.junit.Assert.assertEquals;

public class StocksControllerImplTest {

  @Test
  public void testWelcome() {
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewMock();
    String input = "quit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    Readable rd = new InputStreamReader(in);
    String expectedOutput = "Welcome to the stocks application!" + System.lineSeparator()
            + "Supported user instructions are: " + System.lineSeparator()
            + "select-stock stock-symbol (select a stock to see functions)"
            + System.lineSeparator()
            + "create-portfolio portfolio-name (creates a new empty portfolio)"
            + System.lineSeparator()
            + "check-portfolio portfolio-name date (checks portfolio value at a specific date)"
            + System.lineSeparator()
            + "sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares)"
            + System.lineSeparator()
            + "menu (Print supported instruction list)" + System.lineSeparator()
            + "q or quit (quit the program) " + System.lineSeparator()
            + "[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator();
    StocksControllerImpl controller = new StocksControllerImpl(model, rd, view);
    controller.execute();
  }
}