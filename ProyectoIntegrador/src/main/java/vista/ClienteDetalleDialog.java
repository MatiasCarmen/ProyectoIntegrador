/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mathi
 */
package vista;

import entidades.Cliente;
import entidades.Comuna;
import controladores.ComunasControlador;
import controladores.ClienteControlador;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteDetalleDialog extends JDialog {
    private final JTextField txtRut;
    private final JTextField txtCorreo;
    private final JTextField txtNombres;
    private final JTextField txtApellidoP;
    private final JTextField txtApellidoM;
    private final JTextField txtTelefono;
    private final JTextField txtEdad;
    private final JTextField txtDireccion;
    private final JComboBox<Comuna> cboComuna;
    private final ClienteControlador controlador;
    private boolean aceptado = false;
    private Cliente clienteActual;

    public ClienteDetalleDialog(Window owner, boolean modoEdicion) {
        this(owner, modoEdicion, null);
    }

    public ClienteDetalleDialog(Window owner, boolean modoEdicion, Cliente cliente) {
        super(owner, cliente == null ? "Nuevo Cliente" : "Editar Cliente", ModalityType.APPLICATION_MODAL);
        this.clienteActual = cliente;
        this.controlador = new ClienteControlador();

        setLayout(new MigLayout("wrap 2, insets 20", "[right][grow,fill]"));

        // Crear campos
        txtRut = new JTextField(20);
        txtCorreo = new JTextField();
        txtNombres = new JTextField();
        txtApellidoP = new JTextField();
        txtApellidoM = new JTextField();
        txtTelefono = new JTextField();
        txtEdad = new JTextField();
        txtDireccion = new JTextField();
        cboComuna = new JComboBox<>();

        // Cargar comunas
        ComunasControlador cc = new ComunasControlador();
        List<Comuna> comunas = cc.listarComunas();
        for (Comuna comuna : comunas) {
            cboComuna.addItem(comuna);
        }

        // Agregar componentes
        add(new JLabel("RUT:"), "align label");
        add(txtRut);
        add(new JLabel("Correo:"), "align label");
        add(txtCorreo);
        add(new JLabel("Nombres:"), "align label");
        add(txtNombres);
        add(new JLabel("Apellido Paterno:"), "align label");
        add(txtApellidoP);
        add(new JLabel("Apellido Materno:"), "align label");
        add(txtApellidoM);
        add(new JLabel("Teléfono:"), "align label");
        add(txtTelefono);
        add(new JLabel("Edad:"), "align label");
        add(txtEdad);
        add(new JLabel("Dirección:"), "align label");
        add(txtDireccion);
        add(new JLabel("Comuna:"), "align label");
        add(cboComuna);

        // Si es edición, cargar datos del cliente
        if (cliente != null) {
            cargarDatosCliente(cliente);
            txtRut.setEditable(false);
        }

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAceptar = new JButton(cliente == null ? "Crear" : "Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.putClientProperty(FlatClientProperties.STYLE, "background:#237CE5;foreground:#FFFFFF;font:bold");
        btnCancelar.putClientProperty(FlatClientProperties.STYLE, "background:#B3B3B3");

        btnAceptar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, "span, center");

        pack();
        setLocationRelativeTo(owner);
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

        // Seleccionar la comuna correcta en el combo
        for (int i = 0; i < cboComuna.getItemCount(); i++) {
            Comuna comuna = cboComuna.getItemAt(i);
            if (comuna.getIdComuna().equals(c.getIdComuna())) {
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

            boolean exito;
            if (clienteActual == null) {
                exito = controlador.crearCliente(c);
            } else {
                exito = controlador.actualizarCliente(c);
            }

            if (exito) {
                aceptado = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar el cliente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese valores numéricos válidos para teléfono y edad",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
