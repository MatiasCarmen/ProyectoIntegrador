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
import java.math.BigDecimal;

/**
 * MVC – Modelo: entidad Adicional (servicio extra).
 * SOLID – SRP: solo datos de Adicional.  
 */
public class Adicional implements Serializable {
    private String idAdicionales;
    private BigDecimal costoT;

    public Adicional() {}

    public Adicional(String idAdicionales, BigDecimal costoT) {
        this.idAdicionales = idAdicionales;
        this.costoT = costoT;
    }

    public String getIdAdicionales() { return idAdicionales; }
    public void setIdAdicionales(String idAdicionales) { this.idAdicionales = idAdicionales; }

    public BigDecimal getCostoT() { return costoT; }
    public void setCostoT(BigDecimal costoT) { this.costoT = costoT; }
}
