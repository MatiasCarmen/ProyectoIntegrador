/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import BD.ConexionBD;
import entidades.ProductoInstalado;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * DAO/Controlador para PRODUCTOS_INSTALADOS.
 */
public class ControladorProductosInstalados {

    private static final Logger LOGGER = Logger.getLogger(ControladorProductosInstalados.class.getName());

    /**
     * Obtiene un map de lista de descripciones por tipo de producto.
     * Tipo = PLAN / ADICIONAL / DESCUENTO
     */
    public Map<String,List<String>> obtenerProductosPorTipo(String idCuenta) {
        Map<String,List<String>> resultado = new HashMap<>();
        try (Connection conn = ConexionBD.conectar()) {
            // 1) Planes instalados
            String sqlPlanes = 
              "SELECT 'PLAN' AS tipo, P.NOMBRE AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN PLANES P ON PI.IDPLAN = P.IDPLAN " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlPlanes)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

            // 2) Adicionales instalados
            String sqlAdic = 
              "SELECT 'ADICIONAL' AS tipo, A.DESCRIPCION AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN ADICIONALES A ON PI.IDADICIONALES = A.IDADICIONAL " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAdic)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

            // 3) Descuentos instalados
            String sqlDesc = 
              "SELECT 'DESCUENTO' AS tipo, D.DESCRIPCION AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN DESCUENTOS D ON PI.IDDESCUENTOS = D.IDDESCUENTO " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDesc)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<ProductoInstalado> obtenerProductosPorCuenta(String idCuenta) {
        LOGGER.info("[Ctrl] obtenerProductosPorCuenta → " + idCuenta);
        List<ProductoInstalado> productos = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM PRODUCTOS_INSTALADOS WHERE IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ProductoInstalado producto = new ProductoInstalado();
                        producto.setId(rs.getString("IDPRODUCTO_INSTALADO"));
                        producto.setIdCuenta(rs.getString("IDCUENTA"));
                        producto.setNombre(obtenerNombreProducto(rs));
                        producto.setDescripcion(obtenerDescripcionProducto(rs));
                        producto.setFechaInstalacion(rs.getDate("FECHA_INSTALACION"));
                        producto.setEstado(rs.getString("ESTADO"));
                        productos.add(producto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private String obtenerNombreProducto(ResultSet rs) throws SQLException {
        // Determinar el tipo de producto y obtener su nombre
        if (rs.getString("IDPLAN") != null) {
            return obtenerNombrePlan(rs.getString("IDPLAN"));
        } else if (rs.getString("IDADICIONALES") != null) {
            return obtenerNombreAdicional(rs.getString("IDADICIONALES"));
        } else if (rs.getString("IDDESCUENTOS") != null) {
            return obtenerNombreDescuento(rs.getString("IDDESCUENTOS"));
        }
        return "Sin nombre";
    }

    private String obtenerDescripcionProducto(ResultSet rs) throws SQLException {
        // Similar al nombre pero obteniendo la descripción según el tipo
        if (rs.getString("IDPLAN") != null) {
            return "Plan";
        } else if (rs.getString("IDADICIONALES") != null) {
            return "Adicional";
        } else if (rs.getString("IDDESCUENTOS") != null) {
            return "Descuento";
        }
        return "Sin tipo";
    }

    private String obtenerNombrePlan(String idPlan) {
        // Implementar lógica para obtener nombre del plan
        return "Plan " + idPlan;
    }

    private String obtenerNombreAdicional(String idAdicional) {
        // Implementar lógica para obtener nombre del adicional
        return "Adicional " + idAdicional;
    }

    private String obtenerNombreDescuento(String idDescuento) {
        // Implementar lógica para obtener nombre del descuento
        return "Descuento " + idDescuento;
    }
}
