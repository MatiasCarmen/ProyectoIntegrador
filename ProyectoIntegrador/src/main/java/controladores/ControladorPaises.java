/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */


import dao.PaisDAO;
import entidades.Pais;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Pais.
 */
public class ControladorPaises {
    private static final Logger LOGGER = Logger.getLogger(ControladorPaises.class.getName());
    private final PaisDAO dao = new PaisDAO();

    public List<Pais> listarPaises() {
       	LOGGER.info("listarPaises");
        return dao.listarTodos();
    }
}
