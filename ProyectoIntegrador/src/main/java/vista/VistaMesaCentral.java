/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/* VistaMesaCentral.java */
package vista;

import controladores.ControladorMesa_central;
import entidades.MesaCentral;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Panel de Mesa Central para actividades.
 */
public class VistaMesaCentral extends JPanel {

    private final ControladorMesa_central ctrl = new ControladorMesa_central();
    private final DefaultTableModel model;
    private final JTable tblMesa;
    private final JButton btnRefresh = new JButton("Actualizar Mesa Central");

    public VistaMesaCentral() {
        setLayout(new MigLayout("fill","[grow]","[grow][]"));

        model = new DefaultTableModel(new String[]{
            "ID Actividad","Teléfono","Lugar"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblMesa = new JTable(model);
        add(new JScrollPane(tblMesa), "grow, wrap");

        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarMesaCentral());
        add(btnRefresh, "align right");

        cargarMesaCentral();
    }

    private void cargarMesaCentral() {
        // Si tu controlador tuviera listarTodos, lo usarías aquí.
        // Por ahora, un ejemplo de llamada a obtenerMesaCentral por cada actividad conocida.
        model.setRowCount(0);
        List<MesaCentral> lista = List.of(); // placeholder
        lista.forEach(m -> model.addRow(new Object[]{
            m.getIdActividad(),
            m.getTelefono(),
            m.getLugar()
        }));
    }

    public void actualizarTabla() {
        try {
            // Limpiar la tabla actual
            DefaultTableModel model = (DefaultTableModel) tblMesa.getModel();
            model.setRowCount(0);

            // Obtener datos actualizados
            List<MesaCentral> registros = ctrl.listarTodos();

            // Agregar los nuevos datos
            for (MesaCentral registro : registros) {
                model.addRow(new Object[]{
                    registro.getIdActividad(),
                    registro.getTelefono(),
                    registro.getLugar()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar la tabla: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
