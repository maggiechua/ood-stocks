package stocks;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Stock Program.
 */
public class StockProgram {

  /**
   * This is the main method to run the program.
   */
  public static void main(String[] args) {
    String init = "";
    List<PortfolioImpl> p = new ArrayList<>();
    StocksModel model = new StocksModelImpl(init, p);
    Readable rd = new InputStreamReader(System.in);
    StocksView ap = new StocksViewImpl(System.out);
    StocksController controller = new StocksControllerImpl(model, rd, ap);
    controller.execute();
  }
}
