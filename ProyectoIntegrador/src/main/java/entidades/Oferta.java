/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author mathi
 */

import java.sql.Date;

public class Oferta {
    private String idOferta;
    private String idCuenta;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private double porcentajeDescuento;

    public Oferta() {}

    public Oferta(String idOferta, String idCuenta, String descripcion,
                  Date fechaInicio, Date fechaFin, double porcentajeDescuento) {
        this.idOferta            = idOferta;
        this.idCuenta            = idCuenta;
        this.descripcion         = descripcion;
        this.fechaInicio         = fechaInicio;
        this.fechaFin            = fechaFin;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getIdOferta() { return idOferta; }
    public void setIdOferta(String idOferta) { this.idOferta = idOferta; }

    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double porcentajeDescuento) { this.porcentajeDescuento = porcentajeDescuento; }
}
