/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author mathi
 */

import dao.FuncionDAO;
import entidades.Funcion;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador: orquesta la lógica de negocio para Funcion.
 */
public class ControladorFunciones {
    private static final Logger LOGGER = Logger.getLogger(ControladorFunciones.class.getName());
    private final FuncionDAO dao = new FuncionDAO();

    public boolean crearFuncion(Funcion f) {
       	LOGGER.info("crearFuncion: " + f.getIdFuncion());
        return dao.crear(f);
    }

    public Funcion obtenerFuncion(String id) {
       	LOGGER.info("obtenerFuncion: " + id);
        return dao.obtenerPorId(id);
    }

    public List<Funcion> listarFunciones() {
       	LOGGER.info("listarFunciones");
        return dao.listarTodos();
    }

    public boolean actualizarFuncion(Funcion f) {
       	LOGGER.info("actualizarFuncion: " + f.getIdFuncion());
        return dao.actualizar(f);
    }

    public boolean eliminarFuncion(String id) {
       	LOGGER.info("eliminarFuncion: " + id);
        return dao.eliminar(id);
    }
}
