package ui.cooperativa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroCooperativaPanel extends JPanel {
    public JTextField txtCooCod, txtCooIde, txtCooNom, txtCooSig, txtCooDir, txtCooTel, txtCooCor, txtCooSlo;
    public JComboBox<String> comboCooUsu;
    public JCheckBox chkEstado;
    private String rutaCooLog;
    private JButton btnSeleccionarCooLog;
    private JLabel lblRutaCooLog;

    public RegistroCooperativaPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Cooperativa"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = crearGBC();

        txtCooCod = new JTextField(5); txtCooCod.setEditable(false);
        txtCooIde = new JTextField(10);
        txtCooNom = new JTextField(20);
        txtCooSig = new JTextField(10);
        txtCooDir = new JTextField(20);
        txtCooTel = new JTextField(9);
        txtCooCor = new JTextField(20);
        txtCooSlo = new JTextField(20);
        comboCooUsu = new JComboBox<>();
        chkEstado = new JCheckBox("Activo");

        lblRutaCooLog = new JLabel("Sin CooLog seleccionado");
        btnSeleccionarCooLog = new JButton("Seleccionar CooLog");
        btnSeleccionarCooLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    rutaCooLog = fileChooser.getSelectedFile().getAbsolutePath();
                    lblRutaCooLog.setText(rutaCooLog);
                }
            }
        });

        int y = 0;
        add(new JLabel("CooCod:"), gbc(0, y)); add(txtCooCod, gbc(1, y++));
        add(new JLabel("CooIde:"), gbc(0, y)); add(txtCooIde, gbc(1, y++));
        add(new JLabel("CooNom:"), gbc(0, y)); add(txtCooNom, gbc(1, y++));
        add(new JLabel("CooSig:"), gbc(0, y)); add(txtCooSig, gbc(1, y++));
        add(new JLabel("CooDir:"), gbc(0, y)); add(txtCooDir, gbc(1, y++));
        add(new JLabel("CooTel:"), gbc(0, y)); add(txtCooTel, gbc(1, y++));
        add(new JLabel("CooCor:"), gbc(0, y)); add(txtCooCor, gbc(1, y++));
        add(new JLabel("CooSlo:"), gbc(0, y)); add(txtCooSlo, gbc(1, y++));
        add(new JLabel("CooUsu:"), gbc(0, y)); add(comboCooUsu, gbc(1, y++));
        add(new JLabel("CooLog:"), gbc(0, y)); add(lblRutaCooLog, gbc(1, y++));
        add(btnSeleccionarCooLog, gbc(1, y++));
        add(new JLabel("Estado Registro:"), gbc(0, y)); add(chkEstado, gbc(1, y));
    }

    private GridBagConstraints crearGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = crearGBC();
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    public String getRutaCooLog() {
        return rutaCooLog;
    }

    public void setRutaCooLog(String rutaCooLog) {
        this.rutaCooLog = rutaCooLog;
        lblRutaCooLog.setText(rutaCooLog != null ? rutaCooLog : "Sin CooLog seleccionado");
    }
}
