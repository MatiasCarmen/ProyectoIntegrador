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
import entidades.Adicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – AdicionalDAO: maneja CRUD de ADICIONALES.
 */
public class AdicionalDAO {
    private static final Logger LOGGER = Logger.getLogger(AdicionalDAO.class.getName());

    public boolean crear(Adicional a) {
        String sql = "INSERT INTO ADICIONALES (IDADICIONALES, COSTOT) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getIdAdicionales());
            ps.setBigDecimal(2, a.getCostoT());
            LOGGER.info("Insertando Adicional: " + a.getIdAdicionales());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Adicional: " + e.getMessage());
            return false;
        }
    }

    public Adicional obtenerPorId(String id) {
        String sql = "SELECT * FROM ADICIONALES WHERE IDADICIONALES = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Adicional(
                        rs.getString("IDADICIONALES"),
                        rs.getBigDecimal("COSTOT")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Adicional: " + e.getMessage());
        }
        return null;
    }

    public List<Adicional> listarTodos() {
        List<Adicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM ADICIONALES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Adicional(
                    rs.getString("IDADICIONALES"),
                    rs.getBigDecimal("COSTOT")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Adicionales: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Adicional a) {
        String sql = "UPDATE ADICIONALES SET COSTOT = ? WHERE IDADICIONALES = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, a.getCostoT());
            ps.setString(2, a.getIdAdicionales());
            LOGGER.info("Actualizando Adicional: " + a.getIdAdicionales());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Adicional: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM ADICIONALES WHERE IDADICIONALES = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            LOGGER.info("Eliminando Adicional: " + id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Adicional: " + e.getMessage());
            return false;
        }
    }
}
