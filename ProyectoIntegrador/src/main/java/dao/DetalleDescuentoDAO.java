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
import entidades.DetalleDescuento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleDescuentoDAO: maneja CRUD de DETALLE_DESCUENTOS.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 */
public class DetalleDescuentoDAO {
	private static final Logger LOGGER = Logger.getLogger(DetalleDescuentoDAO.class.getName());

	/**
	 * Crea un nuevo detalle de descuento.
	 * 
	 * @param dd El detalle de descuento a crear
	 * @return true si la creación fue exitosa, false en caso contrario
	 */
	public boolean crear(DetalleDescuento dd) {
		String sql = "{CALL sp_crear_detalle_descuento(?, ?, ?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, dd.getIdDescuentos());
			cs.setString(2, dd.getIdProducto());
			cs.setBigDecimal(3, dd.getCosto());
			cs.registerOutParameter(4, Types.BOOLEAN);

			cs.execute();
			LOGGER.info("Insertando DetalleDescuento: " + dd.getIdDescuentos() + "↔" + dd.getIdProducto());
			return cs.getBoolean(4);
		} catch (SQLException e) {
			LOGGER.severe("Error crear DetalleDescuento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lista todos los detalles de un descuento específico.
	 * 
	 * @param idDescuento El ID del descuento
	 * @return Lista de detalles del descuento
	 */
	public List<DetalleDescuento> listarPorDescuento(String idDescuento) {
		List<DetalleDescuento> lista = new ArrayList<>();
		String sql = "{CALL sp_listar_detalles_por_descuento(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idDescuento);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleDescuento(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listarPorDescuento: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Lista todos los detalles asociados a un producto específico.
	 * 
	 * @param idProducto El ID del producto
	 * @return Lista de detalles del producto
	 */
	public List<DetalleDescuento> listarPorProducto(String idProducto) {
		List<DetalleDescuento> lista = new ArrayList<>();
		String sql = "{CALL sp_listar_detalles_por_producto(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idProducto);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleDescuento(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listarPorProducto: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Elimina un detalle de descuento específico.
	 * 
	 * @param idDescuento El ID del descuento
	 * @param idProducto  El ID del producto
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public boolean eliminar(String idDescuento, String idProducto) {
		String sql = "{CALL sp_eliminar_detalle_descuento(?, ?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idDescuento);
			cs.setString(2, idProducto);
			cs.registerOutParameter(3, Types.BOOLEAN);

			cs.execute();
			LOGGER.info("Eliminando DetalleDescuento: " + idDescuento + "↔" + idProducto);
			return cs.getBoolean(3);
		} catch (SQLException e) {
			LOGGER.severe("Error eliminar DetalleDescuento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene todos los detalles de un descuento específico.
	 * 
	 * @param idDescuento El ID del descuento
	 * @return Lista de detalles del descuento
	 */
	public ArrayList<DetalleDescuento> obtenerDetallesDescuento(String idDescuento) {
		ArrayList<DetalleDescuento> lista = new ArrayList<>();
		String sql = "{CALL sp_obtener_detalles_descuento(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idDescuento);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleDescuento(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error al obtener detalles del descuento: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Mapea un ResultSet a un objeto DetalleDescuento.
	 * 
	 * @param rs El ResultSet que contiene los datos
	 * @return Un objeto DetalleDescuento con los datos del ResultSet
	 * @throws SQLException si hay un error al acceder a los datos
	 */
	private DetalleDescuento mapearDetalleDescuento(ResultSet rs) throws SQLException {
		return new DetalleDescuento(
				rs.getString("IDDESCUENTOS"),
				rs.getString("IDPRODUCTO"),
				rs.getBigDecimal("COSTO"));
	}
}
