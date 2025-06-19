/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mathi
 */
package vista;

import entidades.Actividad;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Muestra todos los datos de una Actividad en modo lectura.
 */
public class DetalleActividadDialog extends JDialog {
    public DetalleActividadDialog(Window owner, Actividad a) {
        super(owner, "Detalle Actividad", ModalityType.APPLICATION_MODAL);
        setLayout(new MigLayout("wrap 2, insets 10", "[right][grow]"));

        add(new JLabel("ID Actividad:"),        "align label");
        add(new JLabel(a.getIdActividad()));

        add(new JLabel("Cuenta:"),              "align label");
        add(new JLabel(a.getIdCuenta()));

        add(new JLabel("Descripción:"),         "align label");
        JTextArea desc = new JTextArea(a.getDescripcion(),3,20);
        desc.setEditable(false);
        add(new JScrollPane(desc), "growx");

        add(new JLabel("Fecha Creación:"),      "align label");
        add(new JLabel(a.getFechaCreacion().toString()));

        add(new JLabel("Fecha Cierre:"),        "align label");
        add(new JLabel(a.getFechaCierre()!=null ? a.getFechaCierre().toString() : ""));

        add(new JLabel("Tipo:"),                "align label");
        add(new JLabel(a.getTipo()));

        add(new JLabel("Razón:"),               "align label");
        add(new JLabel(a.getRazon()));

        add(new JLabel("Detalle:"),             "align label");
        add(new JLabel(a.getDetalle()));

        add(new JLabel("Resolución:"),          "align label");
        add(new JLabel(a.getResolucion()));

        add(new JLabel("Comentarios:"),         "align label");
        JTextArea comm = new JTextArea(a.getComentarios(),3,20);
        comm.setEditable(false);
        add(new JScrollPane(comm), "growx");

        add(new JLabel("Teléfono:"),            "align label");
        add(new JLabel(a.getTelefono().toString()));

        add(new JLabel("Correo:"),              "align label");
        add(new JLabel(a.getCorreo()));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, "span, center");

        pack();
        setLocationRelativeTo(owner);
    }
}
