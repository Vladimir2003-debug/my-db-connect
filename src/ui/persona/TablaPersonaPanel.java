package ui.persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaPersonaPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaPersonaPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Personas"));
        setLayout(new BorderLayout());

        String[] columnas = {"PerCod", "PerIden", "PerCor", "PerFot", "PerCoo", "PerDat", "PerFecha"};

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
