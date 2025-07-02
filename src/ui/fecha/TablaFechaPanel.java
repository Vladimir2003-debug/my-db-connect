package ui.fecha;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaFechaPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaFechaPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Fechas"));
        setLayout(new BorderLayout());

        String[] columnas = {"FechaCod", "FechaDia", "FechaMes", "FechaAño"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
