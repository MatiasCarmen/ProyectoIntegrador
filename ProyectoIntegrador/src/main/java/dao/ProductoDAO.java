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
import entidades.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – ProductoDAO: maneja CRUD de PRODUCTOS usando procedimientos
 * almacenados.
 */
public class ProductoDAO {
	private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

	/**
	 * Crea un nuevo producto usando el procedimiento almacenado insertarProducto
	 * 
	 * @param p Producto a crear
	 * @return true si se creó correctamente, false en caso contrario
	 */
	public boolean crear(Producto p) {
		String sql = "{CALL insertarProducto(?, ?, ?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, p.getIdProducto());
			cs.setString(2, p.getTipo());
			cs.setString(3, p.getDescripcion());
			cs.setString(4, p.getModalidad());
			LOGGER.info("Insertando Producto: " + p.getIdProducto());
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error crear Producto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene un producto por su ID usando el procedimiento almacenado
	 * obtenerProductoPorId
	 * 
	 * @param id ID del producto
	 * @return Producto encontrado o null si no existe
	 */
	public Producto obtenerPorId(String id) {
		String sql = "{CALL obtenerProductoPorId(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, id);
			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next()) {
					return mapearProducto(rs);
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error obtener Producto: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Lista todos los productos usando el procedimiento almacenado listarProductos
	 * 
	 * @return Lista de todos los productos
	 */
	public List<Producto> listarTodos() {
		List<Producto> lista = new ArrayList<>();
		String sql = "{CALL listarProductos()}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql);
				ResultSet rs = cs.executeQuery()) {
			while (rs.next()) {
				lista.add(mapearProducto(rs));
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listar Productos: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Actualiza un producto existente usando el procedimiento almacenado
	 * actualizarProducto
	 * 
	 * @param p Producto a actualizar
	 * @return true si se actualizó correctamente, false en caso contrario
	 */
	public boolean actualizar(Producto p) {
		String sql = "{CALL actualizarProducto(?, ?, ?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, p.getTipo());
			cs.setString(2, p.getDescripcion());
			cs.setString(3, p.getModalidad());
			cs.setString(4, p.getIdProducto());
			LOGGER.info("Actualizando Producto: " + p.getIdProducto());
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error actualizar Producto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Elimina un producto por su ID usando el procedimiento almacenado
	 * eliminarProducto
	 * 
	 * @param id ID del producto a eliminar
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminar(String id) {
		String sql = "{CALL eliminarProducto(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, id);
			LOGGER.info("Eliminando Producto: " + id);
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error eliminar Producto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene productos por su tipo usando el procedimiento almacenado
	 * obtenerProductosPorTipo
	 * 
	 * @param tipo Tipo de producto
	 * @return Lista de productos del tipo especificado
	 */
	public List<Producto> obtenerPorTipo(String tipo) {
		List<Producto> lista = new ArrayList<>();
		String sql = "{CALL obtenerProductosPorTipo(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, tipo);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(mapearProducto(rs));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error obtener Productos por tipo: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Mapea un ResultSet a un objeto Producto
	 * 
	 * @param rs ResultSet con los datos del producto
	 * @return Producto mapeado
	 * @throws SQLException Si ocurre un error al acceder a los datos
	 */
	private Producto mapearProducto(ResultSet rs) throws SQLException {
		return new Producto(
				rs.getString("IDPRODUCTO"),
				rs.getString("TIPO"),
				rs.getString("DESCRIPCION"),
				rs.getString("MODALIDAD"));
	}
}
