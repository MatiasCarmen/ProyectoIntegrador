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
import entidades.Descuento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DescuentoDAO: maneja CRUD de DESCUENTOS usando procedimientos
 * almacenados.
 * Implementa operaciones básicas de base de datos para la entidad Descuento.
 */
public class DescuentoDAO {
    private static final Logger LOGGER = Logger.getLogger(DescuentoDAO.class.getName());

    /**
     * Crea un nuevo descuento en la base de datos.
     * 
     * @param d El objeto Descuento a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(Descuento d) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_crear_descuento(?, ?, ?)}")) {
            cs.setString(1, d.getIdDescuentos());
            cs.setBigDecimal(2, d.getCostoT());
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(3);

            LOGGER.info("Descuento creado con ID: " + d.getIdDescuentos());
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear descuento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un descuento por su ID.
     * 
     * @param id El ID del descuento a buscar
     * @return El objeto Descuento encontrado o null si no existe
     */
    public Descuento obtenerPorId(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_descuento_por_id(?)}")) {
            cs.setString(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearDescuento(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener descuento: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los descuentos en la base de datos.
     * 
     * @return Lista de todos los descuentos
     */
    public List<Descuento> listarTodos() {
        List<Descuento> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_descuentos()}");
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearDescuento(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar descuentos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza un descuento existente.
     * 
     * @param d El objeto Descuento con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Descuento d) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_actualizar_descuento(?, ?, ?)}")) {
            cs.setString(1, d.getIdDescuentos());
            cs.setBigDecimal(2, d.getCostoT());
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(3);

            LOGGER.info("Descuento actualizado con ID: " + d.getIdDescuentos());
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar descuento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un descuento por su ID.
     * 
     * @param id El ID del descuento a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String id) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_descuento(?, ?)}")) {
            cs.setString(1, id);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(2);

            LOGGER.info("Descuento eliminado con ID: " + id);
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar descuento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un descuento por el ID de cuenta.
     * 
     * @param idCuenta El ID de la cuenta
     * @return El objeto Descuento asociado a la cuenta o null si no existe
     */
    public Descuento obtenerDescuentoPorIdCuenta(String idCuenta) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_descuento_por_cuenta(?)}")) {
            cs.setString(1, idCuenta);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearDescuento(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener descuento por cuenta: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Descuento.
     * 
     * @param rs El ResultSet que contiene los datos del descuento
     * @return Un objeto Descuento con los datos mapeados
     * @throws SQLException si ocurre un error al acceder a los datos
     */
    private Descuento mapearDescuento(ResultSet rs) throws SQLException {
        return new Descuento(
                rs.getString("IDDESCUENTOS"),
                rs.getBigDecimal("COSTOT"));
    }
}
