/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final JTabbedPane tabs = new JTabbedPane();
    private final HashMap<String, Component> loadedTabs = new HashMap<>();

    public MainFrame() {
        setTitle("CRM Integrador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(33, 33, 33));
        menu.setPreferredSize(new Dimension(200, 0));

        String[] items = {
            "Clientes", "Crear Cliente",
            "Actividades", "Crear Actividad",
            "Productos", "Agenda", "Mesa Central", "Salir"
        };
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.addActionListener(e -> abrirVista(item));
            menu.add(btn);
            menu.add(Box.createVerticalStrut(5));
        }

        add(menu, BorderLayout.WEST);
        add(tabs, BorderLayout.CENTER);
    }

    private void abrirVista(String name) {
        if (name.equals("Salir")) {
            System.exit(0);
            return;
        }
        if (loadedTabs.containsKey(name)) {
            tabs.setSelectedComponent(loadedTabs.get(name));
            return;
        }

        JPanel vista = switch (name) {
            case "Clientes"        -> new VistaClientes();
            case "Crear Cliente"   -> new VistaAgregarCliente();
            case "Actividades"     -> {
                // Ya no pasamos null en el constructor
                VistaActividadesPorCuenta vac = new VistaActividadesPorCuenta();
                // Si tienes el idCuenta a mano, setéalo aquí:
                // vac.setCuenta(idCuentaSeleccionada);
                // vac.cargarActividades();
                yield vac;
            }
            case "Crear Actividad" -> new VistaAgregarActividad();
            case "Productos"       -> {
                VistaProductosInstaladosPorCuenta vpc = new VistaProductosInstaladosPorCuenta();
                // vpc.setCuenta(idCuentaSeleccionada);
                // vpc.cargarProductos();
                yield vpc;
            }
            case "Agenda"          -> new VistaAgenda();
            default                -> new JPanel();
        };

        // Agrega la pestaña con cierre
        JPanel wrapper = new JPanel(new BorderLayout());
        JButton close = new JButton("×");
        close.setBorderPainted(false);
        close.setFocusPainted(false);
        close.setContentAreaFilled(false);
        close.setForeground(Color.RED);
        close.addActionListener(e -> {
            tabs.remove(wrapper);
            loadedTabs.remove(name);
        });
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        header.setOpaque(false);
        header.add(new JLabel(name));
        header.add(close);

        wrapper.add(header, BorderLayout.NORTH);
        wrapper.add(vista, BorderLayout.CENTER);

        tabs.addTab(name, vista);
        tabs.setTabComponentAt(tabs.indexOfComponent(vista), header);
        loadedTabs.put(name, vista);
        tabs.setSelectedComponent(vista);
    }
}
