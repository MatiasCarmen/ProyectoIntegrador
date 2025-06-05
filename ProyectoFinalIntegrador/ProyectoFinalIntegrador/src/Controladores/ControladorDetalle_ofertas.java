package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorDetalle_ofertas {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idofertas, String idproducto, String costo) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO DETALLE_OFERTAS VALUES (?, ?, ?)");
            ps.setString(1, idofertas);
            ps.setString(2, idproducto);
            ps.setString(3, costo);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM DETALLE_OFERTAS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM DETALLE_OFERTAS WHERE IDOFERTAS = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
