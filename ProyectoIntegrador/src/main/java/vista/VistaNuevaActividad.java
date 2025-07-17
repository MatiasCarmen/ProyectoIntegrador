package vista;

import controladores.ActividadesControlador;
import controladores.ControladorMesa_central;
import entidades.Actividad;
import entidades.MesaCentral;
import validators.ActividadValidator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import ren.main.main;

public class VistaNuevaActividad extends JDialog {
    private final JTextField txtNumeroMesaCentral = new JTextField();
    private final JTextField txtLugarMesaCentral = new JTextField();
    private final JTextField txtTelefonoCliente = new JTextField();
    private final JTextField txtEmailCliente = new JTextField();
    
    private final JComboBox<String> comboBoxTipoActividad = new JComboBox<>(new String[]{
        "LLAMADA ENTRANTE", "LLAMADA SALIENTE", "SMS", "CORREO"
    });
    private final JComboBox<String> cmbRazon = new JComboBox<>(new String[]{
        "COMERCIAL  HFC/FFTH", "TECNICA HFC/FFTH", "RETENCIONES HFC/FFTH",
        "RETENCIONES PREMIUN HFC/FFTH", "OTT", "BOTON ROJO HFC/FFTH", "DTH",
        "VENTAS HOGAR", "MOVIL POSTPAGO", "MOVIL PREPAGO"
    });
    private final JComboBox<String> cmbDetalle = new JComboBox<>(new String[]{
        "CONSULTA BOLETAS", "MESA CENTRAL", "EXPLICACION DE BOLETAS", "CAMBIO DE PLAN",
        "MIGRACION BACKOFFICE FFTH", "ACTIVACION PLATAFORMAS STREAMING HOGAR",
        "ACTIVACION PLATAFORMAS STREAMING MOVIL", "VENTAS HOGAR", "VENTAS MOVIL",
        "GESTION INTERNA", "AJUSTE DE BOLETA", "DESCUENTO NO APLICADO",
        "DESCONOCIMIENTO PROPORCIONALES", "INHIBICION", "SUSPENSION TRANSITORIA"
    });
    private final JComboBox<String> cmbResolucion = new JComboBox<>(new String[]{"PENDIENTE", "FINALIZADO"});
    private final JTextArea txaDescripcion = new JTextArea(4, 20);
    private final JTextArea txaObservacion = new JTextArea(4, 20);
    private final String idCuenta;

    public VistaNuevaActividad(Window owner, String idCuenta) {
        super(owner, "Registrar Nueva Actividad", ModalityType.APPLICATION_MODAL);
        this.idCuenta = idCuenta;
      
        JPanel panel = new JPanel(new MigLayout("wrap 2, insets 20", "[right][grow,fill]"));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        this.add(scroll, BorderLayout.CENTER);

        // Formulario
        panel.add(new JLabel("Tipo de actividad:")); panel.add(comboBoxTipoActividad);
        panel.add(new JLabel("Descripción:")); panel.add(new JScrollPane(txaDescripcion));
        panel.add(new JLabel("Observación:")); panel.add(new JScrollPane(txaObservacion));
        panel.add(new JLabel("Número Mesa Central:")); panel.add(txtNumeroMesaCentral);
        panel.add(new JLabel("Lugar Mesa Central:")); panel.add(txtLugarMesaCentral);
        panel.add(new JLabel("Teléfono Cliente:")); panel.add(txtTelefonoCliente);
        panel.add(new JLabel("Correo Cliente:")); panel.add(txtEmailCliente);
        panel.add(new JLabel("Razón:")); panel.add(cmbRazon);
        panel.add(new JLabel("Detalle:")); panel.add(cmbDetalle);
        panel.add(new JLabel("Resolución:")); panel.add(cmbResolucion);

        // Botón
        JButton btnGuardar = new JButton("Registrar Actividad");
        btnGuardar.addActionListener(this::registrarActividad);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnGuardar);
        this.add(panelBoton, BorderLayout.SOUTH);

        setSize(600, 500);
        setLocationRelativeTo(owner);
    }

    private void registrarActividad(ActionEvent e) {
        String tipo = (String) comboBoxTipoActividad.getSelectedItem();
        String descripcion = txaDescripcion.getText().trim();
        String observacion = txaObservacion.getText().trim();
        String numeroMesa = txtNumeroMesaCentral.getText().trim();
        String lugarMesa = txtLugarMesaCentral.getText().trim();
        String telefono = txtTelefonoCliente.getText().trim();
        String correo = txtEmailCliente.getText().trim();
        String razon = (String) cmbRazon.getSelectedItem();
        String detalle = (String) cmbDetalle.getSelectedItem();
        String resolucion = (String) cmbResolucion.getSelectedItem();

        // Validaciones con clase validator
        if (!ActividadValidator.isDescripcionValida(descripcion) ||
            !ActividadValidator.isDescripcionValida(observacion)) {
            JOptionPane.showMessageDialog(this, "Descripción u observación inválida (mín. 5 caracteres)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!ActividadValidator.isTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(this, "Teléfono del cliente inválido (9 dígitos, empieza con 9)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!ActividadValidator.isEmailValido(correo)) {
            JOptionPane.showMessageDialog(this, "Correo del cliente inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación mesa central
        if (!numeroMesa.isEmpty()) {
            if (!ActividadValidator.isTelefonoValido(numeroMesa)) {
                JOptionPane.showMessageDialog(this, "Número de mesa central inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (lugarMesa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes ingresar un lugar para la mesa central", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Crear actividad
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        Actividad act = new Actividad();
        act.setIdCuenta(idCuenta);
        act.setIdUsuario(main.logeado.getIdUsuario());
        act.setTipo(tipo);
        act.setDescripcion(descripcion);
        act.setComentarios(observacion);
        act.setTelefono(Long.parseLong(telefono));
        act.setCorreo(correo);
        act.setRazon(razon);
        act.setDetalle(detalle);
        act.setResolucion(resolucion);
        act.setFechaCreacion(ahora);
        act.setFechaCierre(ahora);
        act.setIdActividad(ActividadesControlador.generarIdActividadUnico());

        boolean exito = new ActividadesControlador().crearActividad(act);
        if (exito) {
            if (!numeroMesa.isEmpty() && !lugarMesa.isEmpty()) {
                MesaCentral mesa = new MesaCentral();
                mesa.setIdActividad(act.getIdActividad());
                mesa.setLugar(lugarMesa);
                mesa.setTelefono(Long.parseLong(numeroMesa));
                new ControladorMesa_central().crearMesaCentral(mesa);
            }
            JOptionPane.showMessageDialog(this, "Actividad registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar la actividad", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
       if(main.ventanap != null){
           main.ventanap.refrescarTablaActividades();
       }
    }
}
