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
import entidades.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – PlanDAO: maneja CRUD de PLANES.
 */
public class PlanDAO {
    private static final Logger LOGGER = Logger.getLogger(PlanDAO.class.getName());

    public boolean crear(Plan p) {
        String sql = "INSERT INTO PLANES (IDPLAN, NOMBRE, COSTOT) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getIdPlan());
            ps.setString(2, p.getNombre());
            ps.setBigDecimal(3, p.getCostoT());
           	LOGGER.info("Insertando Plan: " + p.getIdPlan());
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error crear Plan: " + e.getMessage());
           	return false;
        }
    }

    public Plan obtenerPorId(String id) {
       	String sql = "SELECT * FROM PLANES WHERE IDPLAN = ?";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, id);
           	try (ResultSet rs = ps.executeQuery()) {
               	if (rs.next()) {
                   	return new Plan(
                       	rs.getString("IDPLAN"),
                       	rs.getString("NOMBRE"),
                       	rs.getBigDecimal("COSTOT")
                   	);
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error obtener Plan: " + e.getMessage());
       	}
       	return null;
    }

    public List<Plan> listarTodos() {
       	List<Plan> lista = new ArrayList<>();
       	String sql = "SELECT * FROM PLANES";
       	try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
           	while (rs.next()) {
               	lista.add(new Plan(
                   	rs.getString("IDPLAN"),
                   	rs.getString("NOMBRE"),
                   	rs.getBigDecimal("COSTOT")
               	));
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listar Planes: " + e.getMessage());
       	}
       	return lista;
    }

    public boolean actualizar(Plan p) {
       	String sql = "UPDATE PLANES SET NOMBRE=?, COSTOT=? WHERE IDPLAN = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, p.getNombre());
           	ps.setBigDecimal(2, p.getCostoT());
           	ps.setString(3, p.getIdPlan());
           	LOGGER.info("Actualizando Plan: " + p.getIdPlan());
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error actualizar Plan: " + e.getMessage());
           	return false;
       	}
    }

    public boolean eliminar(String id) {
       	String sql = "DELETE FROM PLANES WHERE IDPLAN = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, id);
           	LOGGER.info("Eliminando Plan: " + id);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar Plan: " + e.getMessage());
           	return false;
       	}
    }
   
    public List<Plan> obtenerPlanesInstaladosPorCuenta(String idCuenta) {
    List<Plan> lista = new ArrayList<>();
    String sql = """
        SELECT p.IDPLAN, p.NOMBRE, p.COSTOT
        FROM PRODUCTOS_INSTALADOS pi
        JOIN PLANES p ON pi.IDPLAN = p.IDPLAN
        WHERE pi.IDCUENTA = ?
        """;
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Plan(
                    rs.getString("IDPLAN"),
                    rs.getString("NOMBRE"),
                    rs.getBigDecimal("COSTOT")
                ));
            }
        }
    } catch (SQLException e) {
        LOGGER.severe("Error obtener planes instalados: " + e.getMessage());
    }
    return lista;
}
 public Plan obtenerPlanPorIdCuenta(String idCuenta) {
    Plan plan = null;
        String sql = "SELECT p.* FROM PRODUCTOS_INSTALADOS pi " +
            "JOIN PLANES p ON pi.IDPLAN = p.IDPLAN " +
            "WHERE pi.IDCUENTA = ?";
    try    (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)){
        ps.setString(1, idCuenta);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            plan = new Plan(
                rs.getString("p.IDPLAN"),
                rs.getString("p.NOMBRE"),
                rs.getBigDecimal("p.COSTOT")
            );
            
           
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return plan;
}

}
