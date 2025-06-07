/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.ConexionDB;
import java.sql.*;

/**
 *
 * @author Thiago
 */

public class ActividadesControlador {

    public boolean insertar(String idactividad, String descripcion) {
        String sql = "INSERT INTO ACTIVIDADES (IDACTIVIDAD, DESCRIPCION) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idactividad);
            stmt.setString(2, descripcion);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(String idactividad, String descripcion) {
        String sql = "UPDATE ACTIVIDADES SET DESCRIPCION = ? WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, descripcion);
            stmt.setString(2, idactividad);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM ACTIVIDADES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void listar() {
        String sql = "SELECT * FROM ACTIVIDADES";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("IDACTIVIDAD: " + rs.getString("IDACTIVIDAD"));
                System.out.println("DESCRIPCION: " + rs.getString("DESCRIPCION"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

