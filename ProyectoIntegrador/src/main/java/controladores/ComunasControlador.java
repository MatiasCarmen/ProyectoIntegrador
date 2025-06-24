/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
/**
 *
 * @author matias papu
 */
import dao.ComunaDAO;
import entidades.Comuna;

import java.util.List;
import java.util.logging.Logger;

/**
 * MVC – Controlador de Comunas:
 *  • SRP: única responsabilidad: orquestar operaciones de comunas.
 *  • Usa DAO para separar la lógica de acceso a datos (DAO).
 */
public class ComunasControlador {
    private static final Logger LOGGER = Logger.getLogger(ComunasControlador.class.getName());
    private final ComunaDAO dao = new ComunaDAO();

    /**
     * @return lista de todas las comunas (para llenar combos)
     */
    public List<Comuna> listarComunas() {
        LOGGER.info("ComunasControlador: listarComunas");
        return dao.listarComunas();
    }

    /**
     * Devuelve el ID de comuna dado su nombre/descripción.
     * @param descripcion nombre de la comuna
     * @return ID correspondiente o null si no existe
     */
    public String obtenerIdPorDescripcion(String descripcion) {
        LOGGER.info("ComunasControlador: obtenerIdPorDescripcion -> " + descripcion);
        return dao.obtenerIdPorDescripcion(descripcion);
    }
}
