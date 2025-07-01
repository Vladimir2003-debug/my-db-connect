package ui;

import javax.swing.*;
import java.awt.*;

public class BotonesPanel extends JPanel {
    public JButton btnAdicionar, btnModificar, btnEliminar, btnCancelar;
    public JButton btnInactivar, btnReactivar, btnActualizar, btnSalir;

    public BotonesPanel() {
        setLayout(new GridLayout(2, 4, 10, 10));

        btnAdicionar = new JButton("Adicionar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");
        btnInactivar = new JButton("Inactivar");
        btnReactivar = new JButton("Reactivar");
        btnActualizar = new JButton("Actualizar");
        btnSalir = new JButton("Salir");

        add(btnAdicionar);
        add(btnModificar);
        add(btnEliminar);
        add(btnCancelar);
        add(btnInactivar);
        add(btnReactivar);
        add(btnActualizar);
        add(btnSalir);
    }

    // ✅ Métodos para habilitar o deshabilitar botones según contexto

    public void habilitarBotones(boolean habilitar) {
        btnAdicionar.setEnabled(habilitar);
        btnModificar.setEnabled(habilitar);
        btnEliminar.setEnabled(habilitar);
        btnCancelar.setEnabled(habilitar);
        btnInactivar.setEnabled(habilitar);
        btnReactivar.setEnabled(habilitar);
        btnActualizar.setEnabled(habilitar);
        btnSalir.setEnabled(habilitar);
    }

    public void activarModoAdicionar() {
        habilitarBotones(false);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(true);
    }

    public void activarModoModificar() {
        habilitarBotones(false);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(true);
    }

    public void activarModoInactivar() {
        habilitarBotones(false);
        btnCancelar.setEnabled(true);
        btnReactivar.setEnabled(true);
        btnActualizar.setEnabled(true);
    }

    public void activarModoReactivar() {
        habilitarBotones(true);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(true);
    }

    public void activarModoNormal() {
        btnAdicionar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnInactivar.setEnabled(true);
        btnReactivar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
}
