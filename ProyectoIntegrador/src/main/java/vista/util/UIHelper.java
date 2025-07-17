package vista.util;

/**
 *
 * @author mathi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class UIHelper {

    // Método para crear animación de fade
    public static void fadeIn(JComponent component, int duration) {
        float[] alpha = { 0f };
        component.setOpaque(false);

        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            alpha[0] += 0.1f;
            if (alpha[0] >= 1f) {
                alpha[0] = 1f;
                timer.stop();
                component.setOpaque(true);
            }
            component.putClientProperty("alpha", alpha[0]);
            component.repaint();
        });
        timer.start();
    }

    // Método para mostrar notificación tipo toast
    public static void showToast(JFrame parent, String message, Color backgroundColor) {
        JDialog dialog = new JDialog(parent);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.setAlwaysOnTop(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);

        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label);

        dialog.add(panel);
        dialog.pack();

        // Centrar en el medio de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        // Mostrar con animación
        dialog.setOpacity(1f);
        dialog.setVisible(true);

        // Timer para ocultar después de 3 segundos
        Timer autoHideTimer = new Timer(3000, e -> {
            // Timer para fade out
            Timer fadeOutTimer = new Timer(50, null);
            fadeOutTimer.addActionListener(e2 -> {
                float currentOpacity = dialog.getOpacity();
                if (currentOpacity > 0f) {
                    dialog.setOpacity(Math.max(0f, currentOpacity - 0.1f));
                } else {
                    fadeOutTimer.stop();
                    dialog.dispose();
                }
            });
            fadeOutTimer.start();
        });
        autoHideTimer.setRepeats(false);
        autoHideTimer.start();
    }

    // Método simplificado para mostrar toast con colores automáticos
    public static void showToast(JFrame parent, String message, boolean isError) {
        Color backgroundColor = isError ? new Color(220, 53, 69) : new Color(40, 167, 69); // Rojo para error, verde
                                                                                           // para éxito
        showToast(parent, message, backgroundColor);
    }

    // Método para crear efecto de elevación
    public static class ElevatedPanel extends JPanel {
        private int shadowSize = 5;
        private float shadowOpacity = 0.2f;
        private Color shadowColor = Color.BLACK;
        private int cornerRadius = 10;

        public ElevatedPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar sombra
            for (int i = 0; i < shadowSize; i++) {
                float opacity = shadowOpacity * (1 - (float) i / shadowSize);
                g2.setColor(new Color(shadowColor.getRed() / 255f,
                        shadowColor.getGreen() / 255f,
                        shadowColor.getBlue() / 255f,
                        opacity));
                g2.fill(new RoundRectangle2D.Float(shadowSize - i, shadowSize - i,
                        getWidth() - 2 * shadowSize + 2 * i,
                        getHeight() - 2 * shadowSize + 2 * i,
                        cornerRadius + i, cornerRadius + i));
            }

            // Dibujar panel
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(shadowSize, shadowSize,
                    getWidth() - 2 * shadowSize,
                    getHeight() - 2 * shadowSize,
                    cornerRadius, cornerRadius));
            g2.dispose();
        }
    }

    // Método para crear efecto hover en botones
    public static void setupButtonHover(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                animateButtonColor(button, normalColor, hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                animateButtonColor(button, hoverColor, normalColor);
            }
        });
    }

    private static void animateButtonColor(JButton button, Color from, Color to) {
        Timer timer = new Timer(20, null);
        final float[] progress = { 0f };

        timer.addActionListener(e -> {
            progress[0] += 0.1f;
            if (progress[0] >= 1f) {
                progress[0] = 1f;
                timer.stop();
            }
            Color current = interpolateColor(from, to, progress[0]);
            button.setBackground(current);
        });
        timer.start();
    }

    private static Color interpolateColor(Color c1, Color c2, float fraction) {
        int red = (int) (c1.getRed() + fraction * (c2.getRed() - c1.getRed()));
        int green = (int) (c1.getGreen() + fraction * (c2.getGreen() - c1.getGreen()));
        int blue = (int) (c1.getBlue() + fraction * (c2.getBlue() - c1.getBlue()));
        return new Color(red, green, blue);
    }

    // Método para crear iconos
    public static ImageIcon createWarningIcon(int size, Color color) {
        ImageIcon icon = new ImageIcon(new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB));
        Graphics2D g2 = (Graphics2D) icon.getImage().getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = size / 4;
        int[] xPoints = { size / 2, padding, size - padding };
        int[] yPoints = { padding, size - padding, size - padding };

        g2.setColor(color);
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.WHITE);
        g2.drawLine(size / 2, size / 2 - 2, size / 2, size / 2 + 5);
        g2.fillOval(size / 2 - 1, size / 2 + 7, 3, 3);

        g2.dispose();
        return icon;
    }

    // Método para cargar y redimensionar imágenes a un tamaño específico
    public static ImageIcon cargarImagenRedimensionada(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    // Sobrecarga para aceptar URL directamente
    public static ImageIcon cargarImagenRedimensionada(java.net.URL url, int ancho, int alto) {
        if (url == null)
            return null;
        ImageIcon iconoOriginal = new ImageIcon(url);
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }
}
