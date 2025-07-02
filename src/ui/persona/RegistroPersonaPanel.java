package ui.persona;

import javax.swing.*;
import java.awt.*;

public class RegistroPersonaPanel extends JPanel {
    public JTextField txtPerCod, txtPerIden, txtPerCor, txtPerCoo, txtPerDat, txtPerFecha;
    public JCheckBox chkEstado;

    public RegistroPersonaPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Persona"));
        setLayout(new GridBagLayout());

        txtPerCod = new JTextField(5); txtPerCod.setEditable(false);
        txtPerIden = new JTextField(15);
        txtPerCor = new JTextField(20);
        txtPerCoo = new JTextField(5);
        txtPerDat = new JTextField(5);
        txtPerFecha = new JTextField(10);
        chkEstado = new JCheckBox("Activo");

        int y = 0;
        add(new JLabel("PerCod:"), gbc(0, y)); add(txtPerCod, gbc(1, y++));
        add(new JLabel("PerIden:"), gbc(0, y)); add(txtPerIden, gbc(1, y++));
        add(new JLabel("PerCor:"), gbc(0, y)); add(txtPerCor, gbc(1, y++));
        add(new JLabel("PerCoo:"), gbc(0, y)); add(txtPerCoo, gbc(1, y++));
        add(new JLabel("PerDat:"), gbc(0, y)); add(txtPerDat, gbc(1, y++));
        add(new JLabel("PerFecha:"), gbc(0, y)); add(txtPerFecha, gbc(1, y++));
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
