/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.ClienteControlador;
import entidades.Cliente;
import validators.ClienteValidator;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author mathi
 */

/**
 * MVC – VistaAgregarCliente (Vista):
 *   • Construye el formulario de alta de cliente + cuenta
 *   • SRP: solo maneja la interfaz y eventos, delegando la lógica a los controladores
 */
public class VistaAgregarCliente extends JPanel {
    private final ClienteControlador controlador;

    // Inicialización de campos en la declaración
    private final JTextField txtRut = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtNombres = new JTextField();
    private final JTextField txtApellidoP = new JTextField();
    private final JTextField txtApellidoM = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtEdad = new JTextField();
    private final JTextField txtDireccion = new JTextField();
    private final JTextField txtComuna = new JTextField();

    // Labels para mostrar errores
    private final JLabel lblErrorRut = new JLabel();
    private final JLabel lblErrorCorreo = new JLabel();
    private final JLabel lblErrorNombres = new JLabel();
    private final JLabel lblErrorTelefono = new JLabel();
    private final JLabel lblErrorEdad = new JLabel();

    // Bordes para indicar estado de validación
    private final Border bordeNormal = BorderFactory.createLineBorder(Color.GRAY);
    private final Border bordeError = BorderFactory.createLineBorder(Color.RED);
    private final Border bordeValido = BorderFactory.createLineBorder(new Color(0, 150, 0));

    // Colores del tema
    private static final Color COLOR_PRIMARIO = new Color(237, 28, 36);    // Rojo Kaiser
    private static final Color COLOR_SECUNDARIO = new Color(33, 33, 33);   // Gris oscuro
    private static final Color COLOR_FONDO = Color.WHITE;
    private static final Color COLOR_EXITO = new Color(46, 125, 50);       // Verde
    private static final Color COLOR_ERROR = new Color(211, 47, 47);       // Rojo error

