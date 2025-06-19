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
 * MVC – Modelo: relación N:M entre Rol y Funcion.
 * SOLID – SRP: esta clase solo contiene los datos de la relación.
 * Buenas prácticas: Serializable, atributos privados, getters/setters.
 */
public class RolFuncion implements Serializable {
    private String idRol;
    private String idFuncion;

    public RolFuncion() {}

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
