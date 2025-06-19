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
import entidades.Funcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – FuncionDAO: maneja CRUD de FUNCIONES.
 */
public class FuncionDAO {
    private static final Logger LOGGER = Logger.getLogger(FuncionDAO.class.getName());

    public boolean crear(Funcion f) {
        String sql = "INSERT INTO FUNCIONES (IDFUNCION, DESCRIPCION) VALUES (?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getIdFuncion());
            ps.setString(2, f.getDescripcion());
            LOGGER.info("Insertando Funcion: " + f.getIdFuncion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Funcion: " + e.getMessage());
            return false;
        }
    }

    public Funcion obtenerPorId(String id) {
        String sql = "SELECT * FROM FUNCIONES WHERE IDFUNCION = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Funcion(
                        rs.getString("IDFUNCION"),
                        rs.getString("DESCRIPCION")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Funcion: " + e.getMessage());
        }
        return null;
    }

    public List<Funcion> listarTodos() {
        List<Funcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM FUNCIONES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Funcion(
                    rs.getString("IDFUNCION"),
                    rs.getString("DESCRIPCION")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Funciones: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Funcion f) {
        String sql = "UPDATE FUNCIONES SET DESCRIPCION = ? WHERE IDFUNCION = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getDescripcion());
            ps.setString(2, f.getIdFuncion());
            LOGGER.info("Actualizando Funcion: " + f.getIdFuncion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Funcion: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM FUNCIONES WHERE IDFUNCION = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
           	LOGGER.info("Eliminando Funcion: " + id);
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error eliminar Funcion: " + e.getMessage());
           	return false;
        }
    }
}
