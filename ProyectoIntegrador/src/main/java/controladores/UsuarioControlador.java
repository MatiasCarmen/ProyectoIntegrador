/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.UsuarioDAO;
import entidades.Usuario;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Usuario.
 * SOLID – SRP: delega autenticación y listados a UsuarioDAO.
 */
public class UsuarioControlador {
    private static final Logger LOGGER = Logger.getLogger(UsuarioControlador.class.getName());
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario validarLogin(String idUsuario, String clave) {
        LOGGER.info("validarLogin: " + idUsuario);
        return dao.validarLogin(idUsuario, clave);
    }

    public List<Usuario> listarUsuarios() {
        LOGGER.info("listarUsuarios");
        return dao.listarTodos();
    }
}
