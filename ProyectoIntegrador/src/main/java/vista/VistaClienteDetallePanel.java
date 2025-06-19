/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// VistaClienteDetallePanel.java
package vista;

import entidades.Cliente;
import controladores.ComunasControlador;
import javax.swing.*;
import java.awt.*;

/**
 * Panel de detalle de cliente con pestañas para Actividades y Productos.
 */
public class VistaClienteDetallePanel extends JPanel {

    private final Cliente cliente;
    private final ComunasControlador comunaCtrl = new ComunasControlador();

    public VistaClienteDetallePanel(Cliente cliente) {
        this.cliente = cliente;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        // Pestaña de Actividades
        VistaActividadesPorCuenta vac = new VistaActividadesPorCuenta();
        vac.setCuenta(cliente.getRut());         // o el idCuenta si lo tienes
        vac.cargarActividades();
        tabs.addTab("Actividades", vac);

        // Pestaña de Productos
        VistaProductosInstaladosPorCuenta vpc = new VistaProductosInstaladosPorCuenta();
        vpc.setCuenta(cliente.getRut());         // o el idCuenta correspondiente
        vpc.cargarProductos();
        tabs.addTab("Productos", vpc);

        add(tabs, BorderLayout.CENTER);
    }
}
