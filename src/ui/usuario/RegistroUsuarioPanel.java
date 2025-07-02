package ui.usuario;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioPanel extends JPanel {
    public JTextField txtUsuCod, txtUsuIde, txtUsuUsu;
    public JComboBox<String> comboUsuRol;
    public JCheckBox chkEstado;
    public JPasswordField txtUsuPas;
    public JComboBox<String> comboUsuCoo;

    public RegistroUsuarioPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Usuario"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUsuCod = new JTextField(10);
        txtUsuIde = new JTextField(10);
        txtUsuUsu = new JTextField(10);
        txtUsuPas = new JPasswordField(10);
comboUsuCoo = new JComboBox<>();
        comboUsuRol = new JComboBox<>();
        chkEstado = new JCheckBox("Activo");

        txtUsuCod.setEditable(false); // ID autogenerado

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("UsuCod:"), gbc);
        gbc.gridx = 1;
        add(txtUsuCod, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("UsuIde:"), gbc);
        gbc.gridx = 1;
        add(txtUsuIde, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("UsuUsu:"), gbc);
        gbc.gridx = 1;
        add(txtUsuUsu, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("UsuPas:"), gbc);
        gbc.gridx = 1;
        add(txtUsuPas, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("UsuRol:"), gbc);
        gbc.gridx = 1;
        add(comboUsuRol, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("UsuEmp:"), gbc);
        gbc.gridx = 1;
        add(comboUsuCoo, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Estado Registro:"), gbc);
        gbc.gridx = 1;
        add(chkEstado, gbc);
    }

    public void setCamposEditables(boolean editable) {
        txtUsuIde.setEditable(editable);
        txtUsuUsu.setEditable(editable);
        txtUsuPas.setEditable(editable);
        comboUsuRol.setEnabled(editable);
        comboUsuCoo.setEditable(editable);
        chkEstado.setEnabled(editable);
    }
}