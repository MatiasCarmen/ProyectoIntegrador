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
 * MVC – Modelo: Detalle de Planes (productos incluidos).
 * SOLID – SRP: datos de DETALLE_PLANES.
 */
public class DetallePlan implements Serializable {
    private String idPlan;
    private String idProducto;
    private BigDecimal costo;

    public DetallePlan() {}

    public DetallePlan(String idPlan, String idProducto, BigDecimal costo) {
        this.idPlan = idPlan;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdPlan() { return idPlan; }
    public void setIdPlan(String idPlan) { this.idPlan = idPlan; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
