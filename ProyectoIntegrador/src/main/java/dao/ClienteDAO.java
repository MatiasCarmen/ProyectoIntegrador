/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import BD.ConexionBD;
import com.google.common.collect.ImmutableList;
import entidades.Cliente;
import utils.GuavaUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO de CLIENTES: encapsula todo el acceso JDBC a la tabla CLIENTES usando
 * procedimientos almacenados,
 * incluyendo búsquedas avanzadas con JOIN a CUENTAS_CLIENTES.
 */
public class ClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    /**
     * Crea un nuevo cliente usando el procedimiento almacenado insertarCliente
     * 
     * @param c El cliente a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crearCliente(Cliente c) {
        String sql = "{CALL insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, c.getRut());
            cs.setString(2, c.getCorreo());
            cs.setString(3, c.getNombres());
            cs.setString(4, c.getApellidoP());
            cs.setString(5, c.getApellidoM());
            cs.setLong(6, c.getTelefono());
            cs.setByte(7, c.getEdad());
            cs.setString(8, c.getDireccion());
            cs.setString(9, c.getIdComuna());

            int r = cs.executeUpdate();
            LOGGER.info("Filas insertadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un cliente por su RUT usando el procedimiento almacenado
     * obtenerClientePorRut
     * 
     * @param rut El RUT del cliente a buscar
     * @return El cliente encontrado o null si no existe
     */
    public Cliente obtenerClientePorRut(String rut) {
        String sql = "{CALL obtenerClientePorRut(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, rut);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener cliente: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los clientes usando el procedimiento almacenado listarClientes
     * 
     * @return Lista inmutable de todos los clientes
     */
    public ImmutableList<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "{CALL listarClientes()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar clientes: " + e.getMessage());
        }
        return GuavaUtils.toImmutableList(lista);
    }

    /**
     * Actualiza un cliente existente usando el procedimiento almacenado
     * actualizarCliente
     * 
     * @param c El cliente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarCliente(Cliente c) {
        String sql = "{CALL actualizarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, c.getCorreo());
            cs.setString(2, c.getNombres());
            cs.setString(3, c.getApellidoP());
            cs.setString(4, c.getApellidoM());
            cs.setLong(5, c.getTelefono());
            cs.setByte(6, c.getEdad());
            cs.setString(7, c.getDireccion());
            cs.setString(8, c.getIdComuna());
            cs.setString(9, c.getRut());

            int r = cs.executeUpdate();
            LOGGER.info("Filas actualizadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente por su RUT usando el procedimiento almacenado
     * eliminarCliente
     * 
     * @param rut El RUT del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCliente(String rut) {
        String sql = "{CALL eliminarCliente(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, rut);
            int r = cs.executeUpdate();
            LOGGER.info("Filas eliminadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca clientes por un criterio general usando el procedimiento almacenado
     * buscarClientesPorCriterio
     * 
     * @param criterio El criterio de búsqueda (se aplica a varios campos)
     * @return Lista de clientes que coinciden con el criterio
     */
    public List<Cliente> buscarPorCriterio(String criterio) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "{CALL buscarClientesPorCriterio(?)}";

        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, criterio);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error en búsqueda por criterio: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Realiza una búsqueda avanzada de clientes usando el procedimiento almacenado
     * buscarClientesAvanzado
     * 
     * @param rut        RUT del cliente (parcial o completo)
     * @param nombre     Nombre del cliente
     * @param apellidoP  Apellido paterno
     * @param apellidoM  Apellido materno
     * @param direccion  Dirección
     * @param tipoCuenta Tipo de cuenta asociada
     * @param comuna     Comuna
     * @return Lista de arrays con los resultados de la búsqueda
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
        String sql = "{CALL buscarClientesAvanzado(?, ?, ?, ?, ?, ?, ?)}";

        long startTime = System.currentTimeMillis();

        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {

            // Configurar parámetros
            cs.setString(1, rut);
            cs.setString(2, nombre);
            cs.setString(3, apellidoP);
            cs.setString(4, apellidoM);
            cs.setString(5, direccion);
            cs.setString(6, tipoCuenta);
            cs.setString(7, comuna);

            // Loggear información detallada
            System.out.println("\n=== Búsqueda Avanzada de Clientes ===");
            System.out.println("Procedimiento: buscarClientesAvanzado");
            System.out.println("Parámetros: " + rut + ", " + nombre + ", " + apellidoP + ", " +
                    apellidoM + ", " + direccion + ", " + tipoCuenta + ", " + comuna);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[8];
                    fila[0] = rs.getString("NOMBRES");
                    fila[1] = rs.getString("APELLIDOP");
                    fila[2] = rs.getString("APELLIDOM");
                    fila[3] = rs.getString("RUT");
                    fila[4] = rs.getString("DIRECCION");
                    fila[5] = rs.getString("COMUNA");
                    fila[6] = rs.getString("TIPO_CUENTA");
                    fila[7] = rs.getString("IDCUENTA_CLIENTE");
                    resultados.add(fila);
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución: " + (endTime - startTime) + "ms");
            System.out.println("Resultados encontrados: " + resultados.size());
            System.out.println("====================================\n");

        } catch (SQLException e) {
            System.err.println("Error en búsqueda avanzada:");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Código de error: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            LOGGER.severe("Error en búsqueda avanzada: " + e.getMessage());
        }

        return resultados;
    }

    /**
     * Cuenta el número total de clientes usando el procedimiento almacenado
     * contarClientes
     * 
     * @return El número total de clientes
     */
    public int contarClientes() {
        String sql = "{CALL contarClientes()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al contar clientes: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Método auxiliar para mapear los resultados de la consulta a un objeto Cliente
     * 
     * @param rs ResultSet con los datos del cliente
     * @return Objeto Cliente con los datos del ResultSet
     * @throws SQLException Si ocurre algún error al acceder a los datos
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
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
