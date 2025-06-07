package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorActividades_pedidos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idactividad, String idpedido, String categoria) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ACTIVIDADES_PEDIDOS VALUES (?, ?, ?)");
            ps.setString(1, idactividad);
            ps.setString(2, idpedido);
            ps.setString(3, categoria);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM ACTIVIDADES_PEDIDOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ACTIVIDADES_PEDIDOS WHERE IDACTIVIDAD = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
