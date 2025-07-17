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

    private final DefaultTableModel model;
    private final JTable tblActividades;
    private final ActividadesControlador ctrl = new ActividadesControlador();

    private final JTextField txtFiltro = new JTextField(15);
    private final JComboBox<String> comboCampo = new JComboBox<>(new String[] {
            "ID Actividad", "Cuenta", "Descripción", "Tipo", "Razón", "Resolución"
    });
    private final JButton btnFiltrar = new JButton("Filtrar");
    private final JButton btnLimpiar = new JButton("Limpiar Filtro");
    private final JButton btnVerDetalle = new JButton("Ver Detalle");

    public VistasActividades() {
        setLayout(new BorderLayout());

        // Tabla de actividades
        String[] cols = { "IDAct", "Cuenta", "Descripción", "Fecha", "Tipo", "Razón", "Resolución" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblActividades = new JTable(model);
        tblActividades.setRowHeight(25);
        tblActividades.setSelectionBackground(new Color(220, 230, 240));

        JScrollPane scrollTabla = new JScrollPane(tblActividades);

        // Panel de botones y filtros
        JPanel pnlInferior = new JPanel(new BorderLayout());

        JPanel pnlFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar Actividades"));

        pnlFiltros.add(new JLabel("Campo:"));
        pnlFiltros.add(comboCampo);
        pnlFiltros.add(new JLabel("Valor:"));
        pnlFiltros.add(txtFiltro);
        pnlFiltros.add(btnFiltrar);
        pnlFiltros.add(btnLimpiar);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotones.add(btnVerDetalle);

        pnlInferior.add(pnlFiltros, BorderLayout.WEST);
        pnlInferior.add(pnlBotones, BorderLayout.EAST);

        // Estilos
        btnFiltrar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnVerDetalle.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnLimpiar.putClientProperty(FlatClientProperties.STYLE, "font:italic");

        // Agregar componentes
        add(scrollTabla, BorderLayout.CENTER);
        add(pnlInferior, BorderLayout.SOUTH);

        // Eventos
        btnLimpiar.addActionListener(e -> {
            txtFiltro.setText("");
            recargarTabla();
        });

        btnFiltrar.addActionListener(e -> aplicarFiltro());

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

    public void recargarTabla() {
        model.setRowCount(0);
        List<Actividad> lista = ctrl.listarPorUsuario(main.logeado.getIdUsuario());
        if (lista != null) {
            for (Actividad a : lista) {
                model.addRow(new Object[] {
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
            JOptionPane.showMessageDialog(this, "No se pudo obtener la actividad seleccionada.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("PENDIENTE".equalsIgnoreCase(a.getResolucion()) && a.getIdUsuario().equals(main.logeado.getIdUsuario())) {
            new VistaEditarActividad(SwingUtilities.getWindowAncestor(this), a.getIdActividad()).setVisible(true);
        } else {
            new VistaVerActividad(SwingUtilities.getWindowAncestor(this), a.getIdActividad()).setVisible(true);
        }
        recargarTabla(); // Refrescar al volver
    }

    private void aplicarFiltro() {
        String campoSeleccionado = comboCampo.getSelectedItem().toString();
        String valorFiltro = txtFiltro.getText().trim().toLowerCase();

        if (valorFiltro.isEmpty()) {
            recargarTabla();
            return;
        }

        int columna = switch (campoSeleccionado) {
            case "ID Actividad" -> 0;
            case "Cuenta" -> 1;
            case "Descripción" -> 2;
            case "Tipo" -> 4;
            case "Razón" -> 5;
            case "Resolución" -> 6;
            default -> -1;
        };

        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String valorCelda = model.getValueAt(i, columna).toString().toLowerCase();
            if (!valorCelda.contains(valorFiltro)) {
                model.removeRow(i);
            }
        }
    }
}