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
import entidades.ActividadSolicitud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – ActividadSolicitudDAO: maneja CRUD de la relación ACTIVIDAD_SOLICITUDES.
 */
public class ActividadSolicitudDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadSolicitudDAO.class.getName());

    public boolean crear(ActividadSolicitud as) {
        String sql = "INSERT INTO ACTIVIDAD_SOLICITUDES (IDACTIVIDAD, IDSOLICITUD) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, as.getIdActividad());
            ps.setString(2, as.getIdSolicitud());
            LOGGER.info("Insertando ActividadSolicitud: " + as.getIdActividad() + "↔" + as.getIdSolicitud());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear ActividadSolicitud: " + e.getMessage());
            return false;
        }
    }

    public List<ActividadSolicitud> listarPorActividad(String idActividad) {
        List<ActividadSolicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDAD_SOLICITUDES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ActividadSolicitud(
                        rs.getString("IDACTIVIDAD"),
                        rs.getString("IDSOLICITUD")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorActividad: " + e.getMessage());
        }
        return lista;
    }

    public List<ActividadSolicitud> listarPorSolicitud(String idSolicitud) {
        List<ActividadSolicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDAD_SOLICITUDES WHERE IDSOLICITUD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ActividadSolicitud(
                        rs.getString("IDACTIVIDAD"),
                        rs.getString("IDSOLICITUD")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorSolicitud: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String idActividad, String idSolicitud) {
        String sql = "DELETE FROM ACTIVIDAD_SOLICITUDES WHERE IDACTIVIDAD = ? AND IDSOLICITUD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            ps.setString(2, idSolicitud);
            LOGGER.info("Eliminando ActividadSolicitud: " + idActividad + "↔" + idSolicitud);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar ActividadSolicitud: " + e.getMessage());
            return false;
        }
    }
}
