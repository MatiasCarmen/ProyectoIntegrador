/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ren.main;

import javax.swing.*;
import java.awt.*;

/**
 * ✅ Vista post-login
 * ✅ Parte del patrón MVC (Vista)
 * ✅ SRP: esta clase solo representa el menú general
 */
public class MenuPrincipal extends JPanel {

    public MenuPrincipal() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bienvenido al Sistema CRM", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Puedes agregar botones de navegación aquí
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 20, 20));

        JButton btnClientes = new JButton("Clientes");
        JButton btnUsuarios = new JButton("Usuarios");
        JButton btnPedidos = new JButton("Pedidos");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                main.main.showLogin();
            }
        });

        panelBotones.add(btnClientes);
        panelBotones.add(btnUsuarios);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
    }
}
