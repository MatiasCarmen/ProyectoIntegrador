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
        String sql = "SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA FROM USUARIOS";
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
                  null  // Ya no usamos FECHACREACION
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Usuarios: " + e.getMessage());
        }
        return lista;
    }
}
