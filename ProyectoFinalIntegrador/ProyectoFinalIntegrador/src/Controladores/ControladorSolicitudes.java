package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorSolicitudes {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idsolicitud, String idcuenta, String tipo, String estado, String area, String subarea, String tipo_requerimiento, String fecha_creacion, String fecha_cierre, String comentarios) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO SOLICITUDES VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, idsolicitud);
            ps.setString(2, idcuenta);
            ps.setString(3, tipo);
            ps.setString(4, estado);
            ps.setString(5, area);
            ps.setString(6, subarea);
            ps.setString(7, tipo_requerimiento);
            ps.setString(8, fecha_creacion);
            ps.setString(9, fecha_cierre);
            ps.setString(10, comentarios);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM SOLICITUDES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM SOLICITUDES WHERE IDSOLICITUD = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
