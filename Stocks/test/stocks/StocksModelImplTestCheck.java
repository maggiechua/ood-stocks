package stocks;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Test Class for ME.
 */
public class StocksModelImplTestCheck {
  private StocksModel model;


  @Before
  public void setUp() throws Exception {
    model = new StocksModelImpl("GOOG", new ArrayList<PortfolioImpl>());
  }

  @Test
  public void barTest() throws ParseException {
    model.createPortfolio("a");
    model.buy(5, "2015-9-9", "a");
    model.buy(10, "2018-09-09", "a");
    model.bar("a", "2014-09-09", "2022-09-09");
  }
}
