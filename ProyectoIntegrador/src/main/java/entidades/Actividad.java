/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author mathi
 */


import java.sql.Date;
import java.sql.Timestamp;

/**
 * Entidad que representa una Actividad o caso de gestión en el sistema.
 */
public class Actividad {
    private String idActividad;
    private String idCuenta;
    private String descripcion;
    private String tipo;
    private String razon;
    private String detalle;
    private String resolucion;
    private String comentarios;
    private Long telefono;
    private String correo;
    private Timestamp fechaCreacion;
    private Timestamp fechaCierre;
    private  String idUsuario;

    public Actividad() { }

    public Actividad(String idActividad,
                     String idCuenta,
                     String descripcion,
                     Timestamp fechaCreacion,
                     Timestamp fechaCierre,
                     String tipo,
                     String razon,
                     String detalle,
                     String resolucion,
                     String comentarios,
                     Long telefono,
                     String correo, String idUsuario) {
        this.idActividad   = idActividad;
        this.idCuenta      = idCuenta;
        this.descripcion   = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaCierre   = fechaCierre;
        this.tipo          = tipo;
        this.razon         = razon;
        this.detalle       = detalle;
        this.resolucion    = resolucion;
        this.comentarios   = comentarios;
        this.telefono      = telefono;
        this.correo        = correo;
        this.idUsuario =  idUsuario;
    }
    public String getIdActividad() {
        return idActividad;
    }
    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }
    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRazon() {
        return razon;
    }
    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDetalle() {
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getResolucion() {
        return resolucion;
    }
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getTelefono() {
        return telefono;
    }
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}

