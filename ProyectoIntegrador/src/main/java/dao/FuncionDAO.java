/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author mathi
 */

import BD.ConexionBD;
import entidades.Funcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – FuncionDAO: maneja CRUD de FUNCIONES usando procedimientos almacenados.
 */
public class FuncionDAO {
    private static final Logger LOGGER = Logger.getLogger(FuncionDAO.class.getName());

    /**
     * Crea una nueva función usando el procedimiento almacenado insertarFuncion
     * 
     * @param f Función a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(Funcion f) {
        String sql = "{CALL insertarFuncion(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, f.getIdFuncion());
            cs.setString(2, f.getDescripcion());
            LOGGER.info("Insertando Funcion: " + f.getIdFuncion());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Funcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una función por su ID usando el procedimiento almacenado
     * obtenerFuncionPorId
     * 
     * @param id ID de la función
     * @return Función encontrada o null si no existe
     */
    public Funcion obtenerPorId(String id) {
        String sql = "{CALL obtenerFuncionPorId(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncion(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Funcion: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todas las funciones usando el procedimiento almacenado listarFunciones
     * 
     * @return Lista de todas las funciones
     */
    public List<Funcion> listarTodos() {
        List<Funcion> lista = new ArrayList<>();
        String sql = "{CALL listarFunciones()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFuncion(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Funciones: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza una función existente usando el procedimiento almacenado
     * actualizarFuncion
     * 
     * @param f Función a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Funcion f) {
        String sql = "{CALL actualizarFuncion(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, f.getDescripcion());
            cs.setString(2, f.getIdFuncion());
            LOGGER.info("Actualizando Funcion: " + f.getIdFuncion());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Funcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una función por su ID usando el procedimiento almacenado
     * eliminarFuncion
     * 
     * @param id ID de la función a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String id) {
        String sql = "{CALL eliminarFuncion(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            LOGGER.info("Eliminando Funcion: " + id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Funcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Funcion
     * 
     * @param rs ResultSet con los datos de la función
     * @return Funcion mapeada
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Funcion mapearFuncion(ResultSet rs) throws SQLException {
        return new Funcion(
                rs.getString("IDFUNCION"),
                rs.getString("DESCRIPCION"));
    }
}
