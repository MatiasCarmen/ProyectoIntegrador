/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */
import dao.OfertaDAO;
import entidades.Oferta;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Oferta.
 * SOLID – SRP: delega a OfertaDAO.
 */
public class OfertaControlador {
    private static final Logger LOGGER = Logger.getLogger(OfertaControlador.class.getName());
    private final OfertaDAO dao = new OfertaDAO();

    public boolean crearOferta(Oferta o) {
        LOGGER.info("crearOferta: " + o.getIdOferta());
        return dao.crear(o);
    }

    public Oferta obtenerOferta(String id) {
        LOGGER.info("obtenerOferta: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Oferta> listarOfertas() {
        LOGGER.info("listarOfertas");
        return dao.listarTodos();
    }

    public boolean actualizarOferta(Oferta o) {
        LOGGER.info("actualizarOferta: " + o.getIdOferta());
        return dao.actualizar(o);
    }

    public boolean eliminarOferta(String id) {
        LOGGER.info("eliminarOferta: " + id);
        return dao.eliminar(id);
    }
}
