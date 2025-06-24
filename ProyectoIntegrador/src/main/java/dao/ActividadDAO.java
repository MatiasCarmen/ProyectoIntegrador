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
 * DAO de ACTIVIDADES: CRUD completo para la tabla ACTIVIDADES.
 */
public class ActividadDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadDAO.class.getName());

    public boolean crearActividad(Actividad a) {
        String sql = "INSERT INTO ACTIVIDADES "
                   + "(IDACTIVIDAD, IDCUENTA, DESCRIPCION, FECHACREACION, FECHACIERRE,"
                   + "TIPO, RAZON, DETALLE, RESOLUCION, COMENTARIOS, TELEFONO, CORREO) "
                   + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getIdActividad());
            ps.setString(2, a.getIdCuenta());
            ps.setString(3, a.getDescripcion());
            ps.setDate(4, a.getFechaCreacion());
            ps.setDate(5, a.getFechaCierre());
            ps.setString(6, a.getTipo());
            ps.setString(7, a.getRazon());
            ps.setString(8, a.getDetalle());
            ps.setString(9, a.getResolucion());
            ps.setString(10, a.getComentarios());
            ps.setLong(11, a.getTelefono());
            ps.setString(12, a.getCorreo());
            int r = ps.executeUpdate();
            LOGGER.info("Filas insertadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crearActividad: " + e.getMessage());
            return false;
        }
    }

    public List<Actividad> listarPorCuenta(String idCuenta) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDADES WHERE IDCUENTA = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Actividad a = new Actividad();
                    a.setIdActividad(rs.getString("IDACTIVIDAD"));
                    a.setIdCuenta    (rs.getString("IDCUENTA"));
                    a.setDescripcion(rs.getString("DESCRIPCION"));
                    a.setFechaCreacion(rs.getDate("FECHACREACION"));
                    a.setFechaCierre(rs.getDate("FECHACIERRE"));
                    a.setTipo        (rs.getString("TIPO"));
                    a.setRazon       (rs.getString("RAZON"));
                    a.setDetalle     (rs.getString("DETALLE"));
                    a.setResolucion  (rs.getString("RESOLUCION"));
                    a.setComentarios (rs.getString("COMENTARIOS"));
                    a.setTelefono    (rs.getLong("TELEFONO"));
                    a.setCorreo      (rs.getString("CORREO"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorCuenta: " + e.getMessage());
        }
        return lista;
    }

    public Actividad obtenerPorId(String idActividad) {
        String sql = "SELECT * FROM ACTIVIDADES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Actividad a = new Actividad();
                    a.setIdActividad(rs.getString("IDACTIVIDAD"));
                    a.setIdCuenta    (rs.getString("IDCUENTA"));
                    a.setDescripcion(rs.getString("DESCRIPCION"));
                    a.setFechaCreacion(rs.getDate("FECHACREACION"));
                    a.setFechaCierre(rs.getDate("FECHACIERRE"));
                    a.setTipo        (rs.getString("TIPO"));
                    a.setRazon       (rs.getString("RAZON"));
                    a.setDetalle     (rs.getString("DETALLE"));
                    a.setResolucion  (rs.getString("RESOLUCION"));
                    a.setComentarios (rs.getString("COMENTARIOS"));
                    a.setTelefono    (rs.getLong("TELEFONO"));
                    a.setCorreo      (rs.getString("CORREO"));
                    return a;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerPorId: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarActividad(Actividad a) {
        String sql = "UPDATE ACTIVIDADES SET "
                   + "DESCRIPCION=?, FECHACIERRE=?, TIPO=?, RAZON=?, DETALLE=?, RESOLUCION=?, COMENTARIOS=?, TELEFONO=?, CORREO=? "
                   + "WHERE IDACTIVIDAD=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getDescripcion());
            ps.setDate(2, a.getFechaCierre());
            ps.setString(3, a.getTipo());
            ps.setString(4, a.getRazon());
            ps.setString(5, a.getDetalle());
            ps.setString(6, a.getResolucion());
            ps.setString(7, a.getComentarios());
            ps.setLong(8, a.getTelefono());
            ps.setString(9, a.getCorreo());
            ps.setString(10, a.getIdActividad());
            int r = ps.executeUpdate();
            LOGGER.info("Filas actualizadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizarActividad: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarActividad(String idActividad) {
        String sql = "DELETE FROM ACTIVIDADES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            int r = ps.executeUpdate();
            LOGGER.info("Filas eliminadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminarActividad: " + e.getMessage());
            return false;
        }
    }

    public List<Actividad> listarTodas() {
        List<Actividad> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM ACTIVIDADES";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getString("IDACTIVIDAD"));
                actividad.setIdCuenta(rs.getString("IDCUENTA"));
                actividad.setDescripcion(rs.getString("DESCRIPCION"));
                actividad.setFechaCreacion(rs.getDate("FECHA_CREACION"));
                actividad.setFechaCierre(rs.getDate("FECHA_CIERRE"));
                actividad.setTipo(rs.getString("TIPO"));
                actividad.setRazon(rs.getString("RAZON"));
                actividad.setDetalle(rs.getString("DETALLE"));
                actividad.setResolucion(rs.getString("RESOLUCION"));
                actividad.setComentarios(rs.getString("COMENTARIOS"));
                actividad.setTelefono(rs.getLong("TELEFONO"));
                actividad.setCorreo(rs.getString("CORREO"));
                lista.add(actividad);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }
}
