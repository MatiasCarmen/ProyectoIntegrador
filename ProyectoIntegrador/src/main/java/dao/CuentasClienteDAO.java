/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.CuentaCliente;
import BD.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO de CUENTAS_CLIENTES.
 */
public class CuentasClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(CuentasClienteDAO.class.getName());

    public List<CuentaCliente> listarTodas() {
        List<CuentaCliente> list = new ArrayList<>();
        String sql = "SELECT * FROM CUENTAS_CLIENTES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CuentaCliente(
                    rs.getString("IDCUENTA"),
                    rs.getString("RUT"),
                    rs.getString("CLASE")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarTodas CUENTAS_CLIENTES: " + e.getMessage());
        }
        return list;
    }
}
