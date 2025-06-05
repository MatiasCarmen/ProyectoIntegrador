/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class Oferta {
    private String idOfertas;
    private String idCuenta;
    private double costoT;

    public Oferta() {
    }

    public Oferta(String idOfertas, String idCuenta, double costoT) {
        this.idOfertas = idOfertas;
        this.idCuenta = idCuenta;
        this.costoT = costoT;
    }

    public String getIdOfertas() {
        return idOfertas;
    }

    public void setIdOfertas(String idOfertas) {
        this.idOfertas = idOfertas;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public double getCostoT() {
        return costoT;
    }

    public void setCostoT(double costoT) {
        this.costoT = costoT;
    }
    
    
}
