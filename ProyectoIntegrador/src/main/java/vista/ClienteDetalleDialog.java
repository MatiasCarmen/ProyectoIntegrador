package vista;

import controladores.*;
import entidades.*;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import ren.main.VistaPrincipal;

public class ClienteDetalleDialog extends JDialog {
    private final JTextField txtRut = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JComboBox<Comuna> cboComuna = new JComboBox<>();

    private final ClienteControlador controlador = new ClienteControlador();
    private Cliente clienteActual;
    private boolean aceptado = false;
    private final String idCuenta;

    public ClienteDetalleDialog(Window owner, Cliente cliente, String idCuenta) {
        super(owner, "Detalle del Cliente", Dialog.ModalityType.APPLICATION_MODAL);
        this.clienteActual = cliente;
        this.idCuenta = idCuenta;

        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new MigLayout("wrap 2, insets 20", "[right][grow,fill]"));
        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        configurarCampo(txtRut, "RUT");
        configurarCampo(txtCorreo, "Correo");
        configurarCampo(txtNombres, "Nombres");
        configurarCampo(txtApellidoP, "Apellido Paterno");
        configurarCampo(txtApellidoM, "Apellido Materno");
        configurarCampo(txtTelefono, "Teléfono");
        configurarCampo(txtEdad, "Edad");
        configurarCampo(txtDireccion, "Dirección");

        ComunasControlador cc = new ComunasControlador();
        List<Comuna> comunas = cc.listarComunas();
        for (Comuna c : comunas) cboComuna.addItem(c);

        mainPanel.add(new JLabel("RUT:")); mainPanel.add(txtRut);
        mainPanel.add(new JLabel("Correo:")); mainPanel.add(txtCorreo);
        mainPanel.add(new JLabel("Nombres:")); mainPanel.add(txtNombres);
        mainPanel.add(new JLabel("Apellido Paterno:")); mainPanel.add(txtApellidoP);
        mainPanel.add(new JLabel("Apellido Materno:")); mainPanel.add(txtApellidoM);
        mainPanel.add(new JLabel("Teléfono:")); mainPanel.add(txtTelefono);
        mainPanel.add(new JLabel("Edad:")); mainPanel.add(txtEdad);
        mainPanel.add(new JLabel("Dirección:")); mainPanel.add(txtDireccion);
        mainPanel.add(new JLabel("Comuna:")); mainPanel.add(cboComuna);

        if (cliente != null) {
            cargarDatosCliente(cliente);
            txtRut.setEditable(false);
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAceptar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        mainPanel.add(panelBotones, "span 2, center");

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Actividades", crearPanelActividades());
        tabs.addTab("Agenda", crearPanelAgenda());
        tabs.addTab("Productos Instalados", crearPanelProductos());
        add(tabs, BorderLayout.SOUTH);

        pack();
        setSize(850, 600);
        setLocationRelativeTo(owner);
    }

