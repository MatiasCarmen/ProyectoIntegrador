/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// VistaActividadesPorCuenta.java
package vista;

import Controladores.ActividadesControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import entidades.Actividad;
import ren.main.VistaPrincipal;

/**
 * Panel que muestra las actividades de una cuenta dada.
 */
public class VistaActividadesPorCuenta extends JPanel {
    private final ActividadesControlador ctrl = new ActividadesControlador();
    private String idCuenta;

    private final DefaultTableModel model;
    private final JTable tblActividades;
    private final JButton btnRefresh       = new JButton("Actualizar Actividades");
    private final JButton btnVerProductos  = new JButton("Ver Productos");

    public VistaActividadesPorCuenta() {
        setLayout(new MigLayout("fill","[grow][pref][pref]","[grow][]"));

        model = new DefaultTableModel(new String[]{
            "ID Actividad","Descripción","Fecha Creación","Fecha Cierre","Tipo","Razón"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblActividades = new JTable(model);
        add(new JScrollPane(tblActividades), "grow, wrap");

        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarActividades());
        btnVerProductos.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnVerProductos.addActionListener(e -> {
            VistaPrincipal vp = (VistaPrincipal)
                SwingUtilities.getAncestorOfClass(VistaPrincipal.class, this);
            if (vp != null) vp.mostrarProductos();
        });

        add(btnRefresh, "split 2");
        add(btnVerProductos, "wrap");
    }

    /** Debe llamarse antes de cargar los datos. */
    public void setCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    /** Carga las actividades para la cuenta actual. */
    public void cargarActividades() {
        if (idCuenta == null) return;
        List<Actividad> lista = ctrl.listarPorCuenta(idCuenta);
        model.setRowCount(0);
        lista.forEach(a -> model.addRow(new Object[]{
            a.getIdActividad(),
            a.getDescripcion(),
            a.getFechaCreacion(),
            a.getFechaCierre(),
            a.getTipo(),
            a.getRazon()
        }));
    }
}
