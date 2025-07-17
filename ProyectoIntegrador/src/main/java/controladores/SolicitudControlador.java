/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.SolicitudDAO;
import entidades.Solicitud;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Solicitud.
 * SOLID – SRP: delega a SolicitudDAO.
 */
public class SolicitudControlador {
    private static final Logger LOGGER = Logger.getLogger(SolicitudControlador.class.getName());
    private final SolicitudDAO dao = new SolicitudDAO();

    public boolean crearSolicitud(Solicitud s) {
        LOGGER.info("crearSolicitud: " + s.getIdSolicitud());
        return dao.crear(s);
    }

    public Solicitud obtenerSolicitud(String id) {
        LOGGER.info("obtenerSolicitud: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Solicitud> listarSolicitudes() {
        LOGGER.info("listarSolicitudes");
        return dao.listarTodos();
    }

    public boolean actualizarSolicitud(Solicitud s) {
        LOGGER.info("actualizarSolicitud: " + s.getIdSolicitud());
        return dao.actualizar(s);
    }

    public boolean eliminarSolicitud(String id) {
        LOGGER.info("eliminarSolicitud: " + id);
        return dao.eliminar(id);
    }
}
