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
 * MVC – Modelo: Función disponible en el sistema.
 * SOLID – SRP: única responsabilidad representar función.
 */
public class Funcion implements Serializable {
    private String idFuncion;
    private String descripcion;

    public Funcion() {}

    public Funcion(String idFuncion, String descripcion) {
        this.idFuncion = idFuncion;
        this.descripcion = descripcion;
    }

    public String getIdFuncion() { return idFuncion; }
    public void setIdFuncion(String idFuncion) { this.idFuncion = idFuncion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
