/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class DetalleOferta {
    private String idOfertas;
    private String idProducto;
    private double costo;

    public DetalleOferta() {
    }

    public DetalleOferta(String idOfertas, String idProducto, double costo) {
        this.idOfertas = idOfertas;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdOfertas() {
        return idOfertas;
    }

    public void setIdOfertas(String idOfertas) {
        this.idOfertas = idOfertas;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
}