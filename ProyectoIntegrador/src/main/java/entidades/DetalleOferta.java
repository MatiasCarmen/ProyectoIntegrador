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
 * MVC – Modelo: Detalle de Ofertas por Producto.
 * SOLID – SRP: datos de la relación Oferta–Producto.
 */
public class DetalleOferta implements Serializable {
    private String idOfertas;
    private String idProducto;
    private BigDecimal costo;

    public DetalleOferta() {}

    public DetalleOferta(String idOfertas, String idProducto, BigDecimal costo) {
        this.idOfertas = idOfertas;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdOfertas() { return idOfertas; }
    public void setIdOfertas(String idOfertas) { this.idOfertas = idOfertas; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
