/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ren.main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import vista.Login;

import javax.swing.*;

public class main {

    //  Este método se llama desde Login al hacer login correcto
    public static main main;

    private JFrame frame;

    public static void main(String[] args) {
        // Estilo visual (FlatLaf)
        FlatLaf.setup(new FlatArcDarkIJTheme());

        SwingUtilities.invokeLater(() -> {
            main = new main();
            main.showLogin();  // Muestra el formulario de login
        });
    }

    public void showLogin() {
        if (frame != null) {
            frame.dispose();
        }

        frame = new JFrame("Sistema CRM - Iniciar Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new Login());
        frame.setVisible(true);
    }

    public void showMainForm() {
        if (frame != null) {
            frame.dispose();
        }

        frame = new JFrame("Sistema CRM - Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new MenuPrincipal());
        frame.setVisible(true);
    }
}
