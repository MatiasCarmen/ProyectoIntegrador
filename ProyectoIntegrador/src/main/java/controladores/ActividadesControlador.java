/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mathi
 */
package Controladores;

import entidades.Actividad;
import dao.ActividadDAO;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller de Actividades: orquesta llamadas al DAO.
 */
public class ActividadesControlador {
    private static final Logger LOGGER = Logger.getLogger(ActividadesControlador.class.getName());
    private final ActividadDAO dao = new ActividadDAO();

    public boolean crearActividad(Actividad a) {
        LOGGER.info("[Ctrl] crearActividad → " + a.getIdActividad());
        return dao.crearActividad(a);
    }

    public List<Actividad> listarPorCuenta(String idCuenta) {
        LOGGER.info("[Ctrl] listarPorCuenta → " + idCuenta);
        return dao.listarPorCuenta(idCuenta);
    }

    public Actividad obtenerPorId(String idActividad) {
        LOGGER.info("[Ctrl] obtenerPorId → " + idActividad);
        return dao.obtenerPorId(idActividad);
    }

    public boolean actualizarActividad(Actividad a) {
        LOGGER.info("[Ctrl] actualizarActividad → " + a.getIdActividad());
        return dao.actualizarActividad(a);
    }

    public boolean eliminarActividad(String idActividad) {
        LOGGER.info("[Ctrl] eliminarActividad → " + idActividad);
        return dao.eliminarActividad(idActividad);
    }
}
