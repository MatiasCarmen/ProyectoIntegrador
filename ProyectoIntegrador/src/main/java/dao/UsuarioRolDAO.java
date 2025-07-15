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
import entidades.UsuarioRol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – UsuarioRolDAO: maneja relación N:M USUARIOS_ROLES.
 */
public class UsuarioRolDAO {
    private static final Logger LOGGER = Logger.getLogger(UsuarioRolDAO.class.getName());

    public boolean crear(UsuarioRol ur) {
        String sql = "INSERT INTO USUARIOS_ROLES (IDUSUARIO, IDROL) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ur.getIdUsuario());
            ps.setString(2, ur.getIdRol());
           	LOGGER.info("Insertando UsuarioRol: usu=" + ur.getIdUsuario() + " rol=" + ur.getIdRol());
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error crear UsuarioRol: " + e.getMessage());
           	return false;
       	}
    }

    public List<UsuarioRol> listarPorUsuario(String idUsuario) {
       	List<UsuarioRol> lista = new ArrayList<>();
       	String sql = "SELECT * FROM USUARIOS_ROLES WHERE IDUSUARIO = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idUsuario);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new UsuarioRol(
                       	rs.getString("IDUSUARIO"),
                       	rs.getString("IDROL")
                   	));
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listarPorUsuario: " + e.getMessage());
       	}
       	return lista;
    }

    public boolean eliminar(String idUsuario, String idRol) {
       	String sql = "DELETE FROM USUARIOS_ROLES WHERE IDUSUARIO = ? AND IDROL = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idUsuario);
           	ps.setString(2, idRol);
           	LOGGER.info("Eliminando UsuarioRol: usu=" + idUsuario + " rol=" + idRol);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar UsuarioRol: " + e.getMessage());
           	return false;
       	}
    }
    
}
