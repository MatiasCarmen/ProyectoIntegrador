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
import entidades.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – RolDAO: abstracción de persistencia para Rol usando procedimientos
 * almacenados.
 */
public class RolDAO {
    private static final Logger LOGGER = Logger.getLogger(RolDAO.class.getName());

    /**
     * Lista todos los roles usando el procedimiento almacenado listarRoles
     * 
     * @return Lista de todos los roles
     */
    public List<Rol> listarTodos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "{CALL listarRoles()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearRol(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Roles: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene el ID de un rol por su nombre
     * 
     * @param nombreRol Nombre del rol
     * @return ID del rol o null si no existe
     */
    public String obtenerIdPorNombre(String nombreRol) {
        // Para este método necesitaríamos un procedimiento almacenado adicional
        // Por ahora seguimos con la consulta directa hasta crear el procedimiento
        // correspondiente
        String sql = "SELECT IDROL FROM ROLES WHERE DESCRIPCION = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("IDROL");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener ID de Rol por nombre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene el nombre de un rol por su ID
     * 
     * @param idRol ID del rol
     * @return Nombre del rol o null si no existe
     */
    public String obtenerNombrePorId(String idRol) {
        String sql = "{CALL obtenerRolPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idRol);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("DESCRIPCION");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener nombre de Rol por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Crea un nuevo rol
     * 
     * @param rol Rol a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crearRol(Rol rol) {
        String sql = "{CALL insertarRol(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, rol.getIdRol());
            cs.setString(2, rol.getNombre());

            int r = cs.executeUpdate();
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear rol: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un rol existente
     * 
     * @param rol Rol a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarRol(Rol rol) {
        String sql = "{CALL actualizarRol(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, rol.getNombre());
            cs.setString(2, rol.getIdRol());

            int r = cs.executeUpdate();
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar rol: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un rol por su ID
     * 
     * @param idRol ID del rol a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarRol(String idRol) {
        String sql = "{CALL eliminarRol(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idRol);

            int r = cs.executeUpdate();
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar rol: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Rol
     * 
     * @param rs ResultSet con los datos del rol
     * @return Rol mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Rol mapearRol(ResultSet rs) throws SQLException {
        return new Rol(
                rs.getString("IDROL"),
                rs.getString("DESCRIPCION"));
    }
}
