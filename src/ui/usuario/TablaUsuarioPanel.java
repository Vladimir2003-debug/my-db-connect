package ui.usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaUsuarioPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaUsuarioPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Usuarios"));
        setLayout(new BorderLayout());

        String[] columnas = {"UsuCod", "UsuIde", "UsuUsu", "UsuPas", "UsuRol", "UsuEmp"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}