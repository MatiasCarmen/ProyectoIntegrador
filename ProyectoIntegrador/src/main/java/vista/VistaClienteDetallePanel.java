/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import entidades.Cliente;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal de detalle de cliente con pestañas.
 * Ahora recibe Cliente + idCuenta.
 */
public class VistaClienteDetallePanel extends JPanel {
    public VistaClienteDetallePanel(Cliente c, String idCuenta) {
        setLayout(new BorderLayout());

        // Header rojo
        JLabel header = new JLabel("Datos del Cliente: " + c.getNombres(), SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(Color.RED);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        // Pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Actividades", new VistaActividadesPorCuenta(idCuenta));
        tabs.addTab("Productos Instalados", new VistaProductosInstaladosPorCuenta(idCuenta));
        // … aquí podrías agregar más pestañas (Ofertas, Pedidos, etc.)
        add(tabs, BorderLayout.CENTER);
    }
}
