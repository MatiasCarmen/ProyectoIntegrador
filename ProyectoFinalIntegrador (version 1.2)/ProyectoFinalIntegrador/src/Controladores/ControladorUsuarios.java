package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorUsuarios {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idusuario, String rut, String idrol, String idpais, String clave, String nombres, String apellidop, String apellidom, String area, String fecha_creacion) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO USUARIOS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, idusuario);
            ps.setString(2, rut);
            ps.setString(3, idrol);
            ps.setString(4, idpais);
            ps.setString(5, clave);
            ps.setString(6, nombres);
            ps.setString(7, apellidop);
            ps.setString(8, apellidom);
            ps.setString(9, area);
            ps.setString(10, fecha_creacion);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM USUARIOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM USUARIOS WHERE IDUSUARIO = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
