package stocks.view.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import stocks.view.gui.StocksGUIView;

public class DataPanel extends JPanel implements PanelItems, ActionListener {
  private JPanel valPanel, yearPanel, monthPanel, dayPanel;
  private JTextField enterValue;
  private JLabel enterValLabel, enterYearLabel, enterMonthLabel, enterDayLabel;
  private JButton searchButton;
  JComboBox<String> yearsCombobox, monthsCombobox, daysCombobox;
  StocksGUIView frame;
  ActionListener listen;

  public DataPanel(StocksGUIView view, ActionListener listen) {
    super();
    this.frame = view;
    this.listen = listen;
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(400, 500));
    this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

    valPanel = new JPanel();
    valPanel.setBackground(Color.WHITE);
    this.add(valPanel);
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
    this.add(yearPanel);

    monthPanel = new JPanel();
    monthPanel.setBackground(Color.WHITE);
    monthPanel.setBorder(BorderFactory.createEmptyBorder(-10, 50, -10, 50));
    this.add(monthPanel);

    dayPanel = new JPanel();
    dayPanel.setBackground(Color.WHITE);
    dayPanel.setBorder(BorderFactory.createEmptyBorder(-10, 50, -10, 50));
    this.add(dayPanel);

    enterYearLabel = new JLabel("Enter year:");
    yearPanel.add(enterYearLabel);
    List<String> years = new ArrayList<String>();
    for (int y = 2000; y < 2025; y++) {
      years.add((String.valueOf(y)));
    }
    String[] yearOptions = new String[years.size()];
    yearOptions = years.toArray(yearOptions);
    yearsCombobox = this.createComboBox(yearOptions);
    yearsCombobox.addActionListener(listen);
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
    monthsCombobox.addActionListener(listen);
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
    daysCombobox.addActionListener(listen);
    daysCombobox.setActionCommand("day-select");
    enterDayLabel.add(daysCombobox);
    dayPanel.add(daysCombobox);
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
    listen.actionPerformed(e);
  }
}
