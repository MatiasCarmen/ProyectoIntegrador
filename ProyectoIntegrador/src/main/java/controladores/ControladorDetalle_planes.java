/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */
import dao.DetallePlanDAO;
import entidades.DetallePlan;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para DetallePlan.
 */
public class ControladorDetalle_planes {
    private static final Logger LOGGER = Logger.getLogger(ControladorDetalle_planes.class.getName());
    private final DetallePlanDAO dao = new DetallePlanDAO();

    public boolean crear(DetallePlan dp) {
       	LOGGER.info("crearDetallePlan: plan=" + dp.getIdPlan());
        return dao.crear(dp);
    }

    public List<DetallePlan> listarPorPlan(String idPlan) {
       	LOGGER.info("listarPorPlan: " + idPlan);
        return dao.listarPorPlan(idPlan);
    }

    public boolean eliminar(String idPlan) {
       	LOGGER.info("eliminarDetallePlan: plan=" + idPlan);
        return dao.eliminar(idPlan);
    }
}
