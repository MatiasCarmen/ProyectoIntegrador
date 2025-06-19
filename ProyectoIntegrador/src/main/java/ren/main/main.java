/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ren.main;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import vista.Login;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        FlatArcDarkIJTheme.setup();

        SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Sistema CRM - Iniciar Sesión");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(400, 350);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setContentPane(new Login(loginFrame)); // 🔧 importante pasar el frame
            loginFrame.setVisible(true);
        });
    }
}
