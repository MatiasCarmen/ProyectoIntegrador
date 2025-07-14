package vista;

import controladores.ActividadesControlador;
import entidades.Actividad;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import ren.main.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

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

    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnVerDetalle = new JButton("Ver Detalle");

    private final DefaultTableModel model;
    private final JTable tblActividades;
    private final ActividadesControlador ctrl = new ActividadesControlador();

    public VistasActividades() {
        setLayout(new BorderLayout());

       
        
        // Tabla de actividades
        String[] cols = {"IDAct", "Cuenta", "Descripción", "Fecha", "Tipo", "Razón", "Resolución"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblActividades = new JTable(model);
        tblActividades.setRowHeight(25);
        tblActividades.setSelectionBackground(new Color(220, 230, 240));
        JScrollPane scrollTabla = new JScrollPane(tblActividades);

        // Panel de botones
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.add(btnLimpiar);
        pnlButtons.add(btnVerDetalle);

        // Estilos FlatLaf
        btnVerDetalle.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnLimpiar.putClientProperty(FlatClientProperties.STYLE, "font:italic");

       
        add(scrollTabla, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerDetalle.addActionListener(e -> mostrarDetalle());

        tblActividades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarDetalle();
                }
            }
        });

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

    public void recargarTabla() {
        model.setRowCount(0);
        List<Actividad> lista = ctrl.listarPorUsuario(main.logeado.getIdUsuario());

        if (lista != null) {
            for (Actividad a : lista) {
                model.addRow(new Object[]{
                        a.getIdActividad(),
                        a.getIdCuenta(),
                        a.getDescripcion(),
                        a.getFechaCreacion(),
                        a.getTipo(),
                        a.getRazon(),
                        a.getResolucion()
                });
            }
        }
    }

    private void mostrarDetalle() {
        int row = tblActividades.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad de la tabla.");
            return;
        }

        String id = (String) model.getValueAt(row, 0);
        Actividad a = ctrl.obtenerPorId(id);
        if (a == null) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la actividad seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("PENDIENTE".equalsIgnoreCase(a.getResolucion())) {
            // Mostrar ventana para editar si está pendiente
            new VistaEditarActividad(SwingUtilities.getWindowAncestor(this), a.getIdActividad()).setVisible(true);
        } else {
            // Mostrar solo lectura
            new VistaVerActividad(SwingUtilities.getWindowAncestor(this), a.getIdActividad()).setVisible(true);
        }

        recargarTabla(); // Refrescar al volver
    }
}
