/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.PlanDAO;
import entidades.Plan;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Plan.
 */
public class ControladorPlanes {
    private static final Logger LOGGER = Logger.getLogger(ControladorPlanes.class.getName());
    private final PlanDAO dao = new PlanDAO();

    public boolean crearPlan(Plan p) {
       	LOGGER.info("crearPlan: " + p.getIdPlan());
        return dao.crear(p);
    }

    public Plan obtenerPlan(String id) {
       	LOGGER.info("obtenerPlan: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Plan> listarPlanes() {
       	LOGGER.info("listarPlanes");
        return dao.listarTodos();
    }

    public boolean actualizarPlan(Plan p) {
       	LOGGER.info("actualizarPlan: " + p.getIdPlan());
        return dao.actualizar(p);
    }

    public boolean eliminarPlan(String id) {
       	LOGGER.info("eliminarPlan: " + id);
        return dao.eliminar(id);
    }
    public List<Plan> getPlanesInstalados(String idCuenta) {
    LOGGER.info("getPlanesInstalados → " + idCuenta);
    return dao.obtenerPlanesInstaladosPorCuenta(idCuenta);
}

}
