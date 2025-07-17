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
 * Patrón DAO: maneja exclusivamente la persistencia de Pedido.
 * SOLID – SRP: sólo contiene lógica de acceso a datos.
 * Seguridad: usa PreparedStatement para prevenir SQL Injection.
 */
public class PedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(PedidoDAO.class.getName());

    public boolean crear(Pedido p) {
        String sql = "INSERT INTO PEDIDOS (IDPEDIDO, RUTCLIENTE, FECHA, TOTAL, ESTADO) VALUES (?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getIdPedido());
            ps.setString(2, p.getRutCliente());
            ps.setDate(3, p.getFecha());
            ps.setDouble(4, p.getTotal());
            ps.setString(5, p.getEstado());
            LOGGER.info("Insertando Pedido: " + p.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear Pedido: " + e.getMessage());
            return false;
        }
    }

    public Pedido obtenerPorId(String id) {
        String sql = "SELECT * FROM PEDIDOS WHERE IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                      rs.getString("IDPEDIDO"),
                      rs.getString("RUTCLIENTE"),
                      rs.getDate("FECHA"),
                      rs.getDouble("TOTAL"),
                      rs.getString("ESTADO")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener Pedido: " + e.getMessage());
        }
        return null;
    }

    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDOS";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Pedido(
                  rs.getString("IDPEDIDO"),
                  rs.getString("RUTCLIENTE"),
                  rs.getDate("FECHA"),
                  rs.getDouble("TOTAL"),
                  rs.getString("ESTADO")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listar Pedidos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Pedido p) {
        String sql = "UPDATE PEDIDOS SET RUTCLIENTE=?, FECHA=?, TOTAL=?, ESTADO=? WHERE IDPEDIDO=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getRutCliente());
            ps.setDate(2, p.getFecha());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getEstado());
            ps.setString(5, p.getIdPedido());
            LOGGER.info("Actualizando Pedido: " + p.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar Pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM PEDIDOS WHERE IDPEDIDO = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            LOGGER.info("Eliminando Pedido: " + id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminar Pedido: " + e.getMessage());
            return false;
        }
    }
}
