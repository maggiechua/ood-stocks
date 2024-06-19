package stocks.view.gui.panels;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class DataPanel extends JPanel implements PanelItems {
  private JPanel valPanel, datePanel;
  private JTextField enterValue;
  private JLabel enterValLabel, enterYearLabel, enterMonthLabel, enterDayLabel;
  private JButton searchButton;
  JComboBox<String> yearsCombobox, monthsCombobox, daysCombobox;

  public DataPanel() {
    super();
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(400, 500));
    this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    valPanel = new JPanel();
    valPanel.setBackground(Color.WHITE);
    this.add(valPanel);
    valPanel.setBounds(400, 100, 800, 200);
    enterValLabel = new JLabel("Enter value:");
    enterValue = new JTextField(10);
    enterValLabel.add(enterValue);
    valPanel.add(enterValLabel);
    valPanel.add(enterValue);

    // entering date fields
    datePanel = new JPanel();
    datePanel.setBackground(Color.WHITE);
    this.add(datePanel);
    datePanel.setBounds(400, 200, 800, 400);

    enterYearLabel = new JLabel("Enter year:");
    datePanel.add(enterYearLabel);
    List<String> years = new ArrayList<String>();
    for (int y = 2000; y < 2025; y++) {
      years.add((String.valueOf(y)));
    }
    String[] yearOptions = new String[years.size()];
    yearOptions = years.toArray(yearOptions);
    yearsCombobox = this.createComboBox(yearOptions);
    enterYearLabel.add(yearsCombobox);
    datePanel.add(yearsCombobox);

    enterMonthLabel = new JLabel("Enter month:");
    datePanel.add(enterMonthLabel);
    List<String> months = new ArrayList<String>();
    for (int y = 1; y < 13; y++) {
      months.add((String.valueOf(y)));
    }
    String[] monthOptions = new String[months.size()];
    monthOptions = months.toArray(monthOptions);
    monthsCombobox = this.createComboBox(monthOptions);
    enterMonthLabel.add(monthsCombobox);
    datePanel.add(monthsCombobox);

    enterDayLabel = new JLabel("Enter day:");
    datePanel.add(enterDayLabel);
    List<String> days = new ArrayList<String>();
    for (int y = 1; y < 32; y++) {
      days.add((String.valueOf(y)));
    }
    String[] dayOptions = new String[days.size()];
    dayOptions = days.toArray(dayOptions);
    daysCombobox = this.createComboBox(dayOptions);
    enterDayLabel.add(daysCombobox);
    datePanel.add(daysCombobox);

    // search button
    searchButton = new JButton();
    searchButton.setText("search");
    this.add(searchButton);
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
  public void addCommandListener(ActionListener actionEvent) {
    enterValue.addActionListener(actionEvent);
  }

  @Override
  public String getCommand() {
    String command = this.enterValue.getText();
    this.enterValue.setText("");
    return command;
  }
}
