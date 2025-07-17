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

/**
 * MVC – Modelo: representa la asociación N:M entre Actividad y Pedido.
 * SOLID – SRP: responsabilidad única: contener datos de la relación.
 * Buenas prácticas: atributos privados, Serializable para paso entre vistas.
 */
public class ActividadPedido implements Serializable {
    private String idActividad;
    private String idPedido;
    private String categoria;

    public ActividadPedido() {}

    public ActividadPedido(String idActividad, String idPedido, String categoria) {
        this.idActividad = idActividad;
        this.idPedido = idPedido;
        this.categoria = categoria;
    }

    public String getIdActividad() { return idActividad; }
    public void setIdActividad(String idActividad) { this.idActividad = idActividad; }

    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
