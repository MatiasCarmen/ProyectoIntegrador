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
import entidades.ActividadPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – ActividadPedidoDAO: maneja CRUD de la relación ACTIVIDAD_PEDIDOS.
 * SRP: solo persistencia de ActividadPedido.
 * Seguridad: usa PreparedStatement.
 */
public class ActividadPedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadPedidoDAO.class.getName());

    public boolean crear(ActividadPedido ap) {
        String sql = "INSERT INTO ACTIVIDAD_PEDIDOS (IDACTIVIDAD, IDPEDIDO, CATEGORIA) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ap.getIdActividad());
            ps.setString(2, ap.getIdPedido());
            ps.setString(3, ap.getCategoria());
            LOGGER.info("Insertando ActividadPedido: " + ap.getIdActividad() + "↔" + ap.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear ActividadPedido: " + e.getMessage());
            return false;
        }
    }

    public List<ActividadPedido> listarPorActividad(String idActividad) {
        List<ActividadPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDAD_PEDIDOS WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ActividadPedido(
                        rs.getString("IDACTIVIDAD"),
                        rs.getString("IDPEDIDO"),
                        rs.getString("CATEGORIA")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorActividad: " + e.getMessage());
        }
        return lista;
    }

    public List<ActividadPedido> listarPorPedido(String idPedido) {
        List<ActividadPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDAD_PEDIDOS WHERE IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ActividadPedido(
                        rs.getString("IDACTIVIDAD"),
                        rs.getString("IDPEDIDO"),
                        rs.getString("CATEGORIA")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPedido: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String idActividad, String idPedido) {
        String sql = "DELETE FROM ACTIVIDAD_PEDIDOS WHERE IDACTIVIDAD = ? AND IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            ps.setString(2, idPedido);
            LOGGER.info("Eliminando ActividadPedido: " + idActividad + "↔" + idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar ActividadPedido: " + e.getMessage());
            return false;
        }
    }
}
