/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package vista;

/**
 *
 * @author mathi
 */

import controladores.UsuarioControlador;
import entidades.Usuario;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.Frame;
import java.awt.Window;
import java.awt.Dialog;

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
        setLayout(new MigLayout("fill", "[grow][pref][pref]", "[grow][]"));

        model = new DefaultTableModel(new String[] {
                "ID", "RUT", "Rol", "País", "Nombres", "ApellidoP", "ApellidoM", "Área"
        }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
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

        if (lista != null) {
            lista.forEach(u -> {
                if (u != null) {
                    model.addRow(new Object[] {
                            u.getIdUsuario(),
                            u.getRut(),
                            u.getIdRol(),
                            u.getIdPais(),
                            u.getNombres(),
                            u.getApellidoP(),
                            u.getApellidoM(),
                            u.getArea()
                    });
                }
            });
        }
        System.out.println("Usuarios cargados: " + (lista != null ? lista.size() : 0));
    }

    private void mostrarDialogoAgregar() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof ren.main.VistaPrincipal vistaPrincipal) {
            vistaPrincipal.mostrarPanel(ren.main.VistaPrincipal.TARJETA_CREAR_USUARIO);
        }
    }

    public void actualizarTabla() {
        model.setRowCount(0);

        try {
            List<Usuario> usuarios = ctrl.listarUsuarios();
            if (usuarios != null) {
                for (Usuario usuario : usuarios) {
                    if (usuario != null) {
                        model.addRow(new Object[] {
                                usuario.getIdUsuario(),
                                usuario.getRut(),
                                usuario.getIdRol(),
                                usuario.getIdPais(),
                                usuario.getNombres(),
                                usuario.getApellidoP(),
                                usuario.getApellidoM(),
                                usuario.getArea()
                        });
                    }
                }
                System.out.println("Tabla de usuarios actualizada: " + usuarios.size() + " registros");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la tabla de usuarios: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
