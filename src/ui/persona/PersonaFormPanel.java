package ui.persona;

import javax.swing.*;
import java.awt.*;
import controller.PersonaController;
import ui.BotonesPanel;

public class PersonaFormPanel extends JPanel {
    public PersonaFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroPersonaPanel registro = new RegistroPersonaPanel();
        TablaPersonaPanel tabla = new TablaPersonaPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new PersonaController(registro, tabla, botones);
    }
}
