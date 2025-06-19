/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import java.sql.Date;
/**
 *
 * @author mathi
 */
/**
 * MVC – Modelo: representa la entidad Pedido (datos de un pedido).
 * SOLID – SRP: única responsabilidad: contener datos de un pedido.
 * Buenas prácticas: atributos privados, getters/setters, constructor completo.
 */

public class Pedido {
    private String idPedido;
    private String rutCliente;
    private Date fecha;
    private double total;
    private String estado;

    public Pedido() {}

    public Pedido(String idPedido, String rutCliente, Date fecha, double total, String estado) {
        this.idPedido   = idPedido;
        this.rutCliente = rutCliente;
        this.fecha      = fecha;
        this.total      = total;
        this.estado     = estado;
    }

    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }

    public String getRutCliente() { return rutCliente; }
    public void setRutCliente(String rutCliente) { this.rutCliente = rutCliente; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}