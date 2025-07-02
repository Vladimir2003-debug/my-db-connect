package ui.socio;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class RegistroSocioPanel extends JPanel {
    public JTextField txtSocCod, txtSocIden, txtSocCor, txtSocTipPro, txtSocCta,
                      txtSocDep, txtSocPro, txtSocDis;
    public JComboBox<String> comboSocEmp;
    public JComboBox<String> comboSocDat;
    public JDateChooser dateSocFecha;
    public JCheckBox chkEstado;

    public RegistroSocioPanel() {
        setBorder(BorderFactory.createTitledBorder("Registro de Socio"));
        setLayout(new GridBagLayout());

        txtSocCod = new JTextField(5); txtSocCod.setEditable(false);
        txtSocIden = new JTextField(15);
        txtSocCor = new JTextField(20);
        txtSocTipPro = new JTextField(15);
        txtSocCta = new JTextField(15);
        txtSocDep = new JTextField(10);
        txtSocPro = new JTextField(10);
        txtSocDis = new JTextField(10);
        comboSocEmp = new JComboBox<>();
        comboSocDat = new JComboBox<>();
        dateSocFecha = new JDateChooser();
        dateSocFecha.setDateFormatString("yyyy-MM-dd");
        chkEstado = new JCheckBox("Activo");

        int y = 0;
        add(new JLabel("SocCod:"), gbc(0, y)); add(txtSocCod, gbc(1, y++));
        add(new JLabel("SocIden:"), gbc(0, y)); add(txtSocIden, gbc(1, y++));
        add(new JLabel("SocCor:"), gbc(0, y)); add(txtSocCor, gbc(1, y++));
        add(new JLabel("SocTipPro:"), gbc(0, y)); add(txtSocTipPro, gbc(1, y++));
        add(new JLabel("SocCta:"), gbc(0, y)); add(txtSocCta, gbc(1, y++));
        add(new JLabel("SocDep:"), gbc(0, y)); add(txtSocDep, gbc(1, y++));
        add(new JLabel("SocPro:"), gbc(0, y)); add(txtSocPro, gbc(1, y++));
        add(new JLabel("SocDis:"), gbc(0, y)); add(txtSocDis, gbc(1, y++));
        add(new JLabel("SocEmp:"), gbc(0, y)); add(comboSocEmp, gbc(1, y++));
        add(new JLabel("SocDat:"), gbc(0, y)); add(comboSocDat, gbc(1, y++));
        add(new JLabel("SocFecha:"), gbc(0, y)); add(dateSocFecha, gbc(1, y++));
        add(new JLabel("Estado Registro:"), gbc(0, y)); add(chkEstado, gbc(1, y));
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}
