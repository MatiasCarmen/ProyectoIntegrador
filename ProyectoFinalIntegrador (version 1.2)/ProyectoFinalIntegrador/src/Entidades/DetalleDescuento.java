/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class DetalleDescuento {
    private String idDescuentos;
    private String idProducto;
    private double costo;

    public DetalleDescuento() {
    }
    
    

    public DetalleDescuento(String idDescuentos, String idProducto, double costo) {
        this.idDescuentos = idDescuentos;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdDescuentos() {
        return idDescuentos;
    }

    public void setIdDescuentos(String idDescuentos) {
        this.idDescuentos = idDescuentos;
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
