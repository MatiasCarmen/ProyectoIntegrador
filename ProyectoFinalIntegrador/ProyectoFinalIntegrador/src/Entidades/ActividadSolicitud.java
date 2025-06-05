/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class ActividadSolicitud {
    private String idActividad;
    private String idSolicitud;

    public ActividadSolicitud() {
    }

    public ActividadSolicitud(String idActividad, String idSolicitud) {
        this.idActividad = idActividad;
        this.idSolicitud = idSolicitud;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    
    
}