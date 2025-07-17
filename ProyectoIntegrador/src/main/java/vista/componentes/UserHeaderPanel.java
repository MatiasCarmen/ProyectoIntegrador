package vista.componentes;

import entidades.Usuario;
import utils.PermisoUtils;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

/**
 * Panel para mostrar información del usuario en el header
 * SOLID - SRP: Única responsabilidad de mostrar información del usuario
 */
public class UserHeaderPanel extends JPanel {

    private final JLabel lblNombre;
    private final JLabel lblRol;
    private final NotificacionPanel notificacionPanel;

    public UserHeaderPanel() {
        this.lblNombre = new JLabel();
        this.lblRol = new JLabel();
        this.notificacionPanel = new NotificacionPanel();

        initComponents();
        actualizarInformacionUsuario();
    }

    private void initComponents() {
        setLayout(new MigLayout("insets 10 15", "[grow][pref][pref]", "[pref]"));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)));

        // Panel de información del usuario
        JPanel userInfoPanel = new JPanel(new MigLayout("insets 0", "[pref]", "[pref][pref]"));
        userInfoPanel.setOpaque(false);

        // Nombre del usuario
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(new Color(50, 50, 50));
        userInfoPanel.add(lblNombre, "growx");

        // Rol del usuario
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRol.setForeground(new Color(100, 100, 100));
        userInfoPanel.add(lblRol, "growx");

        // Separador
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 30));
        separator.setBackground(new Color(200, 200, 200));

        // Agregar componentes al panel principal
        add(userInfoPanel, "growx");
        add(separator);
        add(notificacionPanel);
    }

    /**
     * Actualiza la información del usuario logueado
     */
    public void actualizarInformacionUsuario() {
        Usuario usuario = ren.main.main.logeado;

        if (usuario != null) {
            // Mostrar nombre completo
            String nombreCompleto = usuario.getNombres() + " " +
                    usuario.getApellidoP() + " " +
                    usuario.getApellidoM();
            lblNombre.setText(nombreCompleto);

            // Mostrar rol
            String rol = PermisoUtils.obtenerNombreRol(usuario);
            lblRol.setText(rol);

            // Cambiar color del rol según el tipo
            if (PermisoUtils.esAdministrador(usuario)) {
                lblRol.setForeground(new Color(237, 28, 36)); // Rojo Claro
            } else if (PermisoUtils.esSupervisor(usuario)) {
                lblRol.setForeground(new Color(255, 140, 0)); // Naranja
            } else {
                lblRol.setForeground(new Color(100, 100, 100)); // Gris
            }
        } else {
            lblNombre.setText("Usuario no identificado");
            lblRol.setText("Sin rol");
            lblRol.setForeground(new Color(150, 150, 150));
        }
    }

    /**
     * Refresca las notificaciones
     */
    public void refrescarNotificaciones() {
        notificacionPanel.refrescarNotificaciones();
    }

    /**
     * Obtiene el panel de notificaciones para acceso directo
     */
    public NotificacionPanel getNotificacionPanel() {
        return notificacionPanel;
    }
}