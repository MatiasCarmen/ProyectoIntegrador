package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorMesa_central {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idactividad, String telefono, String lugar) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO MESA_CENTRAL VALUES (?, ?, ?)");
            ps.setString(1, idactividad);
            ps.setString(2, telefono);
            ps.setString(3, lugar);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM MESA_CENTRAL");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM MESA_CENTRAL WHERE IDACTIVIDAD = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
