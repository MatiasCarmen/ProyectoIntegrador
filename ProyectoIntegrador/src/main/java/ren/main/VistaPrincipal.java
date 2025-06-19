/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matias papu
 */
package ren.main;

import vista.VistaClientes;
import vista.VistaUsuarios;
import vista.VistaAgenda;
import vista.VistaMesaCentral;
import vista.VistaActividadesPorCuenta;
import vista.VistaProductosInstaladosPorCuenta;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    private final JPanel panelContenedor;
    private final CardLayout cardLayout;

    // Identificadores de tarjetas
    private static final String TARJETA_CLIENTES    = "CLIENTES";
    private static final String TARJETA_USUARIOS    = "USUARIOS";
    private static final String TARJETA_AGENDA      = "AGENDA";
    private static final String TARJETA_MESA        = "MESA_CENTRAL";
    private static final String TARJETA_ACTIVIDADES = "ACTIVIDADES";
    private static final String TARJETA_PRODUCTOS   = "PRODUCTOS";

    // Panels embebidos
    private final VistaClientes clientesPanel;
    private final VistaUsuarios usuariosPanel;
    private final VistaAgenda agendaPanel;
    private final VistaMesaCentral mesaCentralPanel;
    private final VistaActividadesPorCuenta actividadesPanel;
    private final VistaProductosInstaladosPorCuenta productosPanel;

    public VistaPrincipal() {
        super("CRM Integrador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu lateral
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(0, 1, 0, 5));
        menu.setPreferredSize(new Dimension(200, 0));
        String[] items = {
            "Clientes", "Usuarios", "Agenda", "Mesa Central"
        };
        for (String name : items) {
            JButton btn = new JButton(name);
            btn.addActionListener(e -> {
                switch (name) {
                    case "Clientes"      -> showCard(TARJETA_CLIENTES);
                    case "Usuarios"      -> showCard(TARJETA_USUARIOS);
                    case "Agenda"        -> showCard(TARJETA_AGENDA);
                    case "Mesa Central"  -> showCard(TARJETA_MESA);
                }
            });
            menu.add(btn);
        }
        add(menu, BorderLayout.WEST);

        // Contenedor central con CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Instanciación de vistas
        clientesPanel      = new VistaClientes();
        usuariosPanel      = new VistaUsuarios();
        agendaPanel        = new VistaAgenda();
        mesaCentralPanel   = new VistaMesaCentral();
        actividadesPanel   = new VistaActividadesPorCuenta();
        productosPanel     = new VistaProductosInstaladosPorCuenta();

        // Registro de tarjetas
        panelContenedor.add(clientesPanel, TARJETA_CLIENTES);
        panelContenedor.add(usuariosPanel, TARJETA_USUARIOS);
        panelContenedor.add(agendaPanel,   TARJETA_AGENDA);
        panelContenedor.add(mesaCentralPanel, TARJETA_MESA);
        panelContenedor.add(actividadesPanel, TARJETA_ACTIVIDADES);
        panelContenedor.add(productosPanel,   TARJETA_PRODUCTOS);

        add(panelContenedor, BorderLayout.CENTER);

        // Mostrar inicialmente la vista de Clientes
        showCard(TARJETA_CLIENTES);
    }

    /**
     * Muestra la tarjeta con el nombre dado.
     */
    private void showCard(String tarjeta) {
        cardLayout.show(panelContenedor, tarjeta);
    }

    /**
     * Navega al módulo de Actividades + Productos para la cuenta dada.
     */
    public void abrirModuloCuenta(String idCuenta) {
        actividadesPanel.setCuenta(idCuenta);
        actividadesPanel.cargarActividades();
        productosPanel.setCuenta(idCuenta);
        productosPanel.cargarProductos();
        showCard(TARJETA_ACTIVIDADES);
    }

    /**
     * Método auxiliar para que desde los panels se muestre el panel de Productos.
     */
    public void mostrarProductos() {
        showCard(TARJETA_PRODUCTOS);
    }

    /**
     * Método auxiliar para que desde los panels se muestre el panel de Actividades.
     */
    public void mostrarActividades() {
        showCard(TARJETA_ACTIVIDADES);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vp = new VistaPrincipal();
            vp.setVisible(true);
        });
    }
}
