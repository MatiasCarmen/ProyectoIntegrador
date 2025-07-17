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
import entidades.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – PedidoDAO: maneja CRUD de PEDIDOS usando procedimientos almacenados.
 * Adaptado a la entidad Pedido actual.
 */
public class PedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(PedidoDAO.class.getName());

    /**
     * Crea un nuevo pedido usando el procedimiento almacenado sp_crear_pedido
     * 
     * @param p Pedido a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean crear(Pedido p) {
        String sql = "{CALL sp_crear_pedido(?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, p.getIdPedido());
            cs.setString(2, p.getRutCliente());
            cs.setDate(3, p.getFecha());
            cs.setDouble(4, p.getTotal());
            cs.setString(5, p.getEstado());
            LOGGER.info("Insertando Pedido: " + p.getIdPedido());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un pedido por su ID usando el procedimiento almacenado
     * sp_obtener_pedido_por_id
     * 
     * @param id ID del pedido
     * @return Pedido encontrado o null si no existe
     */
    public Pedido obtenerPorId(String id) {
        String sql = "{CALL sp_obtener_pedido_por_id(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Pedido: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los pedidos usando el procedimiento almacenado sp_listar_pedidos
     * 
     * @return Lista de todos los pedidos
     */
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_pedidos()}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Pedidos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza un pedido existente usando el procedimiento almacenado
     * sp_actualizar_pedido
     * 
     * @param p Pedido a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Pedido p) {
        String sql = "{CALL sp_actualizar_pedido(?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, p.getIdPedido());
            cs.setString(2, p.getRutCliente());
            cs.setDate(3, p.getFecha());
            cs.setDouble(4, p.getTotal());
            cs.setString(5, p.getEstado());
            LOGGER.info("Actualizando Pedido: " + p.getIdPedido());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido por su ID usando el procedimiento almacenado
     * sp_eliminar_pedido
     * 
     * @param id ID del pedido a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(String id) {
        String sql = "{CALL sp_eliminar_pedido(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, id);
            LOGGER.info("Eliminando Pedido: " + id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Pedido
     * 
     * @param rs ResultSet con los datos del pedido
     * @return Pedido mapeado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        return new Pedido(
                rs.getString("IDPEDIDO"),
                rs.getString("RUTCLIENTE"),
                rs.getDate("FECHA"),
                rs.getDouble("TOTAL"),
                rs.getString("ESTADO"));
    }
}
