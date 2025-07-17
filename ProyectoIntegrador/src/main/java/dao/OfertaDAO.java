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
import entidades.Oferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – OfertaDAO: abstracción de persistencia para Oferta.
 */
public class OfertaDAO {
    private static final Logger LOGGER = Logger.getLogger(OfertaDAO.class.getName());

    public boolean crear(Oferta o) {
        String sql = "INSERT INTO OFERTAS " +
          "(IDOFERTA, IDCUENTA, DESCRIPCION, FECHAINICIO, FECHAFIN, PORCENTAJEDESCUENTO) " +
          "VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getIdOferta());
            ps.setString(2, o.getIdCuenta());
            ps.setString(3, o.getDescripcion());
            ps.setDate(4, o.getFechaInicio());
            ps.setDate(5, o.getFechaFin());
            ps.setDouble(6, o.getPorcentajeDescuento());
            LOGGER.info("Insertar Oferta: " + o.getIdOferta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Oferta: " + e.getMessage());
            return false;
        }
    }

    public Oferta obtenerPorId(String id) {
        String sql = "SELECT * FROM OFERTAS WHERE IDOFERTA = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Oferta(
                      rs.getString("IDOFERTA"),
                      rs.getString("IDCUENTA"),
                      rs.getString("DESCRIPCION"),
                      rs.getDate("FECHAINICIO"),
                      rs.getDate("FECHAFIN"),
                      rs.getDouble("PORCENTAJEDESCUENTO")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Oferta: " + e.getMessage());
        }
        return null;
    }

    public List<Oferta> listarTodos() {
        List<Oferta> lista = new ArrayList<>();
        String sql = "SELECT * FROM OFERTAS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Oferta(
                  rs.getString("IDOFERTA"),
                  rs.getString("IDCUENTA"),
                  rs.getString("DESCRIPCION"),
                  rs.getDate("FECHAINICIO"),
                  rs.getDate("FECHAFIN"),
                  rs.getDouble("PORCENTAJEDESCUENTO")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Ofertas: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Oferta o) {
        String sql = "UPDATE OFERTAS SET IDCUENTA=?, DESCRIPCION=?, FECHAINICIO=?, FECHAFIN=?, PORCENTAJEDESCUENTO=? WHERE IDOFERTA=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getIdCuenta());
            ps.setString(2, o.getDescripcion());
            ps.setDate(3, o.getFechaInicio());
            ps.setDate(4, o.getFechaFin());
            ps.setDouble(5, o.getPorcentajeDescuento());
            ps.setString(6, o.getIdOferta());
            LOGGER.info("Actualizar Oferta: " + o.getIdOferta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Oferta: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM OFERTAS WHERE IDOFERTA = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            LOGGER.info("Eliminar Oferta: " + id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Oferta: " + e.getMessage());
            return false;
        }
    }
}
