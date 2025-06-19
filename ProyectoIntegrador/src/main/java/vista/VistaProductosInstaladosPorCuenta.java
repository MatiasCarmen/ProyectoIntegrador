/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.ControladorProductosInstalados;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Panel con secciones colapsables por cada tipo de producto.
 */
public class VistaProductosInstaladosPorCuenta extends JPanel {
    private final ControladorProductosInstalados ctrl = new ControladorProductosInstalados();

    public VistaProductosInstaladosPorCuenta(String idCuenta) {
        setLayout(new MigLayout("fillx, wrap 1, insets 10", "[grow]"));

        JLabel title = new JLabel("Productos Instalados", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        add(title, "dock north, span");

        Map<String,List<String>> mapa = ctrl.obtenerProductosPorTipo(idCuenta);

        // Por cada tipo, crea un panel colapsable
        for (Map.Entry<String,List<String>> e : mapa.entrySet()) {
            String tipo = e.getKey();
            List<String> lista = e.getValue();

            JPanel seccion = new JPanel(new BorderLayout());
            seccion.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JToggleButton toggle = new JToggleButton(tipo + " \u25BC");
            toggle.setFont(toggle.getFont().deriveFont(Font.BOLD));
            seccion.add(toggle, BorderLayout.NORTH);

            DefaultListModel<String> lm = new DefaultListModel<>();
            lista.forEach(lm::addElement);
            JList<String> list = new JList<>(lm);
            list.setVisible(false);
            JScrollPane sp = new JScrollPane(list);
            seccion.add(sp, BorderLayout.CENTER);

            toggle.addActionListener(ae -> {
                boolean open = toggle.isSelected();
                toggle.setText(tipo + (open ? " \u25B2" : " \u25BC"));
                list.setVisible(open);
                revalidate();
            });

            add(seccion, "growx, gapy 5");
        }
    }
}
