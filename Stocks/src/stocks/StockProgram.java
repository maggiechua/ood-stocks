package stocks;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stocks.controller.StocksController;
import stocks.controller.StocksControllerImpl;
import stocks.controller.StocksGUIController;
import stocks.view.gui.StocksGUIView;
import stocks.model.Portfolio;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;
import stocks.view.StocksView;

/**
 * This class represents the Stock Program.
 */
public class StockProgram {

  /**
   * This is the main method to run the program.
   */
  public static void main(String[] args) {
    StocksGUIView.setDefaultLookAndFeelDecorated(false);
    StocksGUIView gui = new StocksGUIView();

    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setVisible(true);

    String init = "";
    List<Portfolio> p = new ArrayList<>();
    StocksModel model = new StocksModelImpl(init, p);
    Readable rd = new InputStreamReader(System.in);
    StocksController controller = new StocksGUIController(model, rd, gui);
    controller.execute();
  }
}
