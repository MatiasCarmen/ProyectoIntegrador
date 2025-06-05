package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
import Entidades.ProductoInstalado;
import java.util.ArrayList;
public class ControladorProductos_instalados {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idcuenta, String idadicionales, String idplan, String iddescuentos) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO PRODUCTOS_INSTALADOS VALUES (?, ?, ?, ?)");
            ps.setString(1, idcuenta);
            ps.setString(2, idadicionales);
            ps.setString(3, idplan);
            ps.setString(4, iddescuentos);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM PRODUCTOS_INSTALADOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PRODUCTOS_INSTALADOS WHERE IDCUENTA = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public ArrayList<ProductoInstalado> buscarPorIdCuenta(String idCuenta) {
    ArrayList<ProductoInstalado> lista = new ArrayList<>();
    try {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRODUCTOS_INSTALADOS WHERE IDCUENTA = ?");
        ps.setString(1, idCuenta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ProductoInstalado pi = new ProductoInstalado(
                rs.getString("IDCUENTA"),
                rs.getString("IDADICIONALES"),
                rs.getString("IDPLAN"),
                rs.getString("IDDESCUENTOS")
            );
            lista.add(pi);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
    
  public String obtenerPlanPorCuenta(String idCuenta) {
        String nombrePlan = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT P.NOMBRE FROM PRODUCTOS_INSTALADOS PI " +
                "JOIN PLANES P ON PI.IDPLAN = P.IDPLAN " +
                "WHERE PI.IDCUENTA = ?"
            );
            ps.setString(1, idCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombrePlan = rs.getString("NOMBRE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombrePlan;
    }  
}
