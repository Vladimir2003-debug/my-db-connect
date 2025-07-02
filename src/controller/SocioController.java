package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.socio.RegistroSocioPanel;
import ui.socio.TablaSocioPanel;

import javax.swing.*;
import java.sql.*;

public class SocioController {
    private RegistroSocioPanel registroPanel;
    private TablaSocioPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    public SocioController(RegistroSocioPanel registro, TablaSocioPanel tabla, BotonesPanel botones) {
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
            String sql = "SELECT * FROM socio";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("SocCod"),
                    rs.getString("SocIden"),
                    rs.getString("SocCor"),
                    rs.getString("SocTipPro"),
                    rs.getString("SocCta"),
                    rs.getString("SocDep"),
                    rs.getString("SocPro"),
                    rs.getString("SocDis"),
                    rs.getInt("SocEmp"),
                    rs.getInt("SocDat"),
                    rs.getInt("SocFecha")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar socios: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarSocio());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarSocio());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarSocio());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivar());
        botonesPanel.btnReactivar.addActionListener(e -> reactivar());
    }

    private void agregarSocio() {
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO socio (SocIden, SocCor, SocTipPro, SocCta, SocDep, SocPro, SocDis, SocEmp, SocDat, SocFecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtSocIden.getText().trim());
            ps.setString(2, registroPanel.txtSocCor.getText().trim());
            ps.setString(3, registroPanel.txtSocTipPro.getText().trim());
            ps.setString(4, registroPanel.txtSocCta.getText().trim());
            ps.setString(5, registroPanel.txtSocDep.getText().trim());
            ps.setString(6, registroPanel.txtSocPro.getText().trim());
            ps.setString(7, registroPanel.txtSocDis.getText().trim());
            ps.setInt(8, Integer.parseInt(registroPanel.txtSocEmp.getText().trim()));
            ps.setInt(9, Integer.parseInt(registroPanel.txtSocDat.getText().trim()));
            ps.setInt(10, Integer.parseInt(registroPanel.txtSocFecha.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Socio insertado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Campos numéricos inválidos.");
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar.");
            return;
        }

        registroPanel.txtSocCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtSocIden.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtSocCor.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtSocTipPro.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.txtSocCta.setText(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.txtSocDep.setText(tablaPanel.modelo.getValueAt(fila, 5).toString());
        registroPanel.txtSocPro.setText(tablaPanel.modelo.getValueAt(fila, 6).toString());
        registroPanel.txtSocDis.setText(tablaPanel.modelo.getValueAt(fila, 7).toString());
        registroPanel.txtSocEmp.setText(tablaPanel.modelo.getValueAt(fila, 8).toString());
        registroPanel.txtSocDat.setText(tablaPanel.modelo.getValueAt(fila, 9).toString());
        registroPanel.txtSocFecha.setText(tablaPanel.modelo.getValueAt(fila, 10).toString());

        flagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarSocio() {
        if (flagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE socio SET SocIden=?, SocCor=?, SocTipPro=?, SocCta=?, SocDep=?, SocPro=?, SocDis=?, SocEmp=?, SocDat=?, SocFecha=? WHERE SocCod=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtSocIden.getText().trim());
            ps.setString(2, registroPanel.txtSocCor.getText().trim());
            ps.setString(3, registroPanel.txtSocTipPro.getText().trim());
            ps.setString(4, registroPanel.txtSocCta.getText().trim());
            ps.setString(5, registroPanel.txtSocDep.getText().trim());
            ps.setString(6, registroPanel.txtSocPro.getText().trim());
            ps.setString(7, registroPanel.txtSocDis.getText().trim());
            ps.setInt(8, Integer.parseInt(registroPanel.txtSocEmp.getText().trim()));
            ps.setInt(9, Integer.parseInt(registroPanel.txtSocDat.getText().trim()));
            ps.setInt(10, Integer.parseInt(registroPanel.txtSocFecha.getText().trim()));
            ps.setInt(11, Integer.parseInt(registroPanel.txtSocCod.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Socio actualizado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarSocio() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int cod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Deseas eliminar este socio?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM socio WHERE SocCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Socio eliminado correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        registroPanel.txtSocCod.setText("");
        registroPanel.txtSocIden.setText("");
        registroPanel.txtSocCor.setText("");
        registroPanel.txtSocTipPro.setText("");
        registroPanel.txtSocCta.setText("");
        registroPanel.txtSocDep.setText("");
        registroPanel.txtSocPro.setText("");
        registroPanel.txtSocDis.setText("");
        registroPanel.txtSocEmp.setText("");
        registroPanel.txtSocDat.setText("");
        registroPanel.txtSocFecha.setText("");
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
