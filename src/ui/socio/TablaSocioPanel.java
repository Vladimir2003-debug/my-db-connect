package ui.socio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaSocioPanel extends JPanel {
    public JTable tabla;
    public DefaultTableModel modelo;

    public TablaSocioPanel() {
        setBorder(BorderFactory.createTitledBorder("Tabla de Socios"));
        setLayout(new BorderLayout());

        String[] columnas = {
            "SocCod", "SocIden", "SocCor", "SocTipPro", "SocCta",
            "SocDep", "SocPro", "SocDis", "SocEmp", "SocDat", "SocFecha"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
