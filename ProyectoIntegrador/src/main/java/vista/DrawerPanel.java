/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Menú lateral muy simple: delega navegación al contenedor.
 */

//clase posiblemente borrada ya no sirve creo
public class DrawerPanel extends JPanel {
    public DrawerPanel(Consumer<String> onNavigate) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));

        String[] txt = {"Clientes", "Actividades", "Productos", "Salir"};
        for (String t : txt) {
            JButton b = new JButton(t);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(200, 40));
            add(Box.createVerticalStrut(12));
            add(b);

            switch (t) {
                case "Salir"       -> b.addActionListener(e -> System.exit(0));
                case "Clientes"    -> b.addActionListener(e -> onNavigate.accept("clientes"));
                case "Actividades" -> b.addActionListener(e -> onNavigate.accept("actividades"));
                case "Productos"   -> b.addActionListener(e -> onNavigate.accept("productos"));
            }
        }
        add(Box.createVerticalGlue());
    }
}
