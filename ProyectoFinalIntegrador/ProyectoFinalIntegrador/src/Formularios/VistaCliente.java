package Formularios;

import Controladores.ClienteControlador;
import Controladores.ComunasControlador;
import Controladores.ControladorCuentas_clientes;
import Entidades.Cliente;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class VistaCliente extends JFrame {

    private JPanel panelDatos;
    private JLabel lblNombre, lblApellidoPaterno, lblApellidoMaterno, lblRut, lblTelefono, lblEmail, lblComuna, lblDireccion, lblEdad;
    private String idCuenta;

    public VistaCliente(String idCuenta) {
        this.idCuenta = idCuenta;
        inicializarComponentes();
        cargarDatosCliente(idCuenta);
    }

    public VistaCliente() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("CRM - Vista Cliente");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(Color.WHITE);
        barra.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JPanel menuNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        menuNav.setBackground(Color.WHITE);
        menuNav.add(new JLabel("Claro Chile"));
        menuNav.add(new JButton("Buscar Clientes"));
        menuNav.add(new JButton("Pedidos"));
        menuNav.add(new JButton("Actividades"));
        menuNav.add(new JButton("Agenda"));
        menuNav.add(new JButton("Solicitudes"));

        JMenu usuarioMenu = new JMenu();
        usuarioMenu.setIcon(new ImageIcon("user_icon.png"));
        JMenuItem cuenta = new JMenuItem("Mi cuenta");
        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        usuarioMenu.add(cuenta);
        usuarioMenu.add(cerrarSesion);

        JMenuBar menuUsuario = new JMenuBar();
        menuUsuario.setBackground(Color.WHITE);
        menuUsuario.add(Box.createHorizontalGlue());
        menuUsuario.add(usuarioMenu);

        barra.add(menuNav, BorderLayout.WEST);
        barra.add(menuUsuario, BorderLayout.EAST);

        add(barra, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Datos del Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(Color.RED);
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(5, 10, 5, 10));
        panelCentral.add(titulo, BorderLayout.NORTH);

        panelDatos = new JPanel(new GridLayout(3, 3, 20, 15));
        panelDatos.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelDatos.setBackground(Color.WHITE);

        lblNombre = crearEtiquetaEditable("Nombre:", "");
        lblApellidoPaterno = crearEtiquetaEditable("Apellido Paterno:", "");
        lblApellidoMaterno = crearEtiquetaEditable("Apellido Materno:", "");
        lblRut = crearEtiquetaFija("RUT:", "");
        lblTelefono = crearEtiquetaEditable("Teléfono:", "");
        lblEmail = crearEtiquetaEditable("Correo Electrónico:", "");
        lblComuna = crearEtiquetaEditable("Comuna:", "");
        lblDireccion = crearEtiquetaEditable("Dirección:", "");
        lblEdad = crearEtiquetaEditable("Edad:", "");

        panelCentral.add(panelDatos, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Ofertas", new JPanel());
        tabs.addTab("Actividades", new JPanel());
        tabs.addTab("Productos Instalados", crearPanelProductos());
        tabs.addTab("Pedidos", new JPanel());
        tabs.addTab("Solicitudes", new JPanel());
        tabs.addTab("Perfil de Facturación", new JPanel());
        tabs.addTab("Agenda", new JPanel());

        add(tabs, BorderLayout.SOUTH);
    }

    private void cargarDatosCliente(String idCuenta) {
        if (idCuenta != null && !idCuenta.isEmpty()) {
            Cliente c1 = ClienteControlador.obtenerClientePorRut(ControladorCuentas_clientes.obtenerRutPorIdCuenta(idCuenta));
            lblNombre.setText("<html><b>Nombre:</b><br>" + c1.getNombres() + "</html>");
            lblApellidoPaterno.setText("<html><b>Apellido Paterno:</b><br>" + c1.getApellidoP() + "</html>");
            lblApellidoMaterno.setText("<html><b>Apellido Materno:</b><br>" + c1.getApellidoM() + "</html>");
            lblRut.setText("<html><b>RUT:</b><br>" + c1.getRut() + "</html>");
            lblTelefono.setText("<html><b>Teléfono:</b><br>" + Long.toString(c1.getTelefono()) + "</html>");
            lblEmail.setText("<html><b>Correo Electrónico:</b><br>" + c1.getCorreo() + "</html>");
            lblComuna.setText("<html><b>Comuna:</b><br>" + ComunasControlador.obtenerDescripcionPorId(c1.getIdComuna()) + "</html>");
            lblDireccion.setText("<html><b>Dirección:</b><br>" + c1.getDireccion() + "</html>");
            lblEdad.setText("<html><b>Edad:</b><br>" + String.valueOf(c1.getEdad()) + "</html>");
        }
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Productos Instalados");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(Color.RED);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(titulo);

        panel.add(crearProducto("📱 Móvil", "Plan 50GB", "Activo", "Descuento 20%", "Sin acceso streaming"));
        panel.add(crearProducto("📺 TV Hogar", "Claro TV HD", "Activo", "Incluye streaming", "Plataformas: Claro Video, HBO Max, Paramount+"));
        panel.add(crearProducto("📶 Internet Hogar", "300 Mbps", "Activo", "Descuento 10%", "Plataformas: Claro Video"));

        return panel;
    }

    private JPanel crearProducto(String tipo, String plan, String estado, String beneficio, String detalle) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(Color.WHITE);
        contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton btnExpandir = new JButton("➤");
        btnExpandir.setPreferredSize(new Dimension(40, 40));
        btnExpandir.setFocusPainted(false);
        btnExpandir.setContentAreaFilled(false);
        btnExpandir.setBorderPainted(false);

        JPanel fila = new JPanel(new GridLayout(1, 5));
        fila.setBackground(Color.WHITE);
        fila.add(new JLabel(tipo));
        fila.add(new JLabel(plan));
        fila.add(new JLabel(estado));
        fila.add(new JLabel(beneficio));
        fila.add(new JLabel(detalle));

        JPanel detallePanel = new JPanel();
        detallePanel.setBackground(new Color(245, 245, 245));
        detallePanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        detallePanel.setLayout(new BorderLayout());
        detallePanel.add(new JLabel("Detalles adicionales del servicio: " + plan), BorderLayout.CENTER);
        detallePanel.setVisible(false);

        btnExpandir.addActionListener(e -> detallePanel.setVisible(!detallePanel.isVisible()));

        JPanel filaConBoton = new JPanel(new BorderLayout());
        filaConBoton.setBackground(Color.WHITE);
        filaConBoton.add(btnExpandir, BorderLayout.WEST);
        filaConBoton.add(fila, BorderLayout.CENTER);

        contenedor.add(filaConBoton, BorderLayout.NORTH);
        contenedor.add(detallePanel, BorderLayout.CENTER);

        return contenedor;
    }

    private JLabel crearEtiquetaEditable(String titulo, String valor) {
        JLabel label = new JLabel("<html><b>" + titulo + "</b><br>" + valor + "</html>");
        label.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(10, 10, 10, 10)));
        label.setOpaque(true);
        label.setBackground(new Color(240, 240, 255));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String texto = label.getText();
                String valorActual = texto.replaceAll("(?s)<html><b>.*?</b><br>", "").replaceAll("</html>", "");
                String nuevoValor = JOptionPane.showInputDialog("Editar " + titulo, valorActual);
                if (nuevoValor != null && !nuevoValor.trim().isEmpty()) {
                    label.setText("<html><b>" + titulo + "</b><br>" + nuevoValor + "</html>");
                }
            }
        });

        panelDatos.add(label);
        return label;
    }

    private JLabel crearEtiquetaFija(String titulo, String valor) {
        JLabel label = new JLabel("<html><b>" + titulo + "</b><br>" + valor + "</html>");
        label.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY), new EmptyBorder(10, 10, 10, 10)));
        label.setOpaque(true);
        label.setBackground(new Color(230, 230, 250));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.TOP);

        panelDatos.add(label);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaCliente("CUENTA123").setVisible(true));
    }
}
