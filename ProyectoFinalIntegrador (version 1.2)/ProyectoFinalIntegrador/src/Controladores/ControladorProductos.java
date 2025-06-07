package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
import Entidades.Producto;
import java.util.ArrayList;
import java.util.List;
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
    public Producto obtenerProductoPorId(String idProducto) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRODUCTOS WHERE IDPRODUCTO = ?");
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Producto(
                    rs.getString("IDPRODUCTO"),
                    rs.getString("TIPO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("MODALIDAD")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

   
    public ArrayList<Producto> obtenerProductosPorNombre(String nombre) {
        ArrayList<Producto> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRODUCTOS WHERE DESCRIPCION = ?");
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto prod = new Producto(
                    rs.getString("IDPRODUCTO"),
                    rs.getString("TIPO"),
                    rs.getString("DESCRIPCION"),
                    rs.getString("MODALIDAD")
                );
                lista.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static List<String> obtenerProductosPorTipoDesdePlan(String idCuenta) {
    String sql = "SELECT P.TIPO || '|' || P.DESCRIPCION FROM PRODUCTOS P " +
                 "JOIN DETALLE_PLANES DP ON P.IDPRODUCTO = DP.IDPRODUCTO " +
                 "JOIN PRODUCTOS_INSTALADOS PI ON PI.IDPLAN = DP.IDPLAN " +
                 "WHERE PI.IDCUENTA = ?";
    return obtenerLista(sql, idCuenta);
}

public  static List<String> obtenerProductosPorTipoDesdeAdicional(String idCuenta) {
    String sql = "SELECT P.TIPO || '|' || P.DESCRIPCION FROM PRODUCTOS P " +
                 "JOIN DETALLE_ADICIONALES DA ON P.IDPRODUCTO = DA.IDPRODUCTO " +
                 "JOIN PRODUCTOS_INSTALADOS PI ON PI.IDADICIONALES = DA.IDADICIONALES " +
                 "WHERE PI.IDCUENTA = ?";
    return obtenerLista(sql, idCuenta);
}

public  static List<String> obtenerProductosPorTipoDesdeDescuento(String idCuenta) {
    String sql = "SELECT P.TIPO || '|' || P.DESCRIPCION FROM PRODUCTOS P " +
                 "JOIN DETALLE_DESCUENTOS DD ON P.IDPRODUCTO = DD.IDPRODUCTO " +
                 "JOIN PRODUCTOS_INSTALADOS PI ON PI.IDDESCUENTOS = DD.IDDESCUENTOS " +
                 "WHERE PI.IDCUENTA = ?";
    return obtenerLista(sql, idCuenta);
}

private static List<String> obtenerLista(String sql, String idCuenta) {
    List<String> lista = new ArrayList<>();
    try (Connection conn = new ConexionDB().conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, idCuenta);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(rs.getString(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

}
