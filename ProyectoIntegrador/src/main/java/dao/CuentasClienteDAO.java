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
 * DAO – CuentasClienteDAO: acceso a la tabla CUENTAS_CLIENTES usando
 * procedimientos almacenados.
 * SRP: solo persistencia de entidades CuentaCliente.
 * Implementa operaciones básicas de base de datos para la entidad
 * CuentaCliente.
 */
public class CuentasClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(CuentasClienteDAO.class.getName());

    /**
     * Inserta una nueva fila en CUENTAS_CLIENTES.
     * 
     * @param idCuenta UUID o identificador de la cuenta
     * @param rut      RUT del cliente
     * @param clase    tipo de cuenta
     * @return true si la inserción fue exitosa
     */
    public boolean insertar(String idCuenta, String rut, String clase) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_insertar_cuenta_cliente(?, ?, ?, ?)}")) {
            cs.setString(1, idCuenta);
            cs.setString(2, rut);
            cs.setString(3, clase);
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();
            boolean resultado = cs.getBoolean(4);

            LOGGER.info("CuentasClienteDAO: insertar → cuenta=" + idCuenta);
            return resultado;
        } catch (SQLException e) {
            LOGGER.severe("Error al insertar CuentaCliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las cuentas cliente (para listados o debug).
     * 
     * @return Lista de todas las cuentas cliente
     */
    public List<CuentaCliente> listarTodas() {
        List<CuentaCliente> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_listar_cuentas_cliente()}");
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCuentaCliente(rs));
            }
            LOGGER.info("CuentasClienteDAO: listarTodas → " + lista.size() + " filas");
        } catch (SQLException e) {
            LOGGER.severe("Error al listar CuentasCliente: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene el RUT asociado a una cuenta específica.
     * 
     * @param idCuenta ID de la cuenta
     * @return RUT asociado o null si no existe
     */
    public static String obtenerRutPorIdCuenta(String idCuenta) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_rut_por_idcuenta(?)}")) {
            cs.setString(1, idCuenta);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("RUT");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener RUT por cuenta: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene las cuentas de servicio asociadas a un RUT.
     * 
     * @param rut RUT del cliente
     * @return Lista de IDs de cuentas de servicio
     */
    public static List<String> obtenerCuentasServicioPorRut(String rut) {
        List<String> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_cuentas_servicio_por_rut(?)}")) {
            cs.setString(1, rut);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("IDCUENTA"));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener cuentas servicio por RUT: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene la cuenta de servicio asociada a una cuenta de facturación.
     * 
     * @param idFacturacion ID de la cuenta de facturación
     * @return ID de la cuenta de servicio asociada o null si no existe
     */
    public static String obtenerCuentaServicioAsociada(String idFacturacion) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_cuenta_servicio_asociada(?)}")) {
            cs.setString(1, idFacturacion);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("IDCUENTA_SERVICIO");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener cuenta servicio asociada: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene una cuenta cliente por su ID.
     * 
     * @param idCuenta ID de la cuenta
     * @return Objeto CuentaCliente o null si no existe
     */
    public static CuentaCliente obtenerCuentaClientePorIdCuenta(String idCuenta) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_cuenta_cliente_por_id(?)}")) {
            cs.setString(1, idCuenta);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearCuentaCliente(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener cuenta cliente por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene el ID de cuenta de servicio asociada a una cuenta de facturación.
     * 
     * @param idCuentaFacturacion ID de la cuenta de facturación
     * @return ID de la cuenta de servicio o null si no existe
     */
    public String obtenerIdCuentaServicioDesdeFacturacion(String idCuentaFacturacion) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_obtener_idcuenta_servicio_desde_facturacion(?)}")) {
            cs.setString(1, idCuentaFacturacion);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("IDCUENTA_SERVICIO");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al obtener ID cuenta servicio desde facturación: " + e.getMessage());
        }
        return null;
    }

    /**
     * Genera un ID de cuenta único basado en la clase.
     * 
     * @param clase Tipo de cuenta (FACTURACION, SERVICIO, CLIENTE)
     * @return ID único generado
     * @throws IllegalArgumentException si la clase no es válida
     */
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
        boolean existe;
        do {
            long numero = (long) (Math.random() * 1_000_000_000L);
            idGenerado = numero + prefijo;
            existe = verificarExistenciaIdCuenta(idGenerado);
        } while (existe);
        return idGenerado;
    }

    /**
     * Verifica si un ID de cuenta ya existe.
     * 
     * @param idCuenta ID de cuenta a verificar
     * @return true si el ID ya existe, false en caso contrario
     */
    private static boolean verificarExistenciaIdCuenta(String idCuenta) {
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall("{CALL sp_verificar_existencia_idcuenta(?, ?)}")) {
            cs.setString(1, idCuenta);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            return cs.getBoolean(2);
        } catch (SQLException e) {
            LOGGER.severe("Error al verificar existencia de ID cuenta: " + e.getMessage());
            return true; // Por seguridad, asumimos que existe en caso de error
        }
    }

    /**
     * Mapea un ResultSet a un objeto CuentaCliente.
     * 
     * @param rs ResultSet con los datos de la cuenta
     * @return Objeto CuentaCliente mapeado
     * @throws SQLException si hay error al acceder a los datos
     */
    private static CuentaCliente mapearCuentaCliente(ResultSet rs) throws SQLException {
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setIdCuenta(rs.getString("IDCUENTA"));
        cuenta.setRut(rs.getString("RUT"));
        cuenta.setClase(rs.getString("CLASE"));
        return cuenta;
    }
}
