package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.cooperativa.RegistroCooperativaPanel;
import ui.cooperativa.TablaCooperativaPanel;

import java.sql.*;
import javax.swing.*;

public class CooperativaController {
    private RegistroCooperativaPanel registroPanel;
    private TablaCooperativaPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    public CooperativaController(RegistroCooperativaPanel registro, TablaCooperativaPanel tabla, BotonesPanel botones) {
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
            String sql = "SELECT * FROM cooperativa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("CooCod"),
                    rs.getString("CooIde"),
                    rs.getString("CooNom"),
                    rs.getString("CooSig"),
                    rs.getString("CooDir"),
                    rs.getInt("CooTel"),
                    rs.getString("CooCor"),
                    rs.getString("CooSlo"),
                    rs.getBytes("CooLog"),  // BLOB
                    rs.getString("CooUsu")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cooperativas: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarCooperativa());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarCooperativa());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarCooperativa());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivar());
        botonesPanel.btnReactivar.addActionListener(e -> reactivar());
    }

    private void agregarCooperativa() {
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO cooperativa (CooIde, CooNom, CooSig, CooDir, CooTel, CooCor, CooSlo, CooUsu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtCooIde.getText().trim());
            ps.setString(2, registroPanel.txtCooNom.getText().trim());
            ps.setString(3, registroPanel.txtCooSig.getText().trim());
            ps.setString(4, registroPanel.txtCooDir.getText().trim());
            ps.setInt(5, Integer.parseInt(registroPanel.txtCooTel.getText().trim()));
            ps.setString(6, registroPanel.txtCooCor.getText().trim());
            ps.setString(7, registroPanel.txtCooSlo.getText().trim());
            ps.setString(8, registroPanel.txtCooUsu.getText().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cooperativa insertada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de teléfono inválido.");
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar.");
            return;
        }

        registroPanel.txtCooCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtCooIde.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtCooNom.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtCooSig.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.txtCooDir.setText(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.txtCooTel.setText(tablaPanel.modelo.getValueAt(fila, 5).toString());
        registroPanel.txtCooCor.setText(tablaPanel.modelo.getValueAt(fila, 6).toString());
        registroPanel.txtCooSlo.setText(tablaPanel.modelo.getValueAt(fila, 7).toString());
        registroPanel.txtCooUsu.setText(tablaPanel.modelo.getValueAt(fila, 9).toString());

        flagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarCooperativa() {
        if (flagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE cooperativa SET CooIde=?, CooNom=?, CooSig=?, CooDir=?, CooTel=?, CooCor=?, CooSlo=?, CooUsu=? WHERE CooCod=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtCooIde.getText().trim());
            ps.setString(2, registroPanel.txtCooNom.getText().trim());
            ps.setString(3, registroPanel.txtCooSig.getText().trim());
            ps.setString(4, registroPanel.txtCooDir.getText().trim());
            ps.setInt(5, Integer.parseInt(registroPanel.txtCooTel.getText().trim()));
            ps.setString(6, registroPanel.txtCooCor.getText().trim());
            ps.setString(7, registroPanel.txtCooSlo.getText().trim());
            ps.setString(8, registroPanel.txtCooUsu.getText().trim());
            ps.setInt(9, Integer.parseInt(registroPanel.txtCooCod.getText().trim()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cooperativa actualizada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarCooperativa() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int cooCod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar esta cooperativa?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM cooperativa WHERE CooCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cooCod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Cooperativa eliminada correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        registroPanel.txtCooCod.setText("");
        registroPanel.txtCooIde.setText("");
        registroPanel.txtCooNom.setText("");
        registroPanel.txtCooSig.setText("");
        registroPanel.txtCooDir.setText("");
        registroPanel.txtCooTel.setText("");
        registroPanel.txtCooCor.setText("");
        registroPanel.txtCooSlo.setText("");
        registroPanel.txtCooUsu.setText("");
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
