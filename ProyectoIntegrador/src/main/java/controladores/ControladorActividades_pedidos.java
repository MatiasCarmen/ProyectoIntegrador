/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.ActividadPedidoDAO;
import entidades.ActividadPedido;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para ActividadPedido.
 * SOLID – SRP: delega toda la persistencia a ActividadPedidoDAO.
 */
public class ControladorActividades_pedidos {
    private static final Logger LOGGER = Logger.getLogger(ControladorActividades_pedidos.class.getName());
    private final ActividadPedidoDAO dao = new ActividadPedidoDAO();

    public boolean crearRelacion(ActividadPedido ap) {
        LOGGER.info("crearRelacion(act=" + ap.getIdActividad() + ", ped=" + ap.getIdPedido() + ")");
        return dao.crear(ap);
    }

    public List<ActividadPedido> listarPorActividad(String idActividad) {
        LOGGER.info("listarPorActividad: " + idActividad);
        return dao.listarPorActividad(idActividad);
    }

    public List<ActividadPedido> listarPorPedido(String idPedido) {
        LOGGER.info("listarPorPedido: " + idPedido);
        return dao.listarPorPedido(idPedido);
    }

    public boolean eliminarRelacion(String idActividad, String idPedido) {
        LOGGER.info("eliminarRelacion(act=" + idActividad + ", ped=" + idPedido + ")");
        return dao.eliminar(idActividad, idPedido);
    }
}
