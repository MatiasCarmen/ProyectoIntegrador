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
 * MVC – Modelo: País al que pertenece un Usuario.
 * SOLID – SRP: datos de PAISES.
 */
public class Pais implements Serializable {
    private String idPais;
    private String descripcion;

    public Pais() {}

    public Pais(String idPais, String descripcion) {
        this.idPais = idPais;
        this.descripcion = descripcion;
    }

    public String getIdPais() { return idPais; }
    public void setIdPais(String idPais) { this.idPais = idPais; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
