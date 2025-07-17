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
import entidades.DetallePedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – DetallePedidoDAO: maneja CRUD de DETALLE_PEDIDOS.
 * Esta clase ha sido migrada para utilizar procedimientos almacenados.
 */
public class DetallePedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(DetallePedidoDAO.class.getName());

    /**
     * Crea un nuevo detalle de pedido.
     * 
     * @param dp El detalle de pedido a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean crear(DetallePedido dp) {
        String sql = "{CALL sp_crear_detalle_pedido(?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dp.getIdPedido());
            cs.setString(2, dp.getIdAdicionales());
            cs.setString(3, dp.getIdPlan());
            cs.setString(4, dp.getIdDescuentos());
            cs.registerOutParameter(5, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Insertando DetallePedido: ped=" + dp.getIdPedido());
            return cs.getBoolean(5);
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetallePedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los detalles de un pedido específico.
     * 
     * @param idPedido El ID del pedido
     * @return Lista de detalles del pedido
     */
    public List<DetallePedido> listarPorPedido(String idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_detalles_por_pedido(?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPedido);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetallePedido(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPedido: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina todos los detalles de un pedido.
     * 
     * @param idPedido El ID del pedido cuyos detalles se eliminarán
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String idPedido) {
        String sql = "{CALL sp_eliminar_detalles_pedido(?, ?)}";
        try (Connection conn = ConexionBD.conectar();
                CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, idPedido);
            cs.registerOutParameter(2, Types.BOOLEAN);

            cs.execute();
            LOGGER.info("Eliminando DetallePedido: ped=" + idPedido);
            return cs.getBoolean(2);
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetallePedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea un ResultSet a un objeto DetallePedido.
     * 
     * @param rs El ResultSet que contiene los datos
     * @return Un objeto DetallePedido con los datos del ResultSet
     * @throws SQLException si hay un error al acceder a los datos
     */
    private DetallePedido mapearDetallePedido(ResultSet rs) throws SQLException {
        return new DetallePedido(
                rs.getString("IDPEDIDO"),
                rs.getString("IDADICIONALES"),
                rs.getString("IDPLAN"),
                rs.getString("IDDESCUENTOS"));
    }
}
