package vista;

import entidades.Actividad;
import controladores.ActividadesControlador;
import javax.swing.*;
import java.awt.*;

public class VistaVerActividad extends JDialog {

    private final JTextField txtIdActividad = new JTextField();
    private final JTextField txtTipo = new JTextField();
    private final JTextField txtFechaCreacion = new JTextField();
    private final JTextField txtFechaCierre = new JTextField();
    private final JTextArea txtDescripcion = new JTextArea(5, 20);
    private final JTextField txtIdUsuario = new JTextField();
    private final JTextField txtResolucion = new JTextField();

    public VistaVerActividad(Window owner, String idActividad) {
        super(owner, "Detalle de Actividad", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("ID Actividad:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtIdActividad, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtTipo, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Fecha Creación:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtFechaCreacion, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Fecha Cierre:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtFechaCierre, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++;
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescripcion);
        panel.add(scroll, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("ID Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtIdUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Resolución:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panel.add(txtResolucion, gbc);

        add(panel, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        cargarActividad(idActividad);

        // Deshabilitar todos los campos
        txtIdActividad.setEditable(false);
        txtTipo.setEditable(false);
        txtFechaCreacion.setEditable(false);
        txtFechaCierre.setEditable(false);
        txtDescripcion.setEditable(false);
        txtIdUsuario.setEditable(false);
        txtResolucion.setEditable(false);
    }

    private void cargarActividad(String idActividad) {
        ActividadesControlador ac = new ActividadesControlador();
        Actividad a = ac.obtenerPorId(idActividad);
        if (a != null) {
            txtIdActividad.setText(a.getIdActividad());
            txtTipo.setText(a.getTipo());
            txtFechaCreacion.setText(a.getFechaCreacion() != null ? a.getFechaCreacion().toString() : "");
            txtFechaCierre.setText(a.getFechaCierre() != null ? a.getFechaCierre().toString() : "");
            txtDescripcion.setText(a.getDescripcion());
            txtIdUsuario.setText(a.getIdUsuario());
            txtResolucion.setText(a.getResolucion());
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la actividad.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
