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
import entidades.Solicitud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – SolicitudDAO: abstracción de persistencia para Solicitud usando
 * procedimientos almacenados.
 * SOLID – SRP: única responsabilidad = persistencia de solicitudes.
 * Seguridad: usa procedimientos almacenados y manejo de transacciones.
 */
public class SolicitudDAO {
    private static final Logger LOGGER = Logger.getLogger(SolicitudDAO.class.getName());

    /**
     * Mapea un ResultSet a un objeto Solicitud.
     * 
     * @param rs ResultSet con los datos de la solicitud
     * @return Objeto Solicitud mapeado
     * @throws SQLException si hay error al acceder a los datos
     */
    private Solicitud mapearSolicitud(ResultSet rs) throws SQLException {
        return new Solicitud(
                rs.getString("IDSOLICITUD"),
                rs.getString("IDCUENTA"),
                rs.getString("DESCRIPCION"),
                rs.getDate("FECHASOLICITUD"),
                rs.getString("ESTADO"),
                rs.getString("COMENTARIOS"));
    }

    /**
     * Crea una nueva solicitud en la base de datos.
     * 
     * @param s Solicitud a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(Solicitud s) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_crear_solicitud(?, ?, ?, ?, ?, ?)}")) {

            cs.setString(1, s.getIdSolicitud());
            cs.setString(2, s.getIdCuenta());
            cs.setString(3, s.getDescripcion());
            cs.setDate(4, s.getFechaSolicitud());
            cs.setString(5, s.getEstado());
            cs.setString(6, s.getComentarios());

            LOGGER.info("Insertar Solicitud: " + s.getIdSolicitud());
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Solicitud: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una solicitud por su ID.
     * 
     * @param id ID de la solicitud a buscar
     * @return Solicitud encontrada o null si no existe
     */
    public Solicitud obtenerPorId(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_solicitud_por_id(?)}")) {

            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearSolicitud(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Solicitud: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todas las solicitudes en la base de datos.
     * 
     * @return Lista de todas las solicitudes
     */
    public List<Solicitud> listarTodos() {
        List<Solicitud> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_solicitudes()}");
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearSolicitud(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Solicitudes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza una solicitud existente.
     * 
     * @param s Solicitud con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Solicitud s) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_actualizar_solicitud(?, ?, ?, ?, ?, ?)}")) {

            cs.setString(1, s.getIdSolicitud());
            cs.setString(2, s.getIdCuenta());
            cs.setString(3, s.getDescripcion());
            cs.setDate(4, s.getFechaSolicitud());
            cs.setString(5, s.getEstado());
            cs.setString(6, s.getComentarios());

            LOGGER.info("Actualizar Solicitud: " + s.getIdSolicitud());
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Solicitud: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una solicitud por su ID.
     * 
     * @param id ID de la solicitud a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_solicitud(?)}")) {

            cs.setString(1, id);
            LOGGER.info("Eliminar Solicitud: " + id);
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Solicitud: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cuenta el número de solicitudes en estado 'ABIERTA'.
     * 
     * @return Número de solicitudes abiertas
     */
    public int contarSolicitudesAbiertas() {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_contar_solicitudes_abiertas()}");
                ResultSet rs = cs.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al contar solicitudes abiertas: " + e.getMessage());
        }
        return 0;
    }
}
