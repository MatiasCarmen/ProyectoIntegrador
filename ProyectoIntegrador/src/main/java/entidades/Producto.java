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
 * MVC – Modelo: Producto que ofrece la compañía.
 * SOLID – SRP: atributos solo de PRODUCTOS.
 */
public class Producto implements Serializable {
    private String idProducto;
    private String tipo;
    private String descripcion;
    private String modalidad;

    public Producto() {}

    public Producto(String idProducto, String tipo, String descripcion, String modalidad) {
        this.idProducto = idProducto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.modalidad = modalidad;
    }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
}
