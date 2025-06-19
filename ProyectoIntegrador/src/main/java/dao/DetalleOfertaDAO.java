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
import entidades.DetalleOferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleOfertaDAO: maneja CRUD de DETALLE_OFERTAS.
 * SRP: solo persistencia de DetalleOferta.
 * Seguridad: usa PreparedStatement.
 */
public class DetalleOfertaDAO {
    private static final Logger LOGGER = Logger.getLogger(DetalleOfertaDAO.class.getName());

    public boolean crear(DetalleOferta dto) {
        String sql = "INSERT INTO DETALLE_OFERTAS (IDOFERTAS, IDPRODUCTO, COSTO) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getIdOfertas());
            ps.setString(2, dto.getIdProducto());
            ps.setBigDecimal(3, dto.getCosto());
            LOGGER.info("Insertando DetalleOferta: " + dto.getIdOfertas() + "↔" + dto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetalleOferta: " + e.getMessage());
            return false;
        }
    }

    public List<DetalleOferta> listarPorOferta(String idOferta) {
        List<DetalleOferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLE_OFERTAS WHERE IDOFERTAS = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idOferta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetalleOferta(
                        rs.getString("IDOFERTAS"),
                        rs.getString("IDPRODUCTO"),
                        rs.getBigDecimal("COSTO")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorOferta: " + e.getMessage());
        }
        return lista;
    }

    public List<DetalleOferta> listarPorProducto(String idProducto) {
        List<DetalleOferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLE_OFERTAS WHERE IDPRODUCTO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetalleOferta(
                        rs.getString("IDOFERTAS"),
                        rs.getString("IDPRODUCTO"),
                        rs.getBigDecimal("COSTO")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorProducto: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String idOferta, String idProducto) {
        String sql = "DELETE FROM DETALLE_OFERTAS WHERE IDOFERTAS = ? AND IDPRODUCTO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idOferta);
            ps.setString(2, idProducto);
            LOGGER.info("Eliminando DetalleOferta: " + idOferta + "↔" + idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetalleOferta: " + e.getMessage());
            return false;
        }
    }
}
