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
import entidades.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – ProductoDAO: maneja CRUD de PRODUCTOS.
 */
public class ProductoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    public boolean crear(Producto p) {
        String sql = "INSERT INTO PRODUCTOS (IDPRODUCTO, TIPO, DESCRIPCION, MODALIDAD) VALUES (?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getIdProducto());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getModalidad());
           	LOGGER.info("Insertando Producto: " + p.getIdProducto());
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error crear Producto: " + e.getMessage());
           	return false;
        }
    }

    public Producto obtenerPorId(String id) {
       	String sql = "SELECT * FROM PRODUCTOS WHERE IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, id);
           	try (ResultSet rs = ps.executeQuery()) {
               	if (rs.next()) {
                   	return new Producto(
                       	rs.getString("IDPRODUCTO"),
                       	rs.getString("TIPO"),
                       	rs.getString("DESCRIPCION"),
                       	rs.getString("MODALIDAD")
                   	);
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error obtener Producto: " + e.getMessage());
       	}
       	return null;
    }

    public List<Producto> listarTodos() {
       	List<Producto> lista = new ArrayList<>();
       	String sql = "SELECT * FROM PRODUCTOS";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql);
            	ResultSet rs = ps.executeQuery()) {
           	while (rs.next()) {
               	lista.add(new Producto(
                   	rs.getString("IDPRODUCTO"),
                   	rs.getString("TIPO"),
                   	rs.getString("DESCRIPCION"),
                   	rs.getString("MODALIDAD")
               	));
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listar Productos: " + e.getMessage());
       	}
       	return lista;
    }

    public boolean actualizar(Producto p) {
       	String sql = "UPDATE PRODUCTOS SET TIPO=?, DESCRIPCION=?, MODALIDAD=? WHERE IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, p.getTipo());
           	ps.setString(2, p.getDescripcion());
           	ps.setString(3, p.getModalidad());
           	ps.setString(4, p.getIdProducto());
           	LOGGER.info("Actualizando Producto: " + p.getIdProducto());
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error actualizar Producto: " + e.getMessage());
           	return false;
       	}
    }

    public boolean eliminar(String id) {
       	String sql = "DELETE FROM PRODUCTOS WHERE IDPRODUCTO = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, id);
           	LOGGER.info("Eliminando Producto: " + id);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar Producto: " + e.getMessage());
           	return false;
       	}
    }
}
