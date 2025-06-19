/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.UsuarioRolDAO;
import entidades.UsuarioRol;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para UsuarioRol (Usuario ↔ Rol).
 */
public class ControladorUsuario_rol {
    private static final Logger LOGGER = Logger.getLogger(ControladorUsuario_rol.class.getName());
    private final UsuarioRolDAO dao = new UsuarioRolDAO();

    public boolean asignarRol(UsuarioRol ur) {
       	LOGGER.info("asignarRol usu=" + ur.getIdUsuario() + " rol=" + ur.getIdRol());
        return dao.crear(ur);
    }

    public List<UsuarioRol> listarPorUsuario(String idUsuario) {
       	LOGGER.info("listarPorUsuario: " + idUsuario);
        return dao.listarPorUsuario(idUsuario);
    }

    public boolean eliminarAsignacion(String idUsuario, String idRol) {
       	LOGGER.info("eliminarAsignacion usu=" + idUsuario + " rol=" + idRol);
        return dao.eliminar(idUsuario, idRol);
    }
}
