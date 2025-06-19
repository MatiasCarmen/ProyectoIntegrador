/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Controladores.ActividadesControlador;
import entidades.Actividad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel que lista las actividades de una cuenta específica.
 */
public class VistaActividadesPorCuenta extends JPanel {
    private final DefaultTableModel model = 
        new DefaultTableModel(new String[]{"ID","Tipo","Creación","Cierre","Descripción"},0);
    private final JTable tbl = new JTable(model);
    private final ActividadesControlador ctrl = new ActividadesControlador();

    public VistaActividadesPorCuenta(String idCuenta) {
        setLayout(new BorderLayout());
        add(new JScrollPane(tbl), BorderLayout.CENTER);
        recargar(idCuenta);
    }

    private void recargar(String idCuenta) {
        model.setRowCount(0);
        List<Actividad> lista = ctrl.listarPorCuenta(idCuenta);
        lista.forEach(a -> model.addRow(new Object[]{
            a.getIdActividad(),
            a.getTipo(),
            a.getFechaCreacion(),
            a.getFechaCierre(),
            a.getDescripcion()
        }));
    }
}
