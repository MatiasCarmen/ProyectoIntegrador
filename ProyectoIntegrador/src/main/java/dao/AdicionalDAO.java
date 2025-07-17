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
import entidades.Adicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – AdicionalDAO: maneja CRUD de ADICIONALES usando procedimientos
 * almacenados.
 * SOLID – SRP: Solo maneja operaciones relacionadas con Adicionales.
 */
public class AdicionalDAO {
    private static final Logger LOGGER = Logger.getLogger(AdicionalDAO.class.getName());

    /**
     * Mapea un ResultSet a un objeto Adicional.
     * 
     * @param rs ResultSet con los datos del adicional
     * @return Objeto Adicional mapeado
     * @throws SQLException si hay error al acceder a los datos
     */
    private Adicional mapearAdicional(ResultSet rs) throws SQLException {
        return new Adicional(
                rs.getString("IDADICIONALES"),
                rs.getBigDecimal("COSTOT"));
    }

    /**
     * Crea un nuevo adicional en la base de datos.
     * 
     * @param a Adicional a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(Adicional a) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_crear_adicional(?, ?)}")) {

            cs.setString(1, a.getIdAdicionales());
            cs.setBigDecimal(2, a.getCostoT());

            LOGGER.info("Insertando Adicional: " + a.getIdAdicionales());
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Adicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un adicional por su ID.
     * 
     * @param id ID del adicional a buscar
     * @return Adicional encontrado o null si no existe
     */
    public Adicional obtenerPorId(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_adicional_por_id(?)}")) {

            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearAdicional(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Adicional: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los adicionales en la base de datos.
     * 
     * @return Lista de todos los adicionales
     */
    public List<Adicional> listarTodos() {
        List<Adicional> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_adicionales()}");
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAdicional(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Adicionales: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza un adicional existente.
     * 
     * @param a Adicional con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Adicional a) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_actualizar_adicional(?, ?)}")) {

            cs.setString(1, a.getIdAdicionales());
            cs.setBigDecimal(2, a.getCostoT());

            LOGGER.info("Actualizando Adicional: " + a.getIdAdicionales());
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Adicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un adicional por su ID.
     * 
     * @param id ID del adicional a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_adicional(?)}")) {

            cs.setString(1, id);
            LOGGER.info("Eliminando Adicional: " + id);
            cs.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Adicional: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el adicional asociado a una cuenta específica.
     * 
     * @param idCuenta ID de la cuenta a buscar
     * @return Adicional asociado a la cuenta o null si no existe
     */
    public Adicional obtenerAdicionalPorIdCuenta(String idCuenta) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_adicional_por_cuenta(?)}")) {

            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearAdicional(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Adicional por cuenta: " + e.getMessage());
        }
        return null;
    }
}
