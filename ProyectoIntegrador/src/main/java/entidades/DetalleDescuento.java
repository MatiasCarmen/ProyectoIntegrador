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
 * MVC – Modelo: Detalle de Descuentos por Producto.
 * SOLID – SRP: solo datos de la relación.
 */
public class DetalleDescuento implements Serializable {
    private String idDescuentos;
    private String idProducto;
    private BigDecimal costo;

    public DetalleDescuento() {}

    public DetalleDescuento(String idDescuentos, String idProducto, BigDecimal costo) {
        this.idDescuentos = idDescuentos;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdDescuentos() { return idDescuentos; }
    public void setIdDescuentos(String idDescuentos) { this.idDescuentos = idDescuentos; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
