/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import java.sql.Date;
/**
 *
 * @author mathi
 */

/**
 * MVC – Modelo: representa Solicitud.
 * SOLID – SRP: única responsabilidad = datos de la solicitud.
 */
public class Solicitud {
    private String idSolicitud;
    private String idCuenta;
    private String descripcion;
    private Date fechaSolicitud;
    private String estado;
    private String comentarios;

    public Solicitud() {}

    public Solicitud(String idSolicitud, String idCuenta, String descripcion,
                     Date fechaSolicitud, String estado, String comentarios) {
        this.idSolicitud    = idSolicitud;
        this.idCuenta       = idCuenta;
        this.descripcion    = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.estado         = estado;
        this.comentarios    = comentarios;
    }

    public String getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(String idSolicitud) { this.idSolicitud = idSolicitud; }

    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}