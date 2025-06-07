package Formularios;
import Controladores.*;
import Entidades.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

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
        usuarioMenu.add(new JMenuItem("Mi cuenta"));
        usuarioMenu.add(new JMenuItem("Cerrar sesión"));

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
        lblEmail = crearEtiquetaEditable("Email:", "");
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
            lblTelefono.setText("<html><b>Teléfono:</b><br>" + c1.getTelefono() + "</html>");
            lblEmail.setText("<html><b>Email:</b><br>" + c1.getCorreo() + "</html>");
            lblComuna.setText("<html><b>Comuna:</b><br>" + ComunasControlador.obtenerDescripcionPorId(c1.getIdComuna()) + "</html>");
            lblDireccion.setText("<html><b>Dirección:</b><br>" + c1.getDireccion() + "</html>");
            lblEdad.setText("<html><b>Edad:</b><br>" + c1.getEdad() + "</html>");
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

        panel.add(crearProducto("📺 TV Hogar", "TELEVISION"));
        panel.add(crearProducto("📶 Internet Hogar", "BANDA_ANCHA"));
        panel.add(crearProducto("📞 Telefonía Fija", "TELEFONIA"));
        panel.add(crearProducto("📱 Móvil", "MOVIL"));

        return panel;
    }

    private JPanel crearProducto(String nombre, String tipo) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Color.WHITE);

        JButton btnExpandir = new JButton("➤");
        btnExpandir.setPreferredSize(new Dimension(40, 40));
        btnExpandir.setFocusPainted(false);
        btnExpandir.setContentAreaFilled(false);
        btnExpandir.setBorderPainted(false);

        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Color.WHITE);
        fila.add(new JLabel(nombre), BorderLayout.CENTER);
        fila.add(btnExpandir, BorderLayout.WEST);
        contenedor.add(fila);

        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(new Color(245, 245, 245));
        panelProductos.setVisible(false);
        contenedor.add(panelProductos);

        btnExpandir.addActionListener(e -> {
            panelProductos.setVisible(!panelProductos.isVisible());
            btnExpandir.setText(panelProductos.isVisible() ? "▼" : "➤");
            revalidate();
            repaint();
        });

        try {
            ControladorPlanes controladorPlanes = new ControladorPlanes();
            Plan plan = controladorPlanes.obtenerPlanPorIdCuenta(idCuenta);
            if (plan != null && plan.getIdPlan() != null) {
                ArrayList<DetallePlan> detalles = new ControladorDetalle_planes().obtenerDetallesPlan(plan.getIdPlan());
                agregarProductosAlPanel(detalles, tipo, panelProductos);
            }

            Adicional adicional = new ControladorAdicionales().obtenerAdicionalPorIdCuenta(idCuenta);
            if (adicional != null && adicional.getIdAdicionales() != null) {
                ArrayList<DetalleAdicional> detalles = new ControladorDetalle_adicionales().obtenerDetallesAdicional(adicional.getIdAdicionales());
                agregarProductosAlPanel(detalles, tipo, panelProductos);
            }

            Descuento descuento = new ControladorDescuentos().obtenerDescuentoPorIdCuenta(idCuenta);
            if (descuento != null && descuento.getIdDescuentos() != null) {
                ArrayList<DetalleDescuento> detalles = new ControladorDetalle_descuentos().obtenerDetallesDescuento(descuento.getIdDescuentos());
                agregarProductosAlPanel(detalles, tipo, panelProductos);
            }
        } catch (Exception ex) {
            JLabel errorLabel = new JLabel("Error al cargar productos.");
            errorLabel.setForeground(Color.RED);
            panelProductos.add(errorLabel);
        }

        return contenedor;
    }

    private void agregarProductosAlPanel(ArrayList<?> detalles, String tipo, JPanel panelProductos) throws Exception {
        for (Object obj : detalles) {
            String idProducto = "";
            if (obj instanceof DetallePlan) idProducto = ((DetallePlan) obj).getIdProducto();
            if (obj instanceof DetalleAdicional) idProducto = ((DetalleAdicional) obj).getIdProducto();
            if (obj instanceof DetalleDescuento) idProducto = ((DetalleDescuento) obj).getIdProducto();

            Producto producto = new ControladorProductos().obtenerProductoPorId(idProducto);
            if (producto != null) {
                boolean coincideTipo = tipo.equalsIgnoreCase(producto.getTipo()) ||
                        (tipo.equalsIgnoreCase("MOVIL") &&
                                (producto.getTipo().equalsIgnoreCase("PREPAGO") ||
                                        producto.getTipo().equalsIgnoreCase("POSTPAGO")));

                if (coincideTipo) {
                    JPanel productoPanel = new JPanel(new BorderLayout());
                    productoPanel.setBackground(Color.WHITE);
                    productoPanel.setBorder(new CompoundBorder(
                            new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                            new EmptyBorder(5, 15, 5, 15)
                    ));
                    productoPanel.add(new JLabel(producto.getDescripcion()), BorderLayout.CENTER);
                    panelProductos.add(productoPanel);
                }
            }
        }
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
                String nuevoValor = JOptionPane.showInputDialog("Editar " + titulo, valor);
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