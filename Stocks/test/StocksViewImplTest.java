import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import stocks.StocksController;
import stocks.StocksControllerImpl;
import stocks.StocksModel;
import stocks.StocksView;
import stocks.StocksViewImpl;

import static org.junit.Assert.assertEquals;

public class StocksViewImplTest {
  @Test
  public void testWelcome() {
    Appendable ap = new StringBuilder();
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewImpl(ap);
    String input = "";
    StocksController controller = new StocksControllerMock(model, view, input);
    controller.execute();
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
    assertEquals(expectedOutput, ap.toString());
  }

  @Test
  public void testFarewell() {
    Appendable ap = new StringBuilder();
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewImpl(ap);
    String input = "quit";
    StocksController controller = new StocksControllerMock(model, view, input);
    controller.execute();
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
            + "[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator()
            + "Thank you for using this program!";
    assertEquals(expectedOutput, ap.toString());
  }

  // only issue is something about line separators, but otherwise same
  @Test
  public void testMenu() {
    Appendable ap = new StringBuilder();
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewImpl(ap);
    String input = "quit";
    StocksController controller = new StocksControllerMock(model, view, input);
    controller.execute();
    String[] split = ap.toString().split("\n");
    String s = "";
    for (int i = 1; i < split.length; i++) {
      s = s.concat(split[i]);
    }
    String[] split2 = s.split("Thank");
    s = split2[0];

    String expectedOutput = "Supported user instructions are: " + System.lineSeparator()
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

    assertEquals(expectedOutput, s);
  }

  // FOLLOWING SELECT-STOCK OPTIONS
  @Test
  public void testSelectStock() {
    Appendable ap = new StringBuilder();
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewImpl(ap);
    String input = "select-stock";
    StocksController controller = new StocksControllerMock(model, view, input);
    controller.execute();

    String[] split = ap.toString().split("format.]");
    String s = split[2];

    String expectedOutput = "Supported user instructions for selected stock are: "
            + System.lineSeparator()
            + "check-gain-loss number-of-days start-date (checks gains or losses for stock in " +
            "specific date range)"
            + System.lineSeparator()
            + "moving-average number-of-days date (checks x-day moving average for specified x day"
            + " count and date)"
            + System.lineSeparator()
            + "check-crossovers number-of-days start-date (checks x-day crossovers for" +
            " specified x day count in specific date range)"
            + System.lineSeparator()
            + "buy-stock number-of-shares portfolio-name (buy stock shares)"
            + System.lineSeparator()
            + "stock-menu (Print supported stocks instruction list)"
            + System.lineSeparator()
            + "return-to-menu (return to previous menu)" + System.lineSeparator()
            + "q or quit (quit the program) " + System.lineSeparator()
            + "[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator()
            + "Type instruction: ";
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testPrintStockMenu() {
    Appendable ap = new StringBuilder();
    StocksModel model = new StocksModelMock();
    StocksView view = new StocksViewImpl(ap);
    String input = "stock-menu";
    StocksController controller = new StocksControllerMock(model, view, input);
    controller.execute();
    String[] split = ap.toString().split("\n");
    String s = "";
    for (int i = 1; i < split.length; i++) {
      s = s.concat(split[i]);
    }
    String[] split2 = s.split("Thank");
    s = split2[0];

    String expectedOutput = "Supported user instructions for selected stock are: "
            + System.lineSeparator()
            + "check-gain-loss number-of-days start-date (checks gains or losses for stock in " +
            "specific date range)"
            + System.lineSeparator()
            + "moving-average number-of-days date (checks x-day moving average for specified x day"
            + " count and date)"
            + System.lineSeparator()
            + "check-crossovers number-of-days start-date (checks x-day crossovers for" +
            " specified x day count in specific date range)"
            + System.lineSeparator()
            + "buy-stock number-of-shares portfolio-name (buy stock shares)"
            + System.lineSeparator()
            + "stock-menu (Print supported stocks instruction list)"
            + System.lineSeparator()
            + "return-to-menu (return to previous menu)" + System.lineSeparator()
            + "q or quit (quit the program) " + System.lineSeparator()
            + "[Please enter all dates in: YYYY-MM-DD format.]" + System.lineSeparator();
    assertEquals(expectedOutput, s);
  }

  //
  @Test
  public void testCreatePortfolio() {

  }
}
