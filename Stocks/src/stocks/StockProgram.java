package stocks;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.View;

import stocks.controller.StocksController;
import stocks.controller.StocksControllerImpl;
import stocks.controller.StocksGUIController;
import stocks.model.ReadOnlyModel;
import stocks.model.ViewModel;
import stocks.view.StocksGUIView;
import stocks.view.StocksView;
import stocks.view.StocksViewImpl;
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
    model = model.loadPortfolios();
    ReadOnlyModel rm = new ViewModel(model);
    Readable rd = new InputStreamReader(System.in);

    StocksGUIView.setDefaultLookAndFeelDecorated(false);
    StocksGUIView gui = new StocksGUIView(rm);

    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setVisible(true);
    view = gui;
    controller = new StocksGUIController(model, rd, view);


    if (args.length == 0) {
      StocksGUIView.setDefaultLookAndFeelDecorated(false);
      gui = new StocksGUIView(model);

      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setVisible(true);
      view = gui;
      controller = new StocksGUIController(model, rd, view);
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
