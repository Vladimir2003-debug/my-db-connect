// src/ui/RegistroRolPanel.java
package ui.rol;

import javax.swing.*;
import java.awt.*;

public class RegistroRolPanel extends JPanel {
    public JTextField txtRolCod, txtRolRol, txtRolNom;
    public JCheckBox chkEstado;

    public RegistroRolPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Rol"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtRolCod = new JTextField(10);
        txtRolRol = new JTextField(10);
        txtRolNom = new JTextField(20);
        chkEstado = new JCheckBox("Activo");
        txtRolCod.setEditable(false);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("RolCod:"), gbc);
        gbc.gridx = 1;
        add(txtRolCod, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("RolRol:"), gbc);
        gbc.gridx = 1;
        add(txtRolRol, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("RolNom:"), gbc);
        gbc.gridx = 1;
        add(txtRolNom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Estado Registro:"), gbc);
        gbc.gridx = 1;
        add(chkEstado, gbc); // ✅ agregarlo al panel
    }

    public void setCamposEditables(boolean editable) {
        // txtRolCod sigue siendo ineditable siempre
        txtRolRol.setEditable(editable);
        txtRolNom.setEditable(editable);
        chkEstado.setEnabled(editable);
    }
}
