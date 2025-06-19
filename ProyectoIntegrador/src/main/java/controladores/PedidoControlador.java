/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.PedidoDAO;
import entidades.Pedido;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Pedidos.
 * SOLID – SRP: delega persistencia a PedidoDAO, no contiene SQL.
 * DIP: depende de la abstracción DAO, no de la implementación de BD.
 */
public class PedidoControlador {
    private static final Logger LOGGER = Logger.getLogger(PedidoControlador.class.getName());
    private final PedidoDAO dao = new PedidoDAO();

    public boolean crearPedido(Pedido p) {
        LOGGER.info("Controlador – crearPedido: " + p.getIdPedido());
        return dao.crear(p);
    }

    public Pedido obtenerPedido(String id) {
        LOGGER.info("Controlador – obtenerPedido: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Pedido> listarPedidos() {
        LOGGER.info("Controlador – listarPedidos");
        return dao.listarTodos();
    }

    public boolean actualizarPedido(Pedido p) {
        LOGGER.info("Controlador – actualizarPedido: " + p.getIdPedido());
        return dao.actualizar(p);
    }

    public boolean eliminarPedido(String id) {
        LOGGER.info("Controlador – eliminarPedido: " + id);
        return dao.eliminar(id);
    }
}
