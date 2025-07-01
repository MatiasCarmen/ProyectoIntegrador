/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.DetalleDescuentoDAO;
import entidades.DetalleDescuento;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para DetalleDescuento.
 */
public class ControladorDetalle_descuentos {
    private static final Logger LOGGER = Logger.getLogger(ControladorDetalle_descuentos.class.getName());
    private final DetalleDescuentoDAO dao = new DetalleDescuentoDAO();

    public boolean crear(DetalleDescuento dd) {
        LOGGER.info("crearDetalleDescuento: disc=" + dd.getIdDescuentos() + " prod=" + dd.getIdProducto());
        return dao.crear(dd);
    }

    public List<DetalleDescuento> listarPorDescuento(String idDisc) {
        LOGGER.info("listarPorDescuento: " + idDisc);
        return dao.listarPorDescuento(idDisc);
    }

    public List<DetalleDescuento> listarPorProducto(String idProd) {
        LOGGER.info("listarPorProducto: " + idProd);
        return dao.listarPorProducto(idProd);
    }

    public boolean eliminar(String idDisc, String idProd) {
        LOGGER.info("eliminarDetalleDescuento: disc=" + idDisc + " prod=" + idProd);
        return dao.eliminar(idDisc, idProd);
    }
    
     public ArrayList<DetalleDescuento> obtenerDetallesDescuento(String idDescuento) {
       LOGGER.info("obtenerDetallesDescuento: disc=" + idDescuento);
        return dao.obtenerDetallesDescuento(idDescuento);
     }
}
