/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author mathi
 */

import java.io.Serializable;

/**
 * MVC – Modelo: registro de la Mesa Central para una Actividad.
 * SOLID – SRP: datos de MESA_CENTRAL.
 */
public class MesaCentral implements Serializable {
    private String idActividad;
    private long telefono;
    private String lugar;

    public MesaCentral() {}

    public MesaCentral(String idActividad, long telefono, String lugar) {
        this.idActividad = idActividad;
        this.telefono = telefono;
        this.lugar = lugar;
    }

    public String getIdActividad() { return idActividad; }
    public void setIdActividad(String idActividad) { this.idActividad = idActividad; }

    public long getTelefono() { return telefono; }
    public void setTelefono(long telefono) { this.telefono = telefono; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
}
