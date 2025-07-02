package ui.cooperativa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaCooperativaPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaCooperativaPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Cooperativas"));
        setLayout(new BorderLayout());

        String[] columnas = {
            "CooCod", "CooIde", "CooNom", "CooSig", "CooDir", "CooTel",
            "CooCor", "CooSlo", "CooLog", "CooUsu"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
