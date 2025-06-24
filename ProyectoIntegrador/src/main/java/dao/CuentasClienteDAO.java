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
}
