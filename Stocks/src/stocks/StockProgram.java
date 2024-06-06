package stocks;

import java.io.InputStreamReader;
import java.util.HashMap;

public class StockProgram {
  public static void main(String[] args) {
    String init = "";
    HashMap<String, HashMap<String, Integer>> p = new HashMap<String,HashMap<String, Integer>>();
    StocksModel model = new StocksModelImpl(init, p);
    Readable rd = new InputStreamReader(System.in);
    StocksView ap = new StocksViewImpl(System.out);
    StocksController controller = new StocksControllerImpl(model, rd, ap);
    controller.execute();
  }
}
