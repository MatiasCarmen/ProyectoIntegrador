package vista;
/**
 *
 * @author mathi
 */
import controladores.ControladorUsuarios;
import entidades.Usuario;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class VistaCrearUsuario extends JPanel {
    private final JTextField txtIdUsuario;
    private final JTextField txtNombres;
    private final JTextField txtApellidoP;
    private final JTextField txtApellidoM;
    private final JTextField txtRut;
    private final JPasswordField txtClave;
    private final JButton btnGuardar;
    private final ControladorUsuarios controlador;

    public VistaCrearUsuario() {
        setLayout(new MigLayout("wrap 2, fillx, insets 20", "[right][grow]"));
        setBackground(Color.WHITE);

        // Inicializar controlador
        controlador = new ControladorUsuarios();

        // Crear componentes
        JLabel lblTitulo = new JLabel("Crear Nuevo Usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(237, 28, 36));

        txtIdUsuario = new JTextField(20);
        txtNombres = new JTextField(20);
        txtApellidoP = new JTextField(20);
        txtApellidoM = new JTextField(20);
        txtRut = new JTextField(20);
        txtClave = new JPasswordField(20);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(237, 28, 36));
        btnGuardar.setForeground(Color.WHITE);

        // Agregar componentes
        add(lblTitulo, "span 2, center, gapbottom 20");
        add(new JLabel("ID Usuario:"));
        add(txtIdUsuario, "growx");
        add(new JLabel("Nombres:"));
        add(txtNombres, "growx");
        add(new JLabel("Apellido Paterno:"));
        add(txtApellidoP, "growx");
        add(new JLabel("Apellido Materno:"));
        add(txtApellidoM, "growx");
        add(new JLabel("RUT:"));
        add(txtRut, "growx");
        add(new JLabel("Clave:"));
        add(txtClave, "growx");
        add(btnGuardar, "span 2, center, gaptop 20");

        // Configurar acción del botón
        btnGuardar.addActionListener(e -> guardarUsuario());
    }

    private void guardarUsuario() {
        String idUsuario = txtIdUsuario.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidoP = txtApellidoP.getText().trim();
        String apellidoM = txtApellidoM.getText().trim();
        String rut = txtRut.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (idUsuario.isEmpty() || nombres.isEmpty() || apellidoP.isEmpty() || rut.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete los campos obligatorios",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setIdUsuario(idUsuario);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidoP(apellidoP);
        nuevoUsuario.setApellidoM(apellidoM);
        nuevoUsuario.setRut(rut);
        nuevoUsuario.setClave(clave);

        try {
            if (controlador.validarLogin(idUsuario, clave) != null) {
                JOptionPane.showMessageDialog(this,
                    "El ID de usuario ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Usar el método existente de tu controlador para crear el usuario
            controlador.validarLogin(idUsuario, clave);
            JOptionPane.showMessageDialog(this,
                "Usuario creado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al crear el usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombres.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtRut.setText("");
        txtClave.setText("");
        txtIdUsuario.requestFocus();
    }
}
