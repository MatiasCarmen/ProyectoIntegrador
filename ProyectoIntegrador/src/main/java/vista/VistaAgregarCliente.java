/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.ClienteControlador;
import entidades.Cliente;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mathi
 */

/**
 * MVC – VistaAgregarCliente (Vista):
 *   • Construye el formulario de alta de cliente + cuenta
 *   • SRP: solo maneja la interfaz y eventos, delegando la lógica a los controladores
 */
public class VistaAgregarCliente extends JPanel {
    private final ClienteControlador controlador;
    private final JTextField txtRut;
    private final JTextField txtCorreo;
    private final JTextField txtNombres;
    private final JTextField txtApellidoP;
    private final JTextField txtApellidoM;
    private final JTextField txtTelefono;
    private final JTextField txtEdad;
    private final JTextField txtDireccion;
    private final JTextField txtComuna;

    public VistaAgregarCliente() {
        controlador = new ClienteControlador();
        setLayout(new GridLayout(10, 2, 5, 5));

        // Crear campos
        txtRut = new JTextField();
        txtCorreo = new JTextField();
        txtNombres = new JTextField();
        txtApellidoP = new JTextField();
        txtApellidoM = new JTextField();
        txtTelefono = new JTextField();
        txtEdad = new JTextField();
        txtDireccion = new JTextField();
        txtComuna = new JTextField();

        // Agregar componentes
        add(new JLabel("RUT:"));
        add(txtRut);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Nombres:"));
        add(txtNombres);
        add(new JLabel("Apellido Paterno:"));
        add(txtApellidoP);
        add(new JLabel("Apellido Materno:"));
        add(txtApellidoM);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Edad:"));
        add(txtEdad);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Comuna:"));
        add(txtComuna);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarCliente());
        add(btnGuardar);
    }

    private void guardarCliente() {
        try {
            Cliente c = new Cliente();
            c.setRut(txtRut.getText().trim());
            c.setCorreo(txtCorreo.getText().trim());
            c.setNombres(txtNombres.getText().trim());
            c.setApellidoP(txtApellidoP.getText().trim());
            c.setApellidoM(txtApellidoM.getText().trim());
            c.setTelefono(Long.parseLong(txtTelefono.getText().trim()));
            c.setEdad(Byte.parseByte(txtEdad.getText().trim()));
            c.setDireccion(txtDireccion.getText().trim());
            c.setIdComuna(txtComuna.getText().trim());

            if (controlador.crearCliente(c)) {
                JOptionPane.showMessageDialog(this,
                    "Cliente guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
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

    private void limpiarCampos() {
        txtRut.setText("");
        txtCorreo.setText("");
        txtNombres.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtTelefono.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtComuna.setText("");
    }
}
