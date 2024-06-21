package stocks;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import stocks.model.FileParser;
import stocks.model.Portfolio;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit Test Class for ME.
 */
public class StocksModelImplTestCheck {
  private StocksModel model;
  private FileParser fp;


  @Before
  public void setUp() throws Exception {
    List<Portfolio> pfs = new ArrayList<>();
    model = new StocksModelImpl("GOOG", pfs);
    fp = new FileParser();
  }

  @Test
  public void barTest() throws ParseException {
    model.createPortfolio("a");
    model.buy(5, "2015-09-09", "a");
    model.buy(10, "2018-09-09", "a");
    model.bar("a", "2014-09-09", "2022-09-09");
  }

//  @Test
//  public void lastDay() {
//    String date = fp.getLastWorkingDay("AAPL", 11, 2022);
//    assertEquals("2022-11-30", date);
//    date = fp.getLastWorkingDay("MSFT", 8, 2017);
//    assertEquals("2017-08-31", date);
//    date = fp.getLastWorkingDay("AAPL", 0, 2022);
//    assertEquals("2022-12-30", date);
//  }

  @Test
  public void PLEASE() {


  }

}