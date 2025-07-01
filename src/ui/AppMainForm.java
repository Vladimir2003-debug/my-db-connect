// Archivo: src/ui/AppMainForm.java
package ui;

import javax.swing.*;

import ui.cargo.CargoFormPanel;
import ui.rol.RolFormPanel;
import ui.usuario.UsuarioFormPanel;

import java.awt.*;

public class AppMainForm extends JFrame {
    private JPanel contentPanel;

    public AppMainForm() {
        setTitle("Sistema de Gestión");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con botones de navegación
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRol = new JButton("ROL");
        JButton btnCargo = new JButton("CARGO");
        JButton btnUsuario = new JButton("USUARIO");

        navPanel.add(btnRol);
        navPanel.add(btnCargo);
        navPanel.add(btnUsuario);
        add(navPanel, BorderLayout.NORTH);

        // Panel central donde se cambiará el contenido
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Acciones de los botones
        btnRol.addActionListener(e -> mostrarRol());
        btnCargo.addActionListener(e -> mostrarCargo());
        btnUsuario.addActionListener(e -> mostrarUsuario());

        // Mostrar el primer módulo por defecto
        mostrarRol();
    }

    private void mostrarRol() {
        contentPanel.removeAll();
        contentPanel.add(new RolFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarCargo() {
        contentPanel.removeAll();
        contentPanel.add(new CargoFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarUsuario() {
        contentPanel.removeAll();
        contentPanel.add(new UsuarioFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
