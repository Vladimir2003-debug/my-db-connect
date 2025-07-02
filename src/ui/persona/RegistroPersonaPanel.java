package ui.persona;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;

public class RegistroPersonaPanel extends JPanel {
    public JTextField txtPerCod, txtPerIden, txtPerCor;
    public JCheckBox chkEstado;
    public JComboBox<String> comboPerCoo;  // Cambiado a String
    public JComboBox<String> comboPerDat;  // Cambiado a String
    public JDateChooser datePerFecha;
    public JButton btnSeleccionarFoto;
    public JLabel lblFoto;

    private String rutaFotoSeleccionada;

    public RegistroPersonaPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Persona"));
        setLayout(new GridBagLayout());

        txtPerCod = new JTextField(5);
        txtPerCod.setEditable(false);
        txtPerIden = new JTextField(15);
        txtPerCor = new JTextField(20);

        comboPerCoo = new JComboBox<>();
        comboPerDat = new JComboBox<>();
        datePerFecha = new JDateChooser();
        datePerFecha.setDateFormatString("yyyy-MM-dd");

        chkEstado = new JCheckBox("Activo");
        btnSeleccionarFoto = new JButton("Seleccionar Foto");
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(120, 120));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFoto.setHorizontalAlignment(JLabel.CENTER);
        lblFoto.setVerticalAlignment(JLabel.CENTER);

        int y = 0;
        add(new JLabel("PerCod:"), gbc(0, y));
        add(txtPerCod, gbc(1, y++));
        add(new JLabel("PerIden:"), gbc(0, y));
        add(txtPerIden, gbc(1, y++));
        add(new JLabel("PerCor:"), gbc(0, y));
        add(txtPerCor, gbc(1, y++));
        add(new JLabel("PerCoo:"), gbc(0, y));
        add(comboPerCoo, gbc(1, y++));
        add(new JLabel("PerDat:"), gbc(0, y));
        add(comboPerDat, gbc(1, y++));
        add(new JLabel("PerFecha:"), gbc(0, y));
        add(datePerFecha, gbc(1, y++));
        add(new JLabel("Estado Registro:"), gbc(0, y));
        add(chkEstado, gbc(1, y++));
        add(new JLabel("Foto:"), gbc(0, y));
        add(btnSeleccionarFoto, gbc(1, y++));
        add(lblFoto, gbc(1, y++));
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public void setRutaFoto(String ruta) {
        this.rutaFotoSeleccionada = ruta;
        if (ruta != null && !ruta.isEmpty()) {
            lblFoto.setIcon(
                    new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        } else {
            lblFoto.setIcon(null);
        }
    }

    public String getRutaFoto() {
        return rutaFotoSeleccionada;
    }

    public void limpiarFoto() {
        setRutaFoto(null);
    }
}
