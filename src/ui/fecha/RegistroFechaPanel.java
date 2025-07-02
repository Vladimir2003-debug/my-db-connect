package ui.fecha;

import javax.swing.*;
import java.awt.*;

public class RegistroFechaPanel extends JPanel {
    public JTextField txtFechaCod, txtFechaDia, txtFechaMes, txtFechaAño;
    public JCheckBox chkEstado;

    public RegistroFechaPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Fecha"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFechaCod = new JTextField(10);
        txtFechaDia = new JTextField(10);
        txtFechaMes = new JTextField(10);
        txtFechaAño = new JTextField(10);
        chkEstado = new JCheckBox("Activo");

        txtFechaCod.setEditable(false); // ID autogenerado

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("FechaCod:"), gbc);
        gbc.gridx = 1;
        add(txtFechaCod, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("FechaDia:"), gbc);
        gbc.gridx = 1;
        add(txtFechaDia, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("FechaMes:"), gbc);
        gbc.gridx = 1;
        add(txtFechaMes, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("FechaAño:"), gbc);
        gbc.gridx = 1;
        add(txtFechaAño, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Estado Registro:"), gbc);
        gbc.gridx = 1;
        add(chkEstado, gbc);
    }

    public void setCamposEditables(boolean editable) {
        txtFechaDia.setEditable(editable);
        txtFechaMes.setEditable(editable);
        txtFechaAño.setEditable(editable);
        chkEstado.setEnabled(editable);
    }
}
