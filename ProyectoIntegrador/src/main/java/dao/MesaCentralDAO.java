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
import entidades.MesaCentral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – MesaCentralDAO: maneja CRUD de MESA_CENTRAL.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 */
public class MesaCentralDAO {
    private static final Logger LOGGER = Logger.getLogger(MesaCentralDAO.class.getName());

    /**
     * Crea un nuevo registro de mesa central.
     * 
     * @param m La mesa central a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(MesaCentral m) {
        String sql = "{CALL sp_crear_mesa_central(?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, m.getIdActividad());
            cs.setLong(2, m.getTelefono());
            cs.setString(3, m.getLugar());
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Insertando MesaCentral: actividad=" + m.getIdActividad());
            return cs.getBoolean(4);
        } catch (SQLException e) {
            LOGGER.severe("Error crear MesaCentral: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una mesa central por su ID de actividad.
     * 
     * @param idActividad El ID de la actividad asociada
     * @return La mesa central encontrada o null si no existe
     */
    public MesaCentral obtenerPorActividad(String idActividad) {
        String sql = "{CALL sp_obtener_mesa_central_por_actividad(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idActividad);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearMesaCentral(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener MesaCentral: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza un registro de mesa central existente.
     * 
     * @param m La mesa central con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(MesaCentral m) {
        String sql = "{CALL sp_actualizar_mesa_central(?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, m.getIdActividad());
            cs.setLong(2, m.getTelefono());
            cs.setString(3, m.getLugar());
            cs.registerOutParameter(4, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Actualizando MesaCentral: actividad=" + m.getIdActividad());
            return cs.getBoolean(4);
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar MesaCentral: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una mesa central por su ID de actividad.
     * 
     * @param idActividad El ID de la actividad asociada
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String idActividad) {
        String sql = "{CALL sp_eliminar_mesa_central(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idActividad);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Eliminando MesaCentral: actividad=" + idActividad);
            return cs.getBoolean(2);
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar MesaCentral: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todas las mesas centrales.
     * 
     * @return Lista de todas las mesas centrales
     */
    public List<MesaCentral> listarTodos() {
        List<MesaCentral> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_mesas_centrales()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearMesaCentral(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar mesa central: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Lista todas las mesas centrales asociadas a una cuenta.
     * 
     * @param idCuenta El ID de la cuenta
     * @return Lista de mesas centrales de la cuenta
     */
    public List<MesaCentral> listarTodosPorIdCuenta(String idCuenta) {
        List<MesaCentral> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_mesas_centrales_por_cuenta(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMesaCentral(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar mesa central por cuenta: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Filtra mesas centrales por campo y valor específico.
     * 
     * @param idCuenta El ID de la cuenta
     * @param campo    El campo por el cual filtrar
     * @param valor    El valor a buscar
     * @return Lista de mesas centrales que coinciden con el filtro
     */
    public List<MesaCentral> filtrarPorCampoYValor(String idCuenta, String campo, Object valor) {
        List<MesaCentral> lista = new ArrayList<>();
        List<String> camposPermitidos = List.of("LUGAR", "TELEFONO", "IDACTIVIDAD");
        if (!camposPermitidos.contains(campo.toUpperCase())) {
            return lista;
        }

        String sql = "{CALL sp_filtrar_mesas_centrales(?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            cs.setString(2, campo);
            cs.setString(3, valor.toString());

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMesaCentral(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al filtrar mesa central: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Filtra mesas centrales por campo y valor usando LIKE.
     * 
     * @param idCuenta El ID de la cuenta
     * @param campo    El campo por el cual filtrar
     * @param valor    El valor a buscar (parcial)
     * @return Lista de mesas centrales que coinciden con el filtro
     */
    public List<MesaCentral> filtrarPorCampoYValorLike(String idCuenta, String campo, String valor) {
        List<MesaCentral> lista = new ArrayList<>();
        String sql = "{CALL sp_filtrar_mesas_centrales_like(?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idCuenta);
            cs.setString(2, campo);
            cs.setString(3, valor);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMesaCentral(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al filtrar mesa central: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto MesaCentral.
     * 
     * @param rs El ResultSet que contiene los datos
     * @return Un objeto MesaCentral con los datos del ResultSet
     * @throws SQLException si hay un error al acceder a los datos
     */
    private MesaCentral mapearMesaCentral(ResultSet rs) throws SQLException {
        return new MesaCentral(
                rs.getString("IDACTIVIDAD"),
                rs.getLong("TELEFONO"),
                rs.getString("LUGAR"));
    }
}
