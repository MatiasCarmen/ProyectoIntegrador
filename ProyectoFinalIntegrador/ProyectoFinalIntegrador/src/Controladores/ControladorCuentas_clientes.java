package Controladores;

import Entidades.Cliente;
import java.sql.*;
import Entidades.ConexionDB;
import Entidades.CuentaCliente;
import java.util.ArrayList;
public  class ControladorCuentas_clientes {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idcuenta, String rut, String clase) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO CUENTAS_CLIENTES VALUES (?, ?, ?)");
            ps.setString(1, idcuenta);
            ps.setString(2, rut);
            ps.setString(3, clase);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

 public static ArrayList<CuentaCliente> obtenerTodasLasCuentas() {
    ArrayList<CuentaCliente> lista = new ArrayList<>();
    String sql = "SELECT * FROM CUENTAS_CLIENTES";

    try (Connection conn = ConexionDB.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            CuentaCliente cuenta = new CuentaCliente();
            cuenta.setRut(rs.getString("RUT"));
            cuenta.setClase(rs.getString("CLASE"));
            cuenta.setIdCuenta(rs.getString("IDCUENTA"));
            lista.add(cuenta);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM CUENTAS_CLIENTES WHERE IDCUENTA = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public static ArrayList<CuentaCliente> buscarCuentasClientes(String rut, String nombres, String apellidoP, String apellidoM, String clase) {
    ArrayList<CuentaCliente> lista = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = new ConexionDB().conectar();
        StringBuilder sql = new StringBuilder(
            "SELECT CC.IDCUENTA, CC.RUT, CC.CLASE " +
            "FROM CUENTAS_CLIENTES CC " +
            "INNER JOIN CLIENTES C ON CC.RUT = C.RUT " +
            "WHERE 1=1"
        );

        if (rut != null && !rut.trim().isEmpty()) {
            sql.append(" AND C.RUT LIKE ?");
        }
        if (nombres != null && !nombres.trim().isEmpty()) {
            sql.append(" AND C.NOMBRES LIKE ?");
        }
        if (apellidoP != null && !apellidoP.trim().isEmpty()) {
            sql.append(" AND C.APELLIDOP LIKE ?");
        }
        if (apellidoM != null && !apellidoM.trim().isEmpty()) {
            sql.append(" AND C.APELLIDOM LIKE ?");
        }
        if (clase != null && !clase.trim().isEmpty()) {
            sql.append(" AND CC.CLASE LIKE ?");
        }

        stmt = conn.prepareStatement(sql.toString());

        int index = 1;
        if (rut != null && !rut.trim().isEmpty()) {
            stmt.setString(index++, "%" + rut + "%");
        }
        if (nombres != null && !nombres.trim().isEmpty()) {
            stmt.setString(index++, "%" + nombres + "%");
        }
        if (apellidoP != null && !apellidoP.trim().isEmpty()) {
            stmt.setString(index++, "%" + apellidoP + "%");
        }
        if (apellidoM != null && !apellidoM.trim().isEmpty()) {
            stmt.setString(index++, "%" + apellidoM + "%");
        }
        if (clase != null && !clase.trim().isEmpty()) {
            stmt.setString(index++, "%" + clase + "%");
        }

        rs = stmt.executeQuery();
        while (rs.next()) {
            CuentaCliente cuenta = new CuentaCliente();
            cuenta.setIdCuenta(rs.getString("IDCUENTA"));
            cuenta.setRut(rs.getString("RUT"));
            cuenta.setClase(rs.getString("CLASE"));
            lista.add(cuenta);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return lista;
}
    


public static ArrayList<CuentaCliente> buscarCuentasClientesConFiltros(
        String rut, String nombres, String apellidoP, String apellidoM,
        String direccion, String clase, String comuna) {

    ArrayList<CuentaCliente> resultados = new ArrayList<>();
    ArrayList<CuentaCliente> todasCuentas = obtenerTodasLasCuentas();

    rut = rut.toLowerCase();
    nombres = nombres.toLowerCase();
    apellidoP = apellidoP.toLowerCase();
    apellidoM = apellidoM.toLowerCase();
    direccion = direccion.toLowerCase();
    clase = clase.toLowerCase();
    comuna = comuna.toLowerCase();

    for (CuentaCliente cuenta : todasCuentas) {
        Cliente cliente = ClienteControlador.obtenerClientePorRut(cuenta.getRut());
        if (cliente == null) continue;

        boolean coincide = true;

        if (!rut.isEmpty() && !cuenta.getRut().toLowerCase().contains(rut)) {
            coincide = false;
        }
        if (!nombres.isEmpty() && !cliente.getNombres().toLowerCase().contains(nombres)) {
            coincide = false;
        }
        if (!apellidoP.isEmpty() && !cliente.getApellidoP().toLowerCase().contains(apellidoP)) {
            coincide = false;
        }
        if (!apellidoM.isEmpty() && !cliente.getApellidoM().toLowerCase().contains(apellidoM)) {
            coincide = false;
        }
        if (!direccion.isEmpty() && !cliente.getDireccion().toLowerCase().contains(direccion)) {
            coincide = false;
        }
      
        if (!clase.isEmpty() && !cuenta.getClase().toLowerCase().equals(clase)) {
            coincide = false;
        }
       
        if (!comuna.isEmpty()) {
            String descComuna = ComunasControlador.obtenerDescripcionPorId(cliente.getIdComuna());
            if (descComuna == null || !descComuna.toLowerCase().equals(comuna)) {
                coincide = false;
            }
        }

        if (coincide) {
            resultados.add(cuenta);
        }
    }

    return resultados;
}

public static String obtenerRutPorIdCuenta(String idCuenta) {
    String rut = null;
    String sql = "SELECT RUT FROM CUENTAS_CLIENTES WHERE IDCUENTA = ?";

    try (Connection conn = ConexionDB.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, idCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                rut = rs.getString("RUT");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return rut;
}


}
