package vista;

import controladores.ClienteControlador;
import controladores.ComunasControlador;
import controladores.CuentasClienteControlador;
import entidades.Cliente;
import entidades.Comuna;
import validators.ClienteValidator;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.UUID;

public class VistaAgregarCliente extends JPanel {
    private final ClienteControlador clienteControlador = new ClienteControlador();
    private final ComunasControlador comunasControlador = new ComunasControlador();

    private final JTextField txtRut = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JComboBox<String> comboComuna = new JComboBox<>();

    private final JLabel lblErrorRut = new JLabel();
    private final JLabel lblErrorCorreo = new JLabel();
    private final JLabel lblErrorNombres = new JLabel();
    private final JLabel lblErrorTelefono = new JLabel();
    private final JLabel lblErrorEdad = new JLabel();

    private final Border bordeNormal = BorderFactory.createLineBorder(Color.GRAY);
    private final Border bordeError = BorderFactory.createLineBorder(Color.RED);
    private final Border bordeValido = BorderFactory.createLineBorder(new Color(0, 150, 0));

    private static final Color COLOR_PRIMARIO = new Color(237, 28, 36);
    private static final Color COLOR_SECUNDARIO = new Color(33, 33, 33);
    private static final Color COLOR_FONDO = Color.WHITE;
    private static final Color COLOR_ERROR = new Color(211, 47, 47);

    public VistaAgregarCliente() {
        setLayout(new MigLayout("insets 20, fillx, gap 10 10", "[right][grow,fill][]"));
        setBackground(COLOR_FONDO);

        JPanel mainPanel = new JPanel(new MigLayout("insets 30, fillx, gap 15 15", "[right][grow,fill][]"));
        mainPanel.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Registro de Cliente");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(COLOR_PRIMARIO);
        mainPanel.add(titulo, "span, center, gapbottom 20, wrap");

        configurarCampos();
        llenarComboComunas();
        agregarCamposAlPanel(mainPanel);

        JButton btnGuardar = crearBotonEstilizado("Guardar", COLOR_PRIMARIO);
        JButton btnLimpiar = crearBotonEstilizado("Limpiar", COLOR_SECUNDARIO);
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap 10", "[grow,right]"));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLimpiar, "split 2");
        buttonPanel.add(btnGuardar);
        mainPanel.add(buttonPanel, "span, growx, wrap");

        add(mainPanel, "grow");

        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        configurarValidaciones();
    }

    private void configurarCampos() {
        configurarCampoTexto(txtRut, "Ingrese RUT");
        configurarCampoTexto(txtCorreo, "Ingrese correo");
        configurarCampoTexto(txtNombres, "Ingrese nombres");
        configurarCampoTexto(txtApellidoP, "Ingrese apellido paterno");
        configurarCampoTexto(txtApellidoM, "Ingrese apellido materno");
        configurarCampoTexto(txtTelefono, "Ingrese teléfono");
        configurarCampoTexto(txtEdad, "Ingrese edad");
        configurarCampoTexto(txtDireccion, "Ingrese dirección");
    }

    private void configurarCampoTexto(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.setBorder(bordeNormal);
    }

    private void llenarComboComunas() {
        List<Comuna> comunas = comunasControlador.listarComunas();
        for (Comuna c : comunas) {
            comboComuna.addItem(c.getDescripcion());
        }
    }

    private void agregarCamposAlPanel(JPanel panel) {
        agregarCampoConLabel(panel, "RUT:", txtRut, lblErrorRut);
        agregarCampoConLabel(panel, "Correo:", txtCorreo, lblErrorCorreo);
        agregarCampoConLabel(panel, "Nombres:", txtNombres, lblErrorNombres);
        agregarCampoConLabel(panel, "Apellido Paterno:", txtApellidoP, null);
        agregarCampoConLabel(panel, "Apellido Materno:", txtApellidoM, null);
        agregarCampoConLabel(panel, "Teléfono:", txtTelefono, lblErrorTelefono);
        agregarCampoConLabel(panel, "Edad:", txtEdad, lblErrorEdad);
        agregarCampoConLabel(panel, "Dirección:", txtDireccion, null);

        JLabel lblComuna = new JLabel("Comuna:");
        panel.add(lblComuna);
        panel.add(comboComuna, "span 1");
        panel.add(new JLabel(), "wrap");
    }

    private void agregarSeccion(JPanel panel, String tituloSeccion, int row) {
        JLabel labelSeccion = new JLabel(tituloSeccion);
        labelSeccion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelSeccion.setForeground(new Color(50, 50, 50));
        panel.add(labelSeccion, "cell 0 " + row + ", left, wrap");
    }

    private void agregarCampoComuna(JPanel panel, int row) {
        JLabel lblComuna = new JLabel("Comuna:");
        lblComuna.setForeground(COLOR_SECUNDARIO);
        lblComuna.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Estilizar el combo
        comboComuna.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboComuna.setBackground(Color.WHITE);
        comboComuna.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        panel.add(lblComuna, "cell 3 " + row + ", right");
        panel.add(comboComuna, "cell 4 " + row + ", growx");
    }

