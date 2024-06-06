import org.junit.Test;

import java.util.HashMap;

import stocks.StocksModel;
import stocks.StocksModelImpl;

import static org.junit.Assert.assertEquals;

public class StocksModelImplTest {

  // mock controller
  // mock view
  // check whether i'm getting the correct value and it's being displayed properly

  @Test
  public void test() {
    String init = "";
    HashMap<String, HashMap<String, Integer>> p = new HashMap<String,HashMap<String, Integer>>();
    StocksModel s = new StocksModelImpl(init, p);
    double output = s.gainLoss(5, "2024-05-31");
    assertEquals(31.64, output, 0.01);
  }
}