package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorRoles {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idrol, String descripcion) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ROLES VALUES (?, ?)");
            ps.setString(1, idrol);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM ROLES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ROLES WHERE IDROL = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
