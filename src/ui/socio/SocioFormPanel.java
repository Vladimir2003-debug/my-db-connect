package ui.socio;

import javax.swing.*;
import java.awt.*;
import controller.SocioController;
import ui.BotonesPanel;

public class SocioFormPanel extends JPanel {
    public SocioFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroSocioPanel registro = new RegistroSocioPanel();
        TablaSocioPanel tabla = new TablaSocioPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new SocioController(registro, tabla, botones);
    }
}