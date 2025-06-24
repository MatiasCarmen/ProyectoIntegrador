/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// VistaProductosInstaladosPorCuenta.java
package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import controladores.ControladorProductosInstalados;
import ren.main.VistaPrincipal;

/**
 * Panel que muestra los productos instalados de una cuenta dada.
 */
public class VistaProductosInstaladosPorCuenta extends JPanel {
    private final ControladorProductosInstalados ctrl = new ControladorProductosInstalados();
    private String idCuenta;

    private final DefaultTableModel model;
    private final JTable tblProductos;
    private final JButton btnRefresh        = new JButton("Actualizar Productos");
    private final JButton btnVerActividades = new JButton("Ver Actividades");

    public VistaProductosInstaladosPorCuenta() {
        setLayout(new MigLayout("fill","[grow][pref][pref]","[grow][]"));

        model = new DefaultTableModel(new String[]{
            "Tipo","Descripción"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblProductos = new JTable(model);
        add(new JScrollPane(tblProductos), "grow, wrap");

        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarProductos());
        btnVerActividades.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnVerActividades.addActionListener(e -> {
            VistaPrincipal vp = (VistaPrincipal)
                SwingUtilities.getAncestorOfClass(VistaPrincipal.class, this);
            if (vp != null) vp.mostrarActividades();
        });

        add(btnRefresh, "split 2");
        add(btnVerActividades, "wrap");
    }

    /** Debe llamarse antes de cargar los datos. */
    public void setCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    /** Carga los productos instalados para la cuenta actual. */
    public void cargarProductos() {
        if (idCuenta == null) return;
        Map<String, List<String>> map = ctrl.obtenerProductosPorTipo(idCuenta);
        model.setRowCount(0);
        map.forEach((tipo, lista) ->
            lista.forEach(desc ->
                model.addRow(new Object[]{ tipo, desc })
            )
        );
    }

    public void actualizarTabla() {
        try {
            // Usar el método existente que ya funciona en tu implementación
            Map<String, List<String>> productosMap = ctrl.obtenerProductosPorTipo(idCuenta);

            // Limpiar la tabla actual
            model.setRowCount(0);

            // Agregar los productos usando el formato de map que ya tienes implementado
            productosMap.forEach((tipo, lista) -> {
                lista.forEach(descripcion -> {
                    model.addRow(new Object[]{
                        tipo,          // PLAN / ADICIONAL / DESCUENTO
                        descripcion    // Nombre o descripción del producto
                    });
                });
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar la tabla: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
