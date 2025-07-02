package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.persona.RegistroPersonaPanel;
import ui.persona.TablaPersonaPanel;

import javax.swing.*;
import java.sql.*;

public class PersonaController {
    private RegistroPersonaPanel registroPanel;
    private TablaPersonaPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    public PersonaController(RegistroPersonaPanel registro, TablaPersonaPanel tabla, BotonesPanel botones) {
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
            String sql = "SELECT * FROM persona";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("PerCod"),
                    rs.getString("PerIden"),
                    rs.getString("PerCor"),
                    rs.getBytes("PerFot"),
                    rs.getInt("PerCoo"),
                    rs.getInt("PerDat"),
                    rs.getInt("PerFecha")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar personas: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarPersona());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarPersona());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarPersona());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivar());
        botonesPanel.btnReactivar.addActionListener(e -> reactivar());
    }

    private void agregarPersona() {
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO persona (PerIden, PerCor, PerFot, PerCoo, PerDat, PerFecha) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtPerIden.getText().trim());
            ps.setString(2, registroPanel.txtPerCor.getText().trim());
            ps.setBytes(3, registroPanel.getFotoBytes());  // método que retorna los bytes de la imagen
            ps.setInt(4, Integer.parseInt(registroPanel.txtPerCoo.getText().trim()));
            ps.setInt(5, Integer.parseInt(registroPanel.txtPerDat.getText().trim()));
            ps.setInt(6, Integer.parseInt(registroPanel.txtPerFecha.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona insertada correctamente.");
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

        registroPanel.txtPerCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtPerIden.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtPerCor.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.setFotoBytes((byte[]) tablaPanel.modelo.getValueAt(fila, 3)); // cargar imagen
        registroPanel.txtPerCoo.setText(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.txtPerDat.setText(tablaPanel.modelo.getValueAt(fila, 5).toString());
        registroPanel.txtPerFecha.setText(tablaPanel.modelo.getValueAt(fila, 6).toString());

        flagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarPersona() {
        if (flagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE persona SET PerIden=?, PerCor=?, PerFot=?, PerCoo=?, PerDat=?, PerFecha=? WHERE PerCod=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtPerIden.getText().trim());
            ps.setString(2, registroPanel.txtPerCor.getText().trim());
            ps.setBytes(3, registroPanel.getFotoBytes());
            ps.setInt(4, Integer.parseInt(registroPanel.txtPerCoo.getText().trim()));
            ps.setInt(5, Integer.parseInt(registroPanel.txtPerDat.getText().trim()));
            ps.setInt(6, Integer.parseInt(registroPanel.txtPerFecha.getText().trim()));
            ps.setInt(7, Integer.parseInt(registroPanel.txtPerCod.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona actualizada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarPersona() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int perCod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Deseas eliminar esta persona?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM persona WHERE PerCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, perCod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Persona eliminada correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        registroPanel.txtPerCod.setText("");
        registroPanel.txtPerIden.setText("");
        registroPanel.txtPerCor.setText("");
        registroPanel.txtPerCoo.setText("");
        registroPanel.txtPerDat.setText("");
        registroPanel.txtPerFecha.setText("");
        registroPanel.limpiarFoto();
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