    private void configurarCampo(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
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
            Cliente c = clienteActual == null ? new Cliente() : clienteActual;
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
            boolean exito = clienteActual == null ? controlador.crearCliente(c) : controlador.actualizarCliente(c);
            if (exito) {
                aceptado = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos para teléfono y edad", "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearPanelActividades() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = { "ID", "Tipo", "Fecha Creación", "Fecha Cierre", "Descripción" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        if (idCuenta != null) {
            List<Actividad> actividades = new ActividadesControlador().obtenerActividadesPorCuenta(idCuenta);
            for (Actividad a : actividades) {
                modelo.addRow(new Object[]{
                    a.getIdActividad(), a.getTipo(), a.getFechaCreacion(), a.getFechaCierre(), a.getDescripcion()
                });
            }
        }
         JButton btnAgregarActividad = new JButton("Agregar Actividad");
    btnAgregarActividad.addActionListener(e ->{
        VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getWindowAncestor(this);
                if (vp != null) {
                    vp.showAgregarActividades(idCuenta);
                }
    } );

    
    
    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panelBoton.add(btnAgregarActividad);
    panel.add(panelBoton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    
    private JPanel crearPanelAgenda() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = { "IdActividad", "Telefono","Lugar", "Fecha" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        if (idCuenta != null) {
          List<MesaCentral> todos = new ControladorMesa_central().listarTodosPorIdCuenta(idCuenta);
            System.out.println("Estado:"+todos);
            for (MesaCentral m : todos) {
                System.out.println("m:"+m);
                Actividad a = new ActividadesControlador().obtenerPorId(m.getIdActividad());
                modelo.addRow(new Object[]{
                    m.getIdActividad(), m.getTelefono(), m.getLugar(),a.getFechaCreacion()
                });
            }
        }
         

  
    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    panel.add(panelBoton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    

    private  JScrollPane crearPanelProductos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        String claseCuenta = CuentasClienteControlador.obtenerCuentaClientePorIdCuenta(idCuenta).getClase();
        ArrayList<String> cuentasServicio = new ArrayList<>();

        if (claseCuenta.equalsIgnoreCase("CLIENTE")) {
            String rut = CuentasClienteControlador.obtenerRutPorIdCuenta(idCuenta);
            cuentasServicio = (ArrayList<String>) CuentasClienteControlador.obtenerCuentasServicioPorRut(rut);
        } else if (claseCuenta.equalsIgnoreCase("FACTURACION")) {
            String idServicio = new CuentasClienteControlador().obtenerIdCuentaServicioDesdeFacturacion(idCuenta);
            if (idServicio != null) cuentasServicio.add(idServicio);
        } else {
            cuentasServicio.add(idCuenta);
        }

        for (String cuenta : cuentasServicio) {
            panel.add(crearProducto("📺 TV Hogar", "TELEVISION", cuenta));
            panel.add(crearProducto("📶 Internet Hogar", "BANDA_ANCHA", cuenta));
            panel.add(crearProducto("📞 Telefonía Fija", "TELEFONIA", cuenta));
            panel.add(crearProducto("📱 Móvil", "MOVIL", cuenta));
        }

        return new JScrollPane(panel);
    }

    private JPanel crearProducto(String nombre, String tipo, String idCuentaServicio) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(nombre);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        contenedor.add(lbl);

        JPanel productos = new JPanel();
        productos.setLayout(new BoxLayout(productos, BoxLayout.Y_AXIS));
        productos.setBackground(new Color(245, 245, 245));

        try {
            Plan plan = new ControladorPlanes().obtenerPlanPorIdCuenta(idCuentaServicio);
            if (plan != null) {
                for (DetallePlan d : new ControladorDetalle_planes().obtenerDetallesPlan(plan.getIdPlan())) {
                    agregarProducto(d.getIdProducto(), tipo, productos);
                }
            }
            Adicional adicional = new ControladorAdicionales().obtenerAdicionalPorIdCuenta(idCuentaServicio);
            if (adicional != null) {
                for (DetalleAdicional d : new ControladorDetalle_adicionales().obtenerDetallesAdicional(adicional.getIdAdicionales())) {
                    agregarProducto(d.getIdProducto(), tipo, productos);
                }
            }
            Descuento descuento = new ControladorDescuentos().obtenerDescuentoPorIdCuenta(idCuentaServicio);
            if (descuento != null) {
                for (DetalleDescuento d : new ControladorDetalle_descuentos().obtenerDetallesDescuento(descuento.getIdDescuentos())) {
                    agregarProducto(d.getIdProducto(), tipo, productos);
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

    private void agregarProducto(String idProducto, String tipo, JPanel panel) throws Exception {
        Producto p = new ControladorProductos().obtenerProducto(idProducto);
        if (p != null) {
            boolean coincide = tipo.equalsIgnoreCase(p.getTipo()) ||
                    (tipo.equalsIgnoreCase("MOVIL") && (p.getTipo().equalsIgnoreCase("PREPAGO") || p.getTipo().equalsIgnoreCase("POSTPAGO")));
            if (coincide) {
                JLabel lbl = new JLabel("- " + p.getDescripcion());
                lbl.setBorder(new EmptyBorder(5, 20, 5, 5));
                panel.add(lbl);
            }
        }
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
