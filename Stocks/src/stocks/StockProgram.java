package stocks;

import java.io.InputStreamReader;


public class StockProgram {
  public static void main(String[] args) {
    Stock model = new StockImpl();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    StocksController controller = new StocksController(model, rd, ap);
    controller.execute();
  }
}
