// Archivo: src/ui/AppMainForm.java
package ui;

import javax.swing.*;

import ui.cargo.CargoFormPanel;
import ui.rol.RolFormPanel;
import ui.usuario.UsuarioFormPanel;
import ui.fecha.FechaFormPanel;
import ui.cooperativa.CooperativaFormPanel;
import ui.dato.DatoFormPanel;
import ui.persona.PersonaFormPanel;
import ui.socio.SocioFormPanel;
import ui.tasa.TasaFormPanel;

import java.awt.*;

public class AppMainForm extends JFrame {
    private JPanel contentPanel;

    public AppMainForm() {
        setTitle("Sistema de Gestión");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con botones de navegación
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnRol = new JButton("ROL");
        JButton btnCargo = new JButton("CARGO");
        JButton btnUsuario = new JButton("USUARIO");
        JButton btnFecha = new JButton("FECHA");
        JButton btnCooperativa = new JButton("COOPERATIVA");
        JButton btnDato = new JButton("DATO");
        JButton btnPersona = new JButton("PERSONA");
        JButton btnSocio = new JButton("SOCIO");
        JButton btnTasa = new JButton("TASA");

        navPanel.add(btnRol);
        navPanel.add(btnCargo);
        navPanel.add(btnUsuario);
        navPanel.add(btnFecha);
        navPanel.add(btnCooperativa);
        navPanel.add(btnDato);
        navPanel.add(btnPersona);
        navPanel.add(btnSocio);
        navPanel.add(btnTasa);

        add(navPanel, BorderLayout.NORTH);

        // Panel central donde se cambiará el contenido
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Acciones de los botones
        btnRol.addActionListener(e -> mostrarRol());
        btnCargo.addActionListener(e -> mostrarCargo());
        btnUsuario.addActionListener(e -> mostrarUsuario());
        btnFecha.addActionListener(e -> mostrarFecha());
        btnCooperativa.addActionListener(e -> mostrarCooperativa());
        btnDato.addActionListener(e -> mostrarDato());
        btnPersona.addActionListener(e -> mostrarPersona());
        btnSocio.addActionListener(e -> mostrarSocio());
        btnTasa.addActionListener(e -> mostrarTasa());

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

    private void mostrarFecha() {
        contentPanel.removeAll();
        contentPanel.add(new FechaFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarCooperativa() {
        contentPanel.removeAll();
        contentPanel.add(new CooperativaFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarDato() {
        contentPanel.removeAll();
        contentPanel.add(new DatoFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarPersona() {
        contentPanel.removeAll();
        contentPanel.add(new PersonaFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarSocio() {
        contentPanel.removeAll();
        contentPanel.add(new SocioFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarTasa() {
        contentPanel.removeAll();
        contentPanel.add(new TasaFormPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
