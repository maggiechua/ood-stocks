package stocks;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stocks.controller.StocksController;
import stocks.controller.StocksControllerImpl;
import stocks.controller.StocksGUIController;
import stocks.model.ReadOnlyModel;
import stocks.model.ViewModel;
import stocks.view.StocksGUIView;
import stocks.model.Portfolio;
import stocks.model.StocksModel;
import stocks.model.StocksModelImpl;

/**
 * This class represents the Stock Program.
 */
public class StockProgram {

  /**
   * This is the main method to run the program.
   */
  public static void main(String[] args) {
    // ask user to decide if they want text vs. gui and then use that to initialize the view
    StocksGUIView.setDefaultLookAndFeelDecorated(false);

    String init = "";
    List<Portfolio> p = new ArrayList<>();
    StocksModel model = new StocksModelImpl(init, p);
    Readable rd = new InputStreamReader(System.in);
    ReadOnlyModel rm = new ViewModel(model);
    StocksGUIView gui = new StocksGUIView(rm);
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setVisible(true);
    StocksController controller = new StocksGUIController(model, rd, gui);
    controller.execute();
  }
}
