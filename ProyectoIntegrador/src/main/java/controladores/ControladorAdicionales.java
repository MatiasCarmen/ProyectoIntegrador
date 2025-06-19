/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */
import dao.AdicionalDAO;
import entidades.Adicional;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Adicional.
 * SOLID – SRP: delega persistencia a AdicionalDAO.
 */
public class ControladorAdicionales {
    private static final Logger LOGGER = Logger.getLogger(ControladorAdicionales.class.getName());
    private final AdicionalDAO dao = new AdicionalDAO();

    public boolean crearAdicional(Adicional a) {
        LOGGER.info("crearAdicional: " + a.getIdAdicionales());
        return dao.crear(a);
    }

    public Adicional obtenerAdicional(String id) {
        LOGGER.info("obtenerAdicional: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Adicional> listarAdicionales() {
        LOGGER.info("listarAdicionales");
        return dao.listarTodos();
    }

    public boolean actualizarAdicional(Adicional a) {
        LOGGER.info("actualizarAdicional: " + a.getIdAdicionales());
        return dao.actualizar(a);
    }

    public boolean eliminarAdicional(String id) {
        LOGGER.info("eliminarAdicional: " + id);
        return dao.eliminar(id);
    }
}
