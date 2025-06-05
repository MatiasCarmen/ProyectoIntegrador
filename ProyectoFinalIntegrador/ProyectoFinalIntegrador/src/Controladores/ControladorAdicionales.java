package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorAdicionales {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idadicionales, String costot) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ADICIONALES VALUES (?, ?)");
            ps.setString(1, idadicionales);
            ps.setString(2, costot);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM ADICIONALES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ADICIONALES WHERE IDADICIONALES = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
