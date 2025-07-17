/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */


import dao.ActividadSolicitudDAO;
import entidades.ActividadSolicitud;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para ActividadSolicitud.
 */
public class ControladorActividades_solicitudes {
    private static final Logger LOGGER = Logger.getLogger(ControladorActividades_solicitudes.class.getName());
    private final ActividadSolicitudDAO dao = new ActividadSolicitudDAO();

    public boolean crearRelacion(ActividadSolicitud as) {
        LOGGER.info("crearRelacion(act=" + as.getIdActividad() + ", sol=" + as.getIdSolicitud() + ")");
        return dao.crear(as);
    }

    public List<ActividadSolicitud> listarPorActividad(String idActividad) {
        LOGGER.info("listarPorActividad: " + idActividad);
        return dao.listarPorActividad(idActividad);
    }

    public List<ActividadSolicitud> listarPorSolicitud(String idSolicitud) {
        LOGGER.info("listarPorSolicitud: " + idSolicitud);
        return dao.listarPorSolicitud(idSolicitud);
    }

    public boolean eliminarRelacion(String idActividad, String idSolicitud) {
        LOGGER.info("eliminarRelacion(act=" + idActividad + ", sol=" + idSolicitud + ")");
        return dao.eliminar(idActividad, idSolicitud);
    }
}
