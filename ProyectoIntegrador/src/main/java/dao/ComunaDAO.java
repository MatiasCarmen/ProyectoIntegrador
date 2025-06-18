/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import BD.ConexionBD;
import entidades.Comuna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author mathi
 */
/**
 * DAO de COMUNAS.
 */
public class ComunaDAO {
    private static final Logger LOGGER = Logger.getLogger(ComunaDAO.class.getName());

    public List<Comuna> listarComunas() {
        List<Comuna> list = new ArrayList<>();
        String sql = "SELECT * FROM COMUNAS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Comuna(rs.getString("IDCOMUNA"), rs.getString("DESCRIPCION")));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarComunas: " + e.getMessage());
        }
        return list;
    }
}