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
import entidades.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – PaisDAO: maneja listado de PAISES usando procedimientos almacenados.
 */
public class PaisDAO {
    private static final Logger LOGGER = Logger.getLogger(PaisDAO.class.getName());

    /**
     * Lista todos los países
     * 
     * @return Lista de todos los países
     */
    public List<Pais> listarTodos() {
        List<Pais> lista = new ArrayList<>();
        String sql = "{CALL listarPaises()}"; // Necesitamos crear este procedimiento
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPais(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Paises: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene un país por su ID
     * 
     * @param idPais ID del país
     * @return País encontrado o null si no existe
     */
    public Pais obtenerPorId(String idPais) {
        String sql = "{CALL obtenerPaisPorId(?)}"; // Necesitamos crear este procedimiento
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPais);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearPais(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener País: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Pais
     * 
     * @param rs ResultSet con los datos del país
     * @return Pais mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Pais mapearPais(ResultSet rs) throws SQLException {
        return new Pais(
                rs.getString("IDPAIS"),
                rs.getString("DESCRIPCION"));
    }
}
