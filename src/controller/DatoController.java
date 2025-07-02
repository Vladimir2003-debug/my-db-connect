package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.dato.RegistroDatoPanel;
import ui.dato.TablaDatoPanel;

import javax.swing.*;
import java.sql.*;

public class DatoController {
    private RegistroDatoPanel registroPanel;
    private TablaDatoPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    public DatoController(RegistroDatoPanel registro, TablaDatoPanel tabla, BotonesPanel botones) {
        this.registroPanel = registro;
        this.tablaPanel = tabla;
        this.botonesPanel = botones;

        cargarDatosDesdeBD();
        botonesPanel.activarModoNormal();
        initListeners();
    }

    private void cargarDatosDesdeBD() {
        tablaPanel.modelo.setRowCount(0);
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT * FROM dato";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("DatCod"),
                    rs.getString("DatApeMat"),
                    rs.getString("DatApePat"),
                    rs.getString("DatNom")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarDato());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarDato());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarDato());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivar());
        botonesPanel.btnReactivar.addActionListener(e -> reactivar());
    }

    private void agregarDato() {
        String apeMat = registroPanel.txtDatApeMat.getText().trim();
        String apePat = registroPanel.txtDatApePat.getText().trim();
        String nombre = registroPanel.txtDatNom.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre (DatNom) es obligatorio.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO dato (DatApeMat, DatApePat, DatNom) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, apeMat);
            ps.setString(2, apePat);
            ps.setString(3, nombre);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dato insertado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar.");
            return;
        }

        registroPanel.txtDatCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtDatApeMat.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtDatApePat.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtDatNom.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());

        flagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarDato() {
        if (flagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        int cod = Integer.parseInt(registroPanel.txtDatCod.getText().trim());

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE dato SET DatApeMat=?, DatApePat=?, DatNom=? WHERE DatCod=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, registroPanel.txtDatApeMat.getText().trim());
            ps.setString(2, registroPanel.txtDatApePat.getText().trim());
            ps.setString(3, registroPanel.txtDatNom.getText().trim());
            ps.setInt(4, cod);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Dato actualizado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarDato() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int datCod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Deseas eliminar este dato?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM dato WHERE DatCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, datCod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Dato eliminado correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        registroPanel.txtDatCod.setText("");
        registroPanel.txtDatApeMat.setText("");
        registroPanel.txtDatApePat.setText("");
        registroPanel.txtDatNom.setText("");
    }

    private void inactivar() {
        registroPanel.chkEstado.setSelected(false);
    }

    private void reactivar() {
        registroPanel.chkEstado.setSelected(true);
    }

    private void cancelarOperacion() {
        limpiarCampos();
        flagAct = 0;
        modoOperacion = "";
        tablaPanel.tabla.clearSelection();
    }

    private void salirDelPrograma() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que deseas salir del programa?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
