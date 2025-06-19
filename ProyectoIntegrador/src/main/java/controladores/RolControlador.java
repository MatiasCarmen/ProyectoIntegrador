/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */
import dao.RolDAO;
import entidades.Rol;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Rol.
 * SOLID – SRP: delega a RolDAO.
 */
public class RolControlador {
    private static final Logger LOGGER = Logger.getLogger(RolControlador.class.getName());
    private final RolDAO dao = new RolDAO();

    public List<Rol> listarRoles() {
        LOGGER.info("listarRoles");
        return dao.listarTodos();
    }
}
