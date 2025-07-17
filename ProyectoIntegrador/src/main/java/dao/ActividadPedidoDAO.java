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
import entidades.ActividadPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – ActividadPedidoDAO: maneja CRUD de la relación ACTIVIDAD_PEDIDOS usando
 * procedimientos almacenados.
 * SOLID – SRP: solo persistencia de ActividadPedido.
 * Seguridad: usa procedimientos almacenados y manejo de transacciones.
 */
public class ActividadPedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadPedidoDAO.class.getName());

    /**
     * Mapea un ResultSet a un objeto ActividadPedido.
     * 
     * @param rs ResultSet con los datos de la relación actividad-pedido
     * @return Objeto ActividadPedido mapeado
     * @throws SQLException si hay error al acceder a los datos
     */
    private ActividadPedido mapearActividadPedido(ResultSet rs) throws SQLException {
        return new ActividadPedido(
                rs.getString("IDACTIVIDAD"),
                rs.getString("IDPEDIDO"),
                rs.getString("CATEGORIA"));
    }

    /**
     * Crea una nueva relación entre actividad y pedido.
     * 
     * @param ap ActividadPedido a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(ActividadPedido ap) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_crear_actividad_pedido(?, ?, ?)}")) {

            cs.setString(1, ap.getIdActividad());
            cs.setString(2, ap.getIdPedido());
            cs.setString(3, ap.getCategoria());

            LOGGER.info("Insertando ActividadPedido: " + ap.getIdActividad() + "↔" + ap.getIdPedido());
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error crear ActividadPedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las relaciones actividad-pedido para una actividad específica.
     * 
     * @param idActividad ID de la actividad a buscar
     * @return Lista de ActividadPedido asociados a la actividad
     */
    public List<ActividadPedido> listarPorActividad(String idActividad) {
        List<ActividadPedido> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_pedidos_por_actividad(?)}")) {

            cs.setString(1, idActividad);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividadPedido(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorActividad: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Lista todas las relaciones actividad-pedido para un pedido específico.
     * 
     * @param idPedido ID del pedido a buscar
     * @return Lista de ActividadPedido asociados al pedido
     */
    public List<ActividadPedido> listarPorPedido(String idPedido) {
        List<ActividadPedido> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_actividades_por_pedido(?)}")) {

            cs.setString(1, idPedido);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividadPedido(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPedido: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina una relación actividad-pedido específica.
     * 
     * @param idActividad ID de la actividad
     * @param idPedido    ID del pedido
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String idActividad, String idPedido) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_actividad_pedido(?, ?)}")) {

            cs.setString(1, idActividad);
            cs.setString(2, idPedido);

            LOGGER.info("Eliminando ActividadPedido: " + idActividad + "↔" + idPedido);
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar ActividadPedido: " + e.getMessage());
            return false;
        }
    }
}
