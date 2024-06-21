package stocks;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stocks.controller.StocksController;
import stocks.controller.StocksControllerImpl;
import stocks.controller.StocksGUIController;
import stocks.view.StocksGUIView;
import stocks.view.StocksView;
import stocks.view.StocksViewImpl;
import stocks.view.gui.StocksGUIView;
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
    StocksView view = null;
    StocksController controller;
    String init = "";
    List<Portfolio> p = new ArrayList<>();
    StocksModel model = new StocksModelImpl(init, p);
    Readable rd = new InputStreamReader(System.in);

    if (args.length == 0) {
      StocksGUIView.setDefaultLookAndFeelDecorated(false);
      StocksGUIView gui = new StocksGUIView();

      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setVisible(true);
      view = gui;
      controller = new StocksGUIControlerr(model, rd, view);
    }
    else if (args[0].equals("-text")) {
      view = new StocksViewImpl(System.out);
      controller = new StocksControllerImpl(model, rd, view);
    }
    else {
      System.out.println("Inputted text is not an option for stock program. Please input no command" 
              + "line arguments for a graphical user interface, or enter '-text' for a text based"
              + " interface.");
    }
    controller.execute();
  }
}
