/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.MesaCentralDAO;
import entidades.MesaCentral;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para MesaCentral.
 */
public class ControladorMesa_central {
    private static final Logger LOGGER = Logger.getLogger(ControladorMesa_central.class.getName());
    private final MesaCentralDAO dao = new MesaCentralDAO();

    public boolean crearMesaCentral(MesaCentral m) {
       	LOGGER.info("crearMesaCentral actividad=" + m.getIdActividad());
        return dao.crear(m);
    }

    public MesaCentral obtenerMesaCentral(String idActividad) {
       	LOGGER.info("obtenerMesaCentral: " + idActividad);
        return dao.obtenerPorActividad(idActividad);
    }

    public boolean actualizarMesaCentral(MesaCentral m) {
       	LOGGER.info("actualizarMesaCentral actividad=" + m.getIdActividad());
        return dao.actualizar(m);
    }

    public boolean eliminarMesaCentral(String idActividad) {
       	LOGGER.info("eliminarMesaCentral: " + idActividad);
        return dao.eliminar(idActividad);
    }

    public List<MesaCentral> listarTodos() {
        LOGGER.info("[Ctrl] listarTodos");
        return dao.listarTodos();
    }
    
    public List<MesaCentral> listarTodosPorIdCuenta(String idCuenta) {
        LOGGER.info("[Ctrl] listarTodosPorIdCuenta");
        return dao.listarTodosPorIdCuenta(idCuenta);
    }
}
