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
import entidades.Funcion;
import entidades.Rol;
import entidades.RolFuncion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – RolFuncionDAO: maneja relación N:M ROL_FUNCION usando procedimientos
 * almacenados.
 */
public class RolFuncionDAO {
	private static final Logger LOGGER = Logger.getLogger(RolFuncionDAO.class.getName());

	/**
	 * Asigna una función a un rol usando el procedimiento almacenado
	 * insertarRolFuncion
	 * 
	 * @param idRol     ID del rol
	 * @param idFuncion ID de la función
	 * @return true si se asignó correctamente, false en caso contrario
	 */
	public boolean asignarFuncionARol(String idRol, String idFuncion) {
		String sql = "{CALL insertarRolFuncion(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			cs.setString(2, idFuncion);
			LOGGER.info("Asignando función " + idFuncion + " al rol " + idRol);
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error asignar función a rol: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene todas las funciones de un rol usando el procedimiento almacenado
	 * obtenerFuncionesPorRol
	 * 
	 * @param idRol ID del rol
	 * @return Lista de funciones asignadas al rol
	 */
	public List<Funcion> obtenerFuncionesPorRol(String idRol) {
		List<Funcion> lista = new ArrayList<>();
		String sql = "{CALL obtenerFuncionesPorRol(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(new Funcion(
							rs.getString("IDFUNCION"),
							rs.getString("DESCRIPCION")));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error obtener funciones por rol: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Obtiene todos los roles que tienen asignada una función usando el
	 * procedimiento almacenado obtenerRolesPorFuncion
	 * 
	 * @param idFuncion ID de la función
	 * @return Lista de roles que tienen la función
	 */
	public List<Rol> obtenerRolesPorFuncion(String idFuncion) {
		List<Rol> lista = new ArrayList<>();
		String sql = "{CALL obtenerRolesPorFuncion(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idFuncion);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(new Rol(
							rs.getString("IDROL"),
							rs.getString("DESCRIPCION")));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error obtener roles por función: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Elimina la asignación de una función a un rol usando el procedimiento
	 * almacenado eliminarRolFuncion
	 * 
	 * @param idRol     ID del rol
	 * @param idFuncion ID de la función
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminarFuncionDeRol(String idRol, String idFuncion) {
		String sql = "{CALL eliminarRolFuncion(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			cs.setString(2, idFuncion);
			LOGGER.info("Eliminando función " + idFuncion + " del rol " + idRol);
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error eliminar función de rol: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Crea una nueva relación Rol-Función.
	 * 
	 * @param rf Objeto RolFuncion a crear
	 * @return true si se creó correctamente, false en caso contrario
	 */
	public boolean crear(RolFuncion rf) {
		String sql = "{CALL sp_crear_rol_funcion(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, rf.getIdRol());
			cs.setString(2, rf.getIdFuncion());
			LOGGER.info("Creando RolFuncion: " + rf.getIdRol() + "-" + rf.getIdFuncion());
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error crear RolFuncion: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lista todas las funciones asociadas a un rol.
	 * 
	 * @param idRol ID del rol
	 * @return Lista de RolFuncion
	 */
	public List<RolFuncion> listarPorRol(String idRol) {
		List<RolFuncion> lista = new ArrayList<>();
		String sql = "{CALL sp_listar_rol_funcion_por_rol(?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					lista.add(new RolFuncion(
							rs.getString("IDROL"),
							rs.getString("IDFUNCION")));
				}
			}
		} catch (SQLException e) {
			LOGGER.severe("Error listar RolFuncion por rol: " + e.getMessage());
		}
		return lista;
	}

	/**
	 * Elimina una relación Rol-Función.
	 * 
	 * @param idRol     ID del rol
	 * @param idFuncion ID de la función
	 * @return true si se eliminó correctamente, false en caso contrario
	 */
	public boolean eliminar(String idRol, String idFuncion) {
		String sql = "{CALL sp_eliminar_rol_funcion(?, ?)}";
		try (Connection conn = ConexionBD.conectar();
				CallableStatement cs = conn.prepareCall(sql)) {
			cs.setString(1, idRol);
			cs.setString(2, idFuncion);
			LOGGER.info("Eliminando RolFuncion: " + idRol + "-" + idFuncion);
			return cs.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.severe("Error eliminar RolFuncion: " + e.getMessage());
			return false;
		}
	}
}
