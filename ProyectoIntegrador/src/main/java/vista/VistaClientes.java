/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import controladores.ClienteControlador;
import controladores.ComunasControlador;

import com.formdev.flatlaf.FlatClientProperties;
import entidades.Comuna;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author matias papu
 */
/**
 * Panel de búsqueda avanzada de clientes.
 * MVC puro: dispara eventos al controlador y muestra resultados.
 */
public class VistaClientes extends JPanel {

    private final JTextField txtRut         = new JTextField();
    private final JTextField txtNombre      = new JTextField();
    private final JTextField txtApellidoP   = new JTextField();
    private final JTextField txtApellidoM   = new JTextField();
    private final JTextField txtDireccion   = new JTextField();
    private final JComboBox<String> cmbTipoCuenta = new JComboBox<>(new String[]{"Todos","Cliente","Servicio","Facturacion"});
    private final JComboBox<String> cmbComuna     = new JComboBox<>();
    private final JButton btnBuscar          = new JButton("Aplicar filtros");

    private final DefaultTableModel model;
    private final JTable tblClientes;

    private final ClienteControlador clienteCtrl     = new ClienteControlador();
    private final ComunasControlador comunaCtrl      = new ComunasControlador();

    public VistaClientes() {
        setLayout(new MigLayout("fill, insets 10", "[grow, fill][grow, fill]", "[][grow]"));

        // Panel de filtros (dos columnas)
        add(new JLabel("RUT:"),            "cell 0 0");
        add(txtRut,                        "cell 1 0, wrap");
        add(new JLabel("Nombre:"),         "cell 0 1");
        add(txtNombre,                     "cell 1 1, wrap");
        add(new JLabel("Apellido Paterno:"),"cell 0 2");
        add(txtApellidoP,                  "cell 1 2, wrap");
        add(new JLabel("Apellido Materno:"),"cell 0 3");
        add(txtApellidoM,                  "cell 1 3, wrap");
        add(new JLabel("Dirección:"),      "cell 0 4");
        add(txtDireccion,                  "cell 1 4, wrap");
        add(new JLabel("Tipo de cuenta:"), "cell 0 5");
        add(cmbTipoCuenta,                 "cell 1 5, wrap");
        add(new JLabel("Comuna:"),         "cell 0 6");
        add(cmbComuna,                     "cell 1 6, wrap");
        add(btnBuscar,                     "cell 1 7, tag right, gaptop 10, wrap");

        // Tabla de resultados
        String[] cols = {"Nombre","RUT","Tipo Cuenta","ID Cuenta"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) {
                return false; // no editable
            }
        };
        tblClientes = new JTable(model);
        tblClientes.setRowHeight(28);
        add(new JScrollPane(tblClientes), "span, grow, wrap");

        // Estilo FlatLaf para el botón
        btnBuscar.putClientProperty(FlatClientProperties.STYLE, "font:bold");

        // Carga de datos iniciales
        cargarComunas();
        btnBuscar.addActionListener(e -> aplicarFiltros());
        aplicarFiltros();
    }

    private void cargarComunas() {
        cmbComuna.removeAllItems();
        cmbComuna.addItem("Todos");
        List<Comuna> comunas = comunaCtrl.listarComunas();
        comunas.forEach(c -> cmbComuna.addItem(c.getDescripcion()));
    }

    private void aplicarFiltros() {
        String rut       = txtRut.getText().trim();
        String nombre    = txtNombre.getText().trim();
        String apP       = txtApellidoP.getText().trim();
        String apM       = txtApellidoM.getText().trim();
        String dir       = txtDireccion.getText().trim();
        String tipo      = (String)cmbTipoCuenta.getSelectedItem();
        String comuna    = (String)cmbComuna.getSelectedItem();

        // Llamada al controlador con JOIN + filtros
        List<Object[]> filas = clienteCtrl.buscarClientesAvanzado(
            rut, nombre, apP, apM, dir, tipo, comuna
        );

        // Refrescar tabla
        model.setRowCount(0);
        filas.forEach(fila -> model.addRow(fila));
    }
}