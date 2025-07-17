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
 * MVC – Modelo: entidad Descuento.
 * SOLID – SRP: datos solo de Descuento.
 */
public class Descuento implements Serializable {
    private String idDescuentos;
    private BigDecimal costoT;

    public Descuento() {}

    public Descuento(String idDescuentos, BigDecimal costoT) {
        this.idDescuentos = idDescuentos;
        this.costoT = costoT;
    }

    public String getIdDescuentos() { return idDescuentos; }
    public void setIdDescuentos(String idDescuentos) { this.idDescuentos = idDescuentos; }

    public BigDecimal getCostoT() { return costoT; }
    public void setCostoT(BigDecimal costoT) { this.costoT = costoT; }
}
