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
 * DAO – ComunaDAO: acceso a la tabla COMUNAS.
 * SRP: solo persistencia de entidades Comuna.
 */
public class ComunaDAO {
    private static final Logger LOGGER = Logger.getLogger(ComunaDAO.class.getName());

    /** Lista todas las comunas (para llenar combos). */
    public List<Comuna> listarComunas() {
        List<Comuna> lista = new ArrayList<>();
        String sql = "SELECT * FROM COMUNAS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Comuna(
                    rs.getString("IDCOMUNA"),
                    rs.getString("DESCRIPCION")
                ));
            }
            LOGGER.info("ComunaDAO: listarComunas → " + lista.size() + " filas");
        } catch (SQLException e) {
            LOGGER.severe("Error listarComunas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Dado el nombre/descripción de una comuna, devuelve su ID.
     * @param descripcion texto de la comuna
     * @return IDCOMUNA o null si no existe
     */
    public String obtenerIdPorDescripcion(String descripcion) {
        String sql = "SELECT IDCOMUNA FROM COMUNAS WHERE DESCRIPCION = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("IDCOMUNA");
                    LOGGER.info("ComunaDAO: obtenerIdPorDescripcion(" + descripcion + ") → " + id);
                    return id;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerIdPorDescripcion: " + e.getMessage());
        }
        return null;
    }
}
