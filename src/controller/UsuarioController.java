package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.usuario.RegistroUsuarioPanel;
import ui.usuario.TablaUsuarioPanel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private Map<String, Integer> mapaCoo = new HashMap<>();

    public UsuarioController(RegistroUsuarioPanel registro, TablaUsuarioPanel tabla, BotonesPanel botones) {
        this.registroPanel = registro;
        this.tablaPanel = tabla;
        this.botonesPanel = botones;

        cargarDatosDesdeBD();
        cargarRolesEnCombo();
        cargarCooperativasEnCombo();

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

    private void cargarCooperativasEnCombo() {
        registroPanel.comboUsuCoo.removeAllItems();
        mapaCoo.clear();

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT CooCod, CooNom FROM cooperativa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int cod = rs.getInt("CooCod");
                String nom = rs.getString("CooNom");
                mapaCoo.put(nom, cod);
                registroPanel.comboUsuCoo.addItem(nom);
            }

            registroPanel.comboUsuCoo.setSelectedIndex(-1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cooperativas: " + e.getMessage());
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
        botonesPanel.btnInactivar.addActionListener(e -> inactivarUsuario());
        botonesPanel.btnReactivar.addActionListener(e -> reactivarUsuario());
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void agregarUsuario() {
        String ide = registroPanel.txtUsuIde.getText().trim();
        String usu = registroPanel.txtUsuUsu.getText().trim();
        String pas = new String(registroPanel.txtUsuPas.getPassword());
        String pasHash = hashSHA256(pas);
        String cooNombre = (String) registroPanel.comboUsuCoo.getSelectedItem();
        Integer cooCod = mapaCoo.get(cooNombre);

        if (ide.isEmpty() || usu.isEmpty() || pas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete los campos obligatorios: Ide, Usuario y Contraseña.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO usuario (UsuIde, UsuUsu, UsuPas, UsuRol, UsuEmp) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ide);
            ps.setString(2, usu);
            ps.setString(3, pasHash);

            String rolNombre = (String) registroPanel.comboUsuRol.getSelectedItem();
            Integer rolCod = mapaRol.get(rolNombre);

            if (rolCod == null)
                ps.setNull(4, Types.INTEGER);
            else
                ps.setInt(4, rolCod);

            if (cooCod == null)
                ps.setNull(5, Types.INTEGER);
            else
                ps.setInt(5, cooCod);

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
        int cooCod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 6).toString());
        for (Map.Entry<String, Integer> entry : mapaCoo.entrySet()) {
            if (entry.getValue().equals(cooCod)) {
                registroPanel.comboUsuCoo.setSelectedItem(entry.getKey());
                break;
            }
        }

        usuFlagAct = 1;
        modoOperacion = "modificar";
        botonesPanel.activarModoAdicionar();

    }

    private void actualizarUsuario() {
        if (usuFlagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        int cod = Integer.parseInt(registroPanel.txtUsuCod.getText());
        String sql = "UPDATE usuario SET UsuIde=?, UsuUsu=?, UsuPas=?, UsuRol=?, UsuEmp=?, UsuCoo=? WHERE UsuCod=?";

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

            String cooNombre = (String) registroPanel.comboUsuCoo.getSelectedItem();
            Integer cooCod = mapaCoo.get(cooNombre);

            if (cooCod == null)
                ps.setNull(6, Types.INTEGER);
            else
                ps.setInt(6, cooCod);

            ps.setInt(6, cod);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            usuFlagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();

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

    private void inactivarUsuario() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para inactivar.");
            return;
        }

        registroPanel.txtUsuCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtUsuIde.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtUsuUsu.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtUsuPas.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.comboUsuRol.setSelectedItem(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.chkEstado.setSelected(false); // marcar como inactivo

        bloquearCamposRegistro();
        usuFlagAct = 1;
        modoOperacion = "inactivar";
        botonesPanel.activarModoInactivar();
    }

    private void reactivarUsuario() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para reactivar.");
            return;
        }

        registroPanel.txtUsuCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtUsuIde.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtUsuUsu.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtUsuPas.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.comboUsuRol.setSelectedItem(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.chkEstado.setSelected(true); // marcar como activo

        desbloquearCamposRegistro();
        usuFlagAct = 1;
        modoOperacion = "reactivar";
        botonesPanel.activarModoReactivar();
    }

    private void bloquearCamposRegistro() {
        registroPanel.txtUsuCod.setEnabled(false);
        registroPanel.txtUsuIde.setEnabled(false);
        registroPanel.txtUsuUsu.setEnabled(false);
        registroPanel.txtUsuPas.setEnabled(false);
        registroPanel.comboUsuRol.setEnabled(false);
        registroPanel.comboUsuCoo.setEnabled(false);
        registroPanel.chkEstado.setEnabled(false);
    }

    private void desbloquearCamposRegistro() {
        registroPanel.txtUsuCod.setEnabled(false); // Sigue deshabilitado
        registroPanel.txtUsuIde.setEnabled(true);
        registroPanel.txtUsuUsu.setEnabled(true);
        registroPanel.txtUsuPas.setEnabled(true);
        registroPanel.comboUsuRol.setEnabled(true);
        registroPanel.comboUsuCoo.setEnabled(true);
        registroPanel.chkEstado.setEnabled(true);
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
        registroPanel.comboUsuCoo.setSelectedIndex(-1);
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
