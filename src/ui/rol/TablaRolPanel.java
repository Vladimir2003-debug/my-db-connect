package ui.rol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaRolPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaRolPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Roles"));
        setLayout(new BorderLayout());

        String[] columnas = {"RolCod", "RolRol", "RolNom", "RolUsu"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}