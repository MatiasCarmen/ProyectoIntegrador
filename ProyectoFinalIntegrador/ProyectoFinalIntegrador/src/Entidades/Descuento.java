/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class Descuento {
    private String idDescuentos;
    private double costoT;

    public Descuento() {
    }

    public Descuento(String idDescuentos, double costoT) {
        this.idDescuentos = idDescuentos;
        this.costoT = costoT;
    }
    
    

    public String getIdDescuentos() {
        return idDescuentos;
    }

    public void setIdDescuentos(String idDescuentos) {
        this.idDescuentos = idDescuentos;
    }

    public double getCostoT() {
        return costoT;
    }

    public void setCostoT(double costoT) {
        this.costoT = costoT;
    }
    
}