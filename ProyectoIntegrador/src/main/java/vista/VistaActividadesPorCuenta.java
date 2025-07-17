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
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatClientProperties;
import ren.main.VistaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import reportes.ReporteGenerator;

/**
 * Panel que muestra las actividades de una cuenta dada.
 */
public class VistaActividadesPorCuenta extends JPanel {
    private final ActividadesControlador ctrl = new ActividadesControlador();
    private String idCuenta;

    private final DefaultTableModel model;
    private final JTable tblActividades;
    private final JButton btnRefresh = new JButton("Actualizar Actividades");
    private final JButton btnVerProductos = new JButton("Ver Productos");
    private final JButton btnExportarExcel = new JButton("Exportar a Excel");

    public VistaActividadesPorCuenta() {
        setLayout(new MigLayout("fill", "[grow][pref][pref]", "[grow][]"));

        model = new DefaultTableModel(new String[] {
                "ID Actividad", "Descripción", "Fecha Creación", "Fecha Cierre", "Tipo", "Razón"
        }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblActividades = new JTable(model);
        add(new JScrollPane(tblActividades), "grow, wrap");

        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarActividades());
        btnVerProductos.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnVerProductos.addActionListener(e -> {
            VistaPrincipal vp = (VistaPrincipal) SwingUtilities.getAncestorOfClass(VistaPrincipal.class, this);
            if (vp != null)
                vp.mostrarProductos();
        });

        add(btnRefresh, "split 3");
        add(btnVerProductos);
        btnExportarExcel.setBackground(new Color(46, 125, 50));
        btnExportarExcel.setForeground(Color.WHITE);
        btnExportarExcel.setFocusPainted(false);
        btnExportarExcel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExportarExcel.addActionListener(e -> exportarExcelActividades());
        add(btnExportarExcel, "wrap");
    }

    /** Debe llamarse antes de cargar los datos. */
    public void setCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    /** Carga las actividades para la cuenta actual. */
    public void cargarActividades() {
        if (idCuenta == null) {
            System.out.println("No hay cuenta seleccionada para cargar actividades");
            return;
        }
        List<Actividad> lista = ctrl.listarPorCuenta(idCuenta);
        model.setRowCount(0);

        if (lista != null) {
            lista.forEach(a -> {
                if (a != null) {
                    model.addRow(new Object[] {
                            a.getIdActividad(),
                            a.getDescripcion(),
                            a.getFechaCreacion(),
                            a.getFechaCierre(),
                            a.getTipo(),
                            a.getRazon()
                    });
                }
            });
            System.out.println("Actividades cargadas para cuenta " + idCuenta + ": " + lista.size());
        } else {
            System.out.println("No se encontraron actividades para la cuenta " + idCuenta);
        }
    }

    public void actualizarTabla() {
        try {
            if (idCuenta == null) {
                System.out.println("No hay cuenta seleccionada para actualizar");
                return;
            }

            // Limpiar la tabla actual
            model.setRowCount(0);

            // Obtener datos actualizados
            List<Actividad> actividades = ctrl.listarPorCuenta(idCuenta);

            if (actividades != null) {
                for (Actividad actividad : actividades) {
                    if (actividad != null) {
                        model.addRow(new Object[] {
                                actividad.getIdActividad(),
                                actividad.getDescripcion(),
                                actividad.getFechaCreacion(),
                                actividad.getFechaCierre(),
                                actividad.getTipo(),
                                actividad.getRazon()
                        });
                    }
                }
                System.out.println("Tabla actualizada: " + actividades.size() + " actividades");
            } else {
                System.out.println("No se obtuvieron actividades al actualizar");
            }
        } catch (Exception e) {
            System.err.println("Error actualizando tabla: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la tabla de actividades: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarExcelActividades() {
        String[] columnas = { "ID Actividad", "Descripción", "Fecha Creación", "Fecha Cierre", "Tipo", "Razón" };
        java.util.List<Object[]> datos = new java.util.ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object[] fila = new Object[columnas.length];
            for (int j = 0; j < columnas.length; j++) {
                fila[j] = model.getValueAt(i, j);
            }
            datos.add(fila);
        }
        String archivo = reportes.ReporteGenerator.exportarListaAExcel("Actividades", columnas, datos);
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (archivo != null) {
            vista.util.UIHelper.showToast(parent, "Reporte de actividades exportado exitosamente", false);
        } else {
            vista.util.UIHelper.showToast(parent, "Error al exportar el reporte de actividades", true);
        }
    }
}
