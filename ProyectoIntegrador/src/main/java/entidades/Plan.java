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
 * MVC – Modelo: Plan de servicios.
 * SOLID – SRP: datos de PLANES.
 */
public class Plan implements Serializable {
    private String idPlan;
    private String nombre;
    private BigDecimal costoT;

    public Plan() {}

    public Plan(String idPlan, String nombre, BigDecimal costoT) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.costoT = costoT;
    }

    public String getIdPlan() { return idPlan; }
    public void setIdPlan(String idPlan) { this.idPlan = idPlan; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getCostoT() { return costoT; }
    public void setCostoT(BigDecimal costoT) { this.costoT = costoT; }
}
