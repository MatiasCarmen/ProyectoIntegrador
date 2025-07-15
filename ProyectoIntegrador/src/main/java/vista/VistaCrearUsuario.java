package vista;


import controladores.RolControlador;
import controladores.UsuarioControlador;
import entidades.Usuario;
import net.miginfocom.swing.MigLayout;
import utils.BCryptUtil;
import validators.UsuarioValidator;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class VistaCrearUsuario extends JPanel {

    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtRut = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JComboBox<String> cmbRoles;
    private final JButton btnGuardar = new JButton("Guardar");

    private final Map<String, String> rolesMap;
    private final UsuarioControlador controlador = new UsuarioControlador();

    public VistaCrearUsuario() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        rolesMap = new LinkedHashMap<>();
        rolesMap.put("D001", "Usuario");
        rolesMap.put("S001", "Supervisor");
        // Se excluye R001 (Administrador) de las opciones

        DefaultComboBoxModel<String> modelRoles = new DefaultComboBoxModel<>();
        modelRoles.addElement("-- Seleccione rol --");
        for (String desc : rolesMap.values()) modelRoles.addElement(desc);
        cmbRoles = new JComboBox<>(modelRoles);

        JPanel formPanel = new JPanel(new MigLayout("wrap 2, fillx, insets 20", "[right][grow]"));
        formPanel.setBackground(Color.WHITE);

        addField(formPanel, "Nombres:", txtNombres);
        addField(formPanel, "Apellido Paterno:", txtApellidoP);
        addField(formPanel, "Apellido Materno:", txtApellidoM);
        addField(formPanel, "RUT:", txtRut);
        addField(formPanel, "Clave:", txtClave);
        formPanel.add(new JLabel("Rol:"));
        formPanel.add(cmbRoles, "growx");

        btnGuardar.setBackground(new Color(237, 28, 36));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        btnGuardar.addMouseListener(new Hover(btnGuardar));
        btnGuardar.addActionListener(e -> guardarUsuario());

        formPanel.add(btnGuardar, "span 2, center, gaptop 20");

        JPanel wrapper = new JPanel(new MigLayout("fill, insets 30"));
        wrapper.setBackground(new Color(240, 242, 245));
        wrapper.add(formPanel, "grow");

        JLabel titulo = new JLabel("Crear Nuevo Usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);

        JPanel header = new JPanel();
        header.setBackground(new Color(198, 23, 30));
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.add(titulo, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    private void guardarUsuario() {
        String nombres = txtNombres.getText().trim();
        String apellidoP = txtApellidoP.getText().trim();
        String apellidoM = txtApellidoM.getText().trim();
        String rut = txtRut.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        String rolSeleccionado = (String) cmbRoles.getSelectedItem();

        // Validaciones básicas
        if (!UsuarioValidator.validarCampos(nombres, apellidoP, rut, clave)) {
            JOptionPane.showMessageDialog(this, "Completa los campos correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rolSeleccionado == null || rolSeleccionado.equals("-- Seleccione rol --")) {
            JOptionPane.showMessageDialog(this, "Selecciona un rol válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID de rol desde descripción
        String rolId = rolesMap.entrySet().stream()
                .filter(e -> e.getValue().equals(rolSeleccionado))
                .map(Map.Entry::getKey)
                .findFirst().orElse("");
      //Aqui

        if (rolId.equals("R001")) {
            JOptionPane.showMessageDialog(this, "No está permitido crear usuarios Administradores.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Generar ID automáticamente
        String idUsuario = controlador.generarIdUsuario(nombres, apellidoP);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setIdUsuario(idUsuario);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidoP(apellidoP);
        nuevoUsuario.setApellidoM(apellidoM);
        nuevoUsuario.setRut(rut);
        nuevoUsuario.setClave(BCryptUtil.hashPassword(clave));
        nuevoUsuario.setIdRol(rolId);
        nuevoUsuario.setIdPais("CHL");
        nuevoUsuario.setArea("");

        try {
            controlador.crearUsuario(nuevoUsuario);
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente con ID: " + idUsuario, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtRut.setText("");
        txtClave.setText("");
        cmbRoles.setSelectedIndex(0);
    }

    private void addField(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field, "growx");
        field.setPreferredSize(new Dimension(200, 40));
    }

    private static class Hover extends MouseAdapter {
        private final JButton btn;

        public Hover(JButton btn) {
            this.btn = btn;
        }

        public void mouseEntered(MouseEvent e) {
            btn.setBackground(new Color(178, 0, 0));
        }

        public void mouseExited(MouseEvent e) {
            btn.setBackground(new Color(237, 28, 36));
        }
    }
}