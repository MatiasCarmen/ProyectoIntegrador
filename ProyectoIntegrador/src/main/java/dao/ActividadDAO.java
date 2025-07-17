/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mathi
 */
package dao;

import BD.ConexionBD;
import entidades.Actividad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO de ACTIVIDADES: CRUD completo para la tabla ACTIVIDADES usando
 * procedimientos almacenados.
 */
public class ActividadDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadDAO.class.getName());

    /**
     * Crea una nueva actividad utilizando el procedimiento almacenado
     * insertarActividad
     * 
     * @param a La actividad a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crearActividad(Actividad a) {
        String sql = "{CALL insertarActividad(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, a.getIdActividad());
            cs.setString(2, a.getIdCuenta());
            cs.setString(3, a.getDescripcion());
            cs.setTimestamp(4, a.getFechaCreacion());
            cs.setTimestamp(5, a.getFechaCierre());
            cs.setString(6, a.getTipo());
            cs.setString(7, a.getRazon());
            cs.setString(8, a.getDetalle());
            cs.setString(9, a.getResolucion());
            cs.setString(10, a.getComentarios());
            cs.setLong(11, a.getTelefono());
            cs.setString(12, a.getCorreo());
            cs.setString(13, a.getIdUsuario());

            int r = cs.executeUpdate();
            LOGGER.info("Filas insertadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crearActividad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista las actividades por cuenta utilizando el procedimiento almacenado
     * obtenerActividadesPorCuenta
     * 
     * @param idCuenta ID de la cuenta
     * @return Lista de actividades de la cuenta
     */
    public List<Actividad> listarPorCuenta(String idCuenta) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerActividadesPorCuenta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividad(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorCuenta: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene una actividad por su ID utilizando el procedimiento almacenado
     * obtenerActividadPorId
     * 
     * @param idActividad ID de la actividad
     * @return La actividad encontrada o null si no existe
     */
    public Actividad obtenerPorId(String idActividad) {
        String sql = "{CALL obtenerActividadPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idActividad);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearActividad(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerPorId: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza una actividad existente utilizando el procedimiento almacenado
     * actualizarActividad
     * 
     * @param a La actividad a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarActividad(Actividad a) {
        String sql = "{CALL actualizarActividad(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, a.getDescripcion());
            cs.setTimestamp(2, a.getFechaCierre());
            cs.setString(3, a.getTipo());
            cs.setString(4, a.getRazon());
            cs.setString(5, a.getDetalle());
            cs.setString(6, a.getResolucion());
            cs.setString(7, a.getComentarios());
            cs.setLong(8, a.getTelefono());
            cs.setString(9, a.getCorreo());
            cs.setString(10, a.getIdActividad());

            int r = cs.executeUpdate();
            LOGGER.info("Filas actualizadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizarActividad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una actividad por su ID utilizando el procedimiento almacenado
     * eliminarActividad
     * 
     * @param idActividad ID de la actividad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarActividad(String idActividad) {
        String sql = "{CALL eliminarActividad(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idActividad);
            int r = cs.executeUpdate();
            LOGGER.info("Filas eliminadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminarActividad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las actividades utilizando el procedimiento almacenado
     * obtenerTodasActividades
     * 
     * @return Lista de todas las actividades
     */
    public List<Actividad> listarTodas() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerTodasActividades()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearActividad(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene actividades por cuenta utilizando el procedimiento almacenado
     * obtenerActividadesPorCuenta
     * 
     * @param idCuenta ID de la cuenta
     * @return Lista de actividades de la cuenta
     */
    public List<Actividad> obtenerActividadesPorCuenta(String idCuenta) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerActividadesPorCuenta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividad(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerActividadesPorCuenta: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene las actividades pendientes utilizando el procedimiento almacenado
     * obtenerActividadesPendientes
     * 
     * @return Lista de actividades pendientes
     */
    public List<Actividad> obtenerActividadesPendientes() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerActividadesPendientes()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearActividad(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerActividadesPendientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Cuenta el número de actividades pendientes
     * 
     * @return Número de actividades pendientes
     */
    public int contarActividadesPendientes() {
        int i = 0;
        for (Actividad act : obtenerActividadesPendientes()) {
            i++;
        }
        return i;
    }

    /**
     * Cuenta el número de actividades finalizadas
     * 
     * @return Número de actividades finalizadas
     */
    public int contarActividadesFinalizadas() {
        int i = 0;
        for (Actividad act : obtenerActividadesFinalizadas()) {
            i++;
        }
        return i;
    }

    /**
     * Genera un ID de actividad único
     * 
     * @return ID de actividad único
     */
    public static String generarIdActividadUnico() {
        String id;
        String sql = "{CALL existeActividad(?)}";
        do {
            StringBuilder sb = new StringBuilder();
            sb.append("1-");
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (int i = 0; i < 8; i++) {
                int idx = (int) (Math.random() * caracteres.length());
                sb.append(caracteres.charAt(idx));
            }
            id = sb.toString();

            try (Connection conn = ConexionBD.conectar();
                    CallableStatement cs = conn.prepareCall(sql)) {
                cs.setString(1, id);
                ResultSet rs = cs.executeQuery();
                if (!rs.next())
                    break;
            } catch (SQLException e) {
                LOGGER.severe("Error generarIdActividadUnico: " + e.getMessage());
                return null;
            }
        } while (true);
        return id;
    }

    /**
     * Lista las actividades por usuario utilizando el procedimiento almacenado
     * obtenerActividadesPorUsuario
     * 
     * @param idUsuario ID del usuario
     * @return Lista de actividades del usuario
     */
    public List<Actividad> listarPorUsuario(String idUsuario) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerActividadesPorUsuario(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearActividad(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorUsuario: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene las actividades finalizadas utilizando el procedimiento almacenado
     * obtenerActividadesFinalizadas
     * 
     * @return Lista de actividades finalizadas
     */
    public List<Actividad> obtenerActividadesFinalizadas() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "{CALL obtenerActividadesFinalizadas()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearActividad(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerActividadesFinalizadas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Método auxiliar para mapear los resultados de la consulta a un objeto
     * Actividad
     * 
     * @param rs ResultSet con los datos de la actividad
     * @return Objeto Actividad con los datos del ResultSet
     * @throws SQLException Si ocurre algún error al acceder a los datos
     */
    private Actividad mapearActividad(ResultSet rs) throws SQLException {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(rs.getString("IDACTIVIDAD"));
        actividad.setIdCuenta(rs.getString("IDCUENTA"));
        actividad.setDescripcion(rs.getString("DESCRIPCION"));
        actividad.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
        actividad.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
        actividad.setTipo(rs.getString("TIPO"));
        actividad.setRazon(rs.getString("RAZON"));
        actividad.setDetalle(rs.getString("DETALLE"));
        actividad.setResolucion(rs.getString("RESOLUCION"));
        actividad.setComentarios(rs.getString("COMENTARIOS"));
        actividad.setTelefono(rs.getLong("TELEFONO"));
        actividad.setCorreo(rs.getString("CORREO"));
        actividad.setIdUsuario(rs.getString("IDUSUARIO"));
        return actividad;
    }
}
