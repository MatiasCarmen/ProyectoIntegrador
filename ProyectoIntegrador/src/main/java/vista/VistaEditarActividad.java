/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.ActividadesControlador;
import controladores.ControladorMesa_central;
import entidades.Actividad;
import entidades.MesaCentral;
import validators.MesaCentralValidator;
import java.awt.FlowLayout;
import java.awt.Window;
import java.sql.Timestamp;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.util.Optional;
import ren.main.main;

public class VistaEditarActividad extends JDialog {

    private final JTextField txtNumeroMesaCentral = new JTextField();
    private final JTextField txtLugarMesaCentral = new JTextField();
    private final JTextField txtTelefonoCliente = new JTextField();
    private final JTextField txtEmailCliente = new JTextField();
    private final JComboBox<String> comboBoxTipoActividad = new JComboBox<>(
            new String[] { "LLAMADA ENTRANTE", "LLAMADA SALIENTE", "SMS", "CORREO" });
    private final JComboBox<String> cmbRazon = new JComboBox<>(new String[] { "COMERCIAL  HFC/FFTH", "TECNICA HFC/FFTH",
            "RETENCIONES HFC/FFTH", "RETENCIONES PREMIUN HFC/FFTH", "OTT", "BOTON ROJO HFC/FFTH", "DTH", "VENTAS HOGAR",
            "MOVIL POSTPAGO", "MOVIL PREPAGO" });
    private final JComboBox<String> cmbDetalle = new JComboBox<>(new String[] { "CONSULTA BOLETAS", "MESA CENTRAL",
            "EXPLICACION DE BOLETAS", "CAMBIO DE PLAN", "MIGRACION BACKOFFICE FFTH",
            "ACTIVACION PLATAFORMAS STREAMING HOGAR", "ACTIVACION PLATAFORMAS STREAMING MOVIL", "VENTAS HOGAR",
            "VENTAS MOVIL", "GESTION INTERNA", "AJUSTE DE BOLETA", "DESCUENTO NO APLICADO",
            "DESCONOCIMIENTO PROPORCIONALES", "INHIBICION", "SUSPENSION TRANSITORIA" });
    private final JComboBox<String> cmbResolucion = new JComboBox<>(new String[] { "PENDIENTE", "FINALIZADO" });
    private final JTextArea txaDescripcion = new JTextArea(4, 20);
    private final JTextArea txaObservacion = new JTextArea(4, 20);

    private final String idActividad;
    private Actividad actividadActual;
    private MesaCentral mesaCentralActual;

    public VistaEditarActividad(Window owner, String idActividad) {
        super(owner, "Editar Actividad", ModalityType.APPLICATION_MODAL);
        this.idActividad = idActividad;

        setLayout(new MigLayout("wrap 2, insets 20", "[right][grow,fill]"));

        add(new JLabel("Tipo de actividad:"));
        add(comboBoxTipoActividad);

        add(new JLabel("Descripción:"));
        add(new JScrollPane(txaDescripcion));

        add(new JLabel("Observación:"));
        add(new JScrollPane(txaObservacion));

        add(new JLabel("Teléfono del Cliente:"));
        add(txtTelefonoCliente);

        add(new JLabel("Correo del Cliente:"));
        add(txtEmailCliente);

        add(new JLabel("Razón:"));
        add(cmbRazon);

        add(new JLabel("Detalle:"));
        add(cmbDetalle);

        add(new JLabel("Resolución:"));
        add(cmbResolucion);

        add(new JLabel("Número de Mesa Central:"));
        add(txtNumeroMesaCentral);

        add(new JLabel("Lugar Mesa Central:"));
        add(txtLugarMesaCentral);

        JButton btnActualizar = new JButton("Actualizar Actividad");
        btnActualizar.addActionListener(e -> actualizarActividad());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnActualizar);
        add(panelBoton, "span");

        cargarDatosActividad();

        setSize(600, 550);
        setLocationRelativeTo(owner);
    }

    private void cargarDatosActividad() {
        ActividadesControlador controlador = new ActividadesControlador();
        actividadActual = controlador.obtenerPorId(idActividad);

        if (actividadActual != null) {
            comboBoxTipoActividad.setSelectedItem(actividadActual.getTipo());
            txaDescripcion.setText(actividadActual.getDescripcion());
            txaObservacion.setText(actividadActual.getComentarios());
            txtTelefonoCliente.setText(String.valueOf(actividadActual.getTelefono()));
            txtEmailCliente.setText(actividadActual.getCorreo());
            cmbRazon.setSelectedItem(actividadActual.getRazon());
            cmbDetalle.setSelectedItem(actividadActual.getDetalle());
            cmbResolucion.setSelectedItem(actividadActual.getResolucion());

            ControladorMesa_central cm = new ControladorMesa_central();
            mesaCentralActual = cm.obtenerMesaCentral(idActividad);

            if (mesaCentralActual != null) {
                txtNumeroMesaCentral.setText(String.valueOf(mesaCentralActual.getTelefono()));
                txtLugarMesaCentral.setText(mesaCentralActual.getLugar());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la actividad.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void actualizarActividad() {
        String tipo = (String) comboBoxTipoActividad.getSelectedItem();
        String descripcion = txaDescripcion.getText().trim();
        String observacion = txaObservacion.getText().trim();
        String telefono = txtTelefonoCliente.getText().trim();
        String correo = txtEmailCliente.getText().trim();
        String razon = (String) cmbRazon.getSelectedItem();
        String detalle = (String) cmbDetalle.getSelectedItem();
        String resolucion = (String) cmbResolucion.getSelectedItem();
        String numeroMesa = txtNumeroMesaCentral.getText().trim();
        String lugarMesa = txtLugarMesaCentral.getText().trim();

        if (descripcion.isEmpty() || observacion.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!telefono.matches("^9\\d{8}$")) {
            JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        actividadActual.setTipo(tipo);
        actividadActual.setDescripcion(descripcion);
        actividadActual.setComentarios(observacion);
        actividadActual.setTelefono(Long.parseLong(telefono));
        actividadActual.setCorreo(correo);
        actividadActual.setRazon(razon);
        actividadActual.setDetalle(detalle);
        actividadActual.setResolucion(resolucion);
        actividadActual.setFechaCierre(new Timestamp(System.currentTimeMillis()));

        boolean actualizado = new ActividadesControlador().actualizarActividad(actividadActual);

        if (actualizado) {
            if (!numeroMesa.isEmpty() && numeroMesa.matches("^9\\d{8}$")) {
                if (lugarMesa.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe indicar un lugar para la mesa central.", "Validación",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                MesaCentral mesa = new MesaCentral();
                mesa.setIdActividad(idActividad);
                mesa.setTelefono(Long.parseLong(numeroMesa));
                mesa.setLugar(lugarMesa);

                ControladorMesa_central cm = new ControladorMesa_central();
                if (mesaCentralActual != null) {
                    cm.actualizarMesaCentral(mesa);
                } else {
                    cm.crearMesaCentral(mesa);
                }
            }

            JOptionPane.showMessageDialog(this, "Actividad actualizada exitosamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la actividad.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (main.ventanap != null) {
            main.ventanap.refrescarTablaActividades();
        }
    }
}
