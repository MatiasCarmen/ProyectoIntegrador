package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
import Entidades.Descuento;
public class ControladorDescuentos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String iddescuentos, String costot) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO DESCUENTOS VALUES (?, ?)");
            ps.setString(1, iddescuentos);
            ps.setString(2, costot);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM DESCUENTOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM DESCUENTOS WHERE IDDESCUENTOS = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    
    public Descuento obtenerDescuentoPorIdCuenta(String idCuenta) {
    Descuento descuento = null;
    try {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT d.* FROM PRODUCTOS_INSTALADOS pi " +
            "JOIN DESCUENTOS d ON pi.IDDESCUENTOS = d.IDDESCUENTOS " +
            "WHERE pi.IDCUENTA = ?");
        ps.setString(1, idCuenta);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            descuento = new Descuento(
                rs.getString("IDDESCUENTOS"),
                rs.getDouble("COSTOT")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return descuento;
}
}
