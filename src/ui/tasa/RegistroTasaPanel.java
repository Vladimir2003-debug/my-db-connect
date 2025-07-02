package ui.tasa;

import javax.swing.*;
import java.awt.*;

public class RegistroTasaPanel extends JPanel {
    public JTextField txtTasCod, txtTasIden, txtTasDes, txtTasTas, txtTasPlaz, txtTasIniFecha, txtTasFinFecha;
    public JCheckBox chkEstado;

    public RegistroTasaPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Tasa"));
        setLayout(new GridBagLayout());

        txtTasCod = new JTextField(5); txtTasCod.setEditable(false);
        txtTasIden = new JTextField(15);
        txtTasDes = new JTextField(20);
        txtTasTas = new JTextField(10);
        txtTasPlaz = new JTextField(10);
        txtTasIniFecha = new JTextField(10);
        txtTasFinFecha = new JTextField(10);
        chkEstado = new JCheckBox("Activo");

        int y = 0;
        add(new JLabel("TasCod:"), gbc(0, y)); add(txtTasCod, gbc(1, y++));
        add(new JLabel("TasIden:"), gbc(0, y)); add(txtTasIden, gbc(1, y++));
        add(new JLabel("TasDes:"), gbc(0, y)); add(txtTasDes, gbc(1, y++));
        add(new JLabel("TasTas:"), gbc(0, y)); add(txtTasTas, gbc(1, y++));
        add(new JLabel("TasPlaz:"), gbc(0, y)); add(txtTasPlaz, gbc(1, y++));
        add(new JLabel("TasIniFecha:"), gbc(0, y)); add(txtTasIniFecha, gbc(1, y++));
        add(new JLabel("TasFinFecha:"), gbc(0, y)); add(txtTasFinFecha, gbc(1, y++));
        add(new JLabel("Estado Registro:"), gbc(0, y)); add(chkEstado, gbc(1, y));
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}
