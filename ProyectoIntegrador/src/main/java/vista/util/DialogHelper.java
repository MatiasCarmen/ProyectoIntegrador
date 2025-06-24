package vista.util;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class DialogHelper {
    private static final Color PRIMARY_COLOR = new Color(237, 28, 36);    // Rojo Claro
    private static final Color SUCCESS_COLOR = new Color(46, 125, 50);    // Verde
    private static final Color WARNING_COLOR = new Color(245, 124, 0);    // Naranja
    private static final Color ERROR_COLOR = new Color(198, 40, 40);      // Rojo oscuro

    public static boolean showConfirmDialog(Window parent, String title, String message) {
        JDialog dialog = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);

        // Panel principal con elevación
        UIHelper.ElevatedPanel mainPanel = new UIHelper.ElevatedPanel();
        mainPanel.setLayout(new MigLayout("wrap, fillx, insets 20", "[center]", "[]20[]20[]"));
        mainPanel.setBackground(Color.WHITE);

        // Icono de confirmación
        ImageIcon icon = UIHelper.createWarningIcon(48, WARNING_COLOR);
        JLabel iconLabel = new JLabel(icon);
        mainPanel.add(iconLabel);

        // Mensaje
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(messageLabel);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new MigLayout("insets 0, gap 10", "[grow][grow]"));
        buttonPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirmar");
        JButton cancelButton = new JButton("Cancelar");

        // Estilizar botones
        UIHelper.setupButtonHover(confirmButton, WARNING_COLOR, WARNING_COLOR.darker());
        confirmButton.setForeground(Color.WHITE);

        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        final boolean[] result = {false};

        confirmButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        buttonPanel.add(cancelButton, "grow");
        buttonPanel.add(confirmButton, "grow");
        mainPanel.add(buttonPanel, "growx");

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        // Agregar efecto de fade in
        dialog.setOpacity(0f);
        dialog.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(e -> {
            if (dialog.getOpacity() < 1f) {
                dialog.setOpacity(dialog.getOpacity() + 0.1f);
            } else {
                fadeInTimer.stop();
            }
        });
        fadeInTimer.start();

        return result[0];
    }

    public static void showDetailDialog(Window parent, String title, String subtitle, JComponent content) {
        JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);

        // Panel principal con elevación
        UIHelper.ElevatedPanel mainPanel = new UIHelper.ElevatedPanel();
        mainPanel.setLayout(new MigLayout("wrap, fillx, insets 20", "[grow]", "[]5[]10[]"));
        mainPanel.setBackground(Color.WHITE);

        // Título
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);

        // Subtítulo
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        // Separador
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);

        // Botón de cerrar en la esquina superior derecha
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(Color.GRAY);
        closeButton.addActionListener(e -> dialog.dispose());

        // Agregar componentes
        mainPanel.add(closeButton, "pos (container.w-30) 0 n n");
        mainPanel.add(titleLabel, "gapbefore 10");
        mainPanel.add(subtitleLabel, "gapbefore 10");
        mainPanel.add(separator, "growx");
        mainPanel.add(content, "grow");

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        // Animación de entrada
        dialog.setOpacity(0f);
        dialog.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(e -> {
            if (dialog.getOpacity() < 1f) {
                dialog.setOpacity(dialog.getOpacity() + 0.1f);
            } else {
                fadeInTimer.stop();
            }
        });
        fadeInTimer.start();
    }

    public static void showSuccessDialog(Window parent, String message) {
        showStatusDialog(parent, "Éxito", message, SUCCESS_COLOR);
    }

    public static void showErrorDialog(Window parent, String message) {
        showStatusDialog(parent, "Error", message, ERROR_COLOR);
    }

    private static void showStatusDialog(Window parent, String title, String message, Color color) {
        JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);

        UIHelper.ElevatedPanel panel = new UIHelper.ElevatedPanel();
        panel.setLayout(new MigLayout("wrap, fillx, insets 20", "[center]", "[]10[]20[]"));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton okButton = new JButton("Aceptar");
        UIHelper.setupButtonHover(okButton, color, color.darker());
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> dialog.dispose());

        panel.add(titleLabel);
        panel.add(messageLabel);
        panel.add(okButton, "width 100!");

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);

        // Animación de entrada
        dialog.setOpacity(0f);
        dialog.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(e -> {
            if (dialog.getOpacity() < 1f) {
                dialog.setOpacity(dialog.getOpacity() + 0.1f);
            } else {
                fadeInTimer.stop();
            }
        });
        fadeInTimer.start();
    }
}
