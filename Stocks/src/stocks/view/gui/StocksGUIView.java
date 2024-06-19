package stocks.view.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import stocks.view.StocksView;
import stocks.view.gui.panels.DataPanel;
import stocks.view.gui.panels.MenuPanel;
import stocks.view.gui.panels.PanelItems;
import stocks.view.gui.panels.SearchPanel;

public class StocksGUIView extends JFrame implements StocksView {
  private JPanel mainPanel;
  private JPanel searchPanel, menuPanel, dataPanel;
  private PanelItems pi;

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
    searchPanel = new SearchPanel();
    searchPanel.setPreferredSize(new Dimension(800, 100));
    searchPanel.setBounds(0, 0, 800, 100);

    menuPanel = new MenuPanel();
    menuPanel.setPreferredSize(new Dimension(400, 500));
    menuPanel.setBounds(0,100,400, 600);

    dataPanel = new DataPanel();
    dataPanel.setPreferredSize(new Dimension(400, 500));
    dataPanel.setBounds(400, 100, 800, 600);

    mainPanel.add(searchPanel);
    mainPanel.add(menuPanel);
    mainPanel.add(dataPanel);

    this.pack();
  }

  @Override
  public void setCommandListener(ActionListener e) {
    pi.addCommandListener(e);
  }

  @Override
  public String retrieveCommand() {
    return pi.getCommand();
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
