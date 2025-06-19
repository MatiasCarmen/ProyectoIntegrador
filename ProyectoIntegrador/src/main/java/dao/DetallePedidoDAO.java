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
 */
public class DetallePedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(DetallePedidoDAO.class.getName());

    public boolean crear(DetallePedido dp) {
        String sql = "INSERT INTO DETALLE_PEDIDOS (IDPEDIDO, IDADICIONALES, IDPLAN, IDDESCUENTOS) VALUES (?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dp.getIdPedido());
            ps.setString(2, dp.getIdAdicionales());
            ps.setString(3, dp.getIdPlan());
            ps.setString(4, dp.getIdDescuentos());
            LOGGER.info("Insertando DetallePedido: ped=" + dp.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear DetallePedido: " + e.getMessage());
            return false;
        }
    }

    public List<DetallePedido> listarPorPedido(String idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLE_PEDIDOS WHERE IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetallePedido(
                        rs.getString("IDPEDIDO"),
                        rs.getString("IDADICIONALES"),
                        rs.getString("IDPLAN"),
                        rs.getString("IDDESCUENTOS")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorPedido: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(String idPedido) {
        String sql = "DELETE FROM DETALLE_PEDIDOS WHERE IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPedido);
            LOGGER.info("Eliminando DetallePedido: ped=" + idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar DetallePedido: " + e.getMessage());
            return false;
        }
    }
}
