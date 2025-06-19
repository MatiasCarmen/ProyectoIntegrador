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
import entidades.DetallePlan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetallePlanDAO: maneja CRUD de DETALLE_PLANES.
 */
public class DetallePlanDAO {
    private static final Logger LOGGER = Logger.getLogger(DetallePlanDAO.class.getName());

    public boolean crear(DetallePlan dp) {
        String sql = "INSERT INTO DETALLE_PLANES (IDPLAN, IDPRODUCTO, COSTO) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dp.getIdPlan());
            ps.setString(2, dp.getIdProducto());
            ps.setBigDecimal(3, dp.getCosto());
            LOGGER.info("Insertando DetallePlan: plan=" + dp.getIdPlan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetallePlan: " + e.getMessage());
            return false;
        }
    }

    public List<DetallePlan> listarPorPlan(String idPlan) {
        List<DetallePlan> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLE_PLANES WHERE IDPLAN = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPlan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetallePlan(
                        rs.getString("IDPLAN"),
                        rs.getString("IDPRODUCTO"),
                        rs.getBigDecimal("COSTO")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPlan: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String idPlan) {
        String sql = "DELETE FROM DETALLE_PLANES WHERE IDPLAN = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPlan);
            LOGGER.info("Eliminando DetallePlan: plan=" + idPlan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetallePlan: " + e.getMessage());
            return false;
        }
    }
}
