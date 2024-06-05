import org.junit.Test;

import stocks.StocksModelImpl;

import static org.junit.Assert.assertEquals;

public class StocksModelImplTest {
  @Test
  public void test() {
    StocksModelImpl s = new StocksModelImpl();
    double output = s.gainLoss(5, "2024-05-31");
    assertEquals(31.64, output, 0.01);
  }
}