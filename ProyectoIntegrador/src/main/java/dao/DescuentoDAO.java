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
import entidades.Descuento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DescuentoDAO: maneja CRUD de DESCUENTOS.
 */
public class DescuentoDAO {
    private static final Logger LOGGER = Logger.getLogger(DescuentoDAO.class.getName());

    public boolean crear(Descuento d) {
        String sql = "INSERT INTO DESCUENTOS (IDDESCUENTOS, COSTOT) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getIdDescuentos());
            ps.setBigDecimal(2, d.getCostoT());
            LOGGER.info("Insertando Descuento: " + d.getIdDescuentos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Descuento: " + e.getMessage());
            return false;
        }
    }

    public Descuento obtenerPorId(String id) {
        String sql = "SELECT * FROM DESCUENTOS WHERE IDDESCUENTOS = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Descuento(
                        rs.getString("IDDESCUENTOS"),
                        rs.getBigDecimal("COSTOT")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Descuento: " + e.getMessage());
        }
        return null;
    }

    public List<Descuento> listarTodos() {
        List<Descuento> lista = new ArrayList<>();
        String sql = "SELECT * FROM DESCUENTOS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Descuento(
                    rs.getString("IDDESCUENTOS"),
                    rs.getBigDecimal("COSTOT")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Descuentos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Descuento d) {
        String sql = "UPDATE DESCUENTOS SET COSTOT = ? WHERE IDDESCUENTOS = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, d.getCostoT());
            ps.setString(2, d.getIdDescuentos());
            LOGGER.info("Actualizando Descuento: " + d.getIdDescuentos());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error actualizar Descuento: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
       	String sql = "DELETE FROM DESCUENTOS WHERE IDDESCUENTOS = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, id);
           	LOGGER.info("Eliminando Descuento: " + id);
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error eliminar Descuento: " + e.getMessage());
           	return false;
        }
    }
    
    public Descuento obtenerDescuentoPorIdCuenta(String idCuenta) {
    Descuento descuento = null;
    String sql =  "SELECT d.* FROM PRODUCTOS_INSTALADOS pi " +
            "JOIN DESCUENTOS d ON pi.IDDESCUENTOS = d.IDDESCUENTOS " +
            "WHERE pi.IDCUENTA = ?";
    try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)){
     
        ps.setString(1, idCuenta);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            descuento = new Descuento(
                rs.getString("IDDESCUENTOS"),
                rs.getBigDecimal("COSTOT")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return descuento;
}
}
