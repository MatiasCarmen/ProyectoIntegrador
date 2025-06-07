package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
import Entidades.ProductoInstalado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public ProductoInstalado buscarPorIdCuenta(String idCuenta) {
    ProductoInstalado pi=null;
    try {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRODUCTOS_INSTALADOS WHERE IDCUENTA = ?");
        ps.setString(1, idCuenta);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            pi = new ProductoInstalado(
                rs.getString("IDCUENTA"),
                rs.getString("IDADICIONALES"),
                rs.getString("IDPLAN"),
                rs.getString("IDDESCUENTOS")
            );
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pi;
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
  
  public String obtenerNombrePlanPorTipo(String idCuenta, String tipo) {
    String nombre = null;
    String query = "SELECT P.NOMBRE FROM PRODUCTOS_INSTALADOS PI " +
                   "JOIN PLANES P ON PI.IDPLAN = P.IDPLAN " +
                   "WHERE PI.IDCUENTA = ? AND P.TIPO = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, idCuenta);
        ps.setString(2, tipo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nombre = rs.getString("NOMBRE");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return nombre != null ? nombre : "(sin plan)";
}
  public   Map<String, List<String>> obtenerProductosPorTipo(String idCuenta) {
    Map<String, List<String>> productosPorTipo = new HashMap<>();

    List<String> productosPlanes = ControladorProductos.obtenerProductosPorTipoDesdePlan(idCuenta);
    List<String> productosAdicionales = ControladorProductos.obtenerProductosPorTipoDesdeAdicional(idCuenta);
    List<String> productosDescuentos = ControladorProductos.obtenerProductosPorTipoDesdeDescuento(idCuenta);

    agregarProductos(productosPorTipo, productosPlanes);
    agregarProductos(productosPorTipo, productosAdicionales);
    agregarProductos(productosPorTipo, productosDescuentos);

    return productosPorTipo;
}

private void agregarProductos(Map<String, List<String>> mapa, List<String> productosTipoDescripcion) {
    for (String linea : productosTipoDescripcion) {
        String[] partes = linea.split("\\|");
        String tipo = partes[0];
        String descripcion = partes[1];
        mapa.computeIfAbsent(tipo, k -> new ArrayList<>()).add(descripcion);
    }
}



   
}
