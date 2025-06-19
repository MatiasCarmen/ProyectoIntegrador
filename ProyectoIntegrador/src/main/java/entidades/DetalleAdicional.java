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
 * MVC – Modelo: Detalle de Adicionales por Producto.
 * SOLID – SRP: solo atributos de la relación.
 */
public class DetalleAdicional implements Serializable {
    private String idAdicionales;
    private String idProducto;
    private BigDecimal costo;

    public DetalleAdicional() {}

    public DetalleAdicional(String idAdicionales, String idProducto, BigDecimal costo) {
        this.idAdicionales = idAdicionales;
        this.idProducto = idProducto;
        this.costo = costo;
    }

    public String getIdAdicionales() { return idAdicionales; }
    public void setIdAdicionales(String idAdicionales) { this.idAdicionales = idAdicionales; }

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
