package stocks.view.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import stocks.view.StocksView;
import stocks.view.gui.StocksGUIView;

public class SearchPanel extends JPanel implements PanelItems, ActionListener {
  private JLabel instructionsLabel, searchLabel;
  private JTextField enterStock;
  private JButton helpButton, loadButton;
  JComboBox<String> selectionComboBox;
  private JList<String> portfolios;
  StocksGUIView frame;

  public SearchPanel(StocksGUIView view) {
    super();
    this.frame = view;
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(800, 50));

    instructionsLabel = new JLabel("Welcome to the Stocks Program! To begin, please " +
            "select what you would like to search (stock/portfolio).", SwingConstants.CENTER);
    instructionsLabel.setPreferredSize(new Dimension(800, 50));
    this.add(instructionsLabel);

    helpButton = new JButton("?");
    helpButton.addActionListener(this);
    helpButton.setActionCommand("help-me");
    loadButton = new JButton("Load File");
    loadButton.addActionListener(this);
    loadButton.setActionCommand("load");
    this.add(helpButton);
    this.add(loadButton);

    searchLabel = new JLabel("Search a: ");
    this.add(searchLabel);
    String[] selectionOptions = {"", "stock", "portfolio"};
    //the event listener when an option is selected
    selectionComboBox = this.createComboBox(selectionOptions);
    selectionComboBox.setActionCommand("Stock-Portfolio Selected");
    searchLabel.add(selectionComboBox);
    this.add(selectionComboBox);
  }

  @Override
  public JComboBox<String> createComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<String>();
    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    return comboBox;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    frame.actionPerformed(e);
  }
}
