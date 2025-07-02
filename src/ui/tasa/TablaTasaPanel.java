package ui.tasa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaTasaPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaTasaPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Tasas"));
        setLayout(new BorderLayout());

        String[] columnas = {
            "TasCod", "TasIden", "TasDes", "TasTas", "TasPlaz", "TasIniFecha", "TasFinFecha"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
