/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author mathi
 */

import controladores.ClienteControlador;
import controladores.CuentasClienteControlador;
import controladores.ComunasControlador;
import entidades.Cliente;
import entidades.Comuna;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * MVC – VistaAgregarCliente (Vista):
 *   • Construye el formulario de alta de cliente + cuenta
 *   • SRP: solo maneja la interfaz y eventos, delegando la lógica a los controladores
 */
public class VistaAgregarCliente extends JPanel {

    // — Componentes UI —
    private final JTextField txtRut       = new JTextField();
    private final JTextField txtEmail     = new JTextField();
    private final JTextField txtNombres   = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JSpinner spinnerEdad    = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
    private final JTextField txtDireccion = new JTextField();
    private final JComboBox<String> cmbComuna      = new JComboBox<>();
    private final JComboBox<String> cmbTipoCuenta  = new JComboBox<>(new String[]{
            "Cliente", "Servicio", "Facturacion"
    });
    private final JButton btnGuardar  = new JButton("Guardar");
    private final JButton btnLimpiar  = new JButton("Limpiar");

    // — Controladores (Capa de negocio) —
    private final ClienteControlador clienteCtrl = new ClienteControlador();
    private final CuentasClienteControlador cuentaCtrl = new CuentasClienteControlador();
    private final ComunasControlador comunaCtrl = new ComunasControlador();

    public VistaAgregarCliente() {
        setLayout(new MigLayout("fillx, insets 20", 
                "[right][grow,fill]30[right][grow,fill]", 
                "[]20[]20[]20[]30[]"));

        // Título de sección
        JLabel lblTitulo = new JLabel("Datos del cliente");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 18f));
        add(lblTitulo, "span 4, wrap");

        // Primera fila: Apellidos
        add(new JLabel("Apellido Paterno:"), "cell 0 1");
        add(txtApellidoP, "cell 1 1");
        add(new JLabel("Apellido Materno:"), "cell 2 1");
        add(txtApellidoM, "cell 3 1, wrap");

        // Segunda fila: Nombres + RUT
        add(new JLabel("Nombres:"), "cell 0 2");
        add(txtNombres, "cell 1 2");
        add(new JLabel("RUT:"), "cell 2 2");
        add(txtRut, "cell 3 2, wrap");

        // Tercera fila: Email + Edad
        add(new JLabel("Email:"), "cell 0 3");
        add(txtEmail, "cell 1 3");
        add(new JLabel("Edad:"), "cell 2 3");
        add(spinnerEdad, "cell 3 3, wrap");

        // Separador
        add(new JSeparator(), "span 4, growx, gaptop 10, wrap");

        // Cuarta fila: Dirección + Comuna
        add(new JLabel("Dirección:"), "cell 0 5");
        add(txtDireccion, "cell 1 5");
        add(new JLabel("Comuna:"), "cell 2 5");
        add(cmbComuna, "cell 3 5, wrap");

        // Quinta fila: Tipo de cuenta
        add(new JLabel("Tipo de cuenta:"), "cell 0 6");
        add(cmbTipoCuenta, "cell 1 6, wrap");

        // Sexta fila: Botones
        add(btnLimpiar, "cell 2 7, split 2, sizegroup btn");
        add(btnGuardar, "sizegroup btn, wrap");

        // Estilos FlatLaf
        btnGuardar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnLimpiar.putClientProperty(FlatClientProperties.STYLE, "font:plain");

        // Carga dinámica de comunas
        cargarComunas();

        // Acciones
        btnGuardar.addActionListener(e -> onGuardar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    /** Carga el combo de comunas desde el DAO */
    private void cargarComunas() {
        cmbComuna.removeAllItems();
        List<Comuna> lista = comunaCtrl.listarComunas();
        lista.forEach(c -> cmbComuna.addItem(c.getDescripcion()));
    }

    /** Evento al presionar “Guardar”: valida, crea Cliente y Cuenta, notifica */
    private void onGuardar() {
        // 1. Validaciones básicas
        if (txtApellidoP.getText().trim().isEmpty()
         || txtNombres.getText().trim().isEmpty()
         || txtRut.getText().trim().isEmpty()
         || txtEmail.getText().trim().isEmpty()
         || txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Completa todos los campos obligatorios",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Construcción del modelo Cliente (entidad)
            Cliente c = new Cliente();
            c.setApellidoP(txtApellidoP.getText().trim());
            c.setApellidoM(txtApellidoM.getText().trim());
            c.setNombres(txtNombres.getText().trim());
            c.setRut(txtRut.getText().trim());
            c.setCorreo(txtEmail.getText().trim());
            c.setEdad(((Number) spinnerEdad.getValue()).byteValue());
            c.setDireccion(txtDireccion.getText().trim());

            // ID de comuna a partir de su descripción
            String desc = (String) cmbComuna.getSelectedItem();
            String idComuna = comunaCtrl.obtenerIdPorDescripcion(desc);
            c.setIdComuna(idComuna);

            // 3. Inserción vía controlador
            boolean okCliente = clienteCtrl.crearCliente(c);
            if (!okCliente) throw new Exception("Error al crear el cliente");

            // 4. Crear la cuenta asociada
            String idCuenta = UUID.randomUUID().toString(); 
            String tipo = (String) cmbTipoCuenta.getSelectedItem();
            boolean okCuenta = cuentaCtrl.insertar(idCuenta, c.getRut(), tipo);
            if (!okCuenta) throw new Exception("Error al crear la cuenta cliente");

            // 5. Éxito: alerta y limpiar
            JOptionPane.showMessageDialog(
                this,
                "✅ Cliente y cuenta agregados correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            limpiarCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "❌ " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** Limpia todos los campos para un nuevo ingreso */
    private void limpiarCampos() {
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtNombres.setText("");
        txtRut.setText("");
        txtEmail.setText("");
        spinnerEdad.setValue(18);
        txtDireccion.setText("");
        if (cmbComuna.getItemCount() > 0) cmbComuna.setSelectedIndex(0);
        cmbTipoCuenta.setSelectedIndex(0);
    }
}
