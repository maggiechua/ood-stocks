package stocks.view.gui.panels;

import java.awt.event.ActionListener;

import javax.swing.*;

public interface PanelItems {
  public JComboBox<String> createComboBox(String[] options);

  public void addCommandListener(ActionListener actionEvent);

  public String getCommand();
}
