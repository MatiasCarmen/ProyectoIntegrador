/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Thiago
 */
public class DetallePedido {
    private String idPedido;
    private String idAdicionales;
    private String idPlan;
    private String idDescuentos;

    public DetallePedido() {
    }

    public DetallePedido(String idPedido, String idAdicionales, String idPlan, String idDescuentos) {
        this.idPedido = idPedido;
        this.idAdicionales = idAdicionales;
        this.idPlan = idPlan;
        this.idDescuentos = idDescuentos;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdAdicionales() {
        return idAdicionales;
    }

    public void setIdAdicionales(String idAdicionales) {
        this.idAdicionales = idAdicionales;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getIdDescuentos() {
        return idDescuentos;
    }

    public void setIdDescuentos(String idDescuentos) {
        this.idDescuentos = idDescuentos;
    }
    
    
}
