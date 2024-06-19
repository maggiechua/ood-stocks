package stocks.view.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class MenuPanel extends JPanel implements PanelItems {
  JPanel searchPanel, methodsPanel;
  JTextField stockSearch, enterVal, enterYear, enterMonth, enterDay;
  JList<String> yearOptions, monthOptions, dayOptions;
  JButton search;
  JLabel searchLabel, valueLabel, yearLabel, monthLabel, dayLabel;

  public MenuPanel() {
    super();
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(500, 400));
    this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // stock methods
    methodsPanel = new JPanel();
    methodsPanel.setBackground(Color.WHITE);
    methodsPanel.setLayout(new BoxLayout(methodsPanel, BoxLayout.PAGE_AXIS));
    methodsPanel.setBorder(BorderFactory.createTitledBorder("Select a stock method from the " +
            "following list:"));
    this.add(methodsPanel);

    String[] methodOptions = {"", "check gain-loss", "moving average", "crossover",
            "buy stock", "sell stock"};
    //the event listener when an option is selected
    JComboBox<String> methodsCombobox = this.createComboBox(methodOptions);
    methodsCombobox.setActionCommand("Size options");
//    combobox.addActionListener(this);
    methodsPanel.add(methodsCombobox);

    // search a stock
    searchPanel = new JPanel();
    searchPanel.setBackground(Color.WHITE);
    searchLabel = new JLabel("Search a Stock:");
    stockSearch = new JTextField(15);
    search = new JButton();
    search.setText("search");
    search.setPreferredSize(new Dimension(80, 20));
    searchPanel.add(searchLabel);
    searchPanel.add(stockSearch);
    searchPanel.add(search);
    this.add(searchPanel);
  }

  public JComboBox<String> createComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<String>();
    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    return comboBox;
  }

  @Override
  public void addCommandListener(ActionListener actionEvent) {

  }

  @Override
  public String getCommand() {
    return "";
  }
}
