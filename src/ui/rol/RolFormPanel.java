// Archivo: src/ui/RolFormPanel.java
package ui.rol;

import javax.swing.*;
import java.awt.*;
import controller.RolController;
import ui.BotonesPanel;

public class RolFormPanel extends JPanel {
    public RolFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroRolPanel registro = new RegistroRolPanel();
        TablaRolPanel tabla = new TablaRolPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new RolController(registro, tabla, botones);
    }
}
