package ui.dato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaDatoPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaDatoPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Datos"));
        setLayout(new BorderLayout());

        String[] columnas = {"DatCod", "DatApeMat", "DatApePat", "DatNom"};

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
