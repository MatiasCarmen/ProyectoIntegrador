package Formularios;

import Controladores.ClienteControlador;
import Controladores.ComunasControlador;
import Controladores.ControladorCuentas_clientes;
import Entidades.Cliente;
import Entidades.Comuna;
import Entidades.CuentaCliente;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClienteCRM extends JFrame {
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtRut, txtNombre, txtApellidoPaterno, txtApellidoMaterno, txtDireccion;
    private JComboBox<String> cbxTipoCuenta, cbxComuna;
    private JMenu usuarioMenu;

    public ClienteCRM() {
        setTitle("CRM - Búsqueda de Clientes");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initComponentes();
    }

    private void estilizarCampo(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setPreferredSize(new Dimension(150, 28));
        comp.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
    }

    private void initComponentes() {
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setLayout(new BoxLayout(barraMenu, BoxLayout.X_AXIS));
        barraMenu.setBorder(new EmptyBorder(5, 20, 5, 20));
        barraMenu.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("Claro CRM");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        barraMenu.add(titulo);
        barraMenu.add(Box.createHorizontalStrut(20));
        String[] botonesMenu = {"Buscar Clientes", "Pedidos", "Actividades", "Agenda", "Solicitudes"};
        for (String texto : botonesMenu) {
            JButton btn = new JButton(texto);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            barraMenu.add(btn);
        }
        barraMenu.add(Box.createHorizontalGlue());
        usuarioMenu = new JMenu("Juan Pérez");
        usuarioMenu.setIcon(new ImageIcon("user_icon.png"));
        usuarioMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JMenuItem perfil = new JMenuItem("Mi cuenta");
        JMenuItem cerrar = new JMenuItem("Cerrar sesión");
        usuarioMenu.add(perfil);
        usuarioMenu.add(cerrar);
        barraMenu.add(usuarioMenu);
        add(barraMenu, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFiltros.setBorder(new EmptyBorder(15, 40, 15, 40));
        panelFiltros.setBackground(Color.WHITE);

        txtRut = new JTextField();
        txtNombre = new JTextField();
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtDireccion = new JTextField();

        cbxTipoCuenta = new JComboBox<>();
        cbxTipoCuenta.addItem("Todos");
        cbxTipoCuenta.addItem("Servicio");
        cbxTipoCuenta.addItem("Facturacion");
        cbxTipoCuenta.addItem("Cliente");

        cbxComuna = new JComboBox<>();
        cbxComuna.addItem("Todos");
        for (Comuna comuna : ComunasControlador.listarComunas()) {
            cbxComuna.addItem(comuna.getDescripcion());
        }

        estilizarCampo(txtRut);
        estilizarCampo(txtNombre);
        estilizarCampo(txtApellidoPaterno);
        estilizarCampo(txtApellidoMaterno);
        estilizarCampo(txtDireccion);
        estilizarCampo(cbxTipoCuenta);
        estilizarCampo(cbxComuna);

        agregarKeyListenerBusqueda(txtRut);
        agregarKeyListenerBusqueda(txtNombre);
        agregarKeyListenerBusqueda(txtApellidoPaterno);
        agregarKeyListenerBusqueda(txtApellidoMaterno);
        agregarKeyListenerBusqueda(txtDireccion);

        panelFiltros.add(crearPar("RUT:", txtRut));
        panelFiltros.add(crearPar("Nombre:", txtNombre));
        panelFiltros.add(crearPar("Apellido Paterno:", txtApellidoPaterno));
        panelFiltros.add(crearPar("Apellido Materno:", txtApellidoMaterno));
        panelFiltros.add(crearPar("Dirección:", txtDireccion));
        panelFiltros.add(crearPar("Tipo de cuenta:", cbxTipoCuenta));
        panelFiltros.add(crearPar("Comuna:", cbxComuna));

        JButton btnAplicar = new JButton("Aplicar filtros");
        btnAplicar.setFocusPainted(false);
        btnAplicar.setBackground(new Color(111, 95, 255));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAplicar.setPreferredSize(new Dimension(140, 30));
        btnAplicar.setBorder(new LineBorder(new Color(111, 95, 255), 1, true));
        btnAplicar.addActionListener(e -> filtrarClientes());

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(btnAplicar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(panelFiltros, BorderLayout.CENTER);
        centro.add(wrapper, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        String[] columnas = {"Nombre", "RUT", "Tipo Cuenta", "IDCuenta"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(40);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaClientes.setGridColor(Color.LIGHT_GRAY);
        tablaClientes.setShowGrid(true);
        tablaClientes.setSelectionBackground(new Color(220, 220, 250));
        tablaClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaClientes.setFillsViewportHeight(true);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablaClientes.getSelectedRow() != -1) {
                    int row = tablaClientes.getSelectedRow();
                    String idCuenta = modeloTabla.getValueAt(row, 3).toString();
                    VistaCliente vista = new VistaCliente(idCuenta);
                    vista.setVisible(true);
                    dispose();
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados de búsqueda"));
        scrollTabla.setPreferredSize(new Dimension(950, 250));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.SOUTH);
    }

    private JPanel crearPar(String label, JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(comp, BorderLayout.CENTER);
        return panel;
    }

    private void agregarKeyListenerBusqueda(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filtrarClientes();
                }
            }
        });
    }

    private void filtrarClientes() {
        String rut = txtRut.getText().trim();
        String nombres = txtNombre.getText().trim();
        String apellidoP = txtApellidoPaterno.getText().trim();
        String apellidoM = txtApellidoMaterno.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String clase = cbxTipoCuenta.getSelectedItem().toString().equals("Todos") ? "" : cbxTipoCuenta.getSelectedItem().toString();
        String comuna = cbxComuna.getSelectedItem().toString().equals("Todos") ? "" : cbxComuna.getSelectedItem().toString();
        modeloTabla.setRowCount(0);
        List<CuentaCliente> cuentas = ControladorCuentas_clientes.buscarCuentasClientesConFiltros(
                rut, nombres, apellidoP, apellidoM, direccion, clase, comuna
        );
        if (!cuentas.isEmpty()) {
            for (CuentaCliente cuenta : cuentas) {
                Cliente cliente = ClienteControlador.obtenerClientePorRut(cuenta.getRut());
                if (cliente != null) {
                    modeloTabla.addRow(new Object[]{
                            cliente.getNombres() + " " + cliente.getApellidoP() + " " + cliente.getApellidoM(),
                            cuenta.getRut(),
                            cuenta.getClase(),
                            cuenta.getIdCuenta()
                    });
                }
            }
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron resultados con los filtros proporcionados.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtRut.setText("");
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtDireccion.setText("");
        cbxTipoCuenta.setSelectedIndex(0);
        cbxComuna.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteCRM().setVisible(true));
    }
}