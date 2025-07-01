package ui.cargo;

import javax.swing.*;

import ui.BotonesPanel;

import java.awt.*;

public class CargoFormPanel extends JPanel {
    public CargoFormPanel() {
        setLayout(new BorderLayout(10, 10));

        // Puedes usar los mismos paneles reutilizables mientras no tengas lógica específica
        RegistroCargoPanel registro = new RegistroCargoPanel();
        TablaCargoPanel tabla = new TablaCargoPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // Aquí luego puedes poner: new CargoController(registro, tabla, botones);
    }
}