/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mathi
 */
package vista;

import controladores.*;
import entidades.*;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import ren.main.VistaPrincipal;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel de detalle de cliente con pestañas para Actividades, Agenda y
 * Productos.
 */
public class VistaClienteDetallePanel extends JPanel {

    private final Cliente cliente;
    private final String idCuenta;
    private final ComunasControlador comunaCtrl = new ComunasControlador();
    private final ClienteControlador clienteCtrl = new ClienteControlador();

    // Campos de información del cliente
    private final JTextField txtRut = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JComboBox<Comuna> cboComuna = new JComboBox<>();

    public VistaClienteDetallePanel(Cliente cliente, String idCuenta) {
        this.cliente = cliente;
        this.idCuenta = idCuenta;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con información del cliente
        JPanel infoPanel = crearPanelInformacion();
        add(infoPanel, BorderLayout.NORTH);

        // Panel central con pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Actividades", crearPanelActividades());
        tabs.addTab("Agenda", crearPanelAgenda());
        tabs.addTab("Productos", crearPanelProductos());
        add(tabs, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel botonesPanel = crearPanelBotones();
        add(botonesPanel, BorderLayout.SOUTH);
    }

    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new MigLayout("wrap 4, insets 10", "[right][grow,fill][right][grow,fill]", "[]10[]"));
        panel.setBorder(BorderFactory.createTitledBorder("Información del Cliente"));
        panel.setBackground(new Color(248, 249, 250));

        configurarCampo(txtRut, "RUT");
        configurarCampo(txtCorreo, "Correo");
        configurarCampo(txtNombres, "Nombres");
        configurarCampo(txtApellidoP, "Apellido Paterno");
        configurarCampo(txtApellidoM, "Apellido Materno");
        configurarCampo(txtTelefono, "Teléfono");
        configurarCampo(txtEdad, "Edad");
        configurarCampo(txtDireccion, "Dirección");

        // Cargar comunas
        List<Comuna> comunas = comunaCtrl.listarComunas();
        for (Comuna c : comunas)
            cboComuna.addItem(c);

        // Agregar campos al panel
        panel.add(new JLabel("RUT:"));
        panel.add(txtRut);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Nombres:"));
        panel.add(txtNombres);
        panel.add(new JLabel("Apellido Paterno:"));
        panel.add(txtApellidoP);
        panel.add(new JLabel("Apellido Materno:"));
        panel.add(txtApellidoM);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Comuna:"));
        panel.add(cboComuna, "span 3");

        // Cargar datos del cliente
        if (cliente != null) {
            cargarDatosCliente(cliente);
            txtRut.setEditable(false);
        }

