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
 * MVC – Modelo: relación N:M entre Usuario y Rol.
 * SOLID – SRP: datos de USUARIO_ROL.
 */
public class UsuarioRol implements Serializable {
    private String idUsuario;
    private String idRol;

    public UsuarioRol() {}

    public UsuarioRol(String idUsuario, String idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getIdRol() { return idRol; }
    public void setIdRol(String idRol) { this.idRol = idRol; }
}

