package vista.drawer;

/**
 *
 * @author mathi
 */
import vista.drawer.component.SimpleDrawerBuilder;
import vista.drawer.component.footer.SimpleFooterData;
import vista.drawer.component.header.SimpleHeaderData;
import vista.drawer.component.menu.MenuEvent;
import vista.drawer.component.menu.SimpleMenuOption;
import ren.main.VistaPrincipal;
import utils.PermisoUtils;
import entidades.Usuario;

import javax.swing.*;
import java.awt.*;
import vista.Login;
import vista.VistaNuevaActividad;
import vista.VistaClientes;

public class MyDrawerBuilder extends SimpleDrawerBuilder {
    private final VistaPrincipal mainFrame;
    private static final int LOGO_WIDTH = 48;
    private static final int LOGO_HEIGHT = 48;
    private static final Color PRIMARY_COLOR = new Color(237, 28, 36); // Rojo Claro
    private static final Color SECONDARY_COLOR = new Color(200, 16, 46); // Rojo oscuro para gradiente
    private static final Color HOVER_COLOR = new Color(255, 229, 229); // Rosa claro para hover

    public MyDrawerBuilder(VistaPrincipal mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        // Cargar y escalar el logo
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagenes/logo_claro.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);

        return new SimpleHeaderData()
                .setIcon(new ImageIcon(scaledImage))
                .setTitle("Claro CRM")
                .setDescription("Sistema de Gestión")
                .setStyle("background:linear-gradient(180deg, #ED1C24 0%, #C8102E 100%);" +
                        "titleColor:#FFFFFF;" +
                        "descriptionColor:#FFFFFF;" +
                        "titleFont:bold-20;" +
                        "descriptionFont:semibold-12;" +
                        "padding:15,20,15,20");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        // Obtener opciones de menú según el rol del usuario
        Usuario usuario = ren.main.main.logeado;
        String[][] menus = PermisoUtils.obtenerOpcionesMenu(usuario);

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIconScale(0.8f)
                .addMenuEvent((action, index, subIndex) -> {
                    if (mainFrame == null)
                        return;

                    String menuText = menus[index][0];
                    if (!menuText.startsWith("~")) { // No procesar categorías
                        switch (menuText) {
                            case "Inicio" -> mainFrame.mostrarPanel(VistaPrincipal.TARJETA_INICIO);
                            case "Lista de Clientes" -> {
                                // Crear una nueva instancia de VistaClientes en modo lista simple
                                VistaClientes vistaLista = new VistaClientes(true);
                                mainFrame.mostrarVistaListaClientes(vistaLista);
                            }
                            case "Buscar Cliente" -> mainFrame.mostrarPanel(VistaPrincipal.TARJETA_CLIENTES);
                            case "Agregar Cliente" -> mainFrame.mostrarPanel(VistaPrincipal.TARJETA_AGREGAR_CLIENTE);
                            case "Usuarios" -> {
                                // Validar permisos antes de mostrar
                                if (PermisoUtils.esAdministrador(usuario) || PermisoUtils.esSupervisor(usuario)) {
                                    mainFrame.mostrarPanel(VistaPrincipal.TARJETA_USUARIOS);
                                } else {
                                    JOptionPane.showMessageDialog(mainFrame,
                                            "No tienes permisos para acceder a la gestión de usuarios.",
                                            "Acceso Denegado",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            case "Crear Usuario" -> {
                                // Validar permisos antes de mostrar
                                if (PermisoUtils.esAdministrador(usuario) || PermisoUtils.esSupervisor(usuario)) {
                                    mainFrame.mostrarPanel(VistaPrincipal.TARJETA_CREAR_USUARIO);
                                } else {
                                    JOptionPane.showMessageDialog(mainFrame,
                                            "No tienes permisos para crear usuarios.",
                                            "Acceso Denegado",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            case "Nueva Actividad" -> {
                                String idCuenta = JOptionPane.showInputDialog(mainFrame,
                                        "Ingrese el ID de la cuenta para la nueva actividad:");
                                if (idCuenta != null && !idCuenta.trim().isEmpty()) {
                                    VistaNuevaActividad dialog = new VistaNuevaActividad(mainFrame, idCuenta.trim());
                                    dialog.setLocationRelativeTo(mainFrame);
                                    dialog.setVisible(true);
                                } else if (idCuenta != null) {
                                    JOptionPane.showMessageDialog(mainFrame,
                                            "Debe ingresar un ID de cuenta válido para crear una actividad.");
                                }
                            }
                            case "Lista Actividades" -> mainFrame.mostrarPanel(VistaPrincipal.TARJETA_ACTIVIDADES);
                            case "Agenda" ->
                                mainFrame.mostrarPanel(VistaPrincipal.TARJETA_AGENDA);

                            case "Cerrar Sesión" -> {
                                int confirm = JOptionPane.showConfirmDialog(
                                        mainFrame,
                                        "¿Está seguro que desea cerrar sesión?",
                                        "Confirmar Cierre de Sesión",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    mainFrame.dispose();
                                    // Crear y mostrar el login
                                    JFrame loginFrame = new JFrame("Login - Claro CRM");
                                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    Login loginPanel = new Login(loginFrame);
                                    loginFrame.add(loginPanel);
                                    loginFrame.setLocationRelativeTo(null);
                                    loginFrame.setVisible(true);
                                }
                            }
                        }
                    }
                })
                .setStyle("background:#FFFFFF;" +
                        "selectedBackground:#ED1C24;" +
                        "selectedForeground:#FFFFFF;" +
                        "hoverBackground:#FFE5E5;" +
                        "hoverForeground:#ED1C24;" +
                        "foreground:#333333;" +
                        "menuColor:#666666;" +
                        "font:Segoe UI-bold-13;" +
                        "menuIconSpace:10;" +
                        "menuHeight:35;" +
                        "selectionArc:10");
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("Konecta Claro CRM")
                .setDescription("Versión 1.0.0")
                .setStyle("background:#F8F8F8;" +
                        "titleColor:#ED1C24;" +
                        "titleFont:bold-13;" +
                        "descriptionColor:#666666;" +
                        "descriptionFont:regular-11;" +
                        "padding:15,20,15,20");
    }

    @Override
    public int getDrawerWidth() {
        return 280;
    }

    @Override
    public int getHeaderHeight() {
        return 120;
    }

    @Override
    public boolean isHeaderGradient() {
        return true;
    }

    @Override
    public Color getDrawerBackground() {
        return Color.WHITE;
    }

    public boolean isDrawerResizable() {
        return false;
    }
}
