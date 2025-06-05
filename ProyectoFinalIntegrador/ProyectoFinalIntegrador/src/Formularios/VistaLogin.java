package Formularios;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;

public class VistaLogin extends JFrame {

    private JLabel lblLogo;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JLabel lblClave;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public VistaLogin() {
        initComponents();
        personalizarComponentes();
    }

    private void initComponents() {
        lblLogo = new JLabel();
        lblUsuario = new JLabel();
        txtUsuario = new JTextField();
        lblClave = new JLabel();
        txtClave = new JPasswordField();
        btnIngresar = new JButton();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Iniciar Sesión");
        setSize(350, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        lblLogo.setBounds(100, 30, 150, 150);
       ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagenes/Claro.png"));
       Image imagen = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
       lblLogo.setIcon(new ImageIcon(imagen));
        add(lblLogo);

        lblUsuario.setText("Usuario");
        lblUsuario.setBounds(50, 200, 100, 25);
        add(lblUsuario);

        txtUsuario.setBounds(50, 225, 250, 35);
        add(txtUsuario);

        lblClave.setText("Contraseña");
        lblClave.setBounds(50, 270, 100, 25);
        add(lblClave);

        txtClave.setBounds(50, 295, 250, 35);
        add(txtClave);

        btnIngresar.setText("Ingresar");
        btnIngresar.setBounds(120, 350, 100, 40);
        btnIngresar.addActionListener(e -> btnIngresarActionPerformed());
        add(btnIngresar);
    }

    private void personalizarComponentes() {
        getContentPane().setBackground(Color.WHITE);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(102, 102, 255));
        btnIngresar.setForeground(Color.WHITE);
        txtUsuario.setToolTipText("Ingresa tu usuario");
        txtClave.setToolTipText("Ingresa tu contraseña");
    }

    private void btnIngresarActionPerformed() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        if (usuario.equals("admin") && clave.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaLogin().setVisible(true));
    }
}
