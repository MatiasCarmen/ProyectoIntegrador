/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mathi
 */
package vista;

import controladores.ActividadesControlador;  
import entidades.Actividad;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ren.main.main;

/**
 * Panel para registrar y listar Actividades.
 */
public class VistasActividades extends JPanel {
    private final JTextField txtIdCuenta = new JTextField();
    private final JTextArea txtDescripcion = new JTextArea(3, 20);
    private final JTextField txtTipo = new JTextField();
    private final JTextField txtRazon = new JTextField();
    private final JTextField txtDetalle = new JTextField();
    private final JTextField txtResolucion = new JTextField();
    private final JTextArea txtComentarios = new JTextArea(3, 20);
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtCorreo = new JTextField();

    private final JButton btnRegistrar = new JButton("Registrar");
    private final JButton btnLimpiar   = new JButton("Limpiar");
    private final JButton btnVerDetalle= new JButton("Ver detalle");

    private final DefaultTableModel model;
    private final JTable tblActividades;

    private final ActividadesControlador ctrl = new ActividadesControlador();

    public VistasActividades() {
        setLayout(new BorderLayout(10,10));
        JPanel pnlForm = new JPanel(new MigLayout("wrap 2, insets 10", "[right][grow]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar Actividad"));

        pnlForm.add(new JLabel("ID Cuenta:"));
        pnlForm.add(txtIdCuenta, "growx");
        pnlForm.add(new JLabel("Descripción:"));
        pnlForm.add(new JScrollPane(txtDescripcion), "growx");
        pnlForm.add(new JLabel("Tipo:"));
        pnlForm.add(txtTipo, "growx");
        pnlForm.add(new JLabel("Razón:"));
        pnlForm.add(txtRazon, "growx");
        pnlForm.add(new JLabel("Detalle:"));
        pnlForm.add(txtDetalle, "growx");
        pnlForm.add(new JLabel("Resolución:"));
        pnlForm.add(txtResolucion, "growx");
        pnlForm.add(new JLabel("Comentarios:"));
        pnlForm.add(new JScrollPane(txtComentarios), "growx");
        pnlForm.add(new JLabel("Teléfono:"));
        pnlForm.add(txtTelefono, "growx");
        pnlForm.add(new JLabel("Correo:"));
        pnlForm.add(txtCorreo, "growx");

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.add(btnLimpiar);
        pnlButtons.add(btnRegistrar);
        pnlButtons.add(btnVerDetalle);

        String[] cols = {"IDAct","Cuenta","Desc","Fecha","Tipo","Razón"};
        model = new DefaultTableModel(cols,0) {
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        tblActividades = new JTable(model);
        tblActividades.setRowHeight(24);

        add(pnlForm, BorderLayout.NORTH);
        add(pnlButtons, BorderLayout.CENTER);
        add(new JScrollPane(tblActividades), BorderLayout.SOUTH);

        // Estilos FlatLaf
        btnRegistrar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnVerDetalle.putClientProperty(FlatClientProperties.STYLE, "font:italic");

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegistrar.addActionListener(e -> registrarActividad());
        btnVerDetalle.addActionListener(e -> mostrarDetalle());

        recargarTabla();
    }

    private void limpiarFormulario() {
        txtIdCuenta.setText("");
        txtDescripcion.setText("");
        txtTipo.setText("");
        txtRazon.setText("");
        txtDetalle.setText("");
        txtResolucion.setText("");
        txtComentarios.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

   private void registrarActividad() {
        String idAct = "A" + System.currentTimeMillis();
        
        LocalDateTime ldt = LocalDateTime.now();


Timestamp tsCreacion = Timestamp.valueOf(ldt);

Timestamp tsCierre   = Timestamp.valueOf(ldt.plusDays(1));
        Actividad a = new Actividad(
            idAct,
            txtIdCuenta.getText().trim(),
            txtDescripcion.getText().trim(),
           tsCreacion,
            tsCierre,
            txtTipo.getText().trim(),
            txtRazon.getText().trim(),
            txtDetalle.getText().trim(),
            txtResolucion.getText().trim(),
            txtComentarios.getText().trim(),
            Long.parseLong(txtTelefono.getText().trim()),
            txtCorreo.getText().trim(),
                main.logeado.getIdUsuario()
        );
        if (ctrl.crearActividad(a)) {
            JOptionPane.showMessageDialog(this, "Actividad registrada");
            recargarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recargarTabla() {
        model.setRowCount(0);
        List<Actividad> lista = ctrl.listarPorCuenta(txtIdCuenta.getText().trim());

        if (lista != null) {
            lista.forEach(a -> {
                if (a != null) {
                    model.addRow(new Object[]{
                        a.getIdActividad(),
                        a.getIdCuenta(),
                        a.getDescripcion(),
                        a.getFechaCreacion(),
                        a.getTipo(),
                        a.getRazon()
                    });
                }
            });
            System.out.println("Actividades cargadas: " + lista.size());
        } else {
            System.out.println("No se encontraron actividades para la cuenta");
        }
    }

    private void mostrarDetalle() {
        int row = tblActividades.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad primero");
            return;
        }
        String id = (String)model.getValueAt(row,0);
        Actividad a = ctrl.obtenerPorId(id);
        if (a != null) {
            new DetalleActividadDialog(SwingUtilities.getWindowAncestor(this), a).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo cargar el detalle de la actividad",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
