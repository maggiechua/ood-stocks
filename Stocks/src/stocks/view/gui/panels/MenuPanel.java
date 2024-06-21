package stocks.view.gui.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import stocks.view.gui.StocksGUIView;

public class MenuPanel extends JPanel {
  JPanel searchPanel, stockActionsPanel;
  JTextField stockSearch;
  JButton search;
  JLabel searchLabel;
  StocksGUIView frame;

  public MenuPanel(StocksGUIView view) {
    super();
    this.frame = view;
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(400, 500));
    this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

    //
    stockActionsPanel = new JPanel();
    stockActionsPanel.setBackground(Color.WHITE);
    stockActionsPanel.setBorder(BorderFactory.createTitledBorder("Stock Actions:"));
    stockActionsPanel.setBounds(0, 100, 400, 400);
    stockActionsPanel.setLayout(new BoxLayout(stockActionsPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[4];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();

    List<String> stockMethodOptions = new ArrayList<>(Arrays.asList(
            "portfolio value", "portfolio composition",
            "buy stock", "sell stock"));
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(stockMethodOptions.get(i));
      radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand("Stock Method Selected" + radioButtons[i].getText());
//      radioButtons[i].addActionListener(this);
      rGroup1.add(radioButtons[i]);
      stockActionsPanel.add(radioButtons[i]);
    }
    this.add(stockActionsPanel);

    // search a stock
    searchPanel = new JPanel();
    searchPanel.setBackground(Color.WHITE);
    searchLabel = new JLabel("Search a Stock:");
    stockSearch = new JTextField(15);
    search = new JButton("search");
//    search.addActionListener(this);
    search.setActionCommand("Search");
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

//  @Override
//  public void actionPerformed(ActionEvent e) {
//    frame.actionPerformed(e);
//  }
}
