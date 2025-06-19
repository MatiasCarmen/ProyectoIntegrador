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
import entidades.MesaCentral;

import java.sql.*;
import java.util.logging.Logger;

/**
 * DAO – MesaCentralDAO: maneja CRUD de MESA_CENTRAL.
 */
public class MesaCentralDAO {
    private static final Logger LOGGER = Logger.getLogger(MesaCentralDAO.class.getName());

    public boolean crear(MesaCentral m) {
        String sql = "INSERT INTO MESA_CENTRAL (IDACTIVIDAD, TELEFONO, LUGAR) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getIdActividad());
            ps.setLong(2, m.getTelefono());
            ps.setString(3, m.getLugar());
            LOGGER.info("Insertando MesaCentral: actividad=" + m.getIdActividad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear MesaCentral: " + e.getMessage());
            return false;
        }
    }

    public MesaCentral obtenerPorActividad(String idActividad) {
        String sql = "SELECT * FROM MESA_CENTRAL WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MesaCentral(
                        rs.getString("IDACTIVIDAD"),
                        rs.getLong("TELEFONO"),
                        rs.getString("LUGAR")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener MesaCentral: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(MesaCentral m) {
        String sql = "UPDATE MESA_CENTRAL SET TELEFONO = ?, LUGAR = ? WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, m.getTelefono());
            ps.setString(2, m.getLugar());
            ps.setString(3, m.getIdActividad());
            LOGGER.info("Actualizando MesaCentral: actividad=" + m.getIdActividad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar MesaCentral: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String idActividad) {
        String sql = "DELETE FROM MESA_CENTRAL WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
           	LOGGER.info("Eliminando MesaCentral: actividad=" + idActividad);
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error eliminar MesaCentral: " + e.getMessage());
           	return false;
        }
    }
}
