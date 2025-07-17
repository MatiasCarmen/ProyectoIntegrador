/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matias papu
 */
package ren.main;

import com.formdev.flatlaf.FlatLightLaf;
import entidades.Cliente;
import entidades.Usuario;
import utils.PermisoUtils;
import vista.*;
import vista.componentes.UserHeaderPanel;
import vista.drawer.MyDrawerBuilder;
import vista.componentes.LoadingSpinner;
import vista.util.UIHelper;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    // Identificadores de tarjetas
    public static final String TARJETA_INICIO = "INICIO";
    public static final String TARJETA_CLIENTES = "CLIENTES";
    public static final String TARJETA_AGREGAR_CLIENTE = "AGREGAR_CLIENTE";
    public static final String TARJETA_DETALLE_CLIENTE = "DETALLE_CLIENTE";
    public static final String TARJETA_USUARIOS = "USUARIOS";
    public static final String TARJETA_CREAR_USUARIO = "CREAR_USUARIO";
    public static final String TARJETA_NUEVA_ACTIVIDAD = "NUEVA_ACTIVIDAD";
    public static final String TARJETA_ACTIVIDADES = "ACTIVIDADES";
    public static final String TARJETA_AGENDA = "AGENDA";
    public static final String TARJETA_PRODUCTOS = "PRODUCTOS";

    private final CardLayout cardLayout;
    private final JPanel panelContenedor;
    private final JComponent menuLateral;
    private final LoadingSpinner loadingSpinner;
    private final JPanel overlayPanel;
    private final UserHeaderPanel userHeaderPanel;

    // Panels embebidos
    private final VistaClientes clientesPanel;
    private final VistaUsuarios usuariosPanel;
    private final VistaAgenda agendaPanel;
    private final VistasActividades actividadesPanel;
    private final VistaProductosInstaladosPorCuenta productosPanel;

    private JPanel currentPanel;
    private static final int ANIMATION_DURATION = 300; // milisegundos
    private static final Color SUCCESS_COLOR = new Color(46, 125, 50);
    private static final Color ERROR_COLOR = new Color(198, 40, 40);

    public VistaPrincipal() {
        // Establecer look and feel moderno
        FlatLightLaf.setup();

        // Verificar que hay un usuario logueado
        if (main.logeado == null) {
            JOptionPane.showMessageDialog(this,
                    "Error: No hay usuario logueado",
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Color.WHITE);

        overlayPanel = new JPanel(new GridBagLayout());
        overlayPanel.setBackground(new Color(255, 255, 255, 200));
        overlayPanel.setVisible(false);

        // Configurar loading spinner
        loadingSpinner = new LoadingSpinner();
        overlayPanel.add(loadingSpinner);

        // Inicializar los paneles con efecto de elevación
        clientesPanel = new VistaClientes();
        usuariosPanel = new VistaUsuarios();
        agendaPanel = new VistaAgenda();
        actividadesPanel = new VistasActividades();
        productosPanel = new VistaProductosInstaladosPorCuenta();

        // Crear el drawer con efecto de sombra
        MyDrawerBuilder builder = new MyDrawerBuilder(this);
        menuLateral = builder.build();

        // Crear el header del usuario
        userHeaderPanel = new UserHeaderPanel();

        // Configurar la ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Claro CRM - " + main.logeado.getNombres());
        setMinimumSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel principal con efecto de elevación
        JPanel mainPanel = new UIHelper.ElevatedPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.WHITE);

        // Agregar overlay al panel principal
        mainPanel.add(overlayPanel, BorderLayout.CENTER);

        // Panel de contenido con margen y sombra
        JPanel contentPanel = new UIHelper.ElevatedPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        contentPanel.setBackground(Color.WHITE);

        // Agregar header del usuario en la parte superior
        contentPanel.add(userHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(panelContenedor, BorderLayout.CENTER);

        // Agregar todos los paneles al CardLayout (solo los permitidos)
        agregarPanelesSegunPermisos();

        // Agregar los paneles al frame principal
        mainPanel.add(menuLateral, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Mostrar panel inicial según el rol (sin validación de permisos)
        mostrarPanelInicialSegunRol();
    }

    /**
     * Agrega solo los paneles permitidos según el rol del usuario
     */
    private void agregarPanelesSegunPermisos() {
        Usuario usuario = main.logeado;

        // Paneles básicos para todos
        panelContenedor.add(new VistaInicio(), TARJETA_INICIO);
        panelContenedor.add(clientesPanel, TARJETA_CLIENTES);
        panelContenedor.add(actividadesPanel, TARJETA_ACTIVIDADES);
        panelContenedor.add(agendaPanel, TARJETA_AGENDA);
        panelContenedor.add(productosPanel, TARJETA_PRODUCTOS);

        try {
            Class<?> vistaAgregarClienteClass = Class.forName("vista.VistaAgregarCliente");
            panelContenedor.add((JPanel) vistaAgregarClienteClass.getDeclaredConstructor().newInstance(),
                    TARJETA_AGREGAR_CLIENTE);
        } catch (Exception e) {
            System.out.println("VistaAgregarCliente no encontrada");
        }

        // Paneles solo para administradores y supervisores
        if (PermisoUtils.esAdministrador(usuario) || PermisoUtils.esSupervisor(usuario)) {
            panelContenedor.add(usuariosPanel, TARJETA_USUARIOS);
            panelContenedor.add(new VistaCrearUsuario(), TARJETA_CREAR_USUARIO);

        }

        // Paneles de actividades para todos
        panelContenedor.add(new VistasActividades(), TARJETA_NUEVA_ACTIVIDAD);
    }

    /**
     * Muestra el panel inicial según el rol del usuario (sin validación de
     * permisos)
     */
    private void mostrarPanelInicialSegunRol() {
        Usuario usuario = main.logeado;

        if (PermisoUtils.esAdministrador(usuario) || PermisoUtils.esSupervisor(usuario)) {
            cardLayout.show(panelContenedor, TARJETA_INICIO);
        } else {
            // Usuarios comunes van directo a clientes
            cardLayout.show(panelContenedor, TARJETA_CLIENTES);
        }

        // Refrescar notificaciones
        userHeaderPanel.refrescarNotificaciones();
    }

    // Mejorar el cambio entre paneles con animación y validación de permisos
    public void mostrarPanel(String nombrePanel) {
        // Validar permisos antes de mostrar el panel (solo para navegación manual)
        if (!PermisoUtils.tienePermisoVista(main.logeado, nombrePanel)) {
            JOptionPane.showMessageDialog(this,
                    "No tienes permisos para acceder a esta vista.",
                    "Acceso Denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            showLoading();

            // Simular carga de datos
            Timer timer = new Timer(500, e -> {
                cardLayout.show(panelContenedor, nombrePanel);
                panelContenedor.revalidate();
                panelContenedor.repaint();
                hideLoading();

                // Refrescar notificaciones al cambiar de panel
                userHeaderPanel.refrescarNotificaciones();

                System.out.println("Panel " + nombrePanel + " cargado ✅");
                ((Timer) e.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    private void showLoading() {
        overlayPanel.setVisible(true);
        loadingSpinner.startAnimation();
    }

    private void hideLoading() {
        loadingSpinner.stopAnimation();
        overlayPanel.setVisible(false);
    }

    @Override
    public void setContentPane(Container contentPane) {
        SwingUtilities.invokeLater(() -> {
            super.setContentPane(contentPane);
            contentPane.revalidate();
            contentPane.repaint();
            System.out.println("ContentPane actualizado y revalidado ✅");
        });
    }

    public void initUI() {
        SwingUtilities.invokeLater(() -> {
            // Panel principal con BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
            mainPanel.setBackground(Color.WHITE);

            // Panel lateral (drawer)
            System.out.println("Antes de build(): " + menuLateral);
            JPanel drawerPanel = new JPanel(new BorderLayout());
            drawerPanel.setPreferredSize(new Dimension(280, 0));
            drawerPanel.add(menuLateral, BorderLayout.CENTER);
            drawerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
            System.out.println(
                    "Drawer creado: " + menuLateral.getClass() + ", prefSize=" + menuLateral.getPreferredSize());

            // Panel para el contenido con margen
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.add(panelContenedor, BorderLayout.CENTER);

            mainPanel.add(drawerPanel, BorderLayout.WEST);
            mainPanel.add(contentPanel, BorderLayout.CENTER);

            setContentPane(mainPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
            System.out.println("Drawer revalidated & painted ✅");
        });
    }

    // Métodos para actualizar paneles específicos
    public void actualizarTablaClientes() {
        if (clientesPanel != null) {
            clientesPanel.actualizarTabla();
        }
    }

    public void actualizarTablaUsuarios() {
        if (usuariosPanel != null) {
            usuariosPanel.actualizarTabla();
        }
    }

    public void actualizarAgenda() {
        if (agendaPanel != null) {
            agendaPanel.cargarAgenda();
        }
    }

    public void actualizarActividades() {
        if (actividadesPanel != null) {
            actividadesPanel.recargarTabla();
        }
    }

    public void actualizarProductos() {
        if (productosPanel != null) {
            productosPanel.actualizarTabla();
        }
    }

    public void showDetalleCliente(Cliente cliente, String idCuenta) {
        if (cliente != null) {
            SwingUtilities.invokeLater(() -> {
                // Crear el panel de detalle de cliente
                VistaClienteDetallePanel detallePanel = new VistaClienteDetallePanel(cliente, idCuenta);

                // Agregar al CardLayout si no existe
                panelContenedor.add(detallePanel, TARJETA_DETALLE_CLIENTE);

                // Mostrar el panel
                cardLayout.show(panelContenedor, TARJETA_DETALLE_CLIENTE);
                panelContenedor.revalidate();
                panelContenedor.repaint();

                System.out.println("Panel de detalle de cliente cargado ✅");
            });
        }
    }

    public void showAgregarActividades(String idCuenta) {
        if (!idCuenta.equals("")) {
            SwingUtilities.invokeLater(() -> {
                VistaNuevaActividad dialog = new VistaNuevaActividad(this, idCuenta);
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            });
        }
    }

    public void mostrarPanelAgregarActividad(String idCuenta) {
        if (!idCuenta.equals("")) {
            SwingUtilities.invokeLater(() -> {
                VistaNuevaActividad dialog = new VistaNuevaActividad(this, idCuenta);
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            });
        }
    }

    public void mostrarPanelEditarActividad(String idActividad) {
        if (!idActividad.equals("")) {
            SwingUtilities.invokeLater(() -> {
                VistaEditarActividad dialog = new VistaEditarActividad(this, idActividad);
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            });
        }
    }

    public void mostrarProductos() {
        mostrarPanel(TARJETA_PRODUCTOS);
        if (productosPanel != null) {
            productosPanel.actualizarTabla();
        }
    }

    public void mostrarActividades() {
        mostrarPanel(TARJETA_ACTIVIDADES);
        if (actividadesPanel != null) {
            actividadesPanel.recargarTabla();
        }
    }

    public void mostrarVistaListaClientes(VistaClientes vistaLista) {
        SwingUtilities.invokeLater(() -> {
            // Agregar la nueva vista al CardLayout (sin eliminar las otras)
            panelContenedor.add(vistaLista, "LISTA_CLIENTES");

            // Mostrar la vista
            cardLayout.show(panelContenedor, "LISTA_CLIENTES");
            panelContenedor.revalidate();
            panelContenedor.repaint();

            System.out.println("Vista Lista de Clientes cargada ✅");
        });
    }
}
