package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorRol_funcion {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idrol, String idfuncion) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ROL_FUNCION VALUES (?, ?)");
            ps.setString(1, idrol);
            ps.setString(2, idfuncion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM ROL_FUNCION");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ROL_FUNCION WHERE IDROL = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
