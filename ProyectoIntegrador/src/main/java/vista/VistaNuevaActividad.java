   package vista;

import controladores.ActividadesControlador;
import controladores.ControladorMesa_central;
import entidades.Actividad;
import entidades.MesaCentral;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.sql.Timestamp;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;
import ren.main.main;

public class VistaNuevaActividad extends JDialog {
   private final JTextField txtNumeroMesaCentral = new JTextField();
   private final JTextField txtLugarMesaCentral = new JTextField();
   private final JTextField txtTelefonoCliente = new JTextField();
   private final JTextField txtEmailCliente = new JTextField();
   private final JComboBox<String> comboBoxTipoActividad = new JComboBox(new String[]{"LLAMADA ENTRANTE", "LLAMADA SALIENTE", "SMS", "CORREO"});
   private final JComboBox<String> cmbRazon = new JComboBox(new String[]{"COMERCIAL  HFC/FFTH", "TECNICA HFC/FFTH", "RETENCIONES HFC/FFTH", "RETENCIONES PREMIUN HFC/FFTH", "OTT", "BOTON ROJO HFC/FFTH", "DTH", "VENTAS HOGAR", "MOVIL POSTPAGO", "MOVIL PREPAGO"});
   private final JComboBox<String> cmbDetalle = new JComboBox(new String[]{"CONSULTA BOLETAS","MESA CENTRAL", "EXPLICACION DE BOLETAS", "CAMBIO DE PLAN", "MIGRACION BACKOFFICE FFTH", "ACTIVACION PLATAFORMAS STREAMING HOGAR", "ACTIVACION PLATAFORMAS STREAMING MOVIL", "VENTAS HOGAR", "VENTAS MOVIL", "GESTION INTERNA", "AJUSTE DE BOLETA", "DESCUENTO NO APLICADO", "DESCONOCIMIENTO PROPORCIONALES", "INHIBICION", "SUSPENSION TRANSITORIA"});
   private final JComboBox<String> cmbResolucion = new JComboBox(new String[]{"PENDIENTE", "FINALIZADO"});
   private final JTextArea txaDescripcion = new JTextArea(4, 20);
   private final JTextArea txaObservacion = new JTextArea(4, 20);
   private final String idCuenta;

   public VistaNuevaActividad(Window owner, String idCuenta) {
      super(owner, "Registrar Nueva Actividad", ModalityType.APPLICATION_MODAL);
      this.idCuenta = idCuenta;
      JPanel panel = new JPanel(new MigLayout("wrap 2, insets 20", "[right][grow,fill]"));
      JScrollPane scroll = new JScrollPane(panel);
      scroll.setBorder((Border)null);
      this.add(scroll, "Center");
      panel.add(new JLabel("Tipo de actividad:"));
      panel.add(this.comboBoxTipoActividad);
      panel.add(new JLabel("Descripción:"));
      panel.add(new JScrollPane(this.txaDescripcion));
      panel.add(new JLabel("Observación:"));
      panel.add(new JScrollPane(this.txaObservacion));
      panel.add(new JLabel("Número de Mesa Central:"));
      panel.add(this.txtNumeroMesaCentral);
      panel.add(new JLabel("Lugar Mesa Central:"));
       panel.add(this.txtLugarMesaCentral);
      panel.add(new JLabel("Teléfono del Cliente:"));
      panel.add(this.txtTelefonoCliente);
      panel.add(new JLabel("Correo del Cliente:"));
      panel.add(this.txtEmailCliente);
      panel.add(new JLabel("Razón:"));
      panel.add(this.cmbRazon);
      panel.add(new JLabel("Detalle:"));
      panel.add(this.cmbDetalle);
      panel.add(new JLabel("Resolución:"));
      panel.add(this.cmbResolucion);
      JButton btnGuardar = new JButton("Registrar Actividad");
      btnGuardar.addActionListener((e) -> {
         this.registrarActividad();
      });
      JPanel panelBoton = new JPanel(new FlowLayout(1));
      panelBoton.add(btnGuardar);
      this.add(panelBoton, "South");
      this.setSize(600, 500);
      this.setLocationRelativeTo(owner);
   }

   private void registrarActividad() {
      String tipoActividad = (String)this.comboBoxTipoActividad.getSelectedItem();
      String descripcion = this.txaDescripcion.getText().trim();
      String observacion = this.txaObservacion.getText().trim();
      String numeroMesaCentral = this.txtNumeroMesaCentral.getText().trim();
      String LugarMesaCentral = this.txtLugarMesaCentral.getText();
      String telefono = this.txtTelefonoCliente.getText().trim();
      String email = this.txtEmailCliente.getText().trim();
      String razon = (String)this.cmbRazon.getSelectedItem();
      String detalle = (String)this.cmbDetalle.getSelectedItem();
      String resolucion = (String)this.cmbResolucion.getSelectedItem();
      if (!descripcion.isEmpty() && !observacion.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
         if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Email incorrecto", 2);
         } else if (!telefono.matches("^9\\d{8}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de teléfono chileno válido (9 dígitos, comienza con 9).", "Teléfono inválido", 2);
         } else {
            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
            Actividad nuevaActividad = new Actividad();
            nuevaActividad.setIdCuenta(this.idCuenta);
            nuevaActividad.setIdUsuario(main.logeado.getIdUsuario());
            nuevaActividad.setTipo(tipoActividad);
            nuevaActividad.setDescripcion(descripcion);
            nuevaActividad.setComentarios(observacion);
            nuevaActividad.setTelefono(Long.parseLong(telefono));
            nuevaActividad.setCorreo(email);
            nuevaActividad.setRazon(razon);
            nuevaActividad.setDetalle(detalle);
            nuevaActividad.setResolucion(resolucion);
            nuevaActividad.setIdActividad(ActividadesControlador.generarIdActividadUnico());
            nuevaActividad.setFechaCreacion(fechaActual);
            nuevaActividad.setFechaCierre(fechaActual);
            if (!numeroMesaCentral.isEmpty() && !LugarMesaCentral.isEmpty() && !numeroMesaCentral.matches("^9\\d{8}$")) {
               JOptionPane.showMessageDialog(this, "Número de mesa central inválido. Debe tener 9 dígitos y comenzar con 9.", "Número inválido", 0);
            } else {
                
                 if(!numeroMesaCentral.isEmpty() && numeroMesaCentral.matches("^9\\d{8}$") && LugarMesaCentral.isEmpty() ){
                   JOptionPane.showMessageDialog(this, "Debes registrar un lugar de mesa central", "Lugar inválido", 0);
                }else{
                  
               boolean exito = (new ActividadesControlador()).crearActividad(nuevaActividad);
               if (exito) {
                      
                   if(!numeroMesaCentral.isEmpty() && numeroMesaCentral.matches("^9\\d{8}$") && !LugarMesaCentral.isEmpty() ){
                    ControladorMesa_central CM = new ControladorMesa_central();
                    MesaCentral m = new MesaCentral();
                    m.setIdActividad(nuevaActividad.getIdActividad());
                    m.setLugar(LugarMesaCentral);
                    m.setTelefono(Long.parseLong(numeroMesaCentral));
                    CM.crearMesaCentral(m);
                }
                  JOptionPane.showMessageDialog(this, "Actividad registrada exitosamente.", "Éxito", 1);
                  this.dispose();
               } else {
                  JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar la actividad.", "Error", 0);
               }
                     
                 }
                
              

            }
         }
      } else {
         JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Campos incompletos", 2);
      }
   }
}