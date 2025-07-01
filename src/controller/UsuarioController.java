package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.usuario.RegistroUsuarioPanel;
import ui.usuario.TablaUsuarioPanel;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class UsuarioController {
    private RegistroUsuarioPanel registroPanel;
    private TablaUsuarioPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int usuFlagAct = 0;
    private String modoOperacion = "";
    private Map<String, Integer> mapaRol = new HashMap<>();

    public UsuarioController(RegistroUsuarioPanel registro, TablaUsuarioPanel tabla, BotonesPanel botones) {
        this.registroPanel = registro;
        this.tablaPanel = tabla;
        this.botonesPanel = botones;

        cargarDatosDesdeBD();
        cargarRolesEnCombo();
        initListeners();
        botonesPanel.activarModoNormal();
    }

    private void cargarDatosDesdeBD() {
        tablaPanel.modelo.setRowCount(0);
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT UsuCod, UsuIde, UsuUsu, UsuPas, UsuRol, UsuEmp FROM usuario";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("UsuCod"),
                        rs.getString("UsuIde"),
                        rs.getString("UsuUsu"),
                        rs.getString("UsuPas"),
                        rs.getInt("UsuRol"),
                        rs.getString("UsuEmp")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void cargarRolesEnCombo() {
        registroPanel.comboUsuRol.removeAllItems();
        mapaRol.clear(); // Limpia el mapa

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT RolCod, RolRol FROM rol";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int rolCod = rs.getInt("RolCod");
                String rolNombre = rs.getString("RolRol");
                mapaRol.put(rolNombre, rolCod);
                registroPanel.comboUsuRol.addItem(rolNombre);
            }

            registroPanel.comboUsuRol.setSelectedIndex(-1); // ninguno seleccionado
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar roles: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarUsuario());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarUsuario());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarUsuario());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
    }

    private void agregarUsuario() {
        String ide = registroPanel.txtUsuIde.getText().trim();
        String usu = registroPanel.txtUsuUsu.getText().trim();
        String pas = new String(registroPanel.txtUsuPas.getPassword());
        String emp = registroPanel.txtUsuEmp.getText().trim();
        String rol = (String) registroPanel.comboUsuRol.getSelectedItem();

        if (ide.isEmpty() || usu.isEmpty() || pas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete los campos obligatorios: Ide, Usuario y Contraseña.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO usuario (UsuIde, UsuUsu, UsuPas, UsuRol, UsuEmp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ide);
            ps.setString(2, usu);
            ps.setString(3, pas);

            String rolNombre = (String) registroPanel.comboUsuRol.getSelectedItem();
            Integer rolCod = mapaRol.get(rolNombre);

            if (rolCod == null)
                ps.setNull(4, Types.INTEGER);
            else
                ps.setInt(4, rolCod);

            if (emp.isEmpty())
                ps.setNull(5, Types.VARCHAR);
            else
                ps.setString(5, emp);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario insertado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + e.getMessage());
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar.");
            return;
        }

        registroPanel.txtUsuCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtUsuIde.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtUsuUsu.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtUsuPas.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.comboUsuRol.setSelectedItem(String.valueOf(tablaPanel.modelo.getValueAt(fila, 4)));
        registroPanel.txtUsuEmp.setText(
                tablaPanel.modelo.getValueAt(fila, 5) != null ? tablaPanel.modelo.getValueAt(fila, 5).toString() : "");

        usuFlagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarUsuario() {
        if (usuFlagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        int cod = Integer.parseInt(registroPanel.txtUsuCod.getText());
        String sql = "UPDATE usuario SET UsuIde=?, UsuUsu=?, UsuPas=?, UsuRol=?, UsuEmp=? WHERE UsuCod=?";

        try (Connection conn = ConexionJDBC.getConexion();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, registroPanel.txtUsuIde.getText());
            ps.setString(2, registroPanel.txtUsuUsu.getText());
            ps.setString(3, new String(registroPanel.txtUsuPas.getPassword()));

            String rol = (String) registroPanel.comboUsuRol.getSelectedItem();
            if (rol == null || rol.isEmpty())
                ps.setNull(4, Types.INTEGER);
            else
                ps.setInt(4, Integer.parseInt(rol));

            String emp = registroPanel.txtUsuEmp.getText();
            if (emp.isEmpty())
                ps.setNull(5, Types.VARCHAR);
            else
                ps.setString(5, emp);

            ps.setInt(6, cod);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            usuFlagAct = 0;
            modoOperacion = "";

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int cod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro que deseas eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM usuario WHERE UsuCod=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
            }
        }
    }

    private void cancelarOperacion() {
        limpiarCampos();
        usuFlagAct = 0;
        modoOperacion = "";
        tablaPanel.tabla.clearSelection();
    }

    private void limpiarCampos() {
        registroPanel.txtUsuCod.setText("");
        registroPanel.txtUsuIde.setText("");
        registroPanel.txtUsuUsu.setText("");
        registroPanel.txtUsuPas.setText("");
        registroPanel.comboUsuRol.setSelectedIndex(-1);
        registroPanel.txtUsuEmp.setText("");
    }

    private void salirDelPrograma() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que deseas salir?",
                "Salir del programa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
