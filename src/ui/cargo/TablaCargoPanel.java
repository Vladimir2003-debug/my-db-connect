// Archivo: src/ui/TablaCargoPanel.java
package ui.cargo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaCargoPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaCargoPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Cargos"));
        setLayout(new BorderLayout());

        String[] columnas = {"CarCod", "CarNom", "CarDesc"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
