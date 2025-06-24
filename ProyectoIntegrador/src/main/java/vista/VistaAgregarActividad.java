/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.ActividadesControlador; // Corregido a minúsculas
import entidades.Actividad;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Formulario para registrar nueva actividad.
 */
public class VistaAgregarActividad extends JPanel {
    private final JTextField txtDescripcion = new JTextField();
    private final JTextField txtCuenta = new JTextField();
    private final JButton btnGuardar = new JButton("Guardar");

    public VistaAgregarActividad() {
        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("ID Cuenta:"));
        add(txtCuenta);
        add(new JLabel("Descripción:"));
        add(txtDescripcion);
        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarActividad());
    }

    private void guardarActividad() {
        String cuenta = txtCuenta.getText().trim();
        String desc = txtDescripcion.getText().trim();

        if (cuenta.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        Actividad a = new Actividad();
        a.setIdCuenta(cuenta);
        a.setDescripcion(desc);
        a.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
        a.setFechaCierre(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));

        a.setTipo("VISITA");
        a.setRazon("SOPORTE");
        a.setDetalle("DETALLE");
        a.setResolucion("PENDIENTE");
        a.setComentarios("Creada manualmente.");
        a.setCorreo("soporte@crm.cl");

        boolean ok = new ActividadesControlador().crearActividad(a);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Actividad creada con éxito.");
            txtCuenta.setText("");
            txtDescripcion.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar.");
        }
    }
}
