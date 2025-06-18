/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.ComunaDAO;
import entidades.Comuna;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller de Comuna: orquesta llamadas al DAO.
 */
public class ComunasControlador {
    private static final Logger LOGGER = Logger.getLogger(ComunasControlador.class.getName());
    private final ComunaDAO dao = new ComunaDAO();

    public List<Comuna> listarComunas() {
        LOGGER.info("Controlador: listarComunas");
        return dao.listarComunas();
    }
}
