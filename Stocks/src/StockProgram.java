import java.io.InputStreamReader;

public class StockProgram {
  public static void main(String[] args) {
    StockImpl model = new StockImpl();
    Readable rd = new InputStreamReader(System.in);
    StocksController controller = new StocksController(model, rd);
    controller.execute();
  }
}
