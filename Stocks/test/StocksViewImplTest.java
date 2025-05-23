import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.model.Portfolio;
import stocks.model.PortfolioImpl;
import stocks.controller.StocksController;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;
import stocks.view.StocksView;
import stocks.view.StocksViewImpl;
import stocks.model.Transaction;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Test Class for the Stocks View Implementation.
 */
public class StocksViewImplTest {
  // Note for Future Reference: when testing the view, don't actually have to check that
  // the data is correct, just show that the view is printing out the correct output
  // according to the respective cases.

  private Appendable ap;
  private StocksModel model;
  private StocksView view;
  private StocksController controller;
  private Portfolio p;

  @Before
  public void setUp() {
    ap = new StringBuilder();
    model = new StocksModelMock(ap);
    view = new StocksViewImpl(ap);
    p = new PortfolioImpl("a", new HashMap<String, Double>(), new ArrayList<Transaction>());
  }

  @Test
  public void testWelcome() {
    String input = "";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String[] split = ap.toString().split("Thank");
    String s = split[0];
    String expectedOutput = "Welcome to the stocks application! \n"
            + "Supported user instructions are: \n"
            + "select-stock stock-symbol (select a stock to see functions) \n"
            + "create-portfolio portfolio-name (creates a new empty portfolio) \n"
            + "check-portfolio portfolio-name date (checks portfolio value at a specific date) \n"
            + "sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares) \n"
            + "menu (Print supported instruction list) \n"
            + "q or quit (quit the program) \n"
            + "[Please enter all dates in: YYYY-MM-DD format.] \n";
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testFarewell() {
    String input = "quit";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "Welcome to the stocks application! \n"
            + "Supported user instructions are: \n"
            + "select-stock stock-symbol (select a stock to see functions) \n"
            + "create-portfolio portfolio-name (creates a new empty portfolio) \n"
            + "check-portfolio portfolio-name date (checks portfolio value at a specific date) \n"
            + "sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares) \n"
            + "menu (Print supported instruction list) \n"
            + "q or quit (quit the program) \n"
            + "[Please enter all dates in: YYYY-MM-DD format.] \n"
            + "Thank you for using this program! \n";
    assertEquals(expectedOutput, ap.toString());
  }

  @Test
  public void testUndefinedInstruction() {
    String input = "@";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String[] split = ap.toString().split("format.] \n");
    String s = split[1];
    String expectedOutput = "Invalid input: Please try again. \n" + "Type instruction: ";
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testTypeInstruction() {
    String input = "@";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String[] split = ap.toString().split("Please try again. \n");
    String s = split[1];
    String expectedOutput = "Type instruction: ";
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testMenu() {
    String input = "quit";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String[] split = ap.toString().split("\n");
    String s = "";
    for (int i = 1; i < split.length; i++) {
      s = s.concat(split[i] + "\n");
    }
    String[] split2 = s.split("Thank");
    s = split2[0];

    String expectedOutput = "Supported user instructions are: \n"
            + "select-stock stock-symbol (select a stock to see functions) \n"
            + "create-portfolio portfolio-name (creates a new empty portfolio) \n"
            + "check-portfolio portfolio-name date (checks portfolio value at a specific date) \n"
            + "sell-stock stock-symbol number-of-shares portfolio-name (sell stock shares) \n"
            + "menu (Print supported instruction list) \n"
            + "q or quit (quit the program) \n"
            + "[Please enter all dates in: YYYY-MM-DD format.] \n";

    assertEquals(expectedOutput, s);
  }

  @Test
  public void testPrintStockMenuFollowingSelectStock() {
    String input = "select-stock";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String[] s1 = ap.toString().split("! \n");
    String[] split = s1[1].split("format.] \n");
    String s = split[1] + "format.] \n" + split[2];

    String expectedOutput = "Supported user instructions for selected stock are: \n"
            + "check-gain-loss number-of-days start-date (checks gains or losses for stock in "
            + "specific date range) \n"
            + "moving-average number-of-days date (checks x-day moving average for specified x day"
            + " count and date) \n"
            + "check-crossovers number-of-days start-date (checks x-day crossovers for"
            + " specified x day count in specific date range) \n"
            + "buy-stock number-of-shares portfolio-name (buy stock shares) \n"
            + "stock-menu (Print supported stocks instruction list) \n"
            + "menu (return to previous menu) \n"
            + "q or quit (quit the program) \n"
            + "[Please enter all dates in: YYYY-MM-DD format.] \n"
            + "Type instruction: ";
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testCreatePortfolio() {
    String input = "create-portfolio kiki";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "PortfolioImpl kiki" + " created. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testCheckPortfolio() {
    String input = "check-portfolio kiki 2024-05-31";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = String.format("%,.2f", 250.50) + " \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testPortfolioException() {
    String input = "check-portfolio retirement 2024-05-31";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "There may be an input error, or you may not have created "
            + "this portfolio yet. Please try again. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testReturnCrossOverResultYes() {
    String input = "check-crossovers 30 2020-03-09";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "No, this date is not a 30-day crossover. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testReturnCrossOverResultNo() {
    String input = "check-crossovers 30 2024-05-31";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "Yes, this date is a 30-day crossover. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testBuyMessage() {
    String input = "buy-stock 9 kiki";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "9 of GOOG bought to kiki. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testSellMessage() {
    String input = "sell-stock NVDA 9 college";
    controller = new StocksControllerMock(model, view, input, false, ap);
    controller.execute();
    String expectedOutput = "9 of NVDA sold from college. \n";
    String[] s1 = ap.toString().split("format.] \n");
    String s = s1[1];
    assertEquals(expectedOutput, s);
  }

  @Test
  public void testBarDayPortfolio() throws ParseException {
    p.addToPortfolio("GOOG", "2024-05-20", 5);
    p.addToPortfolio("NVDA", "2024-05-20", 25); //
    p.addToPortfolio("MSFT", "2024-05-20", 20); //8506.8
    List<Portfolio> portfolios = new ArrayList<>();
    portfolios.add(p);

    String expectedBarChart =
            "Performance of portfolio a from 2024-05-20 to 2024-05-24 \n" +
                    "2024-05-20: *********** \n" +
                    "2024-05-21: *********** \n" +
                    "2024-05-22: *********** \n" +
                    "2024-05-23: ************ \n" +
                    "2024-05-24: ************ \n" +
                    "Scale: * = 3000";

    Map<String, Double> testBarChart =
            new StocksModelImpl("", portfolios).bar("a", "2024-05-20",
                    "2024-05-24");
    assertEquals(expectedBarChart, testBarChart.toString());
  }
}
