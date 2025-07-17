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
 * DAO – ActividadSolicitudDAO: maneja CRUD de la relación ACTIVIDAD_SOLICITUDES
 * usando procedimientos almacenados.
 * Implementa operaciones básicas de base de datos para la relación entre
 * Actividad y Solicitud.
 */
public class ActividadSolicitudDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadSolicitudDAO.class.getName());

    /**
     * Crea una nueva relación entre una actividad y una solicitud.
     * 
     * @param as El objeto ActividadSolicitud con los datos de la relación
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(ActividadSolicitud as) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_crear_actividad_solicitud(?, ?, ?)}")) {
            cs.setString(1, as.getIdActividad());
            cs.setString(2, as.getIdSolicitud());
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(3);

            LOGGER.info("Actividad-Solicitud creada: " + as.getIdActividad() + "↔" + as.getIdSolicitud());
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear Actividad-Solicitud: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las relaciones asociadas a una actividad específica.
     * 
     * @param idActividad El ID de la actividad
     * @return Lista de relaciones Actividad-Solicitud
     */
    public List<ActividadSolicitud> listarPorActividad(String idActividad) {
        List<ActividadSolicitud> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_por_actividad(?)}")) {
            cs.setString(1, idActividad);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividadSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar por actividad: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Lista todas las relaciones asociadas a una solicitud específica.
     * 
     * @param idSolicitud El ID de la solicitud
     * @return Lista de relaciones Actividad-Solicitud
     */
    public List<ActividadSolicitud> listarPorSolicitud(String idSolicitud) {
        List<ActividadSolicitud> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_por_solicitud(?)}")) {
            cs.setString(1, idSolicitud);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividadSolicitud(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar por solicitud: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina una relación específica entre actividad y solicitud.
     * 
     * @param idActividad El ID de la actividad
     * @param idSolicitud El ID de la solicitud
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String idActividad, String idSolicitud) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_actividad_solicitud(?, ?, ?)}")) {
            cs.setString(1, idActividad);
            cs.setString(2, idSolicitud);
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(3);

            LOGGER.info("Actividad-Solicitud eliminada: " + idActividad + "↔" + idSolicitud);
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar Actividad-Solicitud: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto ActividadSolicitud.
     * 
     * @param rs ResultSet con los datos de la relación
     * @return Objeto ActividadSolicitud mapeado
     * @throws SQLException si hay error al acceder a los datos
     */
    private ActividadSolicitud mapearActividadSolicitud(ResultSet rs) throws SQLException {
        return new ActividadSolicitud(
                rs.getString("IDACTIVIDAD"),
                rs.getString("IDSOLICITUD"));
    }
}
