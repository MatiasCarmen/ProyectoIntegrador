/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class RolFuncion {
    private String idRol;
    private String idFuncion;

    public RolFuncion() {
    }

    public RolFuncion(String idRol, String idFuncion) {
        this.idRol = idRol;
        this.idFuncion = idFuncion;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }
    
}
