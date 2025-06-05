package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorProductos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idproducto, String tipo, String descripcion, String modalidad) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO PRODUCTOS VALUES (?, ?, ?, ?)");
            ps.setString(1, idproducto);
            ps.setString(2, tipo);
            ps.setString(3, descripcion);
            ps.setString(4, modalidad);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM PRODUCTOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PRODUCTOS WHERE IDPRODUCTO = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
