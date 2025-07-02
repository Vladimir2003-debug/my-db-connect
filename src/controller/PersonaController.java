package controller;

import database.ConexionJDBC;
import ui.BotonesPanel;
import ui.persona.RegistroPersonaPanel;
import ui.persona.TablaPersonaPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class PersonaController {
    private RegistroPersonaPanel registroPanel;
    private TablaPersonaPanel tablaPanel;
    private BotonesPanel botonesPanel;
    private int flagAct = 0;
    private String modoOperacion = "";

    private Map<Integer, String> mapCoo = new HashMap<>();
    private Map<Integer, String> mapDat = new HashMap<>();

    public PersonaController(RegistroPersonaPanel registro, TablaPersonaPanel tabla, BotonesPanel botones) {
        this.registroPanel = registro;
        this.tablaPanel = tabla;
        this.botonesPanel = botones;

        cargarCooperativas();
        cargarDatos();
        cargarDatosDesdeBD();
        botonesPanel.activarModoNormal();
        initListeners();
    }

    private void cargarCooperativas() {
        mapCoo.clear();
        registroPanel.comboPerCoo.removeAllItems();
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT CooCod, CooNom FROM cooperativa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("CooCod");
                String nombre = rs.getString("CooNom");
                mapCoo.put(id, nombre);
                registroPanel.comboPerCoo.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cooperativas: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        mapDat.clear();
        registroPanel.comboPerDat.removeAllItems();
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT DatCod, DatNom FROM dato";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("DatCod");
                String nombre = rs.getString("DatNom");
                mapDat.put(id, nombre);
                registroPanel.comboPerDat.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarDatosDesdeBD() {
        tablaPanel.modelo.setRowCount(0);
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "SELECT * FROM persona";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idCoo = rs.getInt("PerCoo");
                int idDat = rs.getInt("PerDat");

                Object[] fila = {
                    rs.getInt("PerCod"),
                    rs.getString("PerIden"),
                    rs.getString("PerCor"),
                    rs.getString("PerFot"),
                    mapCoo.getOrDefault(idCoo, "Desconocido"),
                    mapDat.getOrDefault(idDat, "Desconocido"),
                    rs.getDate("PerFecha")
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

        registroPanel.btnSeleccionarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Seleccionar imagen");
                int result = chooser.showOpenDialog(registroPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selected = chooser.getSelectedFile();
                    String destino = "src/img/" + selected.getName();
                    try {
                        Files.copy(selected.toPath(), Paths.get(destino));
                        registroPanel.setRutaFoto(destino);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al copiar la imagen: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void agregarPersona() {
        try (Connection conn = ConexionJDBC.getConexion()) {
            String sql = "INSERT INTO persona (PerIden, PerCor, PerFot, PerCoo, PerDat, PerFecha) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, registroPanel.txtPerIden.getText().trim());
            ps.setString(2, registroPanel.txtPerCor.getText().trim());
            ps.setString(3, registroPanel.getRutaFoto());

            int idCoo = obtenerClaveSeleccionada(mapCoo, (String) registroPanel.comboPerCoo.getSelectedItem());
            int idDat = obtenerClaveSeleccionada(mapDat, (String) registroPanel.comboPerDat.getSelectedItem());

            ps.setInt(4, idCoo);
            ps.setInt(5, idDat);

            Date fecha = registroPanel.datePerFecha.getDate();
            if (fecha == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fecha válida");
                return;
            }
            ps.setDate(6, new java.sql.Date(fecha.getTime()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona insertada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
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
        registroPanel.setRutaFoto((String) tablaPanel.modelo.getValueAt(fila, 3));
        registroPanel.comboPerCoo.setSelectedItem(tablaPanel.modelo.getValueAt(fila, 4).toString());
        registroPanel.comboPerDat.setSelectedItem(tablaPanel.modelo.getValueAt(fila, 5).toString());
        registroPanel.datePerFecha.setDate((Date) tablaPanel.modelo.getValueAt(fila, 6));

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
            ps.setString(3, registroPanel.getRutaFoto());

            int idCoo = obtenerClaveSeleccionada(mapCoo, (String) registroPanel.comboPerCoo.getSelectedItem());
            int idDat = obtenerClaveSeleccionada(mapDat, (String) registroPanel.comboPerDat.getSelectedItem());

            ps.setInt(4, idCoo);
            ps.setInt(5, idDat);

            Date fecha = registroPanel.datePerFecha.getDate();
            ps.setDate(6, new java.sql.Date(fecha.getTime()));
            ps.setInt(7, Integer.parseInt(registroPanel.txtPerCod.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona actualizada correctamente.");
            cargarDatosDesdeBD();
            limpiarCampos();
            flagAct = 0;
            modoOperacion = "";
            botonesPanel.activarModoNormal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
        }
    }

    private int obtenerClaveSeleccionada(Map<Integer, String> map, String valor) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(valor)) {
                return entry.getKey();
            }
        }
        return -1;
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
        registroPanel.comboPerCoo.setSelectedIndex(-1);
        registroPanel.comboPerDat.setSelectedIndex(-1);
        registroPanel.datePerFecha.setDate(null);
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
