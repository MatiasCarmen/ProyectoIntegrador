/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controladores;

import dao.CuentasClienteDAO;
import entidades.CuentaCliente;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador de CuentasCliente:
 *  • SRP: única responsabilidad: orquestar operaciones de cuentas.
 *  • Usa DAO para separar la lógica de acceso a datos.
 */
public class CuentasClienteControlador {
    private static final Logger LOGGER = Logger.getLogger(CuentasClienteControlador.class.getName());
    private static final CuentasClienteDAO dao = new CuentasClienteDAO();

    /**
     * Inserta una nueva cuenta cliente en la BD.
     * @param idCuenta UUID de la cuenta
     * @param rut      RUT del cliente
     * @param clase    tipo de cuenta (Cliente/Servicio/Facturacion)
     * @return true si la inserción fue exitosa
     */
    public boolean insertar(String idCuenta, String rut, String clase) {
        LOGGER.info(String.format("CuentasClienteControlador: insertar(idCuenta=%s, rut=%s, clase=%s)",
                idCuenta, rut, clase));
        return dao.insertar(idCuenta, rut, clase);
    }

    /**
     * @return todas las cuentas cliente (para listados)
     */
    public List<CuentaCliente> listarTodas() {
        LOGGER.info("CuentasClienteControlador: listarTodas");
        return dao.listarTodas();
    }
    
    public static String obtenerRutPorIdCuenta(String idCuenta) {
         LOGGER.info("obtenerRutPorIdCuenta");
        return dao.obtenerRutPorIdCuenta(idCuenta);
    }
    
    public static List<String> obtenerCuentasServicioPorRut(String rut) {
        LOGGER.info("obtenerCuentasServicioPorRut");
        return dao.obtenerCuentasServicioPorRut(rut);
    }
    public static String obtenerCuentaServicioAsociada(String idFacturacion) {
         LOGGER.info("obtenerCuentaServicioAsociada");
        return dao.obtenerCuentaServicioAsociada(idFacturacion);
    }
    public static CuentaCliente obtenerCuentaClientePorIdCuenta(String idCuenta) {
        LOGGER.info("obtenerCuentaClientePorIdCuenta");
        return dao.obtenerCuentaClientePorIdCuenta(idCuenta);
    }
    
    public String obtenerIdCuentaServicioDesdeFacturacion(String idCuentaFacturacion) {
        LOGGER.info("obtenerIdCuentaServicioDesdeFacturacion");
        return dao.obtenerIdCuentaServicioDesdeFacturacion(idCuentaFacturacion);
    }
    
    public static String generarIdCuentaUnico(String clase) {
          LOGGER.info("generarIdCuentaUnico");
        return dao.generarIdCuentaUnico(clase);
    }
}
