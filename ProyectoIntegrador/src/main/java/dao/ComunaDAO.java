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
 * DAO – ComunaDAO: acceso a la tabla COMUNAS usando procedimientos almacenados.
 * SRP: solo persistencia de entidades Comuna.
 */
public class ComunaDAO {
    private static final Logger LOGGER = Logger.getLogger(ComunaDAO.class.getName());

    /**
     * Lista todas las comunas (para llenar combos) usando el procedimiento
     * almacenado listarComunas.
     *
     * @return Lista de todas las comunas
     */
    public List<Comuna> listarComunas() {
        List<Comuna> lista = new ArrayList<>();
        String sql = "{CALL listarComunas()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearComuna(rs));
            }
            LOGGER.info("ComunaDAO: listarComunas → " + lista.size() + " filas");
        } catch (SQLException e) {
            LOGGER.severe("Error listarComunas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Dado el nombre/descripción de una comuna, devuelve su ID.
     * 
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

    /**
     * Crea una nueva comuna usando el procedimiento almacenado insertarComuna
     * 
     * @param comuna Comuna a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crearComuna(Comuna comuna) {
        String sql = "{CALL insertarComuna(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, comuna.getIdComuna());
            cs.setString(2, comuna.getDescripcion());

            int r = cs.executeUpdate();
            LOGGER.info("ComunaDAO: crearComuna → " + (r > 0 ? "éxito" : "fallo"));
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crearComuna: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una comuna por su ID usando el procedimiento almacenado
     * obtenerComunaPorId
     * 
     * @param idComuna ID de la comuna
     * @return Comuna encontrada o null si no existe
     */
    public Comuna obtenerPorId(String idComuna) {
        String sql = "{CALL obtenerComunaPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idComuna);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Comuna comuna = mapearComuna(rs);
                    LOGGER.info("ComunaDAO: obtenerPorId(" + idComuna + ") → éxito");
                    return comuna;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerPorId: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza una comuna usando el procedimiento almacenado actualizarComuna
     * 
     * @param comuna Comuna a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarComuna(Comuna comuna) {
        String sql = "{CALL actualizarComuna(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, comuna.getDescripcion());
            cs.setString(2, comuna.getIdComuna());

            int r = cs.executeUpdate();
            LOGGER.info("ComunaDAO: actualizarComuna → " + (r > 0 ? "éxito" : "fallo"));
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizarComuna: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una comuna por su ID usando el procedimiento almacenado
     * eliminarComuna
     * 
     * @param idComuna ID de la comuna a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarComuna(String idComuna) {
        String sql = "{CALL eliminarComuna(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idComuna);

            int r = cs.executeUpdate();
            LOGGER.info("ComunaDAO: eliminarComuna → " + (r > 0 ? "éxito" : "fallo"));
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminarComuna: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Comuna
     * 
     * @param rs ResultSet con los datos de la comuna
     * @return Comuna mapeada
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Comuna mapearComuna(ResultSet rs) throws SQLException {
        return new Comuna(
                rs.getString("IDCOMUNA"),
                rs.getString("DESCRIPCION"));
    }
}
