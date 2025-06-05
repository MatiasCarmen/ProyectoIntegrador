/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

/**
 *
 * @author Thiago
 */


import Entidades.Comuna;
import Entidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComunasControlador {

    public boolean insertarComuna(String idComuna, String descripcion) {
        String sql = "INSERT INTO COMUNAS (IDCOMUNA, DESCRIPCION) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idComuna);
            stmt.setString(2, descripcion);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarComuna(String idComuna, String descripcion) {
        String sql = "UPDATE COMUNAS SET DESCRIPCION = ? WHERE IDCOMUNA = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            stmt.setString(2, idComuna);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarComuna(String idComuna) {
        String sql = "DELETE FROM COMUNAS WHERE IDCOMUNA = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idComuna);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Comuna> listarComunas() {
        String sql = "SELECT * FROM COMUNAS";
        ArrayList<Comuna> comunas = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comunas.add(new Comuna(rs.getString("IDCOMUNA"),
                                  rs.getString("DESCRIPCION")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comunas;
    }
    
    public static String obtenerIdPorDescripcion(String descripcion) {
        String sql = "SELECT IDCOMUNA FROM COMUNAS WHERE DESCRIPCION = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("IDCOMUNA");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

   
    public static String obtenerDescripcionPorId(String idComuna) {
        String sql = "SELECT DESCRIPCION FROM COMUNAS WHERE IDCOMUNA = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idComuna);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("DESCRIPCION");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
