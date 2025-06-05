package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorPlanes {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idplan, String nombre, String costot) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO PLANES VALUES (?, ?, ?)");
            ps.setString(1, idplan);
            ps.setString(2, nombre);
            ps.setString(3, costot);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM PLANES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PLANES WHERE IDPLAN = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
      public String obtenerNombrePorId(String idplan) {
        String nombre = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT NOMBRE FROM PLANES WHERE IDPLAN = ?");
            ps.setString(1, idplan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("NOMBRE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

   
    public String obtenerIdPorNombre(String nombrePlan) {
        String id = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT IDPLAN FROM PLANES WHERE NOMBRE = ?");
            ps.setString(1, nombrePlan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("IDPLAN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
