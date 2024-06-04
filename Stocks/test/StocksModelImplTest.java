import org.junit.Test;

import stocks.StocksModelImpl;

public class StocksModelImplTest {
  @Test
  public void test() {
    StocksModelImpl s = new StocksModelImpl();
    s.getStockInfo("NVDA", 5, "", 1);
  }
}