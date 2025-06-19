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
                  rs.getString("NOMBRE")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Roles: " + e.getMessage());
        }
        return lista;
    }
}
