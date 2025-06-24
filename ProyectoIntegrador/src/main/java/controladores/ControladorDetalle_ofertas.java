/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author matias papu
 */
import dao.DetalleOfertaDAO;
import entidades.DetalleOferta;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para DetalleOferta.
 */
public class ControladorDetalle_ofertas {
    private static final Logger LOGGER = Logger.getLogger(ControladorDetalle_ofertas.class.getName());
    private final DetalleOfertaDAO dao = new DetalleOfertaDAO();

    public boolean crear(DetalleOferta dof) {
        LOGGER.info("crearDetalleOferta: of=" + dof.getIdOfertas() + " prod=" + dof.getIdProducto());
        return dao.crear(dof);
    }

    public List<DetalleOferta> listarPorOferta(String idOf) {
        LOGGER.info("listarPorOferta: " + idOf);
        return dao.listarPorOferta(idOf);
    }

    public List<DetalleOferta> listarPorProducto(String idProd) {
        LOGGER.info("listarPorProducto: " + idProd);
        return dao.listarPorProducto(idProd);
    }

    public boolean eliminar(String idOf, String idProd) {
        LOGGER.info("eliminarDetalleOferta: of=" + idOf + " prod=" + idProd);
        return dao.eliminar(idOf, idProd);
    }
}

