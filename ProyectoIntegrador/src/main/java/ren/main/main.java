package ren.main;

/**
 *
 * @author matias papu
 */
import com.formdev.flatlaf.FlatLightLaf;
import entidades.Usuario;
import vista.Login;
import vista.tema.TemaAdministrador;

import javax.swing.*;
import java.awt.*;
import vista.VistaClienteDetallePanel;

public class main {
    public static Usuario logeado;

    public static VistaClienteDetallePanel ventanap;

    public static void main(String[] args) {
        // mejora aspectos waos
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar FlatLaf y FlatLaf Extras para SVG y temas avanzados
                FlatLightLaf.setup();
            

                // Configuración global de la UI
                UIManager.put("Button.arc", 12);
                UIManager.put("Component.arc", 12);
                UIManager.put("ProgressBar.arc", 12);
                UIManager.put("TextComponent.arc", 12);
                UIManager.put("Component.focusWidth", 1);
                UIManager.put("Component.innerFocusWidth", 1);
                UIManager.put("Component.borderWidth", 1);

                // Colores corporativos de kotecta sipi
                Color rojo_claro = new Color(237, 28, 36);
                Color rojo_hover = new Color(200, 16, 46);

                // Aplicar colores y estilos a las tablitas
                UIManager.put("Button.default.background", rojo_claro);
                UIManager.put("Button.default.foreground", Color.WHITE);
                UIManager.put("Button.default.hoverBackground", rojo_hover);
                UIManager.put("TextField.placeholderForeground", new Color(150, 150, 150));
                UIManager.put("PasswordField.placeholderForeground", new Color(150, 150, 150));
                UIManager.put("Component.focusColor", rojo_claro);
                UIManager.put("Component.borderColor", new Color(220, 220, 220));
                UIManager.put("Table.selectionBackground", new Color(255, 235, 235));
                UIManager.put("Table.selectionForeground", rojo_claro);

                // Crear y mostrar el frame de login, esto hace que se ejecute el login
                JFrame loginFrame = new JFrame();
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Login loginPanel = new Login(loginFrame);
                loginFrame.add(loginPanel);
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}