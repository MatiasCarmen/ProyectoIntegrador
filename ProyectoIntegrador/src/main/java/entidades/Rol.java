/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author mathi
 */

/**
 * MVC – Modelo: representa la entidad Rol.
 * SOLID – SRP: única responsabilidad: contener datos de un rol.
 */
public class Rol {
    private String idRol;
    private String nombre;

    public Rol() {}

    public Rol(String idRol, String nombre) {
        this.idRol   = idRol;
        this.nombre  = nombre;
    }

    public String getIdRol() { return idRol; }
    public void setIdRol(String idRol) { this.idRol = idRol; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
