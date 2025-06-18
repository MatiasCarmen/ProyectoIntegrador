/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.ClienteDAO;
import dao.ComunaDAO;
import dao.CuentasClienteDAO;
import entidades.Cliente;
import entidades.Comuna;

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

    // CRUD básicos delegando al DAO
    public boolean crearCliente(Cliente c) {
        LOGGER.info("[Controlador] crearCliente → " + c.getRut());
        return clienteDAO.crearCliente(c);
    }

    public Cliente obtenerClientePorRut(String rut) {
        LOGGER.info("[Controlador] obtenerClientePorRut → " + rut);
        return clienteDAO.obtenerClientePorRut(rut);
    }

    public boolean actualizarCliente(Cliente c) {
        LOGGER.info("[Controlador] actualizarCliente → " + c.getRut());
        return clienteDAO.actualizarCliente(c);
    }

    public boolean eliminarCliente(String rut) {
        LOGGER.info("[Controlador] eliminarCliente → " + rut);
        return clienteDAO.eliminarCliente(rut);
    }

    public List<Cliente> listarClientes() {
        LOGGER.info("[Controlador] listarClientes");
        return clienteDAO.listarClientes();
    }

    /**
     * Búsqueda avanzada de clientes:
     * delega al DAO que hace el JOIN con CUENTAS_CLIENTES.
     */
    public List<Object[]> buscarClientesAvanzado(
            String rut,
            String nombre,
            String apellidoP,
            String apellidoM,
            String direccion,
            String tipoCuenta,
            String comuna) {
        LOGGER.info("[Controlador] buscarClientesAvanzado");
        return clienteDAO.buscarClientesAvanzado(
            rut, nombre, apellidoP, apellidoM, direccion, tipoCuenta, comuna
        );
    }
}
