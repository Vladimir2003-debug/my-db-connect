package ui.dato;

import javax.swing.*;
import java.awt.*;

public class RegistroDatoPanel extends JPanel {
    public JTextField txtDatCod, txtDatApeMat, txtDatApePat, txtDatNom;
    public JCheckBox chkEstado;

    public RegistroDatoPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Dato"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = crearGBC();

        txtDatCod = new JTextField(5); txtDatCod.setEditable(false);
        txtDatApeMat = new JTextField(15);
        txtDatApePat = new JTextField(15);
        txtDatNom = new JTextField(20);
        chkEstado = new JCheckBox("Activo");

        int y = 0;
        add(new JLabel("DatCod:"), gbc(0, y)); add(txtDatCod, gbc(1, y++));
        add(new JLabel("DatApeMat:"), gbc(0, y)); add(txtDatApeMat, gbc(1, y++));
        add(new JLabel("DatApePat:"), gbc(0, y)); add(txtDatApePat, gbc(1, y++));
        add(new JLabel("DatNom:"), gbc(0, y)); add(txtDatNom, gbc(1, y++));
        add(new JLabel("Estado Registro:"), gbc(0, y)); add(chkEstado, gbc(1, y));
    }

    private GridBagConstraints crearGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = crearGBC();
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}
