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
import entidades.RolFuncion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – RolFuncionDAO: maneja relación N:M ROLES_FUNCIONES.
 */
public class RolFuncionDAO {
    private static final Logger LOGGER = Logger.getLogger(RolFuncionDAO.class.getName());

    public boolean crear(RolFuncion rf) {
        String sql = "INSERT INTO ROLES_FUNCIONES (IDROL, IDFUNCION) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rf.getIdRol());
            ps.setString(2, rf.getIdFuncion());
           	LOGGER.info("Insertando RolFuncion: rol=" + rf.getIdRol() + " func=" + rf.getIdFuncion());
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error crear RolFuncion: " + e.getMessage());
           	return false;
       	}
    }

    public List<RolFuncion> listarPorRol(String idRol) {
       	List<RolFuncion> lista = new ArrayList<>();
       	String sql = "SELECT * FROM ROLES_FUNCIONES WHERE IDROL = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idRol);
           	try (ResultSet rs = ps.executeQuery()) {
               	while (rs.next()) {
                   	lista.add(new RolFuncion(
                       	rs.getString("IDROL"),
                       	rs.getString("IDFUNCION")
                   	));
               	}
           	}
       	} catch (SQLException e) {
           	LOGGER.severe("Error listarPorRol: " + e.getMessage());
       	}
       	return lista;
    }

    public boolean eliminar(String idRol, String idFuncion) {
       	String sql = "DELETE FROM ROLES_FUNCIONES WHERE IDROL = ? AND IDFUNCION = ?";
       	try (Connection conn = ConexionBD.conectar();
            	PreparedStatement ps = conn.prepareStatement(sql)) {
           	ps.setString(1, idRol);
           	ps.setString(2, idFuncion);
           	LOGGER.info("Eliminando RolFuncion: rol=" + idRol + " func=" + idFuncion);
           	return ps.executeUpdate() > 0;
       	} catch (SQLException e) {
           	LOGGER.severe("Error eliminar RolFuncion: " + e.getMessage());
           	return false;
       	}
    }
}
