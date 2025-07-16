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
            LOGGER.info("Filas insertadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al crear cliente: " + e.getMessage());
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
            LOGGER.severe("Error al obtener cliente: " + e.getMessage());
        }
        return null;
    }

    public ImmutableList<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES";
        try (Connection conn = ConexionBD.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

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
            LOGGER.severe("Error al listar clientes: " + e.getMessage());
        }
        return GuavaUtils.toImmutableList(lista);
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
            LOGGER.info("Filas actualizadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(String rut) {
        String sql = "DELETE FROM CLIENTES WHERE RUT=?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rut);
            int r = ps.executeUpdate();
            LOGGER.info("Filas eliminadas: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> buscarPorCriterio(String criterio) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES WHERE " +
                "RUT LIKE ? OR " +
                "NOMBRES LIKE ? OR " +
                "APELLIDOP LIKE ? OR " +
                "APELLIDOM LIKE ? OR " +
                "CORREO LIKE ?";

        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            String patron = "%" + criterio + "%";
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, patron);
            }

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            LOGGER.severe("Error en búsqueda por criterio: " + e.getMessage());
        }
        return lista;
    }

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
                "SELECT c.NOMBRES, c.APELLIDOP, c.APELLIDOM, \n" +
                        "       c.RUT, c.DIRECCION, co.DESCRIPCION as COMUNA, \n" +
                        "       COALESCE(cc.CLASE, 'Sin Cuenta') as TIPO_CUENTA, \n" +
                        "       cc.IDCUENTA as IDCUENTA_CLIENTE " +
                        "FROM CLIENTES c " +
                        "LEFT JOIN CUENTAS_CLIENTES cc ON c.RUT = cc.RUT " +
                        "LEFT JOIN COMUNAS co ON c.IDCOMUNA = co.IDCOMUNA ");
        List<String> params = new ArrayList<>();

        // Búsqueda por nombre completo
        if (nombre != null && !nombre.trim().isEmpty()) {
            sql.append(" AND (CONCAT(c.NOMBRES, ' ', c.APELLIDOP, ' ', c.APELLIDOM) LIKE ? " +
                    "OR c.NOMBRES LIKE ?)");
            params.add("%" + nombre + "%");
            params.add("%" + nombre + "%");
        }

        if (rut != null && !rut.trim().isEmpty()) {
            sql.append(" AND c.RUT LIKE ?");
            params.add("%" + rut + "%");
        }
        if (apellidoP != null && !apellidoP.trim().isEmpty()) {
            sql.append(" AND c.APELLIDOP LIKE ?");
            params.add("%" + apellidoP + "%");
        }
        if (apellidoM != null && !apellidoM.trim().isEmpty()) {
            sql.append(" AND c.APELLIDOM LIKE ?");
            params.add("%" + apellidoM + "%");
        }
        if (direccion != null && !direccion.trim().isEmpty()) {
            sql.append(" AND c.DIRECCION LIKE ?");
            params.add("%" + direccion + "%");
        }
        if (tipoCuenta != null && !tipoCuenta.equals("Todos")) {
            sql.append(" AND cc.CLASE = ?");
            params.add(tipoCuenta);
        }
        if (comuna != null && !comuna.equals("Todas")) {
            sql.append(" AND co.DESCRIPCION = ?");
            params.add(comuna);
        }

        // Agregar ordenamiento
        sql.append(" ORDER BY c.APELLIDOP, c.APELLIDOM, c.NOMBRES");

        // Agregar límite para mejor rendimiento
        sql.append(" LIMIT 1000");

        long startTime = System.currentTimeMillis();

        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Configurar parámetros
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            // Loggear información detallada
            System.out.println("\n=== Búsqueda Avanzada de Clientes ===");
            System.out.println("SQL: " + sql.toString());
            System.out.println("Total parámetros: " + params.size());
            System.out.println("Parámetros aplicados: " + params);

            try (ResultSet rs = ps.executeQuery()) {
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

    public int contarClientes() {
        String sql = "SELECT COUNT(*) FROM CLIENTES";
        try (Connection conn = ConexionBD.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al contar clientes: " + e.getMessage());
        }
        return 0;
    }
}
