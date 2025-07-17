/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.RolFuncionDAO;
import entidades.RolFuncion;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para RolFuncion (Rol ↔ Funcion).
 */
public class ControladorRol_funcion {
    private static final Logger LOGGER = Logger.getLogger(ControladorRol_funcion.class.getName());
    private final RolFuncionDAO dao = new RolFuncionDAO();

    public boolean asignarFuncion(RolFuncion rf) {
       	LOGGER.info("asignarFuncion rol=" + rf.getIdRol() + " func=" + rf.getIdFuncion());
        return dao.crear(rf);
    }

    public List<RolFuncion> listarPorRol(String idRol) {
       	LOGGER.info("listarPorRol: " + idRol);
        return dao.listarPorRol(idRol);
    }

    public boolean eliminarAsignacion(String idRol, String idFunc) {
       	LOGGER.info("eliminarAsignacion rol=" + idRol + " func=" + idFunc);
        return dao.eliminar(idRol, idFunc);
    }
}
