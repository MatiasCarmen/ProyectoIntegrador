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
import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utils.BCryptUtil;

/**
 * DAO – UsuarioDAO: abstracción de persistencia para Usuario usando
 * procedimientos almacenados.
 * Incluye método validarLogin.
 */
public class UsuarioDAO {
    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    /**
     * Crea un nuevo usuario utilizando el procedimiento almacenado crearUsuario
     * 
     * @param user Usuario a crear
     * @throws SQLException Si ocurre un error en la BD
     */
    public void crearUsuario(Usuario user) throws SQLException {
        String sql = "{CALL crearUsuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, user.getIdUsuario());
            cs.setString(2, user.getRut());
            // Asignar rol y país por defecto si no están definidos
            cs.setString(3, user.getIdRol() != null ? user.getIdRol() : "D001");
            cs.setString(4, user.getIdPais() != null ? user.getIdPais() : "CHL");
            cs.setString(5, user.getClave());
            cs.setString(6, user.getNombres());
            cs.setString(7, user.getApellidoP());
            cs.setString(8, user.getApellidoM());
            // Área opcional
            cs.setString(9, user.getArea() != null ? user.getArea() : "");
            cs.setDate(10, new java.sql.Date(System.currentTimeMillis()));
            cs.executeUpdate();
        }
    }

    /**
     * Valida las credenciales de un usuario
     * 
     * @param idUsuario ID del usuario
     * @param clave     Contraseña a validar
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    public Usuario validarLogin(String idUsuario, String clave) {
        String sql = "{CALL obtenerUsuarioPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, idUsuario);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Usuario u = mapearUsuario(rs);

                    if (BCryptUtil.checkPassword(clave, u.getClave())) {
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error validar Usuario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los usuarios
     * 
     * @return Lista de todos los usuarios
     */
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL listarUsuarios()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Usuarios: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Genera un ID de usuario único basado en nombre y apellido
     * 
     * @param nombre    Nombre del usuario
     * @param apellidoP Apellido paterno
     * @return ID generado
     */
    public String generarIdUsuario(String nombre, String apellidoP) {
        String base = (nombre.charAt(0) + apellidoP).toLowerCase();
        String idGenerado = base;
        int sufijo = 0;
        while (existeUsuario(idGenerado)) {
            idGenerado = base + (char) ('a' + sufijo);
            sufijo++;
        }
        return idGenerado;
    }

    /**
     * Verifica si existe un usuario con el ID proporcionado
     * 
     * @param idUsuario ID a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeUsuario(String idUsuario) {
        String sql = "{CALL existeUsuario(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al verificar existencia de usuario: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene un usuario por su ID
     * 
     * @param idUsuario ID del usuario
     * @return El usuario encontrado o null si no existe
     */
    public Usuario obtenerPorId(String idUsuario) {
        String sql = "{CALL obtenerUsuarioPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener usuario por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza la información de un usuario
     * 
     * @param usuario Usuario con la información actualizada
     * @throws SQLException Si ocurre un error en la BD
     */
    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "{CALL actualizarUsuario(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, usuario.getRut());
            cs.setString(2, usuario.getIdRol());
            cs.setString(3, usuario.getIdPais());
            cs.setString(4, usuario.getClave());
            cs.setString(5, usuario.getNombres());
            cs.setString(6, usuario.getApellidoP());
            cs.setString(7, usuario.getApellidoM());
            cs.setString(8, usuario.getArea());
            cs.setString(9, usuario.getIdUsuario());
            cs.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Usuario
     * 
     * @param rs ResultSet con los datos del usuario
     * @return Usuario mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("IDUSUARIO"),
                rs.getString("RUT"),
                rs.getString("IDROL"),
                rs.getString("IDPAIS"),
                rs.getString("CLAVE"),
                rs.getString("NOMBRES"),
                rs.getString("APELLIDOP"),
                rs.getString("APELLIDOM"),
                rs.getString("AREA"),
                rs.getDate("FECHA_CREACION"));
    }
}
