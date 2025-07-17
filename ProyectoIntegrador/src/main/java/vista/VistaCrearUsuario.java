package vista;

import controladores.RolControlador;
import controladores.UsuarioControlador;
import entidades.Rol;
import entidades.Usuario;
import net.miginfocom.swing.MigLayout;
import utils.BCryptUtil;
import validators.UsuarioValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class VistaCrearUsuario extends JPanel {
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtRut = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();

    private final JComboBox<String> cmbRoles;
    private final JComboBox<String> cmbArea;
    private final String[] AREAS = { "Ventas", "Soporte", "Logística", "TI", "RRHH", "Operaciones" };

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnNuevo = new JButton("Nuevo");

    private final JTable tblUsuarios;
    private final DefaultTableModel model;
    private String idUsuarioActual = null;

    private final UsuarioControlador controlador = new UsuarioControlador();

    public VistaCrearUsuario() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // ComboBox de roles
        DefaultComboBoxModel<String> modelRoles = new DefaultComboBoxModel<>();
        modelRoles.addElement("-- Seleccione rol --");
        for (Rol rol : RolControlador.listarRoles()) {
            if (!rol.getNombre().equals("Administrador")) {
                modelRoles.addElement(rol.getNombre());
            }
        }
        cmbRoles = new JComboBox<>(modelRoles);
        cmbArea = new JComboBox<>(AREAS);

        // Panel de formulario
        JPanel formPanel = new JPanel(new MigLayout("wrap 2, fillx", "[right][grow]"));
        formPanel.setBackground(Color.WHITE);
        addField(formPanel, "Nombres:", txtNombres);
        addField(formPanel, "Apellido Paterno:", txtApellidoP);
        addField(formPanel, "Apellido Materno:", txtApellidoM);
        addField(formPanel, "RUT:", txtRut);
        addField(formPanel, "Clave:", txtClave);
        formPanel.add(new JLabel("Rol:"));
        formPanel.add(cmbRoles, "growx");
        formPanel.add(new JLabel("Área:"));
        formPanel.add(cmbArea, "growx");

        // Botones
        btnGuardar.setBackground(new Color(237, 28, 36));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        btnGuardar.addActionListener(e -> guardarUsuario());

        btnNuevo.setBackground(new Color(100, 149, 237));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setPreferredSize(new Dimension(120, 40));
        btnNuevo.addActionListener(e -> limpiarCampos());

        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtns.setBackground(Color.WHITE);
        pnlBtns.add(btnGuardar);
        pnlBtns.add(btnNuevo);
        formPanel.add(pnlBtns, "span 2, center, gaptop 10");

        // Tabla
        model = new DefaultTableModel(
                new String[] { "ID", "Nombre", "Apellido", "RUT", "Rol", "Área", "Fecha Creación" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblUsuarios = new JTable(model);
        tblUsuarios.setRowHeight(25);
        tblUsuarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cargarUsuarioSeleccionado();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblUsuarios);
        scroll.setBorder(BorderFactory.createTitledBorder("Usuarios existentes"));

        // Título
        JLabel titulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);

        JPanel header = new JPanel();
        header.setBackground(new Color(198, 23, 30));
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.add(titulo, BorderLayout.CENTER);

        // Envolver
        JPanel wrapper = new JPanel(new MigLayout("fill, insets 20", "[grow]"));
        wrapper.setBackground(new Color(240, 242, 245));
        wrapper.add(formPanel, "grow, wrap");
        wrapper.add(scroll, "grow");

        add(header, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);

        cargarUsuarios();
    }

    private void guardarUsuario() {
        String nombres = txtNombres.getText().trim();
        String apellidoP = txtApellidoP.getText().trim();
        String apellidoM = txtApellidoM.getText().trim();
        String rut = txtRut.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        String rolNombre = (String) cmbRoles.getSelectedItem();
        String area = (String) cmbArea.getSelectedItem();

        if (!UsuarioValidator.validarCampos(nombres, apellidoP, rut, clave)) {
            JOptionPane.showMessageDialog(this, "Datos incompletos o inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rolNombre == null || rolNombre.equals("-- Seleccione rol --")) {
            JOptionPane.showMessageDialog(this, "Selecciona un rol válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rolId = RolControlador.obtenerIdPorNombre(rolNombre);

        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidoP(apellidoP);
        usuario.setApellidoM(apellidoM);
        usuario.setRut(rut);
        usuario.setClave(BCryptUtil.hashPassword(clave));
        usuario.setIdRol(rolId);
        usuario.setIdPais("CHL");
        usuario.setArea(area);

        try {
            if (idUsuarioActual == null) {
                String nuevoId = controlador.generarIdUsuario(nombres, apellidoP);
                usuario.setIdUsuario(nuevoId);
                controlador.crearUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuario creado con ID: " + nuevoId);
            } else {
                usuario.setIdUsuario(idUsuarioActual);
                Usuario existente = controlador.obtenerPorId(idUsuarioActual);
                if (existente != null && existente.getIdRol().equals("R001")) {
                    JOptionPane.showMessageDialog(this, "No se puede editar un usuario Administrador.");
                    return;
                }
                controlador.actualizarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            }
            limpiarCampos();
            cargarUsuarios();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarUsuarioSeleccionado() {
        int row = tblUsuarios.getSelectedRow();
        if (row >= 0) {
            idUsuarioActual = model.getValueAt(row, 0).toString();
            Usuario usuario = controlador.obtenerPorId(idUsuarioActual);
            if (usuario == null)
                return;

            if (usuario.getIdRol().equals("R001")) {
                JOptionPane.showMessageDialog(this, "No puedes editar un usuario Administrador.");
                return;
            }

            txtNombres.setText(usuario.getNombres());
            txtApellidoP.setText(usuario.getApellidoP());
            txtApellidoM.setText(usuario.getApellidoM());
            txtRut.setText(usuario.getRut());
            txtClave.setText("");
            cmbRoles.setSelectedItem(RolControlador.obtenerNombrePorId(usuario.getIdRol()));
            cmbArea.setSelectedItem(usuario.getArea() != null ? usuario.getArea() : AREAS[0]);
        }
    }

    private void cargarUsuarios() {
        model.setRowCount(0);
        List<Usuario> lista = controlador.listarUsuarios();
        for (Usuario u : lista) {
            model.addRow(new Object[] {
                    u.getIdUsuario(),
                    u.getNombres(),
                    u.getApellidoP(),
                    u.getRut(),
                    u.getIdRol(),
                    u.getArea(),
                    u.getFechaCreacion() != null ? u.getFechaCreacion().toString() : ""
            });
        }
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtRut.setText("");
        txtClave.setText("");
        cmbRoles.setSelectedIndex(0);
        cmbArea.setSelectedIndex(0);
        idUsuarioActual = null;
        tblUsuarios.clearSelection();
    }

    private void addField(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field, "growx");
        field.setPreferredSize(new Dimension(200, 40));
    }
}
