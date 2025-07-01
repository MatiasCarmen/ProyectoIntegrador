/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;
/**
 *
 * @author matias papu
 */

import BD.ConexionBD;
import entidades.CuentaCliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – CuentasClienteDAO: acceso a la tabla CUENTAS_CLIENTES.
 * SRP: solo persistencia de entidades CuentaCliente.
 */
public class CuentasClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(CuentasClienteDAO.class.getName());

    /**
     * Inserta una nueva fila en CUENTAS_CLIENTES.
     * @param idCuenta UUID o identificador de la cuenta
     * @param rut      RUT del cliente
     * @param clase    tipo de cuenta
     * @return true si la inserción fue exitosa
     */
    public boolean insertar(String idCuenta, String rut, String clase) {
        String sql = "INSERT INTO CUENTAS_CLIENTES (IDCUENTA, RUT, CLASE) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCuenta);
            ps.setString(2, rut);
            ps.setString(3, clase);
            int filas = ps.executeUpdate();
            LOGGER.info("CuentasClienteDAO: insertar → filas=" + filas);
            return filas > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error insertar CuentasCliente: " + e.getMessage());
            return false;
        }
    }

    /** Lista todas las cuentas cliente (para listados o debug). */
    public List<CuentaCliente> listarTodas() {
        List<CuentaCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CUENTAS_CLIENTES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CuentaCliente c = new CuentaCliente();
                c.setIdCuenta(rs.getString("IDCUENTA"));
                c.setRut(rs.getString("RUT"));
                c.setClase(rs.getString("CLASE"));
                lista.add(c);
            }
            LOGGER.info("CuentasClienteDAO: listarTodas → " + lista.size() + " filas");
        } catch (SQLException e) {
            LOGGER.severe("Error listarTodas CuentasCliente: " + e.getMessage());
        }
        return lista;
    }
    
       
public static String obtenerRutPorIdCuenta(String idCuenta) {
    String rut = null;
    String sql = "SELECT RUT FROM CUENTAS_CLIENTES WHERE IDCUENTA = ?";

    try (Connection conn = ConexionBD.conectar();
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

public static List<String> obtenerCuentasServicioPorRut(String rut) {
    List<String> lista = new ArrayList<>();
    try {
        Connection con = new ConexionBD().conectar();
        PreparedStatement stmt = con.prepareStatement(
            "SELECT IDCUENTA FROM cuentas_clientes WHERE CLASE = 'SERVICIO' AND RUT = ?");
        stmt.setString(1, rut);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(rs.getString("IDCUENTA"));
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public static String obtenerCuentaServicioAsociada(String idFacturacion) {
    try {
        Connection con = new ConexionBD().conectar();
        PreparedStatement stmt = con.prepareStatement(
            "SELECT IDCUENTA_SERVICIO FROM cuentas_clientes WHERE IDCUENTA = ? AND CLASE = 'FACTURACION'");
        stmt.setString(1, idFacturacion);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("IDCUENTA_SERVICIO");
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public static CuentaCliente obtenerCuentaClientePorIdCuenta(String idCuenta) {
    CuentaCliente cuenta = null;
    String sql = "SELECT * FROM CUENTAS_CLIENTES WHERE IDCUENTA = ?";
    
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
         
        ps.setString(1, idCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cuenta = new CuentaCliente();
                cuenta.setIdCuenta(rs.getString("IDCUENTA"));
                cuenta.setRut(rs.getString("RUT"));
                cuenta.setClase(rs.getString("CLASE"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return cuenta;
}

public String obtenerIdCuentaServicioDesdeFacturacion(String idCuentaFacturacion) {
    String idCuentaServicio = null;
    String sql = "SELECT IDCUENTA_SERVICIO FROM CUENTA_SERVICIO_FACTURACION WHERE IDCUENTA_FACTURACION = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, idCuentaFacturacion);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idCuentaServicio = rs.getString("IDCUENTA_SERVICIO");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return idCuentaServicio;
}

public static String generarIdCuentaUnico(String clase) {
    String prefijo = "";
    if ("FACTURACION".equalsIgnoreCase(clase)) {
        prefijo = "-FAC";
    } else if ("SERVICIO".equalsIgnoreCase(clase)) {
        prefijo = "-SERV";
    } else if ("CLIENTE".equalsIgnoreCase(clase)) {
        prefijo = "-CLI";
    } else {
        throw new IllegalArgumentException("Clase no válida");
    }

    String idGenerado;
    boolean existe = true;
    do {
        long numero = (long) (Math.random() * 1_000_000_000L);
        idGenerado = numero+prefijo;
        existe = verificarExistenciaIdCuenta(idGenerado);
    } while (existe);
    return idGenerado;
}

private static boolean verificarExistenciaIdCuenta(String idCuenta) {
    String sql = "SELECT 1 FROM CUENTAS_CLIENTES WHERE IDCUENTA = ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idCuenta);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return true;
}


}
