package ui.tasa;

import javax.swing.*;
import java.awt.*;
import controller.TasaController;
import ui.BotonesPanel;

public class TasaFormPanel extends JPanel {
    public TasaFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroTasaPanel registro = new RegistroTasaPanel();
        TablaTasaPanel tabla = new TablaTasaPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new TasaController(registro, tabla, botones);
    }
}