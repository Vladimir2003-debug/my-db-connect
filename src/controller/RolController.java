// Archivo: src/controller/RolController.java
package controller;

import database.ConexionJDBC;
import ui.*;
import ui.rol.RegistroRolPanel;
import ui.rol.TablaRolPanel;

import java.sql.*;
import javax.swing.*;

public class RolController {
    private RegistroRolPanel registroPanel;
    private TablaRolPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int rolFlagAct = 0; // 0 = inactivo, 1 = activo
    private String modoOperacion = ""; // "adicionar" o "modificar"

    public RolController(RegistroRolPanel registro, TablaRolPanel tabla, BotonesPanel botones) {
        this.registroPanel = registro;
        this.tablaPanel = tabla;
        this.botonesPanel = botones;

        cargarDatosDesdeBD();
        botonesPanel.activarModoNormal(); // Al cancelar o actualizar

        initListeners();
    }

    private void cargarDatosDesdeBD() {
        tablaPanel.modelo.setRowCount(0); // limpia antes de cargar
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT RolCod, RolRol, RolNom FROM rol";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("RolCod"),
                        rs.getString("RolRol"),
                        rs.getString("RolNom")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar roles: " + e.getMessage());
        }
    }

    private void initListeners() {

        botonesPanel.btnAdicionar.addActionListener(e -> agregarRol());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarRol());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarRol());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivarRol());
        botonesPanel.btnReactivar.addActionListener(e -> reactivarRol());

    }

    private void agregarRol() {
        String rolRol = registroPanel.txtRolRol.getText().trim();
        String rolNom = registroPanel.txtRolNom.getText().trim();

        // Solo validamos que el nombre del rol esté presente
        if (rolNom.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre del rol (RolNom) es obligatorio.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO rol (RolRol, RolNom) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            // Si el campo está vacío, lo pasamos como NULL
            if (rolRol.isEmpty()) {
                ps.setNull(1, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, rolRol);
            }

            ps.setString(2, rolNom); // este es obligatorio

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Rol insertado correctamente.");
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

        // Llena los campos
        registroPanel.txtRolCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtRolRol.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtRolNom.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());

        // ⚠️ Activar mFodo de modificación
        rolFlagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarRol() {
        if (rolFlagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        int rolCod = Integer.parseInt(registroPanel.txtRolCod.getText());
        String nuevoEstado = registroPanel.chkEstado.isSelected() ? "A" : "I";
        String sql = "UPDATE rol SET RolRol = ?, RolNom = ?, estado = ? WHERE RolCod = ?";

        try (Connection conn = ConexionJDBC.getConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, registroPanel.txtRolRol.getText());
            ps.setString(2, registroPanel.txtRolNom.getText());
            ps.setString(3, nuevoEstado);
            ps.setInt(4, rolCod);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            rolFlagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        registroPanel.txtRolCod.setText("");
        registroPanel.txtRolRol.setText("");
        registroPanel.txtRolNom.setText("");
    }

    private void eliminarRol() {
        int fila = tablaPanel.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que deseas eliminar este rol?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            int rolCod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());

            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM rol WHERE RolCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, rolCod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Rol eliminado correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void inactivarRol() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para inactivar.");
            return;
        }

        registroPanel.txtRolCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtRolRol.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtRolNom.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.chkEstado.setSelected(false); // ⚠️ mostrar como inactivo visualmente
        bloquearCamposRegistro();

        rolFlagAct = 1;
        modoOperacion = "inactivar";
        botonesPanel.activarModoInactivar();
    }

    private void reactivarRol() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para reactivar.");
            return;
        }

        registroPanel.txtRolCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtRolRol.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtRolNom.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.chkEstado.setSelected(true); // ⚠️ mostrar como activo visualmente
        desbloquearCamposRegistro();
        rolFlagAct = 1;
        modoOperacion = "reactivar";
        botonesPanel.activarModoReactivar();
    }

    private void bloquearCamposRegistro() {
        registroPanel.txtRolCod.setEnabled(false);
        registroPanel.txtRolRol.setEnabled(false);
        registroPanel.txtRolNom.setEnabled(false);
        registroPanel.chkEstado.setEnabled(false);
    }

    private void desbloquearCamposRegistro() {
        registroPanel.txtRolCod.setEnabled(false); // sigue bloqueado
        registroPanel.txtRolRol.setEnabled(true);
        registroPanel.txtRolNom.setEnabled(true);
        registroPanel.chkEstado.setEnabled(true);
    }

    private void cancelarOperacion() {
        limpiarCampos();
        rolFlagAct = 0;
        modoOperacion = "";
        tablaPanel.tabla.clearSelection(); // deselecciona cualquier fila
    }

    private void salirDelPrograma() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que deseas salir del programa?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0); // Cierra la aplicación
        }
    }
}
