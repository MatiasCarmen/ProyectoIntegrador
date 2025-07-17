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
import entidades.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – PlanDAO: maneja CRUD de PLANES usando procedimientos almacenados.
 */
public class PlanDAO {
    private static final Logger LOGGER = Logger.getLogger(PlanDAO.class.getName());

    /**
     * Crea un nuevo plan usando el procedimiento almacenado insertarPlan
     * 
     * @param p Plan a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(Plan p) {
        String sql = "{CALL insertarPlan(?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, p.getIdPlan());
            cs.setString(2, p.getNombre());
            cs.setBigDecimal(3, p.getCostoT());
            LOGGER.info("Insertando Plan: " + p.getIdPlan());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Plan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un plan por su ID usando el procedimiento almacenado obtenerPlanPorId
     * 
     * @param id ID del plan
     * @return Plan encontrado o null si no existe
     */
    public Plan obtenerPorId(String id) {
        String sql = "{CALL obtenerPlanPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearPlan(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Plan: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los planes usando el procedimiento almacenado listarPlanes
     * 
     * @return Lista de todos los planes
     */
    public List<Plan> listarTodos() {
        List<Plan> lista = new ArrayList<>();
        String sql = "{CALL listarPlanes()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPlan(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Planes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza un plan existente usando el procedimiento almacenado actualizarPlan
     * 
     * @param p Plan a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Plan p) {
        String sql = "{CALL actualizarPlan(?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, p.getNombre());
            cs.setBigDecimal(2, p.getCostoT());
            cs.setString(3, p.getIdPlan());
            LOGGER.info("Actualizando Plan: " + p.getIdPlan());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Plan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un plan por su ID usando el procedimiento almacenado eliminarPlan
     * 
     * @param id ID del plan a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String id) {
        String sql = "{CALL eliminarPlan(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            LOGGER.info("Eliminando Plan: " + id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Plan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene los planes instalados por cuenta.
     * 
     * @param idCuenta ID de la cuenta
     * @return Lista de planes instalados
     */
    public List<Plan> obtenerPlanesInstaladosPorCuenta(String idCuenta) {
        List<Plan> lista = new ArrayList<>();
        String sql = "{CALL sp_obtener_planes_instalados_por_cuenta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPlan(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener planes instalados por cuenta: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene el plan asociado a una cuenta específica.
     * 
     * @param idCuenta ID de la cuenta
     * @return Plan asociado o null si no existe
     */
    public Plan obtenerPlanPorIdCuenta(String idCuenta) {
        String sql = "{CALL sp_obtener_plan_por_id_cuenta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearPlan(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener plan por idCuenta: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Plan
     * 
     * @param rs ResultSet con los datos del plan
     * @return Plan mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Plan mapearPlan(ResultSet rs) throws SQLException {
        return new Plan(
                rs.getString("IDPLAN"),
                rs.getString("NOMBRE"),
                rs.getBigDecimal("COSTOT"));
    }
}