        return panel;
    }

    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = { "ID", "Tipo", "Fecha Creación", "Fecha Cierre", "Descripción", "IdUsuario" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new Color(237, 28, 36, 50));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Actividades del Cliente"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Cargar actividades
        if (idCuenta != null) {
            List<Actividad> actividades = new ActividadesControlador().obtenerActividadesPorCuenta(idCuenta);
            for (Actividad a : actividades) {
                modelo.addRow(new Object[] {
                        a.getIdActividad(), a.getTipo(), a.getFechaCreacion(), a.getFechaCierre(), a.getDescripcion(),
                        a.getIdUsuario()
                });
            }
        }

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAgregarActividad = new JButton("➕ Agregar Actividad");
        JButton btnEditarActividad = new JButton("✏️ Editar Actividad");

        // Estilizar botones
        btnAgregarActividad.setBackground(new Color(46, 125, 50));
        btnAgregarActividad.setForeground(Color.WHITE);
        btnEditarActividad.setBackground(new Color(33, 150, 243));
        btnEditarActividad.setForeground(Color.WHITE);

        btnAgregarActividad.addActionListener(e -> {
            VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getWindowAncestor(this);
            if (vp != null) {
                vp.mostrarPanelAgregarActividad(idCuenta);
            }
        });

        btnEditarActividad.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
                String idActividad = tabla.getValueAt(filaSeleccionada, 0).toString();
                Actividad act = new ActividadesControlador().obtenerPorId(idActividad);

                if (act != null && "PENDIENTE".equalsIgnoreCase(act.getResolucion())) {
                    VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getWindowAncestor(this);
                    if (vp != null) {
                        vp.mostrarPanelEditarActividad(idActividad);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Solo se pueden editar actividades en estado PENDIENTE.",
                            "No editable", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una actividad para editar.", "Sin selección",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        panelBotones.add(btnAgregarActividad);
        panelBotones.add(btnEditarActividad);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAgenda() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = { "IdActividad", "Telefono", "Lugar", "Fecha" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new Color(237, 28, 36, 50));

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Agenda del Cliente"));
        panel.add(scrollPane, BorderLayout.CENTER);

        if (idCuenta != null) {
            List<MesaCentral> todos = new ControladorMesa_central().listarTodosPorIdCuenta(idCuenta);
            for (MesaCentral m : todos) {
                Actividad a = new ActividadesControlador().obtenerPorId(m.getIdActividad());
                modelo.addRow(new Object[] {
                        m.getIdActividad(), m.getTelefono(), m.getLugar(), a.getFechaCreacion()
                });
            }
        }

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtFiltro = new JTextField(15);
        JComboBox<String> comboCampo = new JComboBox<>(new String[] { "LUGAR", "TELEFONO" });
        JButton btnFiltrar = new JButton("🔍 Filtrar");
        JButton btnLimpiar = new JButton("🔄 Limpiar");

        btnFiltrar.setBackground(new Color(33, 150, 243));
        btnFiltrar.setForeground(Color.WHITE);
        btnLimpiar.setBackground(new Color(158, 158, 158));
        btnLimpiar.setForeground(Color.WHITE);

        btnFiltrar.addActionListener(e -> {
            String campo = comboCampo.getSelectedItem().toString();
            String valor = txtFiltro.getText().trim();
            if (!valor.isEmpty()) {
                List<MesaCentral> filtrados;
                if (campo.equalsIgnoreCase("TELEFONO")) {
                    try {
                        filtrados = new ControladorMesa_central().filtrarPorCampoYValor(idCuenta, campo,
                                Long.parseLong(valor));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Ingrese un número válido para teléfono.");
                        return;
                    }
                } else {
                    filtrados = new ControladorMesa_central().filtrarPorCampoYValorLike(idCuenta, campo, valor);
                }

                modelo.setRowCount(0);
                for (MesaCentral m : filtrados) {
                    Actividad a = new ActividadesControlador().obtenerPorId(m.getIdActividad());
                    modelo.addRow(new Object[] {
                            m.getIdActividad(), m.getTelefono(), m.getLugar(), a.getFechaCreacion()
                    });
                }
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtFiltro.setText("");
            modelo.setRowCount(0);
            if (idCuenta != null) {
                List<MesaCentral> todos = new ControladorMesa_central().listarTodosPorIdCuenta(idCuenta);
                for (MesaCentral m : todos) {
                    Actividad a = new ActividadesControlador().obtenerPorId(m.getIdActividad());
                    modelo.addRow(new Object[] {
                            m.getIdActividad(), m.getTelefono(), m.getLugar(), a.getFechaCreacion()
                    });
                }
            }
        });

        panelFiltros.add(new JLabel("Campo:"));
        panelFiltros.add(comboCampo);
        panelFiltros.add(new JLabel("Valor:"));
        panelFiltros.add(txtFiltro);
        panelFiltros.add(btnFiltrar);
        panelFiltros.add(btnLimpiar);

        panel.add(panelFiltros, BorderLayout.SOUTH);
        return panel;
    }

    private JScrollPane crearPanelProductos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Productos Instalados"));

        try {
            String claseCuenta = CuentasClienteControlador.obtenerCuentaClientePorIdCuenta(idCuenta).getClase();
            ArrayList<String> cuentasServicio = new ArrayList<>();

            if (claseCuenta.equalsIgnoreCase("CLIENTE")) {
                String rut = CuentasClienteControlador.obtenerRutPorIdCuenta(idCuenta);
                cuentasServicio = (ArrayList<String>) CuentasClienteControlador.obtenerCuentasServicioPorRut(rut);
            } else if (claseCuenta.equalsIgnoreCase("FACTURACION")) {
                String idServicio = new CuentasClienteControlador().obtenerIdCuentaServicioDesdeFacturacion(idCuenta);
                if (idServicio != null)
                    cuentasServicio.add(idServicio);
            } else {
                cuentasServicio.add(idCuenta);
            }

            for (String cuenta : cuentasServicio) {
                panel.add(crearProducto("📺 TV Hogar", "TELEVISION", cuenta));
                panel.add(crearProducto("📶 Internet Hogar", "BANDA_ANCHA", cuenta));
                panel.add(crearProducto("📞 Telefonía Fija", "TELEFONIA", cuenta));
                panel.add(crearProducto("📱 Móvil", "MOVIL", cuenta));
            }
        } catch (Exception ex) {
            JLabel error = new JLabel("Error al cargar productos: " + ex.getMessage());
            error.setForeground(Color.RED);
            panel.add(error);
        }

        return new JScrollPane(panel);
    }

    private JPanel crearProducto(String nombre, String tipo, String idCuentaServicio) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lbl = new JLabel(nombre);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(51, 51, 51));
        contenedor.add(lbl);

        JPanel productos = new JPanel();
        productos.setLayout(new BoxLayout(productos, BoxLayout.Y_AXIS));
        productos.setBackground(new Color(245, 245, 245));
        productos.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        try {
            Plan plan = new ControladorPlanes().obtenerPlanPorIdCuenta(idCuentaServicio);
            if (plan != null) {
                for (DetallePlan d : new ControladorDetalle_planes().obtenerDetallesPlan(plan.getIdPlan())) {
                    agregarProducto(d.getIdProducto(), tipo, productos, idCuenta, d.getCosto());
                }
            }
            Adicional adicional = new ControladorAdicionales().obtenerAdicionalPorIdCuenta(idCuentaServicio);
            if (adicional != null) {
                for (DetalleAdicional d : new ControladorDetalle_adicionales()
                        .obtenerDetallesAdicional(adicional.getIdAdicionales())) {
                    agregarProducto(d.getIdProducto(), tipo, productos, idCuenta, d.getCosto());
                }
            }
            Descuento descuento = new ControladorDescuentos().obtenerDescuentoPorIdCuenta(idCuentaServicio);
            if (descuento != null) {
                for (DetalleDescuento d : new ControladorDetalle_descuentos()
                        .obtenerDetallesDescuento(descuento.getIdDescuentos())) {
                    agregarProducto(d.getIdProducto(), tipo, productos, idCuenta, d.getCosto());
                }
            }
        } catch (Exception ex) {
            JLabel error = new JLabel("Error al cargar productos.");
            error.setForeground(Color.RED);
            productos.add(error);
        }

        contenedor.add(productos);
        return contenedor;
    }

    private void agregarProducto(String idProducto, String tipo, JPanel panel, String idCuenta, BigDecimal precio)
            throws Exception {
        Producto p = new ControladorProductos().obtenerProducto(idProducto);
        if (p != null) {
            boolean coincide = tipo.equalsIgnoreCase(p.getTipo()) ||
                    (tipo.equalsIgnoreCase("MOVIL")
                            && (p.getTipo().equalsIgnoreCase("PREPAGO") || p.getTipo().equalsIgnoreCase("POSTPAGO")));
            if (coincide) {
                switch (CuentasClienteControlador.obtenerCuentaClientePorIdCuenta(idCuenta).getClase()) {
                    case "FACTURACION":
                        JLabel lbl = new JLabel("• " + p.getDescripcion() + " - Precio: $" + precio);
                        lbl.setBorder(new EmptyBorder(3, 20, 3, 5));
                        panel.add(lbl);
                        break;
                    default:
                        JLabel lbl1 = new JLabel("• " + p.getDescripcion());
                        lbl1.setBorder(new EmptyBorder(3, 20, 3, 5));
                        panel.add(lbl1);
                }
            }
        }
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnGuardar = new JButton("💾 Guardar Cambios");
        JButton btnVolver = new JButton("⬅️ Volver a Clientes");

        btnGuardar.setBackground(new Color(46, 125, 50));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> volverAClientes());

        panel.add(btnGuardar);
        panel.add(btnVolver);

        return panel;
    }

    private void configurarCampo(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        campo.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 8;" +
                "focusWidth: 1;" +
                "focusColor: #ED1C24;" +
                "borderWidth: 1;" +
                "innerFocusWidth: 0");
    }

    private void cargarDatosCliente(Cliente c) {
        txtRut.setText(c.getRut());
        txtCorreo.setText(c.getCorreo());
        txtNombres.setText(c.getNombres());
        txtApellidoP.setText(c.getApellidoP());
        txtApellidoM.setText(c.getApellidoM());
        txtTelefono.setText(String.valueOf(c.getTelefono()));
        txtEdad.setText(String.valueOf(c.getEdad()));
        txtDireccion.setText(c.getDireccion());
        for (int i = 0; i < cboComuna.getItemCount(); i++) {
            if (cboComuna.getItemAt(i).getIdComuna().equals(c.getIdComuna())) {
                cboComuna.setSelectedIndex(i);
                break;
            }
        }
    }

    private void guardar() {
        try {
            Cliente c = cliente == null ? new Cliente() : cliente;
            c.setRut(txtRut.getText().trim());
            c.setCorreo(txtCorreo.getText().trim());
            c.setNombres(txtNombres.getText().trim());
            c.setApellidoP(txtApellidoP.getText().trim());
            c.setApellidoM(txtApellidoM.getText().trim());
            c.setTelefono(Long.parseLong(txtTelefono.getText().trim()));
            c.setEdad(Byte.parseByte(txtEdad.getText().trim()));
            c.setDireccion(txtDireccion.getText().trim());
            Comuna comunaSeleccionada = (Comuna) cboComuna.getSelectedItem();
            if (comunaSeleccionada != null) {
                c.setIdComuna(comunaSeleccionada.getIdComuna());
            }
            boolean exito = cliente == null ? clienteCtrl.crearCliente(c) : clienteCtrl.actualizarCliente(c);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                volverAClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos para teléfono y edad", "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAClientes() {
        VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getWindowAncestor(this);
        if (vp != null) {
            vp.mostrarPanel(VistaPrincipal.TARJETA_CLIENTES);
            vp.actualizarTablaClientes();
        }
    }
}
