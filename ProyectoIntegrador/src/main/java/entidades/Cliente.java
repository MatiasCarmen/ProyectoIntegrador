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
 * Entidad que representa un Cliente en la base de datos.
 */
public class Cliente {
    private String rut;
    private String correo;
    private String nombres;
    private String apellidoP;
    private String apellidoM;
    private long telefono;
    private byte edad;
    private String direccion;
    private String idComuna;

    public Cliente() {}

    public Cliente(String rut, String correo, String nombres,
                   String apellidoP, String apellidoM,
                   long telefono, byte edad,
                   String direccion, String idComuna) {
        this.rut = rut;
        this.correo = correo;
        this.nombres = nombres;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.telefono = telefono;
        this.edad = edad;
        this.direccion = direccion;
        this.idComuna = idComuna;
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidoP() { return apellidoP; }
    public void setApellidoP(String apellidoP) { this.apellidoP = apellidoP; }

    public String getApellidoM() { return apellidoM; }
    public void setApellidoM(String apellidoM) { this.apellidoM = apellidoM; }

    public long getTelefono() { return telefono; }
    public void setTelefono(long telefono) { this.telefono = telefono; }

    public byte getEdad() { return edad; }
    public void setEdad(byte edad) { this.edad = edad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getIdComuna() { return idComuna; }
    public void setIdComuna(String idComuna) { this.idComuna = idComuna; }
}
