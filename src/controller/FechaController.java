package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.fecha.RegistroFechaPanel;
import ui.fecha.TablaFechaPanel;

import javax.swing.*;
import java.sql.*;

public class FechaController {
    private RegistroFechaPanel registroPanel;
    private TablaFechaPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int fechaFlagAct = 0;
    private String modoOperacion = "";

    public FechaController(RegistroFechaPanel registro, TablaFechaPanel tabla, BotonesPanel botones) {
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
            String sql = "SELECT FechaCod, FechaDia, FechaMes, FechaAño FROM fecha";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("FechaCod"),
                        rs.getInt("FechaDia"),
                        rs.getInt("FechaMes"),
                        rs.getInt("FechaAño")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar fechas: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarFecha());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarFecha());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarFecha());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivarFecha());
        botonesPanel.btnReactivar.addActionListener(e -> reactivarFecha());
    }

    private void agregarFecha() {
        String dia = registroPanel.txtFechaDia.getText().trim();
        String mes = registroPanel.txtFechaMes.getText().trim();
        String año = registroPanel.txtFechaAño.getText().trim();

        if (dia.isEmpty() || mes.isEmpty() || año.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO fecha (FechaDia, FechaMes, FechaAño) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(dia));
            ps.setInt(2, Integer.parseInt(mes));
            ps.setInt(3, Integer.parseInt(año));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Fecha insertada correctamente.");
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

        registroPanel.txtFechaCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtFechaDia.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtFechaMes.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtFechaAño.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());

        fechaFlagAct = 1;
        modoOperacion = "modificar";
        botonesPanel.activarModoModificar();
    }

    private void actualizarFecha() {
        if (fechaFlagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        int cod = Integer.parseInt(registroPanel.txtFechaCod.getText());
        int dia = Integer.parseInt(registroPanel.txtFechaDia.getText());
        int mes = Integer.parseInt(registroPanel.txtFechaMes.getText());
        int año = Integer.parseInt(registroPanel.txtFechaAño.getText());

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE fecha SET FechaDia = ?, FechaMes = ?, FechaAño = ? WHERE FechaCod = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, dia);
            ps.setInt(2, mes);
            ps.setInt(3, año);
            ps.setInt(4, cod);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Fecha actualizada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            fechaFlagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarFecha() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int cod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta fecha?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM fecha WHERE FechaCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Fecha eliminada correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void inactivarFecha() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para inactivar.");
            return;
        }

        registroPanel.txtFechaCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtFechaDia.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtFechaMes.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtFechaAño.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.chkEstado.setSelected(false);
        bloquearCamposRegistro();

        fechaFlagAct = 1;
        modoOperacion = "inactivar";
        botonesPanel.activarModoInactivar();
    }

    private void reactivarFecha() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para reactivar.");
            return;
        }

        registroPanel.txtFechaCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtFechaDia.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtFechaMes.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtFechaAño.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.chkEstado.setSelected(true);
        desbloquearCamposRegistro();

        fechaFlagAct = 1;
        modoOperacion = "reactivar";
        botonesPanel.activarModoReactivar();
    }

    private void bloquearCamposRegistro() {
        registroPanel.txtFechaCod.setEnabled(false);
        registroPanel.txtFechaDia.setEnabled(false);
        registroPanel.txtFechaMes.setEnabled(false);
        registroPanel.txtFechaAño.setEnabled(false);
        registroPanel.chkEstado.setEnabled(false);
    }

    private void desbloquearCamposRegistro() {
        registroPanel.txtFechaCod.setEnabled(false);
        registroPanel.txtFechaDia.setEnabled(true);
        registroPanel.txtFechaMes.setEnabled(true);
        registroPanel.txtFechaAño.setEnabled(true);
        registroPanel.chkEstado.setEnabled(true);
    }

    private void cancelarOperacion() {
        limpiarCampos();
        fechaFlagAct = 0;
        modoOperacion = "";
        tablaPanel.tabla.clearSelection();
    }

    private void limpiarCampos() {
        registroPanel.txtFechaCod.setText("");
        registroPanel.txtFechaDia.setText("");
        registroPanel.txtFechaMes.setText("");
        registroPanel.txtFechaAño.setText("");
    }

    private void salirDelPrograma() {
        int confirm = JOptionPane.showConfirmDialog(null, "¿Desea salir del programa?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
