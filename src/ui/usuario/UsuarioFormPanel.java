package ui.usuario;

import javax.swing.*;
import java.awt.*;

import controller.UsuarioController;
import ui.BotonesPanel;

public class UsuarioFormPanel extends JPanel {
    public UsuarioFormPanel() {
        setLayout(new BorderLayout(10, 10));

        RegistroUsuarioPanel registro = new RegistroUsuarioPanel();
        TablaUsuarioPanel tabla = new TablaUsuarioPanel();
        BotonesPanel botones = new BotonesPanel();

        add(registro, BorderLayout.NORTH);
        add(tabla, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        new UsuarioController(registro, tabla, botones);
    }
}