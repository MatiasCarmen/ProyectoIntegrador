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
import entidades.DetalleAdicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleAdicionalDAO: maneja CRUD de DETALLE_ADICIONALES usando
 * procedimientos almacenados.
 * Implementa operaciones básicas de base de datos para la entidad
 * DetalleAdicional.
 */
public class DetalleAdicionalDAO {
	private static final Logger LOGGER = Logger.getLogger(DetalleAdicionalDAO.class.getName());

	/**
	 * Crea un nuevo detalle adicional en la base de datos.
	 * 
	 * @param da El objeto DetalleAdicional a crear
	 * @return true si la creación fue exitosa, false en caso contrario
	 */
	public boolean crear(DetalleAdicional da) {
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall("{CALL sp_crear_detalle_adicional(?, ?, ?, ?)}")) {
			cs.setString(1, da.getIdAdicionales());
			cs.setString(2, da.getIdProducto());
			cs.setBigDecimal(3, da.getCosto());
			cs.registerOutParameter(4, Types.BOOLEAN);

			cs.execute();
			boolean resultado = cs.getBoolean(4);

			LOGGER.info("Detalle Adicional creado: " + da.getIdAdicionales() + "↔" + da.getIdProducto());
			return resultado;
		} catch (SQLException e) {
			LOGGER.severe("Error al crear Detalle Adicional: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lista todos los detalles adicionales asociados a un adicional específico.
	 * 
	 * @param idAdicional El ID del adicional
	 * @return Lista de detalles adicionales
	 */
	public List<DetalleAdicional> listarPorAdicional(String idAdicional) {
		List<DetalleAdicional> lista = new ArrayList<>();
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall("{CALL sp_listar_detalles_por_adicional(?)}")) {
			cs.setString(1, idAdicional);

			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleAdicional(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error al listar detalles por adicional: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Lista todos los detalles adicionales asociados a un producto específico.
	 * 
	 * @param idProducto El ID del producto
	 * @return Lista de detalles adicionales
	 */
	public List<DetalleAdicional> listarPorProducto(String idProducto) {
		List<DetalleAdicional> lista = new ArrayList<>();
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall("{CALL sp_listar_detalles_por_producto(?)}")) {
			cs.setString(1, idProducto);

			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleAdicional(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error al listar detalles por producto: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Elimina un detalle adicional específico.
	 * 
	 * @param idAdicional El ID del adicional
	 * @param idProducto  El ID del producto
	 * @return true si la eliminación fue exitosa, false en caso contrario
	 */
	public boolean eliminar(String idAdicional, String idProducto) {
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall("{CALL sp_eliminar_detalle_adicional(?, ?, ?)}")) {
			cs.setString(1, idAdicional);
			cs.setString(2, idProducto);
			cs.registerOutParameter(3, Types.BOOLEAN);

			cs.execute();
			boolean resultado = cs.getBoolean(3);

			LOGGER.info("Detalle Adicional eliminado: " + idAdicional + "↔" + idProducto);
			return resultado;
		} catch (SQLException e) {
			LOGGER.severe("Error al eliminar Detalle Adicional: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene todos los detalles adicionales asociados a un adicional específico.
	 * 
	 * @param idAdicionales El ID del adicional
	 * @return Lista de detalles adicionales
	 */
	public ArrayList<DetalleAdicional> obtenerDetallesAdicional(String idAdicionales) {
		ArrayList<DetalleAdicional> lista = new ArrayList<>();
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall("{CALL sp_obtener_detalles_adicional(?)}")) {
			cs.setString(1, idAdicionales);

			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearDetalleAdicional(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error al obtener detalles adicionales: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Mapea un ResultSet a un objeto DetalleAdicional.
	 * 
	 * @param rs El ResultSet que contiene los datos del detalle adicional
	 * @return Un objeto DetalleAdicional con los datos mapeados
	 * @throws SQLException si ocurre un error al acceder a los datos
	 */
	private DetalleAdicional mapearDetalleAdicional(ResultSet rs) throws SQLException {
		return new DetalleAdicional(
				rs.getString("IDADICIONALES"),
				rs.getString("IDPRODUCTO"),
				rs.getBigDecimal("COSTO"));
	}
}
