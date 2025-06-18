/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import BD.ConexionBD;            // o el paquete donde esté tu clase de conexión
import entidades.Cliente;       // tu POJO Cliente
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO de CLIENTES: encapsula todo el acceso JDBC a la tabla CLIENTES,
 * incluyendo búsquedas avanzadas con JOIN a CUENTAS_CLIENTES.
 */
public class ClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    public boolean crearCliente(Cliente c) {
        String sql = "INSERT INTO CLIENTES (RUT, CORREO, NOMBRES, APELLIDOP, APELLIDOM, TELEFONO, EDAD, DIRECCION, IDCOMUNA) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getRut());
            ps.setString(2, c.getCorreo());
            ps.setString(3, c.getNombres());
            ps.setString(4, c.getApellidoP());
            ps.setString(5, c.getApellidoM());
            ps.setLong(6, c.getTelefono());
            ps.setByte(7, c.getEdad());
            ps.setString(8, c.getDireccion());
            ps.setString(9, c.getIdComuna());
            int r = ps.executeUpdate();
            LOGGER.info("Filas insertadas CLIENTES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crearCliente: " + e.getMessage());
            return false;
        }
    }

    public Cliente obtenerClientePorRut(String rut) {
        String sql = "SELECT * FROM CLIENTES WHERE RUT = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rut);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setRut(rs.getString("RUT"));
                    c.setCorreo(rs.getString("CORREO"));
                    c.setNombres(rs.getString("NOMBRES"));
                    c.setApellidoP(rs.getString("APELLIDOP"));
                    c.setApellidoM(rs.getString("APELLIDOM"));
                    c.setTelefono(rs.getLong("TELEFONO"));
                    c.setEdad(rs.getByte("EDAD"));
                    c.setDireccion(rs.getString("DIRECCION"));
                    c.setIdComuna(rs.getString("IDCOMUNA"));
                    return c;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerClientePorRut: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE CLIENTES SET CORREO=?, NOMBRES=?, APELLIDOP=?, APELLIDOM=?, TELEFONO=?, EDAD=?, DIRECCION=?, IDCOMUNA=? WHERE RUT=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCorreo());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getApellidoP());
            ps.setString(4, c.getApellidoM());
            ps.setLong(5, c.getTelefono());
            ps.setByte(6, c.getEdad());
            ps.setString(7, c.getDireccion());
            ps.setString(8, c.getIdComuna());
            ps.setString(9, c.getRut());
            int r = ps.executeUpdate();
            LOGGER.info("Filas actualizadas CLIENTES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizarCliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(String rut) {
        String sql = "DELETE FROM CLIENTES WHERE RUT = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rut);
            int r = ps.executeUpdate();
            LOGGER.info("Filas eliminadas CLIENTES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminarCliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setRut(rs.getString("RUT"));
                c.setCorreo(rs.getString("CORREO"));
                c.setNombres(rs.getString("NOMBRES"));
                c.setApellidoP(rs.getString("APELLIDOP"));
                c.setApellidoM(rs.getString("APELLIDOM"));
                c.setTelefono(rs.getLong("TELEFONO"));
                c.setEdad(rs.getByte("EDAD"));
                c.setDireccion(rs.getString("DIRECCION"));
                c.setIdComuna(rs.getString("IDCOMUNA"));
                lista.add(c);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarClientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Búsqueda avanzada con JOIN a CUENTAS_CLIENTES.
     * Devuelve filas {NombreCompleto, RUT, TipoCuenta, IDCuenta}.
     */
    public List<Object[]> buscarClientesAvanzado(
            String rut,
            String nombre,
            String apellidoP,
            String apellidoM,
            String direccion,
            String tipoCuenta,
            String comuna) {

        List<Object[]> resultados = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT CONCAT(c.NOMBRES,' ',c.APELLIDOP,' ',c.APELLIDOM) AS Nombre, " +
            "c.RUT, cc.CLASE AS TipoCuenta, cc.IDCUENTA AS IDCuenta " +
            "FROM CLIENTES c " +
            "LEFT JOIN CUENTAS_CLIENTES cc ON c.RUT = cc.RUT " +
            "WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (!rut.isBlank()) {
            sql.append(" AND c.RUT = ?");
            params.add(rut);
        }
        if (!nombre.isBlank()) {
            sql.append(" AND c.NOMBRES LIKE ?");
            params.add("%" + nombre + "%");
        }
        if (!apellidoP.isBlank()) {
            sql.append(" AND c.APELLIDOP LIKE ?");
            params.add("%" + apellidoP + "%");
        }
        if (!apellidoM.isBlank()) {
            sql.append(" AND c.APELLIDOM LIKE ?");
            params.add("%" + apellidoM + "%");
        }
        if (!direccion.isBlank()) {
            sql.append(" AND c.DIRECCION LIKE ?");
            params.add("%" + direccion + "%");
        }
        if (!"Todos".equals(tipoCuenta)) {
            sql.append(" AND cc.CLASE = ?");
            params.add(tipoCuenta);
        }
        if (!"Todos".equals(comuna)) {
            sql.append(" AND c.IDCOMUNA = ?");
            params.add(comuna);
        }

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Object[]{
                        rs.getString("Nombre"),
                        rs.getString("RUT"),
                        rs.getString("TipoCuenta"),
                        rs.getString("IDCuenta")
                    });
                }
            }
            LOGGER.info("Clientes encontrados: " + resultados.size());
        } catch (SQLException e) {
            LOGGER.severe("Error en buscarClientesAvanzado: " + e.getMessage());
        }

        return resultados;
    }
}
