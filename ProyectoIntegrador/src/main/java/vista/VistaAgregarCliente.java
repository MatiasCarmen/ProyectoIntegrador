package vista;

import controladores.ClienteControlador;
import controladores.ComunasControlador;
import controladores.CuentasClienteControlador;
import entidades.Cliente;
import entidades.Comuna;
import validators.ClienteValidator;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
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

    private final Border bordeNormal = BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true);
    private final Border bordeError = BorderFactory.createLineBorder(new Color(211, 47, 47), 2, true);
    private final Border bordeValido = BorderFactory.createLineBorder(new Color(46, 125, 50), 2, true);

    private static final Color COLOR_PRIMARIO = new Color(237, 28, 36);
    private static final Color COLOR_SECUNDARIO = new Color(33, 33, 33);
    private static final Color COLOR_FONDO = Color.WHITE;
    private static final Color COLOR_ERROR = new Color(211, 47, 47);
    private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

    public VistaAgregarCliente() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Panel de título con icono SVG
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(245, 245, 245));
        JLabel icon = new JLabel(new FlatSVGIcon("imagenes/icons/add-client.svg", 32, 32));
        JLabel title = new JLabel("Registro de Cliente");
        title.setFont(FUENTE_TITULO);
        title.setForeground(COLOR_SECUNDARIO);
        titlePanel.add(icon);
        titlePanel.add(Box.createHorizontalStrut(10));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Panel principal elevado
        JPanel mainPanel = new vista.util.UIHelper.ElevatedPanel();
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new MigLayout("wrap 2, gap 20 10", "[right][grow,fill]"));

        configurarCampos();
        llenarComboComunas();
        agregarCamposAlPanel(mainPanel);

        // Separador visual
        mainPanel.add(new JSeparator(), "span, growx, gaptop 10, gapbottom 10");

        // Botones grandes con iconos SVG
        JButton btnGuardar = new JButton("Guardar", new FlatSVGIcon("imagenes/icons/save.svg", 18, 18));
        JButton btnLimpiar = new JButton("Limpiar", new FlatSVGIcon("imagenes/icons/clear.svg", 18, 18));
        btnGuardar.setBackground(new Color(33, 150, 243));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(FUENTE_BOTON);
        btnGuardar.setPreferredSize(new Dimension(160, 40));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.putClientProperty(FlatClientProperties.STYLE, "arc:16");

        btnLimpiar.setBackground(new Color(158, 158, 158));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(FUENTE_BOTON);
        btnLimpiar.setPreferredSize(new Dimension(160, 40));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpiar.putClientProperty(FlatClientProperties.STYLE, "arc:16");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnLimpiar);
        buttonPanel.add(btnGuardar);
        mainPanel.add(buttonPanel, "span, growx, gaptop 10");

        add(mainPanel, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        configurarValidaciones();
    }

    private void configurarCampos() {
        configurarCampoTexto(txtRut, "Ej: 12.345.678-9", "Ingrese el RUT del cliente");
        configurarCampoTexto(txtCorreo, "Ej: correo@ejemplo.com", "Ingrese el correo electrónico");
        configurarCampoTexto(txtNombres, "Ej: Juan Carlos", "Ingrese los nombres del cliente");
        configurarCampoTexto(txtApellidoP, "Ej: Pérez", "Ingrese el apellido paterno");
        configurarCampoTexto(txtApellidoM, "Ej: González", "Ingrese el apellido materno");
        configurarCampoTexto(txtTelefono, "Ej: 912345678", "Ingrese el teléfono (9 dígitos)");
        configurarCampoTexto(txtEdad, "Ej: 30", "Ingrese la edad (18-120)");
        configurarCampoTexto(txtDireccion, "Ej: Av. Siempre Viva 123", "Ingrese la dirección");
    }

    private void configurarCampoTexto(JTextField campo, String placeholder, String tooltip) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.STYLE, "arc:12; borderWidth:2;");
        campo.setBorder(bordeNormal);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campo.setToolTipText(tooltip);
    }

    private void llenarComboComunas() {
        List<Comuna> comunas = comunasControlador.listarComunas();
        for (Comuna c : comunas) {
            comboComuna.addItem(c.getDescripcion());
        }
        comboComuna.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboComuna.setBackground(Color.WHITE);
        comboComuna.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        comboComuna.setToolTipText("Seleccione la comuna de residencia");
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
        lblComuna.setFont(FUENTE_LABEL);
        panel.add(lblComuna);
        panel.add(comboComuna, "wrap");
    }

    private void agregarCampoConLabel(JPanel panel, String labelText, JTextField field, JLabel errorLabel) {
        JLabel label = new JLabel(labelText);
        label.setFont(FUENTE_LABEL);
        label.setForeground(COLOR_SECUNDARIO);
        panel.add(label);
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        fieldPanel.add(field, BorderLayout.CENTER);
        if (errorLabel != null) {
            errorLabel.setForeground(COLOR_ERROR);
            errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            errorLabel.setBorder(new EmptyBorder(2, 8, 0, 0));
            fieldPanel.add(errorLabel, BorderLayout.SOUTH);
        }
        panel.add(fieldPanel, "wrap");
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
