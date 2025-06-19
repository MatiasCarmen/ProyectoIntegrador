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
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Muestra datos completos de un Cliente.
 */
public class ClienteDetalleDialog extends JDialog {
    public ClienteDetalleDialog(Window owner, Cliente c) {
        super(owner, "Detalle Cliente", ModalityType.APPLICATION_MODAL);
        setLayout(new MigLayout("wrap 2, insets 10", "[right][grow]"));

        ComunasControlador cc = new ComunasControlador();
        String descComuna = cc.listarComunas().stream()
            .filter(x->x.getIdComuna().equals(c.getIdComuna()))
            .map(Comuna::getDescripcion)
            .findFirst().orElse("");

        add(new JLabel("RUT:"),         "align label");
        add(new JLabel(c.getRut()));

        add(new JLabel("Correo:"),      "align label");
        add(new JLabel(c.getCorreo()));

        add(new JLabel("Nombres:"),     "align label");
        add(new JLabel(c.getNombres()));

        add(new JLabel("Ap. Paterno:"), "align label");
        add(new JLabel(c.getApellidoP()));

        add(new JLabel("Ap. Materno:"), "align label");
        add(new JLabel(c.getApellidoM()));

        add(new JLabel("Teléfono:"),    "align label");
        add(new JLabel(String.valueOf(c.getTelefono())));

        add(new JLabel("Edad:"),        "align label");
        add(new JLabel(String.valueOf(c.getEdad())));

        add(new JLabel("Dirección:"),   "align label");
        add(new JLabel(c.getDireccion()));

        add(new JLabel("Comuna:"),      "align label");
        add(new JLabel(descComuna));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, "span, center");

        pack();
        setLocationRelativeTo(owner);
    }
}
