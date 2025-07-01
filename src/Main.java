import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf; // o FlatDarkLaf, etc.
import ui.AppMainForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Aplica el look and feel antes de iniciar la UI
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Inicia la aplicación de forma segura
        SwingUtilities.invokeLater(() -> {
            new AppMainForm().setVisible(true);
        });
    }
}