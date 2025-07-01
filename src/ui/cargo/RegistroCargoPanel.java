// src/ui/RegistroCargoPanel.java
package ui.cargo;

import javax.swing.*;
import java.awt.*;

public class RegistroCargoPanel extends JPanel {
    public JTextField txtCarCod, txtCarNom, txtCarDesc;

    public RegistroCargoPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Cargo"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCarCod = new JTextField(10);
        txtCarNom = new JTextField(15);
        txtCarDesc = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("CarCod:"), gbc);
        gbc.gridx = 1; add(txtCarCod, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("CarNom:"), gbc);
        gbc.gridx = 1; add(txtCarNom, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("CarDesc:"), gbc);
        gbc.gridx = 1; add(txtCarDesc, gbc);
    }
}
