/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

import controladores.UsuarioControlador;
import entidades.Usuario;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Panel de gestión de usuarios.
 */
public class VistaUsuarios extends JPanel {

    private final UsuarioControlador ctrl = new UsuarioControlador();
    private final DefaultTableModel model;
    private final JTable tblUsuarios;
    private final JButton btnRefresh = new JButton("Actualizar Usuarios");
    private final JButton btnAgregar = new JButton("Agregar Usuario");

    public VistaUsuarios() {
        setLayout(new MigLayout("fill","[grow][pref][pref]","[grow][]"));

        model = new DefaultTableModel(new String[]{
            "ID","RUT","Rol","País","Nombres","ApellidoP","ApellidoM","Área"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsuarios = new JTable(model);
        add(new JScrollPane(tblUsuarios), "grow, wrap");

        btnAgregar.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        btnRefresh.addActionListener(e -> cargarUsuarios());
        add(btnAgregar, "split 2");
        add(btnRefresh, "wrap");

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        List<Usuario> lista = ctrl.listarUsuarios();
        model.setRowCount(0);
        lista.forEach(u -> model.addRow(new Object[]{
            u.getIdUsuario(),
            u.getRut(),
            u.getIdRol(),
            u.getIdPais(),
            u.getNombres(),
            u.getApellidoP(),
            u.getApellidoM(),
            u.getArea()
        }));
    }

    private void mostrarDialogoAgregar() {
        // Aquí puedes abrir un diálogo custom de alta de usuario
        JOptionPane.showMessageDialog(
            this,
            "Funcionalidad de agregar usuario pendiente de implementación.",
            "Agregar Usuario",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
