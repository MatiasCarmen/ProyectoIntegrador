/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author mathi
 */

import BD.ConexionBD;
import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utils.BCryptUtil;

/**
 * DAO – UsuarioDAO: abstracción de persistencia para Usuario.
 * Incluye método validarLogin.
 */
public class UsuarioDAO {
    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());
    
     public void crearUsuario(Usuario user) throws SQLException {
        String sql = "INSERT INTO USUARIOS (IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA, FECHA_CREACION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setString(1, user.getIdUsuario());
        ps.setString(2, user.getRut());
        // Asignar rol y país por defecto si no están definidos
        ps.setString(3, user.getIdRol() != null ? user.getIdRol() : "D001");
        ps.setString(4, user.getIdPais() != null ? user.getIdPais() : "CHL");
        ps.setString(5, user.getClave());
        ps.setString(6, user.getNombres());
        ps.setString(7, user.getApellidoP());
        ps.setString(8, user.getApellidoM());
        // Área opcional
        ps.setString(9, user.getArea() != null ? user.getArea() : "");
        ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
        ps.executeUpdate(); 
         }
       
    }

   public Usuario validarLogin(String idUsuario, String clave) {
    String sql = "SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA FROM USUARIOS WHERE IDUSUARIO = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, idUsuario);

        try (ResultSet rs = ps.executeQuery()) {
          
            if (rs.next()) {
                Usuario u = new Usuario(
                    rs.getString("IDUSUARIO"),
                    rs.getString("RUT"),
                    rs.getString("IDROL"),
                    rs.getString("IDPAIS"),
                    rs.getString("CLAVE"),
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOP"),
                    rs.getString("APELLIDOM"),
                    rs.getString("AREA"),
                    null  // FECHACREACION no se usa
                );
                
             

                if (BCryptUtil.checkPassword(clave, u.getClave())) {
                    return u;
                }
            }
        }
    } catch (SQLException e) {
        LOGGER.severe("Error validar Usuario: " + e.getMessage());
    }
    return null;
}

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA,FECHA_CREACION FROM USUARIOS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(
                  rs.getString("IDUSUARIO"),
                  rs.getString("RUT"),
                  rs.getString("IDROL"),
                  rs.getString("IDPAIS"),
                  rs.getString("CLAVE"),
                  rs.getString("NOMBRES"),
                  rs.getString("APELLIDOP"),
                  rs.getString("APELLIDOM"),
                  rs.getString("AREA"),
                  rs.getDate("FECHA_CREACION")
               
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Usuarios: " + e.getMessage());
        }
        return lista;
    }
    public String generarIdUsuario(String nombre, String apellidoP) {
    String base = (nombre.charAt(0) + apellidoP).toLowerCase();
    String idGenerado = base;
    int sufijo = 0;
    while (existeUsuario(idGenerado)) {
        idGenerado = base + (char) ('a' + sufijo);
        sufijo++;
    }
    return idGenerado;
}

public boolean existeUsuario(String idUsuario) {
    String sql = "SELECT 1 FROM USUARIOS WHERE IDUSUARIO = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al verificar existencia de usuario: " + e.getMessage());
    }
    return false;
}

public Usuario obtenerPorId(String idUsuario) {
    String sql = "SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA FROM USUARIOS WHERE IDUSUARIO = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Usuario(
                        rs.getString("IDUSUARIO"),
                        rs.getString("RUT"),
                        rs.getString("IDROL"),
                        rs.getString("IDPAIS"),
                        rs.getString("CLAVE"),
                        rs.getString("NOMBRES"),
                        rs.getString("APELLIDOP"),
                        rs.getString("APELLIDOM"),
                        rs.getString("AREA"),
                        null
                );
            }
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al obtener usuario por ID: " + e.getMessage());
    }
    return null;
}

public void actualizarUsuario(Usuario usuario) throws SQLException {
    String sql = "UPDATE USUARIOS SET RUT = ?, IDROL = ?, IDPAIS = ?, CLAVE = ?, NOMBRES = ?, APELLIDOP = ?, APELLIDOM = ?, AREA = ? WHERE IDUSUARIO = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, usuario.getRut());
        ps.setString(2, usuario.getIdRol());
        ps.setString(3, usuario.getIdPais());
        ps.setString(4, usuario.getClave());
        ps.setString(5, usuario.getNombres());
        ps.setString(6, usuario.getApellidoP());
        ps.setString(7, usuario.getApellidoM());
        ps.setString(8, usuario.getArea());
        ps.setString(9, usuario.getIdUsuario());
        ps.executeUpdate();
    }
}

    
}
