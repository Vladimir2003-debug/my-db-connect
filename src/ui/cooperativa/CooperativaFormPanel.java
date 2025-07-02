package ui.cooperativa;

import javax.swing.*;
import java.awt.*;
import controller.CooperativaController;
import ui.BotonesPanel;

public class CooperativaFormPanel extends JPanel {
    public CooperativaFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroCooperativaPanel registro = new RegistroCooperativaPanel();
        TablaCooperativaPanel tabla = new TablaCooperativaPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new CooperativaController(registro, tabla, botones);
    }
}
