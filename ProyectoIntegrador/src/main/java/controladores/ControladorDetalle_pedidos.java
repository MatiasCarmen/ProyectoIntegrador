/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.DetallePedidoDAO;
import entidades.DetallePedido;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para DetallePedido.
 */
public class ControladorDetalle_pedidos {
    private static final Logger LOGGER = Logger.getLogger(ControladorDetalle_pedidos.class.getName());
    private final DetallePedidoDAO dao = new DetallePedidoDAO();

    public boolean crear(DetallePedido dp) {
        LOGGER.info("crearDetallePedido: ped=" + dp.getIdPedido());
        return dao.crear(dp);
    }

    public List<DetallePedido> listarPorPedido(String idPed) {
        LOGGER.info("listarPorPedido: " + idPed);
        return dao.listarPorPedido(idPed);
    }

    public boolean eliminar(String idPed) {
        LOGGER.info("eliminarDetallePedido: ped=" + idPed);
        return dao.eliminar(idPed);
    }
}

