package ui.fecha;

import javax.swing.*;
import java.awt.*;
import controller.FechaController;
import ui.BotonesPanel;

public class FechaFormPanel extends JPanel {
    public FechaFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroFechaPanel registro = new RegistroFechaPanel();
        TablaFechaPanel tabla = new TablaFechaPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new FechaController(registro, tabla, botones);
    }
}
