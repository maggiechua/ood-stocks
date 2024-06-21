package stocks.view;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import stocks.controller.StocksControllerImpl;
import stocks.view.StocksView;
import swingdemo.SwingFeaturesFrame;

public class StocksGUIView extends JFrame implements StocksView {
  private JPanel mainPanel, searchPanel, menuPanel, dataPanel, stockActionsPanel, searchStockPanel,
          valPanel, yearPanel, monthPanel, dayPanel;
  private JLabel instructionsLabel, searchLabel, searchALabel;
  private JTextField stockSearch, enterValue;
  private JButton helpButton, loadButton, searchButton, createPortfolioButton;
  private JLabel enterValLabel, enterYearLabel, enterMonthLabel, enterDayLabel, result;
  private JComboBox<String> selectionComboBox, yearsCombobox, monthsCombobox, daysCombobox;
  private JRadioButton[] radioButtons;
  private ButtonGroup radioButtonGroup;
  private boolean stock;
  private String resultString;

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
    searchPanel = this.createSearchPanel();
    menuPanel = this.createMenuPanel();
    dataPanel = this.createDataPanel();

    mainPanel.add(searchPanel);
    mainPanel.add(menuPanel);
    mainPanel.add(dataPanel);
    this.pack();
  }

  @Override
  public void loadFileWindow() {
    JFrame fileWindow = new JFrame("Load File");
    fileWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel fileOpenPanel = new JPanel(new BorderLayout());
    fileOpenPanel.setPreferredSize(new Dimension(200, 200));
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Portfolio files", "xml");
    fchooser.setFileFilter(filter);
    JLabel fileOpenDisplay = new JLabel("File path will appear here");
    int retvalue = fchooser.showOpenDialog(fileWindow);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      fileOpenDisplay.setText(f.getAbsolutePath());
    }
  }

  @Override
  public void createHelpWindow() {
    JFrame helpWindow = new JFrame("Help");
    helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel helpPanel = new JPanel(new BorderLayout());
    helpPanel.setPreferredSize(new Dimension(250, 200));
    helpWindow.add(helpPanel);
    JLabel helpLabel = new JLabel(
            "Welcome to the Stocks Program! This program allows you to create portfolios, " +
                    " buy/sell stocks, and examine the value/composition of your portfolio" +
                    " on a specified date. Additionally, you can load in files containing " +
                    " portfolio transactions as long as it is in XML format. "
    );
    helpPanel.add(helpLabel);
    helpWindow.pack();;
    helpWindow.setVisible(true);
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
    createPortfolioButton = new JButton("Create Portfolio");
    createPortfolioButton.setActionCommand("create-portfolio");
    searchPanel.add(helpButton);
    searchPanel.add(loadButton);
    searchPanel.add(createPortfolioButton);
    searchLabel = new JLabel("Search a: ");
    searchPanel.add(searchLabel);
    String[] selectionOptions = {"stock", "portfolio"};
    selectionComboBox = this.createComboBox(selectionOptions);
    selectionComboBox.setActionCommand("Stock-Portfolio Selected");
    searchLabel.add(selectionComboBox);
    searchPanel.add(selectionComboBox);
    return searchPanel;
  }

  /**
   * The following method creates the menu panel on the Stocks Program GUI containing
   * options for the user to choose what stock actions (buy/sell) they want to perform.
   * @return a Menu JPanel
   */
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

    radioButtons = new JRadioButton[4];
    radioButtonGroup = new ButtonGroup();

    List<String> stockMethodOptions = new ArrayList<>(Arrays.asList(
            "portfolio value", "portfolio composition",
            "buy stock", "sell stock"));
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(stockMethodOptions.get(i));
      radioButtons[i].setBackground(Color.WHITE);
      radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand(radioButtons[i].getText());
      radioButtonGroup.add(radioButtons[i]);
      stockActionsPanel.add(radioButtons[i]);
    }
    menuPanel.add(stockActionsPanel);

    // search a stock
    searchStockPanel = new JPanel();
    searchStockPanel.setBackground(Color.WHITE);
    stock = false;
    searchALabel = new JLabel();
    this.setStockOrPortfolio();
    stockSearch = new JTextField(15);
    searchButton = new JButton("search");
    searchButton.setActionCommand("Search");
    searchButton.setPreferredSize(new Dimension(80, 20));
    searchStockPanel.add(searchALabel);
    searchStockPanel.add(stockSearch);
    searchStockPanel.add(searchButton);
    menuPanel.add(searchStockPanel);
    return menuPanel;
  }

  /**
   * The following method creates the data panel on the Stocks Program GUI containing
   * options for the user to specify what date they want to search the stock/portfolio.
   * @return a Data JPanel
   */
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
    yearPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    dataPanel.add(yearPanel);

    monthPanel = new JPanel();
    monthPanel.setBackground(Color.WHITE);
    monthPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    dataPanel.add(monthPanel);

    dayPanel = new JPanel();
    dayPanel.setBackground(Color.WHITE);
    dayPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
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

  @Override
  public void returnResult(String input) {
    String[] results = input.split(":");
    String action = results[0];
    if (action.equals("buy stock")) {
      this.resultString = "User has bought " + results[1] + "stocks to portfolio " + results[2];
    }
    else if (action.equals("sell stock")) {
      this.resultString = "User has sold " + results[1] + " stocks from portfolio " + results[2];
    }
    else if (action.equals("portfolio value")) {
      formattedReturn(Double.parseDouble(results[1]));
      this.resultString = "The value of portfolio " + results[2] + " is " + this.resultString;
    }
    else {
      this.resultString = "error.";
    }
    makeResultWindow(action, this.resultString);
  }

  private void makeResultWindow(String action, String result) {
    JFrame resultWindow = new JFrame("Results for " + action + " method!" + result);
    resultWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel resultPanel = new JPanel(new BorderLayout());
    resultPanel.setPreferredSize(new Dimension(250, 200));
    resultWindow.add(resultPanel);
    JLabel helpLabel = new JLabel(action);
    resultPanel.add(helpLabel);
    resultWindow.pack();;
    resultWindow.setVisible(true);
  }

  @Override
  public void formattedReturn(Double inp) {
    this.resultString = "$" + String.format("%,.2f", inp) + " \n";
  }

  @Override
  public void listWrite(Map<String, Double> input, String type) {
    for (Map.Entry<String, Double> entry : input.entrySet()) {
      this.resultString += entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + "\n";
    }
    makeResultWindow(type, this.resultString);
  }

  public String getYear() {
    return (String) yearsCombobox.getSelectedItem();
  }

  public String getMonth() {
    return (String) monthsCombobox.getSelectedItem();
  }

  public String getDay() {
    return (String) daysCombobox.getSelectedItem();
  }

  public String getStock() {
    return stockSearch.getText();
  }

  public String getValue() {
    return enterValue.getText();
  }

  public String getStockAction() {
    for (JRadioButton b : radioButtons) {
      String a = b.getActionCommand();
      if (a != null) {
        return a.toString();
      }
    }
    return "";
  }

  public void setFieldBlank(String place) {
    if (place.equals("stock")) {
      stockSearch.setText("");
    }
    else if (place.equals("value")) {
      enterValue.setText("");
    }
  }

  public void setHelpListener(ActionListener listen) {
    helpButton.addActionListener(listen);
  }

  public void setLoadListener(ActionListener listen) {
    loadButton.addActionListener(listen);
  }

  public void setStockPortfolioListener(ActionListener listen) {
    selectionComboBox.addActionListener(listen);
  }

  public void setStockActionListener(ActionListener listen) {
    for (JRadioButton radioButton : radioButtons) {
      radioButton.addActionListener(listen);
    }
  }

  @Override
  public void setCreatePortfolioListener(ActionListener listen) {

  }

  public void setStockSearchListener(ActionListener listen) {
    stockSearch.addActionListener(listen);
  }

  public void setEnterValueListener(ActionListener listen) {
    enterValue.addActionListener(listen);
  }

  public void setYearsListener(ActionListener listen) {
    yearsCombobox.addActionListener(listen);
  }

  public void setMonthsListener(ActionListener listen) {
    monthsCombobox.addActionListener(listen);
  }

  public void setDaysListener(ActionListener listen) {
    daysCombobox.addActionListener(listen);
  }

  public void setSearchListener(ActionListener listen) {
    searchButton.addActionListener(listen);
  }

  /**
   * The following method creates a combobox based on the given options.
   * @param options is a list of Strings representing the different options for the combo box
   * @return a combobox of strings
   */
  public JComboBox<String> createComboBox(String[] options) {
    JComboBox<String> comboBox = new JComboBox<String>();
    for (int i = 0; i < options.length; i++) {
      comboBox.addItem(options[i]);
    }
    return comboBox;
  }

  public void setStockOrPortfolio() {
    stock = !stock;
    if (stock) {
      searchALabel.setText("Search a Stock:");
    }
    else {
      searchALabel.setText("Search a Portfolio:");
    }
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
  public void portfolioException(boolean buy) {

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
}