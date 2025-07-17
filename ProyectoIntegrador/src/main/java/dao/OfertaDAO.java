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
import entidades.Oferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – OfertaDAO: abstracción de persistencia para Oferta.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 */
public class OfertaDAO {
    private static final Logger LOGGER = Logger.getLogger(OfertaDAO.class.getName());

    /**
     * Crea una nueva oferta en la base de datos.
     * 
     * @param o La oferta a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(Oferta o) {
        String sql = "{CALL sp_crear_oferta(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, o.getIdOferta());
            cs.setString(2, o.getIdCuenta());
            cs.setString(3, o.getDescripcion());
            cs.setDate(4, o.getFechaInicio());
            cs.setDate(5, o.getFechaFin());
            cs.setDouble(6, o.getPorcentajeDescuento());
            cs.registerOutParameter(7, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Insertar Oferta: " + o.getIdOferta());
            return cs.getBoolean(7);
        } catch (SQLException e) {
            LOGGER.severe("Error crear Oferta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una oferta por su ID.
     * 
     * @param id El ID de la oferta a buscar
     * @return La oferta encontrada o null si no existe
     */
    public Oferta obtenerPorId(String id) {
        String sql = "{CALL sp_obtener_oferta_por_id(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearOferta(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Oferta: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todas las ofertas en la base de datos.
     * 
     * @return Lista de ofertas
     */
    public List<Oferta> listarTodos() {
        List<Oferta> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_ofertas()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearOferta(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Ofertas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza una oferta existente.
     * 
     * @param o La oferta con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Oferta o) {
        String sql = "{CALL sp_actualizar_oferta(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, o.getIdOferta());
            cs.setString(2, o.getIdCuenta());
            cs.setString(3, o.getDescripcion());
            cs.setDate(4, o.getFechaInicio());
            cs.setDate(5, o.getFechaFin());
            cs.setDouble(6, o.getPorcentajeDescuento());
            cs.registerOutParameter(7, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Actualizar Oferta: " + o.getIdOferta());
            return cs.getBoolean(7);
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Oferta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una oferta por su ID.
     * 
     * @param id El ID de la oferta a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String id) {
        String sql = "{CALL sp_eliminar_oferta(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Eliminar Oferta: " + id);
            return cs.getBoolean(2);
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Oferta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Oferta.
     * 
     * @param rs El ResultSet que contiene los datos de la oferta
     * @return Un objeto Oferta con los datos del ResultSet
     * @throws SQLException si hay un error al acceder a los datos
     */
    private Oferta mapearOferta(ResultSet rs) throws SQLException {
        return new Oferta(
                rs.getString("IDOFERTA"),
                rs.getString("IDCUENTA"),
                rs.getString("DESCRIPCION"),
                rs.getDate("FECHAINICIO"),
                rs.getDate("FECHAFIN"),
                rs.getDouble("PORCENTAJEDESCUENTO"));
    }
}
