package vista.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadingSpinner extends JPanel {
    private float progress = 0;
    private final Timer timer;
    private final Color primaryColor;
    private final int size;

    public LoadingSpinner() {
        this(new Color(237, 28, 36), 40); // Color Claro por defecto
    }

    public LoadingSpinner(Color color, int size) {
        this.primaryColor = color;
        this.size = size;
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 0.1f;
                if (progress > 1) {
                    progress = 0;
                }
                repaint();
            }
        });
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Dibujar círculo de fondo
        g2.setColor(new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 50));
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(x + 2, y + 2, size - 4, size - 4, 0, 360);

        // Dibujar arco animado
        g2.setColor(primaryColor);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int startAngle = (int) (progress * 360);
        g2.draw(new Arc2D.Float(x + 2, y + 2, size - 4, size - 4, startAngle, 120, Arc2D.OPEN));

        g2.dispose();
    }
}