    private void agregarCampoConLabel(JPanel panel, String labelText, JTextField field, JLabel errorLabel) {
        JLabel label = new JLabel(labelText);
        label.setForeground(COLOR_SECUNDARIO);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(label);
        panel.add(field);

        if (errorLabel != null) {
            errorLabel.setForeground(COLOR_ERROR);
            errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panel.add(errorLabel, "wrap");
        } else {
            panel.add(new JLabel(), "wrap");
        }
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void configurarValidaciones() {
        txtRut.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String rut = txtRut.getText();
                if (ClienteValidator.isRutValido(rut)) {
                    txtRut.setText(ClienteValidator.formatearRut(rut));
                    if (clienteControlador.obtenerClientePorRut(rut) == null) {
                        txtRut.setBorder(bordeValido);
                        lblErrorRut.setText("");
                    } else {
                        txtRut.setBorder(bordeError);
                        lblErrorRut.setText("RUT ya existe");
                    }
                } else {
                    txtRut.setBorder(bordeError);
                    lblErrorRut.setText("RUT inválido");
                }
            }
        });

        txtCorreo.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (ClienteValidator.isEmailValido(txtCorreo.getText())) {
                    txtCorreo.setBorder(bordeValido);
                    lblErrorCorreo.setText("");
                } else {
                    txtCorreo.setBorder(bordeError);
                    lblErrorCorreo.setText("Correo inválido");
                }
            }
        });

        txtNombres.addFocusListener(validarNombre(txtNombres, lblErrorNombres));
        txtApellidoP.addFocusListener(validarNombre(txtApellidoP, null));
        txtApellidoM.addFocusListener(validarNombre(txtApellidoM, null));

        txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (ClienteValidator.isTelefonoValido(txtTelefono.getText())) {
                    txtTelefono.setBorder(bordeValido);
                    lblErrorTelefono.setText("");
                } else {
                    txtTelefono.setBorder(bordeError);
                    lblErrorTelefono.setText("Teléfono inválido");
                }
            }
        });

        txtEdad.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                try {
                    int edad = Integer.parseInt(txtEdad.getText());
                    if (ClienteValidator.isEdadValida(edad)) {
                        txtEdad.setBorder(bordeValido);
                        lblErrorEdad.setText("");
                    } else {
                        txtEdad.setBorder(bordeError);
                        lblErrorEdad.setText("Edad entre 18 y 120");
                    }
                } catch (NumberFormatException ex) {
                    txtEdad.setBorder(bordeError);
                    lblErrorEdad.setText("Edad inválida");
                }
            }
        });
    }

    private FocusAdapter validarNombre(JTextField campo, JLabel lblError) {
        return new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (ClienteValidator.isNombreValido(campo.getText())) {
                    campo.setBorder(bordeValido);
                    if (lblError != null)
                        lblError.setText("");
                } else {
                    campo.setBorder(bordeError);
                    if (lblError != null)
                        lblError.setText("Solo letras");
                }
            }
        };
    }

    private void guardarCliente() {
        try {
            String rut = txtRut.getText().trim();
            if (clienteControlador.obtenerClientePorRut(rut) != null) {
                JOptionPane.showMessageDialog(this, "RUT ya registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String comunaNombre = (String) comboComuna.getSelectedItem();
            String idComuna = comunasControlador.obtenerIdPorDescripcion(comunaNombre);

            Cliente cliente = new Cliente();
            cliente.setRut(rut);
            cliente.setCorreo(txtCorreo.getText());
            cliente.setNombres(txtNombres.getText());
            cliente.setApellidoP(txtApellidoP.getText());
            cliente.setApellidoM(txtApellidoM.getText());
            cliente.setTelefono(Long.parseLong(txtTelefono.getText()));
            cliente.setEdad(Byte.parseByte(txtEdad.getText()));
            cliente.setDireccion(txtDireccion.getText());
            cliente.setIdComuna(idComuna);

            List<String> errores = clienteControlador.crearClienteConValidacion(cliente);
            if (errores == null) {
                String idCuenta = CuentasClienteControlador.generarIdCuentaUnico("CLIENTE");
                new CuentasClienteControlador().insertar(idCuenta, rut, "CLIENTE");
                JOptionPane.showMessageDialog(this, "Cliente y cuenta registrados", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, String.join("\n", errores), "Errores de validación",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtRut.setText("");
        txtCorreo.setText("");
        txtNombres.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtTelefono.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        comboComuna.setSelectedIndex(0);

        txtRut.setBorder(bordeNormal);
        txtCorreo.setBorder(bordeNormal);
        txtNombres.setBorder(bordeNormal);
        txtApellidoP.setBorder(bordeNormal);
        txtApellidoM.setBorder(bordeNormal);
        txtTelefono.setBorder(bordeNormal);
        txtEdad.setBorder(bordeNormal);
        txtDireccion.setBorder(bordeNormal);

        lblErrorRut.setText("");
        lblErrorCorreo.setText("");
        lblErrorNombres.setText("");
        lblErrorTelefono.setText("");
        lblErrorEdad.setText("");
    }
}
