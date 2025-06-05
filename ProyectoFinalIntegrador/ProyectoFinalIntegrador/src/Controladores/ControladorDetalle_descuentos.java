 package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorDetalle_descuentos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String iddescuentos, String idproducto, String costo) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO DETALLE_DESCUENTOS VALUES (?, ?, ?)");
            ps.setString(1, iddescuentos);
            ps.setString(2, idproducto);
            ps.setString(3, costo);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM DETALLE_DESCUENTOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM DETALLE_DESCUENTOS WHERE IDDESCUENTOS = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
