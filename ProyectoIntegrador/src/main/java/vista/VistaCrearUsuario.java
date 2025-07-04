package vista;

/**
 *
 * @author mathi
 */
import controladores.ControladorUsuarios;
import entidades.Usuario;
import net.miginfocom.swing.MigLayout;
import validators.ClienteValidator;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.geom.RoundRectangle2D;

public class VistaCrearUsuario extends JPanel {
    private final JTextField txtIdUsuario;
    private final JTextField txtNombres;
    private final JTextField txtApellidoP;
    private final JTextField txtApellidoM;
    private final JTextField txtRut;
    private final JPasswordField txtClave;
    private final Map<String, String> rolesMap;
    private final JComboBox<String> cmbRoles;
    private final JButton btnGuardar;
    private final ControladorUsuarios controlador;
    private final JLabel lblErrorId;
    private final JLabel lblErrorNombres;
    private final JLabel lblErrorRut;
    private final Border bordeNormal;
    private final Border bordeError;
    private final Border bordeValido; 

    public VistaCrearUsuario() {
        // Inicializar controlador
        controlador = new ControladorUsuarios();

        // Inicializar mapa de roles (ID -> descripción)
        rolesMap = new LinkedHashMap<>();
        rolesMap.put("D001", "Usuario");
        rolesMap.put("R001", "Administrador");
        rolesMap.put("S001", "Supervisor");

        // Bordes y etiquetas de error
        bordeNormal = BorderFactory.createLineBorder(Color.GRAY);
        bordeError = BorderFactory.createLineBorder(new Color(211, 47, 47), 2); // Borde rojo más grueso
        bordeValido = BorderFactory.createLineBorder(new Color(34, 197, 94), 2); // Borde verde
        lblErrorId = new JLabel();
        lblErrorId.setForeground(new Color(211, 47, 47));
        lblErrorNombres = new JLabel();
        lblErrorNombres.setForeground(new Color(211, 47, 47));
        lblErrorRut = new JLabel();
        lblErrorRut.setForeground(new Color(211, 47, 47));

        // Configuración del panel principal
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245));

        // Panel header con diseño moderno
        JPanel headerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente principal con colores de Claro (rojo)
                GradientPaint gp = new GradientPaint(0, 0, new Color(237, 28, 36), // Rojo Claro
                        getWidth(), 0, new Color(198, 23, 30)); // Rojo oscuro
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Formas decorativas
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth() - 100, getHeight() - 100, 200, 200);

                g2d.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(getWidth(), 150));
        headerPanel.setLayout(new BorderLayout());

        // Título con diseño moderno
        JLabel lblTitulo = new JLabel("Crear Nuevo Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.CENTER);

        // Panel de contenido principal (tarjeta flotante)
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        cardPanel.setBackground(Color.WHITE);

        // Panel del formulario con nuevo diseño
        JPanel formPanel = new JPanel(new MigLayout("wrap 2, fillx, insets 20", "[right][grow]"));
        formPanel.setBackground(Color.WHITE);

        // Crear componentes
        txtIdUsuario = new JTextField(20);
        txtNombres = new JTextField(20);
        txtApellidoP = new JTextField(20);
        txtApellidoM = new JTextField(20);
        txtRut = new JTextField(20);
        txtClave = new JPasswordField(20);
        // Combo para selección de rol
        DefaultComboBoxModel<String> modelRoles = new DefaultComboBoxModel<>();
        modelRoles.addElement("-- Seleccione rol --");
        for (String desc : rolesMap.values()) {
            modelRoles.addElement(desc);
        }
        cmbRoles = new JComboBox<>(modelRoles);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(237, 28, 36));
        btnGuardar.setForeground(Color.WHITE);

        // Estilizar componentes
        styleTextField(txtIdUsuario);
        styleTextField(txtNombres);
        styleTextField(txtApellidoP);
        styleTextField(txtApellidoM);
        styleTextField(txtRut);
        styleTextField(txtClave);
        styleComboBok(cmbRoles);

        // Botón moderno con colores de Claro
        btnGuardar.setPreferredSize(new Dimension(200, 45));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnGuardar.setBackground(new Color(237, 28, 36)); // Rojo Claro
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addMouseListener(new ButtonHoverEffect(btnGuardar));

        // Agregar componentes
        formPanel.add(new JLabel("Rol:"), "gapy 10");
        formPanel.add(cmbRoles, "growx, gapy 10");
        formPanel.add(new JLabel("ID Usuario:"), "gapy 10");
        formPanel.add(txtIdUsuario, "growx, gapy 10");
        formPanel.add(lblErrorId, "span 2, growx, wrap");
        formPanel.add(new JLabel("Nombres:"), "gapy 10");
        formPanel.add(txtNombres, "growx, gapy 10");
        formPanel.add(lblErrorNombres, "span 2, growx, wrap");
        formPanel.add(new JLabel("Apellido Paterno:"), "gapy 10");
        formPanel.add(txtApellidoP, "growx, gapy 10");
        formPanel.add(new JLabel("Apellido Materno:"), "gapy 10");
        formPanel.add(txtApellidoM, "growx, gapy 10");
        formPanel.add(new JLabel("RUT:"), "gapy 10");
        formPanel.add(txtRut, "growx, gapy 10");
        formPanel.add(lblErrorRut, "span 2, growx, wrap");
        formPanel.add(new JLabel("Clave:"), "gapy 10");
        formPanel.add(txtClave, "growx, gapy 10");
        formPanel.add(btnGuardar, "span 2, center, gaptop 20");

        // Agregar el panel del formulario al panel de tarjeta
        cardPanel.add(formPanel, BorderLayout.CENTER);

        // Agregar todo al panel principal
        JPanel wrapperPanel = new JPanel(new MigLayout("fill, insets 30"));
        wrapperPanel.setBackground(new Color(240, 242, 245));
        wrapperPanel.add(cardPanel, "grow");

        // Agregar header y contenido al contenedor principal
        add(headerPanel, BorderLayout.NORTH);
        add(wrapperPanel, BorderLayout.CENTER);

        // Configurar acción del botón
        btnGuardar.addActionListener(e -> guardarUsuario());

        // Configurar validaciones
        configurarValidaciones();

        // Aplicar estilos profesionales a componentes
        aplicarEstiloProfesional(formPanel);
    }

    private void configurarValidaciones() {
        txtIdUsuario.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarIdUsuario();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarIdUsuario();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarIdUsuario();
            }
        });
        txtNombres.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarNombres();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarNombres();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarNombres();
            }
        });
        txtRut.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validarRut();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validarRut();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validarRut();
            }
        });
    }

    private void validarIdUsuario() {
        String idUsuario = txtIdUsuario.getText().trim();
        if (idUsuario.isEmpty()) {
            txtIdUsuario.setBorder(bordeError);
            lblErrorId.setText("ID requerido");
        } else {
            txtIdUsuario.setBorder(bordeValido); // Borde verde si es válido
            lblErrorId.setText("");
        }
    }

    private void validarNombres() {
        String nombres = txtNombres.getText().trim();
        if (nombres.isEmpty()) {
            txtNombres.setBorder(bordeError);
            lblErrorNombres.setText("Nombre requerido");
        } else {
            txtNombres.setBorder(bordeValido); // Borde verde si es válido
            lblErrorNombres.setText("");
        }
    }

    private void validarRut() {
        String rut = txtRut.getText().trim();
        if (!ClienteValidator.isRutValido(rut)) {
            txtRut.setBorder(bordeError);
            lblErrorRut.setText("RUT inválido");
        } else {
            txtRut.setBorder(bordeValido); // Borde verde si es válido
            lblErrorRut.setText("");
        }
    }

    private void guardarUsuario() {
        String idUsuario = txtIdUsuario.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidoP = txtApellidoP.getText().trim();
        String apellidoM = txtApellidoM.getText().trim();
        String rut = txtRut.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();
        String selectedDesc = (String) cmbRoles.getSelectedItem();

        boolean valido = true;
        if (idUsuario.isEmpty()) {
            txtIdUsuario.setBorder(bordeError);
            lblErrorId.setText("ID requerido");
            valido = false;
        }
        if (nombres.isEmpty()) {
            txtNombres.setBorder(bordeError);
            lblErrorNombres.setText("Nombre requerido");
            valido = false;
        }
        if (!ClienteValidator.isRutValido(rut)) {
            txtRut.setBorder(bordeError);
            lblErrorRut.setText("RUT inválido");
            valido = false;
        }
        if (selectedDesc == null || "-- Seleccione rol --".equals(selectedDesc)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un rol válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!valido) {
            return;
        }
        // Mapear descripción a ID
        String rolId = rolesMap.entrySet().stream()
                .filter(e -> e.getValue().equals(selectedDesc))
                .map(Map.Entry::getKey)
                .findFirst().orElse("");

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setIdUsuario(idUsuario);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidoP(apellidoP);
        nuevoUsuario.setApellidoM(apellidoM);
        nuevoUsuario.setRut(rut);
        nuevoUsuario.setClave(clave);
        nuevoUsuario.setIdRol(rolId);
        nuevoUsuario.setIdPais("CHL");
        nuevoUsuario.setArea("");

        try {
            if (controlador.validarLogin(idUsuario, clave) != null) {
                JOptionPane.showMessageDialog(this,
                        "El ID de usuario ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamar al nuevo método crearUsuario en lugar de validarLogin
            controlador.crearUsuario(nuevoUsuario);
            JOptionPane.showMessageDialog(this,
                    "Usuario creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (SQLException e) {
            e.printStackTrace();
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
        cmbRoles.setSelectedIndex(0);
        txtIdUsuario.requestFocus();

        // Resetear bordes y etiquetas de error
        txtIdUsuario.setBorder(bordeNormal);
        txtNombres.setBorder(bordeNormal);
        txtRut.setBorder(bordeNormal);
        lblErrorId.setText("");
        lblErrorNombres.setText("");
        lblErrorRut.setText("");
    }

    // Método actualizado para aplicar el nuevo estilo profesional
    private void aplicarEstiloProfesional(JPanel formPanel) {
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 16); // Letra más grande y en negrita para etiquetas
        Font fontField = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontButton = new Font("Segoe UI", Font.BOLD, 18);
        Font fontError = new Font("Segoe UI", Font.ITALIC, 14); // Fuente para mensajes de error

        // Ajustar fuentes a campos de texto
        txtIdUsuario.setFont(fontField);
        txtNombres.setFont(fontField);
        txtApellidoP.setFont(fontField);
        txtApellidoM.setFont(fontField);
        txtRut.setFont(fontField);
        txtClave.setFont(fontField);

        // Ajustar fuente al botón
        btnGuardar.setFont(fontButton);

        // Ajustar fuentes a etiquetas de error
        lblErrorId.setFont(fontError);
        lblErrorNombres.setFont(fontError);
        lblErrorRut.setFont(fontError);

        // Ajustar las etiquetas del formulario
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JLabel) {
                if (comp != lblErrorId && comp != lblErrorNombres && comp != lblErrorRut) {
                    comp.setFont(fontLabel);
                }
            }
        }
    }

    // Nuevos métodos para estilos
    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setBackground(new Color(249, 250, 251));
    }

    private void styleComboBok(JComboBox<?> combo) {
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, 40));
        combo.setBackground(new Color(249, 250, 251));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    // Clase para efecto hover en botón
    private class ButtonHoverEffect extends MouseAdapter {
        private final JButton button;

        public ButtonHoverEffect(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(198, 23, 30)); // Rojo oscuro al pasar el mouse
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(237, 28, 36)); // Rojo Claro al salir
        }
    }

    // Clase para borde con sombra
    private static class ShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(0, 0, 0, 20));
            g2d.setStroke(new BasicStroke(4));
            RoundRectangle2D rect = new RoundRectangle2D.Float(x + 2, y + 2, width - 4, height - 4, 20, 20);
            g2d.draw(rect);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = 4;
            return insets;
        }
    }
}
