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
import entidades.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – RolDAO: abstracción de persistencia para Rol.
 */
public class RolDAO {
    private static final Logger LOGGER = Logger.getLogger(RolDAO.class.getName());

    public List<Rol> listarTodos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM ROLES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Rol(
                  rs.getString("IDROL"),
                  rs.getString("DESCRIPCION")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Roles: " + e.getMessage());
        }
        return lista;
    }
    public String obtenerIdPorNombre(String nombreRol) {
    String sql = "SELECT IDROL FROM ROLES WHERE DESCRIPCION = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nombreRol);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("IDROL");
            }
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al obtener ID de Rol por nombre: " + e.getMessage());
    }
    return null;
}

// Obtener el nombre del rol por su ID
public String obtenerNombrePorId(String idRol) {
    String sql = "SELECT NOMBRE FROM ROLES WHERE IDROL = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idRol);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("DESCRIPCION");
            }
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al obtener nombre de Rol por ID: " + e.getMessage());
    }
    return null;
}
}
