package stocks;

import java.io.InputStreamReader;

public class StockProgram {
  public static void main(String[] args) {
    StocksModel model = new StocksModelImpl();
    Readable rd = new InputStreamReader(System.in);
    StocksView ap = new StocksViewImpl(System.out);
    StocksController controller = new StocksControllerImpl(model, rd, ap);
    controller.execute();
  }
}
