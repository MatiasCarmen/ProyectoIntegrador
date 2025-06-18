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
 * Controller de CuentasCliente: envuelve al DAO si necesitas lógica extra.
 */
public class CuentasClienteControlador {
    private static final Logger LOGGER = Logger.getLogger(CuentasClienteControlador.class.getName());
    private final CuentasClienteDAO dao = new CuentasClienteDAO();

    public List<CuentaCliente> listarTodas() {
        LOGGER.info("Controlador: listarTodas CuentasCliente");
        return dao.listarTodas();
    }
}
