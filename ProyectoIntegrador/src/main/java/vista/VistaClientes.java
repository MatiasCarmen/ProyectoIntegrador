/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matias papu
 */
package vista;

import controladores.ClienteControlador;
import controladores.ComunasControlador;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.ImageIcon;
import entidades.Cliente;
import entidades.Comuna;
import net.miginfocom.swing.MigLayout;
import ren.main.VistaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import reportes.ReporteClientes;

public class VistaClientes extends JPanel {

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtRut = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JComboBox<String> cmbComuna = new JComboBox<>();
    private final JComboBox<String> cmbTipoCuenta = new JComboBox<>(new String[] {
            "Todos", "Cliente", "Servicio", "Facturacion"
    });
    private final JButton btnBuscar = new JButton("Aplicar filtros");
    private final JButton btnExportarExcel = new JButton("Exportar a Excel");

    private final DefaultTableModel model;
    private final JTable tblClientes;

    private final ClienteControlador clienteCtrl = new ClienteControlador();
    private final ComunasControlador comunaCtrl = new ComunasControlador();

    // Constructor para modo lista simple (sin filtros)
    public VistaClientes(boolean modoListaSimple) {
        this();
        if (modoListaSimple) {
            configurarModoListaSimple();
        }
    }

    public VistaClientes() {
        setLayout(new MigLayout("fillx, insets 20",
                "[pref][grow,fill][pref][100!][pref][100!][pref!]",
                "[]20[]10[]10[]20[][grow,fill]"));

        // Panel principal con elevación
        setBackground(Color.WHITE);

        // Título con estilo Claro y sombra
        JPanel titlePanel = new vista.util.UIHelper.ElevatedPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("<html><h1 style='color:#ED1C24'>Búsqueda de clientes</h1></html>");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(titulo);
        add(titlePanel, "span 7, wrap");

        // Panel de filtros con borde y efecto de elevación
        JPanel panelFiltros = new vista.util.UIHelper.ElevatedPanel();
        panelFiltros.setLayout(new MigLayout("fillx, insets 15",
                "[pref][grow,fill][pref][100!][pref][100!][pref!]", "[]10[]10[]"));
        panelFiltros.setBackground(Color.WHITE);

        // Estilizar campos con efectos modernos
        configurarCampoModerno(txtNombre, "Ingrese nombre a buscar");
        configurarCampoModerno(txtRut, "Ingrese RUT");
        configurarCampoModerno(txtDireccion, "Ingrese dirección");
        configurarCampoModerno(txtApellidoP, "Ingrese apellido paterno");
        configurarCampoModerno(txtApellidoM, "Ingrese apellido materno");

        // Estilizar botón de búsqueda
        vista.util.UIHelper.setupButtonHover(btnBuscar,
                new Color(237, 28, 36), // Color normal (Rojo Claro)
                new Color(200, 16, 46) // Color hover (Rojo oscuro)
        );

        // Agregar componentes al panel de filtros
        panelFiltros.add(crearLabel("Buscar por nombre:"), "cell 0 0");
        panelFiltros.add(txtNombre, "cell 1 0");
        panelFiltros.add(crearLabel("RUT:"), "cell 2 0");
        panelFiltros.add(txtRut, "cell 3 0");
        panelFiltros.add(crearLabel("Dirección:"), "cell 4 0");
        panelFiltros.add(txtDireccion, "cell 5 0");
        panelFiltros.add(btnBuscar, "cell 6 0, wrap");

        panelFiltros.add(crearLabel("Apellido paterno:"), "cell 0 1");
        panelFiltros.add(txtApellidoP, "cell 1 1");
        panelFiltros.add(crearLabel("Apellido materno:"), "cell 2 1");
        panelFiltros.add(txtApellidoM, "cell 3 1, span 3, wrap");

        panelFiltros.add(crearLabel("Comuna:"), "cell 0 2");
        panelFiltros.add(cmbComuna, "cell 1 2");
        panelFiltros.add(crearLabel("Tipo de cuenta:"), "cell 3 2");
        panelFiltros.add(cmbTipoCuenta, "cell 4 2, wrap");

        add(panelFiltros, "span 7, grow, wrap");

        // Tabla con estilo moderno
        String[] cols = { "Nombre", "Apellido P", "Apellido M", "RUT", "Dirección", "Comuna", "Tipo Cuenta",
                "Id Cuenta" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblClientes = new JTable(model);
        tblClientes.setRowHeight(35);
        tblClientes.setShowGrid(false);
        tblClientes.setIntercellSpacing(new Dimension(0, 0));
        tblClientes.setSelectionBackground(new Color(255, 236, 236));
        tblClientes.setSelectionForeground(new Color(237, 28, 36));

        // Panel de la tabla con elevación
        JPanel tablePanel = new vista.util.UIHelper.ElevatedPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(tblClientes), BorderLayout.CENTER);

        // Botón de exportar a Excel
        btnExportarExcel.setBackground(new Color(46, 125, 50));
        btnExportarExcel.setForeground(Color.WHITE);
        btnExportarExcel.setFocusPainted(false);
        btnExportarExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExportarExcel.addActionListener(e -> exportarExcelClientes());
        tablePanel.add(btnExportarExcel, BorderLayout.SOUTH);

        add(tablePanel, "span 7, grow");

        // Protección contra nulos al cargar datos
        btnBuscar.addActionListener(e -> {
            model.setRowCount(0);
            String nombreBusqueda = txtNombre.getText().trim();
            System.out.println("Filtrando por nombre = '" + nombreBusqueda + "'");

            List<Object[]> resultados = clienteCtrl.buscarClientesAvanzado(
                    txtRut.getText().trim(),
                    nombreBusqueda,
                    txtApellidoP.getText().trim(),
                    txtApellidoM.getText().trim(),
                    txtDireccion.getText().trim(),
                    cmbTipoCuenta.getSelectedItem().toString(),
                    cmbComuna.getSelectedItem().toString());

            if (resultados != null) {
                resultados.forEach(fila -> {
                    if (fila != null && fila.length == 8) { // Verificamos que tenga todas las columnas
                        model.addRow(new Object[] {
                                fila[0] != null ? fila[0] : "", // Nombre
                                fila[1] != null ? fila[1] : "", // Apellido P
                                fila[2] != null ? fila[2] : "", // Apellido M
                                fila[3] != null ? fila[3] : "", // RUT
                                fila[4] != null ? fila[4] : "", // Dirección
                                fila[5] != null ? fila[5] : "", // Comuna
                                fila[6] != null ? fila[6] : "", // Tipo Cuenta
                                fila[7] != null ? fila[7] : "" // Id Cuenta
                        });
                    }
                });
                System.out.println("Filas cargadas: " + resultados.size());
            } else {
                System.out.println("La búsqueda no retornó resultados");
            }
        });

        // Agregar evento de doble clic en la tabla
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarDetalleCliente();
                }
            }
        });

        // Cargar comunas en el combo
        cargarComunas();

        // Cargar todos los clientes al inicio
        cargarTodosLosClientes();
    }

    private void configurarModoListaSimple() {
        // Limpiar completamente el panel
        removeAll();

        // Configurar layout para modo lista simple
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[grow][]"));
        setBackground(Color.WHITE);

        // Título centrado y estilizado
        JPanel titlePanel = new vista.util.UIHelper.ElevatedPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titulo = new JLabel("Lista de Clientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(237, 28, 36));
        titlePanel.add(titulo);

        add(titlePanel, "grow, wrap");

        // Panel de la tabla con elevación y borde
        JPanel tablePanel = new vista.util.UIHelper.ElevatedPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configurar tabla
        tblClientes.setRowHeight(35);
        tblClientes.setShowGrid(false);
        tblClientes.setIntercellSpacing(new Dimension(0, 0));
        tblClientes.setSelectionBackground(new Color(255, 236, 236));
        tblClientes.setSelectionForeground(new Color(237, 28, 36));
        tblClientes.getTableHeader().setBackground(new Color(248, 249, 250));
        tblClientes.getTableHeader().setForeground(new Color(51, 51, 51));
        tblClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tblClientes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, "grow, wrap");

        // Panel de botones centrado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        // Estilizar botón de exportar
        btnExportarExcel.setBackground(new Color(46, 125, 50));
        btnExportarExcel.setForeground(Color.WHITE);
        btnExportarExcel.setFocusPainted(false);
        btnExportarExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExportarExcel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnExportarExcel.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(btnExportarExcel);
        add(buttonPanel, "grow");

        // Revalidar y repintar
        revalidate();
        repaint();
    }

    private void configurarCampo(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
    }

    private void configurarCampoModerno(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        campo.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 8;" +
                "focusWidth: 1;" +
                "focusColor: #ED1C24;" +
                "borderWidth: 1;" +
                "innerFocusWidth: 0");
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(new Color(51, 51, 51));
        return label;
    }

    private void cargarComunas() {
        List<Comuna> comunas = comunaCtrl.listarComunas();
        cmbComuna.addItem("Todas");
        if (comunas != null) {
            comunas.forEach(c -> {
                if (c != null && c.getDescripcion() != null) {
                    cmbComuna.addItem(c.getDescripcion());
                }
            });
        }
    }

    private void mostrarDetalleCliente() {
        int row = tblClientes.getSelectedRow();
        if (row >= 0) {
            String rut = (String) model.getValueAt(row, 3);
            String idCuenta = (String) model.getValueAt(row, 7);// RUT está en la columna 3
            Cliente cliente = clienteCtrl.obtenerClientePorRut(rut);
            if (cliente != null) {
                VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getWindowAncestor(this);
                if (vp != null) {
                    vp.showDetalleCliente(cliente, idCuenta);
                }
            }
        }
    }

    public void actualizarTabla() {
        cargarTodosLosClientes();
    }

    private void exportarExcelClientes() {
        String archivo = reportes.ReporteClientes.generarExcel();
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (archivo != null) {
            vista.util.UIHelper.showToast(parent, "Reporte de clientes exportado exitosamente", false);
        } else {
            vista.util.UIHelper.showToast(parent, "Error al exportar el reporte de clientes", true);
        }
    }

    private void cargarTodosLosClientes() {
        model.setRowCount(0);
        List<Object[]> resultados = clienteCtrl.buscarClientesAvanzado("", "", "", "", "", "Todos", "Todas");
        if (resultados != null) {
            for (Object[] fila : resultados) {
                if (fila != null) {
                    model.addRow(new Object[] {
                            fila[0] != null ? fila[0] : "", // Nombre
                            fila[1] != null ? fila[1] : "", // Apellido P
                            fila[2] != null ? fila[2] : "", // Apellido M
                            fila[3] != null ? fila[3] : "", // RUT
                            fila[4] != null ? fila[4] : "", // Dirección
                            fila[5] != null ? fila[5] : "", // Comuna
                            fila[6] != null ? fila[6] : "", // Tipo Cuenta
                            fila[7] != null ? fila[7] : "" // Id Cuenta
                    });
                }
            }
            System.out.println("Todos los clientes cargados: " + resultados.size());
        } else {
            System.out.println("No se pudieron cargar los clientes");
        }
    }
}
