/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Date;

/**
 *
 * @author Thiago
 */
public class Solicitud {
    private String idSolicitud;
    private String idCuenta;
    private String tipo;
    private String estado;
    private String area;
    private String subarea;
    private String tipoRequerimiento;
    private java.sql.Date fechaCreacion;
    private java.sql.Date fechaCierre;
    private String comentarios;

    public Solicitud() {
    }

    public Solicitud(String idSolicitud, String idCuenta, String tipo, String estado, String area, String subarea, String tipoRequerimiento, Date fechaCreacion, Date fechaCierre, String comentarios) {
        this.idSolicitud = idSolicitud;
        this.idCuenta = idCuenta;
        this.tipo = tipo;
        this.estado = estado;
        this.area = area;
        this.subarea = subarea;
        this.tipoRequerimiento = tipoRequerimiento;
        this.fechaCreacion = fechaCreacion;
        this.fechaCierre = fechaCierre;
        this.comentarios = comentarios;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSubarea() {
        return subarea;
    }

    public void setSubarea(String subarea) {
        this.subarea = subarea;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
