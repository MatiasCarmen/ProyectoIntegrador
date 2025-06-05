package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorPaises {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idpais, String descripcion) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO PAISES VALUES (?, ?)");
            ps.setString(1, idpais);
            ps.setString(2, descripcion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM PAISES");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PAISES WHERE IDPAIS = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
