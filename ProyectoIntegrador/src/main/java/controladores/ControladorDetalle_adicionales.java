/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.DetalleAdicionalDAO;
import entidades.DetalleAdicional;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para DetalleAdicional.
 */
public class ControladorDetalle_adicionales {
    private static final Logger LOGGER = Logger.getLogger(ControladorDetalle_adicionales.class.getName());
    private final DetalleAdicionalDAO dao = new DetalleAdicionalDAO();

    public boolean crear(DetalleAdicional da) {
        LOGGER.info("crearDetalleAdicional: adic=" + da.getIdAdicionales() + " prod=" + da.getIdProducto());
        return dao.crear(da);
    }

    public List<DetalleAdicional> listarPorAdicional(String idAd) {
        LOGGER.info("listarPorAdicional: " + idAd);
        return dao.listarPorAdicional(idAd);
    }

    public List<DetalleAdicional> listarPorProducto(String idProd) {
        LOGGER.info("listarPorProducto: " + idProd);
        return dao.listarPorProducto(idProd);
    }

    public boolean eliminar(String idAd, String idProd) {
        LOGGER.info("eliminarDetalleAdicional: adic=" + idAd + " prod=" + idProd);
        return dao.eliminar(idAd, idProd);
    }
}
