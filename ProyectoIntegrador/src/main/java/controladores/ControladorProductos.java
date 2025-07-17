/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.ProductoDAO;
import entidades.Producto;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Producto.
 */
public class ControladorProductos {
    private static final Logger LOGGER = Logger.getLogger(ControladorProductos.class.getName());
    private final ProductoDAO dao = new ProductoDAO();

    public boolean crearProducto(Producto p) {
       	LOGGER.info("crearProducto: " + p.getIdProducto());
        return dao.crear(p);
    }

    public Producto obtenerProducto(String id) {
       	LOGGER.info("obtenerProducto: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Producto> listarProductos() {
       	LOGGER.info("listarProductos");
        return dao.listarTodos();
    }

    public boolean actualizarProducto(Producto p) {
       	LOGGER.info("actualizarProducto: " + p.getIdProducto());
        return dao.actualizar(p);
    }

    public boolean eliminarProducto(String id) {
       	LOGGER.info("eliminarProducto: " + id);
        return dao.eliminar(id);
    }
}
