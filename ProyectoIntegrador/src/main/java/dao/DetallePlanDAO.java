/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import BD.ConexionBD;
import entidades.DetallePlan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetallePlanDAO: maneja CRUD de DETALLE_PLANES.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 */
public class DetallePlanDAO {
    private static final Logger LOGGER = Logger.getLogger(DetallePlanDAO.class.getName());

    /**
     * Crea un nuevo detalle de plan.
     * 
     * @param dp El detalle de plan a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(DetallePlan dp) {
        String sql = "{CALL sp_crear_detalle_plan(?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dp.getIdPlan());
            cs.setString(2, dp.getIdProducto());
            cs.setBigDecimal(3, dp.getCosto());
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Insertando DetallePlan: plan=" + dp.getIdPlan());
            return cs.getBoolean(4);
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetallePlan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los detalles de un plan específico.
     * 
     * @param idPlan El ID del plan
     * @return Lista de detalles del plan
     */
    public List<DetallePlan> listarPorPlan(String idPlan) {
        List<DetallePlan> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_detalles_por_plan(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPlan);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetallePlan(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPlan: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina todos los detalles de un plan.
     * 
     * @param idPlan El ID del plan cuyos detalles se eliminarán
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String idPlan) {
        String sql = "{CALL sp_eliminar_detalles_plan(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPlan);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Eliminando DetallePlan: plan=" + idPlan);
            return cs.getBoolean(2);
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetallePlan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los detalles de un plan específico.
     * 
     * @param idPlan El ID del plan
     * @return Lista de detalles del plan
     */
    public ArrayList<DetallePlan> obtenerDetallesPlan(String idPlan) {
        ArrayList<DetallePlan> lista = new ArrayList<>();
        String sql = "{CALL sp_obtener_detalles_plan(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPlan);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetallePlan(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener detalles del plan: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto DetallePlan.
     * 
     * @param rs El ResultSet que contiene los datos
     * @return Un objeto DetallePlan con los datos del ResultSet
     * @throws SQLException si hay un error al acceder a los datos
     */
    private DetallePlan mapearDetallePlan(ResultSet rs) throws SQLException {
        return new DetallePlan(
                rs.getString("IDPLAN"),
                rs.getString("IDPRODUCTO"),
                rs.getBigDecimal("COSTO"));
    }
}
