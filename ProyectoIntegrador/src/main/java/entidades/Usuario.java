/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Date;

/**
 *  PRINCIPIO MVC: Esta clase representa el "Modelo" del sistema (datos del usuario).
 *  SOLID - SRP: Tiene una sola responsabilidad: representar la entidad Usuario.
 *  Buenas prácticas: atributos privados con getters/setters, constructores, y tipos adecuados.
 */
public class Usuario {

    private String idUsuario;
    private String rut;
    private String idRol;
    private String idPais;
    private String clave;
    private String nombres;
    private String apellidoP;
    private String apellidoM;
    private String area;
    private Date fechaCreacion;

    public Usuario() {}

    public Usuario(String idUsuario, String rut, String idRol, String idPais, String clave,
                   String nombres, String apellidoP, String apellidoM, String area, Date fechaCreacion) {
        this.idUsuario = idUsuario;
        this.rut = rut;
        this.idRol = idRol;
        this.idPais = idPais;
        this.clave = clave;
        this.nombres = nombres;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.area = area;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getIdRol() { return idRol; }
    public void setIdRol(String idRol) { this.idRol = idRol; }

    public String getIdPais() { return idPais; }
    public void setIdPais(String idPais) { this.idPais = idPais; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidoP() { return apellidoP; }
    public void setApellidoP(String apellidoP) { this.apellidoP = apellidoP; }

    public String getApellidoM() { return apellidoM; }
    public void setApellidoM(String apellidoM) { this.apellidoM = apellidoM; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
