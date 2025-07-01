/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/* VistaAgenda.java */
package vista;

import controladores.ActividadesControlador;  // Corregido el paquete a minúsculas
import entidades.Actividad;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel de agenda con actividades por fecha.
 */
public class VistaAgenda extends JPanel {

    private final ActividadesControlador ctrl = new ActividadesControlador();
    private final DefaultTableModel model;
    private final JTable tblAgenda;
    private final JButton btnRefresh = new JButton("Actualizar Agenda");

    public VistaAgenda() {
        setLayout(new MigLayout("fill","[grow]","[grow][]"));

        model = new DefaultTableModel(new String[]{
            "Fecha Creación","ID Actividad","Descripción","Tipo"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblAgenda = new JTable(model);
        add(new JScrollPane(tblAgenda), "grow, wrap");

        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarAgenda());
        add(btnRefresh, "align right");

        cargarAgenda();
    }

    private void cargarAgenda() {
        // Para mostrar todas las actividades, necesitarías un método específico.
        // Aquí usamos listarPorCuenta con null como placeholder.
        List<Actividad> lista = ctrl.listarPorCuenta(null);
        model.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
        lista.forEach(a -> model.addRow(new Object[]{
            a.getFechaCreacion(),
            a.getIdActividad(),
            a.getDescripcion(),
            a.getTipo()
        }));
    }

    public void actualizarAgenda() {
        try {
            // Actualizar la vista de la agenda con los datos más recientes
            List<Actividad> actividades = ctrl.listarPorCuenta(null); // Usando el método existente
            mostrarActividades(actividades);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar la agenda: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarActividades(List<Actividad> actividades) {
        // Limpiar la vista actual
        model.setRowCount(0);

        // Agregar cada actividad a la tabla usando los atributos correctos de la entidad
        for (Actividad actividad : actividades) {
            model.addRow(new Object[]{
                actividad.getFechaCreacion(),
                actividad.getIdActividad(),
                actividad.getDescripcion(),
                actividad.getTipo(),
                actividad.getRazon(),
                actividad.getResolucion()
            });
        }
    }

    private void cargarActividades() {
        List<Actividad> lista = ctrl.listarPorCuenta(null);
        model.setRowCount(0);
        lista.forEach(a -> model.addRow(new Object[]{
            a.getFechaCreacion(),
            a.getIdActividad(),
            a.getDescripcion(),
            a.getTipo()
        }));
    }
}
