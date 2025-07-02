package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.tasa.RegistroTasaPanel;
import ui.tasa.TablaTasaPanel;

import javax.swing.*;
import java.sql.*;

public class TasaController {
    private RegistroTasaPanel registroPanel;
    private TablaTasaPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    public TasaController(RegistroTasaPanel registro, TablaTasaPanel tabla, BotonesPanel botones) {
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
            String sql = "SELECT * FROM tasa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("TasCod"),
                        rs.getString("TasIden"),
                        rs.getString("TasDes"),
                        rs.getFloat("TasTas"),          // ahora float
                        rs.getString("TasPlaz"),
                        rs.getDate("TasIniFecha"),      // fecha como java.sql.Date
                        rs.getDate("TasFinFecha")
                };
                tablaPanel.modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar tasas: " + e.getMessage());
        }
    }

    private void initListeners() {
        botonesPanel.btnAdicionar.addActionListener(e -> agregarTasa());
        botonesPanel.btnModificar.addActionListener(e -> cargarDatosSeleccionados());
        botonesPanel.btnActualizar.addActionListener(e -> actualizarTasa());
        botonesPanel.btnEliminar.addActionListener(e -> eliminarTasa());
        botonesPanel.btnCancelar.addActionListener(e -> cancelarOperacion());
        botonesPanel.btnSalir.addActionListener(e -> salirDelPrograma());
        botonesPanel.btnInactivar.addActionListener(e -> inactivar());
        botonesPanel.btnReactivar.addActionListener(e -> reactivar());
    }

    private void agregarTasa() {
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO tasa (TasCod, TasIden, TasDes, TasTas, TasPlaz, TasIniFecha, TasFinFecha) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            // 1) Obtener código de producto (por defecto 1 si no hay o inválido)
            int codProducto = 1;
            try {
                String input = registroPanel.txtTasCod.getText().trim();
                if (!input.isEmpty()) {
                    codProducto = Integer.parseInt(input);
                }
            } catch (NumberFormatException ex) {
                codProducto = 1;
            }

            // 2) Si no existe el producto en BD, fuerza al producto 1
            String sqlCheck = "SELECT COUNT(*) FROM producto WHERE ProdCod = ?";
            try (PreparedStatement chk = conn.prepareStatement(sqlCheck)) {
                chk.setInt(1, codProducto);
                ResultSet rs = chk.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    codProducto = 1;
                }
            }

            // 3) Setear parámetros
            ps.setInt(1, codProducto);
            ps.setString(2, registroPanel.txtTasIden.getText().trim());
            ps.setString(3, registroPanel.txtTasDes.getText().trim());
            ps.setFloat(4, Float.parseFloat(registroPanel.txtTasTas.getText().trim()));  // ahora float
            ps.setString(5, registroPanel.txtTasPlaz.getText().trim());

            java.util.Date ini = registroPanel.dateIni.getDate();
            java.util.Date fin = registroPanel.dateFin.getDate();
            if (ini == null || fin == null) {
                JOptionPane.showMessageDialog(null, "Seleccione ambas fechas.");
                return;
            }
            ps.setDate(6, new java.sql.Date(ini.getTime()));
            ps.setDate(7, new java.sql.Date(fin.getTime()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tasa insertada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: tasa inválida.");
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para modificar.");
            return;
        }

        registroPanel.txtTasCod.setText(tablaPanel.modelo.getValueAt(fila, 0).toString());
        registroPanel.txtTasIden.setText(tablaPanel.modelo.getValueAt(fila, 1).toString());
        registroPanel.txtTasDes.setText(tablaPanel.modelo.getValueAt(fila, 2).toString());
        registroPanel.txtTasTas.setText(tablaPanel.modelo.getValueAt(fila, 3).toString());
        registroPanel.txtTasPlaz.setText(tablaPanel.modelo.getValueAt(fila, 4).toString());

        // Fechas
        registroPanel.dateIni.setDate((Date) tablaPanel.modelo.getValueAt(fila, 5));
        registroPanel.dateFin.setDate((Date) tablaPanel.modelo.getValueAt(fila, 6));

        flagAct = 1;
        modoOperacion = "modificar";
    }

    private void actualizarTasa() {
        if (flagAct == 0) {
            JOptionPane.showMessageDialog(null, "No hay operación activa para actualizar.");
            return;
        }

        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "UPDATE tasa SET TasIden=?, TasDes=?, TasTas=?, TasPlaz=?, "
                       + "TasIniFecha=?, TasFinFecha=? WHERE TasCod=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtTasIden.getText().trim());
            ps.setString(2, registroPanel.txtTasDes.getText().trim());
            ps.setFloat(3, Float.parseFloat(registroPanel.txtTasTas.getText().trim()));
            ps.setString(4, registroPanel.txtTasPlaz.getText().trim());

            java.util.Date ini = registroPanel.dateIni.getDate();
            java.util.Date fin = registroPanel.dateFin.getDate();
            if (ini == null || fin == null) {
                JOptionPane.showMessageDialog(null, "Seleccione ambas fechas.");
                return;
            }
            ps.setDate(5, new java.sql.Date(ini.getTime()));
            ps.setDate(6, new java.sql.Date(fin.getTime()));
            ps.setInt(7, Integer.parseInt(registroPanel.txtTasCod.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tasa actualizada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarTasa() {
        int fila = tablaPanel.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.");
            return;
        }

        int cod = Integer.parseInt(tablaPanel.modelo.getValueAt(fila, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Deseas eliminar esta tasa?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionJDBC.getConexion()) {
                String sql = "DELETE FROM tasa WHERE TasCod = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cod);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Tasa eliminada correctamente.");
                cargarDatosDesdeBD();
                limpiarCampos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        registroPanel.txtTasCod.setText("");
        registroPanel.txtTasIden.setText("");
        registroPanel.txtTasDes.setText("");
        registroPanel.txtTasTas.setText("");
        registroPanel.txtTasPlaz.setText("");
        registroPanel.dateIni.setDate(null);
        registroPanel.dateFin.setDate(null);
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
