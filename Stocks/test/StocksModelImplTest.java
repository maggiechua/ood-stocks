import org.junit.Test;

import stocks.StocksModelImpl;

public class StocksModelImplTest {
  @Test
  public void test() {
    StocksModelImpl s = new StocksModelImpl();
    s.gainLoss(5, "2004-08-04");
  }
}