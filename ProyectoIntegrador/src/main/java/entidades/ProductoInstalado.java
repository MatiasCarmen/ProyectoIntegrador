/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Entidad que representa un producto instalado en una cuenta.
 */
public class ProductoInstalado {
    private String idCuenta;
    private String idAdicionales;
    private String idPlan;
    private String idDescuentos;

    public ProductoInstalado() { }

    public ProductoInstalado(String idCuenta, String idAdicionales, String idPlan, String idDescuentos) {
        this.idCuenta        = idCuenta;
        this.idAdicionales   = idAdicionales;
        this.idPlan          = idPlan;
        this.idDescuentos    = idDescuentos;
    }

    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    public String getIdAdicionales() { return idAdicionales; }
    public void setIdAdicionales(String idAdicionales) { this.idAdicionales = idAdicionales; }

    public String getIdPlan() { return idPlan; }
    public void setIdPlan(String idPlan) { this.idPlan = idPlan; }

    public String getIdDescuentos() { return idDescuentos; }
    public void setIdDescuentos(String idDescuentos) { this.idDescuentos = idDescuentos; }
}
