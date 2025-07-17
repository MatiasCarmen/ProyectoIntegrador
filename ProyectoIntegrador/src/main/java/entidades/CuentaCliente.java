/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author mathi
 */

/**
 * Entidad que representa la relación CUENTAS_CLIENTES.
 */
public class CuentaCliente {
    private String idCuenta;
    private String rut;
    private String clase;

    public CuentaCliente() { }

    public CuentaCliente(String idCuenta, String rut, String clase) {
        this.idCuenta = idCuenta;
        this.rut      = rut;
        this.clase    = clase;
    }

    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }
}