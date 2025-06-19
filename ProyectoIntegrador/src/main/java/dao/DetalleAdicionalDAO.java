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
import entidades.DetalleAdicional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetalleAdicionalDAO: maneja CRUD de DETALLE_ADICIONALES.
 */
public class DetalleAdicionalDAO {
    private static final Logger LOGGER = Logger.getLogger(DetalleAdicionalDAO.class.getName());

    public boolean crear(DetalleAdicional da) {
        String sql = "INSERT INTO DETALLE_ADICIONALES (IDADICIONALES, IDPRODUCTO, COSTO) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, da.getIdAdicionales());
           	ps.setString(2, da.getIdProducto());
           	ps.setBigDecimal(3, da.getCosto());
           	LOGGER.info("Insertando DetalleAdicional: " + da.getIdAdicionales() + "↔" + da.getIdProducto());
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error crear DetalleAdicional: " + e.getMessage());
           	return false;
        }
    }

    public List<DetalleAdicional> listarPorAdicional(String idAdicional) {
       	List<DetalleAdicional> lista = new ArrayList<>();
       	String sql = "SELECT * FROM DETALLE_ADICIONALES WHERE IDADICIONALES = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idAdicional);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new DetalleAdicional(
                       	rs.getString("IDADICIONALES"),
                       	rs.getString("IDPRODUCTO"),
                       	rs.getBigDecimal("COSTO")
                   	));
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listarPorAdicional: " + e.getMessage());
       	}
       	return lista;
    }

    public List<DetalleAdicional> listarPorProducto(String idProducto) {
       	List<DetalleAdicional> lista = new ArrayList<>();
       	String sql = "SELECT * FROM DETALLE_ADICIONALES WHERE IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idProducto);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new DetalleAdicional(
                       	rs.getString("IDADICIONALES"),
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

    public boolean eliminar(String idAdicional, String idProducto) {
       	String sql = "DELETE FROM DETALLE_ADICIONALES WHERE IDADICIONALES = ? AND IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idAdicional);
           	ps.setString(2, idProducto);
           	LOGGER.info("Eliminando DetalleAdicional: " + idAdicional + "↔" + idProducto);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar DetalleAdicional: " + e.getMessage());
           	return false;
       	}
    }
}
