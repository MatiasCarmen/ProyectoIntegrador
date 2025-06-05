package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorDetalle_pedidos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idpedido, String idadicionales, String idplan, String iddescuentos) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO DETALLE_PEDIDOS VALUES (?, ?, ?, ?)");
            ps.setString(1, idpedido);
            ps.setString(2, idadicionales);
            ps.setString(3, idplan);
            ps.setString(4, iddescuentos);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM DETALLE_PEDIDOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM DETALLE_PEDIDOS WHERE IDPEDIDO = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
