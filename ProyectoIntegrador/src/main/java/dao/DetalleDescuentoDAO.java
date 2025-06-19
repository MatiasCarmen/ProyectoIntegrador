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
import entidades.DetalleDescuento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleDescuentoDAO: maneja CRUD de DETALLE_DESCUENTOS.
 */
public class DetalleDescuentoDAO {
    private static final Logger LOGGER = Logger.getLogger(DetalleDescuentoDAO.class.getName());

    public boolean crear(DetalleDescuento dd) {
       	String sql = "INSERT INTO DETALLE_DESCUENTOS (IDDESCUENTOS, IDPRODUCTO, COSTO) VALUES (?,?,?)";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, dd.getIdDescuentos());
           	ps.setString(2, dd.getIdProducto());
           	ps.setBigDecimal(3, dd.getCosto());
           	LOGGER.info("Insertando DetalleDescuento: " + dd.getIdDescuentos() + "↔" + dd.getIdProducto());
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error crear DetalleDescuento: " + e.getMessage());
           	return false;
       	}
    }

    public List<DetalleDescuento> listarPorDescuento(String idDescuento) {
       	List<DetalleDescuento> lista = new ArrayList<>();
       	String sql = "SELECT * FROM DETALLE_DESCUENTOS WHERE IDDESCUENTOS = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idDescuento);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new DetalleDescuento(
                       	rs.getString("IDDESCUENTOS"),
                       	rs.getString("IDPRODUCTO"),
                       	rs.getBigDecimal("COSTO")
                   	));
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listarPorDescuento: " + e.getMessage());
       	}
       	return lista;
    }

    public List<DetalleDescuento> listarPorProducto(String idProducto) {
       	List<DetalleDescuento> lista = new ArrayList<>();
       	String sql = "SELECT * FROM DETALLE_DESCUENTOS WHERE IDPRODUCTO = ?";
       	try (Connection conn = BD.ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idProducto);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new DetalleDescuento(
                       	rs.getString("IDDESCUENTOS"),
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

    public boolean eliminar(String idDescuento, String idProducto) {
       	String sql = "DELETE FROM DETALLE_DESCUENTOS WHERE IDDESCUENTOS = ? AND IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idDescuento);
           	ps.setString(2, idProducto);
           	LOGGER.info("Eliminando DetalleDescuento: " + idDescuento + "↔" + idProducto);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar DetalleDescuento: " + e.getMessage());
           	return false;
       	}
    }
}
