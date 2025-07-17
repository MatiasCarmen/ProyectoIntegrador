package vista;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.ImageIcon;
import controladores.ControladorUsuarios;
import entidades.Usuario;
import net.miginfocom.swing.MigLayout;
import ren.main.VistaPrincipal;
import vista.tema.TemaAdministrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ren.main.main;
import utils.BCryptUtil;

public class Login extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
    private JLabel lblEstado;
    private JLabel logoLabel;

    private final ControladorUsuarios controlador = new ControladorUsuarios();

    public Login(JFrame loginFrame) {
        // Configuracion de la ventana
        loginFrame.setUndecorated(true);
        init(loginFrame);

        // Ajusta el tamaño para que se adapte mejor :b
        loginFrame.setSize(500, 700);
        loginFrame.setLocationRelativeTo(null);

        // Crear la barra de título personalizada
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        titleBar.setOpaque(false);

        // Botón minimizar
        JButton minButton = new JButton("−");
        minButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        minButton.setForeground(new Color(150, 150, 150));
        minButton.setBorderPainted(false);
        minButton.setContentAreaFilled(false);
        minButton.setFocusPainted(false);
        minButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minButton.addActionListener(e -> loginFrame.setState(Frame.ICONIFIED));
        minButton.addMouseListener(createButtonHoverEffect(minButton));

        // Botón maximizar
        JButton maxButton = new JButton("□");
        maxButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        maxButton.setForeground(new Color(150, 150, 150));
        maxButton.setBorderPainted(false);
        maxButton.setContentAreaFilled(false);
        maxButton.setFocusPainted(false);
        maxButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        maxButton.addActionListener(e -> {
            if (loginFrame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
                loginFrame.setExtendedState(Frame.NORMAL);
            } else {
                loginFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });
        maxButton.addMouseListener(createButtonHoverEffect(maxButton));

        // Botón cerrar
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        closeButton.setForeground(new Color(150, 150, 150));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.addMouseListener(createButtonHoverEffect(closeButton));

        // Agregar botones a la barra de título
        titleBar.add(minButton);
        titleBar.add(maxButton);
        titleBar.add(closeButton);

        // Agregar la barra de título al panel principal
        JPanel mainPanel = (JPanel) getComponent(0);
        JPanel contentPanel = (JPanel) mainPanel.getComponent(0);
        contentPanel.add(titleBar, "pos 280 5 100% 35");

        // Hacer que la ventana sea movible
        MouseAdapter moveAdapter = new MouseAdapter() {
            private Point initialClick;
            private boolean isMaximized = false;

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                isMaximized = loginFrame.getExtendedState() == Frame.MAXIMIZED_BOTH;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isMaximized) {
                    loginFrame.setExtendedState(Frame.NORMAL);
                    return;
                }
                int thisX = loginFrame.getLocation().x;
                int thisY = loginFrame.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                loginFrame.setLocation(thisX + xMoved, thisY + yMoved);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (loginFrame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
                        loginFrame.setExtendedState(Frame.NORMAL);
                    } else {
                        loginFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
                    }
                }
            }
        };

        addMouseListener(moveAdapter);
        addMouseMotionListener(moveAdapter);
    }

    private void init(JFrame loginFrame) {
        setLayout(new MigLayout("wrap,fillx,insets 0", "[fill]"));

        // Panel principal MEJORADO CON DEPENDECIAS PIOLAS
        JPanel mainPanel = new JPanel(new MigLayout("wrap,fillx,insets 10", "[fill]")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente más suave
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(250, 250, 250),
                        0, getHeight(), new Color(245, 245, 245));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Agregar patrón sutil
                g2.setColor(new Color(237, 28, 36, 10));
                for (int i = 0; i < getHeight(); i += 20) {
                    g2.drawLine(0, i, getWidth(), i);
                }

                g2.dispose();
            }
        };
        mainPanel.setOpaque(false);

        // Panel de contenido con sombra y bordes más suaves
        vista.util.UIHelper.ElevatedPanel contentPanel = new vista.util.UIHelper.ElevatedPanel();
        contentPanel.setLayout(new MigLayout("wrap,fillx,insets 30", "[center]"));
        contentPanel.setBackground(Color.WHITE);

        // LOGO de claro obvio
        logoLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagenes/logo_claro.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledImage));
        contentPanel.add(logoLabel, "align center, gapbottom 20");

        // Título de bienvenida
        JLabel lblTitle = new JLabel("¡Bienvenido!");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(237, 28, 36));
        contentPanel.add(lblTitle, "gapbottom 5");

        JLabel lblSubtitle = new JLabel("Ingresa a tu cuenta");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(130, 130, 130));
        contentPanel.add(lblSubtitle, "gapbottom 25");

        // Campos de entrada ajustados
        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuario");
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:8;" +
                "borderWidth:1;" +
                "focusWidth:1;" +
                "innerFocusWidth:1;" +
                "focusColor:#ED1C24");
        contentPanel.add(txtUsername, "width 280!, height 40!, gapbottom 15");

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Contraseña");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:8;" +
                "borderWidth:1;" +
                "focusWidth:1;" +
                "innerFocusWidth:1;" +
                "focusColor:#ED1C24;" +
                "showRevealButton:true");
        contentPanel.add(txtPassword, "width 280!, height 40!, gapbottom 10");

        // Panel de opciones
        JPanel optionsPanel = new JPanel(new MigLayout("insets 0", "[left][push,right]"));
        optionsPanel.setOpaque(false);

        chRememberMe = new JCheckBox("Recordarme");
        chRememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chRememberMe.setForeground(new Color(100, 100, 100));
        optionsPanel.add(chRememberMe);

        JLabel lblForgot = new JLabel("¿Olvidaste tu contraseña?");
        lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblForgot.setForeground(new Color(237, 28, 36));
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionsPanel.add(lblForgot);

        contentPanel.add(optionsPanel, "width 280!, gapbottom 20");

        // Botón de login
        cmdLogin = new JButton("Iniciar Sesión");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:#ED1C24;" +
                "foreground:#FFFFFF;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "arc:8;" +
                "font:bold +1");

        cmdLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                        "background:#D31920;" +
                        "foreground:#FFFFFF;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "arc:8;" +
                        "font:bold +1");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                        "background:#ED1C24;" +
                        "foreground:#FFFFFF;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "arc:8;" +
                        "font:bold +1");
            }
        });

        contentPanel.add(cmdLogin, "width 280!, height 42!, gapbottom 15");

        // Label de estado ajustado
        lblEstado = new JLabel(" ");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        contentPanel.add(lblEstado);

        // Agregar el panel de contenido con mejor espaciado
        mainPanel.add(contentPanel, "width 420!, align center, gaptop 30");
        add(mainPanel, "grow");

        // esto le da animacion al login, transiciones mas que nada, a nada
        cmdLogin.addActionListener(e -> handleLogin(loginFrame));

        // Manejar Enter en campos
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
        txtPassword.addActionListener(e -> cmdLogin.doClick());
    }

    private void handleLogin(JFrame loginFrame) {
        cmdLogin.setEnabled(false);
        lblEstado.setText("Verificando credenciales...");
        lblEstado.setForeground(Color.GRAY);

        Timer timer = new Timer(500, evt -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            System.out.println(BCryptUtil.hashPassword(password));

            Usuario usuario = controlador.validarLogin(username, password);
            if (usuario != null) {
                main.logeado = usuario;
                lblEstado.setText("¡Bienvenido!");
                lblEstado.setForeground(new Color(46, 125, 50));

                // Crear y configurar la ventana principal ANTES de hacerla visible
                VistaPrincipal vp = new VistaPrincipal();
                vp.setLocationRelativeTo(null);
                vp.setUndecorated(true); // Configurar ANTES de setVisible
                vp.setOpacity(0.0f);
                vp.setVisible(true);

                // Animación de fade-out para el login
                Timer fadeOutTimer = new Timer(20, new ActionListener() {
                    float opacity = 1.0f;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        opacity -= 0.1f;
                        if (opacity <= 0.0f) {
                            ((Timer) e.getSource()).stop();
                            loginFrame.dispose();

                            // Animación de fade-in para la ventana principal
                            Timer fadeInMainTimer = new Timer(20, e2 -> {
                                float mainOpacity = vp.getOpacity();
                                mainOpacity += 0.1f;
                                if (mainOpacity >= 1.0f) {
                                    mainOpacity = 1.0f;
                                    ((Timer) e2.getSource()).stop();
                                }
                                vp.setOpacity(mainOpacity);
                            });
                            fadeInMainTimer.start();
                        }
                        try {
                            loginFrame.setOpacity(Math.max(opacity, 0.0f));
                        } catch (IllegalComponentStateException ex) {
                            // Si hay un error con la opacidad, simplemente cerrar la ventana
                            loginFrame.dispose();
                            vp.setOpacity(1.0f);
                        }
                    }
                });
                fadeOutTimer.start();
            } else {
                lblEstado.setText("Usuario o contraseña incorrectos :c no chambeas aca");
                lblEstado.setForeground(new Color(198, 40, 40));
                cmdLogin.setEnabled(true);
                txtPassword.setText("");
            }
            ((Timer) evt.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private MouseAdapter createButtonHoverEffect(JButton button) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setForeground(new Color(237, 28, 36));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setForeground(new Color(150, 150, 150));
            }
        };
    }
}