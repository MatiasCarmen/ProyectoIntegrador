package vista.drawer.component;
/**
 *
 * @author mathi
 */
import javax.swing.*;
import java.awt.*;
import vista.drawer.component.header.SimpleHeaderData;
import vista.drawer.component.menu.SimpleMenuOption;
import vista.drawer.component.footer.SimpleFooterData;

public abstract class SimpleDrawerBuilder {

    public abstract SimpleHeaderData getSimpleHeaderData();
    public abstract SimpleMenuOption getSimpleMenuOption();
    public abstract SimpleFooterData getSimpleFooterData();
    public abstract int getDrawerWidth();

    public JComponent build() {
        JPanel drawer = new JPanel(new BorderLayout());
        drawer.setPreferredSize(new Dimension(getDrawerWidth(), 0));
        drawer.setBackground(getDrawerBackground());

        // Construir header
        SimpleHeaderData headerData = getSimpleHeaderData();
        JPanel header = createHeader(headerData);
        drawer.add(header, BorderLayout.NORTH);

        // Construir menú
        SimpleMenuOption menuData = getSimpleMenuOption();
        JComponent menu = createMenu(menuData);
        drawer.add(menu, BorderLayout.CENTER);

        // Construir footer
        SimpleFooterData footerData = getSimpleFooterData();
        JPanel footer = createFooter(footerData);
        drawer.add(footer, BorderLayout.SOUTH);

        return drawer;
    }

    // Métodos base para permitir override
    public Color getDrawerBackground() {
        return Color.WHITE;
    }

    public int getHeaderHeight() {
        return 100;
    }

    public boolean isHeaderGradient() {
        return false;
    }

    private JPanel createHeader(SimpleHeaderData data) {
        JPanel header = new JPanel(new BorderLayout(10, 0)) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, getHeaderHeight());
            }

            @Override
            protected void paintComponent(Graphics g) {
                if (isHeaderGradient()) {
                    Graphics2D g2 = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(
                        0, 0, new Color(237,28,36),
                        0, getHeight(), new Color(200,16,46)
                    );
                    g2.setPaint(gp);
                    g2.fillRect(0,0,getWidth(),getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        header.setBackground(new Color(237, 28, 36)); // Rojo Claro
        header.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Logo
        if (data.getIcon() != null) {
            JLabel iconLabel = new JLabel(data.getIcon());
            header.add(iconLabel, BorderLayout.WEST);
        }

        // Panel de texto (título y descripción)
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(data.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel descLabel = new JLabel(data.getDescription());
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(Color.WHITE);

        textPanel.add(titleLabel);
        textPanel.add(descLabel);
        header.add(textPanel, BorderLayout.CENTER);

        return header;
    }

    private JComponent createMenu(SimpleMenuOption data) {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);

        String[][] menus = data.getMenus();
        if (menus != null) {
            for (int i = 0; i < menus.length; i++) {
                String[] menuItem = menus[i];
                boolean isCategory = menuItem[0].startsWith("~") && menuItem[0].endsWith("~");

                if (isCategory) {
                    // Categoría
                    JLabel catLabel = new JLabel(menuItem[0].substring(1, menuItem[0].length() - 1));
                    catLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    catLabel.setForeground(new Color(150, 150, 150));
                    catLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
                    menuPanel.add(catLabel);
                } else {
                    // Ítem de menú
                    JPanel itemPanel = new JPanel(new BorderLayout());
                    itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                    itemPanel.setBackground(Color.WHITE);

                    JButton btn = new JButton(menuItem[0]);
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(new Color(51, 51, 51));

                    if (menuItem.length > 1) {
                        String iconPath = menuItem[1];
                        ImageIcon icon = loadIcon(iconPath, 16, 16);
                        if (icon != null) {
                            btn.setIcon(icon);
                            btn.setIconTextGap(10);
                        }
                    }

                    btn.setHorizontalAlignment(SwingConstants.LEFT);
                    btn.setBorderPainted(false);
                    btn.setContentAreaFilled(true);
                    btn.setFocusPainted(false);
                    btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

                    // Efectos hover
                    btn.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            btn.setBackground(new Color(255, 229, 229));
                            btn.setForeground(new Color(237, 28, 36));
                        }

                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            btn.setBackground(Color.WHITE);
                            btn.setForeground(new Color(51, 51, 51));
                        }
                    });

                    final int index = i;
                    btn.addActionListener(e -> {
                        if (data.getMenuEvent() != null) {
                            data.getMenuEvent().selected(null, index, -1);
                        }
                    });

                    itemPanel.add(btn);
                    menuPanel.add(itemPanel);
                    menuPanel.add(Box.createRigidArea(new Dimension(0, 1))); // Espaciado entre items
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private JPanel createFooter(SimpleFooterData data) {
        JPanel footer = new JPanel(new GridLayout(2, 1, 0, 5));
        footer.setBackground(new Color(248, 248, 248));
        footer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(data.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(237, 28, 36));

        JLabel versionLabel = new JLabel(data.getDescription());
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(Color.GRAY);

        footer.add(titleLabel);
        footer.add(versionLabel);

        return footer;
    }
}
