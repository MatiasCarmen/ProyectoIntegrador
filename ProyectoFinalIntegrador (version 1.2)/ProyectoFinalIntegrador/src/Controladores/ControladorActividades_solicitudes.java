package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorActividades_solicitudes {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idactividad, String idsolicitud) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ACTIVIDADES_SOLICITUDES VALUES (?, ?)");
            ps.setString(1, idactividad);
            ps.setString(2, idsolicitud);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM ACTIVIDADES_SOLICITUDES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ACTIVIDADES_SOLICITUDES WHERE IDACTIVIDAD = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
