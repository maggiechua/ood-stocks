package stocks.view.gui.panels;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DataPanel extends JPanel implements PanelItems {
  private JTextField enterValue;
  private JLabel enterValLabel;

  public DataPanel() {
    super();
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(400, 500));

    enterValLabel = new JLabel("Enter value:");
    enterValue = new JTextField(10);
    enterValLabel.add(enterValue);
    this.add(enterValLabel);
    this.add(enterValue);

  }

  @Override
  public JComboBox<String> createComboBox(String[] options) {
    return null;
  }

  @Override
  public void addCommandListener(ActionListener actionEvent) {
    enterValue.addActionListener(actionEvent);
  }
}
