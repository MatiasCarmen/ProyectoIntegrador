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
import entidades.DetalleOferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleOfertaDAO: maneja CRUD de DETALLE_OFERTAS.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 * SRP: solo persistencia de DetalleOferta.
 * Seguridad: usa CallableStatement y procedimientos almacenados.
 */
public class DetalleOfertaDAO {
    private static final Logger LOGGER = Logger.getLogger(DetalleOfertaDAO.class.getName());

    /**
     * Crea un nuevo detalle de oferta.
     * 
     * @param dto El detalle de oferta a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(DetalleOferta dto) {
        String sql = "{CALL sp_crear_detalle_oferta(?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dto.getIdOfertas());
            cs.setString(2, dto.getIdProducto());
            cs.setBigDecimal(3, dto.getCosto());
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Insertando DetalleOferta: " + dto.getIdOfertas() + "↔" + dto.getIdProducto());
            return cs.getBoolean(4);
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetalleOferta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los detalles de una oferta específica.
     * 
     * @param idOferta El ID de la oferta
     * @return Lista de detalles de la oferta
     */
    public List<DetalleOferta> listarPorOferta(String idOferta) {
        List<DetalleOferta> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_detalles_por_oferta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idOferta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalleOferta(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorOferta: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Lista todos los detalles asociados a un producto específico.
     * 
     * @param idProducto El ID del producto
     * @return Lista de detalles del producto
     */
    public List<DetalleOferta> listarPorProducto(String idProducto) {
        List<DetalleOferta> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_detalles_oferta_por_producto(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idProducto);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalleOferta(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorProducto: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina un detalle de oferta específico.
     * 
     * @param idOferta   El ID de la oferta
     * @param idProducto El ID del producto
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String idOferta, String idProducto) {
        String sql = "{CALL sp_eliminar_detalle_oferta(?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idOferta);
            cs.setString(2, idProducto);
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Eliminando DetalleOferta: " + idOferta + "↔" + idProducto);
            return cs.getBoolean(3);
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetalleOferta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto DetalleOferta.
     * 
     * @param rs El ResultSet que contiene los datos
     * @return Un objeto DetalleOferta con los datos del ResultSet
     * @throws SQLException si hay un error al acceder a los datos
     */
    private DetalleOferta mapearDetalleOferta(ResultSet rs) throws SQLException {
        return new DetalleOferta(
                rs.getString("IDOFERTAS"),
                rs.getString("IDPRODUCTO"),
                rs.getBigDecimal("COSTO"));
    }
}
