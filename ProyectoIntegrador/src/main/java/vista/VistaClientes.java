/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matias papu
 */
package vista;

import javax.swing.SwingUtilities;
import controladores.ClienteControlador;
import controladores.ComunasControlador;
import com.formdev.flatlaf.FlatClientProperties;
import entidades.Comuna;
import net.miginfocom.swing.MigLayout;
import ren.main.VistaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel de búsqueda avanzada de clientes, con layout según mockup.
 * Captura doble-click para ver detalle embebido en VistaPrincipal.
 */
public class VistaClientes extends JPanel {

    private final JTextField txtNombre    = new JTextField();
    private final JTextField txtRut       = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JComboBox<String> cmbComuna     = new JComboBox<>();
    private final JComboBox<String> cmbTipoCuenta = new JComboBox<>(new String[]{
        "Todos","Cliente","Servicio","Facturacion"
    });
    private final JButton btnBuscar        = new JButton("Aplicar filtros");

    private final DefaultTableModel model;
    private final JTable tblClientes;

    private final ClienteControlador clienteCtrl = new ClienteControlador();
    private final ComunasControlador comunaCtrl  = new ComunasControlador();

    public VistaClientes() {
        setLayout(new MigLayout("fillx, insets 10",
                "[pref][grow,fill][pref][100!][pref][100!][pref!]",
                "[]10[]10[]10[][grow,fill]"));

        // Título
        add(new JLabel("<html><h2>Búsqueda de clientes</h2></html>"), "span 7, wrap");

        // Filtros...
        add(new JLabel("Buscar por nombre:"),   "cell 0 1");
        add(txtNombre,                          "cell 1 1");
        add(new JLabel("RUT:"),                 "cell 2 1");
        add(txtRut,                             "cell 3 1");
        add(new JLabel("Dirección:"),           "cell 4 1");
        add(txtDireccion,                       "cell 5 1");
        add(btnBuscar,                          "cell 6 1, wrap");

        add(new JLabel("Buscar por apellido paterno:"), "cell 0 2");
        add(txtApellidoP,                                 "cell 1 2");
        add(new JLabel("Buscar por apellido materno:"), "cell 2 2");
        add(txtApellidoM,                                 "cell 3 2, span 3, wrap");

        add(new JLabel("Comuna:"),             "cell 0 3");
        add(cmbComuna,                         "cell 1 3");
        add(new JLabel("Tipo de cuenta:"),     "cell 3 3, alignx center");
        add(cmbTipoCuenta,                     "cell 4 3, wrap");

        add(new JLabel("Resultado de búsqueda"), "span 7, gaptop 10, wrap");

        // Tabla
        String[] cols = {"Cliente","RUT","Tipo Cuenta","ID Cuenta"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblClientes = new JTable(model);
        tblClientes.setRowHeight(28);

        // Doble-click para detalle embebido
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblClientes.rowAtPoint(e.getPoint());
                    if (row < 0) return;

                    // Obtenemos el ID Cuenta (columna 3)
                    String idCuenta = (String) model.getValueAt(row, 3);

                    // Llamamos al método en VistaPrincipal
                    VistaPrincipal vp = (VistaPrincipal)
                        SwingUtilities.getAncestorOfClass(VistaPrincipal.class, VistaClientes.this);
                    if (vp != null) {
                        vp.abrirModuloCuenta(idCuenta);
                    }
                }
            }
        });

        add(new JScrollPane(tblClientes), "span 7, grow, wrap");

        // Estilos y lógica de filtros
        btnBuscar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        cargarComunas();
        btnBuscar.addActionListener(e -> aplicarFiltros());
        aplicarFiltros();
    }

    private void cargarComunas() {
        cmbComuna.removeAllItems();
        cmbComuna.addItem("Todos");
        List<Comuna> lista = comunaCtrl.listarComunas();
        lista.forEach(c -> cmbComuna.addItem(c.getDescripcion()));
    }

    private void aplicarFiltros() {
        String nombre    = txtNombre.getText().trim();
        String rut       = txtRut.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String apP       = txtApellidoP.getText().trim();
        String apM       = txtApellidoM.getText().trim();
        String comuna    = (String)cmbComuna.getSelectedItem();
        String tipo      = (String)cmbTipoCuenta.getSelectedItem();

        List<Object[]> filas = clienteCtrl.buscarClientesAvanzado(
            rut, nombre, apP, apM, direccion, tipo, comuna
        );
        model.setRowCount(0);
        filas.forEach(f -> model.addRow(f));
    }
}
