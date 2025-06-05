/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class DetalleAdicional {
    private String idAdicionales;
    private String idProducto;
    private double costo;

    public DetalleAdicional() {
    }
    
    

    public DetalleAdicional(String idAdicionales, String idProducto, double costo) {
        this.idAdicionales = idAdicionales;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdAdicionales() {
        return idAdicionales;
    }

    public void setIdAdicionales(String idAdicionales) {
        this.idAdicionales = idAdicionales;
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
