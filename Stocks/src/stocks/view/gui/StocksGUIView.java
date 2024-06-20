package stocks.view.gui;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stocks.controller.StocksGUIController;
import stocks.view.StocksView;
import stocks.view.gui.panels.DataPanel;

public class StocksGUIView extends JFrame implements StocksView {
  private JPanel mainPanel;
  private JPanel searchPanel, menuPanel, dataPanel;
  private ActionListener listener;
  private JLabel instructionsLabel, searchLabel;
  private JButton helpButton, loadButton;
  private JComboBox<String> selectionComboBox;
  private JRadioButton[] radioButtons;

  public StocksGUIView() {
    super();
    this.setBackground(Color.WHITE);
    this.setTitle("Welcome to Stocks Program!");
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    mainPanel.setPreferredSize(new Dimension(800, 600));
    this.setResizable(false);
    this.add(mainPanel);

    // panels
    searchPanel = this.makeSearchPanel();

    menuPanel = this.makeMenuPanel();

    dataPanel = new DataPanel(this, listener);

    mainPanel.add(searchPanel);
    mainPanel.add(menuPanel);
    mainPanel.add(dataPanel);

    this.pack();
  }

  private JPanel makeSearchPanel() {
    JPanel sp = new JPanel();
    sp.setPreferredSize(new Dimension(800, 100));
    sp.setBounds(0, 0, 800, 100);
    sp.setBackground(Color.WHITE);
    sp.setPreferredSize(new Dimension(800, 50));

    instructionsLabel = new JLabel("Welcome to the Stocks Program! To begin, please " +
            "select what you would like to search (stock/portfolio).", SwingConstants.CENTER);
    instructionsLabel.setPreferredSize(new Dimension(800, 50));
    sp.add(instructionsLabel);

    helpButton = new JButton("?");
    helpButton.setActionCommand("help-me");
    loadButton = new JButton("Load File");
    loadButton.addActionListener(listener);
    loadButton.setActionCommand("load");
    sp.add(helpButton);
    sp.add(loadButton);

    searchLabel = new JLabel("Search a: ");
    sp.add(searchLabel);
    String[] selectionOptions = {"", "stock", "portfolio"};
    //the event listener when an option is selected
    selectionComboBox = this.createComboBox(selectionOptions);
    selectionComboBox.setActionCommand("Stock-Portfolio Selected");
    searchLabel.add(selectionComboBox);
    sp.add(selectionComboBox);
    return sp;
  }

  private JPanel makeMenuPanel() {
    JPanel mp = new JPanel();
    mp.setPreferredSize(new Dimension(400, 500));
    mp.setBounds(0,100,400, 600);
    mp.setBackground(Color.WHITE);
    mp.setPreferredSize(new Dimension(400, 500));
    mp.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
    JPanel stockActionsPanel = new JPanel();
    stockActionsPanel.setBackground(Color.WHITE);
    stockActionsPanel.setBorder(BorderFactory.createTitledBorder("Stock Actions:"));
    stockActionsPanel.setBounds(0, 100, 400, 400);
    stockActionsPanel.setLayout(new BoxLayout(stockActionsPanel, BoxLayout.PAGE_AXIS));
    radioButtons = new JRadioButton[4];
    ButtonGroup rGroup1 = new ButtonGroup();
    List<String> stockMethodOptions = new ArrayList<>(Arrays.asList(
            "portfolio value", "portfolio composition",
            "buy stock", "sell stock"));
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(stockMethodOptions.get(i));
      radioButtons[i].setSelected(false);
      radioButtons[i].setActionCommand("Stock Method Selected" + radioButtons[i].getText());
      radioButtons[i].addActionListener(listener);
      rGroup1.add(radioButtons[i]);
      stockActionsPanel.add(radioButtons[i]);
    }
    mp.add(stockActionsPanel);
    JPanel search = new JPanel();
    search.setBackground(Color.WHITE);
    JLabel searchL = new JLabel("Search a Stock:");
    JTextField stockS = new JTextField(15);
    JButton searchb = new JButton("search");
    searchb.addActionListener(listener);
    searchb.setActionCommand("search");
    search.setPreferredSize(new Dimension(80, 20));
    search.add(searchL);
    search.add(stockS);
    search.add(searchb);
    mp.add(search);
    return mp;
  }

  public void setListeners(ActionListener listen) {
    helpButton.addActionListener(listen);
    for (JRadioButton radioButton : radioButtons) {
      radioButton.addActionListener(listen);
    }
  }

  public JPanel makeDataPanel() {
    JPanel dp = new JPanel();

    dp.setPreferredSize(new Dimension(400, 500));
    dp.setBounds(400, 100, 800, 600);

    return dp;
  }

  public JComboBox<String> createComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<String>();
    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    return comboBox;
  }


  public void setListener(StocksGUIController listener) {
    this.listener = listener;
  }

  @Override
  public void welcomeMessage() {

  }

  @Override
  public void typeInstruct() {
  }

  @Override
  public void undefined() {

  }

  @Override
  public void farewellMessage() {

  }

  @Override
  public void printMenu() {

  }

  @Override
  public void printStockMenu() {

  }

  @Override
  public void returnResult(String input) {

  }

  @Override
  public void portfolioException(boolean buy) {

  }

  @Override
  public void formattedReturn(Double inp) {

  }

  @Override
  public void portfolioCreationMessage(String name) {

  }

  @Override
  public void buySellMessage(Integer quantity, String stock, String name, boolean sell) {

  }

  @Override
  public void askBalance(String stock) {

  }

  @Override
  public void balanceInstruction() {

  }

  @Override
  public void listWrite(Map<String, Double> input, String type) {

  }

  @Override
  public void barWrite(String name, String date1, String date2, HashMap<String, Double> input, Integer scale, ArrayList<String> order) {

  }

  @Override
  public void invalidDate(String type) {

  }

  @Override
  public void askDate(String type) {

  }

  @Override
  public void whichPortfolio() {

  }

  @Override
  public void rebalanced(String portfolioName) {

  }
}
