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

public class StocksGUIView extends JFrame implements StocksView {
  private JPanel mainPanel;
  private JPanel searchPanel, stocksPanel, portfoliosPanel, stockActionsPanel, portfolioActionsPanel,
          searchStockPanel, searchPortfolioPanel, searchSPanel;
  private JPanel valPanel, yearPanel, monthPanel, dayPanel;
  private JLabel instructionsLabel;
  private JTextField enterStock, enterValue;
  private JButton helpButton, loadButton, searchSButton, createPortfolioButton, searchPButton;
  private JLabel enterStockLabel, enterValLabel, enterYearLabel, enterMonthLabel, enterDayLabel;
  private JComboBox<String> yearsCombobox, monthsCombobox, daysCombobox;
  private JRadioButton[] radioButtons, portfolioRadioButtons;
  private ButtonGroup radioButtonGroup, portfolioRadioButtonGroup;
  private boolean stock;

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
    stocksPanel = this.createStocksPanel();
    portfoliosPanel = this.createPortfoliosPanel();

    mainPanel.add(searchPanel);
    mainPanel.add(stocksPanel);
    mainPanel.add(portfoliosPanel);

    this.pack();
  }

  public void namePortfolioWindow() {
    JFrame namePortfolioWindow = new JFrame("Create Portfolio");
    namePortfolioWindow .setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel portfolioPanel = new JPanel(new BorderLayout());
    portfolioPanel.setPreferredSize(new Dimension(250, 200));
    namePortfolioWindow .add(portfolioPanel);
    JTextField input = new JTextField(15);
    JButton createButton = new JButton("create");
    portfolioPanel.add(input);
    portfolioPanel.add(createButton);
    namePortfolioWindow.pack();
    namePortfolioWindow.setVisible(true);
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
    return searchPanel;
  }

  /**
   * The following method creates the menu panel on the Stocks Program GUI containing
   * options for the user to choose what stock actions (buy/sell) they want to perform.
   * @return a Menu JPanel
   */
  public JPanel createStocksPanel() {
    stocksPanel = new JPanel();
    stocksPanel.setPreferredSize(new Dimension(400, 500));
    stocksPanel.setBounds(0,100,400, 600);
    stocksPanel.setBackground(Color.WHITE);

    stockActionsPanel = new JPanel();
    stockActionsPanel.setBackground(Color.WHITE);
    stockActionsPanel.setBorder(BorderFactory.createTitledBorder("Stock Actions:"));
    stockActionsPanel.setBounds(0, 100, 400, 600);
    stockActionsPanel.setLayout(new BoxLayout(stockActionsPanel, BoxLayout.PAGE_AXIS));

    radioButtons = new JRadioButton[2];
    radioButtonGroup = new ButtonGroup();

    List<String> stockMethodOptions = new ArrayList<>(Arrays.asList(
            "buy stock", "sell stock"));
    this.setUpRadioButtons(stockMethodOptions, radioButtons, radioButtonGroup, stockActionsPanel);
    stocksPanel.add(stockActionsPanel);

    this.createDateFields(stockActionsPanel);

    // search a stock
    searchStockPanel = new JPanel();
    searchStockPanel.setBackground(Color.WHITE);
    stocksPanel.add(searchStockPanel);
    searchStockPanel.setBounds(400, 300, 800, 400);
    enterStockLabel = new JLabel("Select Stock: ");
    enterStock = new JTextField(10);
    enterStockLabel.add(enterStock);
    searchStockPanel.add(enterStockLabel);
    searchStockPanel.add(enterStock);
    stockActionsPanel.add(searchStockPanel);

    searchSPanel = new JPanel();
    searchSPanel.setBackground(Color.WHITE);
    searchSPanel.setBorder(BorderFactory
            .createEmptyBorder(0, 50, 0, 50));
    searchSButton = new JButton("search");
    searchSButton.setActionCommand("Search");
    searchSButton.setPreferredSize(new Dimension(80, 20));
    searchSPanel.add(searchSButton);
    stocksPanel.add(searchSPanel);

    return stocksPanel;
  }

  /**
   * The following method creates the data panel on the Stocks Program GUI containing
   * options for the user to specify what date they want to search the stock/portfolio.
   * @return a Data JPanel
   */
  public JPanel createPortfoliosPanel() {
    portfoliosPanel = new JPanel();
    portfoliosPanel.setPreferredSize(new Dimension(400, 500));
    portfoliosPanel.setBounds(400, 100, 800, 600);
    portfoliosPanel.setBackground(Color.WHITE);
    portfoliosPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

    portfolioActionsPanel = new JPanel();
    portfolioActionsPanel.setBackground(Color.WHITE);
    portfolioActionsPanel.setBorder(BorderFactory.createTitledBorder("Portfolio Actions:"));
    portfolioActionsPanel.setBounds(400, 100, 800, 300);
    portfolioActionsPanel.setLayout(new BoxLayout(portfolioActionsPanel, BoxLayout.PAGE_AXIS));

    portfolioRadioButtons = new JRadioButton[2];
    portfolioRadioButtonGroup = new ButtonGroup();

    List<String> portfolioMethodOptions = new ArrayList<>(Arrays.asList(
            "portfolio value", "portfolio composition"));
    this.setUpRadioButtons(portfolioMethodOptions, portfolioRadioButtons, portfolioRadioButtonGroup,
            portfolioActionsPanel);
    portfoliosPanel.add(portfolioActionsPanel);

    this.createDateFields(portfolioActionsPanel);

    valPanel = new JPanel();
    valPanel.setBackground(Color.WHITE);
    portfoliosPanel.add(valPanel);
    valPanel.setBounds(400, 300, 800, 400);
    enterValLabel = new JLabel("Select portfolio: ");
    enterValue = new JTextField(10);
    enterValLabel.add(enterValue);
    valPanel.add(enterValLabel);
    valPanel.add(enterValue);
    portfolioActionsPanel.add(valPanel);

    searchPortfolioPanel = new JPanel();
    searchPortfolioPanel.setBackground(Color.WHITE);
    searchPortfolioPanel.setBorder(BorderFactory
            .createEmptyBorder(0, 50, 0, 50));
    searchPButton = new JButton("search");
    searchPButton.setActionCommand("Search");
    searchPButton.setPreferredSize(new Dimension(80, 20));
    searchPortfolioPanel.add(searchPButton);
    portfoliosPanel.add(searchPortfolioPanel);

    return portfoliosPanel;
  }

  /**
   *
   */
  public void createDateFields(JPanel panel) {
    yearPanel = new JPanel();
    yearPanel.setBackground(Color.WHITE);
    yearPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    panel.add(yearPanel);

    monthPanel = new JPanel();
    monthPanel.setBackground(Color.WHITE);
    monthPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    panel.add(monthPanel);

    dayPanel = new JPanel();
    dayPanel.setBackground(Color.WHITE);
    dayPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
    panel.add(dayPanel);

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
//    return stockSearch.getText();
    return "";
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
//      stockSearch.setText("");
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

  public void setCreatePortfolioListener(ActionListener listen) {
    createPortfolioButton.addActionListener(listen);
  }

  public void setStockActionListener(ActionListener listen) {
    for (JRadioButton radioButton : radioButtons) {
      radioButton.addActionListener(listen);
    }
  }

  public void setStockSearchListener(ActionListener listen) {
//    stockSearch.addActionListener(listen);
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
    searchPButton.addActionListener(listen);
//    searchSButton.addActionListener(listen);
  }

  public void setStockOrPortfolio() {
    stock = !stock;
    if (stock) {
//      searchALabel.setText("Search a Stock:");
    }
    else {
//      searchALabel.setText("Search a Portfolio:");
    }
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

  /**
   * The following method sets up radio button features.
   * @param options the given list of options to be displayed
   * @param buttons an array of buttons
   * @param bg a button group
   * @param panel the panel to add the buttons to
   */
  public void setUpRadioButtons(List<String> options, JRadioButton[] buttons, ButtonGroup bg,
                                JPanel panel) {
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new JRadioButton(options.get(i));
      buttons[i].setBackground(Color.WHITE);
      buttons[i].setSelected(false);

      buttons[i].setActionCommand(buttons[i].getText());
      bg.add(buttons[i]);
      panel.add(buttons[i]);
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
}
