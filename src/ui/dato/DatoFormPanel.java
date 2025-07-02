package ui.dato;

import javax.swing.*;
import java.awt.*;
import controller.DatoController;
import ui.BotonesPanel;

public class DatoFormPanel extends JPanel {
    public DatoFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroDatoPanel registro = new RegistroDatoPanel();
        TablaDatoPanel tabla = new TablaDatoPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new DatoController(registro, tabla, botones);
    }
}
