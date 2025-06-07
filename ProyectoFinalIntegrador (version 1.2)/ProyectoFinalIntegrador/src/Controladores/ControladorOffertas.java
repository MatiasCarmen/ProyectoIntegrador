package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorOffertas {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idofertas, String idcuenta, String costot) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO OFFERTAS VALUES (?, ?, ?)");
            ps.setString(1, idofertas);
            ps.setString(2, idcuenta);
            ps.setString(3, costot);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM OFFERTAS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM OFFERTAS WHERE IDOFERTAS = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
