/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

/**
 *
 * @author Thiago
 */
import Entidades.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entidades.ConexionDB;


public class ClienteControlador {

    public boolean crearCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTES (RUT, CORREO, NOMBRES, APELLIDOP, APELLIDOM, TELEFONO, EDAD, DIRECCION, IDCOMUNA) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getRut());
            ps.setString(2, cliente.getCorreo());
            ps.setString(3, cliente.getNombres());
            ps.setString(4, cliente.getApellidoP());
            ps.setString(5, cliente.getApellidoM());
            ps.setLong(6, cliente.getTelefono());
            ps.setInt(7, cliente.getEdad());
            ps.setString(8, cliente.getDireccion());
            ps.setString(9, cliente.getIdComuna());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Cliente obtenerClientePorRut(String rut) {
        String sql = "SELECT * FROM CLIENTES WHERE RUT = ?";
        Cliente cliente = null;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rut);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setRut(rs.getString("RUT"));
                cliente.setCorreo(rs.getString("CORREO"));
                cliente.setNombres(rs.getString("NOMBRES"));
                cliente.setApellidoP(rs.getString("APELLIDOP"));
                cliente.setApellidoM(rs.getString("APELLIDOM"));
                cliente.setTelefono(rs.getLong("TELEFONO"));
                cliente.setEdad(rs.getByte("EDAD"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setIdComuna(rs.getString("IDCOMUNA"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE CLIENTES SET CORREO = ?, NOMBRES = ?, APELLIDOP = ?, APELLIDOM = ?, TELEFONO = ?, EDAD = ?, DIRECCION = ?, IDCOMUNA = ? WHERE RUT = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getCorreo());
            ps.setString(2, cliente.getNombres());
            ps.setString(3, cliente.getApellidoP());
            ps.setString(4, cliente.getApellidoM());
            ps.setLong(5, cliente.getTelefono());
            ps.setInt(6, cliente.getEdad());
            ps.setString(7, cliente.getDireccion());
            ps.setString(8, cliente.getIdComuna());
            ps.setString(9, cliente.getRut());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(String rut) {
        String sql = "DELETE FROM CLIENTES WHERE RUT = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rut);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setRut(rs.getString("RUT"));
                cliente.setCorreo(rs.getString("CORREO"));
                cliente.setNombres(rs.getString("NOMBRES"));
                cliente.setApellidoP(rs.getString("APELLIDOP"));
                cliente.setApellidoM(rs.getString("APELLIDOM"));
                cliente.setTelefono(rs.getLong("TELEFONO"));
                cliente.setEdad(rs.getByte("EDAD"));
                cliente.setDireccion(rs.getString("DIRECCION"));
                cliente.setIdComuna(rs.getString("IDCOMUNA"));

                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
     public ArrayList<Cliente> buscarClientesFiltrados(String nombre, String comuna, Integer edad) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM CLIENTES WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND NOMBRES LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (comuna != null && !comuna.equals("Todos")) {
            sql.append(" AND IDCOMUNA = ?");
            parametros.add(comuna);
        }
        if (edad != null) {
            sql.append(" AND EDAD = ?");
            parametros.add(edad);
        }

        try (Connection conn = new ConexionDB().conectar();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setRut(rs.getString("RUT"));
                c.setNombres(rs.getString("NOMBRES"));
                c.setApellidoP(rs.getString("APELLIDOP"));
                c.setApellidoM(rs.getString("APELLIDOM"));
                c.setEdad(rs.getByte("EDAD"));
                c.setTelefono(rs.getInt("TELEFONO"));
                c.setCorreo(rs.getString("CORREO"));
                c.setDireccion(rs.getString("DIRECCION"));
                c.setIdComuna(rs.getString("IDCOMUNA"));
                clientes.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
}

