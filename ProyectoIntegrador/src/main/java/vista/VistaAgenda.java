package vista;

import controladores.ActividadesControlador;
import controladores.ControladorMesa_central;
import entidades.Actividad;
import entidades.MesaCentral;
import ren.main.main;
import validators.MesaCentralValidator;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class VistaAgenda extends JPanel {

    private final DefaultTableModel model;
    private final JTable tblAgenda;
    private final JButton btnRefresh = new JButton("Actualizar Agenda");

    private final JComboBox<String> comboCampo = new JComboBox<>(new String[] { "Teléfono", "Lugar" });
    private final JTextField txtFiltro = new JTextField(15);
    private final JButton btnFiltrar = new JButton("Filtrar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnEditar = new JButton("Editar");

    private final ActividadesControlador actividadesCtrl = new ActividadesControlador();

    public VistaAgenda() {
        setLayout(new MigLayout("fill", "[grow]", "[grow][]"));

        model = new DefaultTableModel(new String[] {
                "ID Actividad", "Teléfono", "Lugar", "Fecha"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarAgenda();
            }
        });

        tblAgenda = new JTable(model);
        tblAgenda.setRowHeight(28);
        tblAgenda.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tblAgenda);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Agenda (Mesa Central) del Usuario"));
        add(scrollPane, "grow, wrap");

        // === Panel de filtros ===
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar Agenda"));

        panelFiltros.add(new JLabel("Campo:"));
        panelFiltros.add(comboCampo);
        panelFiltros.add(new JLabel("Valor:"));
        panelFiltros.add(txtFiltro);
        panelFiltros.add(btnFiltrar);
        panelFiltros.add(btnLimpiar);

        btnFiltrar.setBackground(new Color(33, 150, 243));
        btnFiltrar.setForeground(Color.WHITE);

        btnLimpiar.setBackground(new Color(158, 158, 158));
        btnLimpiar.setForeground(Color.WHITE);

        btnFiltrar.addActionListener(e -> aplicarFiltro());
        btnLimpiar.addActionListener(e -> {
            txtFiltro.setText("");
            cargarAgenda();
        });

        add(panelFiltros, "dock north");

        // === Botón actualizar y editar ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.setBackground(new Color(33, 150, 243));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> cargarAgenda());

        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.addActionListener(e -> editarRegistro());

        panelBotones.add(btnEditar);
        panelBotones.add(btnRefresh);
        add(panelBotones, "dock south");

        cargarAgenda();
    }

    public void cargarAgenda() {
        model.setRowCount(0);

        String idUsuario = main.logeado.getIdUsuario();
        ControladorMesa_central mesaCtrl = new ControladorMesa_central();
        ActividadesControlador actividadesCtrl = new ActividadesControlador();

        List<Actividad> actividades = actividadesCtrl.listarPorUsuario(idUsuario);

        for (Actividad a : actividades) {
            MesaCentral mesa = mesaCtrl.obtenerMesaCentral(a.getIdActividad());
            if (mesa != null) {
                model.addRow(new Object[] {
                        a.getIdActividad(),
                        mesa.getTelefono(),
                        mesa.getLugar(),
                        a.getFechaCreacion()
                });
            }
        }
    }

    private void aplicarFiltro() {
        String campo = comboCampo.getSelectedItem().toString();
        String valor = txtFiltro.getText().trim().toLowerCase();

        if (valor.isEmpty()) {
            cargarAgenda();
            return;
        }

        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String celda = model.getValueAt(i, campo.equals("Teléfono") ? 1 : 2).toString().toLowerCase();
            if (!celda.contains(valor)) {
                model.removeRow(i);
            }
        }
    }

    private void editarRegistro() {
        int fila = tblAgenda.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idActividad = model.getValueAt(fila, 0).toString();

        // Validar que el usuario logueado sea dueño de la actividad
        Actividad actividad = actividadesCtrl.obtenerPorId(idActividad);
        if (!actividad.getIdUsuario().equals(main.logeado.getIdUsuario())) {
            JOptionPane.showMessageDialog(this, "No puedes editar una actividad que no te pertenece.",
                    "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String telefonoActual = model.getValueAt(fila, 1).toString();
        String lugarActual = model.getValueAt(fila, 2).toString();

        JTextField txtTelefono = new JTextField(telefonoActual);
        JTextField txtLugar = new JTextField(lugarActual);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Lugar:"));
        panel.add(txtLugar);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Editar Mesa Central", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoTelefono = txtTelefono.getText().trim();
            String nuevoLugar = txtLugar.getText().trim();

            List<String> errores = MesaCentralValidator.validarMesaCentral(nuevoTelefono, nuevoLugar);

            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        String.join("\n", errores),
                        "Errores de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ControladorMesa_central mesaCtrl = new ControladorMesa_central();
            boolean actualizado = mesaCtrl
                    .actualizarMesaCentral(new MesaCentral(idActividad, Long.parseLong(nuevoTelefono), nuevoLugar));

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarAgenda();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el registro.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
