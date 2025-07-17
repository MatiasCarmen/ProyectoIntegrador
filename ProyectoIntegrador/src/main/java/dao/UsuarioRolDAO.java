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
import entidades.UsuarioRol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – UsuarioRolDAO: maneja relación N:M USUARIOS_ROLES usando procedimientos
 * almacenados.
 */
public class UsuarioRolDAO {
	private static final Logger LOGGER = Logger.getLogger(UsuarioRolDAO.class.getName());

	/**
	 * Crea una nueva relación usuario-rol usando el procedimiento almacenado
	 * insertarUsuarioRol
	 * 
	 * @param ur Objeto UsuarioRol con los datos
	 * @return true si se creó correctamente, false en caso contrario
	 */
	public boolean crear(UsuarioRol ur) {
		String sql = "{CALL insertarUsuarioRol(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, ur.getIdUsuario());
			cs.setString(2, ur.getIdRol());
			LOGGER.info("Insertando UsuarioRol: usu=" + ur.getIdUsuario() + " rol=" + ur.getIdRol());
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error crear UsuarioRol: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lista las relaciones usuario-rol por usuario usando el procedimiento
	 * almacenado obtenerRolesPorUsuario
	 * 
	 * @param idUsuario ID del usuario
	 * @return Lista de relaciones usuario-rol
	 */
	public List<UsuarioRol> listarPorUsuario(String idUsuario) {
		List<UsuarioRol> lista = new ArrayList<>();
		String sql = "{CALL obtenerRolesPorUsuario(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idUsuario);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(new UsuarioRol(
							idUsuario, // Ya tenemos el ID del usuario por parámetro
							rs.getString("IDROL") // El ID del rol viene del resultado
					));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listarPorUsuario: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Elimina una relación usuario-rol usando el procedimiento almacenado
	 * eliminarUsuarioRol
	 * 
	 * @param idUsuario ID del usuario
	 * @param idRol     ID del rol
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminar(String idUsuario, String idRol) {
		String sql = "{CALL eliminarUsuarioRol(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idUsuario);
			cs.setString(2, idRol);
			LOGGER.info("Eliminando UsuarioRol: usu=" + idUsuario + " rol=" + idRol);
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error eliminar UsuarioRol: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lista las relaciones usuario-rol por rol usando el procedimiento almacenado
	 * obtenerUsuariosPorRol
	 * 
	 * @param idRol ID del rol
	 * @return Lista de relaciones usuario-rol
	 */
	public List<UsuarioRol> listarPorRol(String idRol) {
		List<UsuarioRol> lista = new ArrayList<>();
		String sql = "{CALL obtenerUsuariosPorRol(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(new UsuarioRol(
							rs.getString("IDUSUARIO"),
							idRol // Ya tenemos el ID del rol por parámetro
					));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listarPorRol: " + e.getMessage());
		}
		return lista;
	}
}
