/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class Adicional {
    private String idAdicionales;
    private double costoT;

    public Adicional() {
    }

    public Adicional(String idAdicionales, double costoT) {
        this.idAdicionales = idAdicionales;
        this.costoT = costoT;
    }

    public String getIdAdicionales() {
        return idAdicionales;
    }

    public void setIdAdicionales(String idAdicionales) {
        this.idAdicionales = idAdicionales;
    }

    public double getCostoT() {
        return costoT;
    }

    public void setCostoT(double costoT) {
        this.costoT = costoT;
    }
    
}

