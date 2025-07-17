package vista.componentes;

import entidades.Actividad;
import controladores.ActividadesControlador;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Date;

/**
 * Panel para mostrar notificaciones de actividades pendientes
 * SOLID - SRP: Única responsabilidad de mostrar notificaciones
 */
public class NotificacionPanel extends JPanel {

    private final ActividadesControlador controlador;
    private final JLabel lblContador;
    private final JPopupMenu popupMenu;
    private final JPanel panelNotificaciones;

    public NotificacionPanel() {
        this.controlador = new ActividadesControlador();
        this.lblContador = new JLabel("0");
        this.popupMenu = new JPopupMenu();
        this.panelNotificaciones = new JPanel();

        initComponents();
        cargarNotificaciones();
    }

    private void initComponents() {
        setLayout(new MigLayout("insets 0", "[pref]", "[pref]"));
        setOpaque(false);

        // Panel principal de notificaciones
        JPanel mainPanel = new JPanel(new MigLayout("insets 0", "[pref]", "[pref]"));
        mainPanel.setOpaque(false);

        // Ícono de notificaciones
        JLabel iconLabel = new JLabel();
        java.net.URL url = getClass().getResource("/imagenes/notification.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image scaledIcon = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledIcon));
            } else {
                iconLabel.setText("🔔");
                iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            }
        } else {
            iconLabel.setText("🔔");
            iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        }

        // Contador de notificaciones
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblContador.setForeground(Color.WHITE);
        lblContador.setBackground(new Color(237, 28, 36)); // Rojo Claro
        lblContador.setOpaque(true);
        lblContador.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        lblContador.setVisible(false);

        // Panel para el ícono y contador
        JPanel iconPanel = new JPanel(new MigLayout("insets 0", "[pref][pref]", "[pref]"));
        iconPanel.setOpaque(false);
        iconPanel.add(iconLabel);
        iconPanel.add(lblContador, "pos 0.8al 0.2al");

        // Hacer clickeable
        iconPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarNotificaciones(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                iconPanel.setBackground(new Color(255, 229, 229));
                iconPanel.setOpaque(true);
                iconPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iconPanel.setOpaque(false);
                iconPanel.repaint();
            }
        });

        mainPanel.add(iconPanel);
        add(mainPanel);

        // Configurar popup de notificaciones
        configurarPopup();
    }

    private void configurarPopup() {
        popupMenu.setLayout(new MigLayout("wrap, fillx, insets 10", "[grow]", "[]"));

        // Header del popup
        JLabel headerLabel = new JLabel("Notificaciones");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(237, 28, 36));
        popupMenu.add(headerLabel, "align center, gapbottom 10");

        // Panel para las notificaciones
        panelNotificaciones.setLayout(new MigLayout("wrap, fillx, insets 0", "[grow]", "[]"));
        panelNotificaciones.setBackground(Color.WHITE);
        popupMenu.add(panelNotificaciones, "width 300!, height 200!");

        // Botón para cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.putClientProperty(FlatClientProperties.STYLE,
                "background:#ED1C24; foreground:#FFFFFF; borderWidth:0; focusWidth:0; arc:8");
        btnCerrar.addActionListener(e -> popupMenu.setVisible(false));
        popupMenu.add(btnCerrar, "align center, gaptop 10");
    }

    private void mostrarNotificaciones(Component component, int x, int y) {
        cargarNotificaciones();
        popupMenu.show(component, x, y);
    }

    public void cargarNotificaciones() {
        try {
            System.out.println("🔄 Cargando notificaciones...");

            // Limpiar notificaciones anteriores
            panelNotificaciones.removeAll();

            // Obtener actividades pendientes (fecha_cierre >= hoy)
            List<Actividad> actividadesPendientes = controlador.obtenerActividadesPendientes();

            System.out.println("📊 Actividades pendientes encontradas: " + actividadesPendientes.size());

            if (actividadesPendientes.isEmpty()) {
                JLabel noNotifLabel = new JLabel("No hay notificaciones pendientes");
                noNotifLabel.setForeground(Color.GRAY);
                noNotifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panelNotificaciones.add(noNotifLabel, "align center");
                System.out.println("ℹ️ No hay notificaciones pendientes");
            } else {
                // Mostrar cada actividad como notificación
                for (Actividad actividad : actividadesPendientes) {
                    System.out.println("📋 Agregando notificación: " + actividad.getDescripcion());
                    JPanel notifPanel = crearPanelNotificacion(actividad);
                    panelNotificaciones.add(notifPanel, "growx, gapbottom 5");
                }
            }

            // Actualizar contador
            actualizarContador(actividadesPendientes.size());

            // Revalidar y repintar
            panelNotificaciones.revalidate();
            panelNotificaciones.repaint();

            System.out.println("✅ Notificaciones cargadas exitosamente");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar notificaciones: " + e.getMessage());
            e.printStackTrace();

            // Mostrar mensaje de error en el panel
            panelNotificaciones.removeAll();
            JLabel errorLabel = new JLabel("Error al cargar notificaciones");
            errorLabel.setForeground(new Color(198, 40, 40));
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panelNotificaciones.add(errorLabel, "align center");
            panelNotificaciones.revalidate();
            panelNotificaciones.repaint();
        }
    }

    private JPanel crearPanelNotificacion(Actividad actividad) {
        JPanel panel = new JPanel(new MigLayout("wrap, fillx, insets 8", "[grow]", "[]"));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Título de la actividad
        JLabel lblTitulo = new JLabel("Actividad: " + actividad.getDescripcion());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitulo.setForeground(new Color(237, 28, 36));
        panel.add(lblTitulo, "growx");

        // Detalles
        JLabel lblTipo = new JLabel("Tipo: " + actividad.getTipo());
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panel.add(lblTipo, "growx");

        JLabel lblFecha = new JLabel("Fecha cierre: " + actividad.getFechaCierre());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        panel.add(lblFecha, "growx");

        // Hacer clickeable
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Aquí se puede abrir la vista de detalles de la actividad
                JOptionPane.showMessageDialog(NotificacionPanel.this,
                        "Detalles de actividad:\n" +
                                "ID: " + actividad.getIdActividad() + "\n" +
                                "Descripción: " + actividad.getDescripcion() + "\n" +
                                "Tipo: " + actividad.getTipo() + "\n" +
                                "Fecha cierre: " + actividad.getFechaCierre(),
                        "Detalles de Actividad",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(255, 229, 229));
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(248, 249, 250));
                panel.repaint();
            }
        });

        return panel;
    }

    private void actualizarContador(int cantidad) {
        if (cantidad > 0) {
            lblContador.setText(String.valueOf(cantidad));
            lblContador.setVisible(true);
        } else {
            lblContador.setVisible(false);
        }
    }

    /**
     * Método público para refrescar notificaciones desde otras partes de la
     * aplicación
     */
    public void refrescarNotificaciones() {
        cargarNotificaciones();
    }
}