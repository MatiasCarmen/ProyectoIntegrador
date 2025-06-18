/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ren.main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import vista.VistaClientes;

/**
 *
 * @author matias papu
 */
/**
 * Vista principal del CRM: contiene la barra lateral y panel central dinámico.
 * 
 *  Vista post-login
 * Parte del patrón MVC (Vista)
 * SRP: esta clase solo representa el menú general
 */
 
public class VistaPrincipal extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(VistaPrincipal.class.getName());

    private CardLayout cardLayout;
    private JPanel panelContenido;
    private Map<String, JPanel> modulos = new HashMap<>();

    public VistaPrincipal() {
        LOGGER.info("Creando VistaPrincipal");
        initUI();
    }

    private void initUI() {
        LOGGER.info("Inicializando UI de VistaPrincipal");
        setLayout(new BorderLayout());

        // Barra lateral
        JPanel barraLateral = new JPanel(new MigLayout("wrap 1, insets 10", "[grow,center]"));
        barraLateral.setPreferredSize(new Dimension(200, getHeight()));
        barraLateral.putClientProperty(FlatClientProperties.STYLE,
                "background: darken(@background, 10%);");

        String[] nombres = {"Dashboard", "Clientes", "Usuarios", "Actividades", "Agenda", "Cerrar Sesión"};
        for (String nombre : nombres) {
            JButton btn = new JButton(nombre);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(160, 40));
            btn.addActionListener(e -> {
                LOGGER.info("Botón seleccionado: " + nombre);
                navegar(nombre);
            });
            barraLateral.add(btn, "growx, wrap");
        }

        // Panel central con CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);

        // Módulos iniciales (vacíos o con placeholder)
        modulos.put("Dashboard", new JPanel());
        modulos.put("Clientes", new VistaClientes());
        modulos.put("Usuarios", new JPanel());
        modulos.put("Actividades", new JPanel());
        modulos.put("Agenda", new JPanel());

        // Agregar cada módulo al contenedor
        modulos.forEach((key, panel) -> {
            LOGGER.info("Agregando módulo: " + key);
            panelContenido.add(panel, key);
        });

        // Layout general
        add(barraLateral, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // Mostrar módulo por defecto
        cardLayout.show(panelContenido, "Dashboard");
        LOGGER.info("Módulo por defecto: Dashboard mostrado");
    }

    private void navegar(String nombre) {
        LOGGER.info("Navegando a: " + nombre);
        if ("Cerrar Sesión".equals(nombre)) {
            LOGGER.info("Cerrando sesión");
            ren.main.main.main.showLogin();
        } else {
            cardLayout.show(panelContenido, nombre);
            LOGGER.info("Módulo mostrado: " + nombre);
        }
    }
}