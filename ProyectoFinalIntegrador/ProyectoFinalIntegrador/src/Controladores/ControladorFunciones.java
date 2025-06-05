package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorFunciones {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idfuncion, String descripcion) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO FUNCIONES VALUES (?, ?)");
            ps.setString(1, idfuncion);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM FUNCIONES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM FUNCIONES WHERE IDFUNCION = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
