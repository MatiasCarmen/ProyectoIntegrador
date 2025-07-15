package vista;

import controladores.ActividadesControlador;
import controladores.ControladorMesa_central;
import entidades.Actividad;
import entidades.MesaCentral;
import ren.main.main;

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
    private final JButton btnRefresh = new JButton("🔄 Actualizar Agenda");

    private final JComboBox<String> comboCampo = new JComboBox<>(new String[]{"Teléfono", "Lugar"});
    private final JTextField txtFiltro = new JTextField(15);
    private final JButton btnFiltrar = new JButton("🔍 Filtrar");
    private final JButton btnLimpiar = new JButton("❌ Limpiar");

    public VistaAgenda() {
        setLayout(new MigLayout("fill", "[grow]", "[grow][]"));

        model = new DefaultTableModel(new String[]{
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

        // === Botón actualizar ===
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.setBackground(new Color(33, 150, 243));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> cargarAgenda());

        add(btnRefresh, "align right");

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
                model.addRow(new Object[]{
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
        
        cargarAgenda();
    }

  
}
