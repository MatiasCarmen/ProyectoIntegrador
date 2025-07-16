package vista;

/**
 *
 * @author mathi
 */
import controladores.ActividadesControlador;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import dao.ClienteDAO;
import dao.ActividadDAO;
import dao.SolicitudDAO;

public class VistaInicio extends JPanel {

    public VistaInicio() {
        setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
        setBackground(Color.WHITE);

        // Panel central con elevación
        vista.util.UIHelper.ElevatedPanel mainPanel = new vista.util.UIHelper.ElevatedPanel();
        mainPanel.setLayout(new MigLayout("wrap, fillx, insets 40", "[center]", "[]30[]20[]20[]"));
        mainPanel.setBackground(Color.WHITE);

        // Logo con efecto de sombra
        try {
            URL logoUrl = getClass().getResource("/imagenes/logo_claro.png");
            System.out.println("Logo encontrado en classpath: " + logoUrl);

            if (logoUrl != null) {
                ImageIcon originalIcon = new ImageIcon(logoUrl);
                Image img = originalIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(img));
                mainPanel.add(logoLabel, "wrap");
            } else {
                throw new Exception("No se pudo encontrar el logo en el classpath");
            }
        } catch (Exception e) {
            System.err.println("Error cargando logo: " + e.getMessage());
            e.printStackTrace();
        }

        // Texto de bienvenida
        JLabel titulo = new JLabel("Bienvenido a Claro CRM");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(new Color(237, 28, 36));

        JLabel subtitulo = new JLabel("Sistema de Gestión de Clientes y Servicios");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(100, 100, 100));

        // Stats cards con efecto de elevación
        JPanel statsPanel = new JPanel(new MigLayout("wrap 3, insets 0, gap 20", "[grow,fill][grow,fill][grow,fill]"));
        statsPanel.setOpaque(false);

        // Obtener datos reales de la base de datos
        int clientesActivos = new ClienteDAO().contarClientes();
        int actividadesPendientes = new ActividadDAO().contarActividadesPendientes();
        int actividadesCerradas  = ActividadesControlador.contarActividadesFinalizadas();

        // Crear tarjetas de estadísticas con datos reales
        statsPanel.add(createStatCard("Clientes Activos", String.valueOf(clientesActivos), new Color(46, 125, 50)));
        statsPanel.add(createStatCard("Actividades Pendientes", String.valueOf(actividadesPendientes),
                new Color(237, 28, 36)));
        statsPanel.add(
                createStatCard("Solicitudes ", String.valueOf(actividadesCerradas), new Color(33, 150, 243)));

        // Agregar componentes al panel principal
        mainPanel.add(titulo);
        mainPanel.add(subtitulo);
        mainPanel.add(statsPanel, "width 80%");

        // Agregar el panel principal centrado
        add(mainPanel, "cell 0 0, width 80%, height 80%, align center");
    }

    private JPanel createStatCard(String title, String value, Color accentColor) {
        vista.util.UIHelper.ElevatedPanel card = new vista.util.UIHelper.ElevatedPanel();
        card.setLayout(new MigLayout("wrap, insets 20", "[center]"));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel);
        card.add(valueLabel);

        return card;
    }
}