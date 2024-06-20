package stocks.view.gui;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import stocks.controller.StocksControllerImpl;
import stocks.view.StocksView;
import stocks.view.gui.panels.DataPanel;
import stocks.view.gui.panels.MenuPanel;
import stocks.view.gui.panels.SearchPanel;

public class StocksGUIView extends JFrame implements StocksView {
  private JPanel mainPanel;
  private JPanel searchPanel, menuPanel, dataPanel, stockActionsPanel, searchStockPanel;
  private List<ActionEvent> listeners = new ArrayList<>();
  private JLabel instructionsLabel, searchLabel;
  private JTextField enterStock, stockSearch;
  private JButton helpButton, loadButton, searchButton;
  JComboBox<String> selectionComboBox;
  private JList<String> portfolios;
  private JPanel valPanel, yearPanel, monthPanel, dayPanel;
  private JTextField enterValue;
  private JLabel enterValLabel, enterYearLabel, enterMonthLabel, enterDayLabel;
  JComboBox<String> yearsCombobox, monthsCombobox, daysCombobox;

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

    // panels
    mainPanel.add(this.createSearchPanel());
    mainPanel.add(this.createMenuPanel());
    mainPanel.add(this.createDataPanel());

    this.add(mainPanel);
    this.pack();
  }

  @Override
  public void setUpListeners(ActionListener listener) {
    // search panel components
    helpButton.addActionListener(listener);
    loadButton.addActionListener(listener);
    loadButton.setActionCommand("load");

    // menu panel components


    // data panel components
    yearsCombobox.addActionListener(listener);
    monthsCombobox.addActionListener(listener);
    daysCombobox.addActionListener(listener);
    searchButton.addActionListener(listener);
  }

  public ArrayList<ActionEvent> getListeners() {
    return (ArrayList<ActionEvent>) listeners;
  }

  public JPanel createSearchPanel() {
    searchPanel = new JPanel();
    searchPanel.setPreferredSize(new Dimension(800, 100));
    searchPanel.setBounds(0, 0, 800, 100);
    searchPanel.setBackground(Color.WHITE);

    instructionsLabel = new JLabel("Welcome to the Stocks Program! To begin, please " +
            "select what you would like to search (stock/portfolio).", SwingConstants.CENTER);
    instructionsLabel.setPreferredSize(new Dimension(800, 50));
    searchPanel.add(instructionsLabel);

    helpButton = new JButton("?");
    helpButton.setActionCommand("help-me");
    loadButton = new JButton("Load File");
    searchPanel.add(helpButton);
    searchPanel.add(loadButton);

    searchLabel = new JLabel("Search a: ");
    searchPanel.add(searchLabel);
    String[] selectionOptions = {"", "stock", "portfolio"};
    selectionComboBox = this.createComboBox(selectionOptions);
    selectionComboBox.setActionCommand("Stock-Portfolio Selected");
    searchLabel.add(selectionComboBox);
    searchPanel.add(selectionComboBox);
    return searchPanel;
  }

  public JPanel createMenuPanel() {
    menuPanel = new JPanel();
    menuPanel.setPreferredSize(new Dimension(400, 500));
    menuPanel.setBounds(0,100,400, 600);
    menuPanel.setBackground(Color.WHITE);

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
      radioButtons[i].setBackground(Color.WHITE);
      radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand("Stock Method Selected" + radioButtons[i].getText());
//      radioButtons[i].addActionListener(this);
      rGroup1.add(radioButtons[i]);
      stockActionsPanel.add(radioButtons[i]);
    }
    menuPanel.add(stockActionsPanel);

    // search a stock
    searchStockPanel = new JPanel();
    searchStockPanel.setBackground(Color.WHITE);
    searchLabel = new JLabel("Search a Stock:");
    stockSearch = new JTextField(15);
    searchButton = new JButton("search");
    searchButton.setActionCommand("Search");
    searchButton.setPreferredSize(new Dimension(80, 20));
    searchStockPanel.add(searchLabel);
    searchStockPanel.add(stockSearch);
    searchStockPanel.add(searchButton);
    menuPanel.add(searchStockPanel);
    return menuPanel;
  }

  public JPanel createDataPanel() {
    dataPanel = new JPanel();
    dataPanel.setPreferredSize(new Dimension(400, 500));
    dataPanel.setBounds(400, 100, 800, 600);
    dataPanel.setBackground(Color.WHITE);
    dataPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    valPanel = new JPanel();
    valPanel.setBackground(Color.WHITE);
    dataPanel.add(valPanel);
    valPanel.setBounds(400, 100, 800, 400);
    enterValLabel = new JLabel("Enter value:");
    enterValue = new JTextField(10);
    enterValLabel.add(enterValue);
    valPanel.add(enterValLabel);
    valPanel.add(enterValue);

    // entering date fields
    yearPanel = new JPanel();
    yearPanel.setBackground(Color.WHITE);
    yearPanel.setBorder(BorderFactory.createEmptyBorder(-10, 50, -10, 50));
    dataPanel.add(yearPanel);

    monthPanel = new JPanel();
    monthPanel.setBackground(Color.WHITE);
    monthPanel.setBorder(BorderFactory.createEmptyBorder(-10, 50, -10, 50));
    dataPanel.add(monthPanel);

    dayPanel = new JPanel();
    dayPanel.setBackground(Color.WHITE);
    dayPanel.setBorder(BorderFactory.createEmptyBorder(-10, 50, -10, 50));
    dataPanel.add(dayPanel);

    enterYearLabel = new JLabel("Enter year:");
    yearPanel.add(enterYearLabel);
    List<String> years = new ArrayList<String>();
    for (int y = 2000; y < 2025; y++) {
      years.add((String.valueOf(y)));
    }
    String[] yearOptions = new String[years.size()];
    yearOptions = years.toArray(yearOptions);
    yearsCombobox = this.createComboBox(yearOptions);
    yearsCombobox.setActionCommand("year-select");
    enterYearLabel.add(yearsCombobox);
    yearPanel.add(yearsCombobox);

    enterMonthLabel = new JLabel("Enter month:");
    monthPanel.add(enterMonthLabel);
    List<String> months = new ArrayList<String>();
    for (int y = 1; y < 13; y++) {
      months.add((String.valueOf(y)));
    }
    String[] monthOptions = new String[months.size()];
    monthOptions = months.toArray(monthOptions);
    monthsCombobox = this.createComboBox(monthOptions);
    monthsCombobox.setActionCommand("month-select");
    enterMonthLabel.add(monthsCombobox);
    monthPanel.add(monthsCombobox);

    enterDayLabel = new JLabel("Enter day:");
    dayPanel.add(enterDayLabel);
    List<String> days = new ArrayList<String>();
    for (int y = 1; y < 32; y++) {
      days.add((String.valueOf(y)));
    }
    String[] dayOptions = new String[days.size()];
    dayOptions = days.toArray(dayOptions);
    daysCombobox = this.createComboBox(dayOptions);
    daysCombobox.setActionCommand("day-select");
    enterDayLabel.add(daysCombobox);
    dayPanel.add(daysCombobox);
    return dataPanel;
  }

  /**
   *
   * @param options
   * @return
   */
  public JComboBox<String> createComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<String>();
    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    return comboBox;
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
  public void barWrite(String name, String date1, String date2, HashMap<String, Double> input, Integer scale, List<String> order) {

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

//  @Override
//  public void actionPerformed(ActionEvent e) {
//    listeners.add(e);
//  }
}
