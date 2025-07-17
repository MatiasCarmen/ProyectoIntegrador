/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.ClienteDAO;
import dao.ComunaDAO;
import dao.CuentasClienteDAO;
import entidades.Cliente;
import validators.ClienteValidator; //libreria apache commons para las validadciones papu
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller de Cliente: orquesta llamadas a los DAOs.
 * Ahora cumple SRP y el patrón DAO por completo.
 */
public class ClienteControlador {
    private static final Logger LOGGER = Logger.getLogger(ClienteControlador.class.getName());

    // DAOs inyectados
    private final ClienteDAO clienteDAO         = new ClienteDAO();
    private final ComunaDAO comunaDAO           = new ComunaDAO();
    private final CuentasClienteDAO cuentasDAO  = new CuentasClienteDAO();

    // Métodos para compatibilidad con la vista
    public List<Cliente> obtenerTodosLosClientes() {
        return listarClientes();
    }

    public List<Cliente> buscarClientes(String criterio) {
        LOGGER.info("[Controlador] buscarClientes con criterio: " + criterio);
        if (criterio == null || criterio.trim().isEmpty()) {
            return listarClientes();
        }
        // Implementar búsqueda por criterio
        return clienteDAO.buscarPorCriterio(criterio);
    }

    /**
     * Crea un nuevo cliente con validaciones
     * @param c Cliente a crear
     * @return List<String> con errores o null si todo está bien
     */
    public List<String> crearClienteConValidacion(Cliente c) {
        LOGGER.info("[Controlador] Validando cliente para crear → " + c.getRut());

        List<String> errores = ClienteValidator.validarCliente(
            c.getRut(),
            c.getNombres(),
            c.getApellidoP(),
            c.getApellidoM(),
            c.getCorreo(),
            String.valueOf(c.getTelefono()),
            c.getEdad()
        );

        if (errores.isEmpty()) {
            // Formatear el RUT antes de guardar
            c.setRut(ClienteValidator.formatearRut(c.getRut()));
            if (clienteDAO.crearCliente(c)) {
                return null; // null indica éxito
            } else {
                errores.add("Error al guardar en la base de datos");
            }
        }

        return errores;
    }

    /**
     * Actualiza un cliente con validaciones
     * @param c Cliente a actualizar
     * @return List<String> con errores o null si todo está bien
     */
    public List<String> actualizarClienteConValidacion(Cliente c) {
        LOGGER.info("[Controlador] Validando cliente para actualizar → " + c.getRut());

        List<String> errores = ClienteValidator.validarCliente(
            c.getRut(),
            c.getNombres(),
            c.getApellidoP(),
            c.getApellidoM(),
            c.getCorreo(),
            String.valueOf(c.getTelefono()),
            c.getEdad()
        );

        if (errores.isEmpty()) {
            // Formatear el RUT antes de actualizar
            c.setRut(ClienteValidator.formatearRut(c.getRut()));
            if (clienteDAO.actualizarCliente(c)) {
                return null; // null indica éxito
            } else {
                errores.add("Error al actualizar en la base de datos");
            }
        }

        return errores;
    }

    // Mantener los métodos antiguos por compatibilidad
    public boolean crearCliente(Cliente c) {
        List<String> errores = crearClienteConValidacion(c);
        return errores == null;
    }

    public boolean actualizarCliente(Cliente c) {
        List<String> errores = actualizarClienteConValidacion(c);
        return errores == null;
    }

    public boolean eliminarCliente(String rut) {
        LOGGER.info("[Controlador] eliminarCliente → " + rut);
        return clienteDAO.eliminarCliente(rut);
    }

    public List<Cliente> listarClientes() {
        LOGGER.info("[Controlador] listarClientes");
        return clienteDAO.listarClientes();
    }

    public Cliente obtenerClientePorRut(String rut) {
        LOGGER.info("[Controlador] obtenerClientePorRut → " + rut);
        return clienteDAO.obtenerClientePorRut(rut);
    }

    // Búsqueda avanzada
    public List<Object[]> buscarClientesAvanzado(
            String rut, String nombre, String apellidoP,
            String apellidoM, String direccion,
            String tipoCuenta, String comuna) {
        LOGGER.info("[Controlador] buscarClientesAvanzado");
        return clienteDAO.buscarClientesAvanzado(
            rut, nombre, apellidoP, apellidoM,
            direccion, tipoCuenta, comuna
        );
    }
}