    public VistaAgregarCliente() {
        controlador = new ClienteControlador();
        setLayout(new MigLayout("insets 20, fillx, gap 10 10", "[right][grow,fill][]"));
        setBackground(COLOR_FONDO);

        // Panel principal con efecto de elevación
        JPanel mainPanel = new JPanel(new MigLayout("insets 30, fillx, gap 15 15", "[right][grow,fill][]"));
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                BorderFactory.createLineBorder(new Color(0, 0, 0, 20))
            )
        ));

        // Título
        JLabel titulo = new JLabel("Registro de Cliente");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(COLOR_PRIMARIO);
        mainPanel.add(titulo, "span, center, gapbottom 20, wrap");

        // Configurar campos
        configurarCampos();

        // Agregar campos al panel
        agregarCamposAlPanel(mainPanel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap 10", "[grow,right]"));
        buttonPanel.setOpaque(false);

        JButton btnGuardar = crearBotonEstilizado("Guardar", COLOR_PRIMARIO);
        JButton btnLimpiar = crearBotonEstilizado("Limpiar", COLOR_SECUNDARIO);

        buttonPanel.add(btnLimpiar, "split 2");
        buttonPanel.add(btnGuardar);
        mainPanel.add(buttonPanel, "span, growx, wrap");

        // Agregar el panel principal
        add(mainPanel, "grow");

        // Eventos
        btnGuardar.addActionListener(e -> guardarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Configurar validaciones
        configurarValidaciones();
    }

    private void configurarCampos() {
        // Configurar placeholders y estilos para cada campo
        configurarCampoTexto(txtRut, "Ingrese RUT");
        configurarCampoTexto(txtCorreo, "Ingrese correo electrónico");
        configurarCampoTexto(txtNombres, "Ingrese nombres");
        configurarCampoTexto(txtApellidoP, "Ingrese apellido paterno");
        configurarCampoTexto(txtApellidoM, "Ingrese apellido materno");
        configurarCampoTexto(txtTelefono, "Ingrese teléfono");
        configurarCampoTexto(txtEdad, "Ingrese edad");
        configurarCampoTexto(txtDireccion, "Ingrese dirección");
        configurarCampoTexto(txtComuna, "Ingrese comuna");
    }

    private void configurarCampoTexto(JTextField campo, String placeholder) {
        campo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        campo.putClientProperty(FlatClientProperties.STYLE, "" +
            "arc: 8;" +
            "focusWidth: 1;" +
            "focusColor: " + String.format("#%02x%02x%02x",
                COLOR_PRIMARIO.getRed(),
                COLOR_PRIMARIO.getGreen(),
                COLOR_PRIMARIO.getBlue()) + ";" +
            "borderWidth: 1");
    }

    private void configurarValidaciones() {
        configurarValidacionRut();
        configurarValidacionCorreo();
        configurarValidacionNombres();
        configurarValidacionTelefono();
        configurarValidacionEdad();
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
        agregarCampoConLabel(panel, "Comuna:", txtComuna, null);
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
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // Clase interna para el borde con sombra
    private class ShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int shadow = 3;
            for (int i = 0; i < shadow; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i * 6));
                g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, 8, 8);
            }
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }

    private void configurarLabelError(JLabel label) {
        label.setForeground(Color.RED);
        label.setFont(label.getFont().deriveFont(11f));
    }

    private void configurarValidacionRut() {
        txtRut.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (ClienteValidator.isRutValido(txtRut.getText())) {
                    txtRut.setBorder(bordeValido);
                    lblErrorRut.setText("");
                    txtRut.setText(ClienteValidator.formatearRut(txtRut.getText()));
                } else {
                    txtRut.setBorder(bordeError);
                    lblErrorRut.setText("RUT inválido");
                }
            }
        });
    }

    private void configurarValidacionCorreo() {
        txtCorreo.addFocusListener(new FocusAdapter() {
            @Override
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
    }

    private void configurarValidacionNombres() {
        FocusAdapter validadorNombre = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField campo = (JTextField) e.getComponent();
                if (ClienteValidator.isNombreValido(campo.getText())) {
                    campo.setBorder(bordeValido);
                    lblErrorNombres.setText("");
                } else {
                    campo.setBorder(bordeError);
                    lblErrorNombres.setText("Solo letras permitidas");
                }
            }
        };
        txtNombres.addFocusListener(validadorNombre);
        txtApellidoP.addFocusListener(validadorNombre);
        txtApellidoM.addFocusListener(validadorNombre);
    }

    private void configurarValidacionTelefono() {
        txtTelefono.addFocusListener(new FocusAdapter() {
            @Override
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
    }

    private void configurarValidacionEdad() {
        txtEdad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int edad = Integer.parseInt(txtEdad.getText());
                    if (ClienteValidator.isEdadValida(edad)) {
                        txtEdad.setBorder(bordeValido);
                        lblErrorEdad.setText("");
                    } else {
                        txtEdad.setBorder(bordeError);
                        lblErrorEdad.setText("Edad debe ser entre 18 y 120");
                    }
                } catch (NumberFormatException ex) {
                    txtEdad.setBorder(bordeError);
                    lblErrorEdad.setText("Ingrese un número válido");
                }
            }
        });
    }

    private void guardarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setRut(txtRut.getText());
            cliente.setCorreo(txtCorreo.getText());
            cliente.setNombres(txtNombres.getText());
            cliente.setApellidoP(txtApellidoP.getText());
            cliente.setApellidoM(txtApellidoM.getText());
            cliente.setTelefono(Long.parseLong(txtTelefono.getText()));
            cliente.setEdad(Byte.parseByte(txtEdad.getText()));
            cliente.setDireccion(txtDireccion.getText());
            cliente.setIdComuna(txtComuna.getText());

            List<String> errores = controlador.crearClienteConValidacion(cliente);
            if (errores == null) {
                JOptionPane.showMessageDialog(this,
                    "Cliente guardado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                    String.join("\n", errores),
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Por favor, revise los campos numéricos",
                "Error", JOptionPane.ERROR_MESSAGE);
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
        txtComuna.setText("");

        // Restaurar bordes solo de los JTextField
        txtRut.setBorder(bordeNormal);
        txtCorreo.setBorder(bordeNormal);
        txtNombres.setBorder(bordeNormal);
        txtApellidoP.setBorder(bordeNormal);
        txtApellidoM.setBorder(bordeNormal);
        txtTelefono.setBorder(bordeNormal);
        txtEdad.setBorder(bordeNormal);
        txtDireccion.setBorder(bordeNormal);
        txtComuna.setBorder(bordeNormal);

        // Limpiar mensajes de error
        lblErrorRut.setText("");
        lblErrorCorreo.setText("");
        lblErrorNombres.setText("");
        lblErrorTelefono.setText("");
        lblErrorEdad.setText("");

        // Efecto de transición al limpiar
        Timer timer = new Timer(50, new ActionListener() {
            float alpha = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.1f;
                if (alpha <= 0.0f) {
                    ((Timer)e.getSource()).stop();
                    setBackground(COLOR_FONDO);
                } else {
                    setBackground(new Color(
                        COLOR_FONDO.getRed()/255f,
                        COLOR_FONDO.getGreen()/255f,
                        COLOR_FONDO.getBlue()/255f,
                        alpha));
                }
                repaint();
            }
        });
        timer.start();
    }
}
