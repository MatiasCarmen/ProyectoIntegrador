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
import entidades.Solicitud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – SolicitudDAO: abstracción de persistencia para Solicitud.
 */
public class SolicitudDAO {
    private static final Logger LOGGER = Logger.getLogger(SolicitudDAO.class.getName());

    public boolean crear(Solicitud s) {
        String sql = "INSERT INTO SOLICITUDES " +
                "(IDSOLICITUD, IDCUENTA, DESCRIPCION, FECHASOLICITUD, ESTADO, COMENTARIOS) " +
                "VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getIdSolicitud());
            ps.setString(2, s.getIdCuenta());
            ps.setString(3, s.getDescripcion());
            ps.setDate(4, s.getFechaSolicitud());
            ps.setString(5, s.getEstado());
            ps.setString(6, s.getComentarios());
            LOGGER.info("Insertar Solicitud: " + s.getIdSolicitud());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Solicitud: " + e.getMessage());
            return false;
        }
    }

    public Solicitud obtenerPorId(String id) {
        String sql = "SELECT * FROM SOLICITUDES WHERE IDSOLICITUD = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Solicitud(
                            rs.getString("IDSOLICITUD"),
                            rs.getString("IDCUENTA"),
                            rs.getString("DESCRIPCION"),
                            rs.getDate("FECHASOLICITUD"),
                            rs.getString("ESTADO"),
                            rs.getString("COMENTARIOS"));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Solicitud: " + e.getMessage());
        }
        return null;
    }

    public List<Solicitud> listarTodos() {
        List<Solicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM SOLICITUDES";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Solicitud(
                        rs.getString("IDSOLICITUD"),
                        rs.getString("IDCUENTA"),
                        rs.getString("DESCRIPCION"),
                        rs.getDate("FECHASOLICITUD"),
                        rs.getString("ESTADO"),
                        rs.getString("COMENTARIOS")));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Solicitudes: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Solicitud s) {
        String sql = "UPDATE SOLICITUDES SET IDCUENTA=?, DESCRIPCION=?, FECHASOLICITUD=?, " +
                "ESTADO=?, COMENTARIOS=? WHERE IDSOLICITUD=?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getIdCuenta());
            ps.setString(2, s.getDescripcion());
            ps.setDate(3, s.getFechaSolicitud());
            ps.setString(4, s.getEstado());
            ps.setString(5, s.getComentarios());
            ps.setString(6, s.getIdSolicitud());
            LOGGER.info("Actualizar Solicitud: " + s.getIdSolicitud());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Solicitud: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM SOLICITUDES WHERE IDSOLICITUD = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            LOGGER.info("Eliminar Solicitud: " + id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Solicitud: " + e.getMessage());
            return false;
        }
    }

    public int contarSolicitudesAbiertas() {
        String sql = "SELECT COUNT(*) FROM SOLICITUDES WHERE ESTADO = 'ABIERTA'";
        try (Connection conn = ConexionBD.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al contar solicitudes abiertas: " + e.getMessage());
        }
        return 0;
    }
}
