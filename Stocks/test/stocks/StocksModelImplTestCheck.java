package stocks;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class StocksModelImplTestCheck {
  private StocksModel model;


  @Before
  public void setUp() throws Exception {
    model = new StocksModelImpl("GOOG", new HashMap<String, HashMap<String, Double>>());
  }

  @Test
  public void barTest() throws ParseException {
    model.createPortfolio("a");
    model.buy(5, "2015-09-09", "a");
    model.bar("a", "2010-09-09", "2019-09-09");
  }

}