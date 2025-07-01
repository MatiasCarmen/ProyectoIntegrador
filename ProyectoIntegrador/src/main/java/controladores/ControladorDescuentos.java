/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */
import dao.DescuentoDAO;
import entidades.Descuento;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Descuento.
 */
public class ControladorDescuentos {
    private static final Logger LOGGER = Logger.getLogger(ControladorDescuentos.class.getName());
    private final DescuentoDAO dao = new DescuentoDAO();

    public boolean crearDescuento(Descuento d) {
        LOGGER.info("crearDescuento: " + d.getIdDescuentos());
        return dao.crear(d);
    }

    public Descuento obtenerDescuento(String id) {
        LOGGER.info("obtenerDescuento: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Descuento> listarDescuentos() {
        LOGGER.info("listarDescuentos");
        return dao.listarTodos();
    }

    public boolean actualizarDescuento(Descuento d) {
        LOGGER.info("actualizarDescuento: " + d.getIdDescuentos());
        return dao.actualizar(d);
    }

    public boolean eliminarDescuento(String id) {
        LOGGER.info("eliminarDescuento: " + id);
        return dao.eliminar(id);
    }
    
       public Descuento obtenerDescuentoPorIdCuenta(String idCuenta) {
           LOGGER.info("obtenerDescuentoPorIdCuenta: " + idCuenta);
        return dao.obtenerDescuentoPorIdCuenta(idCuenta);
     }
}
