/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author mathi
 */


import com.formdev.flatlaf.FlatClientProperties;
import controladores.ControladorUsuarios;
import entidades.Usuario;


import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import ren.main.main;

import java.awt.*;

public class Login extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
    private JLabel lblEstado;

    private final ControladorUsuarios controlador = new ControladorUsuarios(); // ✅ DAO

    public Login() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Recordarme");
        cmdLogin = new JButton("Ingresar");
        lblEstado = new JLabel("", JLabel.CENTER);

        // Estilos visuales
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 35 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "[light]background:darken(@background,3%);" +
                        "[dark]background:lighten(@background,3%)");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                        "[dark]background:lighten(@background,10%);" +
                        "margin:4,6,4,6;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingrese su ID de usuario");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingrese su contraseña");

        JLabel lbTitle = new JLabel("Bienvenido");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JLabel description = new JLabel("Ingrese sus credenciales para acceder");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                        "[dark]foreground:darken(@foreground,30%)");

        // Acción del botón de login
        cmdLogin.addActionListener(e -> realizarLogin());

        // Agregar componentes
        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Usuario"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña"), "gapy 8");
        panel.add(txtPassword);
        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 10");
        panel.add(lblEstado, "gapy 5");
        add(panel);
    }

    /**
     * ✅ LÓGICA DE LOGIN
     * Este método hace:
     * - Validación de campos
     * - Llamado al DAO
     * - Manejo del resultado
     */
    private void realizarLogin() {
        String usuario = txtUsername.getText().trim();
        String clave = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            mostrarEstado("⚠️ Completa todos los campos", Color.RED);
            return;
        }

        Usuario u = controlador.validarLogin(usuario, clave);

        if (u != null) {
            mostrarEstado("✅ Bienvenido, " + u.getNombres(), new Color(0, 153, 51));
            JOptionPane.showMessageDialog(this,
                    "Acceso concedido a " + u.getNombres() + " (" + u.getArea() + ")",
                    "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            main.main.showMainForm(); // Cambia esto si tu app abre otra ventana
        } else {
            mostrarEstado("❌ Usuario o contraseña incorrectos", Color.RED);
        }
    }

    private void mostrarEstado(String mensaje, Color color) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(color);
    }
}
