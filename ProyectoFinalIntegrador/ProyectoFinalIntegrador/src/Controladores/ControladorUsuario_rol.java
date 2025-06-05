package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorUsuario_rol {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idusuario, String idrol) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO USUARIO_ROL VALUES (?, ?)");
            ps.setString(1, idusuario);
            ps.setString(2, idrol);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM USUARIO_ROL");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM USUARIO_ROL WHERE IDUSUARIO = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
